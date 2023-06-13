package com.example.thp101_team1_bagchance.controller.explore

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thp101_team1_bagchance.R
import com.example.thp101_team1_bagchance.viewmodel.explore.ExploreMainViewModel
import com.example.thp101_team1_bagchance.databinding.FragmentExploreMainBinding
import com.google.android.material.slider.RangeSlider


class ExploreMainFragment : Fragment() {
    private lateinit var binding: FragmentExploreMainBinding
    private var minAge = 18
    private var maxAge = 120






    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {



        val viewModel: ExploreMainViewModel by viewModels()
        binding = FragmentExploreMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toggleButton = view.findViewById<Button>(R.id.toggleButton)
        val linearLayoutSelect = view.findViewById<LinearLayout>(R.id.linearLayoutselect)

        toggleButton.setOnClickListener {
            if (linearLayoutSelect.visibility == View.VISIBLE) {
                linearLayoutSelect.visibility = View.GONE
            } else {
                linearLayoutSelect.visibility = View.VISIBLE
            }
        }



        with(binding) {

            rvstory.layoutManager = LinearLayoutManager(requireContext())
            viewModel?.mainstorys?.observe(viewLifecycleOwner) { mainstorys ->

                // adapter為null要建立新的adapter；之後只要呼叫updateFriends(friends)即可

                if (rvstory.adapter == null) {
                    rvstory.adapter = ExploreMainStoryAdapter(mainstorys)
                } else {
                    (rvstory.adapter as ExploreMainStoryAdapter).updatemainStorys(mainstorys)
                }

            }
            checkboxnorth.setOnCheckedChangeListener { buttonView, isChecked ->
                filterAndSetAdapter()
            }

            checkboxsouth.setOnCheckedChangeListener { buttonView, isChecked ->
                filterAndSetAdapter()
            }

            checkboxeast.setOnCheckedChangeListener { buttonView, isChecked ->
                filterAndSetAdapter()
            }

            checkboxwest.setOnCheckedChangeListener { buttonView, isChecked ->
                filterAndSetAdapter()
            }

            checkboxmale.setOnCheckedChangeListener { buttonView, isChecked ->
                filterAndSetAdapter()
            }

            checkboxfemale.setOnCheckedChangeListener { buttonView, isChecked ->
                filterAndSetAdapter()
            }
            val rangeTextView: TextView = binding.rangeTextView
            val rangeSlider: RangeSlider = binding.rangeSlider
            // 设置初始范围为 18 到 120
            rangeSlider.setValues(18f, 120f)

            // 添加 OnChangeListener
            rangeSlider.addOnChangeListener { slider,_, _ ->
                val rangeValues = slider.values
                minAge = rangeValues[0].toInt()
                maxAge = rangeValues[1].toInt()

                // 更新文本视图显示当前选择范围
                rangeTextView.text = "Range: $minAge - $maxAge"
                filterAndSetAdapter()
            }





        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterAndSetAdapter() {
        with(binding) {


            val northSelected = checkboxnorth.isChecked
            val southSelected = checkboxsouth.isChecked
            val eastSelected = checkboxeast.isChecked
            val westSelected = checkboxwest.isChecked
            val maleSelected = checkboxmale.isChecked
            val femaleSelected = checkboxfemale.isChecked



            val mainstorys = viewModel?.mainstorys?.value
            val filteredList = mainstorys!!.filter { item ->
                // 根據方向進行過濾
                val directionMatched = when (item.explore_area) {
                    "N" -> northSelected
                    "S" -> southSelected
                    "E" -> eastSelected
                    "W" -> westSelected
                    else -> false
                }

                // 根據性別進行過濾
                val genderMatched = when (item.gender) {
                    "M" -> maleSelected
                    "F" -> femaleSelected
                    else -> false
                }
                val ageMatched = item.age in minAge..maxAge
//                Log.d("==============","item.age: ${item.age}")
//                Log.d("==============","minAge: ${minAge}")
//                Log.d("==============","maxAge: ${maxAge}")
//                Log.d("==============","ageMatched: ${ageMatched}")


                // 回傳是否符合所有篩選條件
                directionMatched && genderMatched && ageMatched
            }
            Log.d("==============","filteredList.size: ${filteredList.size}")
            val adapter = ExploreMainStoryAdapter(filteredList)
            rvstory.adapter = adapter


        }


    }


}
