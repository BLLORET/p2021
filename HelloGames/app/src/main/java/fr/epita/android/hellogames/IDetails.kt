package fr.epita.android.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IDetails {
    @GET("game/details")
    fun getDetail(@Query("game_id") game_id : Int) : Call<DDetails>
}