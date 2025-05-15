package com.redveloper.feature_onboarding.ui.signup_podcast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redveloper.feature_onboarding.databinding.PodcastItemLayoutBinding

class SignUpPodcastAdapter: RecyclerView.Adapter<SignUpPodcastAdapter.ViewHolder>() {

    private val diffUtilCallback = object : DiffUtil.ItemCallback<PodcastModel>(){
        override fun areItemsTheSame(
            oldItem: PodcastModel,
            newItem: PodcastModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PodcastModel,
            newItem: PodcastModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            PodcastItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class ViewHolder(private val binding: PodcastItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: PodcastModel){
            Glide.with(binding.root.context)
                .load(item.image)
                .into(binding.imgArtist)

            binding.tvArtist.text = item.title
        }
    }
}