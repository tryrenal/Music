package com.redveloper.feature_onboarding.ui.signup_artist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.redveloper.core_ui.utils.BaseFragment
import com.redveloper.core_ui.utils.ext.ScrollAlphaItemDecoration
import com.redveloper.feature_onboarding.R
import com.redveloper.feature_onboarding.databinding.FragmentSignUpArtistBinding
import com.redveloper.feature_onboarding.ui.signup_artist.adapter.ArtistModel
import com.redveloper.feature_onboarding.ui.signup_artist.adapter.SignUpArtistAdapter

class SignUpArtistFragment : BaseFragment<FragmentSignUpArtistBinding>() {

    private lateinit var artistAdapter: SignUpArtistAdapter
    private var image = "data:image/jpeg;base64,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"

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpArtistBinding {
        return FragmentSignUpArtistBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artistAdapter = SignUpArtistAdapter()
        binding.recyclerviewArtist.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = artistAdapter
            addItemDecoration(ScrollAlphaItemDecoration(
                minAlpha = 0.1f
            ))
        }

        artistAdapter.differ.submitList(listOf<ArtistModel>(
            ArtistModel(id = "1", image, "Billie Ellish"),
            ArtistModel(id = "2", image, "Billie Ellish"),
            ArtistModel(id = "3", image, "Billie Ellish"),
            ArtistModel(id = "4", image, "Billie Ellish"),
            ArtistModel(id = "5", image, "Billie Ellish"),
            ArtistModel(id = "6", image, "Billie Ellish"),
            ArtistModel(id = "7", image, "Billie Ellish"),
            ArtistModel(id = "8", image, "Billie Ellish"),
            ArtistModel(id = "9", image, "Billie Ellish"),
            ArtistModel(id = "10", image, "Billie Ellish"),
            ArtistModel(id = "11", image, "Billie Ellish"),
            ArtistModel(id = "12", image, "Billie Ellish"),
            ArtistModel(id = "13", image, "Billie Ellish"),
            ArtistModel(id = "14", image, "Billie Ellish"),
            ArtistModel(id = "15", image, "Billie Ellish"),
            ArtistModel(id = "16", image, "Billie Ellish"),
            ArtistModel(id = "17", image, "Billie Ellish"),
            ArtistModel(id = "18", image, "Billie Ellish"),
            ArtistModel(id = "19", image, "Billie Ellish"),
            ArtistModel(id = "20", image, "Billie Ellish"),
            ArtistModel(id = "21", image, "Billie Ellish"),
            ArtistModel(id = "22", image, "Billie Ellish"),
            ArtistModel(id = "23", image, "Billie Ellish"),
            ArtistModel(id = "24", image, "Billie Ellish"),
        ))

        binding.appbar.onBack {
            findNavController().popBackStack()
        }

        artistAdapter.selectedItem = {
            findNavController().navigate(R.id.action_to_choose_podcast)
        }
    }


}