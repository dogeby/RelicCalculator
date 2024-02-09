package com.dogeby.core.data.fake

import com.dogeby.core.database.dao.PresetDao
import com.dogeby.core.database.model.preset.PresetEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePresetDao : PresetDao {

    private val tables = mutableMapOf<String, PresetEntity>()

    override suspend fun insertOrIgnorePresets(presets: List<PresetEntity>): List<Long> {
        return presets.map {
            val value = tables.putIfAbsent(it.characterId, it)
            if (value == null) 1 else -1
        }
    }

    override suspend fun updatePresets(presets: List<PresetEntity>): Int {
        return presets
            .mapNotNull {
                tables.replace(it.characterId, it)
            }
            .count()
    }

    override suspend fun upsertPresets(presets: List<PresetEntity>): List<Long> {
        return presets.map {
            if (tables.put(it.characterId, it) == null) 1 else -1
        }
    }

    override suspend fun deletePresets(presets: List<PresetEntity>): Int {
        return presets.mapNotNull {
            tables.remove(it.characterId)
        }.size
    }

    override fun getPresets(): Flow<List<PresetEntity>> {
        return flow {
            emit(tables.values.toList())
        }
    }

    override fun getPresets(ids: Set<String>): Flow<List<PresetEntity>> {
        return flow {
            emit(tables.values.filter { it.characterId in ids }.toList())
        }
    }
}
