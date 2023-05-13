package com.example.thp101_team1_bagchance.controller.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thp101_team1_bagchance.R

/**
 * A simple [Fragment] subclass.
 * Use the [SettingAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingAccountFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_account, container, false)
    }


}