package com.example.thp101_team1_bagchance.controller.explore

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.thp101_team1_bagchance.databinding.FragmentExploreStoryCommentBinding
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreComment
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreStoryCommentViewModel

class ExploreStoryCommentAdapter (private var comments: List<ExploreComment>) :
    RecyclerView.Adapter<ExploreStoryCommentAdapter.ExploreCommentViewHolder>() {

    /**
     * 之後這邊會寫方法後更新留言列表
     */
    fun updatecomments(comments: List<ExploreComment>) {
        this.comments = comments
        notifyDataSetChanged()
    }

    class ExploreCommentViewHolder(val itemViewBinding : FragmentExploreStoryCommentBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreCommentViewHolder {
        val itemViewBinding = FragmentExploreStoryCommentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        itemViewBinding.viewModel = ExploreStoryCommentViewModel()
        // 設定lifecycleOwner方能監控LiveData資料變化，layout檔案的view才會更新顯示
        itemViewBinding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return ExploreCommentViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ExploreCommentViewHolder, position: Int) {
        val comment = comments[position]
        with(holder) {
            val bitmapcommentuserpic =
                BitmapFactory.decodeByteArray(comment.profile_pic, 0, comment.profile_pic.size)
            itemViewBinding.btcommentuserpic.setImageBitmap(bitmapcommentuserpic)
            // 將欲顯示的friend物件指派給LiveData，就會自動更新layout檔案的view顯示
            itemViewBinding.viewModel?.comment?.value = comment
        }
    }

}