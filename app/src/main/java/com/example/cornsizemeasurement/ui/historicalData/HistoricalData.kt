package com.example.cornsizemeasurement.ui.historicalData

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cornsizemeasurement.HistoricalDataApplication
import com.example.cornsizemeasurement.R
import com.example.cornsizemeasurement.db.CornSize
import com.example.cornsizemeasurement.db.CornSizeDatabase
import com.example.cornsizemeasurement.db.CornSizeDatabase.Companion.getInstance
import com.example.cornsizemeasurement.db.CornSizeRepository
import com.google.android.material.internal.ContextUtils
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class HistoricalData : Fragment() {
    companion object {
        fun newInstance() = HistoricalData()
    }

    lateinit var v : View
    lateinit var db : CornSizeDatabase

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

        db = getInstance(requireContext().applicationContext)
        GlobalScope.launch {
            db.cornSizeDao().deleteAll()
            val cornSize1 : CornSize = CornSize(1,"2", "1,5,6")
            val cornSize2 : CornSize = CornSize(2,"2", "1,7,9")
            val cornSize3 : CornSize = CornSize(3,"2", "1,2,3")

            db.cornSizeDao().insertAll(cornSize1)
            db.cornSizeDao().insertAll(cornSize2)
            db.cornSizeDao().insertAll(cornSize3)
        }

        val showDataBtn: Button = v.findViewById(R.id.showDataBtn)


        showDataBtn.setOnClickListener() {
            getData()
        }

    }

    fun getData() {

        System.out.println("===================")
        val showHistory: TextView = v.findViewById(R.id.historicalDataTV)
        GlobalScope.launch {
            var allCornSize = db.cornSizeDao().getAll()
            var allVals = allCornSize
            if(allVals != null) {
                for(entry in allVals) {
                    val cId = entry.cornId
                    val cTS = entry.timeStamp
                    val cSize = entry.sizeData

                    showHistory.append("\nCorn number: $cId is created at time $cTS has data: $cSize")

                    System.out.println("------------")
                    System.out.println(cId)
                }
            }
        }
    }
}