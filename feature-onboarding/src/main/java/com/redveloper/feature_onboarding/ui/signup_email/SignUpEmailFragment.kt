package com.redveloper.feature_onboarding.ui.signup_email

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.core_ui.utils.BaseFragment
import com.redveloper.feature_onboarding.R
import com.redveloper.feature_onboarding.databinding.FragmentSignUpEmailBinding

class SignUpEmailFragment : BaseFragment<FragmentSignUpEmailBinding>() {
    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpEmailBinding {
        return FragmentSignUpEmailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appbar.onBack {
            findNavController().popBackStack()
        }

        binding.btnNext.onClick {
            findNavController().navigate(R.id.action_to_password)
        }
    }


}