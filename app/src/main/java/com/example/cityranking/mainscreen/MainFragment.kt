package com.example.cityranking.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.cityranking.R
import com.example.cityranking.SharedViewModel
import com.example.cityranking.data.City
import com.example.cityranking.data.Resource
import com.example.cityranking.databinding.FragmentMainBinding
import com.example.cityranking.utilities.ECONOMIST_LIST
import com.example.cityranking.utilities.MERCER_LIST
import com.example.cityranking.utilities.MONOCLE_LIST

class MainFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var toast: Toast? = null

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMercerListPreview()
        setEconomistListPreview()
        setMonocleListPreview()
        binding.viewMercerListButton.setOnClickListener {
            navigateToFullListFragment(
                it,
                MERCER_LIST
            )
        }
        binding.viewEconomistListButton.setOnClickListener {
            navigateToFullListFragment(
                it,
                ECONOMIST_LIST
            )
        }
        binding.viewMonocleListButton.setOnClickListener {
            navigateToFullListFragment(
                it,
                MONOCLE_LIST
            )
        }
    }

    /**
     * Binds adapter to viewpager and fetches top5 from Mercer list
     */
    private fun setMercerListPreview() {
        val adapter =
            ViewPagerAdapter(MERCER_LIST, sharedViewModel)
        val viewPager = binding.mercerViewpager
        viewPager.adapter = adapter
        setupViewPagerAnimation(viewPager)
        adapter.data = arrayListOf()
        sharedViewModel.getMercerTop5Cities.observe(viewLifecycleOwner, Observer {
            setAdapterData(it, adapter)
        })
    }

    /**
     * Binds adapter to viewpager and fetches top5 from Economist list
     */
    private fun setEconomistListPreview() {
        val adapter =
            ViewPagerAdapter(ECONOMIST_LIST, sharedViewModel)
        val viewPager = binding.economistViewpager
        viewPager.adapter = adapter
        setupViewPagerAnimation(viewPager)
        adapter.data = arrayListOf()
        sharedViewModel.getEconomistTop5Cities.observe(viewLifecycleOwner, Observer {
            setAdapterData(it, adapter)
        })
    }

    /**
     * Binds adapter to viewpager and fetches top5 from Economist list
     */
    private fun setMonocleListPreview() {
        val adapter =
            ViewPagerAdapter(MONOCLE_LIST, sharedViewModel)
        val viewPager = binding.monocleViewpager
        viewPager.adapter = adapter
        setupViewPagerAnimation(viewPager)
        adapter.data = arrayListOf()
        sharedViewModel.getMonocleTop5Cities.observe(viewLifecycleOwner, Observer {
            setAdapterData(it, adapter)
        })
    }

    /**
     * Passes data to adapter
     * @param resource state of query and data
     * @param adapter adapter for horizontal ViewPager2
     */
    private fun setAdapterData(resource: Resource<ArrayList<City>>, adapter: ViewPagerAdapter) {
        when (resource) {
            // toasts should eventually be replaced with something more fancy
            is Resource.Loading<*> -> {
                showToast("Loading...")
                binding.mainScreenRoot.visibility = View.GONE
            }
            is Resource.Success<*> -> {
                adapter.data = resource.data as ArrayList<City>
                binding.mainScreenRoot.visibility = View.VISIBLE
            }
            is Resource.Failure<*> -> {
                showToast("An error has ocurred:${resource.throwable.message}")
                binding.mainScreenRoot.visibility = View.GONE
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

    /**
     * Attaches transition zoom in/out animation to a viewpager
     * @param viewPager2
     */
    private fun setupViewPagerAnimation(viewPager2: ViewPager2) {
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)
        viewPager2.setPageTransformer(
            ViewPagerPageTransformation(
                offsetPx + pageMarginPx
            )
        )
    }

    /**
     * Navigates to full list screen
     * @param view view associated with the nav controller
     * @param listName name of the list to display
     */
    private fun navigateToFullListFragment(view: View, dataSource: String) {
        // add safe arg of listName
        val action = MainFragmentDirections.actionMainFragmentToFullListFragment(dataSource)
        view.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
