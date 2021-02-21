package com.example.cornsizemeasurement.ui.historicalData

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cornsizemeasurement.R

class HistoricalData : Fragment() {

    companion object {
        fun newInstance() = HistoricalData()
    }

    private lateinit var viewModel: HistoricalDataViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.historical_data_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoricalDataViewModel::class.java)
    }

}