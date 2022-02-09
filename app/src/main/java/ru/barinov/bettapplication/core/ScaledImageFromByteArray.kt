package ru.barinov.bettapplication.core

import android.graphics.*
import kotlin.coroutines.*

 fun Bitmap.scaledImageFromBitmap(image: Bitmap, baseImageScale: Int, defaultScale: Float ): Bitmap {
        val pixels = (baseImageScale * defaultScale + 0.5f).toInt()
        return Bitmap.createScaledBitmap(image, pixels, pixels, true)
    }
