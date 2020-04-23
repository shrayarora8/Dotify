package com.example.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {
    var curSongClicked: Song? = null
    companion object {
        const val SONG_KEY = "SONG_KEY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        title = "All Songs"
        var everySong: List<Song> = SongDataProvider.getAllSongs()
        val adapter = SongListAdapter(everySong, this)
        adapter.songClickListener = {song ->
            tvTitle.text = getString(R.string.dispSong, song.title, song.artist)
            clPlayer.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(SONG_KEY, song)
                startActivity(intent)
            }
        }

        rvList.adapter = adapter
    }
}
