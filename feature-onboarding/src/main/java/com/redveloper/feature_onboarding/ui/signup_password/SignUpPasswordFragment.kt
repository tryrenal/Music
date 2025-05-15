package com.redveloper.feature_onboarding.ui.signup_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.core_ui.utils.BaseFragment
import com.redveloper.feature_onboarding.R
import com.redveloper.feature_onboarding.databinding.FragmentSignUpPasswordBinding

class SignUpPasswordFragment : BaseFragment<FragmentSignUpPasswordBinding>() {
    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpPasswordBinding {
        return FragmentSignUpPasswordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appbar.onBack {
            findNavController().popBackStack()
        }

        binding.btnNext.onClick {
            findNavController().navigate(R.id.action_to_gender)
        }
    }

}