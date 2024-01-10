package com.currencywizard.presenter.converter

import android.content.Context
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.currencywizard.R
import com.currencywizard.data.modules.Currency
import com.currencywizard.data.modules.Rate
import com.currencywizard.data.states.DataState
import com.currencywizard.data.states.UiState
import com.currencywizard.databinding.FragmentConverterBinding
import com.currencywizard.di.appComponent
import com.currencywizard.di.viewModel.ViewModelFactory
import javax.inject.Inject

class ConverterFragment  : Fragment(R.layout.fragment_converter) {

    private val binding: FragmentConverterBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ConverterViewModel by viewModels { viewModelFactory }

    private var baseValue: Double = 0.0


    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currencies.observe(viewLifecycleOwner){
            when(it){
                is UiState.Success -> initializeSpinners(it.value)
                is UiState.Failure -> initializeSpinners(listOf())
                is UiState.Loading -> initializeSpinners(listOf())
            }
        }
        viewModel.rate.observe(viewLifecycleOwner){
            initializeTarget(it)
        }
        viewModel.baseValue.observe(viewLifecycleOwner){
            initializeBase(it)
        }
        initializeKeyboard()
        viewModel.loadCurrencies()

    }

    private fun initializeSpinners(currencies: List<Currency>){
        val adapter1 = ArrayAdapter(requireContext(), R.layout.spinner_item , currencies.map{it.symbol})
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.baseCurrencyDropdown.setAdapter(adapter1)
        val adapter2 = ArrayAdapter(requireContext(), R.layout.spinner_item , currencies.map{it.symbol})
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.targetCurrencyDropdown.setAdapter(adapter2)
    }

    private fun initializeKeyboard(){
        binding.btn0.setOnClickListener{ onDigitClick(0) }
        binding.btn1.setOnClickListener{ onDigitClick(1) }
        binding.btn2.setOnClickListener{ onDigitClick(2) }
        binding.btn3.setOnClickListener{ onDigitClick(3) }
        binding.btn4.setOnClickListener{ onDigitClick(4) }
        binding.btn5.setOnClickListener{ onDigitClick(5) }
        binding.btn6.setOnClickListener{ onDigitClick(6) }
        binding.btn7.setOnClickListener{ onDigitClick(7) }
        binding.btn8.setOnClickListener{ onDigitClick(8) }
        binding.btn9.setOnClickListener{ onDigitClick(9) }
        binding.btnClear.setOnClickListener{ onClearClick() }
        binding.btnGraph.setOnClickListener{ onGraphClick() }
        binding.btnHistory.setOnClickListener{ onHistoryClick() }
        binding.btnSwitch.setOnClickListener{onSwitchClick()}
        binding.btnEqual.setOnClickListener{ onEnterClick() }
    }


    private fun initializeTarget(state: UiState<Rate>){
        when(state){
            is UiState.Success -> binding.targetCurrencyTextView.text = (baseValue * state.value.coefficient).toString()
            is UiState.Failure -> binding.targetCurrencyTextView.text = "failed"
            is UiState.Loading -> binding.targetCurrencyTextView.text = "0"
        }
    }
    private fun initializeBase(value: Double){
        binding.baseCurrencyTextView.text = value.toInt().toString()
    }

    private fun onDigitClick(digit : Byte){
        viewModel.addDigitToBase(digit)
    }

    private fun onEnterClick(){
        viewModel.loadRate("EUR","USD")

    }
    private fun onClearClick(){
        viewModel.resetBase()
    }
    private fun onGraphClick(){

    }
    private fun onHistoryClick(){

    }
    private fun onSwitchClick(){

    }

}
