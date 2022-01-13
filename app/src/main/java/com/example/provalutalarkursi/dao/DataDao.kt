package com.example.provalutalarkursi.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.provalutalarkursi.models.Data

@Dao
interface DataDao {
    @Insert
    fun addData(data: Data)

    @Query("select * from data")
    fun getList(): List<Data>

    @Query("delete from data")
    fun deleteAll()
}