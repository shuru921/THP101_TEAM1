package com.example.thp101_team1_bagchance.controller.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel
import com.example.thp101_team1_bagchance.R

class ChatRoomFragment : Fragment() {

    companion object {
        fun newInstance() = ChatRoomFragment()
    }

    private lateinit var viewModel: ChatRoomViewModel

=======
import androidx.fragment.app.viewModels
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentChatMainBinding
import com.example.thp101_team1_bagchance.databinding.FragmentChatRoomBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMainViewModel

class ChatRoomFragment : Fragment() {
    private lateinit var binding: FragmentChatRoomBinding
>>>>>>> main
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<< HEAD
        return inflater.inflate(R.layout.fragment_chat_room, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatRoomViewModel::class.java)
        // TODO: Use the ViewModel
    }

=======
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
>>>>>>> main
}