package com.oguzhanturkmen.myplantapp.ui.plantdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.databinding.FragmentPlantDetailsBinding
import com.yagmurerdogan.toasticlib.Toastic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPlantDetailsBinding
    private val viewModel: PlantDetailViewModel by viewModels()
    private val args: PlantDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_plant_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        arguments?.let {
            val gelenData = PlantDetailsFragmentArgs.fromBundle(it).plantDetailData
            binding.plants = gelenData
        }
 */
        val product = args.plantDetailData
        binding.plant = product


        binding.btnAddToBasketDetails.setOnClickListener {

            viewModel.addToBasket(
                product.let { product ->
                    Plant(
                        product.id,
                        product.plantDescription,
                        product.plantImage,
                        product.plantPrice,
                        product.plantName,
                        product.plantCount,
                        product.plantEmail
                    )

                }
            )
            Toastic.toastic(
                context = requireContext(),
                message = "Added To Basket",
                duration = Toastic.LENGTH_SHORT,
                type = Toastic.SUCCESS,
                isIconAnimated = true
            ).show()        }

    }
}



