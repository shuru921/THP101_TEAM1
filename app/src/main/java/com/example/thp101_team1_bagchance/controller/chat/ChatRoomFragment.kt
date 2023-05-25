package com.example.thp101_team1_bagchance.controller.chat

import android.Manifest
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thp101_team1_bagchance.Friend
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentChatRoomBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.*
import java.io.File
import java.lang.Runnable
import java.util.*


class ChatRoomFragment : Fragment(),  View.OnTouchListener {
    private lateinit var binding: FragmentChatRoomBinding
    private val fileName = "sample.3gp"
    private var mediaRecorder: MediaRecorder? = null
//    private var mediaPlayer: MediaPlayer? = null
    private var recordGranted = false
    private lateinit var file: File
    private val timer: Timer? = null
    private var durationInSeconds = 0
    private var job: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val viewModel: ChatRoomViewModel by viewModels()
        binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            it.getSerializable("friend")?.let {

//              把聊天室基本資料傳入
                binding.viewModel?.friend?.value = it as Friend
            }
        }

        with(binding) {
            rvMessageChat.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.messagelist?.observe(viewLifecycleOwner) {
                if (rvMessageChat.adapter == null) {
                    rvMessageChat.adapter = ChatRoomAdapter(it)
                } else {
                    (rvMessageChat.adapter as ChatRoomAdapter).update(it)
                }
            }

            ivPhotoChat.setOnClickListener {
                // TODO: 照片或拍照得下方彈出篩選 一樣對話框得加入格式(縮圖)
                val bottomSheetBehavior = BottomSheetBehavior.from(included2.photobottomSheet)
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
                // TODO: bottomSheet String還沒加 
                included2.tvcameraChat.setOnClickListener {  }

                included2.tvalbumChat.setOnClickListener {  }
            }
            ivRecordingChat.setOnClickListener {
                val bottomSheetBehavior = BottomSheetBehavior.from(included.bottomSheet)
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
//                設定長點擊錄音 放開就結束錄音
                 included.ivRecordChat.setOnTouchListener(this@ChatRoomFragment)
            }
            ivSendChat.setOnClickListener {
                // TODO: 要把資料傳給資料庫 並且傳入對話框內
            }
        }
    }

    override fun onStart() {
        super.onStart()
//        啟動requestPermissionLauncher 向使用者申請權限
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    private var requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            recordGranted = if (result) true
            else {
                // TODO: 還沒加入String 
                Toast.makeText(requireContext(),"請授權", Toast.LENGTH_SHORT).show()
                false
            }
        }
//          設定長按及放開
override fun onTouch(v: View?, event: MotionEvent?): Boolean {
    when (v?.id) {
        R.id.ivRecord_Chat -> {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    startRecording()
                    startRecordingTimer()
                }
                MotionEvent.ACTION_UP -> {
                    stopRecording()
                    stopRecordingTimer()
                    // TODO: 发送录音至聊天室
                }
            }
        }
    }
    return true
}
//          開始錄音
    private fun startRecording() {

        // 开始录音
        val dir = requireContext().getExternalFilesDir(null)
        file = File(dir, fileName)

        if (mediaRecorder == null) {
            // API 31 MediaPlayer()列為deprecated，要改呼叫MediaRecorder(context)
            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(requireContext())
            } else {
                MediaRecorder()
            }
        } else {
            mediaRecorder!!.reset()
        }
        try {
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder!!.setOutputFile(file.path)

            mediaRecorder!!.prepare()
            mediaRecorder!!.start() // 开始录音
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//          停止錄音
    private fun stopRecording() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder!!.stop() // 停止录音
            }
            mediaRecorder?.reset()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            // TODO: 处理 IllegalStateException 异常
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mediaRecorder?.release()
        mediaRecorder = null
    }

//          錄音介面計時器


    private fun startRecordingTimer() {
        durationInSeconds = 0
        // 创建一个 Job 对象用于管理协程的生命周期
        job = CoroutineScope(Dispatchers.Main).launch {
            // 在协程作用域中执行代码块
            while (isActive) {
                // 延迟 1 秒钟
                delay(1000)
                // 每秒钟递增 durationInSeconds 的值
                durationInSeconds++
                // 更新录音计时器的显示
                updateDurationTextView()
            }
        }
    }

    private fun stopRecordingTimer() {
        // 取消协程的执行
        job?.cancel()
        // 将 job 设置为 null，表示协程已经停止
        job = null
    }

    //          textview刷新方法
    private fun updateDurationTextView() {
        val minutes = durationInSeconds / 60
        val seconds = durationInSeconds % 60
        val durationText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

//          併回UI線程
        activity?.runOnUiThread {
            binding.included.tvTimeChat.text = durationText
        }
    }
}

