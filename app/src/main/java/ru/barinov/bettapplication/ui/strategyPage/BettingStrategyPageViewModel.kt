package ru.barinov.bettapplication.ui.strategyPage

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.barinov.bettapplication.data.BettingStrategyRepository
import ru.barinov.bettapplication.domain.BettingStrategyEntity

class BettingStrategyPageViewModel(
    private val localRepository: BettingStrategyRepository, private val id: String
) : ViewModel() {

    private val _selectedStrategy: Flow<BettingStrategyEntity?> =
        localRepository.getStrategyById(id).flowOn(Dispatchers.IO)

    val selectedStrategy: StateFlow<BettingStrategyEntity?> =
        _selectedStrategy.stateIn(viewModelScope, SharingStarted.Eagerly, null)

}