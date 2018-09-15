package com.github.yasushi.hansel

import android.arch.persistence.room.Room
import android.net.Uri
import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.*

@RunWith(AndroidJUnit4::class)
class TripTests {
    val trip = Trip("0", "0", Uri.EMPTY, System.currentTimeMillis(), 1000)

    @Test
    fun insertTrip(){
        val c = InstrumentationRegistry.getTargetContext()
        val db = Room.databaseBuilder(c, TripDatabase::class.java, "tripDatabase").build()
        db.clearAllTables()
        db.tripDao().insert(trip)

        val rTrip = db.tripDao().selectById(trip.id)
        assertEquals(trip.toMap(), rTrip.toMap())
    }

    @Test
    fun deleteTrip(){
        val c = InstrumentationRegistry.getTargetContext()
        val db = Room.databaseBuilder(c, TripDatabase::class.java, "tripDatabase").build()
        db.clearAllTables()
        db.tripDao().insert(trip)

        var numTrips = db.tripDao().selectAll().size
        assertEquals(numTrips, 1)
        db.tripDao().delete("0")
        numTrips = db.tripDao().selectAll().size
        assertEquals(numTrips, 0)
    }

}