package com.example.cityranking.citylist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.cityranking.SharedViewModel
import com.example.cityranking.data.City
import com.example.cityranking.data.Resource
import com.example.cityranking.databinding.FragmentFullListBinding
import com.example.cityranking.utilities.*

/**
 * Fragment that displays full list of cities
 * Used for 7 different lists, hence spaghetti
 */
class FullListFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: FullListFragmentArgs by navArgs()
    private lateinit var dataSource: String
    private lateinit var adapter: FullListAdapter
    private var toast: Toast? = null

    private var _binding: FragmentFullListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get data source from Bundle (safe args)
        dataSource = args.dataSource
        adapter = FullListAdapter(dataSource)
        adapter.data = arrayListOf()
        val recyclerView = binding.fullListRecyclerview
        recyclerView.adapter = adapter
        setDataSource()
    }

    /**
     * downloads the data based on the source
     */
    private fun setDataSource() {
        when (dataSource) {
            MERCER_LIST -> setMercerData()
            ECONOMIST_LIST -> setEconomistData()
            MONOCLE_LIST -> setMonocleData()
            NUMBEO_LIST -> setNumbeoData()
            QS_STUDENT_LIST -> setQsData()
            MOST_VISITED_LIST -> setMostVisitedData()
            UHNW_LIST -> setUhnwData()
        }
    }

    private fun setMercerData() {
        sharedViewModel.getMercerTop25Cities.observe(viewLifecycleOwner, Observer {
            setAdapterData(it)
        })
    }

    private fun setEconomistData() {
        sharedViewModel.getEconomistTop10Cities.observe(viewLifecycleOwner, Observer {
            setAdapterData(it)
        })
    }

    private fun setMonocleData() {
        sharedViewModel.getMonocleTop25Cities.observe(viewLifecycleOwner, Observer {
            setAdapterData(it)
        })
    }

    private fun setNumbeoData() {
        sharedViewModel.getNumbeoTop25Cities.observe(viewLifecycleOwner, Observer {
            setAdapterData(it)
        })
    }

    private fun setQsData() {
        sharedViewModel.getQsTop25Cities.observe(viewLifecycleOwner, Observer {
            setAdapterData(it)
        })
    }

    private fun setMostVisitedData() {
        Log.i("varvar", "setMostVisitedData")
        sharedViewModel.getMostVisitedTop10Cities.observe(viewLifecycleOwner, Observer {
            setAdapterData(it)
        })
    }

    private fun setUhnwData() {
        sharedViewModel.getUhnwTop10Cities.observe(viewLifecycleOwner, Observer {
            setAdapterData(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Passes data to adapter
     * @param resource state of query and data
     */
    private fun setAdapterData(resource: Resource<ArrayList<City>>) {
        Log.i("varvar", "setAdapterData, res="+resource.toString())
        when (resource) {
            // toasts should eventually be replaced with something more fancy
            is Resource.Loading<*> -> showToast("Loading...")
            is Resource.Success<*> -> adapter.data = resource.data as ArrayList<City>
            is Resource.Failure<*> -> showToast("An error has ocurred:${resource.throwable.message}")
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
            toast?.setText(text)
        toast?.show()
    }

}
