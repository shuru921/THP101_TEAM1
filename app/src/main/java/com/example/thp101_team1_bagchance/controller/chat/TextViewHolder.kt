package com.example.thp101_team1_bagchance.controller.chat

import com.example.thp101_team1_bagchance.ChatMessage
import com.example.thp101_team1_bagchance.ChatMessageType
import com.example.thp101_team1_bagchance.databinding.LtextItemViewBinding

class TextViewHolder(private val binding: LtextItemViewBinding) : MessageViewHolder<ChatMessageType.Ltext>(binding.root) {

    override fun onBind(item: ChatMessageType.Ltext) {
        binding.tvImessageChat.text = item.text
    }
}