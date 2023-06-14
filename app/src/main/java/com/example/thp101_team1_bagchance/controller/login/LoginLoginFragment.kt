package com.example.thp101_team1_bagchance.controller.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.thp101_team1_bagchance.LoginUser
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.UserActivity
import com.example.thp101_team1_bagchance.core.service.requestTask
import com.example.thp101_team1_bagchance.core.util.URL_ROOT
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
            viewModel?.email?.observe(viewLifecycleOwner) {
                inputvalid()
            }

            viewModel?.password?.observe(viewLifecycleOwner) {
                inputvalid()
            }

            val onClickListener = DialogInterface.OnClickListener { dialog, which ->
                val buttonText = when (which) {
                    AlertDialog.BUTTON_POSITIVE -> getString(R.string.txtdialog_positive_button)
                    else -> ""
                }

                dialog.cancel()

                if (which == AlertDialog.BUTTON_POSITIVE) {
                    dialog.dismiss()
                }
            }

            btOKLoginLogin.setOnClickListener {
                if (!inputvalid()) {
                    return@setOnClickListener
                }
                val login = requestTask<LoginUser>(
                    url = URL_ROOT + "user/login/${viewModel?.email?.value}/${viewModel?.password?.value}",
                    respBodyType = LoginUser::class.java
                )

                if (login == null) {
                    android.app.AlertDialog.Builder(view.context)
                        .setTitle(R.string.txtdialog_title_notify)
                        .setMessage(R.string.txtdialog_message_loginlogin)
                        .setPositiveButton(R.string.txtdialog_positive_button, onClickListener)
                        .setCancelable(false)
                        .show()
                } else {
                    val intent = Intent(requireActivity(), UserActivity::class.java)
                    startActivity(intent)
                }
            }
            btForgetPasswordLoginLogin.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    R.id.action_loginLoginFragment_to_loginForgetPasswordFragment
                )
            }
            btPreviousPageLoginLogin.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    R.id.action_loginLoginFragment_to_loginMainFragment
                )
            }
        }
    }
    private fun inputvalid(): Boolean {
        var valid = true
        with(binding) {
            val email = viewModel?.email?.value?.trim()
            val password = viewModel?.password?.value?.trim()
            if (email == null || email.isEmpty()) {
                etPhoneLoginLogin.error = "信箱不可空白"
                valid = false
            }
            if (password?.length ?: 0 < 6 || password?.length ?: 0 > 12) {
                etPasswordLoginLogin.error = "密碼最少6個字、最多12個字"
                valid = false
            }
        }
        return valid
    }
}