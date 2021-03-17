package com.example.cornsizemeasurement.db
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.util.*;

class CornSizeRepository(private val cornSizeDao: CornSizeDao) {
    val allCornSize: Flow<List<CornSize>> = cornSizeDao.getAllCornSize();

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        cornSizeDao.deleteAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cornSize: CornSize) {
        cornSizeDao.insert(cornSize)
    }
}