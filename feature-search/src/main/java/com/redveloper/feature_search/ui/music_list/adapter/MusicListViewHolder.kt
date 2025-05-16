package com.redveloper.feature_search.ui.music_list.adapter

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redveloper.feature_search.databinding.ItemMusicLayoutBinding
import com.redveloper.feature_search.utils.getGroupName
import com.redveloper.music.domain.model.Music
import com.redveloper.feature_search.R as AppR
import com.redveloper.core_ui.R as CoreR

class MusicListViewHolder(val binding: ItemMusicLayoutBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Music){
        binding.tvTitle.text = data.collectionName
        binding.tvGroupName.text = getGroupName(data.artisName)

        binding.imgPlay.let {
            Glide.with(binding.root)
                .load(data.coverArt)
                .into(it)
        }
    }

    fun setIconToPlay(){
        binding.btnPlay.apply {
            setImageResource(AppR.drawable.ic_play)
            imageTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, CoreR.color.green))
        }
    }

    fun resetIcon(){
        binding.btnPlay.apply {
            setImageResource(AppR.drawable.ic_add_circle)
            imageTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, CoreR.color.grey))
        }
    }
}