package com.oguzhanturkmen.myplantapp.ui.basket

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.databinding.BasketRowBinding
import kotlin.coroutines.coroutineContext

class BasketAdapter(
    val listener: BasketAdapterListener,
    private val onIncreaseClick: (Plant) -> Unit = {},
    private val onDecreaseClick: (Plant) -> Unit = {}

) : ListAdapter<Plant, BasketAdapter.BasketHolder>(BasketPlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<BasketRowBinding>(
            inflater, R.layout.basket_row, parent, false
        )
        return BasketHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class BasketHolder(val binding: BasketRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(p: Plant) {

            binding.plant = p
            binding.executePendingBindings()

            binding.btnDeleteBasket.setOnClickListener {
                listener.onDeleteClicked(p.plantName!!)
            }

            binding.btnPlus.setOnClickListener {
                onIncreaseClick(p)
                p.count++
                Log.v("Count", p.count.toString())
                binding.tvPlantCountBasketrow.text = p.count.toString()
            }

            binding.btnMinus.setOnClickListener {
                if (p.count != 1) {
                    onDecreaseClick(p)
                    p.count--
                    binding.tvPlantCountBasketrow.text = p.count.toString()
                } else {
                    showDeleteConfirmationDialog(p.plantName!!,binding)
                }
            }
        }
    }

    private fun showDeleteConfirmationDialog(plantName: String,binding: BasketRowBinding) {
        val builder = AlertDialog.Builder(binding.root.context)
        builder
            .setMessage(R.string.confirm_remove_plant)
            .setPositiveButton(R.string.delete) { _, _ ->
                listener.onDeleteClicked(plantName)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    class BasketPlantDiffCallback : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.plantName == oldItem.plantName &&
                    oldItem.plantCount == oldItem.plantCount &&
                    oldItem.plantPrice == newItem.plantPrice
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface BasketAdapterListener {
        fun onDeleteClicked(name: String)
    }
}
