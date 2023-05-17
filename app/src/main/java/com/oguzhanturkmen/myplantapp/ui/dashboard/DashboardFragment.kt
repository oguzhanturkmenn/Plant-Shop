package com.oguzhanturkmen.myplantapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.databinding.FragmentDashboardBinding
import com.oguzhanturkmen.myplantapp.ui.favorite.FavoriteAdapter
import com.oguzhanturkmen.myplantapp.utils.gecisYap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val dashboardAdapter by lazy { DashboardAdapter(dashboardViewModel::deleteFavPlant,dashboardViewModel::addToFav,dashboardViewModel::addToBasket) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dashboardFragment = this
        binding.dashboardAdapter = dashboardAdapter

        dashboardViewModel.getDatas()
        dashboardViewModel.getLiveUser()
        observeLiveData()
    }

    private fun observeLiveData() {
        dashboardViewModel.list.observe(viewLifecycleOwner) {
            dashboardAdapter.submitList(it)
        }
        dashboardViewModel.userLive.observe(viewLifecycleOwner) {
            Log.e("deneme", it.toString())
            it.let {
                binding.user = it
                Log.d("dede",binding.user.toString())
            }
        }
    }

    fun letsSeeAllPlantsClicked() {
        Navigation.gecisYap(requireView(), R.id.plantFragment)
    }
}