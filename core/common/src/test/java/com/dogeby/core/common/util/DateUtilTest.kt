package com.dogeby.core.common.util

import com.dogeby.core.common.util.DateUtil.toDate
import com.dogeby.core.common.util.DateUtil.toString
import java.util.Date
import java.util.Locale
import org.junit.Assert
import org.junit.Test

class DateUtilTest {

    private val dateFormat = "yy-MM-dd HH:mm:ss"
    private val locale = Locale.getDefault()

    @Test
    fun test_convertStringAndDate_success() {
        val initialDate = Date(0)
        val dateToString = initialDate.toString(dateFormat, locale).getOrThrow()
        val stringToDate = dateToString.toDate(dateFormat, locale).getOrThrow()

        Assert.assertEquals(initialDate, stringToDate)
    }
}
