package com.topgames.ccouto.topgames.domain

import java.io.Serializable

/**
 * Created by ccouto on 19/02/2019.
 */
data class Game(val _id: Long?, val name:String, val box: Box):Serializable {
}