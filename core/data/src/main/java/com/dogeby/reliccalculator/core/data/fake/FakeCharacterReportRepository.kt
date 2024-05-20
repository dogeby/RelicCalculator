package com.dogeby.reliccalculator.core.data.fake

import com.dogeby.reliccalculator.core.data.repository.CharacterReportRepository
import com.dogeby.reliccalculator.core.model.report.CharacterReport
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakeCharacterReportRepository : CharacterReportRepository {

    private val characterReportsFlow: MutableSharedFlow<List<CharacterReport>> =
        MutableSharedFlow<List<CharacterReport>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        ).apply {
            tryEmit(emptyList())
        }

    override fun getAllCharacterReports(): Flow<List<CharacterReport>> {
        return characterReportsFlow
    }

    override fun getCharacterReportsByCharacterIds(ids: Set<String>): Flow<List<CharacterReport>> {
        return characterReportsFlow.map { reports ->
            reports.filter { it.character.id in ids }
        }
    }

    override fun getLatestCharacterReports(): Flow<List<CharacterReport>> {
        return characterReportsFlow.map { reports ->
            reports
                .groupBy { it.character.id }
                .mapValues { entry ->
                    entry.value.maxByOrNull { it.generationTime }
                }
                .values
                .filterNotNull()
        }
    }

    override suspend fun insertCharacterReports(
        reports: List<CharacterReport>,
    ): Result<List<Long>> {
        return Result.success(emptyList())
    }

    override suspend fun updateCharacterReports(reports: List<CharacterReport>): Result<Int> {
        return Result.success(0)
    }

    override suspend fun upsertCharacterReports(
        reports: List<CharacterReport>,
    ): Result<List<Long>> {
        return Result.success(emptyList())
    }

    override suspend fun deleteCharacterReports(reports: List<CharacterReport>): Result<Int> {
        return Result.success(0)
    }

    fun sendCharacterReports(characterReports: List<CharacterReport>) {
        characterReportsFlow.tryEmit(characterReports)
    }
}
