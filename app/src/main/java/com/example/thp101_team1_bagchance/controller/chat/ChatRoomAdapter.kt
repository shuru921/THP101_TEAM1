package com.example.thp101_team1_bagchance.controller.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thp101_team1_bagchance.ChatMessageType
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.*

class ChatRoomAdapter(var messageList: List<ChatMessageType>) :
    RecyclerView.Adapter<MessageViewHolder<ChatMessageType>>() {

    fun update(message: List<ChatMessageType>) {
        this.messageList = message
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<ChatMessageType> {
        return getViewHolder(viewType, parent)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return messageList[position].viewType
    }

    override fun onBindViewHolder(holder: MessageViewHolder<ChatMessageType>, position: Int) {
        holder.onBind(messageList[position])
    }

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun getViewHolder(viewType: Int, parent: ViewGroup): MessageViewHolder<ChatMessageType> {
            return when (viewType) {
                R.layout.ltext_item_view -> TextViewHolder(LtextItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<ChatMessageType>
                R.layout.rtext_item_view -> MeTextViewHolder(RtextItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<ChatMessageType>
                R.layout.limage_item_view -> ImageViewHolder(LimageItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<ChatMessageType>
                R.layout.rimage_item_view -> MeImageViewHolder(RimageItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<ChatMessageType>
                R.layout.lrecording_item_view -> RecordingViewHolder(LrecordingItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<ChatMessageType>
                R.layout.rrecording_item_view -> MeRecordingViewHolder(RrecordingItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) as MessageViewHolder<ChatMessageType>
                else -> throw Exception()
            }
        }
    }
}