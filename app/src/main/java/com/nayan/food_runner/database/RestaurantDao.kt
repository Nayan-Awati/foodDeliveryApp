package com.nayan.food_runner.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RestaurantDao {

    @Insert
    fun insertResto(restaurantEntity: RestaurantEntity)

    @Delete
    fun deleteResto(restaurantEntity: RestaurantEntity)

    @Query("SELECT * FROM resto")
    fun getAllResto(): List<RestaurantEntity>

    @Query("SELECT * FROM resto WHERE resId = :resto_id")
    fun getRestoById( resto_id: String):RestaurantEntity
}