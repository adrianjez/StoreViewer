package com.hqapps.storeviewer.ui.storelist

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hqapps.storeviewer.R
import com.hqapps.storeviewer.databinding.FragmentStoreListBinding
import com.hqapps.storeviewer.extensions.hide
import com.hqapps.storeviewer.extensions.show
import com.hqapps.storeviewer.ui.storelist.adapter.StoreAdapter
import com.hqapps.storeviewer.ui.storelist.adapter.StoreLoadStateAdapter
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreListFragment : Fragment(R.layout.fragment_store_list) {

    private val viewModel by viewModels<StoreListViewModel>()
    private lateinit var binding: FragmentStoreListBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStoreListBinding.bind(view)
        initLocation()
    }

    private fun initLocation() {
        binding.contentLocationError.btnRetry.setOnClickListener {
            initLocation()
        }
        Dexter.withContext(requireActivity())
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            binding.contentLocationError.root.hide()
                            registerForLocation()
                        } else {
                            binding.contentLocationError.root.show()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener { binding.contentLocationError.emptyTv.text = it.name }
            .check()
    }

    @SuppressLint("MissingPermission")
    private fun registerForLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                location?.let { initView(it) }
            }

    }
    private fun initView(location: Location) {
        val adapter = StoreAdapter()

        viewModel.reloadStores(location)
        viewModel.stores.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        binding.apply {
            recycler.apply {
                setHasFixedSize(true)
                itemAnimator = null
                this.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = StoreLoadStateAdapter { adapter.retry() },
                    footer = StoreLoadStateAdapter { adapter.retry() }
                )
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
            }

            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progress.isVisible = loadState.source.refresh is LoadState.Loading
                recycler.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                error.isVisible = loadState.source.refresh is LoadState.Error

                // no results found
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recycler.isVisible = false
                    emptyTv.isVisible = true
                } else {
                    emptyTv.isVisible = false
                }
            }
        }
    }
}