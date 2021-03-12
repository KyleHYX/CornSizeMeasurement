package com.example.cornsizemeasurement.ui.historicalData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cornsizemeasurement.db.CornSize

class HistoricalDataViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Here's historical Data"
    }
    val text: LiveData<String> = _text

    val someList: MutableLiveData<ArrayList<Int>> = MutableLiveData(arrayListOf(1,2,3));
}