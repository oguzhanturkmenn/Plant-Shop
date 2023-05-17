package com.oguzhanturkmen.myplantapp.ui.success

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.databinding.FragmentSuccessBinding
import com.oguzhanturkmen.myplantapp.utils.gecisYap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment : Fragment() {
    private val viewModel: SuccessViewModel by viewModels()
    private val args: SuccessFragmentArgs by navArgs()
    private lateinit var binding: FragmentSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_success, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.successFragment = this
        val totalAmount = args.successamount

        initializeObserver()

        binding.tvThanks.text = getString(R.string.success_payment, totalAmount.toString())
    }

    fun backToShopping() {
        Navigation.gecisYap(requireView(), R.id.action_successFragment_to_basketFragment)
    }

    private fun initializeObserver() {
        viewModel.readAllBasket.observe(viewLifecycleOwner) { basketList ->
            basketList.forEach { basketModel ->
                viewModel.deleteFromBasket(basketModel.plantName!!)
            }
        }
    }
}