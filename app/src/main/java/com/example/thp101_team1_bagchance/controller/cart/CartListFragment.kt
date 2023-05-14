<<<<<<<< HEAD:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/ChatRoomFragment.kt
package com.example.thp101_team1_bagchance.controller.chat
========
package com.example.thp101_team1_bagchance.controller.cart
>>>>>>>> origin/me_Henry:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/CartListFragment.kt

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatRoomViewModel
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.viewmodel.cart.CartListViewModel

<<<<<<<< HEAD:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/ChatRoomFragment.kt
class ChatRoomFragment : Fragment() {

    companion object {
        fun newInstance() = ChatRoomFragment()
    }

    private lateinit var viewModel: ChatRoomViewModel
========
class CartListFragment : Fragment() {

    companion object {
        fun newInstance() = CartListFragment()
    }

    private lateinit var viewModel: CartListViewModel
>>>>>>>> origin/me_Henry:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/CartListFragment.kt

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<<< HEAD:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/ChatRoomFragment.kt
        return inflater.inflate(R.layout.fragment_chat_room, container, false)
========
        return inflater.inflate(R.layout.fragment_cart_list, container, false)
>>>>>>>> origin/me_Henry:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/CartListFragment.kt
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
<<<<<<<< HEAD:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/ChatRoomFragment.kt
        viewModel = ViewModelProvider(this).get(ChatRoomViewModel::class.java)
========
        viewModel = ViewModelProvider(this).get(CartListViewModel::class.java)
>>>>>>>> origin/me_Henry:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/CartListFragment.kt
        // TODO: Use the ViewModel
    }

}