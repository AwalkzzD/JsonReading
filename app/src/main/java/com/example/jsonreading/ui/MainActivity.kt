package com.example.jsonreading.ui

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.jsonreading.data.Response
import com.example.jsonreading.databinding.ActivityMainBinding
import com.example.jsonreading.utils.CommonUtils
import com.example.jsonreading.utils.FileUtils
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileReader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var jsonFile: File
    private lateinit var mCoroutineScope: CoroutineScope
    private lateinit var progressDialog: AlertDialog

    private val jsonLiveData: MutableLiveData<Response> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = CommonUtils.getProgressDialog(this)

        val getFileContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

                if (uri != null) {
                    jsonFile = FileUtils.fileFromContentUri(this, uri)

                    progressDialog.show()

                    // separate thread for reading file and parsing json string to object
                    CoroutineScope(Dispatchers.IO).launch {
                        jsonLiveData.postValue(
                            Gson().fromJson(
                                FileReader(jsonFile), Response::class.java
                            )
                        )
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
            jsonLiveData.value = null

            // manual garbage collection
            Runtime.getRuntime().gc()
        }

        // json parsing object live data observer
        jsonLiveData.observe(this) {
            if (jsonLiveData.value != null) {
                progressDialog.dismiss()
                Toast.makeText(this, "${it.instituteName} completed parsing", Toast.LENGTH_SHORT)
                    .show()
            }

            // delete file
            if (::jsonFile.isInitialized) {
                FileUtils.deleteFile(jsonFile)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mCoroutineScope.isInitialized && mCoroutineScope.isActive) {
            mCoroutineScope.cancel()
        }
    }

}