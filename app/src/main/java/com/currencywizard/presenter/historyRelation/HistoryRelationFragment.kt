package com.currencywizard.presenter.historyRelation

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.currencywizard.R
import com.currencywizard.data.states.UiState
import com.currencywizard.databinding.FragmentHistoryRelationBinding
import com.currencywizard.di.appComponent
import com.currencywizard.di.viewModel.ViewModelFactory
import im.dacer.androidcharts.LineView
import java.util.ArrayList
import javax.inject.Inject


class HistoryRelationFragment : Fragment(R.layout.fragment_history_relation) {

    private val binding: FragmentHistoryRelationBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: HistoryRelationViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lineView.setDrawDotLine(true)

        binding.lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY)

        binding.lineView.setColorArray(intArrayOf(Color.GREEN))

        viewModel.ratesLiveData.observe(viewLifecycleOwner) {
            when(it){
                is UiState.Success -> {
                    binding.lineView.setBottomTextList(
                        it.value.map {
                            it.date
                        } as ArrayList<String>?
                    )
                    binding.lineView.setFloatDataList(
                        arrayListOf(
                            it.value.map {
                                it.coefficient.toFloat()
                            } as ArrayList<Float>?
                        )
                    )
                }
                is UiState.Failure -> {}
                is UiState.Loading -> {}
            }

        }

        viewModel.loadHistoryOveFiveYears(
            arguments?.getString("from").toString(),
            arguments?.getString("to").toString()
        )


    }

}