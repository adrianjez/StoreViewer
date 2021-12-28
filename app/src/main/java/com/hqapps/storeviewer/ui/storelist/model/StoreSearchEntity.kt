package com.hqapps.storeviewer.ui.storelist.model

internal data class StoreSearchEntity(
    val latitude: Float = 50f,
    val longitude: Float = 20f,
    val radius: Int = 1000
)