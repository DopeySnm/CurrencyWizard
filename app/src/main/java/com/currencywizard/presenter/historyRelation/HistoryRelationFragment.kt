package com.currencywizard.presenter.historyRelation

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.currencywizard.DateHelper
import com.currencywizard.R
import com.currencywizard.data.states.UiState
import com.currencywizard.databinding.FragmentHistoryRelationBinding
import com.currencywizard.di.appComponent
import com.currencywizard.di.viewModel.ViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class HistoryRelationFragment : Fragment(R.layout.fragment_history_relation) {

    private val binding: FragmentHistoryRelationBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: HistoryRelationViewModel by viewModels { viewModelFactory }

    private var textRelation = ""

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textRelation = "History relations ${arguments?.getString("from").toString()}" +
                " and ${arguments?.getString("to").toString()} "

        initializeLineChart()

        initializeLineView()

        initializeButton()

        onFroTheThreeMonthsClick()
    }

    private fun initializeLineChart() {

        val lineChart: LineChart = binding.lineChart

        val textDescriptionSize = 15f
        val textColor = Color.WHITE
        val lineColor = Color.WHITE
        val descriptionText = "There could be your advertisement here :)"

        viewModel.ratesLiveData.observe(viewLifecycleOwner) {
            when(it){
                is UiState.Success -> {
                    val entries = it.value.map {rate ->
                        Entry(
                            DateHelper.dateToFloat(DateHelper.formatter, rate.date),
                            rate.result.toFloat()
                        )
                    }

                    val dataSet = LineDataSet(entries, textRelation)
                    dataSet.color = lineColor
                    dataSet.setDrawCircles(true)
                    dataSet.circleRadius = 3f
                    dataSet.circleHoleRadius = 2f
                    dataSet.setDrawValues(false)

                    // fill
                    // green-red
//                    val startColor = Color.GREEN
//                    val endColor = Color.RED
                    // orange-transparent
                    val startColor = Color.parseColor("#FF5722")
                    val endColor =  Color.TRANSPARENT
                    val gradientDrawable = GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        intArrayOf(startColor, endColor)
                    )
                    dataSet.fillDrawable = gradientDrawable
                    dataSet.setDrawFilled(true)

                    // format line
                    dataSet.mode = LineDataSet.Mode.STEPPED

                    val lineData = LineData(dataSet)

                    lineChart.data = lineData
                }
                is UiState.Failure -> {}
                is UiState.Loading -> {}
            }
        }



        val xAxis: XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = textColor

        val leftAxis: YAxis = lineChart.axisLeft
        leftAxis.textColor = textColor
        val rightAxis: YAxis = lineChart.axisRight
        rightAxis.isEnabled = false

        lineChart.description.text = descriptionText
        lineChart.description.textSize = textDescriptionSize
        lineChart.description.textColor = textColor
        lineChart.legend.textSize = textDescriptionSize
        lineChart.legend.textColor = textColor
        lineChart.animateXY(2000, 2000)

        // customizing xAxis to date
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return DateHelper.formatter.format(Date(value.toLong()))
            }
        }

        xAxis.setLabelCount(5, true)

        xAxis.granularity = 0.5f

        lineChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                Toast.makeText(
                    binding.root.context,
                    "Selected Value: ${e?.y} ${DateHelper.formatter.format(Date(e?.x!!.toLong()))}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onNothingSelected() {}
        })
    }

    private fun initializeLineView() {
//        binding.lineView.setDrawDotLine(true)
//
//        binding.lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY)
//
//        binding.lineView.setColorArray(intArrayOf(Color.GREEN))
//
//        viewModel.ratesLiveData.observe(viewLifecycleOwner) {
//            when(it){
//                is UiState.Success -> {
//                    binding.lineView.setBottomTextList(
//                        it.value.map {
//                            it.date
//                        } as ArrayList<String>?
//                    )
//                    binding.lineView.setFloatDataList(
//                        arrayListOf(
//                            it.value.map {
//                                it.coefficient.toFloat()
//                            } as ArrayList<Float>?
//                        )
//                    )
//                }
//                is UiState.Failure -> {}
//                is UiState.Loading -> {}
//            }
//
//        }
    }

    private fun initializeButton(){
        binding.relationFroTheThreeMonthsButton.setOnClickListener{ onFroTheThreeMonthsClick() }
        binding.relationFroTheThreeMonthsButton.text = "Three Months"

        binding.relationForTheOneYearButton.setOnClickListener{ onForTheOneYearClick() }
        binding.relationForTheOneYearButton.text = "One Years"

        binding.relationForTheThreeYearsButton.setOnClickListener{ onForTheThreeYearsClick() }
        binding.relationForTheThreeYearsButton.text = "Three Years"

        binding.relationForTheFiveYearsButton.setOnClickListener{ onForTheFiveYearsClick() }
        binding.relationForTheFiveYearsButton.text = "Five Years"
    }

    private fun onFroTheThreeMonthsClick() {
        binding.currentRelationsTextView.text = "${textRelation} fro the last three months"
        viewModel.loadForTheLastThreeMonths(
            arguments?.getString("from").toString(),
            arguments?.getString("to").toString()
        )
    }

    private fun onForTheOneYearClick() {
        binding.currentRelationsTextView.text = "${textRelation} for the last one year"
        viewModel.loadForTheLastOneYears(
            arguments?.getString("from").toString(),
            arguments?.getString("to").toString()
        )
    }

    private fun onForTheThreeYearsClick() {
        binding.currentRelationsTextView.text = "${textRelation} for the last three year"
        viewModel.loadForTheLastThreeYears(
            arguments?.getString("from").toString(),
            arguments?.getString("to").toString()
        )
    }

    private fun onForTheFiveYearsClick() {
        binding.currentRelationsTextView.text = "${textRelation} for the last five year"
        viewModel.loadForTheLastFiveYears(
            arguments?.getString("from").toString(),
            arguments?.getString("to").toString()
        )
    }

}