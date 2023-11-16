package com.example.demomusic

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

interface ItemClickListener {
    fun onClick(position: Int)
}