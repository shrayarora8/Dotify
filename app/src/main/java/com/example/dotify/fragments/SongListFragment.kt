package com.example.dotify.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.example.dotify.OnSongClickListener
import com.example.dotify.R
import com.example.dotify.SongListAdapter
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListFragment: Fragment() {
    private var onSongClickListener: OnSongClickListener? = null
    private lateinit var songList: List<Song>
    private lateinit var songAdapter: SongListAdapter

    companion object {
        const val SONG_LIST = "song_list"
        val TAG: String = SongListFragment::class.java.simpleName
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                songList = getParcelableArrayList<Song>(SONG_LIST) as List<Song>
            }
        } else {
            arguments?.let { args ->
                songList = args.getParcelableArrayList<Song>(SONG_LIST) as List<Song>
            }
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songAdapter = SongListAdapter(songList)
        rvList.adapter = songAdapter
        songAdapter.songClickListener = { song ->
            onSongClickListener?.onSongClicked(song)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(SONG_LIST, ArrayList(songList))
    }

    fun shuffleList(newList: List<Song>) {
        songAdapter.change(newList)
        songList = newList
    }
}