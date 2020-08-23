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
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import kotlinx.android.synthetic.main.fragment_city_details.*


class CityDetailsFragment : Fragment() {

    private var chart = city_details_chart

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentCityDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val city = arguments?.getParcelable<City>("city")
        binding.cityDetailsName.text = "${city?.name}, ${city?.country}"
        GlideApp.with(this)
            .load(Utils.getImageRef(city?.id, IMAGE_SIZE_MEDIUM))
            .into(binding.cityDetailsThumbnail)
        setupChart()
    }

    private fun setupChart(){
        chart.setViewPortOffsets(0F, 0F, 0F, 0F);
        chart.setBackgroundColor(Color.rgb(104, 241, 175));
        chart.description.isEnabled = false
        chart.setTouchEnabled(false)
        chart.isDragEnabled = false
        chart.setScaleEnabled(false)
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)
        chart.legend.isEnabled = false
    }

    private fun setData(count: Int, range: Float) {
        val values: ArrayList<Entry> = ArrayList()
        for (i in 0 until count) {
            val value = (Math.random() * (range + 1)).toFloat() + 20
            values.add(Entry(value, i.toFloat()))
        }
        val set1: LineDataSet
        if (chart.getData() != null &&
            chart.getData().getDataSetCount() > 0
        ) {
            set1 = chart.getData().getDataSetByIndex(0)
            set1.setValues(values)
            chart.getData().notifyDataChanged()
            chart.notifyDataSetChanged()
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
            set1.setDrawHorizontalHighlightIndicator(false)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> chart.getAxisLeft().getAxisMinimum() }

            // create a data object with the data sets
            val data = LineData(set1)
            data.setValueTypeface(tfLight)
            data.setValueTextSize(9f)
            data.setDrawValues(false)

            // set data
            chart.setData(data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
