package com.dogeby.core.common.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {

    fun Date.toString(
        dateFormat: String,
        locale: Locale,
    ): Result<String> = runCatching {
        SimpleDateFormat(dateFormat, locale).format(this) ?: throw NullPointerException()
    }

    fun String.toDate(
        dateFormat: String,
        locale: Locale,
    ): Result<Date> = runCatching {
        SimpleDateFormat(dateFormat, locale).parse(this) ?: throw NullPointerException()
    }
}
