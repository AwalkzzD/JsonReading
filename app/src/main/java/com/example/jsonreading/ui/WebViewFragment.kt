package com.example.jsonreading.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import androidx.webkit.WebViewAssetLoader.ResourcesPathHandler
import com.example.jsonreading.databinding.FragmentWebViewBinding
import com.example.jsonreading.utils.LocalContentWebViewClient
import com.example.jsonreading.utils.WebAppInterface

private const val TAG = "WebViewFragment"

@SuppressLint("SetJavaScriptEnabled")
class WebViewFragment : Fragment() {

    private lateinit var webViewBinding: FragmentWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        webViewBinding = FragmentWebViewBinding.inflate(inflater)
        return webViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", AssetsPathHandler(requireActivity()))
            .addPathHandler("/res/", ResourcesPathHandler(requireActivity()))
            .build()

        webViewBinding.webView.apply {
            webViewClient = LocalContentWebViewClient(assetLoader)
            loadUrl("https://appassets.androidplatform.net/assets/index.html")
            // Enable Javascript
            settings.javaScriptEnabled = true
            settings.supportZoom()
            addJavascriptInterface(WebAppInterface(requireActivity()), "injectedObj")

            // TODO: call JS code/function from native Android code 
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            if (webViewBinding.webView.canGoBack()) {
                webViewBinding.webView.goBack()
            }
        }
    }


    /*inner class LocalContentWebViewClient(private val assetLoader: WebViewAssetLoader) :
        WebViewClientCompat() {

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            // start progress dialog
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun shouldInterceptRequest(
            view: WebView, request: WebResourceRequest
        ): WebResourceResponse? {
            return assetLoader.shouldInterceptRequest(request.url)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            // stop progress dialog
            super.onPageFinished(view, url)
        }
    }*/
}