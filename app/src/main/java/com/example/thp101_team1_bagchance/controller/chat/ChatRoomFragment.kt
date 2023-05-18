package com.example.thp101_team1_bagchance.controller.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentChatMainBinding
import com.example.thp101_team1_bagchance.databinding.FragmentChatRoomBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMainViewModel

class ChatRoomFragment : Fragment() {
    private lateinit var binding: FragmentChatRoomBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel : ChatRoomViewModel by viewModels()
        binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getSerializable("friend")?.let {
//              Any亂打的沒紅字希望我會記得
                binding.viewModel?.friend?.value = it as Any
            }
        }

        with (binding) {
            ivPhotoChat
                // TODO: 照片或拍照得下方彈出篩選 一樣對話框得加入格式(縮圖)
            ivRecordingChat
                // TODO: 錄音下方彈出怎麼寫 對話框目前只有textview 得修改
            ivSendChat.setOnClickListener{
                // TODO: 要把資料傳給資料庫 並且傳入對話框內 判斷置左置右
            }
        }
    }
}