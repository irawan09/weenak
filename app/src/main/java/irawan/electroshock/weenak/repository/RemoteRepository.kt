package irawan.electroshock.weenak.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import irawan.electroshock.weenak.MainActivity.Companion.puncRemoval
import irawan.electroshock.weenak.api.RetrofitServiceFactory
import irawan.electroshock.weenak.model.FullIngredients
import irawan.electroshock.weenak.model.FullRecipe
import irawan.electroshock.weenak.model.RecipeModel
import irawan.electroshock.weenak.model.Summary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class RemoteRepository {
    var recipeArray : ArrayList<RecipeModel> = ArrayList()
    var ingredientsArray : ArrayList<String> = ArrayList()
    var fullIngredientsArray : ArrayList<FullIngredients> = ArrayList()
    private var fullRecipeResponseLiveData: MutableLiveData<FullRecipe>? = null

    var summary = Summary()

    init {
        fullRecipeResponseLiveData = MutableLiveData<FullRecipe>()
        JSONData()
    }

    fun JSONData() {
        val service = RetrofitServiceFactory.createService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()?.feed
                    if (items != null) {
                        for (i in 0 until items.count()) {
                            var foodName = items[i].display?.displayName ?: "N/A"
                            var foodImage = items[i].display?.images ?: "N/A"
                            var foodImageNew = puncRemoval(foodImage.toString(), 1, 1)
                            var foodDescription = items[i].seo?.web?.metaTags?.description ?: "N/A"
                            var foodPreparationSteps = items[i].content?.prepartionsSteps ?: "N/A"
                            var foodPreparationStepsNew = puncRemoval(foodPreparationSteps.toString(), 1, 1)
                            var foodVideos = items[i].content?.videos?.originalVideoUrl ?: "N/A"
                            var foodTotalTime = items[i].content?.details?.totalTime ?: "N/A"
                            var foodNumberOfServings = items[i].content?.details?.numberOfServings ?: "N/A"
                            var foodRatings = items[i].content?.details?.rating ?: "N/A"

                            val dataRecipe =
                                RecipeModel(
                                    foodName,
                                    foodImageNew,
                                    foodDescription,
                                    foodPreparationStepsNew,
                                    foodVideos,
                                    foodTotalTime,
                                    foodNumberOfServings,
                                    foodRatings
                                )
                            recipeArray.add(dataRecipe)
                        }

                        for (j in 0 until items.count()) {
                            var foodIngredient = items[j].content?.ingredientLines
                            if (foodIngredient != null) {
                                for (k in 0 until foodIngredient.count()) {
                                    var ingredient = foodIngredient[k].ingredient ?: "N/A"
                                    ingredientsArray.add(ingredient)
                                }
                                val fullIngredients =
                                    FullIngredients(
                                        ingredientsArray
                                    )
                                fullIngredientsArray.add(fullIngredients)
                            }
                        }

                        for (k in 0 until items.count()){
                            val fullRecipe =
                                    FullRecipe (
                                    recipeArray,
                                    fullIngredientsArray
                                    )
                            fullRecipeResponseLiveData?.postValue(fullRecipe)

                        }
                    } else {
                        Log.e("Retrofit Error", response.code().toString())
                    }
                }
            }
        }
    }

    fun getFullRecipeResponseLiveData() : LiveData<FullRecipe>{
        return fullRecipeResponseLiveData!!
    }
}