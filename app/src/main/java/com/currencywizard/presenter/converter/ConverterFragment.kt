package com.currencywizard.presenter.converter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.currencywizard.R
import com.currencywizard.databinding.FragmentConverterBinding

class ConverterFragment : Fragment(R.layout.fragment_converter) {
    private val binding: FragmentConverterBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}