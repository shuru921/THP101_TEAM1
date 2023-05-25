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
import com.example.thp101_team1_bagchance.databinding.FragmentLoginLoginBinding
import com.example.thp101_team1_bagchance.viewmodel.login.LoginLoginViewModel

class LoginLoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: LoginLoginViewModel by viewModels()
        binding = FragmentLoginLoginBinding.inflate(inflater, container, false)
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

            btOKLoginLogin.setOnClickListener {
                if (!inputvalid()) {
                    return@setOnClickListener
                }
//              之後要再加入判斷是否填寫過個人資料的程式碼
//                以下是要導入首頁的程式碼，暫時沒有
//                Navigation.findNavController(it).navigate(
//                    R.id.
//                )
            }
        }
    }

    private fun inputvalid(): Boolean {
        var valid = true
        with(binding) {
            val phone = viewModel?.user?.value?.phone?.trim()
            val password = viewModel?.user?.value?.password?.trim()
            if (phone == null || phone.isEmpty()) {
                etPhoneLoginLogin.error = "電話不可空白"
                valid = false
            }
            if (password == null || password.isEmpty()) {
                etPasswordLoginLogin.error = "密碼不可空白"
                valid = false
            }
        }
        return valid
    }
}