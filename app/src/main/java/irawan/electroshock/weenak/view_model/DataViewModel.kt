package irawan.electroshock.weenak.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import irawan.electroshock.weenak.model.FullRecipe
import irawan.electroshock.weenak.model.RemoteRepository

public class DataViewModel : ViewModel() {
    private var remoteRepository : RemoteRepository? = null
    private var remoteResponseLiveData : LiveData<FullRecipe>? = null
    private var recipeList = arrayListOf<FullRecipe>()

    init {
        remoteRepository = RemoteRepository()
        remoteResponseLiveData = remoteRepository!!.getRecipeResponseLiveData()
    }

    fun getRecipeResponseLiveData() : LiveData<FullRecipe>{
//        remoteResponseLiveData = recipeList
        return remoteResponseLiveData!!
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("DataViewModel", "DataViewModel Destroyed")
    }
}