package com.example.thp101_team1_bagchance.controller.chat


import android.Manifest
import android.app.Activity
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentChatRoomBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.*
import com.example.thp101_team1_bagchance.controller.explore.requestTask
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
//      紀錄權限拒絕次數
var count: Int = 0
class ChatRoomFragment : Fragment(), OnTouchListener {
    private lateinit var binding: FragmentChatRoomBinding
    //    接收前景推播
    private lateinit var newsReceiver: BroadcastReceiver
    //    存錄音檔位置名稱
    private val fileName = "sample.3gp"
    // fixme   private var mediaPlayer: MediaPlayer? = null 還沒寫播放
    private var mediaRecorder: MediaRecorder? = null
    //    紀錄錄音狀態
    private var recordGranted = false
    //    錄音檔路徑
    private lateinit var file: File
    //    錄音計時
    private var durationInSeconds = 0
    //    執行續(隨錄音狀態改變)
    private var job: Job? = null
    //    指定根目錄創建資料夾
    private lateinit var camerafile: File
    //    路徑(?)
    private lateinit var contentUri: Uri
    private val myTag = "myTag_${javaClass.simpleName}"
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
//        把adapter 索引裡的邀請被邀請人取出 在去後端抓詳細資料
        arguments?.let {
            it.getSerializable("chatmaterial_inuid")?.let {
                val inuid = it as Int
                arguments?.let {
                    it.getSerializable("chatmaterial_beuid")?.let {
                        val beuid = it as Int
                        val gson = GsonBuilder()
                            .setDateFormat("MMM d, yyyy, h:mm:ss a")
                            .create()
                        Log.d( myTag ,"inuid: ${inuid}")
                        Log.d( myTag ,"beuid: ${beuid}")

                        //              帶入暱稱 頭貼
                        binding.viewModel?.chatmaterial?.value = gson.fromJson(
//                          fixme  這邊是抓user(有空要改成restful 後端要改在doget 並switch判斷)
                            requestTask<JsonObject>(
                                "http://10.0.2.2:8080/bag-chance/web/ChatController/" + "${inuid}" + "/" + "${beuid}",
                                method = "DELETE"
                            ), SelectChat::class.java
                        )
                        Log.d( myTag ,"chatmaterial: ${binding.viewModel?.chatmaterial?.value}")
                    }
                }
            }
        }

//        設定聊天室對方頭像 姓名
        if (binding.viewModel?.chatmaterial?.value?.inviteUid == binding.viewModel?.user?.id) {
            binding.tvNameChat.text =
                binding.viewModel?.chatmaterial?.value?.beInvitedUidname
        } else {
            binding.tvNameChat.text = binding.viewModel?.chatmaterial?.value?.inviteUidname
        }
        val byteArray: ByteArray?
        val options = BitmapFactory.Options()
        options.inSampleSize = 3 // 将inSampleSize设置为3，表示将图像尺寸缩小为原来的1/3
        if (binding.viewModel?.chatmaterial?.value?.inviteUid == binding.viewModel?.user?.id) {
            binding.viewModel?.chatmaterial?.value?.beInvitedUidpic?.let {
                byteArray = binding.viewModel?.chatmaterial?.value?.beInvitedUidpic
                val bitmap =
                    BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size, options)
                binding.ivRoomAvatarChat.setImageBitmap(bitmap)
            }
        } else {
            binding.viewModel?.chatmaterial?.value?.inviteUidpic?.let {
                byteArray = binding.viewModel?.chatmaterial?.value?.inviteUidpic
                val bitmap =
                    BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size, options)
                binding.ivRoomAvatarChat.setImageBitmap(bitmap)
            }
        }

        //                攔截推播 並調用方法
        newsReceiver = NewsReceiver()
        registerNewsReceiver()


        with(binding) {
//            設定垂直布局
            rvMessageChat.layoutManager = LinearLayoutManager(requireContext())
//            監控聊天室訊息變化
            viewModel?.messagelist?.observe(viewLifecycleOwner) {
                if (rvMessageChat.adapter == null) {
                    rvMessageChat.adapter = ChatRoomAdapter(it)
                } else {
                    (rvMessageChat.adapter as ChatRoomAdapter).update(it)
//                     获取 RecyclerView 的 LayoutManager 对象
                    val layoutManager = rvMessageChat.layoutManager

//            滚动到最后一个项的位置
                    val lastPosition = ChatRoomAdapter(it).itemCount - 1

//            使用 LayoutManager 对象滚动 RecyclerView
                    if (layoutManager is LinearLayoutManager) {
                        layoutManager.scrollToPosition(lastPosition)
                    }
                }
            }
//            相機相簿 bottom_sheet
            ivPhotoChat.setOnClickListener {
                val bottomSheetBehavior = BottomSheetBehavior.from(included2.photobottomSheet)
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
//                相機
                included2.tvcameraChat.setOnClickListener {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    camerafile = File(requireContext().getExternalFilesDir(null), "picture.jpg")
                    // Android 7開始，指定拍照存檔路徑要改使用FileProvider
                     contentUri = FileProvider.getUriForFile(
                        requireContext(), requireContext().packageName, camerafile
                    )
                    // 拍照前指定存檔路徑就可取得原圖而非縮圖
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
                    Log.d(
                        "myTag_${javaClass.simpleName}",
                        "included2.tvalbumChat.setOnClickListener"
                    )
                    takePictureLargeLauncher.launch(intent)
                    Log.d(
                        "myTag_ ${javaClass.simpleName}",
                        " takePictureLargeLauncher.launch(intent)"
                    )
                }
//                    相簿
                included2.tvalbumChat.setOnClickListener {
                    val intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    Log.d(
                        "myTag_${javaClass.simpleName}",
                        "included2.tvalbumChat.setOnClickListener"
                    )

                    pickPictureLauncher.launch(intent)
                    Log.d("myTag_${javaClass.simpleName}", "pickPictureLauncher.launch(intent)")
                }


//                  錄音
                ivRecordingChat.setOnClickListener {
                    if (recordGranted == false) return@setOnClickListener
                    val bottomSheetBehavior = BottomSheetBehavior.from(included.bottomSheet)
                    if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                    requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
//                設定長點擊錄音 放開就結束錄音
                    included.ivRecordChat.setOnTouchListener(this@ChatRoomFragment)
                }
            }
        }
//        一進入聊天室先載入訊息
        binding.viewModel?.getNewMessage()
//        清空避免重複訊息
        binding.viewModel?.messagelist?.value?.toMutableList()?.clear()
    }

        override fun onStart() {
            super.onStart()
//        啟動requestPermissionLauncher 向使用者申請權限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            // FIXME: 拒絕兩次後 android內建不再詢問 要寫判斷並跳至設定頁提醒使用者
//            if (recordGranted == false) {
//                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
//                count++
//                println(count)
//                if (count >= 3) {
//                    AlertDialog.Builder(view?.context)
//                        // 設定標題文字
//                        .setTitle(R.string.txtauthorized)
//                        // 設定訊息文字
//                        .setMessage(R.string.txtrequestauthorization)
//                        // 設定positive, negative, neutral按鈕上面的文字與點擊事件監聽器
//                        .setPositiveButton(R.string.txtyes, onClickListener)
//                        .setNegativeButton(R.string.txtno, onClickListener)
//                        .setNeutralButton(R.string.txtCancel, onClickListener)
//                        // false代表要點擊按鈕方能關閉，預設為true
//                        .setCancelable(true)
//                        .show()
//                    count = 0
//                }
//            }
        }

//          錄音拒絕提醒的Dialog
//    val onClickListener = DialogInterface.OnClickListener { dialog, which ->
//        val text = when (which) {
//            AlertDialog.BUTTON_POSITIVE -> {
//                getString(R.string.txtyes)
//                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                startActivity(intent)
//            }
//            AlertDialog.BUTTON_NEGATIVE -> getString(R.string.txtno)
//            AlertDialog.BUTTON_NEUTRAL -> getString(R.string.txtCancel)
//            else -> ""
//        }
//        dialog.cancel()
//    }

    //        錄音請求權限
        private var requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                recordGranted = if (result) true
                else {
                    Toast.makeText(requireContext(), R.string.txtrecording, Toast.LENGTH_SHORT)
                        .show()
                    false
                }
            }

        //          設定長按錄音及放開結束
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
                            // fixme: 发送录音至聊天室
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
                mediaRecorder!!.start()
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

        //          計時錄音秒數刷新方法
        private fun updateDurationTextView() {
            val minutes = durationInSeconds / 60
            val seconds = durationInSeconds % 60
            val durationText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

//          併回UI線程(主線程)
            activity?.runOnUiThread {
                binding.included.tvTimeChat.text = durationText
            }
        }

//        相機啟動
        private var takePictureLargeLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                Log.d("myTag_${javaClass.simpleName}", "takePictureLargeLauncher")

                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d(
                        "myTag_${javaClass.simpleName}",
                        "result.resultCode == Activity.RESULT_OK"
                    )
                    crop(contentUri)
                    val test: ChatMessage
                    val options = BitmapFactory.Options()
                    options.inSampleSize = 3
                    val bitmap = BitmapFactory.decodeFile(camerafile.absolutePath)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                    val byteArray = byteArrayOutputStream.toByteArray()
                    if (binding.viewModel?.chatmaterial?.value!!.inviteUid == binding.viewModel?.user?.id) {
                        test = ChatMessage(
                            chatId = binding.viewModel?.chatmaterial?.value!!.id,
                            sendUid = binding.viewModel?.user?.id!!,
                            picture = byteArray
                        )
                    } else {
                        test = ChatMessage(
                            chatId = binding.viewModel?.chatmaterial?.value!!.id,
                            sendUid = binding.viewModel?.chatmaterial?.value!!.beInvitedUid,
                            picture = byteArray
                        )
                    }
                    requestTask<JsonObject>(
                        "http://10.0.2.2:8080/bag-chance/web/ChatMessageController/",
                        method = "POST",
                        reqBody = test
                    )

                    toFcm()
                }
            }

//            相簿啟動
        private var pickPictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                Log.d("myTag_${javaClass.simpleName}", "result: $result")

                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d(
                        "myTag_${javaClass.simpleName}",
                        "result.resultCode == Activity.RESULT_OK"
                    )
                    result.data?.data?.let {
                        Log.d("myTag${javaClass.simpleName}", "result ! null")
                        crop(it)
                        val test: ChatMessage
                        val options = BitmapFactory.Options()
                        options.inSampleSize = 1

                        val inputStream = requireActivity().contentResolver.openInputStream(it)
                        val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                        inputStream?.close()
                        val byteArrayOutputStream = ByteArrayOutputStream()

                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                        val byteArray = byteArrayOutputStream.toByteArray()
                        if (binding.viewModel?.chatmaterial?.value!!.inviteUid == binding.viewModel?.user?.id) {
                            test = ChatMessage(
                                chatId = binding.viewModel?.chatmaterial?.value!!.id,
                                sendUid = binding.viewModel?.user?.id!!,
                                picture = byteArray
                            )
                        } else {
                            test = ChatMessage(
                                chatId = binding.viewModel?.chatmaterial?.value!!.id,
                                sendUid = binding.viewModel?.chatmaterial?.value!!.beInvitedUid,
                                picture = byteArray
                            )
                        }
                        requestTask<JsonObject>(
                            "http://10.0.2.2:8080/bag-chance/ChatMessageController/",
                            method = "POST",
                            reqBody = test
                        )

                        toFcm()
                    }
                }
            }

//         裁切
        private fun crop(sourceImageUri: Uri) {
    // TODO: 尚未裁切就送出了 
            Log.d("myTag${javaClass::getSimpleName}", "crop")
            val file = File(requireContext().getExternalFilesDir(null), "picture_cropped.jpg")
            val destinationUri = Uri.fromFile(file)
            val cropIntent: Intent = UCrop.of(
                sourceImageUri,
                destinationUri
            )
                .getIntent(requireContext())
            cropPictureLauncher.launch(cropIntent)
        }
//        裁切啟動
    private var cropPictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        }
//          註冊攔截意圖
    private fun registerNewsReceiver() {
        val intentFilter = IntentFilter("action_ message")
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(newsReceiver, intentFilter)
    }

    private inner class NewsReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val news = intent.extras?.getString("newMessage")
//             攔截到時連結後端更新資料
            Log.d("myTag_${javaClass.simpleName}","onReceive")
            binding.viewModel?.getNewMessage()
        }
    }
//        發送推播
    private fun toFcm () {
        // TODO: 只能單向待處理
        val toMail = if (binding.viewModel?.chatmaterial?.value?.inviteUid == binding.viewModel?.user?.id) {
            binding.viewModel?.chatmaterial?.value?.beInvitedUidMail
        }else {
            binding.viewModel?.chatmaterial?.value?.invitedUidMail
        }
        val jsonObject = JsonObject()
        jsonObject.addProperty("action", "singleFcm")
        jsonObject.addProperty("title", "您有新訊息")
        jsonObject.addProperty("body", "快來看看是誰吧!")
//        Log.d("================","${chatmaterial?.value?.beInvitedUidMail}") ##null
//        Log.d("================","${chatmaterial?.value?.invitedUidMail}")
        jsonObject.addProperty("toMail", toMail )
        requestTask<JsonObject>("http://10.0.2.2:8080/bag-chance/fcm/", method = "POST", reqBody = jsonObject)
//        收到推播立刻更新資料
        binding.viewModel?.getNewMessage()
    }
}






