package com.example.cityranking.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.cityranking.SharedViewModel
import com.example.cityranking.data.Resource
import com.example.cityranking.databinding.FragmentMainBinding
import com.example.cityranking.mainscreen.list.CityRankCardSection
import com.example.cityranking.mainscreen.list.CityRankCarouselSection
import com.example.cityranking.utilities.ECONOMIST_LIST
import com.example.cityranking.utilities.MERCER_LIST
import com.example.cityranking.utilities.MONOCLE_LIST
import com.example.cityranking.utilities.MOST_VISITED_LIST
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class MainFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var toast: Toast? = null

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val mainScreenAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var mercerSection: CityRankCarouselSection
    private lateinit var economistSection: CityRankCarouselSection
    private lateinit var monocleSection: CityRankCarouselSection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMainScreenSections()
        setObservers()
    }

    private fun setMainScreenSections() {
        mercerSection = CityRankCarouselSection(
            "Mercer",
            "Mercer's Quality of living city ranking",
            MERCER_LIST,
            Resource.Loading()
        )
        economistSection = CityRankCarouselSection(
            "The Economist",
            "The Economist's Global Liveability Index",
            ECONOMIST_LIST,
            Resource.Loading()
        )
        monocleSection = CityRankCarouselSection(
            "Monocle",
            "Monocle: Quality of life survey",
            MONOCLE_LIST,
            Resource.Loading()
        )

        mainScreenAdapter.update(listOf(
            mercerSection,
            CityRankCardSection("Most visited cities", MOST_VISITED_LIST),
            economistSection,
            monocleSection
        ))
        binding.mainScreenSections.run {
            adapter = mainScreenAdapter
        }
    }

    private fun setObservers() {
        sharedViewModel.getMercerTop5Cities.observe(viewLifecycleOwner, Observer {
            mercerSection.update(it)
        })
        sharedViewModel.getEconomistTop5Cities.observe(viewLifecycleOwner, Observer {
            economistSection.update(it)
        })
        sharedViewModel.getMonocleTop5Cities.observe(viewLifecycleOwner, Observer {
            monocleSection.update(it)
        })
    }

    /**
     * Displays single Loading / Error status toast
     * new toast message will replace the old one
     * @param text toast message
     * @param duration toast duration
     */
    private fun showToast(text: String) {
        if (toast == null)
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        else
            toast!!.setText(text)
        toast!!.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
