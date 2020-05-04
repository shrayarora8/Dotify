package com.example.dotify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.example.dotify.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class NowPlayingFragment: Fragment() {

    private var song: Song? = null

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val ARG_SONG = "arg_song"
        const val COUNT_PLAY = "count_play"
        var randomNumber: Int? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            this.song = args.getParcelable(ARG_SONG)
        }

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                if (containsKey(COUNT_PLAY)) {
                    randomNumber = getInt(COUNT_PLAY)
                    val text = "$randomNumber plays"
                    if (tvPlays != null) {
                        tvPlays.text = text
                    }
                }
            }
        } else {
            randomNumber = Random.nextInt(10000, 100000)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        randomNumber?.let {
            outState.putInt(COUNT_PLAY, it)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateSong(song)
        val noPlays = "$randomNumber plays"
        tvPlays.text = noPlays

        imgNext.setOnClickListener {
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        imgPlay.setOnClickListener {
            randomNumber?.let {rand ->
                randomNumber = rand + 1
            }
            val text = "$randomNumber plays"
            tvPlays.text = text
        }

        imgPrev.setOnClickListener {
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateSong(song: Song?) {
        if(song != null) {
            tVName.text = song.title
            img.setImageResource(song.largeImageID)
            tVArtist.text = song.artist
        }
    }
}