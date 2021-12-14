package irawan.electroshock.weenak.model

import com.google.gson.annotations.SerializedName

data class JSONModel(

    @SerializedName("feed")
    val feed: ArrayList<FeedData>? = null,
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
    val images : ArrayList<String>? = null
)

data class SEOData(

    @SerializedName("web")
    val web : Web? = null
)

data class Content(

    @SerializedName("preparationSteps")
    val prepartionsSteps : ArrayList<String>? = null,

    @SerializedName("videos")
    val videos : Videos? = null,

    @SerializedName("details")
    val details : Details? = null,

    @SerializedName("ingredientLines")
    val ingredientLines : ArrayList<Ingredient>? = null
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