package com.mobilProgramlama.odev.di

import android.content.Context
import android.content.SharedPreferences
import com.mobilProgramlama.odev.common.Constants
import com.mobilProgramlama.odev.util.MyPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREF_FILE, Context.MODE_PRIVATE)
    }


    @Singleton
    @Provides
    fun provideMySharedPreferenceManager(sharedPreferences: SharedPreferences) =
        MyPreferences(sharedPreferences)

}