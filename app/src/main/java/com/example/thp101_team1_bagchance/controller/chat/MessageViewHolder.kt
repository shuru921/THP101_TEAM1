package com.example.thp101_team1_bagchance.controller.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.thp101_team1_bagchance.Message

abstract class MessageViewHolder<T : Message>(itemView: View) : ViewHolder(itemView) {
    abstract fun onBind(item: T)
}