package com.mobilProgramlama.odev.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

object Utils {

    fun getTimestampByDateString(s: String): Long {
        return try {
            SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT).parse(s)?.time ?: Date().time
        } catch (p: ParseException) {
            Date().time
        }
    }

    fun getDateStringByTimestamp(timestamp: Long): String {
        return try {
            SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT).format(Date(timestamp))
        } catch (p: ParseException) {
            ""
        }
    }
}