<<<<<<<< HEAD:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/ChatMainFragment.kt
package com.example.thp101_team1_bagchance.controller.chat
========
package com.example.thp101_team1_bagchance.controller.cart
>>>>>>>> origin/me_Henry:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/CartPayFragment.kt

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMainViewModel
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.viewmodel.cart.CartPayViewModel

<<<<<<<< HEAD:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/ChatMainFragment.kt
class ChatMainFragment : Fragment() {

    companion object {
        fun newInstance() = ChatMainFragment()
    }

    private lateinit var viewModel: ChatMainViewModel
========
class CartPayFragment : Fragment() {

    companion object {
        fun newInstance() = CartPayFragment()
    }

    private lateinit var viewModel: CartPayViewModel
>>>>>>>> origin/me_Henry:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/CartPayFragment.kt

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<<< HEAD:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/ChatMainFragment.kt
        return inflater.inflate(R.layout.fragment_chat_main, container, false)
========
        return inflater.inflate(R.layout.fragment_cart_pay, container, false)
>>>>>>>> origin/me_Henry:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/CartPayFragment.kt
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
<<<<<<<< HEAD:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/ChatMainFragment.kt
        viewModel = ViewModelProvider(this).get(ChatMainViewModel::class.java)
========
        viewModel = ViewModelProvider(this).get(CartPayViewModel::class.java)
>>>>>>>> origin/me_Henry:app/src/main/java/com/example/thp101_team1_bagchance/controller/cart/CartPayFragment.kt
        // TODO: Use the ViewModel
    }

}