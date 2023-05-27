package com.example.thp101_team1_bagchance.controller.chat

import android.view.View
import com.example.thp101_team1_bagchance.Message
import com.example.thp101_team1_bagchance.databinding.RoomItemViewBinding
import com.example.thp101_team1_bagchance.databinding.RtextItemViewBinding

class MeTextViewHolder(private val binding: RtextItemViewBinding) : MessageViewHolder<Message.Rtext>(binding.root) {

    override fun onBind(item: Message.Rtext) {
        binding.tvMeImessageChat.text = item.text
    }
}