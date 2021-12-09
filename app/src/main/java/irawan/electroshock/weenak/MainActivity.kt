package irawan.electroshock.weenak

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.button.MaterialButton
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import irawan.electroshock.weenak.api.RetrofitServiceFactory
import irawan.electroshock.weenak.ui.theme.WeenakTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : ComponentActivity() {

    private var prettyJson: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContext(this)
        JSONData()
        setContent {
            WeenakTheme {

                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(prettyJson)
                }
            }
        }
    }

    fun JSONData(){

        val service = RetrofitServiceFactory.createService()

        CoroutineScope(Dispatchers.IO).launch{

            val response = service.getData()
            Log.d("Sampe sini", response.toString())

            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    prettyJson = gson.toJson(response.body())
                    Log.d("Pretty JSON response ", prettyJson)
                }
            }
        }

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
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                return when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    //for other device how are able to connect with Ethernet
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    //for check internet over Bluetooth
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
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
fun Greeting(name: String) {
    Text(text = "test $name")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeenakTheme {
        Greeting("Android")
    }
}