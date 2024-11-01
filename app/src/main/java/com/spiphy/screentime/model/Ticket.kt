package com.spiphy.screentime.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.spiphy.screentime.R
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

import java.util.UUID

val exclamations = listOf(
    R.string.exclamation_01,
    R.string.exclamation_02,
    R.string.exclamation_03,
    R.string.exclamation_04,
    R.string.exclamation_05,
    R.string.exclamation_06,
    R.string.exclamation_07,
    R.string.exclamation_08,
    R.string.exclamation_09,
    R.string.exclamation_10,
    R.string.exclamation_11,
    R.string.exclamation_12,
    R.string.exclamation_13,
    R.string.exclamation_14,
    R.string.exclamation_15,
    R.string.exclamation_16,
    R.string.exclamation_17,
    R.string.exclamation_18,
    R.string.exclamation_19,
    R.string.exclamation_20
)

enum class TicketType(val id: Int, @DrawableRes val imageRes: Int, @StringRes val textRes: Int) {
    EFFORT(1, R.drawable.effort, R.string.award_effort),
    BONUS(2, R.drawable.bonus, R.string.award_bonus),
    DAILY(3, R.drawable.daily, R.string.award_daily),
    MOVIE_NIGHT(4, R.drawable.cinema, R.string.award_movie_night),
    WEEKEND(5, R.drawable.fireworks, R.string.award_weekend),
    STAR(6, R.drawable.star, R.string.award_star)
}

val TicketRedemptionMap = mapOf<String, TicketRedemption>(
    "TV Show" to TicketRedemption.TV,
    "Movie" to TicketRedemption.MOVIE,
    "Video Games" to TicketRedemption.GAMING,
    "Phone" to TicketRedemption.PHONE
)

enum class TicketRedemption(
    val id: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val textRes: Int
) {
    TV(1, R.drawable.television, R.string.redeem_tv_show),
    GAMING(2, R.drawable.game_console, R.string.redeem_video_games),
    MOVIE(3, R.drawable.movie, R.string.redeem_movie),
    PHONE(4, R.drawable.smartphone, R.string.redeem_phone),
    NONE(5, 0, 0)
}

@Serializable
data class Ticket(
    val id: String = UUID.randomUUID().toString(),
    val exclamationId: Int,
    val note: String,
    val earnedDate: String,
    val type: TicketType,
    val usedDate: String,
    var used: Boolean,
    val redemption: TicketRedemption,
    val time: Int
)


val testTickets = listOf(
    Ticket(
        UUID.randomUUID().toString(),
        0,
        "Note 1",
        Clock.System.now().toString(),
        TicketType.DAILY,
        Clock.System.now().toString(),
        false,
        TicketRedemption.TV,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        1,
        "Note 2",
        Clock.System.now().toString(),
        TicketType.EFFORT,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        2,
        "Note 3",
        Clock.System.now().toString(),
        TicketType.BONUS,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        3,
        "Note 4",
        Clock.System.now().toString(),
        TicketType.EFFORT,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        4,
        "Note 5",
        Clock.System.now().toString(),
        TicketType.EFFORT,
        Clock.System.now().toString(),
        true,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        5,
        "Note 6",
        Clock.System.now().toString(),
        TicketType.EFFORT,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        6,
        "Note 7",
        Clock.System.now().toString(),
        TicketType.EFFORT,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        7,
        "Note 8",
        Clock.System.now().toString(),
        TicketType.EFFORT,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        8,
        "Note 9",
        Clock.System.now().toString(),
        TicketType.EFFORT,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        9,
        "Note 10",
        Clock.System.now().toString(),
        TicketType.EFFORT,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        10,
        "Note 11",
        Clock.System.now().toString(),
        TicketType.EFFORT,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        11,
        "Note 12",
        Clock.System.now().toString(),
        TicketType.BONUS,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        12,
        "Note 13",
        Clock.System.now().toString(),
        TicketType.BONUS,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        13,
        "Note 14",
        Clock.System.now().toString(),
        TicketType.BONUS,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        14,
        "Note 15",
        Clock.System.now().toString(),
        TicketType.BONUS,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        15,
        "Note 16",
        Clock.System.now().toString(),
        TicketType.BONUS,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        16,
        "Note 17",
        Clock.System.now().toString(),
        TicketType.BONUS,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        17,
        "Note 18",
        Clock.System.now().toString(),
        TicketType.BONUS,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        18,
        "Note 19",
        Clock.System.now().toString(),
        TicketType.BONUS,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    ),
    Ticket(
        UUID.randomUUID().toString(),
        20,
        "Note 20",
        Clock.System.now().toString(),
        TicketType.BONUS,
        Clock.System.now().toString(),
        false,
        TicketRedemption.PHONE,
        20
    )
)