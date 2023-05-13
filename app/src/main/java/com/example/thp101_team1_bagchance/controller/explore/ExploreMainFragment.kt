package com.example.thp101_team1_bagchance.controller.explore

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thp101_team1_bagchance.ExploreMainViewModel
import com.example.thp101_team1_bagchance.R

class ExploreMainFragment : Fragment() {

    companion object {
        fun newInstance() = ExploreMainFragment()
    }

    private lateinit var viewModel: ExploreMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExploreMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}