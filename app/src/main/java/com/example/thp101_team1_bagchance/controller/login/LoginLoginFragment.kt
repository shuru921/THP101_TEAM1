package com.example.thp101_team1_bagchance.controller.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.viewmodel.login.LoginLoginViewModel

class LoginLoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginLoginFragment()
    }

    private lateinit var viewModel: LoginLoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginLoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}