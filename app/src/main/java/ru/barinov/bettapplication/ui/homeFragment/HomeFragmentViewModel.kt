package ru.barinov.bettapplication.ui.homeFragment

import androidx.lifecycle.*
import androidx.room.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.barinov.bettapplication.core.Event
import ru.barinov.bettapplication.data.BettingStrategyRepository
import ru.barinov.bettapplication.domain.BettingStrategyEntity
import ru.barinov.bettapplication.ui.ItemListener
import ru.barinov.bettapplication.ui.uiModels.RecyclerViewItemModel

class HomeFragmentViewModel(
    private val localRepository: BettingStrategyRepository
) : ViewModel() {

    private val _onlyFavoritesMode: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val onlyFavoritesMode: StateFlow<Boolean> = _onlyFavoritesMode

    private val _onStrategyChecked: MutableStateFlow<Event<String>> = MutableStateFlow(Event(""))
    val onStrategyChecked: StateFlow<Event<String>> = _onStrategyChecked


    private val _bettingStrategies: Flow<List<RecyclerViewItemModel>> =
        combine(onlyFavoritesMode, localRepository.getAllStrategies()) { isOnlyFavoritesMode, entityList ->

            return@combine if (isOnlyFavoritesMode) {
                entityList.filter { entity -> entity.isFavorite }
            } else return@combine entityList
        }.map { list ->
            return@map list.map { entity ->
                RecyclerViewItemModel(
                    id = entity.id,
                    title = entity.title,
                    imageURL = entity.imageURL,
                    body = entity.body,
                    isFavorite = entity.isFavorite,
                    listener = sendListener()
                )
            }
        }.flowOn(Dispatchers.IO)

    val profilesData = _bettingStrategies.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onFavoriteClicked(selected: Boolean) {
        _onlyFavoritesMode.value = selected
    }

    private fun sendListener(): ItemListener {

        return object : ItemListener {
            override fun onItemClicker(strategyId: String) {
                _onStrategyChecked.value = Event(strategyId)
            }

            override fun onFavoriteButtonChecked(strategy: RecyclerViewItemModel) {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        localRepository.insertEntity(
                            BettingStrategyEntity(
                                id = strategy.id,
                                title = strategy.title,
                                imageURL = strategy.imageURL,
                                body = strategy.body,
                                isFavorite = !strategy.isFavorite
                            )
                        )
                    }
                }
            }
        }
    }

     suspend fun initialDataBase(titles: Array<String>, bodies: Array<String>, urls: Array<String> ) {

        val count = 5
        for (i in 0.. count){
            localRepository.insertEntity(BettingStrategyEntity(
                id = i.toString(),
                title = titles[i],
                imageURL = urls[i],
                body = bodies[i],
                isFavorite = false
            ))
        }

    }


}