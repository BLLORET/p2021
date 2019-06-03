package fr.epita.android.hellogames

import retrofit2.Call
import retrofit2.http.GET

interface IGames {
    @GET("game/list")
    fun getAllGame() : Call<List<DGame>>
}