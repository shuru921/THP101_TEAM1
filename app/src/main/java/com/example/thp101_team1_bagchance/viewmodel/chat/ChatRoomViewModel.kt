package com.example.thp101_team1_bagchance.viewmodel.chat

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaRecorder
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thp101_team1_bagchance.LoginUser
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.controller.chat.ChatRoomFragment
import com.example.thp101_team1_bagchance.databinding.ChatItemViewBinding
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import tw.idv.william.androidwebserver.core.service.requestTask
import java.io.ByteArrayOutputStream
import java.io.File

class ChatRoomViewModel : ViewModel() {
    //    假登入帳號
    var user : LoginUser? = null
    //    完整聊天室列表 若判斷資料沒變化即回傳
    val chats : MutableList<SelectChat>
    //    受監控聊天室列表 變化後回傳
    val chatlist : MutableLiveData<List<SelectChat>> by lazy { MutableLiveData<List<SelectChat>>() }
    //    Adapter綁定的資料 把選定的聊天室資料傳入用
    val chatmaterial: MutableLiveData<SelectChat> by lazy { MutableLiveData<SelectChat>() }
    //    聊天輸入框
    val text: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    //        受監控訊息列表 變化後回傳
    val messagelist: MutableLiveData<List<ChatMessageType>> by lazy { MutableLiveData<List<ChatMessageType>>() }
    init {
        val type = object : TypeToken<MutableList<SelectChat>>(){}.type
        user =  requestTask<LoginUser>("http://10.0.2.2:8080/test/web/ChatController/"+"aaa", respBodyType = LoginUser::class.java, method = "OPTIONS")
        chats =  requestTask<MutableList<SelectChat>>("http://10.0.2.2:8080/test//web/ChatController/${user?.id}", respBodyType = type)!!.toMutableList()
        this.chatlist.value = chats
    }


    //         每10秒刷新資料以讓聊天室更新
    fun getNewMessage() {
        viewModelScope.launch {
            while (isActive) {
//                fixme 改用firebase推播去抓聊天室變化 一變化就連資料庫更新 線程不用輪巡可以不用開
                val type = object : TypeToken<List<ChatMessage>>(){}.type
                val chatMessage = requestTask<List<ChatMessage>>("http://10.0.2.2:8080/test/web/ChatMessageController/"+"${chatmaterial!!.value?.id}", respBodyType = type)
                val oldMessageList = (messagelist.value ?: listOf<ChatMessageType>()).toMutableList()
                if (chatMessage != null) {
                    for (i in chatMessage) {
                        oldMessageList.add(i.toChatMessageType(user!!.id!!))
                    }
                }
                messagelist.value = oldMessageList
                delay(30000)
            }
        }
        messagelist.value?.toMutableList()?.clear()
    }

    fun search(newText: String?) {
//    參數空或null把完整列表地址給受監控列表
        if (newText.isNullOrEmpty()) {
            this.chatlist.value = chats
        } else {
//    如果不是空字串，宣告新的集合，走訪每個元素的className屬性，如果符合就放到新的集合並指派
            val searchchatList = mutableListOf<SelectChat>()
//    走訪出來的是一個個聊天室 判斷搜尋條件並且不分大小寫
            for (i in chats) {
                // fixme:  inviteUidname 這裡要寫判斷 判斷自己是邀請人還是被邀請人
                if (i.inviteUidname.contains(newText,true)){
                    searchchatList.add(i)
                }
            }
//    指派給LiveData 就可以即時刷新View
            chatlist.value = searchchatList
        }
    }

    fun sendOnClick () {
//        val test = messagelist.value?.toMutableList() ?: mutableListOf()
        if (text?.value == null || text?.value == "") {
            return
        }else {
            val test2 = ChatMessage(
                chatId = chatmaterial.value!!.id,
                sendUid = user?.id!!,
                message = text.value
            )
            requestTask<JsonObject>(
                "http://10.0.2.2:8080/test//web/ChatMessageController/",
                method = "POST",
                reqBody = test2
            )
            text?.value = ""
        }
    }
}