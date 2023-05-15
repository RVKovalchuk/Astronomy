package com.example.astronomy

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenStarted
import androidx.navigation.NavController
import com.example.astronomy.data.retrofit.Apod
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStream
import java.net.URL

fun Context.getColorList(@ColorRes id: Int): ColorStateList {
    return AppCompatResources.getColorStateList(this, id)
}

fun NavController.backOrNavigate(@IdRes id: Int) {
    if (this.currentDestination?.id == id) return
    if (!this.popBackStack(id, false)) {
        this.navigate(id)
    }
}

fun <T> LifecycleOwner.collectWhenStarted(flow: Flow<T>, collect: (T) -> Unit) {
    lifecycleScope.launch {
        lifecycle.whenStarted {
            flow.collect(collect)
        }
    }
}

fun <T> LifecycleOwner.collectWhenCreated(flow: Flow<T>, collect: (T) -> Unit) {
    lifecycleScope.launch {
        lifecycle.whenCreated {
            flow.collect(collect)
        }
    }
}

fun Context?.loadImage(image: Apod?) {
    val context = this
    if (image == null || context == null) return
    val resolver = context.contentResolver

    val share = Intent(Intent.ACTION_SEND)
    share.type = "image/jpeg"

    val values = ContentValues()
    values.put(MediaStore.Images.Media.TITLE, image.title)
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    val uri: Uri = resolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        values
    ) ?: return

    CoroutineScope(Dispatchers.IO).launch {
        val stream: OutputStream? = resolver.openOutputStream(uri)

        withContext(Dispatchers.IO) {
            stream?.write(URL(image.url).readBytes())
            stream?.close()
        }

        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
        }
    }
}

fun Context?.shareImage(image: Apod?) {
    val context = this
    if (image == null || context == null) return
    val resolver = context.contentResolver

    val share = Intent(Intent.ACTION_SEND)
    share.type = "image/jpeg"

    val values = ContentValues()
    values.put(MediaStore.Images.Media.TITLE, image.title)
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    val uri: Uri = resolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        values
    ) ?: return

    CoroutineScope(Dispatchers.IO).launch {
        val stream: OutputStream? = resolver.openOutputStream(uri)

        withContext(Dispatchers.IO) {
            stream?.write(URL(image.url).readBytes())
            stream?.close()
        }

        share.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(Intent.createChooser(share, "Share Image"))
    }
}