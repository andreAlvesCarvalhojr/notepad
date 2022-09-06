package com.example.ceep3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ceep3.database.dao.NotaDao
import com.example.ceep3.model.Nota

@Database(entities = [Nota::class], version = 1, exportSchema = false)
abstract class NotaDatabase : RoomDatabase() {

    abstract fun getRoomDatabase(): NotaDao

    companion object {
        fun getInstance(context: Context): NotaDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NotaDatabase::class.java,
                "nota.db"
            )
                .build()
        }
    }
}