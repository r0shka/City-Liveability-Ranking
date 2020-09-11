package com.example.cityranking.citydetails

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cityranking.SharedViewModel
import com.example.cityranking.config.GlideApp
import com.example.cityranking.data.City
import com.example.cityranking.databinding.FragmentCityDetailsBinding
import com.example.cityranking.utilities.IMAGE_SIZE_MEDIUM
import com.example.cityranking.utilities.Utils
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import kotlinx.android.synthetic.main.fragment_city_details.*


class CityDetailsFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentCityDetailsBinding? = null
    private val binding get() = _binding!!
    private var city: City? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        city = arguments?.getParcelable("city")
        binding.cityDetailsName.text = "${city?.name}, ${city?.country}"
        GlideApp.with(this)
            .load(Utils.getImageRef(city?.id, IMAGE_SIZE_MEDIUM))
            .into(binding.cityDetailsThumbnail)
        setupChart()
        setData()
    }

    private fun setupChart(){
        city_details_chart.setViewPortOffsets(0F, 0F, 0F, 0F);
        city_details_chart.setBackgroundColor(Color.rgb(31, 128, 255));
        city_details_chart.description.isEnabled = false
        city_details_chart.setTouchEnabled(false)
        city_details_chart.isDragEnabled = false
        city_details_chart.setScaleEnabled(false)
        city_details_chart.setPinchZoom(false)
        city_details_chart.setDrawGridBackground(false)
        city_details_chart.legend.isEnabled = false
    }

    private fun setData() {
        val values: ArrayList<Entry> = ArrayList()
        val mercer: ArrayList<Pair<String?,Int?>> = ArrayList()
        city?.mercer?.forEach{ (key,value) -> mercer.add(Pair(key,value))}

        mercer.forEach {
            values.add(Entry(it.first!!.toFloat(), it.second!!.toFloat()))
        }
        val set1: LineDataSet
        if (city_details_chart.data != null &&
            city_details_chart.data.dataSetCount > 0
        ) {
            set1 = city_details_chart.data.getDataSetByIndex(0) as LineDataSet
            set1.setValues(values)
            city_details_chart.getData().notifyDataChanged()
            city_details_chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(false)
            set1.lineWidth = 1.8f
            set1.circleRadius = 4f
            set1.setCircleColor(Color.WHITE)
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.color = Color.WHITE
            set1.fillColor = Color.WHITE
            set1.fillAlpha = 100
            set1.setDrawHorizontalHighlightIndicator(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> city_details_chart.getAxisLeft().getAxisMinimum() }

            // create a data object with the data sets
            val data = LineData(set1)
            //data.setValueTypeface(tfLight)
            data.setValueTextSize(9f)
            data.setDrawValues(true)

            // set data
            city_details_chart.setData(data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
