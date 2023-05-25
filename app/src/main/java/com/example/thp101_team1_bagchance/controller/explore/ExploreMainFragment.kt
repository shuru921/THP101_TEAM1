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
            viewModel?.storys?.observe(viewLifecycleOwner) { storys ->
                // adapter為null要建立新的adapter；之後只要呼叫updateFriends(friends)即可
                if (rvstory.adapter == null) {
                    rvstory.adapter = ExploreStoryAdapter(storys)
                } else {
                    (rvstory.adapter as ExploreStoryAdapter).updateFriends(storys)
                }
            }

//            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                // 輸入的文字改變時呼叫
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    viewModel?.search(newText)
//                    return true
//                }
//                // 點擊虛擬鍵盤上的提交鈕時呼叫
//                override fun onQueryTextSubmit(text: String): Boolean {
//                    return false
//                }
//            })
        }
    }
}