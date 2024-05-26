package com.haxon.githubreposapp.presentation.repo_info

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String) {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        factory = {
            WebView(it).apply {
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        })
}