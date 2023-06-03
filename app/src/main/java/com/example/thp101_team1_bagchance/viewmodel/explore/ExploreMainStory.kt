package com.example.thp101_team1_bagchance.viewmodel.explore

import java.io.Serializable
import java.sql.Timestamp

//class ExploreMainStory  (var exlporeimageId: Int, var explorename: String, var exploredate: String, var storytext: String,var storypic: Int) : Serializable
class ExploreMainStory (var id: Integer,
                        var nickname: String,
                        var profile_pic: ByteArray,
                        var create_date: Timestamp,
                        var content: String,
                        var pic: String,
                        var comment: String)
