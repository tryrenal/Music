package com.redveloper.feature_onboarding.ui.signup_name_tnc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.core_ui.utils.BaseFragment
import com.redveloper.feature_onboarding.R
import com.redveloper.feature_onboarding.databinding.FragmentSignUpTncBinding

class SignUpTncFragment : BaseFragment<FragmentSignUpTncBinding>() {
    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpTncBinding {
        return FragmentSignUpTncBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appbar.onBack {
            findNavController().popBackStack()
        }

        binding.checkedNews.onChecked {

        }

        binding.checkedMarketing.onChecked {

        }

        binding.btnCreateAccount.onClick {
            findNavController().navigate(R.id.action_to_choose_artist)
        }
    }

}