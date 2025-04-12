package com.redveloper.music.ui.music_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redveloper.music.databinding.ItemMusicLayoutBinding
import com.redveloper.music.domain.model.Music

class MusicListAdapter: RecyclerView.Adapter<MusicListViewHolder>() {

    private val _data: MutableList<Music> = mutableListOf()
    private var playListener: ((data: Music) -> Unit)? = null

    fun setOnPlayListener(listener: (data: Music) -> Unit){
        this.playListener = listener
    }

    fun setData(data: List<Music>){
        this._data.clear()
        this._data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicListViewHolder {
        return MusicListViewHolder(
            ItemMusicLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: MusicListViewHolder,
        position: Int
    ) {
        holder.bind(_data[position])

        holder.binding.root.setOnClickListener {
            playListener?.invoke(_data[position])
        }
    }

    override fun getItemCount(): Int {
        return _data.size
    }

}