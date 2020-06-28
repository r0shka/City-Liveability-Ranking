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
import com.example.cityranking.data.City
import com.example.cityranking.data.MainScreenSection
import com.example.cityranking.data.Resource
import com.example.cityranking.databinding.FragmentMainBinding
import com.example.cityranking.utilities.ECONOMIST_LIST
import com.example.cityranking.utilities.MERCER_LIST
import com.example.cityranking.utilities.MONOCLE_LIST

class MainFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var toast: Toast? = null
    private val mainScreenSections = arrayListOf<MainScreenSection>()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainScreenSections.clear()
        setMainScreenSections()
    }

    private fun setMainScreenSections() {
        val adapter = MainScreenAdapter(sharedViewModel, requireContext())
        val viewPager = binding.mainScreenSections
        viewPager.adapter = adapter
        sharedViewModel.getMercerTop5Cities.observe(viewLifecycleOwner, Observer {
            createSegment(
                "Mercer",
                "Mercer's top 5 cities",
                MERCER_LIST,
                it,
                adapter
            )
        })
        sharedViewModel.getEconomistTop5Cities.observe(viewLifecycleOwner, Observer {
            createSegment(
                "The Economist",
                "The Economist's top 5 cities",
                ECONOMIST_LIST,
                it,
                adapter
            )
        })
        sharedViewModel.getMonocleTop5Cities.observe(viewLifecycleOwner, Observer {
            createSegment(
                "Monocle",
                "Monocle top 5 cities",
                MONOCLE_LIST,
                it,
                adapter
            )
        })
    }

    private fun createSegment(
        title: String,
        description: String,
        dataSource: String,
        resource: Resource<ArrayList<City>>,
        adapter: MainScreenAdapter
    ) {
        when (resource) {
            // toasts should eventually be replaced with something more fancy
            is Resource.Loading<*> -> {
                showToast("Loading...")
            }
            is Resource.Success<*> -> {
                mainScreenSections.add(
                    MainScreenSection(
                        title,
                        description,
                        dataSource,
                        carouselItems = resource.data as ArrayList<City>
                    )
                )
                adapter.data = mainScreenSections
            }
            is Resource.Failure<*> -> {
                showToast("An error has ocurred:${resource.throwable.message}")
            }
        }
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
