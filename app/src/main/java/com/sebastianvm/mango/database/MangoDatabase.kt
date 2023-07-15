package com.sebastianvm.mango.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sebastianvm.mango.database.dao.IncomeSourceDao
import com.sebastianvm.mango.database.models.IncomeSourceEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Database(entities = [IncomeSourceEntity::class], version = 1, exportSchema = false)
abstract class MangoDatabase : RoomDatabase() {
    abstract fun incomeSourceDao(): IncomeSourceDao
}

@Module
@InstallIn(SingletonComponent::class)
object MangoDatabaseModule {

    @Provides
    fun provideMangoDatabase(@ApplicationContext appContext: Context): MangoDatabase {
        return Room.databaseBuilder(
            appContext,
            MangoDatabase::class.java,
            "MangoDatabase"
        ).build()
    }

    @Provides
    fun provideIncomeSourceDao(mangoDatabase: MangoDatabase): IncomeSourceDao {
        return mangoDatabase.incomeSourceDao()
    }
}
