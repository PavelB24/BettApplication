package ru.barinov.bettapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.barinov.bettapplication.domain.BettingStrategyEntity

@Dao
interface BettingStrategyDAO {


    @Query("SELECT * FROM betting_strategy_table")
    fun getBettingStrategies(): Flow<List<BettingStrategyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntity(entity: BettingStrategyEntity)

    @Query("SELECT * FROM betting_strategy_table WHERE id =:id")
    fun getSelectedStrategy(id: String): Flow<BettingStrategyEntity>
}