package com.example.thp101_team1_bagchance

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.thp101_team1_bagchance.databinding.ActivityLoginBinding
import com.example.thp101_team1_bagchance.databinding.ActivityUserBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        // 頁面切換可以整合BottomNavigationView功能
//        NavigationUI.setupWithNavController(
//            binding.bottomNavigationView,
//            navHostFragment.navController
//        )
    }


}