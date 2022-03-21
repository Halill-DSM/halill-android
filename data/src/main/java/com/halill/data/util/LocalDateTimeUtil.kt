package com.halill.data.util

import java.time.LocalDateTime

fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this)