package com.example.thp101_team1_bagchance.controller.explore

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thp101_team1_bagchance.databinding.FragmentExploreStoryBinding
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreMainStory
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
            bundle.getSerializable("mainstory")?.let {
                val mainstory = it as ExploreMainStory
                val bitmap = BitmapFactory.decodeByteArray(mainstory.profile_pic, 0, mainstory.profile_pic.size)
                binding.btstorypic.setImageBitmap(bitmap)
                val bitmapsyory = BitmapFactory.decodeByteArray(mainstory.pic, 0, mainstory.pic.size)
                binding.StoryinimageView.setImageBitmap(bitmapsyory)
                binding.viewModel?.mainstory?.value = mainstory
                Log.d("sssssssssss","mainstory.value?.id : ${mainstory.id}")

            }
        }
        binding.viewModel?.findcomment()
        with(binding){
            rvcomment.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.comments?.observe(viewLifecycleOwner) { commemts ->
                // adapter為null要建立新的adapter；之後只要呼叫updateFriends(friends)即可
                if (rvcomment.adapter == null) {
                    rvcomment.adapter = ExploreStoryCommentAdapter(commemts)
                } else {
                    (rvcomment.adapter as ExploreStoryCommentAdapter).updatecomments(commemts)
                }
            }
        }


    }
}