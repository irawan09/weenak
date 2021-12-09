package irawan.electroshock.weenak.model

import com.google.gson.annotations.SerializedName

data class JSONModel(

    @SerializedName("feed")
    val feed: ArrayList<FeedData>?,

)

data class FeedData(

    @SerializedName("display")
    val display : Display?,

    @SerializedName("seo")
    val seo : SEOData?,

    @SerializedName("content")
    val content : Content?

)

data class Display(

    @SerializedName("displayName")
    val displayName : String?,

    @SerializedName("images")
    val images : List<String>?
)

data class SEOData(

    @SerializedName("web")
    val web : Web?
)

data class Content(
    @SerializedName("preparationSteps")
    val prepartionsSteps : List<String>?
)

data class Web(

    @SerializedName("meta-tags")
    val metaTags : Meta_tags?
)

data class Meta_tags(
    @SerializedName("description")
    val description : String?
)


