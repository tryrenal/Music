package com.redveloper.music.ui.music_list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.redveloper.music.databinding.FragmentMusicListBinding
import com.redveloper.music.ui.music_list.adapter.MusicListAdapter
import com.redveloper.music.util.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MusicListFragment : Fragment() {

    private lateinit var binding: FragmentMusicListBinding
    private val viewModel: MusicListViewModel by viewModels()
    private lateinit var adapter: MusicListAdapter

    @Inject
    lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MusicListAdapter()
        searchAction()

        lifecycleScope.launch {
            viewModel.musicState
                .collect { result ->
                    setupUI(result)
                }
        }

        adapter.setOnPlayListener { data ->
            if (!data.music.isBlank()) {
                binding.layoutMusicPlay.root.visibility = View.VISIBLE
                playMusic(data.music)
            }
        }

    }

    private fun playMusic(music: String){
        exoPlayer.apply {
            clearMediaItems()
            setMediaItem(MediaItem.fromUri(music))
            binding.layoutMusicPlay.styledPlayer.player = this
            prepare()
            playWhenReady = true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun searchAction(){
        binding.etSearch.doAfterTextChanged { query ->
            if(!query.isNullOrBlank()){
                viewModel.searchSong(query.toString())
            }
        }

        binding.etSearch.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_UP){
                val drawableEnd = binding.etSearch.compoundDrawablesRelative[2]

                if(drawableEnd != null && event.rawX >= (binding.etSearch.right - drawableEnd.bounds.width() - binding.etSearch.paddingEnd)){
                    viewModel.clearSearch()
                    binding.etSearch.text.clear()
                    return@setOnTouchListener true
                }
            }

            false
        }
    }

    private fun setupUI(result: MusicListState) {
        result.error?.let { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        result.songs.let { data ->
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = this@MusicListFragment.adapter
            }
            adapter.setData(data)

            binding.initialLayout.root.isVisible(
                data.isEmpty() && binding.etSearch.text.isNullOrBlank())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(::exoPlayer.isInitialized)
            exoPlayer.release()
    }
}