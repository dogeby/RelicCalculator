package com.dogeby.core.data.exception

import java.util.Date

class NoUpdateNeededException(date: Date) : Exception(date.toString())
