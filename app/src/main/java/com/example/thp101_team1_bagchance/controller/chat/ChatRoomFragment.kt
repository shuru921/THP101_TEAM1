package com.example.thp101_team1_bagchance.controller.chat

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thp101_team1_bagchance.Friend
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentChatRoomBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.File
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
            }
            ivRecordingChat.setOnClickListener {
                val bottomSheetBehavior = BottomSheetBehavior.from(included.bottomSheet)
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
//                設定長點擊錄音 放開就結束錄音

                 included.ivRecordChat.setOnTouchListener(this@ChatRoomFragment)
                    // TODO:  送出

            }
            ivSendChat.setOnClickListener {
                // TODO: 要把資料傳給資料庫 並且傳入對話框內
            }
        }
    }

    override fun onStart() {
        super.onStart()
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    private var requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            recordGranted = if (result) true
            else {
//                還沒加入String
                Toast.makeText(requireContext(),"請授權", Toast.LENGTH_SHORT).show()
                false
            }
        }
//          設定長按
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


    private fun startRecordingTimer() {
        durationInSeconds = 0
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                durationInSeconds++
                updateDurationTextView()
            }
        }, 1000, 1000)
    }

    private fun stopRecordingTimer() {
        timer?.cancel()
        timer?.purge()
    }

    private fun updateDurationTextView() {
        val minutes = durationInSeconds / 60
        val seconds = durationInSeconds % 60
        val durationText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

        Handler(Looper.getMainLooper()).post {
            binding.included.tvTimeChat.text = durationText
        }
        activity?.runOnUiThread {
            binding.included.tvTimeChat.text = durationText
        }
    }
}

