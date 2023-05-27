package com.example.thp101_team1_bagchance

//data class Message(
//    val id: Int = 0,
//    val fromId: Int = 0,
//    val toId: Int = 0,
//    val text: String = "",
//    val image: String = "",
//    val record: String = ""
//)

sealed class Message {
    
    abstract val viewType: Int

    data class Ltext(val text: String, override val viewType: Int = R.layout.ltext_item_view) : Message()
    data class Rtext(val text: String, override val viewType: Int = R.layout.rtext_item_view) : Message()
    data class Limage(val image: String, override val viewType: Int = R.layout.limage_item_view) : Message()
    data class Rimage(val image: String, override val viewType: Int = R.layout.rimage_item_view ) : Message()
    data class LRecording(val image: String, override val viewType: Int = R.layout.lrecording_item_view ) : Message()
    data class RRecording(val image: String, override val viewType: Int = R.layout.rrecording_item_view ) : Message()

}