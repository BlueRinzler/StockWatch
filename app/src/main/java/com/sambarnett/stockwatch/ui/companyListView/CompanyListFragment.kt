package com.sambarnett.stockwatch.ui.companyListView


import android.os.Bundle
import android.view.*

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import androidx.recyclerview.widget.LinearLayoutManager
import com.sambarnett.stockwatch.R
import com.sambarnett.stockwatch.adapter.CompanyListAdapter
import com.sambarnett.stockwatch.databinding.FragmentCompanyListBinding
import com.sambarnett.stockwatch.domain.model.CompanyListing

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.FieldPosition


/**
 * This is the first fragment displaying company details for all companies in the database.
 */

@AndroidEntryPoint
class CompanyListFragment : Fragment() {


    private var _binding: FragmentCompanyListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CompanyListingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompanyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpSearchBar()
    }


    private fun initView() {
        //Initial view to set up the adapter with the list of companies from the viewModel
        val adapter = CompanyListAdapter {
            val action =
                CompanyListFragmentDirections.actionCompanyListFragmentToStockDetailsFragment(it.symbol)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { companies ->
                    companies.let {
                        adapter.submitList(it.companies)
                    }
                }
            }
        }
    }

    private fun setUpSearchBar() {
        binding.stockSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.onEvent(
                        CompanyListingEvent.OnSearchQueryChange(query = newText)
                    )
                }
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean = false
        })
    }

    override fun onDestroyView() {
        super.onDestroy()
        _binding = null
    }

}










