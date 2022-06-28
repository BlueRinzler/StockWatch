package com.sambarnett.stockwatch.ui.companyListView

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sambarnett.stockwatch.adapter.CompanyListAdapter
import com.sambarnett.stockwatch.databinding.FragmentCompanyListBinding
import com.sambarnett.stockwatch.util.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * This is the first fragment displaying company details for all companies in the database.
 */
class CompanyListFragment : Fragment() {


    private var _binding: FragmentCompanyListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CompanyListingsViewModel by viewModel()

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


    }

}


//    private fun initView() {
//        val state = viewModel.uiState
//        // Start a coroutine in the lifecycle scope
//
//    }





