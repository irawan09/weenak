package irawan.electroshock.weenak.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import irawan.electroshock.weenak.database.RecipeDao
import irawan.electroshock.weenak.model.*
import irawan.electroshock.weenak.repository.DataRepository

class DataViewModel : ViewModel() {
    private var dataRepository : DataRepository? = null
    private var recipeDao : RecipeDao? = null
    private var fullRecipeResponseLiveData : MutableLiveData<FullRecipe>? = null
    private var databaseResponseLiveData : MutableLiveData<DatabaseModel>? = null

    init {
        dataRepository = DataRepository(/*recipeDao = recipeDao!!*/)
        fullRecipeResponseLiveData = dataRepository!!.getFullRecipeResponseLiveData()
        databaseResponseLiveData = dataRepository!!.getDatabaseFullRecipeLivedata()
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