package com.example.thp101_team1_bagchance.controller.explore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thp101_team1_bagchance.databinding.FragmentExploreStoryBinding
import com.example.thp101_team1_bagchance.databinding.FragmentExploreStoryCommentBinding
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreComment
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreStory
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreStoryViewModel


class ExploreStoryFragment : Fragment() {
    private lateinit var binding: FragmentExploreStoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        val viewModel: ExploreStoryViewModel by viewModels()
        binding = FragmentExploreStoryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            rvcomment.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.commemts?.observe(viewLifecycleOwner) { commemts ->
                // adapter為null要建立新的adapter；之後只要呼叫updateFriends(friends)即可
                if (rvcomment.adapter == null) {
                    rvcomment.adapter = ExploreStoryCommentAdapter(commemts)
                } else {
                    (rvcomment.adapter as ExploreStoryCommentAdapter).updatecomments(commemts)
                }
            }
        }


        arguments?.let { bundle ->
            bundle.getSerializable("story")?.let {
                binding.viewModel?.story?.value = it as ExploreStory

            }
        }
    }
}