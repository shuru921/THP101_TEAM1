package com.example.thp101_team1_bagchance.viewmodel.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.reflect.TypeToken
import tw.idv.william.androidwebserver.core.service.requestTask

import java.util.*

class ExploreMainStoryViewModel : ViewModel() {
    val mainstory: MutableLiveData<ExploreMainStory> by lazy { MutableLiveData<ExploreMainStory>() }


}

