package com.pwr.knif.omatko

import android.arch.persistence.room.*

@Dao
interface ResultDao {

    @Query("SELECT * FROM results")
    fun getAllResults(): List<Result>

    @Query("SELECT * FROM results WHERE resultId = :currentResultsId")
    fun getResultById(currentResultsId: String): Result

    @Query("SELECT * FROM results WHERE lastModification < :modificationTime")
    fun getOutdatedEvents(modificationTime: Long): List<Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResults(results: List<Result?>)

    @Delete()
    fun deleteResults(results: List<Result?>)

    @Update()
    fun updateResult(result: Result?)
}