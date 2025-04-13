package com.redveloper.music.ui.music_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redveloper.music.databinding.ItemMusicLayoutBinding
import com.redveloper.music.domain.model.Music

class MusicListAdapter: RecyclerView.Adapter<MusicListViewHolder>() {

    private val _data: MutableList<Music> = mutableListOf()
    private var playListener: ((data: Music, position: Int) -> Unit)? = null

    fun setOnPlayListener(listener: (data: Music, position: Int) -> Unit){
        this.playListener = listener
    }

    private fun getNextData(currentPosition: Int): Music? {
        val nextPosition = currentPosition + 1
        try {
            if(nextPosition < _data.size){
                val nextData = _data[nextPosition]
                return nextData
            }
            return null
        }
        catch (e: Exception){
            return null
        }
    }

    fun playNext(position: Int){
        getNextData(position)?.let {
            playListener?.invoke(it, position + 1)
        }
    }

    fun playPrev(position: Int){
        getPrevData(position)?.let {
            playListener?.invoke(it, position - 1)
        }
    }

    private fun getPrevData(currentPosition: Int): Music? {
        val prevPosition = currentPosition - 1
        try {
            if(prevPosition > 0){
                val prevData = _data[prevPosition]
                return prevData
            }
            return null
        }
        catch (e: Exception){
            return null
        }
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
            playListener?.invoke(_data[position], position)
        }
    }

    override fun getItemCount(): Int {
        return _data.size
    }

}