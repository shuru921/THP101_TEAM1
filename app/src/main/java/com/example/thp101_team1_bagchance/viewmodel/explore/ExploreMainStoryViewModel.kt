package com.example.thp101_team1_bagchance.viewmodel.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thp101_team1_bagchance.R
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import tw.idv.william.androidwebserver.core.service.requestTask
import java.util.*

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
        val url = "http://10.0.2.2:8080/THP101G2-WebServer-School/explore"
        val type = object : TypeToken<List<ExploreMainStory>>(){}.type
        val list = requestTask<List<ExploreMainStory>>(url, respBodyType = type)
//        val byteArray = list!![0].profile_pic
//        println(Arrays.toString(byteArray))

        mainstoryList.clear()
        list?.let { list1 ->
            mainstoryList.addAll(list1)
            mainstorys.value = mainstoryList
        }
        }
}
