package ru.barinov.bettapplication.core

import androidx.recyclerview.widget.DiffUtil
import ru.barinov.bettapplication.ui.uiModels.RecyclerViewItemModel

class DiffCallback(
    private var oldList: List<RecyclerViewItemModel>,
    private var newList: List<RecyclerViewItemModel>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].id.equals(oldList[oldItemPosition].id)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newNote = newList[newItemPosition]
        val oldNote = oldList[oldItemPosition]
        return newNote == oldNote
    }
}



