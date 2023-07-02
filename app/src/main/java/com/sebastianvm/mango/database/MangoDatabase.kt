package com.sebastianvm.mango.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
<<<<<<< HEAD
import com.sebastianvm.mango.database.dao.JobDao
import com.sebastianvm.mango.database.models.JobEntity
import com.sebastianvm.mango.database.dao.UserDao
import com.sebastianvm.mango.database.models.UserEnt
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Database(entities = [JobEntity::class], version = 1, exportSchema = false)
abstract class MangoDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
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
    fun provideJobDao(mangoDatabase: MangoDatabase): JobDao {
        return mangoDatabase.jobDao()
    }
}
