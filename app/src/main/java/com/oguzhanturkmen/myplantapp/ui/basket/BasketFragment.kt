package com.oguzhanturkmen.myplantapp.ui.basket

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.databinding.FragmentBasketBinding
import com.oguzhanturkmen.myplantapp.ui.dashboard.DashboardFragmentDirections
import com.oguzhanturkmen.myplantapp.utils.gecisYap
import com.yagmurerdogan.toasticlib.Toastic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : Fragment(), BasketAdapter.BasketAdapterListener {
    private lateinit var binding: FragmentBasketBinding
    private lateinit var viewModel: BasketViewModel
    private val adapter by lazy {
        BasketAdapter(
            this,
            viewModel::increase,
            viewModel::decrease
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[BasketViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basket, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModel.getAllBasket()
        binding.basketAdapter = adapter

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.readAllBasket.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)

            if (list.isEmpty()) binding.imgNoProduct.visibility = View.VISIBLE
            else binding.imgNoProduct.visibility = View.GONE


            viewModel.totalBasket()

            binding.btnPaynow.setOnClickListener {
                if (list.isNotEmpty()) {
                    viewModel.totalAmount.value?.let {
                        val action =
                            BasketFragmentDirections.actionBasketFragmentToPaymentFragment(it)
                        Navigation.gecisYap(requireView(), action)
                    }

                } else {
                    Toastic.toastic(
                        context = requireContext(),
                        message = "Empty Basket",
                        duration = Toastic.LENGTH_SHORT,
                        type = Toastic.ERROR,
                        isIconAnimated = true
                    ).show()
                }
            }

            viewModel.totalAmount.observe(viewLifecycleOwner) {
                if (it == null) binding.tvTotalPriceBasketFragment.setText(R.string.zero)
                else binding.tvTotalPriceBasketFragment.text = it.toString()
            }
        }
    }

    override fun onDeleteClicked(name: String) {
        viewModel.deleteFromBasket(name)
    }
}