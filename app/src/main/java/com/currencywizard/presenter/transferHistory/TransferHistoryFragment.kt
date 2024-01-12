package com.currencywizard.presenter.transferHistory

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.currencywizard.R
import com.currencywizard.data.states.UiState
import com.currencywizard.databinding.FragmentTransferHistoryBinding
import com.currencywizard.di.appComponent
import com.currencywizard.di.viewModel.ViewModelFactory
import javax.inject.Inject

class TransferHistoryFragment : Fragment(R.layout.fragment_transfer_history) {

    private val binding: FragmentTransferHistoryBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: TransferHistoryViewModel by viewModels { viewModelFactory }
    private val adapter = RateAdapter()

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.rates.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> adapter.submitList(listOf())
                is UiState.Failure -> adapter.submitList(listOf())
                is UiState.Success -> adapter.submitList(it.value)
            }
        }
        initializeRecycler()
        viewModel.loadRates()
    }

    private fun initializeRecycler() = with(binding.rateList) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@TransferHistoryFragment.adapter
        addItemDecoration(
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL).apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.space_divider)
                    ?.let { this@apply.setDrawable(it) }
            }
        )
    }
}