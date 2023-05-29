package com.example.thp101_team1_bagchance.viewmodel.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thp101_team1_bagchance.R

class ExploreMainStoryViewModel : ViewModel() {
    val mainstory: MutableLiveData<ExploreMainStory> by lazy { MutableLiveData<ExploreMainStory>() }

    private var mainstoryList = mutableListOf<ExploreMainStory>()
    val mainstorys: MutableLiveData<List<ExploreMainStory>> by lazy { MutableLiveData<List<ExploreMainStory>>() }


    init {
        loadmainStorys()
    }



    /** 模擬取得遠端資料 */
    private fun loadmainStorys() {
        val mainstoryList = mutableListOf<ExploreMainStory>()
        mainstoryList.add(ExploreMainStory(
            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
            R.drawable.ic_vip))
        mainstoryList.add(ExploreMainStory(
            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
            R.drawable.ic_vip))
        mainstoryList.add(ExploreMainStory(
            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
            R.drawable.ic_vip))
        mainstoryList.add(ExploreMainStory(
            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
            R.drawable.ic_vip))
        mainstoryList.add(ExploreMainStory(
            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
            R.drawable.ic_vip))
        mainstoryList.add(ExploreMainStory(
            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
            R.drawable.ic_vip))
        this.mainstoryList = mainstoryList
        this.mainstorys.value = this.mainstoryList
        }
}
