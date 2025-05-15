package com.redveloper.feature_onboarding.ui.signup_artist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redveloper.feature_onboarding.databinding.ArtistItemLayoutBinding

class SignUpArtistAdapter : RecyclerView.Adapter<SignUpArtistAdapter.ViewHolder>(){

    private val diffUtilCallback = object: DiffUtil.ItemCallback<ArtistModel>(){
        override fun areItemsTheSame(
            oldItem: ArtistModel,
            newItem: ArtistModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ArtistModel,
            newItem: ArtistModel
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtilCallback)

    var selectedItem: ((item: ArtistModel) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ArtistItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(differ.currentList[position])
        holder.itemView.setOnClickListener {
            selectedItem?.invoke(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class ViewHolder(private val binding: ArtistItemLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: ArtistModel){
            Glide.with(binding.root.context)
                .load(item.image)
                .into(binding.imgArtist)

            binding.tvArtist.text = item.title
        }
    }
}