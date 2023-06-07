package com.example.thp101_team1_bagchance.viewmodel.explore


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class ExploreMainStory (var id: Integer,
                        var nickname: String,
                        var profile_pic: ByteArray,
                        var create_date: String,
                        var content: String,
                        var pic: List<ByteArray>,
                        var explore_area: String,
                        var gender: String,
                        var birthday:String,
                        var comment: String
                        )
{
    val age: Int
        get() {
            val birthDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("M月 d, yyyy"))
            val currentDate = LocalDate.now()
            return Period.between(birthDate, currentDate).years
        }
}









