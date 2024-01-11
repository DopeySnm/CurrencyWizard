package com.currencywizard.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.currencywizard.data.db.models.RateEntity

@Dao
interface RateDao {

    @Upsert
    suspend fun saveRate(rate: RateEntity)

    @Upsert
    suspend fun saveAll(rates: List<RateEntity>)

    @Query("SELECT * FROM rates WHERE source = 'TRANSFER'")
    suspend fun getTransferHistory() : List<RateEntity>

    @Query("SELECT * FROM rates WHERE source = 'HISTORY' " +
            "AND base = :base " +
            "AND target = :target")
    suspend fun getRelationHistory(base:String, target: String) : List<RateEntity>

    @Query("DELETE FROM rates WHERE source = 'HISTORY' AND base = :base AND target = :target ")
    suspend fun deleteCache(base:String, target: String)

    @Transaction
    suspend fun refreshCache(base:String, target: String, newData: List<RateEntity>){
        deleteCache(base, target)
        saveAll(newData)
    }
}