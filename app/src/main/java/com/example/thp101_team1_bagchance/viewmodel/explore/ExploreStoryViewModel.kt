package com.example.thp101_team1_bagchance.viewmodel.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thp101_team1_bagchance.R

class ExploreStoryViewModel : ViewModel() {
    val story: MutableLiveData<ExploreStory> by lazy { MutableLiveData<ExploreStory>() }


    private var commentList = mutableListOf<ExploreComment>()
    // 受監控的LiveData，一旦指派新值就會更新好友列表畫面
    val commemts: MutableLiveData<List<ExploreComment>> by lazy { MutableLiveData<List<ExploreComment>>() }

    init {
        loadComments()
    }
    private fun loadComments() {
        val commentList = mutableListOf<ExploreComment>()
        commentList.add(ExploreComment(
            R.drawable.ic_vip, "Ivy","容哥好帥"
        ))
        commentList.add(ExploreComment(
            R.drawable.ic_vip, "Ivy","容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師容哥好師"
        ))
        commentList.add(ExploreComment(
            R.drawable.ic_vip, "Ivy","容哥太帥"
        ))
        commentList.add(ExploreComment(
            R.drawable.ic_vip, "Ivy","容哥太師"
        ))
        commentList.add(ExploreComment(
            R.drawable.ic_vip, "Ivy","容哥好帥"
        ))
        this.commentList = commentList
        this.commemts.value = this.commentList
    }
}