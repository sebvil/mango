package com.sebastianvm.mango.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sebastianvm.mango.database.converters.DeductionConverters
import com.sebastianvm.mango.database.converters.IncomeInfoConverters
import com.sebastianvm.mango.database.converters.TaxTypeConverters
import com.sebastianvm.mango.database.dao.IncomeSourceDao
import com.sebastianvm.mango.database.dao.TaxDao
import com.sebastianvm.mango.database.models.IncomeSourceEntity
import com.sebastianvm.mango.database.models.IncomeStreamEntity
import com.sebastianvm.mango.database.models.TaxEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Database(
    entities = [IncomeSourceEntity::class, IncomeStreamEntity::class, TaxEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(IncomeInfoConverters::class, TaxTypeConverters::class, DeductionConverters::class)
abstract class MangoDatabase : RoomDatabase() {
    abstract fun incomeSourceDao(): IncomeSourceDao
    abstract fun taxDao(): TaxDao
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

    @Provides
    fun provideTaxDao(mangoDatabase: MangoDatabase): TaxDao {
        return mangoDatabase.taxDao()
    }
}
