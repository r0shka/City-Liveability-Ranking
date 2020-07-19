package com.example.cityranking.mainscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cityranking.SharedViewModel
import com.example.cityranking.data.MainScreenSection
import com.example.cityranking.databinding.MainScreenSectionItemBinding
import com.example.cityranking.utilities.Utils

class MainScreenAdapter(
    private val viewModel: SharedViewModel,
    private val context: Context
) :
    RecyclerView.Adapter<MainScreenAdapter.ViewHolder>() {

    var data = ArrayList<MainScreenSection>(6)
        set(value) {
            field = value
            // as the data won't be changing, no DiffUtil implementation
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainScreenSectionItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, viewModel, context)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class ViewHolder(
        binding: MainScreenSectionItemBinding,
        val viewModel: SharedViewModel,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val title: TextView = binding.sectionItemTitle
        private val description: TextView = binding.sectionItemDescription
        private val viewPager: ViewPager2 = binding.sectionItemViewPager
        private val seeMoreButton: Button = binding.sectionItemSeeMore

        fun bind(item: MainScreenSection) {
            val adapter = ViewPagerAdapter(item.dataSource, viewModel)
            title.text = item.title
            description.text = item.description
            viewPager.adapter = adapter
            Utils.setupViewPagerAnimation(viewPager, context)
            adapter.data = item.carouselItems
            seeMoreButton.setOnClickListener { view ->
                val action =
                    MainFragmentDirections.actionMainFragmentToFullListFragment(item.dataSource)
                view.findNavController().navigate(action)
            }
        }
    }
}