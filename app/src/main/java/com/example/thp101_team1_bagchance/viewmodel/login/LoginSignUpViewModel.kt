package com.example.thp101_team1_bagchance.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thp101_team1_bagchance.LoginUser

class LoginSignUpViewModel : ViewModel() {
    val email: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val phone: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val password: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val confirmpassword: MutableLiveData<String> by lazy { MutableLiveData<String>() }
}