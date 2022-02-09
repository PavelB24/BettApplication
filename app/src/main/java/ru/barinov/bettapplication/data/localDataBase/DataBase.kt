package ru.barinov.bettapplication.data.localDataBase

import androidx.room.*
import ru.barinov.bettapplication.data.BettingStrategyDAO
import ru.barinov.bettapplication.domain.BettingStrategyEntity

@Database(
    version = 1,
    entities = [
        BettingStrategyEntity::class
    ]
)
abstract class DataBase : RoomDatabase() {

    abstract fun getProfileDao(): BettingStrategyDAO
}
