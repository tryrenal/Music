package com.redveloper.music.ui.music_list.adapter

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redveloper.music.databinding.ItemMusicLayoutBinding
import com.redveloper.music.domain.model.Music
import com.redveloper.music.util.getGroupName
import com.redveloper.music.R as AppR

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
            imageTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, AppR.color.green))
        }
    }

    fun resetIcon(){
        binding.btnPlay.apply {
            setImageResource(AppR.drawable.ic_add_circle)
            imageTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, AppR.color.grey))
        }
    }
}