package com.oguzhanturkmen.myplantapp.ui.dashboard

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.models.PlantFavModel
import com.oguzhanturkmen.myplantapp.databinding.PlantRowBinding
import com.oguzhanturkmen.myplantapp.utils.gecisYap
import com.oguzhanturkmen.myplantapp.utils.makeToast
import com.yagmurerdogan.toasticlib.Toastic
import kotlin.coroutines.coroutineContext

class DashboardAdapter(
    private val onRemoveFav: (PlantFavModel) -> Unit = {},
    private val onAddFav: (PlantFavModel) -> Unit = {},
    private val onAddBasket: (Plant) -> Unit = {}

) : ListAdapter<Plant, DashboardAdapter.DashboardHolder>(DashboardPlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardHolder {
        val binding: PlantRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.plant_row, parent, false
        )
        return DashboardHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardHolder, position: Int) {
        holder.bind(currentList[position])

    }

    inner class DashboardHolder(val binding: PlantRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(p: Plant) {
            binding.plant = p
            binding.executePendingBindings()

            binding.llFav.setOnClickListener {
                if (p.plantFav) {
                    binding.imgHeartPlantrow.setImageResource(R.drawable.ic_heart)
                    p.plantFav = false
                    onRemoveFav(PlantFavModel(p.id, p.plantImage, p.plantPrice, p.plantName))
                } else {
                    binding.imgHeartPlantrow.setImageResource(R.drawable.ic_heart_faved)
                    p.plantFav = true
                    onAddFav(PlantFavModel(p.id, p.plantImage, p.plantPrice, p.plantName))
                    Log.v("Fav", p.plantFav.toString())
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
                    message = binding.root.context.getString(R.string.added_to_basket),
                    duration = Toastic.LENGTH_SHORT,
                    type = Toastic.SUCCESS,
                    isIconAnimated = true
                ).show()
            }

            //Navigate to PlantDetailsFragment
            binding.cardView.setOnClickListener {
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToPlantDetailsFragment(p)
                Navigation.gecisYap(it, action)
            }

        }
    }

    class DashboardPlantDiffCallback : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.plantName == oldItem.plantName &&
                    oldItem.plantPrice == newItem.plantPrice
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount() = currentList.size
}