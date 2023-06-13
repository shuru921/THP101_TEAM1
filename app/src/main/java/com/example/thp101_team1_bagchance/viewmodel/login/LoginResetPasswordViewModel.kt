package com.example.thp101_team1_bagchance.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thp101_team1_bagchance.LoginUser

class LoginResetPasswordViewModel : ViewModel() {
    val newpassword: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val confirmnewpassword: MutableLiveData<String> by lazy { MutableLiveData<String>() }
}