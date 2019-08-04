package com.mind.coolest.url_checker.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mind.coolest.url_checker.databinding.ItemUrlBinding
import com.mind.coolest.url_checker.ui.main.model.URLItemModel

class UrlAdapter : RecyclerView.Adapter<UrlAdapter.URLItemViewHolder>(), Filterable {

    private var urlsData: MutableList<URLItemModel> = ArrayList()
    private var urlSearchList: MutableList<URLItemModel> = ArrayList()

    fun updateData(data: List<URLItemModel>?) {
        urlsData.clear()
        urlSearchList.clear()
        if (!data.isNullOrEmpty()) {
            this.urlsData.addAll(data)
            this.urlSearchList.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun updateUrlsSearchList(data: List<URLItemModel>?) {
        urlSearchList.clear()
        if (!data.isNullOrEmpty()) {
            this.urlSearchList.addAll(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): URLItemViewHolder {
        return URLItemViewHolder(
            ItemUrlBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return urlSearchList.size
    }

    override fun onBindViewHolder(holder: URLItemViewHolder, position: Int) {
        holder.setupViewModel(urlSearchList[position])
    }

    class URLItemViewHolder(private val binding: ItemUrlBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setupViewModel(itemModel: URLItemModel) {
            binding.viewModel = itemModel
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    updateUrlsSearchList(urlsData)
                } else {
                    val filteredList = ArrayList<URLItemModel>()
                    for (row in urlsData) {

                        if (row.urlName.get()!!.toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row)
                        }
                    }
                    updateUrlsSearchList(filteredList)
                }

                val filterResults = FilterResults()
                filterResults.values = urlSearchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                urlSearchList = filterResults.values as ArrayList<URLItemModel>
                notifyDataSetChanged()
            }
        }
    }

}
