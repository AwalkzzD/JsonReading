package com.example.jsonreading.utils.common

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files

object FileUtils {
    // method to get local file path from content uri
    fun fileFromContentUri(context: Context, contentUri: Uri): File {

        // get file details to create a temp file
        val fileExtension = getFileExtension(context, contentUri)
        val fileName = "temporary_file" + if (fileExtension != null) ".$fileExtension" else ""

        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()

        try {
            val oStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(contentUri)

            // copy contents of file to temp file using ip stream and op stream
            inputStream?.copyTo(oStream)

            // close io operations
            oStream.flush()
            oStream.close()
            inputStream?.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return tempFile
    }

    // supporting method to get file details to create temp file
    private fun getFileExtension(context: Context, uri: Uri): String? {
        val fileType: String? = context.contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
    }

    // delete file if exists
    fun deleteFile(file: File): Boolean {
        return Files.deleteIfExists(file.toPath())
    }

    fun createExternalFile(context: Context, internalFile: File): File {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "my_pdf.pdf"
        )
        file.createNewFile()
        val oStream = FileOutputStream(file)
        val inputStream = internalFile.inputStream()

        // copy contents of file to temp file using ip stream and op stream
        inputStream.copyTo(oStream)

        // close io operations
        oStream.flush()
        oStream.close()
        inputStream.close()

        return file
    }
}