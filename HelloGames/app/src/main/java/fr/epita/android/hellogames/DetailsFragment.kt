package fr.epita.android.hellogames


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Log.println
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_game.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
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
        val service: IDetails = retrofit.create(IDetails::class.java)

        var detailsCallback : Callback<DDetails> = object : Callback<DDetails> {
            override fun onFailure(call: Call<DDetails>, t: Throwable) {
                Log.w("Details", "Fail by load details")
            }

            override fun onResponse(call: Call<DDetails>, response: Response<DDetails>) {
                if (response.code() == 200) {
                    var data : DDetails? = response.body()
                    if (data != null) {
                        Glide
                            .with(view)
                            .load(data.picture)
                            .into(image_details)
                        txt_name.text = data.name
                        txt_type.text = data.type
                        txt_playeurs.text = data.players.toString()
                        txt_year.text = data.year.toString()
                        txt_description.text = data.description_en

                        button_more.setOnClickListener {
                            val url = data.url
                            // Define an implicit intent
                            val implicitIntent = Intent(Intent.ACTION_VIEW)
                            // Add the required data in the intent (here the URL we want to open)
                            implicitIntent.data = Uri.parse(url)
                            // Launch the intent
                            startActivity(implicitIntent)
                        }
                    }
                }
            }
        }

        // arguments is never null but prefer display 1 that crash the app.
        var game_id : Int = 1
        if (arguments != null)
            game_id = (arguments as Bundle).getInt("Id", 1)
        service.getDetail(game_id).enqueue(detailsCallback)
    }
}
