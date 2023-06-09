package com.example.thp101_team1_bagchance.viewmodel.explore

import java.io.Serializable

class ExploreComment (val comment : String,
                      val story_id : Int? = null,
                      var profile_pic: ByteArray,
                      val uid : Int? = null,
                      var nickname: String,
                      ) : Serializable


