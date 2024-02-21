package com.dogeby.reliccalculator.core.domain.model

import kotlinx.datetime.Instant

data class DefaultPresetUpdateResult(
    val updateDate: Instant,
    val updatedPresetCount: Int,
)
