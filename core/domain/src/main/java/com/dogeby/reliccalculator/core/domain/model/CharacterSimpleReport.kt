package com.dogeby.reliccalculator.core.domain.model

import com.dogeby.reliccalculator.core.model.report.RelicReport
import kotlinx.datetime.Instant

data class CharacterSimpleReport(
    val id: Int,
    val characterId: String,
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

fun RelicReport.toSimpleRelicRatingReport(icon: String) = SimpleRelicRatingReport(
    id = id,
    icon = icon,
    score = score,
)
