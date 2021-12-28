package com.hqapps.storeviewer.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hqapps.storeviewer.api.StoreAPI
import com.hqapps.storeviewer.data.model.Store
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class StorePagingSource(
    private val storeAPI: StoreAPI,
    private val longitude: Float,
    private val latitude: Float,
    private val radius: Int,
): PagingSource<Int, Store>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Store> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = storeAPI.getStores(longitude, latitude, radius, position, params.loadSize)
            val stores = response.store
            LoadResult.Page(
                data = stores.orEmpty(),
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (stores.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Store>): Int? {
        return state.anchorPosition
    }
}