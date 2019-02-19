package com.topgames.ccouto.topgames.utils

import com.topgames.ccouto.topgames.domain.Top

/**
 * Created by ccouto on 19/02/2019.
 */
interface PaginationAdapterCallback {
    fun retryPageLoad()

    fun onClickItem(top: Top)
}