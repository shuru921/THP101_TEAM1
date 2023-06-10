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
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.controller.chat.ChatRoomFragment
import com.example.thp101_team1_bagchance.controller.chat.MyFCMService
import com.example.thp101_team1_bagchance.databinding.ChatItemViewBinding
import com.google.firebase.messaging.FirebaseMessaging
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
    //
    val myTag = "myTag_${javaClass.simpleName}"
    //    假登入帳號
    var user: User? = null
    //    完整聊天室列表 若判斷資料沒變化即回傳
    val chats : MutableList<SelectChat>
    //    受監控聊天室列表 變化後回傳
    val chatlist : MutableLiveData<List<SelectChat>> by lazy { MutableLiveData<List<SelectChat>>() }
    //    Adapter綁定的資料 把選定的聊天室資料傳入用
    val chatmaterial: MutableLiveData<SelectChat> by lazy { MutableLiveData<SelectChat>() }
    //    聊天輸入框
    val text: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    //    Firebase token
    val token: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    //    受監控聊天室訊息列表 變化後回傳
    val messagelist: MutableLiveData<List<ChatMessageType>> by lazy { MutableLiveData<List<ChatMessageType>>() }

    init {
//       發送token至server server會再送至db
        getToken()
        val type = object : TypeToken<MutableList<SelectChat>>(){}.type
        user =  requestTask<User>("http://10.0.2.2:8080/test/web/ChatController/"+"aaa@gmail.com", respBodyType = User::class.java, method = "OPTIONS")
        Log.d( myTag ,"user => ${user}")
        chats =  requestTask<MutableList<SelectChat>>("http://10.0.2.2:8080/test//web/ChatController/${user?.id}", respBodyType = type)!!.toMutableList()
        Log.d( myTag ,"chat => ${chats}")
        this.chatlist.value = chats
    }
//  向firebase申請token
    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.let { token ->
                    Log.d( myTag , "token: $token")
                    this.token.value = token
//                    發送至後端
                    sendToken(token)
                }
            }
        }
    }

    fun sendToken(token : String) {
        val jo = JsonObject()
        jo.addProperty("mail", user?.mail)
        jo.addProperty("token", token)
        requestTask<JsonObject>("http://10.0.2.2:8080/test/fcm/", method = "PUT", reqBody = jo)
    }

    //         與後端連線更新聊天頁面
    fun getNewMessage() {
        viewModelScope.launch {
//            while (isActive) {
                val type = object : TypeToken<List<ChatMessage>>(){}.type
                Log.d( myTag ,"chatmaterial!!.value?.id => ${chatmaterial!!.value?.id}")
                val chatMessage = requestTask<List<ChatMessage>>("http://10.0.2.2:8080/test/web/ChatMessageController/"+"${chatmaterial!!.value?.id}", respBodyType = type)
                val oldMessageList = mutableListOf<ChatMessageType>()
                if (chatMessage != null) {
                    for (i in chatMessage) {
                        oldMessageList.add(i.toChatMessageType(user!!.id!!))
                    }
                }
                Log.d("TAG_${javaClass.simpleName}", "oldMessageList: ${oldMessageList} ")
                messagelist.value = oldMessageList
                Log.d("TAG_${javaClass.simpleName}", "messagelist: ${messagelist.value} ")
//                delay(30000)
//            }
        }
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
//                判斷聊天室列表暱稱顯示誰的
                val text = if (chatmaterial?.value?.inviteUid == user?.id) {
                    chatmaterial?.value?.beInvitedUidname
                }else {
                    chatmaterial?.value?.inviteUidname
                }

                if (text == chatmaterial?.value?.beInvitedUidname) {
                    if (i.beInvitedUidname.contains(newText, true)) {
                        searchchatList.add(i)
                    }
                }else {
                    if (i.inviteUidname.contains(newText, true)) {
                        searchchatList.add(i)
                    }
                }
            }
//    指派給LiveData 就可以即時刷新View
            chatlist.value = searchchatList
        }
    }

//    聊天室列表 文字輸入發送
    fun sendOnClick () {
//      發送訊息
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
//        發送推播
        // TODO: 只能單向待處理
        val toMail = if (chatmaterial?.value?.inviteUid == user?.id) {
            chatmaterial?.value?.beInvitedUidMail
        }else {
            chatmaterial?.value?.invitedUidMail
        }
        val jsonObject = JsonObject()
        jsonObject.addProperty("action", "singleFcm")
        jsonObject.addProperty("title", "您有新訊息")
        jsonObject.addProperty("body", "快來看看是誰吧!")
        Log.d( myTag ,"${toMail}")
        jsonObject.addProperty("toMail", toMail )
        requestTask<JsonObject>("http://10.0.2.2:8080/test//fcm/", method = "POST", reqBody = jsonObject)

        getNewMessage()
    }
}
