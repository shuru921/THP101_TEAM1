package com.example.thp101_team1_bagchance

data class LoginUser(
    var id:Int? = null,
    var email:String? = null,
    var phone:String? = null,
    var password:String? = null,
    var nickname:String? = null,
    var birthday:String? = null,
    var gender:String? = null,
    var area:String? = null,
    var introduction:String? = null
    ): java.io.Serializable {
}
