package com.example.thp101_team1_bagchance.controller.me

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.viewmodel.me.MeMainViewModel

class MeMainFragment : Fragment() {
//狗溝狗溝
    companion object {
        fun newInstance() = MeMainFragment()
    }

    private lateinit var viewModel: MeMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_me_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MeMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}