package ru.barinov.bettapplication.domain.uiModels

import android.graphics.Bitmap
import ru.barinov.bettapplication.ui.ItemListener

data class RecyclerViewItemModel(
    val id: String,
    val title: String,
    val imageURL: String,
    val body: String,
    val isFavorite: Boolean,
    val listener: ItemListener,
)