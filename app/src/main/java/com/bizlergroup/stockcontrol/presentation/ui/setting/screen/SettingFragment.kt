package com.bizlergroup.stockcontrol.presentation.ui.setting.screen

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bizlergroup.stockcontrol.R
import com.bizlergroup.stockcontrol.databinding.FragmentSettingBinding
import com.bizlergroup.stockcontrol.presentation.ui.main.MainFragment
import com.bizlergroup.stockcontrol.utils.SharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment(R.layout.fragment_setting) {
    private lateinit var binding: FragmentSettingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)

        initVariables()
        initListeners()

    }

    private fun initListeners() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
                MainFragment.BottomNavigationViewVisibilityLiveData.setVisibility(View.VISIBLE)
                MainFragment.FloatActionButtonVisibilityLiveData.setVisibility(View.VISIBLE)
            }
            btnEdit.setOnClickListener {
                findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToEditProfileFragment())
            }
            btnLogout.setOnClickListener {

                SharedPref.prefEditor.putString("token", ".")
                SharedPref.prefEditor.putBoolean("isLogin", false)
                SharedPref.prefEditor.apply()



                with(activity) {
                    MainFragment.BottomNavigationViewVisibilityLiveData.setVisibility(View.VISIBLE)
                    MainFragment.FloatActionButtonVisibilityLiveData.setVisibility(View.VISIBLE)
                    this?.moveTaskToBack(true)
                    this?.finish()
                }
            }
            llEditPassword.setOnClickListener {
                findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToEditPasswordFragment())
            }
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                        MainFragment.BottomNavigationViewVisibilityLiveData.setVisibility(View.VISIBLE)
                        MainFragment.FloatActionButtonVisibilityLiveData.setVisibility(View.VISIBLE)
                    }
                }
            }
            )
    }

    private fun initVariables() {
        binding.tvName.text = SharedPref.pref.getString("name", "Name")
        binding.tvSurname.text = SharedPref.pref.getString("surname", "Surname")
    }

}