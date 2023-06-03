package com.example.thp101_team1_bagchance.controller.chat

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.viewmodel.chat.SelectChat
import com.example.thp101_team1_bagchance.databinding.ChatItemViewBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel

class ChatMainAdapter(var chats: List<SelectChat>) :
    RecyclerView.Adapter<ChatMainAdapter.FriendViewHolder>() {
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
//      以下省略
        itemviewbinding.viewModel = ChatRoomViewModel()
        itemviewbinding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return FriendViewHolder(itemviewbinding)
    }

    override fun getItemCount(): Int {
//      監控列表幾個好友 影響重複執行幾次
        return chats.size
    }


    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val chat = chats[position]
        with(holder) {
            itemviewbinding.viewModel?.chatmaterial?.value = chat
//          跳頁帶資料走所以寫bundle
            val bundle = Bundle()
            bundle.putSerializable("chatmaterial", chat)
            itemView.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_chatMainFragment_to_chatRoomFragment, bundle)

            }
        }
    }
}