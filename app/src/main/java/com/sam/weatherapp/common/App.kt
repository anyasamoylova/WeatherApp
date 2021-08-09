package com.sam.weatherapp.common

import com.sam.weatherapp.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication(){
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
    }

}