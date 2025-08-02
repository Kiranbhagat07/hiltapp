package com.example.hiltproject.data.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hiltproject.data.model.UserCacheEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserCacheEntity)

    @Query("SELECT * FROM user_cache WHERE isSynced = 0")
    suspend fun getUnsyncedUsers(): List<UserCacheEntity>

    @Query("UPDATE user_cache SET isSynced = 1 WHERE id = :id")
    suspend fun markUserAsSynced(id: Int)


    @Query("SELECT * FROM user_cache LIMIT 1")
    suspend fun getUser(): UserCacheEntity?

    @Query("DELETE FROM user_cache")
    suspend fun deleteAll()
}
