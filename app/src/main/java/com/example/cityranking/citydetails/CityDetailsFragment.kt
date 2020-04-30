package com.example.cityranking.citydetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.cityranking.R
import com.example.cityranking.SharedViewModel
import com.example.cityranking.config.GlideApp
import com.example.cityranking.databinding.FragmentCityDetailsBinding
import com.example.cityranking.utilities.IMAGE_SIZE_MEDIUM
import com.example.cityranking.utilities.IMAGE_SIZE_ORIGINAL
import com.example.cityranking.utilities.IMAGE_SIZE_SMALL
import com.example.cityranking.utilities.Utils


class CityDetailsFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentCityDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val city = sharedViewModel.getCity()!!
        binding.cityDetailsName.text = "${city.name}, ${city.country}"
        binding.cityDetailsRank.text = "${city.mercer?.get("2019")}"
        GlideApp.with(this)
            .load(Utils.getImageRef(city.id, IMAGE_SIZE_MEDIUM))
            .into(binding.cityDetailsThumbnail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
