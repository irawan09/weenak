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

@Database(entities = [DatabaseModel::class], version = 15, exportSchema = false)
abstract class RecipeRoomDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object{
        @Volatile
        private var INSTANCE: RecipeRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): RecipeRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: kotlin.synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeRoomDatabase::class.java,
                        "developer_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(DeveloperDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                    instance
                }
        }

        private class DeveloperDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        val jsonObj = RemoteRepository().getDatabaseFullRecipeLivedata()
                        val jsonObjNew = Gson().toJson(jsonObj)
                        val recipeType = object : TypeToken<DatabaseModel>() {}.type
                         val recipe: DatabaseModel = Gson().fromJson(jsonObjNew, recipeType)

//                        populateDatabase(
//                            database,
//                            recipe
//                        )
                    }
                }
            }
        }

        // Populate the database from company.json file - needs to be in a new coroutine
        // If needed, you can parse the fields you want from the database and use them
        // This example shows a list by the Developer object/class

        fun populateDatabase(database: RecipeRoomDatabase, recipe: DatabaseModel) {
            val recipeDao = database.recipeDao()

            // Empty database on first load
            recipeDao.deleteAll()

            recipeDao.insertData(DatabaseModel(recipe.toString()))

        }

        }
}