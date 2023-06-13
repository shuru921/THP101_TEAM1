package com.example.thp101_team1_bagchance.viewmodel.explore


import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class ExploreMainStory (var id: Int,
                        var nickname: String,
                        var profile_pic: ByteArray,
                        var create_date: String,
                        var content: String,
                        var pic: ByteArray,
                        var explore_area: String,
                        var gender: String,
                        var birthday:String,
                        var comment: String
                        ): Serializable
{
    val age: Int
        get() {
            val birthDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("MMM d, yyyy"))
            val currentDate = LocalDate.now()
            return Period.between(birthDate, currentDate).years
        }
}









