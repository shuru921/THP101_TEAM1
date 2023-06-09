package com.example.thp101_team1_bagchance.controller.explore

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentExploreMainStoryBinding

import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreMainStory
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreMainStoryViewModel


import java.util.*

class ExploreMainStoryAdapter(private var mainstorys: List<ExploreMainStory>) :
    RecyclerView.Adapter<ExploreMainStoryAdapter.ExploreMainStoryViewHolder>() {

    /**
     * 之後這邊會寫方法後更新貼文列表
     */
    fun updatemainStorys(mainStorys: List<ExploreMainStory>) {
        mainstorys = mainStorys
        notifyDataSetChanged()
    }

    class ExploreMainStoryViewHolder(val itemViewBinding: FragmentExploreMainStoryBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root){
        var currentPicIndex = 0
        }

    override fun getItemCount(): Int {
        return mainstorys.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreMainStoryViewHolder {
        val itemViewBinding = FragmentExploreMainStoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        itemViewBinding.viewModel = ExploreMainStoryViewModel()
        // 設定lifecycleOwner方能監控LiveData資料變化，layout檔案的view才會更新顯示
        itemViewBinding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return ExploreMainStoryViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ExploreMainStoryViewHolder, position: Int) {
        val mainstory = mainstorys[position]


        with(holder) {
            val bitmap =
                BitmapFactory.decodeByteArray(mainstory.profile_pic, 0, mainstory.profile_pic.size)
            itemViewBinding.ExploreimageButtonpic.setImageBitmap(bitmap)
            val bitmapstorypic =
                BitmapFactory.decodeByteArray(mainstory.pic, 0, mainstory.pic.size)
            itemViewBinding.StoryimageView.setImageBitmap(bitmapstorypic)

//            val picList = mainstory.pic
//            // 等以後可以選多張照片再用
//            // 判断图片列表是否为空且有图片数据
//            if (picList != null && picList.isNotEmpty()) {
//                // 设置当前显示的图片
//                val currentPicBytes = picList[currentPicIndex]
//                val bitmap = BitmapFactory.decodeByteArray(currentPicBytes, 0, currentPicBytes.size)
//                itemViewBinding.StoryimageView.setImageBitmap(bitmap)
//                itemViewBinding.StoryimageView.visibility = View.VISIBLE
//
//                // 点击 nextimageButton 切换到下一张图片
//                itemViewBinding.nextimageButton.setOnClickListener {
//                    currentPicIndex = (currentPicIndex + 1) % picList.size
//                    val nextPicBytes = picList[currentPicIndex]
//                    val nextBitmap = BitmapFactory.decodeByteArray(nextPicBytes, 0, nextPicBytes.size)
//                    itemViewBinding.StoryimageView.setImageBitmap(nextBitmap)
//                }
//
//                // 点击 backimageButton 切换到上一张图片
//                itemViewBinding.backimageButton.setOnClickListener {
//                    currentPicIndex = (currentPicIndex - 1 + picList.size) % picList.size
//                    val previousPicBytes = picList[currentPicIndex]
//                    val previousBitmap = BitmapFactory.decodeByteArray(previousPicBytes, 0, previousPicBytes.size)
//                    itemViewBinding.StoryimageView.setImageBitmap(previousBitmap)
//                }
//            } else {
//                itemViewBinding.StoryimageView.visibility = View.GONE
//            }

            // 將欲顯示的mainstory物件指派給LiveData，就會自動更新layout檔案的view顯示
            itemViewBinding.viewModel?.mainstory?.value = mainstory

            val bundle = Bundle()
            bundle.putSerializable("mainstory", mainstory)
            Log.d("xxxxxxxxxxxxxxxxxx","mainstory.value?.id : ${mainstory.id}")
            itemView.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_exploreMainFragment_to_exploreStoryFragment,bundle)

            }
        }
    }
}