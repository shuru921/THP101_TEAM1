package com.example.thp101_team1_bagchance.controller.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.thp101_team1_bagchance.Message
import com.example.thp101_team1_bagchance.databinding.RoomItemViewBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel

//        不確定要不要改成可改List
class ChatRoomAdapter(var messageList: List<Message>) :
    RecyclerView.Adapter<ChatRoomAdapter.MessageViewHolder>() {

    fun update(message: List<Message>) {
        this.messageList = message
        notifyDataSetChanged()
    }

    class MessageViewHolder(val itemViewBinding: RoomItemViewBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemViewBinding = RoomItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        itemViewBinding.viewModel = ChatRoomViewModel()
        itemViewBinding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return MessageViewHolder(itemViewBinding)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]
        with(holder.itemViewBinding) {
            if (message.fromId == 0) {
                llTo.visibility = View.VISIBLE
                if (message.text.isNotEmpty()) {
                    tvMeImessageChat.visibility = View.VISIBLE
                    tvMeImessageChat.text = message.text
                } else if (message.record.isNotEmpty()) {
//                    recorderMessage.ivPlayChat.visibility = View.VISIBLE
                } else if (message.image.isNotEmpty()) {
                    ivMePictureChat.visibility = View.VISIBLE
                }
            } else {
                llFrom.visibility = View.VISIBLE
                if (message.text.isNotEmpty()) {
                    tvImessageChat.visibility = View.VISIBLE
                    tvImessageChat.text = message.text
                } else if (message.record.isNotEmpty()) {
//                    recorderMessage.ivPlayChat.visibility = View.VISIBLE
                } else if (message.image.isNotEmpty()) {
                    ivPictureChat.visibility = View.VISIBLE
                }
            }
        }
    }
}