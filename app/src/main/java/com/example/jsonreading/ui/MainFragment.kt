package com.example.jsonreading.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.jsonreading.R
import com.example.jsonreading.data.studycloud.Response
import com.example.jsonreading.databinding.FragmentMainBinding
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

private const val TAG = "MainFragment"

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var jsonFile: File
    private lateinit var pdfFile: File
    private lateinit var mCoroutineScope: CoroutineScope
    private lateinit var progressDialog: AlertDialog

    private val jsonLiveData: MutableLiveData<Response?> = MutableLiveData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // create progress dialog
        progressDialog = CommonUtils.getProgressDialog(requireActivity())

        // get JSON file
        val getFileContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

                if (uri != null) {
                    jsonFile = FileUtils.fileFromContentUri(requireActivity(), uri)

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

        // get PDF file
        val getPdfFile =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

                if (uri != null) {
                    pdfFile = FileUtils.fileFromContentUri(requireActivity(), uri)
                    Log.d(TAG, "PDF File: ${pdfFile.path}")

                    val externalFile = FileUtils.createExternalFile(requireActivity(), pdfFile)
                    Log.d(TAG, "External File: ${externalFile.path}")

                    val intent = Intent(Intent.ACTION_VIEW).setDataAndType(
                        FileProvider.getUriForFile(
                            requireActivity(),
                            requireActivity().applicationContext.packageName + ".provider",
                            externalFile
                        ), "application/pdf"
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

                    try {
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }

        // file choose button listener
        binding.chooseFile.setOnClickListener {
            Toast.makeText(requireActivity(), "Choose your file", Toast.LENGTH_SHORT).show()

            // choose file dialog to create local copy of json data
            getFileContent.launch("application/json")
        }

        // web view fragment redirect button listener
        binding.webViewRedirect.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_webViewFragment)
        }

        // pdf conversion button listener
        binding.pdfConversion.setOnClickListener {
            // choose file dialog to convert pdf to byte array and store to external storage
            getPdfFile.launch("application/pdf")
        }

        // database explore button listener
        binding.dbTest.setOnClickListener {
            // redirect to Database Fragment to explore different databases
            findNavController().navigate(R.id.action_mainFragment_to_databaseFragment)
        }

        // json parsing object live data observer
        jsonLiveData.observe(viewLifecycleOwner) { response ->

            if (jsonLiveData.value != null) {
                progressDialog.dismiss()
                Toast.makeText(
                    requireActivity(),
                    "${response?.instituteName} completed parsing",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // delete file
            if (::jsonFile.isInitialized) {
                FileUtils.deleteFile(jsonFile)
            }

            // manual garbage collection
            Runtime.getRuntime().gc()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mCoroutineScope.isInitialized && mCoroutineScope.isActive) {
            mCoroutineScope.cancel()
        }
    }

}