package com.spiphy.screentime.model

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Star(
    val id: String,
    val date: String,
    val note: String
)

val testStars = listOf(
    Star(
        id = UUID.randomUUID().toString(),
        date = Clock.System.now().toString(),
        note = "Test"
    ),
    Star(
        id = UUID.randomUUID().toString(),
        date = Clock.System.now().toString(),
        note = "Test"
    ),
    Star(
        id = UUID.randomUUID().toString(),
        date = Clock.System.now().toString(),
        note = "Test"
    ),
    Star(
        id = UUID.randomUUID().toString(),
        date = Clock.System.now().toString(),
        note = "Test"
    )
)