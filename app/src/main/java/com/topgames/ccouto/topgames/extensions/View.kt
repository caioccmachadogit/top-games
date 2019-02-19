package com.topgames.ccouto.topgames.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by ccouto on 19/02/2019.
 */

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}