package com.example.thp101_team1_bagchance.viewmodel.chat

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaRecorder
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thp101_team1_bagchance.controller.chat.ChatRoomFragment
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import tw.idv.william.androidwebserver.core.service.requestTask
import java.io.ByteArrayOutputStream
import java.io.File

class ChatRoomViewModel : ViewModel() {
    //    Adapter綁定的資料 把選定的聊天室資料傳入用
    val chatmaterial: MutableLiveData<SelectChat> by lazy { MutableLiveData<SelectChat>() }
    //    設定聊天室大頭
    val imageBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    //    聊天輸入框
    val text: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    //        受監控訊息列表 變化後回傳
    val messagelist: MutableLiveData<List<ChatMessageType>> by lazy { MutableLiveData<List<ChatMessageType>>() }
//         假資料
    init {
        byteArrayToView()
        var messageList = mutableListOf<ChatMessageType>()
//        messageList.add(ChatMessage(1,2,3,"aaa",null,null,"I",null).toChatMessageType(3))
//        messageList.add(ChatMessage(1,2,3,"aaa",null,null,"I",null).toChatMessageType(3))
//        messageList.add(ChatMessage(1,2,3,"aaa",null,null,"I",null).toChatMessageType(3))
//        messageList.add(ChatMessage(1,2,3,"aaa",null,null,"I",null).toChatMessageType(5))
//        messageList.add(ChatMessage(1,2,3,"aaa",null,null,"I",null).toChatMessageType(5))
        messageList =  requestTask<>()
        messagelist.value = messageList
    }
    fun byteArrayToView () {
        var byteArray : ByteArray?
        if (chatmaterial.value?.inviteUid == ChatMainViewModel().user.id ) {
            byteArray = chatmaterial.value?.beInvitedUidpic
        }else{
            byteArray = chatmaterial.value?.inviteUidpic
        }
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        imageBitmap.value = bitmap
    }
//         每10秒刷新資料以讓聊天室更新
    fun getNewMessage() {
        viewModelScope.launch {
            while (isActive) {
                //  fixme 偉銘的方法貼這 下面34行是範例記得要修改 36行要判斷uid也要修改(判斷置左置右)
//                fixme 改用firebase推播去抓聊天室變化 一變化就連資料庫更新 線程不用輪巡可以不用開
                val chatMessage = ChatMessage(id = 0, sendUid = 0, chatId = 0, messageStatus = "")
                val oldMessageList = messagelist.value ?: listOf()
                messagelist.value = oldMessageList.plus(chatMessage.toChatMessageType(123))
                delay(10000)
            }
        }
    }

    fun sendOnClick () {
        val test = messagelist.value?.toMutableList() ?: mutableListOf()
        if (text?.value == null || text?.value == "") {
            return
        }else {
            test.add(ChatMessageType.Rtext(text = text?.value.toString()))
            messagelist.value = test
        }
        text?.value = ""
        // fixme: 要把資料傳給資料庫 並且傳入對話框內
    }
}