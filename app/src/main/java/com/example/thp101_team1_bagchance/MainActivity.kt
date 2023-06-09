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
import com.example.thp101_team1_bagchance.databinding.ActivityUserBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var newsReceiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // 頁面切換可以整合BottomNavigationView功能
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navHostFragment.navController
        )

        newsReceiver = NewsReceiver()
        registerNewsReceiver()
    }

    private fun registerNewsReceiver() {
        val intentFilter = IntentFilter("action_ message")
        LocalBroadcastManager.getInstance(this).registerReceiver(newsReceiver, intentFilter)
    }

    private inner class NewsReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val news = intent.extras?.getString("newMessage")
//            fixme 攔截到時連結後端更新資料
        }
    }
}