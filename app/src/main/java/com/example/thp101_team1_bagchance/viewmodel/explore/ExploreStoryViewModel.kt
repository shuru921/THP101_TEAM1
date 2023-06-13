package com.example.thp101_team1_bagchance.viewmodel.explore

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.example.thp101_team1_bagchance.controller.explore.requestTask

class ExploreStoryViewModel : ViewModel() {
    val mainstory: MutableLiveData<ExploreMainStory> by lazy { MutableLiveData<ExploreMainStory>() }
    val comment: MutableLiveData<ExploreComment> by lazy { MutableLiveData<ExploreComment>() }
    private var commentList = mutableListOf<ExploreComment>()
    val comments: MutableLiveData<List<ExploreComment>> by lazy { MutableLiveData<List<ExploreComment>>() }
    val result :MutableLiveData<String> by lazy { MutableLiveData() }
    var inputText = ObservableField<String>("")


//    fun addcomment() {
//
//        val url = "http://10.0.2.2:8080/bagchance/comment"
//        val jsonObject = JsonObject()
//        jsonObject.addProperty("uid", xuid.toInt())
//        jsonObject.addProperty("story_id", mainstory.value?.id)
//        jsonObject.addProperty("comment", inputText.get()!!)
//        val respBody = requestTask<JsonObject>(url, "POST", jsonObject)
//        result.value =respBody?.get("message")?.asString
//
//
//    }

    fun findcomment() {
        val url = "http://10.0.2.2:8080/bagchance/comment/${mainstory.value?.id}"
        val type = object : TypeToken<List<ExploreComment>>() {}.type
        val list: List<ExploreComment>? = requestTask(url, respBodyType = type)
        Log.d("=================","mainstory.value?.id : ${mainstory.value?.id}")
        commentList.clear()
        list?.let { list2 ->
            commentList.addAll(list2)
            Log.d("fuuuuuuuuuuuuuuuk","${commentList[0].profile_pic}")
            comments.value = commentList
        }
    }
}




