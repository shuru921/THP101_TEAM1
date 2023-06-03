package com.example.thp101_team1_bagchance.viewmodel.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.sql.Timestamp


class ChatMainViewModel : ViewModel() {
    //    完整聊天室列表 若判斷資料沒變化即回傳
    val chats = mutableListOf<SelectChat>()
    //    受監控聊天室列表 變化後回傳
    val chatlist : MutableLiveData<List<SelectChat>> by lazy { MutableLiveData<List<SelectChat>>() }
    //    假資料
    init {
        chats.add((SelectChat(1,1,2,"A", Timestamp(System.currentTimeMillis()),Timestamp(System.currentTimeMillis()),"aaa","bbb",null,null,"123",null,null)))
        chats.add((SelectChat(1,1,3,"A", Timestamp(System.currentTimeMillis()),Timestamp(System.currentTimeMillis()),"aaa","ccc",null,null, "456",null,null)))
        chats.add((SelectChat(1,1,4,"A", Timestamp(System.currentTimeMillis()),Timestamp(System.currentTimeMillis()),"aaa","ddd",null,null,"789",null,null)))
        this.chatlist.value = chats
    }

    //    設置方法搜尋時調用
    fun search(newText: String?) {
//    參數空或null把完整列表地址給受監控列表
        if (newText.isNullOrEmpty()) {
            this.chatlist.value = chats
        } else {
//    如果不是空字串，宣告新的集合，走訪每個元素的className屬性，如果符合就放到新的集合並指派
            val searchchatList = mutableListOf<SelectChat>()
//    走訪出來的是一個個聊天室 判斷搜尋條件並且不分大小寫
            for (i in chats) {
                // fixme:  inviteUidname 這裡要寫判斷 判斷自己是邀請人還是被邀請人
                if (i.inviteUidname.contains(newText,true)){
                    searchchatList.add(i)
                }
            }
//    指派給LiveData 就可以即時刷新View
            chatlist.value = searchchatList
        }
    }
}