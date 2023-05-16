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
    }
}