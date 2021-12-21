package irawan.electroshock.weenak.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class DatabaseModel (
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
    var dcFootIngredients: String? = "N/A"
)
