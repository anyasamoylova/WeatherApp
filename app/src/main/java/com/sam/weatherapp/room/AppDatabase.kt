package com.sam.weatherapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sam.weatherapp.features.model.data_class.City

/**
 * Database consist of favorite cities
 */
@Database(entities = [City::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}