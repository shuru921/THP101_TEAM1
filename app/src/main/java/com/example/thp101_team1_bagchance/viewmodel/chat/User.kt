package com.example.thp101_team1_bagchance.viewmodel.chat

import java.sql.Date
import java.sql.Timestamp

data class User(
    val id: Int,
    val mail: String,
    val phone: String,
    val password: String,
    val nickname: String,
    val gender: String,
    val birthday: Date,
    val exploreArea: String,
    val profilePic: ByteArray,
    val profileIntro: String,
    val userStatus: String,
    val createDate: Timestamp,
    val lastUpdateDate: Timestamp,
    val tokenGoogle: String?,
    val tokenFacebook: String?
): java.io.Serializable