package com.example.cornsizemeasurement.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CornSizeDao {
    @Query("SELECT * FROM cornSize")
    fun getAll(): List<CornSize>

    @Insert
    fun insertAll(vararg cornSizes: CornSize)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cornSizes: CornSize)

    @Query("SELECT * FROM cornSize ORDER BY cornId ASC")
    fun getAllCornSize(): LiveData<List<CornSize>>

    @Delete
    fun delete(cornSize: CornSize)

    @Query("DELETE FROM cornSize")
    fun deleteAll()
}