package com.example.thp101_team1_bagchance.controller.chat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thp101_team1_bagchance.databinding.FragmentChatMainBinding
import com.example.thp101_team1_bagchance.viewmodel.chat.ChatMainViewModel


class ChatMainFragment : Fragment() {
    private lateinit var binding: FragmentChatMainBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        //requireActivity().setTitle(R.string.txtChat)
        val viewModel : ChatMainViewModel by viewModels()
        binding = FragmentChatMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //activity?.setTitle(R.string.txtChat)
        with(binding) {
//            設定recyclerview佈局為垂直
            rvRoomChat.layoutManager = LinearLayoutManager(requireContext())
//            監聽聊天室列表
            viewModel?.chatlist?.observe(viewLifecycleOwner) {
//              沒建立過就建立(第一次)
                if (rvRoomChat.adapter == null) {
                    rvRoomChat.adapter = ChatMainAdapter(it)
                }else {
//                  建立過更新
                    (rvRoomChat.adapter as ChatMainAdapter).update(it)
                }
            }

//            searchview 要送出才會篩選
            svSearchChat.setOnQueryTextListener(object : OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
//            內容變化呼叫search()
                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel?.search(newText)
                    return true
                }
            })
        }
    }
}