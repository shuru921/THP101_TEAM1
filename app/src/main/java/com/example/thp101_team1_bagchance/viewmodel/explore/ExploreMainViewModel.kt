package com.example.thp101_team1_bagchance.viewmodel.explore


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import tw.idv.william.androidwebserver.core.service.requestTask


class ExploreMainViewModel : ViewModel() {
    val mainstorys: MutableLiveData<List<ExploreMainStory>> by lazy { MutableLiveData<List<ExploreMainStory>>() }
    private var mainstoryList = mutableListOf<ExploreMainStory>()


    init {
        loadmainStorys()
    }


    private fun loadmainStorys() {
        viewModelScope.launch {
            val url = "http://10.0.2.2:8080/bagchance/explore"
            val type = object : TypeToken<List<ExploreMainStory>>() {}.type
            val list: List<ExploreMainStory>? = requestTask(url, respBodyType = type)
            Log.d("=================","list : ${list}")
            mainstoryList.clear()
            list?.let { list1 ->
                mainstoryList.addAll(list1)
                mainstorys.value = mainstoryList


            }
        }
    }
}



