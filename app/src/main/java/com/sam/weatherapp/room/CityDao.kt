package com.sam.weatherapp.room

import androidx.room.*
import com.sam.weatherapp.features.model.data_class.City

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    suspend fun getAll(): List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City)

    @Update
    suspend fun update(city: City)

    @Delete
    suspend fun delete(city: City)
}