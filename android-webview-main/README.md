This Android application functions as a dedicated fullscreen WebView container that loads and displays external web content while dynamically adjusting the system status bar icon color for optimal visibility based on the page’s background color.

Architectural Overview: Initialization & UI Setup
Upon MainActivity creation, the app inflates the layout and configures the UI to operate in immersive, edge-to-edge mode using WindowCompat.setDecorFitsSystemWindows. Safe-area padding is applied to the WebView via ViewCompat.setOnApplyWindowInsetsListener to ensure content does not overlap system UI elements. The WindowInsetsControllerCompat is used to programmatically adjust the status bar icon style between light and dark modes depending on background luminance.

WebView Configuration & Feature Enablement
The WebView is configured to enable JavaScript execution (setJavaScriptEnabled(true)), support responsive scaling through the viewport meta tag (setLoadWithOverviewMode(true) and setUseWideViewPort(true)), and allow gesture-based zoom while disabling default on-screen zoom controls for a cleaner appearance. This setup ensures the application can handle modern, interactive, and mobile-friendly web applications.

Dynamic Status Bar Adaptation
A custom WebViewClient intercepts the onPageFinished event to run a JavaScript snippet that retrieves the CSS background-color property of the document body. The retrieved value is parsed using Color.parseColor(), and luminance is calculated using the formula 0.299*R + 0.587*G + 0.114*B. Based on this luminance value, the application decides whether to render the status bar icons in light or dark mode, ensuring optimal contrast and legibility.

Navigation Handling & User Experience
The onBackPressed method is overridden to check whether the WebView can navigate backward within its history stack before closing the activity. This provides a seamless in-app browsing experience without prematurely exiting the application. Error handling is implemented to prevent crashes due to malformed or unsupported color values retrieved from the webpage.

Design Considerations & Performance
This design prioritizes a minimalist UI that focuses on the web content, removes unnecessary browser chrome, and adapts dynamically to the loaded site’s style. The combination of immersive layout, adaptive status bar, and responsive WebView configuration results in a polished, app-like experience suitable for both static sites and modern web applications.
