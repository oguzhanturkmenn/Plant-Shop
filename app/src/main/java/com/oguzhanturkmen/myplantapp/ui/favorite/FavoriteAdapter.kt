package com.oguzhanturkmen.myplantapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.models.PlantFavModel
import com.oguzhanturkmen.myplantapp.databinding.FavRowBinding

class FavoriteAdapter(private val onRemoveFavClick: (PlantFavModel) -> Unit = {}) :
    ListAdapter<PlantFavModel, FavoriteAdapter.FavoriteHolder>(FavoritePlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FavRowBinding>(
            inflater, R.layout.fav_row, parent, false
        )
        return FavoriteHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FavoriteHolder(val binding: FavRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(p: PlantFavModel) {
            binding.plant = Plant(
                p.id,
                "",
                p.plantImage,
                p.plantPrice,
                p.plantName,
                0,
                ""
            )
            binding.executePendingBindings()

            binding.imgHeartFavRow.setOnClickListener {
                onRemoveFavClick(p)
            }
            
        }
    }

    class FavoritePlantDiffCallback : DiffUtil.ItemCallback<PlantFavModel>() {
        override fun areItemsTheSame(oldItem: PlantFavModel, newItem: PlantFavModel): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.plantName == oldItem.plantName &&
                    oldItem.plantPrice == newItem.plantPrice
        }

        override fun areContentsTheSame(oldItem: PlantFavModel, newItem: PlantFavModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

}