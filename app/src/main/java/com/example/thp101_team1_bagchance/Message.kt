package com.example.thp101_team1_bagchance

data class Message(
    val id: Int = 0,
    val fromId: Int = 0,
    val toId: Int = 0,
    val text: String = "",
    val image: String = "",
    val record: String = ""
)
