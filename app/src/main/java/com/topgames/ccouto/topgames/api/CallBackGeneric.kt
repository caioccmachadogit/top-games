package com.topgames.ccouto.topgames.api

/**
 * Created by ccouto on 14/11/2017.
 */
interface CallBackGeneric<T> {

    fun onSuccess(response: T)

    fun onError(response: T?)
}