package irawan.electroshock.weenak

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import irawan.electroshock.weenak.model.RemoteRepository
import irawan.electroshock.weenak.model.FullRecipe
import irawan.electroshock.weenak.ui.theme.WeenakTheme
import irawan.electroshock.weenak.view_model.DataViewModel
import java.lang.reflect.Modifier

class MainActivity : ComponentActivity() {

    private lateinit var viewModel : DataViewModel
    private var recipeList = arrayListOf<FullRecipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContext(this)
        var data = RemoteRepository().getRecipeResponseLiveData()
        viewModel = ViewModelProvider(this).get((DataViewModel::class.java))
        viewModel.getRecipeResponseLiveData().observe(this, Observer {
            setContent {
                WeenakTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        recipeList.add(it)
                        Log.d("Full Recipe data", recipeList.toString())
                        DataCard(it)
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
fun DataCard(fullRecipe: FullRecipe) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
                .wrapContentSize(Alignment.TopStart)){
                    for (i in 0 until fullRecipe.utilityRecipe!!.size) {
                        var foodName = fullRecipe.utilityRecipe?.get(i)?.foodName.toString()
                        var foodDescription = fullRecipe.utilityRecipe?.get(i)?.foodDescription.toString()
                        Row(verticalAlignment = Alignment.CenterVertically){
//                            Image()
                            Column() {
                                Text(text = foodName, fontWeight = FontWeight.Bold)
//                                Text(text = foodDescription)
                            }
                        }
                        
            }


        }
 }