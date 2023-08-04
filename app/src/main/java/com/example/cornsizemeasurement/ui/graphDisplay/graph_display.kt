package com.example.cornsizemeasurement.ui.graphDisplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cornsizemeasurement.R
import com.example.cornsizemeasurement.db.CornSize
import com.example.cornsizemeasurement.db.CornSizeDatabase
import com.example.cornsizemeasurement.ui.home.HomeViewModel
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class graph_display : Fragment() {
    companion object {
        fun newInstance() = graph_display()
    }

    lateinit var v : View
    lateinit var db : CornSizeDatabase
    private lateinit var viewModel: HomeViewModel
    private lateinit var graph: GraphView
    private lateinit var displayTV: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.graph_display_fragment, container, false)

        return v;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(HistoricalDataViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        viewModel.selectedCorn.observe(viewLifecycleOwner, Observer { cornSelected -> generateGraph(cornSelected) })


        graph = v.findViewById(R.id.graph)
        graph.setVisibility(View.VISIBLE)
        displayTV = v.findViewById(R.id.graphDataTV)
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time/Hour")

        graph.getGridLabelRenderer().setHumanRounding(true);
        graph.getViewport().setYAxisBoundsManual(true)
        graph.getViewport().setXAxisBoundsManual(true)
    }

    fun generateGraph(cornSize: CornSize) {
        if(cornSize == null) {
            Toast.makeText(activity, "select a corn first", Toast.LENGTH_LONG).show()
        }

        if(cornSize.sizeData == null) {
            Toast.makeText(activity, "empty size data", Toast.LENGTH_LONG).show()
        }
        else {
            var cornData: String = cornSize.sizeData
            var dps: MutableList<Double> = ArrayList()
            var stringArray = cornData.split(",")
            var max: Double = 0.0

            for(entry in stringArray) {
                val entryVal = entry.toDouble()

                if(entryVal > max)
                    max = entryVal

                dps.add(entryVal)
            }

            try {
                val points: MutableList<DataPoint?> = ArrayList()

                for(i in 0 until dps.size) {
                    points.add(DataPoint(i.toDouble(), dps[i]))
                }

                var series: LineGraphSeries <DataPoint> = LineGraphSeries<DataPoint>(points.toTypedArray())
                graph.addSeries(series)

                graph.getViewport().setMinX(0.0)
                graph.getViewport().setMaxX(dps.size.toDouble() - 1)
                graph.getViewport().setMinY(0.0)
                graph.getViewport().setMaxY(max)

                displayTV.text = viewModel.selectedObs.value
            }
            catch (e: IllegalArgumentException){
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}