package com.dogeby.reliccalculator.core.data.repository

import com.dogeby.reliccalculator.core.data.model.toCharacterReportEntity
import com.dogeby.reliccalculator.core.database.dao.CharacterReportDao
import com.dogeby.reliccalculator.core.database.model.report.CharacterReportEntity
import com.dogeby.reliccalculator.core.database.model.report.toCharacterReport
import com.dogeby.reliccalculator.core.model.report.CharacterReport
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class CharacterReportRepositoryImpl @Inject constructor(
    private val characterReportDao: CharacterReportDao,
) : CharacterReportRepository {

    override fun getAllCharacterReports(): Flow<List<CharacterReport>> {
        return characterReportDao.getCharacterReports().map {
            it.map(CharacterReportEntity::toCharacterReport)
        }
    }

    override fun getCharacterReportsByCharacterIds(ids: Set<String>): Flow<List<CharacterReport>> {
        return characterReportDao.getCharacterReportsByCharacterIds(ids).map {
            it.map(CharacterReportEntity::toCharacterReport)
        }
    }

    override fun getLatestCharacterReports(): Flow<List<CharacterReport>> {
        return characterReportDao.getLatestCharacterReports().map {
            it.map(CharacterReportEntity::toCharacterReport)
        }
    }

    override suspend fun insertCharacterReports(
        reports: List<CharacterReport>,
    ): Result<List<Long>> = runCatching {
        characterReportDao.insertOrIgnoreCharacterReports(
            reports.map(CharacterReport::toCharacterReportEntity),
        )
    }

    override suspend fun updateCharacterReports(reports: List<CharacterReport>): Result<Int> =
        runCatching {
            characterReportDao.updateCharacterReports(
                reports.map(CharacterReport::toCharacterReportEntity),
            )
        }

    override suspend fun upsertCharacterReports(
        reports: List<CharacterReport>,
    ): Result<List<Long>> = runCatching {
        characterReportDao.upsertCharacterReports(
            reports.map(CharacterReport::toCharacterReportEntity),
        )
    }

    override suspend fun deleteCharacterReports(reports: List<CharacterReport>): Result<Int> =
        runCatching {
            characterReportDao.deleteCharacterReports(
                reports.map(CharacterReport::toCharacterReportEntity),
            )
        }
}
