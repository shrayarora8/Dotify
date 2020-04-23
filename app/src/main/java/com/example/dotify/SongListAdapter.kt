package com.example.dotify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(songList: List<Song>, context: Context): RecyclerView.Adapter<SongListAdapter.SongView>() {
    private var songList: List<Song> = songList.toList()
    var songClickListener: ((song: Song) -> Unit?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_list, parent, false)
        return SongView(view)
    }

    override fun getItemCount() = songList.size

    override fun onBindViewHolder(holder: SongView, position: Int) {
        val song = songList[position]
        holder.bind(song)
    }

    inner class SongView(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvNameofSong by lazy { itemView.findViewById<TextView>(R.id.tvNameOfSong) }
        private val tvArtistName by lazy { itemView.findViewById<TextView>(R.id.tvArtistName) }
        private val ivSongArt by lazy { itemView.findViewById<ImageView>(R.id.ivSongArt) }

        fun bind(song: Song) {
            tvNameofSong.text = song.title
            tvArtistName.text = song.artist
            ivSongArt.contentDescription = song.title + "-" + song.artist + "cover image"
            ivSongArt.setImageResource(song.smallImageID)

            itemView.setOnClickListener {
                songClickListener?.invoke(song)
            }
        }
    }
}