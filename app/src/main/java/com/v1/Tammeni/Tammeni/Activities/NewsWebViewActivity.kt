package com.v1.Tammeni.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.v1.Tammeni.R
import kotlinx.android.synthetic.main.activity_news_web_view.*


class NewsWebViewActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_web_view)

        when (Build.VERSION.SDK_INT) {
            in Int.MIN_VALUE..24 -> {
                webview()
            }
            else -> {
                chrome()
            }

        }

        closeWebview.setOnClickListener {

            finish()
        }


    }


    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    fun webview() {
        newsWebView.webViewClient = WebViewClient()
        newsWebView.apply {
            val web = intent.getStringExtra("url")
            val web2 = intent.getStringExtra("url2")
            if (web != null) {
                loadUrl(web)
            }
            if (web2 != null) {
                loadUrl(web2)
            }
            settings.javaScriptEnabled = true
            // settings.safeBrowsingEnabled = true
        }
    }

    fun chrome() {
        val web = intent.getStringExtra("url")
        val web2 = intent.getStringExtra("url2")
        val i = Intent(Intent.ACTION_VIEW)
        if (web != null) {
            i.data = Uri.parse(web)
        }
        if (web2 != null) {
            i.data = Uri.parse(web2)
        }
        startActivity(i)
        finish()
    }

    override fun onBackPressed() {
        val name = intent.getStringExtra("name")

        if (newsWebView.canGoBack()) {
            newsWebView.goBack()
        } else if (name == "main") {
            super.onBackPressed()

            finish()
        } else {
            super.onBackPressed()
            finish()
        }
    }
}