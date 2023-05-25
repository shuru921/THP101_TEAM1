package com.example.thp101_team1_bagchance.viewmodel.chat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.thp101_team1_bagchance.Friend
import com.example.thp101_team1_bagchance.Message
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.controller.chat.ChatMainAdapter

class ChatRoomViewModel : ViewModel() {
    //    Adapter綁定的資料 把選定的聊天室資料傳入用
    val friend: MutableLiveData<Friend> by lazy { MutableLiveData<Friend>() }

    //    聊天輸入框
    val text: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //    照片路徑
    val imageUrl: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //    錄音檔
    val audioUrl: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //        受監控訊息列表 變化後回傳
    val messagelist: MutableLiveData<List<Message>> by lazy { MutableLiveData<List<Message>>() }

    init {
        val messageList = mutableListOf<Message>()
        messageList.add(Message(1, fromId = 1, text = "aaaaa"))
        messageList.add(Message(2, toId = 1, text = "bbbbb"))
        messageList.add(Message(3, toId = 1, text = "bbbbb"))
        messageList.add(Message(4, fromId = 1, text = "aaaaa"))
        messageList.add(Message(5, toId = 1, image = "cccccc"))
        messageList.add(Message(6, fromId = 1, record = "dddddd"))
        messageList.add(Message(7, fromId = 1, text = "aaaaa"))
        messageList.add(Message(8, toId = 1, text = "bbbbb"))
        messageList.add(Message(9, toId = 1, text = "bbbbb"))
        messageList.add(Message(10, fromId = 1, text = "aaaaa"))
        messageList.add(Message(11, toId = 1, image = "cccccc"))
        messageList.add(Message(12, fromId = 1, record = "dddddd"))
        messageList.add(Message(13, fromId = 1, text = "aaaaa"))
        messageList.add(Message(14, toId = 1, text = "bbbbb"))
        messageList.add(Message(15, toId = 1, text = "bbbbb"))
        messageList.add(Message(16, fromId = 1, text = "aaaaa"))
        messageList.add(Message(17, toId = 1, image = "cccccc"))
        messageList.add(Message(18, toId =1, record = "dddddd"))
        messagelist.value = messageList
    }
}