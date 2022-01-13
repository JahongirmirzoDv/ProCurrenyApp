package com.example.provalutalarkursi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.provalutalarkursi.dao.DataDao
import com.example.provalutalarkursi.models.Data

@Database(entities = [Data::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao

    companion object {
        var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "Data")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}
