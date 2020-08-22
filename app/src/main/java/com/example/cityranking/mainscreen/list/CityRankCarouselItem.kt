package com.example.cityranking.mainscreen.list

import android.view.View
import androidx.navigation.findNavController
import com.example.cityranking.R
import com.example.cityranking.data.City
import com.example.cityranking.databinding.CityRankCarouselItemBinding
import com.example.cityranking.mainscreen.MainFragmentDirections
import com.example.cityranking.mainscreen.ViewPagerAdapter
import com.example.cityranking.utilities.LOADING_ITEM
import com.example.cityranking.utilities.Utils
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class CityRankCarouselItem(
    private val title: String,
    private val description: String,
    private val dataSource: String,
    private val data: ArrayList<City>
) : BindableItem<CityRankCarouselItemBinding>() {

    override fun bind(viewBinding: CityRankCarouselItemBinding, position: Int) {
        viewBinding.sectionItemTitle.text = title
        viewBinding.sectionItemDescription.text = description
        viewBinding.sectionItemSeeMore.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragmentToFullListFragment(dataSource)
            it.findNavController().navigate(action)
        }
        Utils.setupViewPagerAnimation(viewBinding.sectionItemViewPager, viewBinding.root.context)
        val adapter = ViewPagerAdapter(dataSource)
        if(data.isEmpty()){
            data.add(City(LOADING_ITEM))
        }
        adapter.data = data
        viewBinding.sectionItemViewPager.adapter = adapter

    }

    override fun getLayout(): Int = R.layout.city_rank_carousel_item

    override fun initializeViewBinding(view: View): CityRankCarouselItemBinding {
        return CityRankCarouselItemBinding.bind(view)
    }

    override fun isSameAs(other: Item<*>): Boolean {
        return other is CityRankCarouselItem
    }
}