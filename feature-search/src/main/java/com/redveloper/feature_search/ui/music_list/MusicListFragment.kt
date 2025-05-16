package com.redveloper.feature_search.ui.music_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.redveloper.core_ui.bottomsheet.basic.BasicBottomSheet
import com.redveloper.core_ui.utils.ext.isVisible
import com.redveloper.feature_search.databinding.FragmentMusicListBinding
import com.redveloper.feature_search.databinding.ItemPlayMusicLayoutBinding
import com.redveloper.feature_search.ui.music_list.adapter.MusicListAdapter
import com.redveloper.music.domain.model.Music
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.android.exoplayer2.ui.R as ExoPlayerR
import com.redveloper.feature_search.R

@AndroidEntryPoint
class MusicListFragment : Fragment() {

    private lateinit var binding: FragmentMusicListBinding
    private lateinit var controlExoBinding: ItemPlayMusicLayoutBinding

    private val viewModel: MusicListViewModel by viewModels()
    private lateinit var adapter: MusicListAdapter

    private var currentPosition = -1

    @Inject
    lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicListBinding.inflate(inflater, container, false)
        binding.layoutMusicPlay.styledPlayer.apply {
            controlExoBinding = ItemPlayMusicLayoutBinding.bind(findViewById(ExoPlayerR.id.exo_controller))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MusicListAdapter()
        searchAction()
        setupExoPlayer()

        lifecycleScope.launch {
            viewModel.musicState
                .collect { result ->
                    setupUI(result)
                }
        }

        adapter.setOnPlayListener { data, position ->
            if (!data.music.isBlank()) {
                currentPosition = position
                updateUIMusicPlay(data)
            }
        }
    }

    private fun updateUIMusicPlay(data: Music){
        binding.layoutMusicPlay.root.visibility = View.VISIBLE
        playMusic(data.music)
        binding.layoutMusicPlay.styledPlayer.apply {
            controlExoBinding.apply {
                tvPlay.text = data.collectionName
                tvBandName.text = data.artisName

                Glide.with(controlExoBinding.root)
                    .load(data.coverArt)
                    .into(imgPlay)
            }
        }

        exoListener()
    }

    private fun setupExoPlayer(){
        binding.layoutMusicPlay.styledPlayer.apply {
            player = exoPlayer
            setShowNextButton(true)
            setShowPreviousButton(true)

            useController = true
            controllerAutoShow = true
            controllerHideOnTouch = false
        }
    }

    private fun exoListener(){
        exoPlayer.addListener(object: Player.Listener{
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                controlExoBinding.exoPlay.setImageResource(
                    if (exoPlayer.isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                )
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                controlExoBinding.exoPlay.setImageResource(
                    if (exoPlayer.isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                )
            }

            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                exoListenerPrevNext()
            }
        })

        controlExoBinding.exoPlay.setOnClickListener {
            if (exoPlayer.isPlaying)
                exoPlayer.pause()
            else
                exoPlayer.play()
        }

        binding.layoutMusicPlay.styledPlayer.apply {
            showController()
            setControllerVisibilityListener(object : StyledPlayerControlView.VisibilityListener{
                override fun onVisibilityChange(visibility: Int) {
                    if(visibility == View.VISIBLE){
                        showController()
                        exoListenerPrevNext()
                    } else {
                        showController()
                    }
                }
            })
        }

        exoListenerPrevNext()
    }

    private fun exoListenerPrevNext(){
        controlExoBinding.exoNext.apply {
            isEnabled = true
            alpha = 1f
            setOnClickListener {
                adapter.playNext(currentPosition)
            }
        }

        controlExoBinding.exoPrev.apply {
            isEnabled = true
            alpha = 1f
            setOnClickListener {
                adapter.playPrev(currentPosition)
            }
        }
    }

    private fun playMusic(music: String){
        exoPlayer.apply {
            clearMediaItems()
            setMediaItem(MediaItem.fromUri(music))
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
            BasicBottomSheet
                .create(error)
                .show(childFragmentManager, "error bs")
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