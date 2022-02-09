package ru.barinov.bettapplication.domain

import android.graphics.Bitmap
import androidx.room.*

@Entity(tableName = "betting_strategy_table")
data class BettingStrategyEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    @ColumnInfo(name = "image_url")
    val imageURL: String,
    val body: String,
    val isFavorite: Boolean,
    )

