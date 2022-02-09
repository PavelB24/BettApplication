package ru.barinov.bettapplication.ui

import android.view.*
import androidx.recyclerview.widget.*
import ru.barinov.bettapplication.R
import ru.barinov.bettapplication.core.DiffCallback
import ru.barinov.bettapplication.databinding.BettingStrategyItemLayoutBinding
import ru.barinov.bettapplication.ui.uiModels.RecyclerViewItemModel

class RecyclerViewAdapter : RecyclerView.Adapter<BettingStrategyViewHolder>() {

    private var itemsList = emptyList<RecyclerViewItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BettingStrategyViewHolder {
        val viewHolderBinding: BettingStrategyItemLayoutBinding =
            BettingStrategyItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BettingStrategyViewHolder(viewHolderBinding)
    }

    override fun onBindViewHolder(holder: BettingStrategyViewHolder, position: Int) {
        val item = getItem(position)

        if (item.isFavorite) {
            holder.favoriteButton.setImageResource(R.drawable.ic_favourites_selected_star)
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_favourites_black_star)
        }

        holder.titleTextView.text = item.title
        holder.imageView.setImageBitmap(item.bitmap)

        holder.itemView.setOnClickListener { item.listener.onItemClicker(item.id) }
        holder.favoriteButton.setOnClickListener { item.listener.onFavoriteButtonChecked(item) }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    private fun getItem(position: Int): RecyclerViewItemModel {
        return itemsList[position]
    }

    fun setList(newData: List<RecyclerViewItemModel>) {
        val result = DiffUtil.calculateDiff(DiffCallback(itemsList, newData), true)
        itemsList = newData
        result.dispatchUpdatesTo(this)
    }
}