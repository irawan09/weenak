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

class DataRepository(private val recipeDao : RecipeDao) {
    private val LOG = "Observer Data "
    private var recipeArray : ArrayList<RecipeModel> = ArrayList()
    private var ingredientArray : ArrayList<String> = ArrayList()
    private var fullIngredientsArray : ArrayList<FullIngredients> = ArrayList()
    private var fullRecipeResponseLiveData: MutableLiveData<FullRecipe>? = null
    private var databaseFullRecipeLivedata: MutableLiveData<DatabaseModel>? =null

    init {
        fullRecipeResponseLiveData = MutableLiveData<FullRecipe>()
        databaseFullRecipeLivedata = MutableLiveData<DatabaseModel>()
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
                            val foodName = recipeArray[l].foodName
                            val foodImage = recipeArray[l].foodImage
                            val foodDescription = recipeArray[l].foodDescription
                            val foodPreparation = recipeArray[l].foodPreparationSteps
                            val foodVideo = recipeArray[l].foodVideos
                            val foodTotalTime = recipeArray[l].foodTotalTime
                            val foodNumberOfServings = recipeArray[l].foodNumberOfServings
                            val foodRating = recipeArray[l].foodRatings
                            val foodIngredients = fullIngredientsArray[l].fullIngredients.toString()
//                            Log.d(LOG, foodIngredients)

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

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun  insert(databaseModel: DatabaseModel){
        recipeDao.inserData(databaseModel)
    }
}