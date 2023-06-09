package com.example.thp101_team1_bagchance.controller.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.thp101_team1_bagchance.LoginUser
import com.example.thp101_team1_bagchance.viewmodel.login.LoginForgetPasswordViewModel
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.core.service.requestTask
import com.example.thp101_team1_bagchance.core.util.URL_ROOT
import com.example.thp101_team1_bagchance.databinding.FragmentLoginForgetPasswordBinding


class LoginForgetPasswordFragment : Fragment() {
    private lateinit var binding: FragmentLoginForgetPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: LoginForgetPasswordViewModel by viewModels()
        binding = FragmentLoginForgetPasswordBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel?.phone?.observe(viewLifecycleOwner) {
                inputvalid()
            }
            viewModel?.verificationcode?.observe(viewLifecycleOwner) {
                inputvalid()
            }

            btVerificationCodeLoginForgetPassword.setOnClickListener {
                //這邊需要判斷輸入的電話號碼是否為已註冊的使用者資料
                //然後再連接手機驗證碼API取得驗證碼
            }

            btOKLoginForgetPassword.setOnClickListener {
                if(!inputvalid()) {
                    return@setOnClickListener
                }

                val bundle = Bundle()
                val findBody = LoginUser(phone = "${viewModel?.phone?.value}")
                val user = requestTask<LoginUser>(URL_ROOT + "user/find", method = "POST" , reqBody = findBody)

                bundle.putSerializable("user", user)
                //這邊還要加入判斷驗證碼是否正確的程式碼
                Navigation.findNavController(it).navigate(
                    R.id.action_loginForgetPasswordFragment_to_loginResetPasswordFragment,
                    bundle
                )
            }
            btPreviousPageLoginForgetPassword.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    R.id.action_loginForgetPasswordFragment_to_loginLoginFragment
                )
            }
        }
    }

    private fun inputvalid(): Boolean {
        var valid = true
        with(binding) {
            val phone = viewModel?.phone?.value?.trim()
            val verificationcode = viewModel?.verificationcode?.value?.trim()
            if (phone == null || phone.isEmpty()) {
                etPhoneLoginForgetPassword.error = "電話不可空白"
                valid = false
            }
            if (verificationcode == null || verificationcode.isEmpty()) {
                etVerificationCodeLoginForgetPassword.error = "驗證碼不可空白"
                valid = false
            }
        }
        return valid
    }
}