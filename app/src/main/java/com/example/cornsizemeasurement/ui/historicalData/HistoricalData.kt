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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cornsizemeasurement.R
import com.example.cornsizemeasurement.db.CornSize
import com.example.cornsizemeasurement.db.CornSizeDatabase
import com.example.cornsizemeasurement.db.CornSizeDatabase.Companion.getInstance
import com.example.cornsizemeasurement.ui.home.HomeViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HistoricalData : Fragment() {
    companion object {
        fun newInstance() = HistoricalData()
    }

    lateinit var v : View
    lateinit var db : CornSizeDatabase
    lateinit var allCornSize: List<CornSize>
    private lateinit var viewModel: HomeViewModel
    lateinit var hda: HistoricalDataAdapter

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

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        db = getInstance(requireContext().applicationContext)

        val historicalDataRV: RecyclerView = v.findViewById(R.id.cornSizeList)
        var cornSizeList: List<CornSize> = emptyList()
        var selectedCornSize: CornSize

        hda = HistoricalDataAdapter(cornSizeList)
        historicalDataRV.adapter = hda
        historicalDataRV.layoutManager = LinearLayoutManager(this.context)

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
        showDataBtn.text = viewModel.sth

        showDataBtn.setOnClickListener() {
            getData()
        }

    }

    fun getData() {
        GlobalScope.launch {
            allCornSize = db.cornSizeDao().getAll()

            activity?.runOnUiThread(java.lang.Runnable {
                if(allCornSize != null) {
                    hda.setData(allCornSize)
                }
            })
        }
    }
}