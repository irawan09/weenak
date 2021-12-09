package irawan.electroshock.weenak.model

import android.util.Log
import com.google.gson.GsonBuilder
import irawan.electroshock.weenak.api.RetrofitServiceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JSONParser {

    fun JSONData() {
        val service = RetrofitServiceFactory.createService()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    var prettyJson = gson.toJson(response.body())
                    Log.d("Pretty JSON response ", prettyJson)

                    val items = response.body()?.feed
                    Log.d("Body Response ", items.toString())
                    Log.d("Items count ", items?.count().toString())

                    if (items != null) {
                        for (i in 0 until items.count()) {
                            var foodName = items[i].display?.displayName
                            var foodImage = items[i].display?.images
                            var foodDescription = items[i].seo?.web?.metaTags?.description
                            var foodPreparationSteps = items[i].content?.prepartionsSteps
                            var foodVideos = items[i].content?.videos
                            var foodTotalTime = items[i].content?.details?.totalTime
                            var foodNumberOfServings = items[i].content?.details?.numberOfServings
                            var foodRatings = items[i].content?.details?.rating
                            var foodIngredient = items[i].content?.ingredientLines
//                            Log.d("Food name ", foodName.toString())

                            if (foodIngredient != null) {
                                for (j in 0 until foodIngredient.count()){
                                    val ingredients = mutableListOf<String>()

                                    var ingredient = foodIngredient[j].ingredient
                                    ingredients.add(ingredient.toString())
                                    Log.d("Food ingredients ", ingredients.toString())

                                }
                            }
                            break
                        }
                    }
                }
            }
        }

    }
}