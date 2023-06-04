package com.example.thp101_team1_bagchance.viewmodel.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostNewContentViewModel : ViewModel() {
    val locationName: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val textReverseGeocode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val text: MutableLiveData<String> by lazy { MutableLiveData<String>() }
}

