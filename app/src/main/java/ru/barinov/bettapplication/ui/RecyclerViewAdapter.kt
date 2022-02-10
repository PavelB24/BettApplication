package ru.barinov.bettapplication.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.*
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.barinov.bettapplication.R
import ru.barinov.bettapplication.core.*
import ru.barinov.bettapplication.databinding.BettingStrategyItemLayoutBinding
import ru.barinov.bettapplication.domain.uiModels.RecyclerViewItemModel

private const val imageScaleBase = 75

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
        Glide.with(holder.itemView.context).asBitmap().load(item.imageURL)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val scaledBitMap = resource.scaledImageFromBitmap(
                        resource,
                        imageScaleBase,
                        holder.itemView.context.resources.displayMetrics.density
                    )
                    holder.imageView.setImageBitmap(scaledBitMap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })


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