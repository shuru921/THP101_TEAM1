package com.example.thp101_team1_bagchance.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thp101_team1_bagchance.LoginUser

class LoginForgetPasswordViewModel : ViewModel() {
    val phone: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val verificationcode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
}