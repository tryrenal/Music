package com.redveloper.feature_onboarding.ui.signup_gender

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.core_ui.components.spinner.RedveloperSpinnerModel
import com.redveloper.core_ui.utils.BaseFragment
import com.redveloper.feature_onboarding.R
import com.redveloper.feature_onboarding.databinding.FragmentSignUpGenderBinding

class SignUpGenderFragment : BaseFragment<FragmentSignUpGenderBinding>() {

    private val data = listOf(
        RedveloperSpinnerModel("male", "Male"),
        RedveloperSpinnerModel("female", "Female"),
    )

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpGenderBinding {
        return FragmentSignUpGenderBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appbar.onBack {
            findNavController().popBackStack()
        }

        binding.spinnerGender.setData(data)
        binding.spinnerGender.selectedItemCallback = { data ->
            Log.i("SpinnerData", data.toString())
        }

        binding.btnNext.onClick {
            findNavController().navigate(R.id.action_to_tnc)
        }

    }

}