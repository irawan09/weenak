package irawan.electroshock.weenak.model

import com.google.gson.annotations.SerializedName
import irawan.electroshock.weenak.utils.SetterGetter
import irawan.electroshock.weenak.utils.SgFoodIngredient
import org.chromium.base.Log

data class JSONModel(

    @SerializedName("feed")
    val feed: List<FeedData>? = null,
)

data class FeedData(

    @SerializedName("display")
    val display : Display?,

    @SerializedName("seo")
    val seo : SEOData? = null,

    @SerializedName("content")
    val content : Content? = null
)

data class Display(

    @SerializedName("displayName")
    val displayName : String?,

    @SerializedName("images")
    val images : List<String>?
)

data class SEOData(

    @SerializedName("web")
    val web : Web? = null
)

data class Content(

    @SerializedName("preparationSteps")
    val prepartionsSteps : List<String>?,

    @SerializedName("videos")
    val videos : Videos? = null,

    @SerializedName("details")
    val details : Details? = null,

    @SerializedName("ingredientLines")
    val ingredientLines : List<Ingredient>? = null
)

data class Videos(

    @SerializedName("originalVideoUrl")
    val originalVideoUrl : String?
)

data class Details(

    @SerializedName("totalTime")
    val totalTime : String?,

    @SerializedName("numberOfServings")
    val numberOfServings : String?,

    @SerializedName("rating")
    val rating : String?
)

data class Ingredient(

    @SerializedName("wholeLine")
    val ingredient: String?
)

data class Web(

    @SerializedName("meta-tags")
    val metaTags : Meta_tags? = null
)

data class Meta_tags(
    @SerializedName("description")
    val description : String?
)

data class Summary (
    var dcFoodName : String = "N/A",
    var dcFoodImage : String = "N/A",
    var dcFoodDescription : String = "N/A",
    var dcPreparationSteps : String = "N/A",
    var dcFoodVideos : String = "N/A",
    var dcFoodTotalTime : String = "N/A",
    var dcFoodNumberOfServings : String = "N/A",
    var dcFoodRatings : String ="N/A",
    var dcFootIngredients: List<Ingredient>? = null
)