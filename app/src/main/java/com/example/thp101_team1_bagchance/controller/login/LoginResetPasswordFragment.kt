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
import com.example.thp101_team1_bagchance.LoginUser
import com.example.thp101_team1_bagchance.viewmodel.login.LoginResetPasswordViewModel
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.core.service.requestTask
import com.example.thp101_team1_bagchance.core.util.URL_ROOT
import com.example.thp101_team1_bagchance.databinding.FragmentLoginResetPasswordBinding
import org.json.JSONObject

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
            viewModel?.newpassword?.observe(viewLifecycleOwner) {
                inputvalid()
            }
            viewModel?.confirmnewpassword?.observe(viewLifecycleOwner) {
                inputvalid()
            }

            val onClickListener = DialogInterface.OnClickListener { dialog, which ->
                val buttonText = when (which) {
                    AlertDialog.BUTTON_POSITIVE -> getString(R.string.txtdialog_positive_button)
                    else -> ""
                }

                dialog.cancel()

                if (which == AlertDialog.BUTTON_POSITIVE) {
                    Navigation.findNavController(view).navigate(
                        R.id.action_loginResetPasswordFragment_to_loginLoginFragment
                    )
                }
            }

            btOKLoginResetPassword.setOnClickListener {
                if (!inputvalid()) {
                    return@setOnClickListener
                }

                val bundle = arguments?.let {
                    it.getSerializable("user") as LoginUser

                }

                val editBody = LoginUser(phone = bundle?.phone, password = viewModel?.newpassword?.value)
                requestTask<JSONObject>(URL_ROOT + "user/edit", method = "POST", reqBody = editBody)


                android.app.AlertDialog.Builder(view.context)
                    .setTitle(R.string.txtdialog_title_notify)
                    .setMessage(R.string.txtdialog_message_loginresetpassword)
                    .setPositiveButton(R.string.txtdialog_positive_button, onClickListener)
                    .setCancelable(false)
                    .show()
            }
        }
    }

    private fun inputvalid(): Boolean {
        var valid = true
        with(binding) {
            val newpassword = viewModel?.newpassword?.value?.trim()
            val confirmnewpassword = viewModel?.confirmnewpassword?.value?.trim()
            if (newpassword?.length ?: 0 < 6 || newpassword?.length ?: 0 > 12) {
                etNewPasswordLoginResetPassword.error = "密碼最少6個字、最多12個字"
                valid = false
            }
            if (confirmnewpassword == null || confirmnewpassword.isEmpty() || confirmnewpassword != newpassword) {
                etConfirmNewPasswordLoginResetPassword.error = "密碼不相同"
                valid = false
            }
        }
        return valid
    }
}