package com.example.cornsizemeasurement.ui.historicalData

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cornsizemeasurement.HistoricalDataApplication
import com.example.cornsizemeasurement.R
import com.example.cornsizemeasurement.db.CornSize
import com.example.cornsizemeasurement.db.CornSizeDatabase
import com.example.cornsizemeasurement.db.CornSizeRepository
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class HistoricalData : Fragment() {
    companion object {
        fun newInstance() = HistoricalData()
    }

    lateinit var v : View
    lateinit var curViewModel : HistoricalDataViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.historical_data_fragment, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //viewModel = ViewModelProvider(this).get(HistoricalDataViewModel::class.java)
        val application = HistoricalDataApplication()

        val viewModel: HistoricalDataViewModel by viewModels {
            HistoricalViewModelFactory((application as HistoricalDataApplication).repository)
        }

        curViewModel = viewModel

        val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CornSizeListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        putSimulatedData()
    }

    fun putSimulatedData() {
        /*val db = getInstance(applicationContext)

        println("--------------------------------")
        GlobalScope.launch {
            db.cornSizeDao().deleteAll()
            val cornSize : CornSize = CornSize(5,"2", "3")

            db.cornSizeDao().insertAll(cornSize)

            val data = db.cornSizeDao().getAll()
        }
        */

        curViewModel.deleteAll()
        val cornSize1 : CornSize = CornSize(1,"2", "1,5,6")
        val cornSize2 : CornSize = CornSize(2,"2", "1,7,9")
        val cornSize3 : CornSize = CornSize(3,"2", "1,2,3")
        curViewModel.insert(cornSize1)
        curViewModel.insert(cornSize2)
        curViewModel.insert(cornSize3)
    }
}