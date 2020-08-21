package com.example.cityranking.citylist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cityranking.R
import com.example.cityranking.SharedViewModel
import com.example.cityranking.config.GlideApp
import com.example.cityranking.data.City
import com.example.cityranking.databinding.CityListItemBinding
import com.example.cityranking.utilities.IMAGE_SIZE_SMALL
import com.example.cityranking.utilities.Utils
import com.google.android.material.chip.Chip

/**
 * Adapter for RecyclerView that displays full list of cities
 * @param dataSource name of the list from which cities are loaded
 */
class FullListAdapter(private val dataSource: String) :
    RecyclerView.Adapter<FullListAdapter.ViewHolder>() {

    var data = arrayListOf<City>()
        set(value) {
            field = value
            // as the data won't be changing, no DiffUtil implementation
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CityListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(
            binding
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, dataSource)
    }

    class ViewHolder(binding: CityListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val root = binding.root
        private val title: TextView = binding.cityListItemName
        private val thumbnail: ImageView = binding.cityListItemImage
        private val rank: TextView = binding.cityListItemRank
        private val country: Chip = binding.cityListItemCountry
        private val population: Chip = binding.cityListItemPopulation

        /**
         * Binds data to UI
         */
        fun bind(item: City, dataSource: String) {
            title.text = "${item.name}"
            rank.text = Utils.getCityRank(item, dataSource).toString()
            country.text = "${item.country}"
            population.text = String.format("%,8d%n", item.population)
            // downloads and injects image into view with FirebaseUI
            GlideApp.with(thumbnail.context)
                .load(Utils.getImageRef(item.id, IMAGE_SIZE_SMALL))
                .centerCrop()
                .into(thumbnail)
            val bundle = bundleOf("city" to item)
            root.setOnClickListener {
                //viewModel.setCity(item)
                it.findNavController()
                    .navigate(R.id.action_fullListFragment_to_cityDetailsFragment, bundle)
            }
        }
    }

}