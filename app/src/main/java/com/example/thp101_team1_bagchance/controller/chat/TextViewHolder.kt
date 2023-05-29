package com.example.thp101_team1_bagchance.controller.chat

import android.view.View
import com.example.thp101_team1_bagchance.Message
import com.example.thp101_team1_bagchance.databinding.LtextItemViewBinding
import com.example.thp101_team1_bagchance.databinding.RoomItemViewBinding

class TextViewHolder(private val binding: LtextItemViewBinding) : MessageViewHolder<Message.Ltext>(binding.root) {

    override fun onBind(item: Message.Ltext) {
        binding.tvImessageChat.text = item.text
    }
}