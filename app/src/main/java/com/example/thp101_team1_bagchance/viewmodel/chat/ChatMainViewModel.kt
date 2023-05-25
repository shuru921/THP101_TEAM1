package com.example.thp101_team1_bagchance.viewmodel.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thp101_team1_bagchance.Friend
import com.example.thp101_team1_bagchance.Message

class ChatMainViewModel : ViewModel() {
    //    完整好友列表 若判斷資料沒變化即回傳
    val friends = mutableListOf<Friend>()
    //    受監控好友列表 變化後回傳
    val friendlist : MutableLiveData<List<Friend>> by lazy { MutableLiveData<List<Friend>>() }
    //    假資料
    init {
        friends.add((Friend(name = "xxx")))
        friends.add((Friend(name = "bbb")))
        friends.add((Friend(name = "aaa")))

        this.friendlist.value = friends
    }

    //    設置方法搜尋時調用
    fun search(newText: String?) {
//    參數空或null把完整列表地址給受監控列表
        if (newText.isNullOrEmpty()) {
            this.friendlist.value = friends
        } else {
//    如果不是空字串，宣告新的集合，走訪每個元素的className屬性，如果符合就放到新的集合並指派
            val searchfriendList = mutableListOf<Friend>()
//    走訪出來的是一個個好友 判斷搜尋條件並且不分大小寫
            for (i in friends) {
                if (i.name.contains(newText,true)){
                    searchfriendList.add(i)
                }
            }
//    指派給LiveData 就可以即時刷新View
            friendlist.value = searchfriendList
        }
    }
}