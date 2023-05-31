package com.example.thp101_team1_bagchance.controller.chat

import android.graphics.BitmapFactory
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMessageType
import com.example.thp101_team1_bagchance.databinding.RimageItemViewBinding

class MeImageViewHolder(private val binding: RimageItemViewBinding) : MessageViewHolder<ChatMessageType.Rimage>(binding.root) {

    override fun onBind(item: ChatMessageType.Rimage) {
        val byteArray = item.image
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        binding.ivPictureChat.setImageBitmap(bitmap)
    }
}