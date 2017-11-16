package com.topgames.ccouto.topgames.utils

import com.topgames.ccouto.topgames.domain.Top

/**
 * Created by ccouto on 15/11/2017.
 */
interface PaginationAdapterCallback {
    fun retryPageLoad()

    fun onClickItem(top: Top)
}