package com.vedic.deepinsea.hilt

import android.app.Application
import com.vedic.deepinsea.utils.CreateAppDirectory
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class DeepInSeaApplication : Application() {

    private lateinit var createAppDirectory: CreateAppDirectory

    override fun onCreate() {
        super.onCreate()
        createAppDirectory = CreateAppDirectory(applicationContext)

       /* MobileAds.initialize(
            this
        ) { }*/
    }
}