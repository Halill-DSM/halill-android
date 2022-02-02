package com.halill.halill.util

import java.time.LocalDateTime

fun LocalDateTime.changeYear(year: Int): LocalDateTime =
    LocalDateTime.of(year, this.month, this.dayOfMonth, this.hour, this.minute)

fun LocalDateTime.changeMonth(month: Int): LocalDateTime =
    LocalDateTime.of(this.year, month, this.dayOfMonth, this.hour, this.minute)

fun LocalDateTime.changeDay(day: Int): LocalDateTime =
    LocalDateTime.of(this.year, this.monthValue, day, this.hour, this.minute)

fun LocalDateTime.changeHour(hour: Int): LocalDateTime =
    LocalDateTime.of(this.year, this.monthValue, this.dayOfMonth, hour, this.minute)

fun LocalDateTime.changeMinute(minute: Int): LocalDateTime =
    LocalDateTime.of(this.year, this.monthValue, this.dayOfMonth, this.hour, minute)