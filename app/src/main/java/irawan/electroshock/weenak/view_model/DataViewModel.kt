package irawan.electroshock.weenak.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import irawan.electroshock.weenak.database.RecipeDao
import irawan.electroshock.weenak.model.*
import irawan.electroshock.weenak.repository.RemoteRepository

class DataViewModel : ViewModel() {
    private var remoteRepository : RemoteRepository? = null
    private var recipeDao : RecipeDao? = null
    private var fullRecipeResponseLiveData : MutableLiveData<FullRecipe>? = null
    private var databaseResponseLiveData : MutableLiveData<DatabaseModel>? = null

    init {
        remoteRepository = RemoteRepository()
        fullRecipeResponseLiveData = remoteRepository!!.getFullRecipeResponseLiveData()
        databaseResponseLiveData = remoteRepository!!.getDatabaseFullRecipeLivedata()
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