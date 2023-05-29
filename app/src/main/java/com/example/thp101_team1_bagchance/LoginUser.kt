package com.example.thp101_team1_bagchance

data class LoginUser(
    var email:String?,
    var phone:String?,
    var password:String?,
    var nickname:String?,
    var birthday:String?,
    var gender:String?,
    var area:String?,
    var introduction:String?
    ): java.io.Serializable {
}
