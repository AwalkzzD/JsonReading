package com.example.jsonreading.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.jsonreading.data.Response
import com.example.jsonreading.databinding.ActivityMainBinding
import com.example.jsonreading.utils.FileUtils
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileReader
import java.nio.file.Files

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
                    jsonFile = FileUtils.fileFromContentUri(this, uri)

                    // separate thread for reading file and parsing json string to object
                    GlobalScope.launch {
                        val reader = FileReader(jsonFile)

                        jsonLiveData.postValue(
                            Gson().fromJson(
                                reader, Response::class.java
                            )
                        )
                        reader.close()
                    }
                }

            }

        // file choose button listener
        binding.chooseFile.setOnClickListener {
            Toast.makeText(this, "Choose your file", Toast.LENGTH_SHORT).show()

            // choose file dialog to create local copy of json data
            getFileContent.launch("application/json")
        }

        // remove obj button listener
        binding.removeObj.setOnClickListener {
            // make json obj value to null to check memory usage
            if (jsonLiveData.value != null) {
                jsonLiveData.postValue(null)
            }
        }

        // json parsing object live data observer
        jsonLiveData.observe(this) {
            if (jsonLiveData.value != null) {
                Toast.makeText(this, "Completed Parsing", Toast.LENGTH_SHORT).show()

                Log.d(TAG, "Showing result: ${it.expireDatetime}")
            }

            if (Files.deleteIfExists(jsonFile.toPath())) {
                Log.d(TAG, "file cleanup: file deleted")
            }
        }

    }
}