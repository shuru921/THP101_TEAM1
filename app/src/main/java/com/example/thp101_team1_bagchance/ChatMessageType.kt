package com.example.thp101_team1_bagchance

import java.sql.Timestamp


sealed class ChatMessageType {
//    接收ChatMessage 並判斷
    abstract val viewType: Int

    data class Ltext(val text: String, override val viewType: Int = R.layout.ltext_item_view) : ChatMessageType()
    data class Rtext(val text: String, override val viewType: Int = R.layout.rtext_item_view) : ChatMessageType()
    data class Limage(val image: ByteArray, override val viewType: Int = R.layout.limage_item_view) : ChatMessageType()
    data class Rimage(val image: ByteArray, override val viewType: Int = R.layout.rimage_item_view ) : ChatMessageType()
    data class LRecording(val image: String, override val viewType: Int = R.layout.lrecording_item_view ) : ChatMessageType()
    data class RRecording(val image: String, override val viewType: Int = R.layout.rrecording_item_view ) : ChatMessageType()

}