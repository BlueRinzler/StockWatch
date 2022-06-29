package com.sambarnett.stockwatch.ui.companyListView

import android.nfc.Tag
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import androidx.recyclerview.widget.LinearLayoutManager
import com.sambarnett.stockwatch.adapter.CompanyListAdapter
import com.sambarnett.stockwatch.databinding.FragmentCompanyListBinding
import dagger.hilt.android.AndroidEntryPoint
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
        val state = viewModel.uiState

        val adapter = CompanyListAdapter()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                state.collectLatest {
                    adapter.submitList(it.companies)
                }

            }
        }

    }

}

//    private fun initView() {
//        val state = viewModel.uiState
//        // Start a coroutine in the lifecycle scope
//
//    }





