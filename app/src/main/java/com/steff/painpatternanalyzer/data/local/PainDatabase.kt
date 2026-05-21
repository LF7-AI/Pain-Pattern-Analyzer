package com.steff.painpatternanalyzer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.steff.painpatternanalyzer.model.PainEntry

@Database(entities = [PainEntry::class], version = 1, exportSchema = false)
abstract class PainDatabase : RoomDatabase() {

    abstract fun painEntryDao(): PainEntryDao

    companion object {
        @Volatile
        private var INSTANCE: PainDatabase? = null

        fun getInstance(context: Context): PainDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    PainDatabase::class.java,
                    "pain_pattern_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}