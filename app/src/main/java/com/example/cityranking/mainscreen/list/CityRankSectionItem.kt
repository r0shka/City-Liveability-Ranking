package com.example.cityranking.mainscreen.list

import android.view.View
import androidx.navigation.findNavController
import com.example.cityranking.R
import com.example.cityranking.data.City
import com.example.cityranking.databinding.MainScreenSectionItemBinding
import com.example.cityranking.mainscreen.MainFragmentDirections
import com.example.cityranking.mainscreen.ViewPagerAdapter
import com.example.cityranking.utilities.Utils
import com.xwray.groupie.viewbinding.BindableItem

class CityRankSectionItem(
    private val title: String,
    private val description: String,
    private val dataSource: String,
    private val data: ArrayList<City>
) : BindableItem<MainScreenSectionItemBinding>() {

    override fun bind(viewBinding: MainScreenSectionItemBinding, position: Int) {
        viewBinding.sectionItemTitle.text = title
        viewBinding.sectionItemDescription.text = description
        viewBinding.sectionItemSeeMore.setOnClickListener { view ->
            val action =
                MainFragmentDirections.actionMainFragmentToFullListFragment(dataSource)
            view.findNavController().navigate(action)
        }
        Utils.setupViewPagerAnimation(viewBinding.sectionItemViewPager, viewBinding.root.context)
        val adapter = ViewPagerAdapter(dataSource)
        adapter.data = data
        viewBinding.sectionItemViewPager.adapter = adapter

    }

    override fun getLayout(): Int = R.layout.main_screen_section_item

    override fun initializeViewBinding(view: View): MainScreenSectionItemBinding {
        return MainScreenSectionItemBinding.bind(view)
    }
}