package irawan.electroshock.weenak.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JSONModel(

    @SerializedName("feed")
    val feed: List<FeedData>? = null,
)

data class FeedData(

    @SerializedName("display")
    val display : Display? = null,

    @SerializedName("seo")
    val seo : SEOData? = null,

    @SerializedName("content")
    val content : Content? = null
)

data class Display(

    @SerializedName("displayName")
    @Expose
    val displayName : String?,

    @SerializedName("images")
    @Expose
    val images : List<String>?
)

data class SEOData(

    @SerializedName("web")
    val web : Web? = null
)

data class Content(

    @SerializedName("preparationSteps")
    @Expose
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
    @Expose
    val originalVideoUrl : String?
)

data class Details(

    @SerializedName("totalTime")
    @Expose
    val totalTime : String?,

    @SerializedName("numberOfServings")
    @Expose
    val numberOfServings : String?,

    @SerializedName("rating")
    @Expose
    val rating : String?
)

data class Ingredient(

    @SerializedName("wholeLine")
    @Expose
    val ingredient: String?
)

data class Web(

    @SerializedName("meta-tags")
    val metaTags : Meta_tags? = null
)

data class Meta_tags(
    @SerializedName("description")
    @Expose
    val description : String?
)