package irawan.electroshock.weenak.model

import android.util.Log
import irawan.electroshock.weenak.api.RetrofitServiceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JSONParser {
    var recipeArray : ArrayList<RecipeModel> = ArrayList()
    var ingredientsArray : ArrayList<IngredientsModel> = ArrayList()

    fun JSONData() {
        val service = RetrofitServiceFactory.createService()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
//                    val gson = GsonBuilder().setPrettyPrinting().create()
//                    var prettyJson = gson.toJson(response.body())
//                    Log.d("Pretty JSON response ", prettyJson)

                    val items = response.body()?.feed
//                    Log.d("Body Response ", items.toString())
//                    Log.d("Items count ", items?.count().toString())

                    if (items != null) {
                        for (i in 0 until items.count()) {
                            var foodName = items[i].display?.displayName ?: "N/A"
                            var foodImage = items[i].display?.images ?: "N/A"
                            var foodDescription = items[i].seo?.web?.metaTags?.description ?: "N/A"
                            var foodPreparationSteps = items[i].content?.prepartionsSteps ?: "N/A"
                            var foodVideos = items[i].content?.videos ?: "N/A"
                            var foodTotalTime = items[i].content?.details?.totalTime ?: "N/A"
                            var foodNumberOfServings = items[i].content?.details?.numberOfServings ?: "N/A"
                            var foodRatings = items[i].content?.details?.rating ?: "N/A"
                            var foodIngredient = items[i].content?.ingredientLines
                            Log.d("Food image ", foodImage.toString())

                            if (foodIngredient != null) {
                                for (j in 0 until foodIngredient.count()){
                                    var ingredient = foodIngredient[j].ingredient ?: "N/A"
//                                    Log.d("Number of ingredients ", ingredient?.count().toString())
//                                    Log.d("Food ingredients ", ingredient.toString())
//                                    break
                                    val dataIngredient =
                                        IngredientsModel(
                                            ingredient
                                        )
                                    ingredientsArray.add(dataIngredient)
                                }
                            }
//                            val dataRecipe =
//                                RecipeModel(
//                                    foodName,
//                                    foodImage as String,
//                                    foodDescription,
//                                    foodPreparationSteps as String,
//                                    foodVideos as String,
//                                    foodTotalTime,
//                                    foodNumberOfServings,
//                                    foodRatings,
//                                    ingredientsArray as String
//                                )
//                            recipeArray.add(dataRecipe)
//                            Log.d("Recipe ", recipeArray.toString())
                        }
                    }
                }
            }
        }

    }
}