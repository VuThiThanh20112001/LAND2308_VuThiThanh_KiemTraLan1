package com.example.demomusic

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import phucdv.android.musichelper.Song

class MusicListAdapter(
    private val onItemClickListener: ItemClickListener
    ) : RecyclerView.Adapter<MusicListAdapter.MusicViewHolder>() {
    private var songList: List<Song> = emptyList()
    private var previousClickedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        Log.d("VuThanh", "onCreateViewHolder: ")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_item, parent, false)
        val holder = MusicViewHolder(view)
        return holder
    }
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        Log.d("VuThanh", "onBindViewHolder: $position")
        holder.txt_name.text = songList[position].title

    }

    override fun getItemCount(): Int {
        return songList.size
    }

    inner class MusicViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val txt_name = itemView.findViewById<TextView>(R.id.txt_name)
        init {
            itemView.setOnClickListener {
                var position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    if (previousClickedPosition != RecyclerView.NO_POSITION) {
                        notifyItemChanged(previousClickedPosition)
                    }
                    itemView.setBackgroundColor(Color.LTGRAY)
                    previousClickedPosition = position
                    onItemClickListener.onClick(position)

                }else {
                    itemView.setBackgroundColor(Color.WHITE)

                }


            }
        }
    }
    fun getItem(position: Int): Song {
        return songList[position]
    }
    fun setSongs(newSongs: List<Song>) {
        songList = newSongs
        notifyDataSetChanged()
    }

}