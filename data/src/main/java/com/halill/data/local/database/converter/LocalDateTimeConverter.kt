package com.halill.data.local.database.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime?): String? =
        localDateTime?.toString()

    @TypeConverter
    fun stringToLocalDateTime(string: String?): LocalDateTime? =
        LocalDateTime.parse(string)

    @TypeConverter
    fun localDateToString(localDate: LocalDate?): String? =
        localDate?.toString()

    @TypeConverter
    fun stringToLocalDateT(string: String?): LocalDate? =
        LocalDate.parse(string)
}