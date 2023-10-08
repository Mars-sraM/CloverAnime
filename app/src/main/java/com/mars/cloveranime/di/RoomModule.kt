package com.mars.cloveranime.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mars.cloveranime.data.AnimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val ANIME_DATABASE_NAME = "anime_database_v2"

    @Singleton
    @Provides
    fun providesRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AnimeDatabase::class.java, ANIME_DATABASE_NAME).build()


    @Singleton
    @Provides
    fun providesDao(db: AnimeDatabase) = db.getAnimeDao()

    @Singleton
    @Provides
    fun providesPendingDao(db: AnimeDatabase) = db.getAnimePendingDao()

    @Singleton
    @Provides
    fun providesFinishedDao(db: AnimeDatabase) = db.getAnimeFinishedDao()
}