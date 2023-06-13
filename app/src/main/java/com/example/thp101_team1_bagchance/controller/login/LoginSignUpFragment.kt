package com.example.thp101_team1_bagchance.controller.login

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.thp101_team1_bagchance.LoginUser
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.core.service.requestTask
import com.example.thp101_team1_bagchance.core.util.URL_ROOT
import com.example.thp101_team1_bagchance.databinding.FragmentLoginSignUpBinding
import com.example.thp101_team1_bagchance.viewmodel.login.LoginSignUpViewModel
import org.json.JSONObject

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
            viewModel?.email?.observe(viewLifecycleOwner) {
                inputvalid()
            }
            viewModel?.phone?.observe(viewLifecycleOwner) {
                inputvalid()
            }
            viewModel?.password?.observe(viewLifecycleOwner) {
                inputvalid()
            }
            viewModel?.confirmpassword?.observe(viewLifecycleOwner) {
                inputvalid()
            }

            val onClickListener = DialogInterface.OnClickListener { dialog, which ->
                val buttonText = when (which) {
                    AlertDialog.BUTTON_POSITIVE -> R.string.txtdialog_positive_button
                    else -> ""
                }

                dialog.cancel()

                if (which == AlertDialog.BUTTON_POSITIVE) {
                    Navigation.findNavController(view).navigate(
                        R.id.action_loginSignUpFragment_to_loginLoginFragment
                    )
                }
            }

            btOKLoginSignup.setOnClickListener {

                if (!inputvalid()) {
                    return@setOnClickListener
                }

                val user = LoginUser(email = "${viewModel?.email?.value}", phone = "${viewModel?.phone?.value}", password = "${viewModel?.password?.value}")
                val signup = requestTask<JSONObject>(url = URL_ROOT + "user/register", method = "POST" , reqBody = user)

                //這邊條件不會寫，所以不管後端回傳註冊true或是false都會進入else並彈出註冊成功視窗
                if (signup == null) {
                    return@setOnClickListener
                }
                else {
                    android.app.AlertDialog.Builder(view.context)
                        .setTitle(R.string.txtdialog_title_notify)
                        .setMessage(R.string.txtdialog_message_loginsignup_true)
                        .setPositiveButton(R.string.txtdialog_positive_button, onClickListener)
                        .setCancelable(false)
                        .show()
                }
            }
        }
    }

    private fun inputvalid(): Boolean {
        var valid = true
        with(binding) {
            val email = viewModel?.email?.value?.trim()
            val phone = viewModel?.phone?.value?.trim()
            val password = viewModel?.password?.value?.trim()
            val confirmpassword = viewModel?.confirmpassword?.value?.trim()
            if (email == null || email.isEmpty()) {
                etEmailLoginSignup.error = "信箱不可空白"
                valid = false
            }
            if (phone == null || phone.isEmpty()) {
                etPhoneLoginSignup.error = "電話不可空白"
                valid = false
            }
            if (password?.length ?: 0 < 6 || password?.length ?: 0 > 12) {
                etPasswordLoginSignup.error = "密碼最少6個字、最多12個字"
                valid = false
            }
            if (confirmpassword == null || confirmpassword.isEmpty() || confirmpassword != password) {
                etConfirmPasswordLoginSignup.error = "密碼不相同"
                valid = false
            }
        }
        return valid
    }
}