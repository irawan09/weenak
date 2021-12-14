package irawan.electroshock.weenak.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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

@Entity(tableName = "recipe_table")
data class Summary (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "name")
    @NonNull
    var dcFoodName : String = "N/A",
    @ColumnInfo(name = "image")
    @NonNull
    var dcFoodImage : String = "N/A",
    @ColumnInfo(name = "description")
    @NonNull
    var dcFoodDescription : String = "N/A",
    @ColumnInfo(name = "prep_steps")
    @NonNull
    var dcPreparationSteps : String = "N/A",
    @ColumnInfo(name = "video")
    var dcFoodVideos : String = "N/A",
    @ColumnInfo(name = "total_time")
    var dcFoodTotalTime : String = "N/A",
    @ColumnInfo(name = "number_of_servings")
    var dcFoodNumberOfServings : String = "N/A",
    @ColumnInfo(name = "rating")
    var dcFoodRatings : String ="N/A",
    @ColumnInfo(name = "ingredients")
    @NonNull
    var dcFootIngredients: List<String>? = null
)