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
import androidx.recyclerview.widget.LinearLayoutManager
import com.redveloper.music.databinding.FragmentMusicListBinding
import com.redveloper.music.ui.music_list.adapter.MusicListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MusicListFragment : Fragment() {

    private lateinit var binding: FragmentMusicListBinding
    private val viewModel: MusicListViewModel by viewModels()
    private lateinit var adapter: MusicListAdapter

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
        }

        result.songs.let { data ->
            if(data.isNotEmpty()){
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = this@MusicListFragment.adapter
                }
                adapter.setData(data)
            }
        }
    }
}