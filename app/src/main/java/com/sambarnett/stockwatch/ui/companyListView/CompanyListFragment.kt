package com.sambarnett.stockwatch.ui.companyListView


import android.os.Bundle
import android.view.*

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import androidx.recyclerview.widget.LinearLayoutManager
import com.sambarnett.stockwatch.R
import com.sambarnett.stockwatch.adapter.CompanyListAdapter
import com.sambarnett.stockwatch.databinding.FragmentCompanyListBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * This is the first fragment displaying company details for all companies in the database.
 */


@AndroidEntryPoint
class CompanyListFragment : Fragment() {

    private var _binding: FragmentCompanyListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CompanyListingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        binding.actionBar.inflateMenu(R.menu.menu_stock)

    }


    private fun initView() {
        //Initial view to set up the adapter with the list of companies from the viewModel
        val adapter = CompanyListAdapter()
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    adapter.submitList(it.companies)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_stock, menu)

        val stockItem = menu.findItem(R.id.search_bar)
        val searchView = stockItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val adapter = CompanyListAdapter()
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.scrollToPosition(0)
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getCompanyListingsSearch(query).collectLatest {
                            adapter.submitList(it)
                        }
                    }
                    searchView.clearFocus()
                }
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }


        })


    }
}





