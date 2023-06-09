package com.example.thp101_team1_bagchance.viewmodel.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thp101_team1_bagchance.R
import com.google.gson.JsonObject
import tw.idv.william.androidwebserver.core.service.requestTask

class ExploreStoryCommentViewModel : ViewModel() {
    val comment: MutableLiveData<ExploreComment> by lazy { MutableLiveData<ExploreComment>() }

}