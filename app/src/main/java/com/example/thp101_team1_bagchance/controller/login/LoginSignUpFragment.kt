package com.example.thp101_team1_bagchance.controller.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel?.user?.observe(viewLifecycleOwner) {
                inputvalid()
            }
            viewModel?.confirmpassword?.observe(viewLifecycleOwner) {
                inputvalid()
            }

            btOKLoginSignup.setOnClickListener {
                if (!inputvalid()) {
                    return@setOnClickListener
                }
                //這邊應該要彈出視窗告知使用者註冊成功，然後在彈出視窗點擊確認後再進入個人資料填寫頁面
            }
        }
    }

    private fun inputvalid(): Boolean {
        var valid = true
        with(binding) {
            val email = viewModel?.user?.value?.email?.trim()
            val phone = viewModel?.user?.value?.phone?.trim()
            val password = viewModel?.user?.value?.password?.trim()
            val confirmpassword = viewModel?.confirmpassword?.value?.trim()
            if (email == null || email.isEmpty()) {
                etEmailLoginSignup.error = "信箱不可空白"
                valid = false
            }
            if (phone == null || phone.isEmpty()) {
                etPhoneLoginSignup.error = "電話不可空白"
                valid = false
            }
            if (password == null || password.isEmpty()) {
                etPasswordLoginSignup.error = "密碼不可空白"
                valid = false
            }
            if (confirmpassword == null || confirmpassword.isEmpty()) {
                etConfirmPasswordLoginSignup.error = "密碼不相同"
                valid = false
            }
        }
        return valid
    }
}