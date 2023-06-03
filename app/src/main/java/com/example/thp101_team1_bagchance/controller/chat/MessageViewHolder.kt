package com.example.thp101_team1_bagchance.controller.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMessageType

abstract class MessageViewHolder<T : ChatMessageType>(itemView: View) : ViewHolder(itemView)  {
    abstract fun onBind(item: T)
}