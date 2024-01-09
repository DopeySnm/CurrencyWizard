package com.currencywizard.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.currencywizard.data.db.models.RateEntity

@Dao
interface RateDao {

    @Upsert
    suspend fun saveRate(rate: RateEntity)

    @Upsert
    suspend fun saveAll(rates: List<RateEntity>)

    @Query("SELECT * FROM rates WHERE source = 'TRANSFER'")
    fun getTransferHistory() : List<RateEntity>

    @Query("SELECT * FROM rates WHERE source = 'HISTORY' " +
            "AND base = :base " +
            "AND target = :target ")
    fun getRelationHistory(base:String, target: String) : List<RateEntity>
}