package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import kotlin.random.Random
import android.graphics.Color

class MainActivity : AppCompatActivity() {

    private var randomNumber = Random.nextInt(10000, 100000)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numPlays = findViewById<TextView>(R.id.tvPlays)
        numPlays.text = "$randomNumber plays"

        val prev = findViewById<ImageButton>(R.id.imgPrev)
        prev.setOnClickListener {prev: View ->
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        val play = findViewById<ImageButton>(R.id.imgPlay)
        play.setOnClickListener {play: View ->
            randomNumber++
            numPlays.text = "$randomNumber plays";
        }

        val next = findViewById<ImageButton>(R.id.imgNext)
        next.setOnClickListener {next: View ->
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
    }

    fun changeClicked(view: View) {
        val user = findViewById<Button>(R.id.btnUser)
        val username = findViewById<TextView>(R.id.tvUser)
        val userEdit = findViewById<TextView>(R.id.edit)
        if (user.text != "Apply") {
            user.text = "Apply"
            userEdit.visibility = View.VISIBLE
            username.visibility = View.GONE;
        } else {
            user.text = "Change User"
            userEdit.visibility = View.GONE
            username.visibility = View.VISIBLE
            username.text = userEdit.text
        }
    }
}
