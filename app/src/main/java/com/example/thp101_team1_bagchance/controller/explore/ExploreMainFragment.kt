package com.example.thp101_team1_bagchance.controller.explore

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thp101_team1_bagchance.MainActivity
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreMainViewModel
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentExploreMainBinding
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreStory
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreStoryViewModel

class ExploreMainFragment : Fragment() {
    private lateinit var binding: FragmentExploreMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        val viewModel: ExploreMainViewModel by viewModels()
        binding = FragmentExploreMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        with(binding) {
            rvstory.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.mainstorys?.observe(viewLifecycleOwner) { mainstorys ->
                // adapter為null要建立新的adapter；之後只要呼叫updateFriends(friends)即可
                if (rvstory.adapter == null) {
                    rvstory.adapter = ExploreMainStoryAdapter(mainstorys)
                } else {
                    (rvstory.adapter as ExploreMainStoryAdapter).updatemainStorys(mainstorys)
                }
            }

        }


    }
}