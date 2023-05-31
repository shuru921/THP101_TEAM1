package com.example.thp101_team1_bagchance.controller.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMessageType
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.*

class ChatRoomAdapter(var messageList: List<ChatMessageType>) :
    RecyclerView.Adapter<MessageViewHolder<ChatMessageType>>() {
//        更新聊天室方法
    fun update(message: List<ChatMessageType>) {
        this.messageList = message
        notifyDataSetChanged()
    }
//        判斷資料類別後binding對應的itemview 19行調用靜態方法內的判斷
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<ChatMessageType> {
        return getViewHolder(viewType, parent)
    }
//        抓訊息數量
    override fun getItemCount(): Int {
        return messageList.size
    }
//        判斷資料是 文字 圖片 錄音 置左還是置右
    override fun getItemViewType(position: Int): Int {
        return messageList[position].viewType
    }
//        對應的viewholder 有onbind方法調用 這裡會執行很多次 每次都會調用對應的onbind方法
    override fun onBindViewHolder(holder: MessageViewHolder<ChatMessageType>, position: Int) {
        holder.onBind(messageList[position])
    }
//        kotlin靜態方法
    companion object {
//        真正判斷資料類別的地方
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