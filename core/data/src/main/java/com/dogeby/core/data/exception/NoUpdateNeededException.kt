package com.dogeby.core.data.exception

import kotlinx.datetime.Instant

class NoUpdateNeededException(instant: Instant) : Exception(instant.toString())
