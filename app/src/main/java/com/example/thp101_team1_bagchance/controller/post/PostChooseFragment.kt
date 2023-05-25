package com.example.thp101_team1_bagchance.controller.post

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentPostChooseBinding
import com.yalantis.ucrop.UCrop
import java.io.File

class PostChooseFragment : Fragment() {
    private lateinit var binding: FragmentPostChooseBinding
    private lateinit var contentUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPostChooseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btPhotographPost.setOnClickListener {
                //點擊選擇拍照按鈕
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                //宣告一個intent，打算使用拍照功能
                val file = File(requireContext().getExternalFilesDir(null), "picture.jpg")
                /*以上這行的目的是在外部文件目錄中創建一個名為 "picture.jpg" 的文件对象。
                  requireContext() 在Android 中獲取上下文（Context）的方法。
                  getExternalFilesDir(null) 用於指定特定類型的外部文件目錄。通過傳入 null，可以獲取根级外部文件目錄。
                */
                contentUri = FileProvider.getUriForFile(
                    //contentUri 是指内容URI用於標示 Android 系统中的特定類型數據，如圖像、影音、文件等。
                    requireContext(), requireContext().packageName, file
                )

                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
                //putExtra是Android 中的一个方法，用於向Intent對象添加额外的數據。
                //contentUri，表示要將拍攝的照片或影片保存到的位置。
                //MediaStore.EXTRA_OUTPUT 用於標示將要添加到 Intent 中的額外數據的目的地。
                takePictureLauncher.launch(intent)
                //用於啟動相機拍照操作的活動结果啟動器。
            }

            btPickPicturePost.setOnClickListener {
                //點擊選擇相簿
                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                //指定意圖的操作動作為 "ACTION_PICK"，表示選擇數據。
                /*MediaStore.Images.Media.EXTERNAL_CONTENT_URI：
                 指定数据的 URI，表示選擇的數據是存儲在外部存儲器中的圖像文件。
                 */
                pickPictureLauncher.launch(intent)
                /*通過調用 launch() 方法来啟動選擇圖像文件的操作，
                 並傳遞一个選擇圖像的意圖 intent 作為参数。
                 */
            }
        }
    }

    private var takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                crop(contentUri)
            }
        }
    /*
    *當拍照操作完成後，takePictureLauncher 的回調函数將被觸發。
    * 在回調函數中，檢查 result.resultCode 是否等於 Activity.RESULT_OK，以確定拍照操作是否成功。
    * 如果成功，調用 crop(contentUri) 函数對拍攝的照片進行裁剪。
    */

    private var pickPictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri -> crop(uri) }
            }
        }

    /*
    是一個註冊活動结果啟動器的函数，
    它接受一个回調函數来處理活動结果。
    在這種情况下，回調函数使用 lambda 表達式作為參数。
     */
    private fun crop(sourceImageUri: Uri) {
        val file = File(requireContext().getExternalFilesDir(null), "picture_cropped.jpg")
        val destinationUri = Uri.fromFile(file)
        val cropIntent: Intent = UCrop.of(
            sourceImageUri,
            destinationUri
        )
            .getIntent(requireContext())
        cropPictureLauncher.launch(cropIntent)
    }

    /*
    sourceImageUri 是傳入函数的原始圖像的 URI，表示要進行裁剪的圖像的位置。
    file 是创建一个用於保存裁剪後圖像的文件對象。
    回應用的外部文件目錄，然后使用該目錄和文件名 "picture_cropped.jpg" 創建一个 File 對象。
    destinationUri 是通過調用 Uri.fromFile(file) 將文件對象轉換為對應的 URI。
    */
    private var cropPictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            /*
            這段代碼是一个名為 cropPictureLauncher 的啟動器變量，
            通過 registerForActivityResult 方法註冊了一個用於處理裁剪结果的回調函数。
            然後通過 result.data?.let { intent -> ... } 的語法，檢查裁剪結果中的 data 是否為空。如果不為空，則執行下面的程式區塊。
            */
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    UCrop.getOutput(intent)?.let { uri ->

                        val bundle = Bundle()
                        bundle.putParcelable("uri", uri)
                        //bundle.put先存檔 07dataCCESS INTER

                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                            .navigate(
                                R.id.action_postChooseFragment_to_postNewContentFragment,
                                bundle
                            )
                    }
                }
            }
        }
}
