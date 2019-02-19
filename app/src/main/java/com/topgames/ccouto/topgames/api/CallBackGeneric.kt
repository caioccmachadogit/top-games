package com.topgames.ccouto.topgames.api

/**
 * Created by ccouto on 19/02/2019.
 */
interface CallBackGeneric<T> {

    fun onSuccess(response: T)

    fun onError(response: T?)
}