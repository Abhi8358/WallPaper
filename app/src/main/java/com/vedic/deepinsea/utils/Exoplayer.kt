package com.vedic.deepinsea.utils

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Exoplayer @Inject constructor(@ApplicationContext applicationContext: Context) {
    val  player = ExoPlayer.Builder(applicationContext).build()

//    val player: ExoPlayer by lazy {
//        ExoPlayer.Builder(applicationContext).build()
//    }
}