package irawan.electroshock.weenak.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import irawan.electroshock.weenak.model.*
import irawan.electroshock.weenak.repository.RemoteRepository

class DataViewModel : ViewModel() {
    private var remoteRepository : RemoteRepository? = null
    private var fullRecipeResponseLiveData : MutableLiveData<FullRecipe>? = null

    init {
        remoteRepository = RemoteRepository()
        fullRecipeResponseLiveData = remoteRepository!!.getFullRecipeResponseLiveData()
    }

    fun getFullRecipeResponseLiveData() : LiveData<FullRecipe>{
        return fullRecipeResponseLiveData!!
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("DataViewModel", "DataViewModel Destroyed")
    }
}