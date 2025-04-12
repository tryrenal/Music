package com.redveloper.music.ui.music_list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redveloper.music.databinding.ItemMusicLayoutBinding
import com.redveloper.music.domain.model.Music
import com.redveloper.music.util.getGroupName

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
}