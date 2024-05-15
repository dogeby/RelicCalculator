package com.dogeby.reliccalculator.core.domain.model

import kotlinx.datetime.Instant

data class CharacterSimpleReport(
    val id: String,
    val name: String,
    val icon: String,
    val updatedDate: Instant,
    val totalScore: Float,
    val cavernRelics: List<SimpleRelicRatingReport>,
    val planarOrnaments: List<SimpleRelicRatingReport>,
)

data class SimpleRelicRatingReport(
    val id: String,
    val icon: String,
    val score: Float,
)
