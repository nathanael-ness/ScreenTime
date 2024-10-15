package com.spiphy.screentime.model

import kotlinx.serialization.Serializable

@Serializable
data class Star(
    val id: String,
    val date: String,
    val note: String
)
