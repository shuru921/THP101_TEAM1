package com.example.thp101_team1_bagchance.controller.chat

import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMessageType
import com.example.thp101_team1_bagchance.databinding.RtextItemViewBinding

class MeTextViewHolder(private val binding: RtextItemViewBinding) : MessageViewHolder<ChatMessageType.Rtext>(binding.root) {

    override fun onBind(item: ChatMessageType.Rtext) {
        binding.tvMeImessageChat.text = item.text
    }
}