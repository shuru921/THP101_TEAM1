package com.example.thp101_team1_bagchance.controller.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentSettingMainBinding

class SettingMainFragment : Fragment() {
    private lateinit var binding: FragmentSettingMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        requireActivity().setTitle(R.string.txtSetting)
        binding = FragmentSettingMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle(R.string.txtSetting)
        with(binding) {

        }
    }

}