package irawan.electroshock.weenak.api

import irawan.electroshock.weenak.model.JSONModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface DataService {

    @Headers(
        "x-rapidapi-host: yummly2.p.rapidapi.com",
        "x-rapidapi-key: 12e8b63ed1msh05c2a58a2b5f889p18479djsne558e99a6272"
    )
    @GET("feeds/list?limit=20&start=0&tag=list.recipe.popular")
    suspend fun getData() : Response<JSONModel>
}