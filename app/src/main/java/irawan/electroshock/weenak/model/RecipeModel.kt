package irawan.electroshock.weenak.model

data class RecipeModel(
    var foodName: String = "N/A",
    var foodImage: String = "N/A",
    var foodDescription: String = "N/A",
    var foodPreparationSteps: String = "N/A",
    var foodVideos: String = "N/A",
    var foodTotalTime: String = "N/A",
    var foodNumberOfServings: String = "N/A",
    var foodRatings: String = "N/A",
)

data class FullIngredients(
    var fullIngredients: ArrayList<String>? = null
)

data class FullRecipe(
    var utilityRecipe: ArrayList<RecipeModel>? = null,
    var ingredients: ArrayList<FullIngredients>? = null
)
