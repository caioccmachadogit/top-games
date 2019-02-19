package com.topgames.ccouto.topgames.domain

import java.io.Serializable

/**
 * Created by ccouto on 19/02/2019.
 */
data class Top(val game: Game?, val viewers:String, val channels:String) : Serializable {
}