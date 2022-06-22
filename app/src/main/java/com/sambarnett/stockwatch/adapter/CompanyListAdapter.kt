package com.sambarnett.stockwatch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
            FragmentCompanyListBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, postion: Int) {
        val current = getItem(postion)
//        holder.itemView.setOnClickListener {
//            onCompanyClicked(current)
//        }
        holder.bind(current)
    }


    //Creating a holder for each data object. First name, sur name, age and weight.
    class CompanyViewHolder(private var binding: FragmentCompanyListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //binding Person data to PersonListPerson UI values
        fun bind(company: CompanyListing) {
            binding.apply {
                company.name
                company.exchange
                company.symbol
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


