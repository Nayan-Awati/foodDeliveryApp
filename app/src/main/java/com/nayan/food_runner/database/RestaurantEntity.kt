package com.nayan.food_runner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resto")
data class RestaurantEntity (

    @PrimaryKey val resId: String,
    @ColumnInfo(name = "res_name")val resName: String,
    @ColumnInfo(name = "res_rating")val resRating: String,
    @ColumnInfo(name = "res_cost")val resCost: String,
    @ColumnInfo(name = "res_image")val resImage: String
        )