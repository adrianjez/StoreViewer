package com.hqapps.storeviewer.ui.storelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hqapps.storeviewer.data.model.Store
import com.hqapps.storeviewer.databinding.FragmentStoreListItemBinding

class StoreAdapter: PagingDataAdapter<Store, StoreAdapter.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Store>() {
            override fun areItemsTheSame(oldItem: Store, newItem: Store) = oldItem.posId == newItem.posId
            override fun areContentsTheSame(oldItem: Store, newItem: Store) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentStoreListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { store ->
            with(holder) {
                itemView.tag = store
                if (store != null) {
                    bind(store)
                }
            }
        }
    }

    class ViewHolder(private val binding: FragmentStoreListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(store: Store) {

            binding.apply {

                Glide.with(itemView)
                    .load(store.posTypeLogo)
                    .centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .into(storeImage)

                storeName.text = store.name
                storeAddress.text = store.address
                storePostal.text = store.postalCode
                storeCity.text = store.city
            }

        }
    }
}