package com.example.thp101_team1_bagchance.controller.explore

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentExploreMainStoryBinding
import com.example.thp101_team1_bagchance.databinding.FragmentExploreStoryBinding
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreMainStory
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreMainStoryViewModel
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreStory
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreStoryViewModel

class ExploreMainStoryAdapter(private var mainstorys: List<ExploreMainStory>) :
    RecyclerView.Adapter<ExploreMainStoryAdapter.ExploreMainStoryViewHolder>() {

    /**
     * 之後這邊會寫方法後更新貼文列表
     */
    fun updatemainStorys(mainstorys: List<ExploreMainStory>) {
        this.mainstorys = mainstorys
        notifyDataSetChanged()
    }

    class ExploreMainStoryViewHolder(val itemViewBinding : FragmentExploreMainStoryBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

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
            val bitmap = BitmapFactory.decodeByteArray(mainstory.profile_pic, 0, mainstory.profile_pic.size)

//            val bitmap = BitmapFactory.decodeByteArray(itemViewBinding.viewModel?.mainstory?.value?.profile_pic, 0, 100)
            itemViewBinding.imageButton2.setImageBitmap(bitmap)
            // 將欲顯示的friend物件指派給LiveData，就會自動更新layout檔案的view顯示
            itemViewBinding.viewModel?.mainstory?.value = mainstory
            val bundle = Bundle()
//            bundle.putSerializable("mainstory", mainstory)
            itemView.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_exploreMainFragment_to_exploreStoryFragment,bundle)
            }
        }
    }
}