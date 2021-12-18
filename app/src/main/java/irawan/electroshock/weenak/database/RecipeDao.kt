package irawan.electroshock.weenak.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import irawan.electroshock.weenak.model.DatabaseModel

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe_table")
    fun getAllRecipe(): LiveData<List<DatabaseModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun inserData(databaseModel: DatabaseModel){
        Log.d("Database ", databaseModel.dcFoodName)
    }

    @Query("DELETE FROM recipe_table")
    fun deleteAll(){
        Log.d("Database status ", "Deleted")
    }

}