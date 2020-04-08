package com.example.stamurai.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.stamurai.R




class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val rangeButton: Button = root.findViewById(R.id.range_button)

        homeViewModel.text.observe(this, Observer {
            val sharedPref = this.activity?.getPreferences(Context.MODE_PRIVATE)
            val minRange = sharedPref?.getInt("Min", -1)

            if (minRange != -1){
                val maxRange = sharedPref?.getInt("Max", -1)
                if (maxRange != -1){
                    rangeButton.text = "Range $minRange-$maxRange"
                }
            }
        })

        val data = arrayOf("0", "1", "2", "3","4","5","6","7","8","9")

        val pickerMin : NumberPicker = root.findViewById(R.id.min_range_picker)
        pickerMin.minValue = 0
        pickerMin.maxValue = 9
        pickerMin.maxValue = data.size - 1
        pickerMin.displayedValues = data

        val pickerMax : NumberPicker = root.findViewById(R.id.max_range_picker)
        pickerMax.minValue = 0
        pickerMax.maxValue = 9
        pickerMax.maxValue = data.size - 1
        pickerMax.displayedValues = data


        return root
    }
}