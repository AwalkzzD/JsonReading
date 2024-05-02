package com.example.jsonreading.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import androidx.webkit.WebViewAssetLoader.ResourcesPathHandler
import androidx.webkit.WebViewClientCompat
import com.example.jsonreading.databinding.FragmentWebViewBinding
import com.example.jsonreading.utils.common.CommonUtils
import com.example.jsonreading.utils.WebAppInterface

private const val TAG = "WebViewFragment"

@SuppressLint("SetJavaScriptEnabled")
class WebViewFragment : Fragment() {

    private lateinit var webViewBinding: FragmentWebViewBinding
    private lateinit var progressDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        webViewBinding = FragmentWebViewBinding.inflate(inflater)
        progressDialog = CommonUtils.getProgressDialog(requireActivity())
        return webViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", AssetsPathHandler(requireActivity()))
            .addPathHandler("/res/", ResourcesPathHandler(requireActivity())).build()

        webViewBinding.webView.apply {

            webViewClient = object : WebViewClientCompat() {

                override fun shouldOverrideUrlLoading(
                    view: WebView, request: WebResourceRequest
                ): Boolean {
                    progressDialog.show()
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun shouldInterceptRequest(
                    view: WebView, request: WebResourceRequest
                ): WebResourceResponse? {
                    return assetLoader.shouldInterceptRequest(request.url)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressDialog.dismiss()
                    super.onPageFinished(view, url)
                }
            }

            loadUrl("https://appassets.androidplatform.net/assets/index.html")
            // Enable Javascript
            settings.javaScriptEnabled = true
            settings.supportZoom()

            // calling native code from web view
            addJavascriptInterface(WebAppInterface(requireActivity()), "injectedObj")

            // evaluating JS code of web view
            evaluateJavascript("getToastMessage();") { returnValue ->
                Log.d(TAG, "Toast message from web view: $returnValue")
            }

            /*evaluateJavascript("(function() { return document.getElementById('toastMessage').value; })();") { returnValue ->
                Toast.makeText(requireContext(), returnValue, Toast.LENGTH_SHORT).show()
            }*/

            // intercepting console messages
            webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                    if (consoleMessage != null) {
                        Log.d(
                            TAG,
                            "onConsoleMessage: ${consoleMessage.lineNumber()}: ${consoleMessage.message()}"
                        )
                    }
                    return true
                }
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback {
            if (webViewBinding.webView.canGoBack()) {
                webViewBinding.webView.goBack()
            } else {
                findNavController().navigateUp()
            }
        }
    }
}