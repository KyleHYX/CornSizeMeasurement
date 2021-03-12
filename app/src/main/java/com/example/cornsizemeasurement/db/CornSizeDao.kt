package com.example.cornsizemeasurement.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CornSizeDao {
    @Query("SELECT * FROM cornSize")
    fun getAll(): LiveData<List<CornSize>>

    @Insert
    fun insertAll(vararg cornSizes: CornSize)

    @Delete
    fun delete(cornSize: CornSize)

    @Query("DELETE FROM cornSize")
    fun deleteAll()
}