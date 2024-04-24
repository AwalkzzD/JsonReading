package com.example.jsonreading

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.jsonreading.data.Response
import com.example.jsonreading.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var jsonFile: File
    private val jsonLiveData: MutableLiveData<Response> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getFileContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                Log.d(TAG, "onCreate: ${uri.toString()}")

                if (uri != null) {

                    jsonFile = fileFromContentUri(this, uri)

                    // separate thread for reading file and parsing json string to object
                    GlobalScope.launch {
                        val reader = FileReader(jsonFile)
                        val resultObj: Response = Gson().fromJson(
                            reader, Response::class.java
                        )
                        jsonLiveData.postValue(resultObj)
                        reader.close()
                    }
                }

            }



        binding.chooseFile.setOnClickListener {
            Toast.makeText(this, "Choose your file", Toast.LENGTH_SHORT).show()

            // choose file dialog to create local copy of json data
            getFileContent.launch("application/json")
        }

        jsonLiveData.observe(this) {
            Toast.makeText(this, "Completed Parsing", Toast.LENGTH_SHORT).show()

            Log.d(TAG, "Showing result: ${it.expireDatetime}")
        }

    }

    // method to get local file path from content uri
    private fun fileFromContentUri(context: Context, contentUri: Uri): File {

        val fileExtension = getFileExtension(context, contentUri)
        val fileName = "temporary_file" + if (fileExtension != null) ".$fileExtension" else ""

        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()

        try {
            val oStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(contentUri)

            inputStream?.let {
                copy(inputStream, oStream)
            }

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

    // method for copying input stream data to output stream using bytearray
    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }
}