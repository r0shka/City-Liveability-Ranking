package com.example.cityranking.mainscreen.list

import android.view.View
import androidx.navigation.findNavController
import com.example.cityranking.R
import com.example.cityranking.databinding.CityRankCardItemBinding
import com.example.cityranking.mainscreen.MainFragmentDirections
import com.xwray.groupie.viewbinding.BindableItem

class CityRankCardItem(
    private val title: String,
    private val dataSource: String
) : BindableItem<CityRankCardItemBinding>() {

    override fun bind(viewBinding: CityRankCardItemBinding, position: Int) {
        viewBinding.cardTitle.text = title
        viewBinding.seeMoreButton.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragmentToFullListFragment(dataSource)
            it.findNavController().navigate(action)
        }
    }

    override fun getLayout(): Int = R.layout.city_rank_card_item

    override fun initializeViewBinding(view: View): CityRankCardItemBinding {
        return CityRankCardItemBinding.bind(view)
    }

}