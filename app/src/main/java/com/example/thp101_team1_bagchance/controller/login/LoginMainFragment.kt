package com.example.thp101_team1_bagchance.controller.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentLoginMainBinding
import com.example.thp101_team1_bagchance.viewmodel.login.LoginMainViewModel

class LoginMainFragment : Fragment() {
    private lateinit var binding: FragmentLoginMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btLoginLoginMain.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    R.id.action_loginMainFragment_to_loginLoginFragment
                )
            }

            btSignUpLoginMain.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    R.id.action_loginMainFragment_to_loginSignUpFragment
                )
            }
        }
    }
}