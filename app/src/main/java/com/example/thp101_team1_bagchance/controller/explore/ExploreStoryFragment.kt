package com.example.thp101_team1_bagchance.controller.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentExploreMainBinding
import com.example.thp101_team1_bagchance.databinding.FragmentExploreStoryBinding
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreMainViewModel
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
        arguments?.let { bundle ->
            bundle.getSerializable("story")?.let {
                binding.viewModel?.story?.value = it as ExploreStory
            }
        }
    }
}