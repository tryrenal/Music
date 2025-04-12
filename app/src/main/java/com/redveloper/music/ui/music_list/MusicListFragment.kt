package com.redveloper.music.ui.music_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.redveloper.music.databinding.FragmentMusicListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MusicListFragment : Fragment() {

    private lateinit var binding: FragmentMusicListBinding
    private val viewModel: MusicListViewModel by viewModels()

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

        binding.etSearch.doAfterTextChanged { query ->
            viewModel.searchSong(query.toString())
        }

        lifecycleScope.launch {
            viewModel.musicState
                .collect { result ->
                    setupUI(result)
                }
        }

    }

    private fun setupUI(result: MusicListState) {
        result.error?.let { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            binding.tvTitle.text = error
        }

        result.songs.let { data ->
            if(data.isNotEmpty()){
                binding.tvTitle.text = data.size.toString() + " data"
            }
        }
    }
}