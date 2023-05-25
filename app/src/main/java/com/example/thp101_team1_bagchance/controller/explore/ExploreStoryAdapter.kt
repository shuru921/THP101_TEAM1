package com.example.thp101_team1_bagchance.controller.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.databinding.FragmentExploreMainBinding
import com.example.thp101_team1_bagchance.databinding.FragmentExploreStoryBinding
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreStory
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreStoryViewModel

class ExploreStoryAdapter(private var storys: List<ExploreStory>) :
    RecyclerView.Adapter<ExploreStoryAdapter.ExploreStoryViewHolder>() {

    /**
     * 之後這邊會寫方法後更新貼文列表
     */
    fun updateStorys(storys: List<ExploreStory>) {
        this.storys = storys
        notifyDataSetChanged()
    }

    class ExploreStoryViewHolder(val itemViewBinding : FragmentExploreStoryBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    override fun getItemCount(): Int {
        return storys.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreStoryViewHolder {
        val itemViewBinding = FragmentExploreStoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        itemViewBinding.viewModel = ExploreStoryViewModel()
        // 設定lifecycleOwner方能監控LiveData資料變化，layout檔案的view才會更新顯示
        itemViewBinding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return ExploreStoryViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ExploreStoryViewHolder, position: Int) {
        val story = storys[position]
        with(holder) {
            // 將欲顯示的friend物件指派給LiveData，就會自動更新layout檔案的view顯示
            itemViewBinding.viewModel?.story?.value = story
            val bundle = Bundle()
            bundle.putSerializable("story", story)
            itemView.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_exploreMainFragment_to_exploreStoryFragment)
            }
        }
    }
}