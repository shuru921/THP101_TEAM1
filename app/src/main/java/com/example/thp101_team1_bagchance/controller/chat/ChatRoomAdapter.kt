package com.example.thp101_team1_bagchance.controller.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.thp101_team1_bagchance.Message
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.*
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel

class ChatRoomAdapter(var messageList: List<Message>) :
    RecyclerView.Adapter<MessageViewHolder<Message>>() {

    fun update(message: List<Message>) {
        this.messageList = message
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<Message> {
        return getViewHolder(viewType, parent)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return messageList[position].viewType
    }

    override fun onBindViewHolder(holder: MessageViewHolder<Message>, position: Int) {
        holder.onBind(messageList[position])
//        val message = messageList[position]
//        with(holder.itemViewBinding) {
//            if (message.fromId == 0) {
//                llTo.visibility = View.VISIBLE
//                llFrom.visibility = View.GONE
//                if (message.text.isNotEmpty()) {
//                    tvMeImessageChat.visibility = View.VISIBLE
//                    tvMeImessageChat.text = message.text
//                    llMeRecorderChat.visibility = View.GONE
//                    ivMePictureChat.visibility = View.GONE
//                } else if (message.record.isNotEmpty()) {
//                    llMeRecorderChat.visibility = View.VISIBLE
//                    tvMeImessageChat.visibility = View.GONE
//                    ivMePictureChat.visibility = View.GONE
//                } else if (message.image.isNotEmpty()) {
//                    ivMePictureChat.visibility = View.VISIBLE
//                    llMeRecorderChat.visibility = View.GONE
//                    tvMeImessageChat.visibility = View.GONE
//                }
//            } else {
//                llFrom.visibility = View.VISIBLE
//                llTo.visibility = View.GONE
//                if (message.text.isNotEmpty()) {
//                    tvImessageChat.visibility = View.VISIBLE
//                    tvImessageChat.text = message.text
//                    llRecorderChat.visibility = View.GONE
//                    ivPictureChat.visibility = View.GONE
//                } else if (message.record.isNotEmpty()) {
//                    llRecorderChat.visibility = View.VISIBLE
//                    tvImessageChat.visibility = View.GONE
//                    ivPictureChat.visibility = View.GONE
//                } else if (message.image.isNotEmpty()) {
//                    ivPictureChat.visibility = View.VISIBLE
//                    llRecorderChat.visibility = View.GONE
//                    tvImessageChat.visibility = View.GONE
//                }
//            }
//        }
    }

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun getViewHolder(viewType: Int, parent: ViewGroup): MessageViewHolder<Message> {
            return when (viewType) {
                R.layout.ltext_item_view -> TextViewHolder(LtextItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<Message>
                R.layout.rtext_item_view -> MeTextViewHolder(RtextItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<Message>
                R.layout.limage_item_view -> ImageViewHolder(LimageItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<Message>
                R.layout.rimage_item_view -> MeImageViewHolder(RimageItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<Message>
                R.layout.lrecording_item_view -> RecordingViewHolder(LrecordingItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<Message>
                R.layout.rrecording_item_view -> MeRecordingViewHolder(RrecordingItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<Message>
                else -> throw Exception()
            }
        }
    }
}