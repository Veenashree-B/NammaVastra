package com.example.namma_vastraself_employment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.namma_vastraself_employment.data.local.dao.TrendDao
import com.example.namma_vastraself_employment.data.local.entity.TrendEntity

@Database(entities = [TrendEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trendDao(): TrendDao
}
