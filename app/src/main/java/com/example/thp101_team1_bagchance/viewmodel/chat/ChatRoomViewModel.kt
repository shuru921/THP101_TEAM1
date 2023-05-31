package com.example.thp101_team1_bagchance.viewmodel.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thp101_team1_bagchance.ChatMessage
import com.example.thp101_team1_bagchance.ChatMessageType
import com.example.thp101_team1_bagchance.SelectChat
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ChatRoomViewModel : ViewModel() {
    //    Adapter綁定的資料 把選定的聊天室資料傳入用
    val chatmaterial: MutableLiveData<SelectChat> by lazy { MutableLiveData<SelectChat>() }

    //    聊天輸入框
    val text: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //        受監控訊息列表 變化後回傳
    val messagelist: MutableLiveData<List<ChatMessageType>> by lazy { MutableLiveData<List<ChatMessageType>>() }
//         假資料
    init {
        val messageList = mutableListOf<ChatMessageType>()
        messageList.add(ChatMessage(1,2,3,"aaa",null,null,"I",null).toChatMessageType(3))
        messageList.add(ChatMessage(1,2,3,"aaa",null,null,"I",null).toChatMessageType(3))
        messageList.add(ChatMessage(1,2,3,"aaa",null,null,"I",null).toChatMessageType(3))
        messageList.add(ChatMessage(1,2,3,"aaa",null,null,"I",null).toChatMessageType(5))
        messageList.add(ChatMessage(1,2,3,"aaa",null,null,"I",null).toChatMessageType(5))
        messagelist.value = messageList
    }

    fun getNewMessage() {
        viewModelScope.launch {
            while (isActive) {
                // 抓資料的方法
                val chatMessage = ChatMessage(id = 0, sendUid = 0, chatId = 0, messageStatus = "")
                val oldMessageList = messagelist.value ?: listOf()
                messagelist.value = oldMessageList.plus(chatMessage.toChatMessageType(123))
                delay(10000)
            }
        }
    }
}