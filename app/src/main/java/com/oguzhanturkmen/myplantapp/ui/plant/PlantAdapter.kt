package com.oguzhanturkmen.myplantapp.ui.plant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.models.PlantFavModel
import com.oguzhanturkmen.myplantapp.databinding.PlantRowBinding
import com.oguzhanturkmen.myplantapp.ui.dashboard.DashboardViewModel
import com.oguzhanturkmen.myplantapp.utils.gecisYap
import com.yagmurerdogan.toasticlib.Toastic
import java.util.*
import kotlin.collections.ArrayList

class PlantAdapter(
    private val onRemoveFav: (PlantFavModel) -> Unit = {},
    private val onAddFav: (PlantFavModel) -> Unit = {},
    private val onAddBasket: (Plant) -> Unit = {}
) :

    ListAdapter<Plant, PlantAdapter.PlantHolder>(PlantDiffCallback()), Filterable {
    private var filteredList = ArrayList<Plant>()
    var plantList = listOf<Plant>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<PlantRowBinding>(
            inflater, R.layout.plant_row, parent, false
        )
        return PlantHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantHolder, position: Int) {
        val list = currentList[position]
        holder.bind(list)
    }

    inner class PlantHolder(val binding: PlantRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(p: Plant) {
            binding.executePendingBindings()
            binding.plant = p

            binding.llFav.setOnClickListener {
                if (p.plantFav) {
                    binding.imgHeartPlantrow.setImageResource(R.drawable.ic_heart)
                    p.plantFav = false
                    onRemoveFav(PlantFavModel(p.id, p.plantImage, p.plantPrice, p.plantName))
                } else {
                    binding.imgHeartPlantrow.setImageResource(R.drawable.ic_heart_faved)
                    p.plantFav = true
                    onAddFav(PlantFavModel(p.id, p.plantImage, p.plantPrice, p.plantName))
                }
            }

            if (p.plantFav) {
                binding.imgHeartPlantrow.setImageResource(R.drawable.ic_heart_faved)
            } else {
                binding.imgHeartPlantrow.setImageResource(R.drawable.ic_heart)
            }

            binding.btnAddToBasketPlantRow.setOnClickListener {
                onAddBasket(
                    p.let { product ->
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
                    context = binding.root.context,
                    message = "Added To Basket",
                    duration = Toastic.LENGTH_SHORT,
                    type = Toastic.SUCCESS,
                    isIconAnimated = true
                ).show()
            }

            //Navigate to PlantDetailsFragment
            binding.cardView.setOnClickListener {
                val action =
                    PlantFragmentDirections.actionPlantFragmentToPlantDetailsFragment(p)
                Navigation.gecisYap(it, action)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val list = plantList
                val filtered = ArrayList<Plant>()

                if (constraint.isBlank() or constraint.isEmpty()) {
                    filtered.addAll(list)
                } else {
                    val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()

                    list.forEach {
                        if (it.plantName?.lowercase(Locale.ROOT)?.contains(filterPattern) == true) {
                            filtered.add(it)
                        }
                    }
                }

                val result = FilterResults()
                result.values = filtered
                result.count = filtered.size
                filteredList = filtered

                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val items = ArrayList<Plant>(filteredList)
                submitList(items)
            }
        }
    }

    class PlantDiffCallback : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.plantName == newItem.plantName &&
                    oldItem.plantPrice == newItem.plantPrice
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun getItemCount() = currentList.size
}


