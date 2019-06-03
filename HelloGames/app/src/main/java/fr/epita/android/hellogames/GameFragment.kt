package fr.epita.android.hellogames


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_game.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // The base URL where the WebService is located
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        // Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: IGames = retrofit.create(IGames::class.java)

        var gamesCallback : Callback<List<DGame>> = object : Callback<List<DGame>> {

            override fun onFailure(call: Call<List<DGame>>, t: Throwable) {
                Log.w("Game", "Error by loading games.")
            }

            override fun onResponse(call: Call<List<DGame>>, response: Response<List<DGame>>) {
                if (response.code() == 200) {
                    var data: List<DGame>? = response.body()
                    if (data != null) {
                        data = data.shuffled()
                        Glide
                            .with(view)
                            .load(data[0].picture)
                            .into(imageView1)
                        Glide
                            .with(view)
                            .load(data[1].picture)
                            .into(imageView2)
                        Glide
                            .with(view)
                            .load(data[2].picture)
                            .into(imageView3)
                        Glide
                            .with(view)
                            .load(data[3].picture)
                            .into(imageView4)

                        imageView1.setOnClickListener {
                            (activity as MainActivity).go_to_details(data[0].id)
                        }

                        imageView2.setOnClickListener {
                            (activity as MainActivity).go_to_details(data[1].id)
                        }
                        imageView3.setOnClickListener {
                            (activity as MainActivity).go_to_details(data[2].id)
                        }
                        imageView4.setOnClickListener {
                            (activity as MainActivity).go_to_details(data[3].id)
                        }
                    }
                }
            }
        }

        service.getAllGame().enqueue(gamesCallback)
    }
}
