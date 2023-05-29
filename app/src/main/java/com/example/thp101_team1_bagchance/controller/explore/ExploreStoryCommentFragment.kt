package com.example.thp101_team1_bagchance.controller.explore

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentExploreMainBinding
import com.example.thp101_team1_bagchance.databinding.FragmentExploreStoryBinding
import com.example.thp101_team1_bagchance.databinding.FragmentExploreStoryCommentBinding
import com.example.thp101_team1_bagchance.viewmodel.explore.*

//class ExploreStoryCommentFragment: Fragment() {
//    private lateinit var binding: FragmentExploreStoryCommentBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View {
//
//
//        val viewModel: ExploreStoryCommentViewModel by viewModels()
//        binding = FragmentExploreStoryCommentBinding.inflate(inflater, container, false)
//        binding.viewModel = viewModel
//        return binding.root
//    }
//
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        with(binding) {
//            rvcomment.layoutManager = LinearLayoutManager(requireContext())
//            viewModel?.storys?.observe(viewLifecycleOwner) { storys ->
//                // adapter為null要建立新的adapter；之後只要呼叫updateFriends(friends)即可
//                if (rvcomment.adapter == null) {
//                    rvcomment.adapter = ExploreStoryAdapter(storys)
//                } else {
//                    (rvcomment.adapter as ExploreStoryAdapter).updateStorys(storys)
//                }
//            }
//
//        }
//    }
//}