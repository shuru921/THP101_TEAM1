package com.example.thp101_team1_bagchance.controller.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thp101_team1_bagchance.viewmodel.login.LoginPersonalInformationViewModel
import com.example.thp101_team1_bagchance.R

class LoginPersonalInformationFragment : Fragment() {

    companion object {
        fun newInstance() = LoginPersonalInformationFragment()
    }

    private lateinit var viewModel: LoginPersonalInformationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_personal_information, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginPersonalInformationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}