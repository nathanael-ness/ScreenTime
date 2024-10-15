package com.spiphy.screentime.ui.screens

import com.spiphy.screentime.model.Star
import com.spiphy.screentime.model.StarGroup
import com.spiphy.screentime.model.Ticket
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class Utilities {
    companion object {
        fun ticketToTimeString(ticket: Ticket): String {
            val timestamp = if (ticket.used) ticket.usedDate else ticket.earnedDate
            val time =
                Instant.parse(timestamp).toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
            val displayTime = "${time.dayOfWeek} ${time.month} ${time.dayOfMonth}, ${time.year}"
            return displayTime
        }

        fun starToTimeString(star: Star): String {
            val time =
                Instant.parse(star.date).toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
            val displayTime = "${time.dayOfWeek} ${time.month} ${time.dayOfMonth}, ${time.year}"
            return displayTime
        }

        fun starGroupToTimeString(starGroup: StarGroup): String {
            val time = Instant.parse(starGroup.date)
                .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
            val displayTime = "${time.dayOfWeek} ${time.month} ${time.dayOfMonth}, ${time.year}"
            return displayTime
        }
    }
}