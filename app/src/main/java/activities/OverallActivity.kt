package activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.OnSongClickListener
import com.example.dotify.R
import com.example.dotify.fragments.NowPlayingFragment
import com.example.dotify.fragments.SongListFragment
import kotlinx.android.synthetic.main.activity_overall.*
import java.util.*
import kotlin.collections.ArrayList

class OverallActivity : AppCompatActivity(), OnSongClickListener {

    companion object {
        private var songPlaying: Song? = null
        private lateinit var listOfSongs: ArrayList<Song>
        private const val SONG_PLAYING = "song_playing"
        private const val SONG_LIST = "song_list"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overall)
        val songListFragment = SongListFragment()
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                songPlaying = getParcelable(SONG_PLAYING)
                songPlaying?.let {
                    onSongClicked(it)
                }
            }
        } else {
            listOfSongs = SongDataProvider.getAllSongs() as ArrayList<Song>

            // showing the list of songs
            val bundleOfSongs = Bundle().apply {
                val list = ArrayList(listOfSongs)
                putParcelableArrayList(SONG_LIST, list)
            }
            songListFragment.arguments = bundleOfSongs
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }

        // add to the back stack
        if (supportFragmentManager.backStackEntryCount > 0) {
            clPlayer.visibility = View.INVISIBLE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        clPlayer.setOnClickListener {
            if(songPlaying != null) {
                clPlayer.visibility = View.INVISIBLE
                nowPlaying()
            }
        }
        btnShuffle.setOnClickListener {
            val songListFragment = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment
            songListFragment?.shuffleList(listOfSongs.toList().shuffled(Random()))
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val bStack = supportFragmentManager.backStackEntryCount > 0
            if(bStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                clPlayer.visibility = View.VISIBLE
            }
        }
    }

    private fun nowPlaying() {
        var nowPlayingFragment = getNowPlayingFragment()
        if(nowPlayingFragment == null) {
            nowPlayingFragment = NowPlayingFragment()
            val argumentBundle = Bundle().apply {
                putParcelable(NowPlayingFragment.ARG_SONG, songPlaying)
            }
            nowPlayingFragment.arguments = argumentBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                .addToBackStack(NowPlayingFragment.TAG)
                .commit()
        } else {
            nowPlayingFragment.updateSong(songPlaying)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SONG_PLAYING, songPlaying)
        outState.putParcelableArrayList(SONG_LIST, listOfSongs)
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(
        NowPlayingFragment.TAG) as? NowPlayingFragment

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        clPlayer.visibility = View.VISIBLE
        return super.onNavigateUp()
    }

    override fun onBackPressed() {
        clPlayer.visibility = View.VISIBLE
        super.onBackPressed()
    }

    override fun onSongClicked(song: Song) {
        tvTitle.text = getString(R.string.dispSong, song.title, song.artist)
        songPlaying = song
    }
}
