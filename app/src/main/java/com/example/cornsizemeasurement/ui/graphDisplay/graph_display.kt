package com.example.cornsizemeasurement.ui.graphDisplay

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Dimension.DP
import com.example.cornsizemeasurement.MainActivity
import com.example.cornsizemeasurement.R
import com.example.cornsizemeasurement.ui.historicalData.HistoricalDataViewModel
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class graph_display : Fragment() {
    companion object {
        fun newInstance() = graph_display()
    }

    private lateinit var viewModel: HistoricalDataViewModel
    lateinit var v : View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.graph_display_fragment, container, false)
        return v;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoricalDataViewModel::class.java)
        // TODO: Use the ViewModel

        print(viewModel.someList)

        var graph: GraphView = v.findViewById(R.id.graph)
        graph.setVisibility(View.VISIBLE)

        try {
            val x= 0;
            val points = arrayOf(DataPoint(x.toDouble(),x.toDouble()), DataPoint((x + 1).toDouble(),(x+1).toDouble()));
            var series: LineGraphSeries <DataPoint> = LineGraphSeries<DataPoint>(points)

            graph.addSeries(series)
        }
        catch (e: IllegalArgumentException){
            Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
        }
    }

    fun generateGraph(dataPoints: ArrayList<Integer>) {

    }

}