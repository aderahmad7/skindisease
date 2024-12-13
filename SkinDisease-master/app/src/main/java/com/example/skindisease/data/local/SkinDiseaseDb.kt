package com.example.skindisease.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.skindisease.domain.model.DetectionResult

@Database(entities = [DetectionResult::class], version = 1)
abstract class SkinDiseaseDb : RoomDatabase() {
    abstract fun detectionResultDao(): DetectionResultDao

    companion object {
        @Volatile
        private var instance: SkinDiseaseDb? = null

        fun getInstance(context: Context): SkinDiseaseDb {
            synchronized(this) {
                var instances = instance
                if (instances == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SkinDiseaseDb::class.java,
                        "cashflow.db"
                    )
                        .build()
                    instances = instance
                }
                return instances!!
            }
        }
    }
}