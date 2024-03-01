package com.dogeby.reliccalculator.core.database.di

import android.content.Context
import androidx.room.Room
import com.dogeby.reliccalculator.core.database.RelicCalculatorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesRelicCalculatorDatabase(
        @ApplicationContext context: Context,
    ): RelicCalculatorDatabase = Room.databaseBuilder(
        context,
        RelicCalculatorDatabase::class.java,
        "relic-calculator-database",
    ).build()
}
