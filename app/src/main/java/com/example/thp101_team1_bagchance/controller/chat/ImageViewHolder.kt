package com.example.thp101_team1_bagchance.controller.chat

import android.graphics.BitmapFactory
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMessageType
import com.example.thp101_team1_bagchance.databinding.LimageItemViewBinding

class ImageViewHolder(private val binding: LimageItemViewBinding) : MessageViewHolder<ChatMessageType.Limage>(binding.root) {

    override fun onBind(item: ChatMessageType.Limage) {
        val byteArray = item.image
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        binding.ivPictureChat.setImageBitmap(bitmap)
    }
}