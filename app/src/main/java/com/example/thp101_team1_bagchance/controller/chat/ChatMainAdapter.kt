package com.example.thp101_team1_bagchance.controller.chat

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.viewmodel.chat.SelectChat
import com.example.thp101_team1_bagchance.databinding.ChatItemViewBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.File
import java.io.FileOutputStream
var content = 0
class ChatMainAdapter(var chats: List<SelectChat>) :
    RecyclerView.Adapter<ChatMainAdapter.FriendViewHolder>() {
    val myTag = "myTag_${javaClass.simpleName}"

    //  更新受監控列表方法
    fun update(chats: List<SelectChat>) {
        this.chats = chats
        notifyDataSetChanged()
    }

    //  內部類別轉型用
    class FriendViewHolder(val itemviewbinding: ChatItemViewBinding) :
        RecyclerView.ViewHolder(itemviewbinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
//      就是把chat_item_view給binding進來
        val itemviewbinding = ChatItemViewBinding.inflate(
//          生命週期 容器 不要建立就加進去
            LayoutInflater.from(parent.context), parent, false
        )
//      連結viewmodel 並與父類生命週期綁定(?)
        itemviewbinding.viewModel = ChatRoomViewModel()
        itemviewbinding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return FriendViewHolder(itemviewbinding)
    }

    override fun getItemCount(): Int {
//      監控列表幾個好友 影響重複執行幾次
        return chats.size
    }


    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
//        資料載入的地方
        val chat = chats[position]
        with(holder) {
//            判斷載入誰的大頭貼
            itemviewbinding.viewModel?.chatmaterial?.value = chat
            var byteArray: ByteArray?
            if (chat.inviteUid == itemviewbinding.viewModel?.user?.id) {
                byteArray = chat.beInvitedUidpic
            } else {
                byteArray = chat.inviteUidpic
            }
            if (byteArray != null && byteArray.isNotEmpty()) {
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                itemviewbinding.ivAvatarChat.setImageBitmap(bitmap)
            }
//            判斷載入誰的暱稱
            itemviewbinding.tvIdChat.text = if (chat.inviteUid == itemviewbinding.viewModel?.user?.id) {
                chat.beInvitedUidname
            } else {
                chat.inviteUidname
            }
//            載入最後一筆訊息 若為錄音圖片則轉為文字
            itemviewbinding.tvLastMessageChat.text = if (chat.message != null && chat.message.isNotEmpty()) {
                "${chat.message}"
            } else if (chat.PICTURE != null && chat.PICTURE.size != 0) {
                if (chat.sendUid == itemviewbinding.viewModel?.user?.id) {
                    "${itemviewbinding.root.context.getString(R.string.txtmesendpic)}"
                }else {
                    "${itemviewbinding.tvIdChat.text}${itemviewbinding.root.context.getString(R.string.txtsendpic)}"
                }
            } else if (chat.RECORDINGPATH != null && chat.RECORDINGPATH.isNotEmpty()) {
                if (chat.sendUid != itemviewbinding.viewModel?.user?.id) {
                    "${itemviewbinding.root.context.getString(R.string.txtmesendrecording)}"
                }else {
                    "${itemviewbinding.tvIdChat.text}${itemviewbinding.root.context.getString(R.string.txtsendrecording)}"
                }
            } else {
                ""
            }
//            fixme 已讀未讀標示
//            大頭貼圖片過大 將雙方uid bundle至下個頁面 以雙方載入uid該聊天室訊息
            val bundle = Bundle()
            bundle.putSerializable("chatmaterial_inuid",chat.inviteUid)
            bundle.putSerializable("chatmaterial_beuid",chat.beInvitedUid)
            Log.d(myTag ,"chat: ${chat.toString()}"
            )
            itemView.setOnClickListener {
                findNavController(it).navigate(R.id.action_chatMainFragment_to_chatRoomFragment,bundle)
            }

        }
    }
}

