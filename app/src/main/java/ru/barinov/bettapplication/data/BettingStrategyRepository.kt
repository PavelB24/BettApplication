package ru.barinov.bettapplication.data

import kotlinx.coroutines.flow.Flow
import ru.barinov.bettapplication.domain.BettingStrategyEntity

class BettingStrategyRepository(
    private val dao: BettingStrategyDAO
) {

    fun getAllStrategies(): Flow<List<BettingStrategyEntity>> {
        return dao.getBettingStrategies()
    }

    suspend fun insertEntity(entity: BettingStrategyEntity) {
        return dao.insertEntity(entity)
    }

    fun getStrategyById(id: String): Flow<BettingStrategyEntity> {
        return dao.getSelectedStrategy(id)
    }

}