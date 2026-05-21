package com.steff.painpatternanalyzer.data.local

import androidx.room.*
import com.steff.painpatternanalyzer.model.PainEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface PainEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: PainEntry)

    @Delete
    suspend fun deleteEntry(entry: PainEntry)

    @Query("SELECT * FROM pain_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<PainEntry>>

    @Query("SELECT * FROM pain_entries WHERE bodyArea = :area ORDER BY timestamp DESC")
    fun getEntriesByBodyArea(area: String): Flow<List<PainEntry>>
}

