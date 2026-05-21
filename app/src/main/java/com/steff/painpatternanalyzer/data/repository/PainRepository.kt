package com.steff.painpatternanalyzer.data.repository

import com.steff.painpatternanalyzer.data.local.PainEntryDao
import com.steff.painpatternanalyzer.model.PainEntry
import kotlinx.coroutines.flow.Flow

class PainRepository(private val dao: PainEntryDao) {

    fun getAllEntries(): Flow<List<PainEntry>> {
        return dao.getAllEntries()
    }

    fun getEntriesByBodyArea(area: String): Flow<List<PainEntry>> {
        return dao.getEntriesByBodyArea(area)
    }

    suspend fun insertEntry(entry: PainEntry) {
        dao.insertEntry(entry)
    }

    suspend fun deleteEntry(entry: PainEntry) {
        dao.deleteEntry(entry)
    }
}