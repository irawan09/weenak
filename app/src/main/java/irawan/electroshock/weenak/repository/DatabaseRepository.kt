package irawan.electroshock.weenak.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import irawan.electroshock.weenak.database.RecipeDao
import irawan.electroshock.weenak.model.DatabaseModel

class DatabaseRepository(private val recipeDao : RecipeDao) {
    val allRecipe : LiveData<List<DatabaseModel>> = recipeDao.getAllRecipe()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(databaseModel: DatabaseModel){

    }
}