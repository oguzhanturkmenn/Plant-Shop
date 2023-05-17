package com.oguzhanturkmen.myplantapp.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.databinding.FragmentFavoritePlantBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePlantFragment : Fragment() {
    private lateinit var binding: FragmentFavoritePlantBinding
    private val favViewModel: FavoriteViewModel by viewModels()
    private val adapter by lazy { FavoriteAdapter(favViewModel::deleteFromFav) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_plant, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favViewModel.getAllFavPlants()
        binding.adapter = adapter

        observeLiveData()
    }

    private fun observeLiveData() {
        favViewModel.favList.observe(viewLifecycleOwner) {
            adapter.submitList(it)

            if (it.isEmpty()) binding.imgNoProductFav.visibility = View.VISIBLE
            else binding.imgNoProductFav.visibility = View.GONE
        }

    }

}