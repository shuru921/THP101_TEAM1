package com.example.thp101_team1_bagchance.controller.chat

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentChatRoomBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.File

class ChatRoomFragment : Fragment() {
    private lateinit var binding: FragmentChatRoomBinding
    private val fileName = "sample.3gp"
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var recordGranted = false
    private lateinit var file: File
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewModel: ChatRoomViewModel by viewModels()
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
//              把聊天室基本資料傳入
                binding.viewModel?.friend?.value = it as Any
            }
        }

        with(binding) {
            ivPhotoChat.setOnClickListener {
                // TODO: 照片或拍照得下方彈出篩選 一樣對話框得加入格式(縮圖)
            }
            ivRecordingChat.setOnClickListener {
                // TODO: 錄音下方彈出怎麼寫 對話框目前只有textview 得修改
                val bottomSheetBehavior = BottomSheetBehavior.from(included.bottomSheet)
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
//                設定長點擊錄音 放開就結束錄音
                included.ivRecordChat
                    // TODO: 錄音功能 送出

            }
            ivSendChat.setOnClickListener {
                // TODO: 要把資料傳給資料庫 並且傳入對話框內 判斷置左置右
            }
        }
    }
}

