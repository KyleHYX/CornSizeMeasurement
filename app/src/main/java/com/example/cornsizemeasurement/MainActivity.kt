package com.example.cornsizemeasurement


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cornsizemeasurement.db.CornSize
import com.example.cornsizemeasurement.db.CornSizeDatabase.Companion.getInstance
import com.example.cornsizemeasurement.db.CornSizeRepository
import com.example.cornsizemeasurement.ui.historicalData.CornSizeListAdapter
import com.example.cornsizemeasurement.ui.historicalData.HistoricalDataViewModel
import com.example.cornsizemeasurement.ui.historicalData.HistoricalViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //lateinit var curViewModel : HistoricalDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //navBar
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_historicalData, R.id.navigation_graph_display))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val viewModel: HistoricalDataViewModel by viewModels {
            HistoricalViewModelFactory((application as HistoricalDataApplication).repository)
        }

        //curViewModel = viewModel

        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        //val adapter = CornSizeListAdapter()
        //recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        //putSimulatedData()
    }
    /*fun putSimulatedData() {
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
    }*/

}