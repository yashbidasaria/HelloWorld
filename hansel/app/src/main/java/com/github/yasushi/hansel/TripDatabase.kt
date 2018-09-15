package com.github.yasushi.hansel

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(entities = [Trip::class], version = 1, exportSchema = true)
@TypeConverters(UriToStringConverter::class)
abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao() : TripDao
}