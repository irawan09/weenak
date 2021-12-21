package irawan.electroshock.weenak.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import irawan.electroshock.weenak.model.DatabaseModel
import irawan.electroshock.weenak.repository.RemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [DatabaseModel::class], version = 1, exportSchema = false)
abstract class RecipeRoomDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

}