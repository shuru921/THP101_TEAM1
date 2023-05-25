package com.example.thp101_team1_bagchance.controller.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentLoginSignUpBinding
import com.example.thp101_team1_bagchance.viewmodel.login.LoginSignUpViewModel

class LoginSignUpFragment : Fragment() {
    private lateinit var binding: FragmentLoginSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: LoginSignUpViewModel by viewModels()
        binding = FragmentLoginSignUpBinding.inflate(inflater, container ,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

}