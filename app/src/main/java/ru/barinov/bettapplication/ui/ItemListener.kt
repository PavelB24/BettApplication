package ru.barinov.bettapplication.ui

import ru.barinov.bettapplication.ui.uiModels.RecyclerViewItemModel

interface ItemListener {

   fun onItemClicker(strategyId: String)

   fun onFavoriteButtonChecked(strategy: RecyclerViewItemModel)
}