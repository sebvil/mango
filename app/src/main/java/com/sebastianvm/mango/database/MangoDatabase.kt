package com.sebastianvm.mango.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sebastianvm.mango.database.dao.JobDao
import com.sebastianvm.mango.database.models.JobEnt
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Database(entities = [JobEnt::class], version = 1)
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

}