package com.example.cornsizemeasurement.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CornSize(
        @PrimaryKey val cornId: Int,
        @ColumnInfo(name = "time_stamp") val timeStamp: String?,
        @ColumnInfo(name = "size_data") val sizeData: String?
)