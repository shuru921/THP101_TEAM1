package com.example.thp101_team1_bagchance.controller.post


import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentPostNewContentBinding
import com.example.thp101_team1_bagchance.viewmodel.post.PostNewContentViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import java.io.IOException


class PostNewContentFragment : Fragment() {
    private val viewModel: PostNewContentViewModel by viewModels()
    private lateinit var binding: FragmentPostNewContentBinding
    private val myTag = "TAG_${javaClass.simpleName}"
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var lastLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000
        )
            .setMinUpdateDistanceMeters(1000f).build()
        /*
          取得高精度google map，每10公尺更新一次
         */
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                lastLocation = locationResult.lastLocation
                lastLocation?.let {
                    updateLastLocationInfo(it)
                    /*
                    onLocationResult 是 LocationCallback 类的一个回调方法，用于接收位置更新的结果。
                     */
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPostNewContentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            arguments?.let {
                // arguments即為前頁傳來的bundle，如果不為null即可取值
                val uri = it.getParcelable("uri") as Uri?
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                lyPicturePost.setImageBitmap(bitmap)
            }

          /*
            這裡要寫一個把照片存到資料庫的內容。

          */
            btPostPost.setOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(
                        R.id.action_postNewContentFragment_to_meMainFragment,

                        )
          //點擊post後跳轉到me頁面
            }

            btLocationSelectPost.setOnClickListener {
                checkLocationSettings()
                // 檢查裝置是否需要開啟Location設定

            }
        }
        viewModel.text.observe(viewLifecycleOwner) {
            binding.tvLocationPost.text = it
        }
    }

    private fun checkLocationSettings() {
        viewModel.text.value = getString(R.string.txtWait_Post)
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        // 必須將LocationRequest設定加入檢查
        val task = LocationServices.getSettingsClient(requireActivity())
            .checkLocationSettings(builder.build())
        task.addOnSuccessListener(requireActivity()) {
            // 已經開啟過並且使用者已經同意取得裝置位置，就顯示裝置最新位置
            showLastLocation()
        }
        task.addOnFailureListener { e: Exception ->
            if (e is ResolvableApiException) {
                Log.e(myTag, e.message ?: "ResolvableApiException")
                // 尚未開啟Location設定的對話視窗，就開啟該視窗
                resolutionForResult.launch(
                    IntentSenderRequest.Builder(e.resolution).build()
                )
            }
        }
    }


    private val resolutionForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            // 檢查使用者是否同意取得裝置位置(checkLocationSettings)
            if (result.resultCode == Activity.RESULT_OK) {
                showLastLocation()
                // 使用者同意就顯示裝置最新位置
            } else {
                binding.viewModel?.text?.value =
                    getString(R.string.txterrLocationAccessNotGrant_Post)
                // 使用者不同意就顯示錯誤訊息
            }
        }

    override fun onStart() {
        super.onStart()
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        // 請求使用者同意app取得裝置位置
    }

    private var requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            with(binding) {
                if (result) {
                    btLocationSelectPost.isEnabled = true
                } else {
                    binding.viewModel?.text?.value =
                        getString(R.string.txterrLocationNotFound_Post)
                    btLocationSelectPost.isEnabled = false
                }
            }
        }

    override fun onPause() {
        super.onPause()
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

    private fun showLastLocation() {
        with(binding) {
            fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                viewModel?.text?.value = getString(R.string.txterrLocationAccessNotGrant_Post)
                return
            }
            // 取得裝置位置要花點時間，先顯示需要等待的訊息
            fusedLocationClient?.lastLocation?.addOnCompleteListener { task: Task<Location> ->
                if (task.isSuccessful) {
                    lastLocation = task.result
                    lastLocation?.let {
                        updateLastLocationInfo(it)
                    }
                    fusedLocationClient?.requestLocationUpdates(
                        locationRequest, locationCallback, Looper.getMainLooper()
                        // 持續取得最新位置。Looper.getMainLooper()代表以主執行緒呼叫callback方法
                    )
                }
            }
        }
    }

    private fun reverseGeocode(latitude: Double, longitude: Double): Address? {
        val geocoder = Geocoder(requireActivity())
        var addressList: List<Address?>? = null
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1)
        } catch (e: IOException) {
            Log.e(myTag, e.toString())
        }
        return if (addressList == null || addressList.isEmpty()) {
            null
        } else {
            addressList[0]
        }
    }

    /*
    将给定的经纬度坐标转换为对应的地址信息，并返回一个 Address 对象。
    您可以根据需要使用返回的地址对象，获取地址的各个部分，如街道、城市、州/省、国家等，以进行进一步的处理和显示。
    */

    private fun updateLastLocationInfo(lastLocation: Location) {
        // reverse geocode
        reverseGeocode(lastLocation.latitude, lastLocation.longitude)?.let { addressReverse ->
            val sb = StringBuilder()
            for (i in 0..addressReverse.maxAddressLineIndex) {
                sb.append(addressReverse.getAddressLine(i)).append("\n")
            }
            binding.viewModel?.text?.value = sb.toString()

        }
    }
}






/*
   val bitmap = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
       BitmapFactory.decodeStream(
           requireContext().contentResolver.openInputStream(uri)
       )//寫在第二頁
   } else {
       val source = ImageDecoder.createSource(
           requireContext().contentResolver, uri
       )
       ImageDecoder.decodeBitmap(source)
   }

    這段程式碼是用於檢查裁剪結果是否成功以及進行圖像解碼的處理。
    首先，檢查 result.resultCode 是否等於 Activity.RESULT_OK，以確保裁剪操作成功完成。
    接著，使用 result.data?.let { intent -> ... } 的語法，檢查裁剪結果中的 data 是否為空。如果不為空，則執行下面的程式區塊。
    在程式區塊中，使用 UCrop.getOutput(intent) 取得裁剪結果的意圖 (intent)，並使用 ?.let { uri -> ... } 的語法檢查裁剪結果的 URI 是否為空。
    如果不為空，則執行下面的程式區塊。
   */
   /*if (bitmap != null) {
       binding.imageView.setImageBitmap(bitmap)
       /*這段程式碼的作用是根據裁剪結果的 URI 進行圖像解碼，
         並將解碼後的圖像資料存儲在 bitmap 變數中，以供後續使用。
        */
   } else {
       binding.imageView.setImageResource(R.drawable.no_image)
   }
*/

//有圖片即顯示，沒圖片則套用no_image圖片
