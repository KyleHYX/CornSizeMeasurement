package com.example.cornsizemeasurement.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.cornsizemeasurement.HistoricalDataApplication
import com.example.cornsizemeasurement.db.CornSize
import com.example.cornsizemeasurement.db.CornSizeDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "home fragment"
    }

    val text: LiveData<String> = _text

    var selectedObs = MutableLiveData<String>().apply {
        value = "No Corn Selected"
    }

    var selectedCorn = MutableLiveData<CornSize>().apply {
        value = CornSize(0,null,null)
    }
}