package com.vedic.deepinsea.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.content.PackageManagerCompat.LOG_TAG
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateAppDirectory @Inject constructor(val context: Context) {
    init {
        createDirectory()
        getAppSpecificAlbumStorageDir(context = context, "DeepinSea")
    }

    private fun createDirectory() {
        val dir = File("${Environment.getExternalStorageDirectory()}/DeepInSea")
        try {
            if (dir.exists()) {
                System.out.println("Directory created");
            } else {
                System.out.println("Directory is not created 0")
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    fun getAppSpecificAlbumStorageDir(context: Context, albumName: String): File? {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        val file = File(context.getExternalFilesDir(
            Environment.MEDIA_MOUNTED), albumName)

        val file2 = File("${Environment.getExternalStorageDirectory()} + ${File.separator} + $albumName");
        file.mkdirs()
        if (!file.mkdirs()) {
            Log.e("Abhishek", "Directory not created")
        }
        if (!file2.mkdirs()) {
            Log.e("Abhishek", "Directory not created 2")
        }
        return file
    }
}