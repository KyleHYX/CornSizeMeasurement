package com.example.cornsizemeasurement.ui.graphDisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GraphDisplayViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Here's graphic display"
    }
    val text: LiveData<String> = _text
}