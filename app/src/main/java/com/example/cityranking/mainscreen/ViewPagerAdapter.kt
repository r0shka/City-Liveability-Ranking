package com.example.cityranking.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cityranking.R
import com.example.cityranking.config.GlideApp
import com.example.cityranking.data.City
import com.example.cityranking.databinding.CityViewpagerItemBinding
import com.example.cityranking.utilities.IMAGE_SIZE_SMALL
import com.example.cityranking.utilities.Utils


/**
 * Adapter used for ViewPager2s in Main Fragment.
 * Would also work with a RecyclerView
 */
class ViewPagerAdapter(private val dataSource: String) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    var data = arrayListOf<City>()
        set(value) {
            field = value
            // as the data won't be changing, no DiffUtil implementation
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CityViewpagerItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, dataSource)
    }

    class ViewHolder(binding: CityViewpagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val root = binding.root
        private val title: TextView = binding.cityViewpagerItemName
        private val thumbnail: ImageView = binding.cityViewpagerItemImage
        private val rank: TextView = binding.cityViewpagerItemRank

        /**
         * Binds data to UI
         */
        fun bind(item: City, listName: String) {
            title.text = item.name
            rank.text = Utils.getCityRank(item, listName).toString()
            // downloads and injects image into view with FirebaseUI for Storage
            GlideApp.with(thumbnail.context)
                .load(Utils.getImageRef(item.id, IMAGE_SIZE_SMALL))
                .centerCrop()
                .into(thumbnail)
            val bundle = bundleOf("city" to item)
            root.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_mainFragment_to_cityDetailsFragment, bundle)

            }
        }
    }

}