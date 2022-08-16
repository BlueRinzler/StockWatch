package com.sambarnett.stockwatch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sambarnett.stockwatch.databinding.CompanyListCompanyBinding
import com.sambarnett.stockwatch.databinding.FragmentCompanyListBinding
import com.sambarnett.stockwatch.domain.model.CompanyListing


/**
 * Adapter for the [RecyclerView] in person_list_fragment. Displays Person data object.
 */

//(private val onCompanyClicked: (CompanyListing) -> Unit)
class CompanyListAdapter :
    ListAdapter<CompanyListing, CompanyListAdapter.CompanyViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CompanyViewHolder {
        return CompanyViewHolder(
            CompanyListCompanyBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, postion: Int) {
        val current = getItem(postion)
//        holder.itemView.setOnClickListener {
//            onCompanyClicked(current)
//        }
        if (current != null) {
            holder.bind(current)
        }
    }

    //Creating a holder for each data object.
    class CompanyViewHolder(private var binding: CompanyListCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //binding Person data to PersonListPerson UI values
        fun bind(company: CompanyListing) {
            binding.apply {
                companyListName.text = company.name
                companyExchangeName.text = company.exchange
                companySymbolName.text = company.symbol
            }
        }
    }

    //creating DiffCallback object to be used in PersonListAdapter.
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CompanyListing>() {
            override fun areContentsTheSame(
                oldItem: CompanyListing,
                newItem: CompanyListing
            ): Boolean {
                return oldItem.name == newItem.name
            }
            override fun areItemsTheSame(
                oldItem: CompanyListing,
                newItem: CompanyListing
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}


