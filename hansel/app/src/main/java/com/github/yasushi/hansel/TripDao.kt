package com.github.yasushi.hansel

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TripDao {

    @Query("select * from trip")
    fun selectAll(): List<Trip>

    @Query("select * from trip where id = :id")
    fun selectById(id: String): Trip

    @Insert
    fun insert(trip: Trip)

    @Query("delete from trip where id = :id")
    fun delete(id: String)
}