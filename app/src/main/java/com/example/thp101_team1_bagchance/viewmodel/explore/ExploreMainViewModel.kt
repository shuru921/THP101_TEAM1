package com.example.thp101_team1_bagchance.viewmodel.explore


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.google.gson.reflect.TypeToken


import kotlinx.coroutines.launch


class ExploreMainViewModel : ViewModel() {
    val mainstorys: MutableLiveData<List<ExploreMainStory>> by lazy { MutableLiveData<List<ExploreMainStory>>() }
    private var mainstoryList = mutableListOf<ExploreMainStory>()

    init {

        loadmainStorys()
    }

    private fun loadmainStorys() {
            viewModelScope.launch {
                val url = "http://10.0.2.2:8080/THP101G2-WebServer-School/explore"
                val type = object : TypeToken<List<ExploreMainStory>>() {}.type
//                  fixme 要連後端加回去
//                val list: List<ExploreMainStory>? = requestTask(url, respBodyType = type)
//                mainstoryList.clear()
//                list?.let { list1 ->
//                    mainstoryList.addAll(list1)
                    mainstorys.value = mainstoryList
                }
            }
    }

    // 原始好友列表
    private var storyList = mutableListOf<ExploreStory>()

    // 受監控的LiveData，一旦指派新值就會更新好友列表畫面
    val storys: MutableLiveData<List<ExploreStory>> by lazy { MutableLiveData<List<ExploreStory>>() }

//    init {
//        loadStorys()
//    }


    /** 模擬取得遠端資料 */
//    private fun loadStorys() {
//        val storyList = mutableListOf<ExploreStory>()
//        storyList.add(ExploreStory(
//            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
//            R.drawable.ic_vip,"",
//            R.drawable.img_2))
//        storyList.add(ExploreStory(
//            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
//            R.drawable.ic_vip,"",
//            R.drawable.img_2))
//        storyList.add(ExploreStory(
//            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
//            R.drawable.ic_vip,"",
//            R.drawable.img_2))
//        storyList.add(ExploreStory(
//            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
//            R.drawable.ic_vip,"",
//            R.drawable.img_2))
//        storyList.add(ExploreStory(
//            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
//            R.drawable.ic_vip,"",
//            R.drawable.img_2))
//        storyList.add(ExploreStory(
//            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
//            R.drawable.ic_vip,"",
//            R.drawable.img_2))
//        storyList.add(ExploreStory(
//            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
//            R.drawable.ic_vip,"",
//            R.drawable.img_2))
//        storyList.add(ExploreStory(
//            R.drawable.img_1, "Ivy", "2023/01/01","我今天超帥有VIP",
//            R.drawable.ic_vip,"",
//            R.drawable.img_2))
//        this.storyList = storyList
//        this.storys.value = this.storyList
//    }
//}