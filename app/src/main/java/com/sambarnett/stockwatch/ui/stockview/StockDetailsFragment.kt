package com.sambarnett.stockwatch.ui.stockview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.sambarnett.stockwatch.databinding.FragmentStockDetailsBinding
import com.sambarnett.stockwatch.domain.model.CompanyDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StockDetailsFragment : Fragment() {

    private val navigationArgs: StockDetailsFragmentArgs by navArgs()

    private var _binding: FragmentStockDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StockDetailsViewModel by viewModels()
    lateinit var company: CompanyDetails

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStockDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.symbol


        //needs work, wont inflate
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state?.let {
                        binding.root.isVisible = !it.isLoading
                        bind(company)
                    }
                }
            }
        }
    }


    private fun bind(company: CompanyDetails) {
        binding.apply {
            binding.companyName.text = company.name
            binding.companySymbol.text = company.symbol
            binding.companyCountry.text = company.country
            binding.companySector.text = company.sector
            binding.companyDescription.text = company.description
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}