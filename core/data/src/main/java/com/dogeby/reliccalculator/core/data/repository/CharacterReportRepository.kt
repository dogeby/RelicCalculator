package com.dogeby.reliccalculator.core.data.repository

import com.dogeby.reliccalculator.core.model.report.CharacterReport
import kotlinx.coroutines.flow.Flow

interface CharacterReportRepository {

    fun getAllCharacterReports(): Flow<List<CharacterReport>>

    fun getCharacterReportsByCharacterIds(ids: Set<String>): Flow<List<CharacterReport>>

    fun getLatestCharacterReports(): Flow<List<CharacterReport>>

    suspend fun insertCharacterReports(reports: List<CharacterReport>): Result<List<Long>>

    suspend fun updateCharacterReports(reports: List<CharacterReport>): Result<Int>

    suspend fun upsertCharacterReports(reports: List<CharacterReport>): Result<List<Long>>

    suspend fun deleteCharacterReports(reports: List<CharacterReport>): Result<Int>
}
