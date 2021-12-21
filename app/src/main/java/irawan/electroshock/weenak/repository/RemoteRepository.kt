package irawan.electroshock.weenak.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import irawan.electroshock.weenak.MainActivity.Companion.puncRemoval
import irawan.electroshock.weenak.api.RetrofitServiceFactory
import irawan.electroshock.weenak.database.RecipeDao
import irawan.electroshock.weenak.model.FullIngredients
import irawan.electroshock.weenak.model.FullRecipe
import irawan.electroshock.weenak.model.RecipeModel
import irawan.electroshock.weenak.model.DatabaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class RemoteRepository() {
    private var recipeArray : ArrayList<RecipeModel> = ArrayList()
    private var ingredientArray : ArrayList<String> = ArrayList()
    private var fullIngredientsArray : ArrayList<FullIngredients> = ArrayList()
    private var fullRecipeResponseLiveData: MutableLiveData<FullRecipe>? = null
    private var databaseFullRecipeLivedata: MutableLiveData<DatabaseModel>? =null

    init {
        fullRecipeResponseLiveData = MutableLiveData<FullRecipe>()
        databaseFullRecipeLivedata = MutableLiveData<DatabaseModel>()
        jsonData()
    }

    fun jsonData() {
        val service = RetrofitServiceFactory.createService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()?.feed
                    if (items != null) {
                        for (i in 0 until items.count()) {
                            items[i].let {
                                val foodName = it.display?.displayName ?: "N/A"
                                val foodImage = it.display?.images ?: "N/A"
                                val foodImageNew = puncRemoval(foodImage.toString(), 1, 1)
                                val foodDescription = it.seo?.web?.metaTags?.description ?: "N/A"
                                val foodPreparationSteps = it.content?.prepartionsSteps ?: "N/A"
                                val foodPreparationStepsNew = puncRemoval(foodPreparationSteps.toString(), 1, 1)
                                val foodVideos = it.content?.videos?.originalVideoUrl ?: "N/A"
                                val foodTotalTime = it.content?.details?.totalTime ?: "N/A"
                                val foodNumberOfServings = it.content?.details?.numberOfServings ?: "N/A"
                                val foodRatings = it.content?.details?.rating ?: "N/A"

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
                        }

                        for (j in 0 until items.count()) {
                            var foodIngredient = items[j].content?.ingredientLines
                            if (foodIngredient != null) {
                                for (k in 0 until foodIngredient.count()) {
                                    var ingredient = foodIngredient[k].ingredient ?: "N/A"
                                    ingredientArray.add(ingredient)
                                }
                                val fullIngredients =
                                    FullIngredients(
                                        ingredientArray
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

                        for (l in 0 until items.count()){
                            recipeArray[l].let {
                                val foodName = it.foodName
                                val foodImage = it.foodImage
                                val foodDescription = it.foodDescription
                                val foodPreparation = it.foodPreparationSteps
                                val foodVideo = it.foodVideos
                                val foodTotalTime = it.foodTotalTime
                                val foodNumberOfServings = it.foodNumberOfServings
                                val foodRating = it.foodRatings
                                val foodIngredients = fullIngredientsArray[l].fullIngredients.toString()

                                val database =
                                    DatabaseModel(
                                        foodName,
                                        foodImage,
                                        foodDescription,
                                        foodPreparation,
                                        foodVideo,
                                        foodTotalTime,
                                        foodNumberOfServings,
                                        foodRating,
                                        foodIngredients
                                    )
                                databaseFullRecipeLivedata!!.postValue(database)
                            }
                        }

                    } else {
                        Log.e("Retrofit Error", response.code().toString())
                    }
                }
            }
        }
    }

    fun getFullRecipeResponseLiveData() : MutableLiveData<FullRecipe>{
        return fullRecipeResponseLiveData!!
    }

    fun getDatabaseFullRecipeLivedata() : MutableLiveData<DatabaseModel>{
        return databaseFullRecipeLivedata!!
    }

}