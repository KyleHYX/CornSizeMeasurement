package com.example.cornsizemeasurement.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(CornSize::class), version = 1, exportSchema = false)
abstract class CornSizeDatabase : RoomDatabase() {
    abstract fun cornSizeDao() : CornSizeDao

    companion object {
        private var instance: CornSizeDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): CornSizeDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CornSizeDatabase {
            return Room.databaseBuilder(context, CornSizeDatabase::class.java, "CornSizeDatabase").build()
        }
    }
}