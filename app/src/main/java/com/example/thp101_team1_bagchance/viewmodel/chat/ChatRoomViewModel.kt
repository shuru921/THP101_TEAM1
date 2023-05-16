package com.example.thp101_team1_bagchance.viewmodel.chat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.controller.chat.ChatMainAdapter

class ChatRoomViewModel : ViewModel() {
//    Adapter綁定的資料 把選定的聊天室資料傳入用
    val friend : MutableLiveData<Any> by lazy { MutableLiveData<Any>() }
//    聊天輸入框
    val text : MutableLiveData<String> by lazy { MutableLiveData<String>() }
}