package com.dogeby.core.common.util

import com.dogeby.core.common.util.DateUtil.toDate
import com.dogeby.core.common.util.DateUtil.toString
import java.util.Date
import java.util.Locale
import org.junit.Assert
import org.junit.Test

class DateUtilTest {

    private val dateFormat = "yy-MM-dd HH:mm:ss"
    private val locale = Locale.KOREA

    @Test
    fun test_dateToString_success() {
        val result = Date(0).toString(dateFormat, locale).getOrThrow()

        Assert.assertEquals("70-01-01 09:00:00", result)
    }

    @Test
    fun test_stringToDate_success() {
        val result = "70-01-01 09:00:00".toDate(dateFormat, locale).getOrThrow()

        Assert.assertEquals(Date(0), result)
    }
}
