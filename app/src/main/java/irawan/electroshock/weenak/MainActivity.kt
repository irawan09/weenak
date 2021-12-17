package irawan.electroshock.weenak

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import irawan.electroshock.weenak.model.RecipeModel
import irawan.electroshock.weenak.ui.theme.WeenakTheme
import irawan.electroshock.weenak.view_model.DataViewModel

class MainActivity : ComponentActivity() {

    private lateinit var viewModel : DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContext(this)
        viewModel = ViewModelProvider(this).get((DataViewModel::class.java))
        viewModel.getFullRecipeResponseLiveData().observe(this, Observer {
            setContent {
                WeenakTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        var utility = it.utilityRecipe
                        var ingredients = it.ingredients
//                        ingredients?.toList()
//                        Log.d("Utility Data ", utility?.toList().toString())
//                        Log.d("Jumlah Utility data ", utility?.toList()?.size.toString())
                        DataCard(utility?.toList())
                    }
                }
            }
        })
    }

    companion object{
        private lateinit var context: Context

        fun setContext(con: Context){
            context = con
        }

        fun getContext() : Context{
            return context
        }

        fun puncRemoval(data : String, start : Int, end :Int) : String{
            var feedback = data.drop(start)
            feedback = feedback.dropLast(end)
            return feedback
        }

        fun hasNetwork(context: Context): Boolean? {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return  false
                return when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    else -> false
                }
            } else {
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                return nwInfo.isConnected
            }
        }
    }
}

@Composable
fun DataCard(recipeModel: List<RecipeModel>?) {
    LazyColumn{
        items(recipeModel!!.size){ index ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(4.dp)
                    .wrapContentSize(Alignment.TopStart)){
                        Card(elevation = 4.dp, modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 1.dp)
                            .clickable { }){

                            var foodName = recipeModel[index].foodName
                            var foodImage = recipeModel[index].foodImage
                            Log.d("data Image", foodImage)
                            var foodDescription = recipeModel[index].foodDescription
                            Column( modifier = Modifier.padding(all = 1.dp)){
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(), contentAlignment = Alignment.Center){
                                    val painter = rememberImagePainter(data = foodImage, builder = {})
                                    Image(
                                        painter = painter,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth())
                                }



                                Column(modifier = Modifier.padding(2.dp)) {
                                    Text(text = foodName, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    Text(text = foodDescription, fontSize = 10.sp)
                                }
                            }

                        }
            }
        }
    }

 }