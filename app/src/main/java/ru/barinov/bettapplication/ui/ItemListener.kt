package ru.barinov.bettapplication.ui

import ru.barinov.bettapplication.domain.uiModels.RecyclerViewItemModel

interface ItemListener {

   fun onItemClicker(strategyId: String)

   fun onFavoriteButtonChecked(strategy: RecyclerViewItemModel)
}