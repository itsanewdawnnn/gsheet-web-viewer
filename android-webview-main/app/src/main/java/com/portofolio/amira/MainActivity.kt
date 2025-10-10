package com.portofolio.amira

import android.graphics.Color
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var insetsController: WindowInsetsControllerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set layout activity_main.xml
        setContentView(R.layout.activity_main)

        // Fullscreen tanpa padding default sistem (konten bisa extend ke status bar)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        // Inisialisasi controller untuk status bar icon dan bar warna
        insetsController = WindowInsetsControllerCompat(window, window.decorView)
        // Default status bar icon gelap (untuk background cerah)
        insetsController.isAppearanceLightStatusBars = true

        // Ambil WebView dari layout
        webView = findViewById(R.id.webview)

        // Tambah padding top agar konten WebView tidak ketutup status bar
        ViewCompat.setOnApplyWindowInsetsListener(webView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = systemBars.top)
            insets
        }

        // Konfigurasi WebView
        webView.settings.apply {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            builtInZoomControls = true
            displayZoomControls = false
        }

        // WebViewClient untuk intercept event halaman selesai dimuat
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                // Jalankan JavaScript untuk mendapatkan background color halaman
                view?.evaluateJavascript(
                    """
                    (function() {
                        var bg = window.getComputedStyle(document.body, null).backgroundColor;
                        return bg;
                    })();
                    """
                ) { result ->
                    val color = parseCssColor(result)
                    val isDark = (color != null) && isColorDark(color)

                    // Jika background gelap, set icon status bar jadi putih (false)
                    // Jika background terang, set icon jadi gelap (true)
                    insetsController.isAppearanceLightStatusBars = !isDark
                }
            }
        }

        // Muat URL utama
        webView.loadUrl("https://itsanewdawnnn.github.io/portofolio/")

        // Tangani tombol back agar bisa navigasi WebView
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        })
    }

    /**
     * Parse warna CSS (rgb / rgba) ke Android Color Int.
     * Contoh input: "rgba(255, 255, 255, 1)" atau "rgb(255, 255, 255)"
     */
    private fun parseCssColor(cssColor: String?): Int? {
        if (cssColor == null) return null
        try {
            val cleaned = cssColor.trim().removePrefix("\"").removeSuffix("\"")
            val regex = Regex("rgba?\\((\\d+),\\s*(\\d+),\\s*(\\d+)(,\\s*(\\d+(\\.\\d+)?))?\\)")
            val match = regex.find(cleaned) ?: return null

            val r = match.groupValues[1].toInt()
            val g = match.groupValues[2].toInt()
            val b = match.groupValues[3].toInt()
            val a = if (match.groupValues.size >= 6 && match.groupValues[5].isNotEmpty()) {
                (match.groupValues[5].toFloat() * 255).toInt()
            } else 255
            return Color.argb(a, r, g, b)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Cek apakah warna tergolong gelap.
     * Return true jika gelap, false jika terang.
     */
    private fun isColorDark(color: Int): Boolean {
        val darkness =
            1 - (0.299 * Color.red(color) +
                    0.587 * Color.green(color) +
                    0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }
}
