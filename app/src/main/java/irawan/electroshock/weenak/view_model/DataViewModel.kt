package irawan.electroshock.weenak.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import irawan.electroshock.weenak.model.FullRecipe

class DataViewModel : ViewModel() {

    private val _data = MutableLiveData<Collection<FullRecipe>>()
    val data : LiveData<Collection<FullRecipe>> = _data

    var newList = arrayListOf<FullRecipe>()

    init {
        _data.postValue(emptyList())
    }

    fun add(fullRecipe : FullRecipe){
        newList.add(fullRecipe)
        _data.value = newList
        Log.d("Data View Model", _data.value.toString())
    }

    fun getData(): ArrayList<FullRecipe> {
        return newList
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("DataViewModel", "DataViewModel Destroyed")
    }
}