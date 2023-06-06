package com.example.thp101_team1_bagchance.controller.chat

import android.graphics.BitmapFactory
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMessageType
import com.example.thp101_team1_bagchance.databinding.RimageItemViewBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMainViewModel
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMessage
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel
import com.google.gson.JsonObject
import org.json.JSONObject
import tw.idv.william.androidwebserver.core.service.requestTask

class MeImageViewHolder(private val binding: RimageItemViewBinding) : MessageViewHolder<ChatMessageType.Rimage>(binding.root) {

    override fun onBind(item: ChatMessageType.Rimage) {
        val byteArray = item.image
        val options = BitmapFactory.Options()
        options.inSampleSize = 3 // 将inSampleSize设置为3，表示将图像尺寸缩小为原来的1/3
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)
        binding.ivPictureChat.setImageBitmap(bitmap)


        // FIXME: 要寫輸出照片
//        val byteArray = item.image
//        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//        binding.ivPictureChat.setImageBitmap(bitmap)
//        requestTask<JsonObject>("http://10.0.2.2:8080/test//web/ChatMessageController/", method = "POST", reqBody = item)
    }
}