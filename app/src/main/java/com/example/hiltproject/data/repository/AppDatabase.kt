package com.example.hiltproject.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hiltproject.data.api.UserDao
import com.example.hiltproject.data.model.UserCacheEntity

@Database(entities = [UserCacheEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
