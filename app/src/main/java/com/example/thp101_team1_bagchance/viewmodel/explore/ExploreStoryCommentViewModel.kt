package com.example.thp101_team1_bagchance.viewmodel.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExploreStoryCommentViewModel : ViewModel() {
    val comment: MutableLiveData<ExploreComment> by lazy { MutableLiveData<ExploreComment>() }

}