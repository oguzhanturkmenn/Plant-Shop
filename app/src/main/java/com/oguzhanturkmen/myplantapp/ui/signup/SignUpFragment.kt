package com.oguzhanturkmen.myplantapp.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.databinding.FragmentSignUpBinding
import com.oguzhanturkmen.myplantapp.utils.gecisYap
import com.oguzhanturkmen.myplantapp.utils.makeToast
import com.yagmurerdogan.toasticlib.Toastic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpFragment = this
    }

    fun alreadyHaveAccount(){
        Navigation.gecisYap(requireView(),SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())

    }

    private fun observeLiveData() {
        viewModel.answer.observe(viewLifecycleOwner) { answer ->
            answer.success?.let {
                if (it == 1) {
                    Navigation.gecisYap(requireView(), R.id.dashboardFragment)
                    Toastic.toastic(
                        context = requireContext(),
                        message = getString(R.string.register_done),
                        duration = Toastic.LENGTH_SHORT,
                        type = Toastic.SUCCESS,
                        isIconAnimated = true
                    ).show()
                } else {
                    Toastic.toastic(
                        context = requireContext(),
                        message = getString(R.string.cant_be_empty),
                        duration = Toastic.LENGTH_SHORT,
                        type = Toastic.ERROR,
                        isIconAnimated = true
                    ).show()
                }
            }
        }
    }

    fun signUpClicked(email: String?, password: String?, passwordAgain: String?) {
        if (email != "" && password != "" && passwordAgain != "") {
            email?.let { nEmail ->
                password?.let { nPassword ->
                    if (password == passwordAgain) {
                        viewModel.register(nEmail, nPassword)
                        observeLiveData()
                    } else Toastic.toastic(
                        context = requireContext(),
                        message = "Wrong Password",
                        duration = Toastic.LENGTH_SHORT,
                        type = Toastic.ERROR,
                        isIconAnimated = true
                    ).show()
                }
            }
        } else Toastic.toastic(
            context = requireContext(),
            message = "Can't Be Empty",
            duration = Toastic.LENGTH_SHORT,
            type = Toastic.ERROR,
            isIconAnimated = true
        ).show()
    }

}