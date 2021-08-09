package com.sam.weatherapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sam.weatherapp.room.AppDatabase
import com.sam.weatherapp.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun bindContext(application: Application): Context

    @ContributesAndroidInjector(modules = [FragmentModule::class])
     abstract fun contributeMainActivity(): MainActivity

     companion object {

         @Singleton
         @Provides
         fun provideAppDatabase(context: Context): AppDatabase {
             return Room.databaseBuilder(
                 context.applicationContext,
                 AppDatabase::class.java,
                 "app_database")
                 .build()
         }
     }



}