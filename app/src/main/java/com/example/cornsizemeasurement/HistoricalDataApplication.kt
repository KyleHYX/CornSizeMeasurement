package com.example.cornsizemeasurement

import android.app.Application
import android.content.Context
import com.example.cornsizemeasurement.db.CornSizeDatabase
import com.example.cornsizemeasurement.db.CornSizeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class HistoricalDataApplication : Application() {
    //val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { CornSizeDatabase.getInstance(this) }
    val repository by lazy { CornSizeRepository(database.cornSizeDao()) }
}