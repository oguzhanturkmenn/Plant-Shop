package com.oguzhanturkmen.myplantapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.databinding.FragmentLoginBinding
import com.oguzhanturkmen.myplantapp.utils.gecisYap
import com.oguzhanturkmen.myplantapp.utils.makeToast
import com.yagmurerdogan.toasticlib.Toastic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginFragment = this

        if (viewModel.firebaseAuth.currentUser != null) {
            Navigation.gecisYap(
                requireView(),
                LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
            )
        }
    }

    private fun observeLiveData() {
        viewModel.answer.observe(viewLifecycleOwner) { answer ->
            answer.success?.let {
                if (it == 1) {
                    Navigation.gecisYap(requireView(), R.id.dashboardFragment)
                } else {
                    makeToast(requireContext(), answer.message.toString())
                }
            }
        }
    }

    fun loginClicked(email: String?, password: String?) {
        email?.let { nEmail ->
            password?.let { nPassword ->

                if (nEmail.isNotEmpty() && nPassword.isNotEmpty()) {
                    viewModel.login(nEmail, nPassword)
                    observeLiveData()

                } else Toastic.toastic(
                    context = requireContext(),
                    message = getString(R.string.cant_be_empty),
                    duration = Toastic.LENGTH_SHORT,
                    type = Toastic.ERROR,
                    isIconAnimated = true
                ).show()
            }
        }
    }

    fun goToSignup(view: View) {
        Navigation.findNavController(view)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }


}