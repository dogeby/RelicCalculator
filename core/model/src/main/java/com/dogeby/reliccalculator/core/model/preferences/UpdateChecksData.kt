package com.dogeby.reliccalculator.core.model.preferences

import kotlinx.datetime.Instant

data class UpdateChecksData(
    val defaultPresetLastCheckDate: Instant,
    val defaultPresetCheckIntervalSecond: Int,
)
