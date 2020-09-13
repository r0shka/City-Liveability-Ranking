package com.example.cityranking.citydetails

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.chip.Chip
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
        addChips()
        setupChart()
        setData()
    }

    private fun setupChart() {
        city_details_chart.setViewPortOffsets(0F, 0F, 0F, 0F);
        city_details_chart.setBackgroundColor(Color.rgb(190, 180, 255));
        city_details_chart.description.isEnabled = false
        city_details_chart.setTouchEnabled(false)
        city_details_chart.isDragEnabled = false
        city_details_chart.setScaleEnabled(false)
        city_details_chart.setPinchZoom(false)
        city_details_chart.axisLeft.isInverted = true
        city_details_chart.axisLeft.setDrawGridLines(false)
        city_details_chart.axisRight.setDrawGridLines(false)
        city_details_chart.xAxis.setDrawGridLines(false)
        city_details_chart.setDrawGridBackground(false)
        city_details_chart.legend.isEnabled = false
        city_details_chart.animateY(1000)
    }

    private fun addChips() {
        city?.mercer?.let {
            Log.i("varvar", "contains mercer")
            val chip = Chip(context)
            chip.text = "Mercer"
            binding.chipGroup.addView(chip)
        }
        city?.eiu?.let {
            Log.i("varvar", "contains economist")
            val chip = Chip(context)
            chip.text = "Economist"
            binding.chipGroup.addView(chip)
        }
        city?.monocle?.let {
            Log.i("varvar", "contains monocle")
            val chip = Chip(context)
            chip.text = "Monocle"
            binding.chipGroup.addView(chip)
        }
        city?.numbeo?.let {
            Log.i("varvar", "contains numbeo")
            val chip = Chip(context)
            chip.text = "Numbeo"
            binding.chipGroup.addView(chip)
        }
        city?.qs?.let {
            Log.i("varvar", "contains qs")
            val chip = Chip(context)
            chip.text = "QS"
            binding.chipGroup.addView(chip)
        }
    }

    private fun setData() {
        val values: ArrayList<Entry> = ArrayList()
        city?.mercer?.let {
            Log.i("varvar", "contains mercer")
            it.toSortedMap().forEach { (key, value) ->
                Log.i("varvar", " key=$key, value=$value")
                values.add(Entry(key.toFloat(), value.toFloat()))
            }
        }
        city?.eiu?.let {
            Log.i("varvar", "contains economist")
            it.forEach { (key, value) -> Log.i("varvar", " key=$key, value=$value") }
        }
        city?.monocle?.let {
            Log.i("varvar", "contains monocle")
            it.forEach { (key, value) -> Log.i("varvar", " key=$key, value=$value") }
        }
        city?.numbeo?.let {
            Log.i("varvar", "contains numbeo")
            it.forEach { (key, value) -> Log.i("varvar", " key=$key, value=$value") }
        }
        city?.qs?.let {
            Log.i("varvar", "contains qs")
            it.forEach { (key, value) -> Log.i("varvar", " key=$key, value=$value") }
        }
        val set1: LineDataSet
        if (city_details_chart.data != null &&
            city_details_chart.data.dataSetCount > 0
        ) {
            set1 = city_details_chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            city_details_chart.getData().notifyDataChanged()
            city_details_chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(true)
            set1.lineWidth = 1.8f
            set1.circleRadius = 4f
            set1.setCircleColor(Color.rgb(90, 50, 255))
            set1.highLightColor = Color.rgb(0, 255, 0)
            set1.color = Color.rgb(90, 50, 255)
            set1.fillColor = Color.rgb(255, 255, 255)
            set1.fillAlpha = 255
            set1.setDrawHorizontalHighlightIndicator(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider ->
                    city_details_chart.getAxisLeft().getAxisMinimum()
                }

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
