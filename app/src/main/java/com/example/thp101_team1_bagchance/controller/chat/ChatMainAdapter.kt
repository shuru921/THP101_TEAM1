package com.example.thp101_team1_bagchance.controller.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thp101_team1_bagchance.Friend
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.ChatItemViewBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel

class ChatMainAdapter(var friends: List<Friend>)
    : RecyclerView.Adapter<ChatMainAdapter.FriendViewHolder>() {
//  更新受監控列表方法
    fun update (friends: List<Friend>) {
        this.friends = friends
        notifyDataSetChanged()
    }
//  內部類別轉型用(30行)
    class FriendViewHolder(val itemviewbinding: ChatItemViewBinding) :
        RecyclerView.ViewHolder(itemviewbinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
//      就是把chat_item_view給binding進來
        val itemviewbinding = ChatItemViewBinding.inflate(
//          生命週期 容器 不要建立就加進去(看不懂你試試看)
            LayoutInflater.from(parent.context), parent, false
        )
//      以下省略
        itemviewbinding.viewModel = ChatRoomViewModel()
        itemviewbinding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return FriendViewHolder(itemviewbinding)
    }

    override fun getItemCount(): Int {
//      監控列表幾個好友 影響重複執行幾次
        return friends.size
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
//      每次重複執行丟入對應資料[]裡面那叫索引
        val friend = friends[position]
        with(holder) {
            itemviewbinding.viewModel?.friend?.value = friend
//          跳頁我想帶資料走所以寫bundle
            val bundle = Bundle()

            bundle.putSerializable("friend", friend)
            itemView.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_chatMainFragment_to_chatRoomFragment,bundle)
            }

            // TODO: 考慮寫個偷讀訊息 有空的話
        }
    }
}