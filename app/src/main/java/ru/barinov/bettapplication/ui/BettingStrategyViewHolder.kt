package ru.barinov.bettapplication.ui


import androidx.recyclerview.widget.RecyclerView
import ru.barinov.bettapplication.databinding.BettingStrategyItemLayoutBinding

class BettingStrategyViewHolder(
     binding: BettingStrategyItemLayoutBinding)
    : RecyclerView.ViewHolder(binding.root) {


        val titleTextView = binding.bettingStrategyName
        val imageView = binding.bettingStrategyImgView
        val favoriteButton = binding.itemsFavoriteButton
    }