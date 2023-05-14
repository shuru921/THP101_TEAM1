package com.example.thp101_team1_bagchance.controller.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thp101_team1_bagchance.viewmodel.login.LoginResetPasswordViewModel
import com.example.thp101_team1_bagchance.R

class LoginResetPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = LoginResetPasswordFragment()
    }

    private lateinit var viewModel: LoginResetPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_reset_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginResetPasswordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}