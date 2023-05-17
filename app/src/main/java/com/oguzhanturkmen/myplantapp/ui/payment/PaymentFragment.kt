package com.oguzhanturkmen.myplantapp.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.databinding.FragmentBasketBinding
import com.oguzhanturkmen.myplantapp.databinding.FragmentPaymentBinding
import com.oguzhanturkmen.myplantapp.databinding.FragmentPlantDetailsBinding
import com.oguzhanturkmen.myplantapp.ui.plantdetails.PlantDetailsFragmentArgs
import com.oguzhanturkmen.myplantapp.utils.gecisYap
import com.oguzhanturkmen.myplantapp.utils.showErrorSnackBar
import com.oguzhanturkmen.myplantapp.utils.trimmedText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding

    private val args: PaymentFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalAmount = args.amount
        binding.btnPay.setOnClickListener {
            if (validatePaymentDetails()) {
                val action =
                    PaymentFragmentDirections.actionPaymentFragmentToSuccessFragment(totalAmount)
                Navigation.gecisYap(requireView(),action)
            }
        }    }

    private fun validatePaymentDetails(): Boolean {
        with(binding) {
            val cardName = etCardName.trimmedText()
            val cardNumber = etCardNumber.trimmedText()
            val expiryDate = etExpiryDate.trimmedText()
            val cvv = etCvv.trimmedText()
            val address = etAddress.trimmedText()


            return when {
                cardName.isEmpty() -> showError(getString(R.string.required_card_name))
                cardNumber.isEmpty() -> showError(getString(R.string.required_card_number))
                expiryDate.isEmpty() -> showError(getString(R.string.required_expiry))
                cvv.isEmpty() -> showError(getString(R.string.required_cvv))
                address.isEmpty() -> showError(getString(R.string.required_address))
                else -> true
            }
        }
    }

    private fun showError(errorMsg: String): Boolean {
        requireView().showErrorSnackBar(errorMsg, true)
        return false
    }

}