package ru.barinov.bettapplication.ui.uiModels

import android.graphics.Bitmap
import ru.barinov.bettapplication.ui.ItemListener

data class RecyclerViewItemModel(
    val id: String,
    val title: String,
    val imageURL: String,
    val body: String,
    val isFavorite: Boolean,
    val listener: ItemListener,
    var bitmap: Bitmap? = null
)