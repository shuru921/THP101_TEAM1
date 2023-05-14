package com.example.thp101_team1_bagchance.controller.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.viewmodel.cart.CartPointViewModel

class CartPointFragment : Fragment() {

    companion object {
        fun newInstance() = CartPointFragment()
    }

    private lateinit var viewModel: CartPointViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart_point, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CartPointViewModel::class.java)
        // TODO: Use the ViewModel
    }

}