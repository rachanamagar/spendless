package com.myapp.spendless.data.LocalData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SpendLessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM user")
    fun getAllUser(): Flow<List<UserEntity>>

    @Query("SELECT * from user WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity
}