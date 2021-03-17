package com.example.cornsizemeasurement.ui.graphDisplay

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cornsizemeasurement.R
import com.example.cornsizemeasurement.db.CornSize
import com.example.cornsizemeasurement.db.CornSizeDatabase
import com.example.cornsizemeasurement.ui.historicalData.CornSizeListAdapter
import com.example.cornsizemeasurement.ui.historicalData.HistoricalDataViewModel
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class graph_display : Fragment() {
    companion object {
        fun newInstance() = graph_display()
    }

    private lateinit var viewModel: HistoricalDataViewModel
    lateinit var v : View
    lateinit var db : CornSizeDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.graph_display_fragment, container, false)

        //val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerview)
        //val adapter = CornSizeListAdapter();
        //recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(this.context);

        return v;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(HistoricalDataViewModel::class.java)

        //print(viewModel.someList)

        var graph: GraphView = v.findViewById(R.id.graph)
        graph.setVisibility(View.VISIBLE)

        db = CornSizeDatabase.getInstance(requireContext().applicationContext)
        GlobalScope.launch {
            var allCornSize = db.cornSizeDao().getAll()
            var allVals = allCornSize
            var first: String = " "
            if(allVals != null) {
                first= allVals[0].sizeData.toString()
            }

            var dps: MutableList<Int> = ArrayList()
            var valArray = first.toCharArray()
            for(entry in valArray) {
                dps.add(entry.toInt())
            }
            try {
                val x= 0;
                val points = arrayOf(DataPoint(0.toDouble(), dps[1].toDouble()), DataPoint(1.toDouble(), dps[0].toDouble()), DataPoint(2.toDouble(), dps[2].toDouble()));
                var series: LineGraphSeries <DataPoint> = LineGraphSeries<DataPoint>(points)

                graph.addSeries(series)
            }
            catch (e: IllegalArgumentException){
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun generateGraph(dataPoints: ArrayList<Integer>) {

    }

}