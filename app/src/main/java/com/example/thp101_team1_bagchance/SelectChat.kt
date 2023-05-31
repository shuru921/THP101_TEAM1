package com.example.thp101_team1_bagchance

import java.sql.Timestamp

data class SelectChat (val id: Int,
                       val inviteUid: Int,
                       val beInvitedUid: Int,
                       val status: String,
                       val createDate: Timestamp,
                       val lastUpdateDate: Timestamp,
                       val inviteUidname: String,
                       val beInvitedUidname: String,
                       val inviteUidpic: ByteArray?,
                       val beInvitedUidpic: ByteArray?,
                       val message : String?,
                       val picture : ByteArray?,
                       val recordingpath : String?,
                       val image :Int = R.drawable.ic_camera ) : java.io.Serializable
// TODO: val image :Int = R.drawable.ic_camera 是假資料串後端得刪除
// TODO: pic如果是null看要不要給預設值
// TODO:  Serializable 視情況可能要刪除
