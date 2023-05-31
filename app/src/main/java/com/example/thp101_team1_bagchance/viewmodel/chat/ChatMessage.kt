package com.example.thp101_team1_bagchance.viewmodel.chat

import java.sql.Timestamp

// 收發聊天室訊息
// fixme:  messageStatus 視情況修正
data class ChatMessage (val id: Int,
                        val chatId: Int,
                        val sendUid: Int,
                        val message: String? = null,
                        val picture: ByteArray? = null,
                        val recordingPath: String? = null,
                        val messageStatus: String,
                        val createDate: Timestamp? = null) {
// 將後端得到的資料 轉型為ChatMessageType
    fun toChatMessageType(selfId: Int): ChatMessageType {
//    將後端拿到的資料的類別 轉為ChatMessageType 才可以判斷資料是 文字 圖片 錄音
        return if (sendUid == selfId) {
            when {
                message != null -> ChatMessageType.Rtext(message)
                picture != null -> ChatMessageType.Rimage(picture)
                recordingPath != null -> ChatMessageType.RRecording(recordingPath)
                else -> throw Exception()
            }
        } else {
            when {
                message != null -> ChatMessageType.Ltext(message)
                picture != null -> ChatMessageType.Limage(picture)
                recordingPath != null -> ChatMessageType.LRecording(recordingPath)
                else -> throw Exception()
            }
        }
    }
}
