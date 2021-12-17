package irawan.electroshock.weenak.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import irawan.electroshock.weenak.model.FullIngredients

class Converter {
    @TypeConverter
    fun fromIngredientsList(ingredients: List<FullIngredients?>?): String?{
        val type = object : TypeToken<List<FullIngredients?>?>(){}.type
        return Gson().toJson(ingredients, type)
    }

    @TypeConverter
    fun toIngredientsList(ingredients: String?): List<FullIngredients>? {
        val type = object : TypeToken<List<FullIngredients?>?>(){}.type
        return Gson().fromJson<List<FullIngredients>>(ingredients, type)
    }
}