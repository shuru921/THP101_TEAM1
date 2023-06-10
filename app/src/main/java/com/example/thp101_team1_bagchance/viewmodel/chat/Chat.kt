package com.example.thp101_team1_bagchance.viewmodel.chat

import java.io.Serializable
import java.sql.Timestamp

//data class Chat(val id : Int? = null, val inviteUid : Int, val beInvitedUid : Int, val status : String? = null,
//                val createDate : Timestamp? = null, val lastUpdateDate : Timestamp? = null)
//  Chat只用來INSERT和UPDATE設定為NULL JSON轉格式時會略過此行(刪掉) 送到SQL後會給DEFULT
//  STATUS在UPDATE時要給值 有(I,A,D)
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!