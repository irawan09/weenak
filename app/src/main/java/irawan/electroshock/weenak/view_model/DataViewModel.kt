package irawan.electroshock.weenak.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.RoomDatabase
import irawan.electroshock.weenak.database.RecipeDao
import irawan.electroshock.weenak.database.RecipeRoomDatabase
import irawan.electroshock.weenak.model.*
import irawan.electroshock.weenak.repository.DatabaseRepository
import irawan.electroshock.weenak.repository.RemoteRepository

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private var remoteRepository : RemoteRepository? = null
//    private val databaseRepository : DatabaseRepository
//    val allRecipe : LiveData<List<DatabaseModel>>
    private var fullRecipeResponseLiveData : MutableLiveData<FullRecipe>? = null
    private var databaseResponseLiveData : MutableLiveData<DatabaseModel>? = null

    init {
        remoteRepository = RemoteRepository()
        fullRecipeResponseLiveData = remoteRepository!!.getFullRecipeResponseLiveData()
        databaseResponseLiveData = remoteRepository!!.getDatabaseFullRecipeLivedata()

//        val wordDao = RecipeRoomDatabase.getDatabase(application, viewModelScope).recipeDao()
//        databaseRepository = DatabaseRepository(wordDao)
//        allRecipe = databaseRepository.allRecipe
    }

    fun getFullRecipeResponseLiveData() : LiveData<FullRecipe>{
        return fullRecipeResponseLiveData!!
    }

    fun getDatabaseRecipeResponseLiveData() : LiveData<DatabaseModel>{
        return databaseResponseLiveData!!
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("DataViewModel", "DataViewModel Destroyed")
    }
}