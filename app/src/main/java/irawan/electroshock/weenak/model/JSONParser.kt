package irawan.electroshock.weenak.model

import android.util.Log
import com.google.gson.GsonBuilder
import irawan.electroshock.weenak.api.RetrofitServiceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JSONParser {

    fun JSONData() {
        val service = RetrofitServiceFactory.createService()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    var prettyJson = gson.toJson(response.body())
                    Log.d("Pretty JSON response ", prettyJson)

                    val items = response.body()?.feed
                    Log.d("Body Response ", items.toString())
                    Log.d("Items count ", items?.count().toString())

                    if (items != null) {
                        for (i in 0 until items.count()) {

                        }
                    }
                }
            }
        }

    }
}