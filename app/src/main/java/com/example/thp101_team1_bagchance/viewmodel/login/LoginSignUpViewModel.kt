package com.example.thp101_team1_bagchance.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thp101_team1_bagchance.LoginUser

class LoginSignUpViewModel : ViewModel() {
    val user: MutableLiveData<LoginUser> by lazy { MutableLiveData<LoginUser>() }
    val confirmpassword: MutableLiveData<String> by lazy { MutableLiveData<String>() }
}