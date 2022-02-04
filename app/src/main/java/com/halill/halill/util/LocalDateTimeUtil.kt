package com.halill.halill.util

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

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

fun LocalDateTime.toShowDeadlineText(): String =
    "${year}년 ${monthValue}월 ${dayOfMonth}일 ${hour}시 ${minute}분 까지"

fun LocalDateTime.toRemainTimeText(): String {
    val timeDifferent = DifferentTime(this)

    val minuteDifferent = timeDifferent.minute
    var text = if (minuteDifferent <= 0) "지난시간: " else "남은시간: "
    text += timeDifferent.toRemainShowText()
    return text
}

fun DifferentTime.toRemainShowText(): String = when {
    year > 0 -> "${year}년 "
    month > 0 -> "${month}개월 "
    day > 0 -> "${day}일 "
    hour > 0 -> "${hour}시간 "
    minute > 0 -> "${minute}분"

    year < 0 -> "${year*-1}년 "
    month < 0 -> "${month*-1}개월 "
    day < 0 -> "${day*-1}일 "
    hour < 0 -> "${hour*-1}시간 "
    minute < 0 -> "${minute*-1}분"

    else -> "0분"
}


class DifferentTime(compareTime: LocalDateTime) {
    private val currentTime = LocalDateTime.now()
    val year = ChronoUnit.YEARS.between(currentTime, compareTime)
    val month = ChronoUnit.MONTHS.between(currentTime, compareTime)
    val day = ChronoUnit.DAYS.between(currentTime, compareTime)
    val hour = ChronoUnit.HOURS.between(currentTime, compareTime)
    val minute = ChronoUnit.MINUTES.between(currentTime, compareTime)
}