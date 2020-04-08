package com.example.stamurai.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Rating <Range>"
    }
    private val _min = 0
    private val _max = 9
    val text: LiveData<String> = _text
}