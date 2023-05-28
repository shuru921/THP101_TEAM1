package com.example.thp101_team1_bagchance.controller.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.thp101_team1_bagchance.viewmodel.login.LoginResetPasswordViewModel
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentLoginResetPasswordBinding

class LoginResetPasswordFragment : Fragment() {
    private lateinit var binding: FragmentLoginResetPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: LoginResetPasswordViewModel by viewModels()
        binding = FragmentLoginResetPasswordBinding.inflate(inflater, container, false)
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
            viewModel?.confirmnewpassword?.observe(viewLifecycleOwner) {
                inputvalid()
            }

            btOKLoginResetPassword.setOnClickListener {
                if (!inputvalid()) {
                    return@setOnClickListener
                }
                showAlertDialog()

                Navigation.findNavController(it).navigate(
                    R.id.action_loginResetPasswordFragment_to_loginLoginFragment
                )
                //這邊按下確認且沒有問題應該要彈出視窗告知使用者重設密碼成功，然後在彈出視窗點擊確認才跳到LoginLogin
            }
        }
    }

    private fun inputvalid(): Boolean {
        var valid = true
        with(binding) {
            val newpassword = viewModel?.user?.value?.password?.trim()
            val confirmnewpassword = viewModel?.confirmnewpassword?.value?.trim()
            if (newpassword == null || newpassword.isEmpty()) {
                etNewPasswordLoginResetPassword.error = "密碼不可空白"
                valid = false
            }
            if (confirmnewpassword == null || confirmnewpassword.isEmpty()) {
                etConfirmNewPasswordLoginResetPassword.error = "密碼不相同"
                valid = false
            }
        }
        return valid
    }

    private fun showAlertDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.txtdialog_title_notify))
        alertDialog.setMessage(getString(R.string.txtdialog_message_logingresetpassword))
        alertDialog.setPositiveButton(getString(R.string.txtdialog_positive_button)) {
            dialog: DialogInterface, which: Int ->
            //這裡是點擊確認後應該要導至LoginLogin頁面
        }
    }
}