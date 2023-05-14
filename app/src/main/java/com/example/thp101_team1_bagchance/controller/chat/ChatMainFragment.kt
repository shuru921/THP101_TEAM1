package com.example.thp101_team1_bagchance.controller.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMainViewModel
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentChatMainBinding
import com.example.thp101_team1_bagchance.databinding.FragmentSettingMainBinding

class ChatMainFragment : Fragment() {
    private lateinit var binding: FragmentChatMainBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        //requireActivity().setTitle(R.string.txtChat)
        binding = FragmentChatMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //activity?.setTitle(R.string.txtChat)
        with(binding) {

        }
    }

}