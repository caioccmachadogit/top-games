package com.topgames.ccouto.topgames.domain

import java.io.Serializable

/**
 * Created by ccouto on 15/11/2017.
 */
data class Top(val game: Game?, val viewers:String, val channels:String) : Serializable {
}