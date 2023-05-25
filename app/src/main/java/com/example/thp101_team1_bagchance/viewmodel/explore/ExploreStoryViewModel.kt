package com.example.thp101_team1_bagchance.viewmodel.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thp101_team1_bagchance.R

class ExploreStoryViewModel : ViewModel() {
    val story: MutableLiveData<ExploreStory> by lazy { MutableLiveData<ExploreStory>() }
}