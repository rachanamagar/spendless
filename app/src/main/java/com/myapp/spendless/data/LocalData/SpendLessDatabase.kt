package com.myapp.spendless.data.LocalData

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class SpendLessDatabase: RoomDatabase() {
    companion object{
        const val TABLE = "spendLess_db"
    }
    abstract fun getDao(): SpendLessDao
}