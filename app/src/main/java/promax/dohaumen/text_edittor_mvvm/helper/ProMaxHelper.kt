package promax.dohaumen.text_edittor_mvvm.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import promax.dohaumen.text_edittor_mvvm.MyApplication
import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.hmp.dev.heler.StringHelper
import java.io.BufferedReader
import java.io.File
import java.util.*


fun demSoTu(string: String): Int {
    if (string == "") return 0
    return string.split(" ").size
}
fun getTuVietTat(s: String): String {
    val arr = s.trim().split(" ")
    var result = ""
    for (i in 0..arr.size-1) {
        result += arr[i].substring(0, 1)
    }
    return result
}

fun getString(resources: Int): String = MyApplication.context.getString(resources)

fun readTextFromUri(uri: Uri): String {
    val inputStream = MyApplication.context.contentResolver.openInputStream(uri)
    val reader = BufferedReader(inputStream?.reader())
    val content = StringBuilder()
    try {
        var line = reader.readLine()
        while (line != null) {
            content.append(line).append("\n")
            line = reader.readLine()
        }
    } finally {
        reader.close()
    }
    return content.toString()
}

fun getFileNameFromUri(uri: Uri): String {
    return File(uri.path).name
}

fun getRealPathFromURI(contentUri: Uri?): String? {
    var path: String? = null
    val proj = arrayOf(MediaStore.MediaColumns.DATA)
    val cursor: Cursor =
        MyApplication.context.getContentResolver().query(contentUri!!, proj, null, null, null)!!
    if (cursor.moveToFirst()) {
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        path = cursor.getString(column_index)
    }
    cursor.close()
    return path
}

fun writeFileText(uri: Uri, content: String) {
    val file = File(getRealPathFromURI(uri))
    file.writeBytes(content.toByteArray())
}

fun isStoragePermissionGranted(activity: Activity): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
                return true
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 9)
            return false
        }
    } else {
        return true
    }
}


suspend fun searchFileText(list: List<FileText>, keySearch: String): List<FileText> {
    val result = ArrayList<FileText>()

    for (i in 0..list.size-1) {
        val fileName = list[i].name.toLowerCase(Locale.ROOT)

        if (fileName == keySearch || getTuVietTat(fileName).contains(keySearch) || fileName.contains(
                keySearch
            )
            || StringHelper.loaiBoDauTiengViet(fileName).contains(keySearch)
        ) {
            result.add(list[i])
        }
    }
    return result
}

suspend fun testCroutine(): String {
    for (i in 0..10_000_000_000) {

    }
    return "Comple"
}