package fr.epita.android.hellogames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {

    fun go_to_game() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_countainer, GameFragment())
            .commit()
    }

    fun go_to_details(id : Int) {
        var bundle = Bundle()
        bundle.putInt("Id", id)
        val detail = DetailsFragment()
        detail.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_countainer, detail)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        go_to_game()
    }
}
