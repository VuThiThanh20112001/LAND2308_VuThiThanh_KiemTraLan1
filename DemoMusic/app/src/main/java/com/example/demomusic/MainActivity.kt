package com.example.demomusic

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import phucdv.android.musichelper.MediaHelper
import phucdv.android.musichelper.Song
import java.text.FieldPosition


class MainActivity : AppCompatActivity(), ItemClickListener {
    val song : List<Song> = emptyList()
    val adapter: MusicListAdapter = MusicListAdapter(this)

    val mediaPlayer : MediaPlayer = MediaPlayer().apply {
        setAudioStreamType(AudioManager.STREAM_MUSIC)
        setOnPreparedListener{player ->
        player.start()}
    }
    fun playSong(id: Long) {
        mediaPlayer.reset()
        val trackUri =
            ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
        try {
            mediaPlayer.setDataSource(this, trackUri)
        } catch (e: Exception) {
            Log.e("MUSIC SERVICE", "Error starting data source", e)
        }
        mediaPlayer.prepareAsync()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doRetrieveAllSong()
        val recyclerView = findViewById<RecyclerView>(R.id.rcv)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
    fun doRetrieveAllSong() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 999)

        }else {
            MediaHelper.retrieveAllSong(this) {listSong ->
                adapter.setSongs(listSong)
                adapter.notifyDataSetChanged()
                //playSong(listSong[0].id)

            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 999) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doRetrieveAllSong()
            }
        }
    }

    override fun onClick(position: Int) {
        val song = adapter.getItem(position)
        playSong(song.id)
    }

}




