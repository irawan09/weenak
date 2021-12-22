package irawan.electroshock.weenak.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import androidx.room.RoomDatabase
import irawan.electroshock.weenak.MainActivity
import irawan.electroshock.weenak.database.RecipeDao
import irawan.electroshock.weenak.database.RecipeRoomDatabase
import irawan.electroshock.weenak.model.*
import irawan.electroshock.weenak.repository.DatabaseRepository
import irawan.electroshock.weenak.repository.RemoteRepository

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private var db : RecipeRoomDatabase? = null
    private var remoteRepository : RemoteRepository? = null
    private var fullRecipeResponseLiveData : MutableLiveData<FullRecipe>? = null
    private var databaseResponseLiveData : MutableLiveData<DatabaseModel>? = null

    init {
        remoteRepository = RemoteRepository()
        remoteRepository.let {
            fullRecipeResponseLiveData = it?.getFullRecipeResponseLiveData()
            databaseResponseLiveData = it?.getDatabaseFullRecipeLivedata()
        }
        db = Room.databaseBuilder(MainActivity.getContext(), RecipeRoomDatabase::class.java,"recipe-db").build()

        databaseResponseLiveData?.observe(MainActivity.getLifeCycleOwner(), Observer {
            Log.d("Database", it.toString())
        })
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