package com.example.thp101_team1_bagchance.controller.post

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.example.thp101_team1_bagchance.databinding.FragmentPostNewContentBinding
import com.example.thp101_team1_bagchance.viewmodel.post.PostNewContentViewModel

class PostNewContentFragment : Fragment() {
    private lateinit var binding: FragmentPostNewContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
       val viewModel: PostNewContentViewModel by viewModels()
        binding = FragmentPostNewContentBinding.inflate(inflater, container, false)
        //binding. = viewModel
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
