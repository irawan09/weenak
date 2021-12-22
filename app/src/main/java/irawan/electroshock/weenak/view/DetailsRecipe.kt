package irawan.electroshock.weenak.view

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import irawan.electroshock.weenak.model.RecipeModel

fun detailsRecipe(details : RecipeModel) {
    Log.d("Data clicked ", details.foodName)
//    DetailsDisplay(input = details.foodName)
}

@Composable
fun DetailsDisplay(input : String){
    Text(text = input)

}