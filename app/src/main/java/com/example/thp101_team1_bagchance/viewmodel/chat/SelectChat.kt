package com.example.thp101_team1_bagchance.viewmodel.chat

import com.example.thp101_team1_bagchance.R
import java.sql.Timestamp

data class SelectChat (val id: Int,
                       val inviteUid: Int,
                       val beInvitedUid: Int,
                       val status: String,
                       val createDate: Timestamp,
                       val lastUpdateDate: Timestamp,
                       val inviteUidname: String,
                       val beInvitedUidname: String,
                       val inviteUidpic: ByteArray? = null,
                       val beInvitedUidpic: ByteArray? = null,
                       val message : String? = null,
                       val picture : ByteArray? = null,
                       val recordingpath : String? = null,
                       val senduid : Int
) : java.io.Serializable
// fixme: val image :Int = R.drawable.ic_camera 是假資料串後端得刪除
// fixme: pic如果是null看要不要給預設值(沒頭貼給個醜醜的預設)
// fixme:  Serializable 視情況可能要刪除
