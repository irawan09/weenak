package irawan.electroshock.weenak.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import irawan.electroshock.weenak.api.RetrofitServiceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class DataRepository {
    var post = MutableLiveData<ArrayList<FullRecipe>>()

    var recipeArray : ArrayList<RecipeModel> = ArrayList()
    var ingredientsArray : ArrayList<String> = ArrayList()
    var fullIngredientsArray : ArrayList<FullIngredients> = ArrayList()
    var completeArray : ArrayList<FullRecipe> = ArrayList()

    fun puncRemoval(data : String) : String{
        var feedback = data.drop(1)
        feedback = feedback.dropLast(1)
        return feedback
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
                            var foodImageNew = puncRemoval(foodImage.toString())
                            var foodDescription = items[i].seo?.web?.metaTags?.description ?: "N/A"
                            var foodPreparationSteps = items[i].content?.prepartionsSteps ?: "N/A"
                            var foodPreparationStepsNew = puncRemoval(foodPreparationSteps.toString())
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

                        for (k in 0 until recipeArray.size){
                            val fullRecipe =
                                    FullRecipe (
                                    recipeArray,
                                    fullIngredientsArray
                                    )
                            completeArray.add(fullRecipe)
                        }
                        post.value = completeArray
                        Log.d("Data", post.value.toString())
//                        mRequestQueue.add(post)

                    }
                }
            }
        }

    }
}