package com.sambarnett.stockwatch.companyListView

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sambarnett.stockwatch.databinding.FragmentCompanyListBinding

import com.sambarnett.stockwatch.adapter.CompanyListAdapter
import com.sambarnett.stockwatch.di.viewModels
import kotlinx.coroutines.launch
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
        val adapter = CompanyListAdapter {

        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


    }


}