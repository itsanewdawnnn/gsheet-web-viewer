This repository contains two complementary components:
1. **Front-End Web Application** — a static HTML/JS/CSS interface that dynamically renders spreadsheet data from Google Sheets.  
2. **Android Application** — a fullscreen WebView container designed to host and display the front-end application seamlessly within a native mobile environment.

Both components are designed to work together, providing a responsive, adaptive, and cloud-connected user experience across web and mobile platforms.

---

## 🧩 Front-End Web Application (HTML / JS / CSS)

### Overview
This front-end application is a statically served HTML document that leverages JavaScript and a CSV parsing library to consume data from an external cloud-based spreadsheet and render it into structured tabular formats within the DOM.

### Architectural Overview
- On page load, an initialization function automatically triggers data retrieval and rendering.
- Includes a **Refresh button** to manually reload data and a **Redirect button** to open the live Google Sheets document in a new browser tab.

### External Data Source Integration
- Connects directly to a publicly accessible **Google Sheets CSV export endpoint**.
- Ensures real-time data retrieval without any server-side components.

### Data Acquisition Layer
- Utilizes the `fetch()` API to request CSV data.
- Handles errors gracefully by hiding loaders, showing alerts, and logging technical details when network issues occur.

### Client-Side CSV Parsing
- Uses **PapaParse**, a lightweight client-side CSV parsing library, to transform CSV text into structured arrays for rendering.

### DOM Rendering Logic
- Dynamically generates content using vanilla JavaScript.
- Clears previously rendered data before displaying new content for consistency.
- No external frameworks required.

### Dynamic UI Composition
- Renders two main tables: **portfolio details** and **summary information**, maintaining clarity and organization.

### Responsive Styling & UX Enhancements
- Styled via external CSS with a clean, responsive layout.
- Full-screen loader animation provides visual feedback.
- Accessibility supported through ARIA attributes and intuitive button placement.

### Error Handling & Fallbacks
- Displays user alerts and hides loaders when data access fails.
- Logs errors for developer debugging.

### Caching Considerations
- Prevents outdated asset loading by appending version query parameters (e.g., `script.js?v=2`).

---

## 📱 Android Application (Kotlin / WebView)

### Overview
This Android application functions as a **dedicated fullscreen WebView container** that loads and displays the external front-end web app while dynamically adjusting the system status bar icon color for optimal visibility.

### Architectural Overview: Initialization & UI Setup
- On `MainActivity` creation:
  - Inflates the layout and configures an immersive, edge-to-edge UI via `WindowCompat.setDecorFitsSystemWindows`.
  - Applies safe-area padding to the WebView using `ViewCompat.setOnApplyWindowInsetsListener` to avoid overlap with system UI elements.
  - Uses `WindowInsetsControllerCompat` to dynamically toggle light/dark status bar icons based on background luminance.

### WebView Configuration & Feature Enablement
- Enables JavaScript execution: `setJavaScriptEnabled(true)`
- Supports responsive scaling:  
  `setLoadWithOverviewMode(true)` and `setUseWideViewPort(true)`
- Allows gesture-based zoom, while disabling default zoom controls for a cleaner interface.
- Ensures compatibility with modern, interactive, mobile-friendly web content.

### Dynamic Status Bar Adaptation
- Implements a custom `WebViewClient` with an `onPageFinished` callback.
- Executes JavaScript to detect the page’s background color via `getComputedStyle(document.body).backgroundColor`.
- Parses the color using `Color.parseColor()`, calculates luminance (`0.299R + 0.587G + 0.114B`), and adjusts status bar icons accordingly for visibility.

### Navigation Handling & UX
- Overrides `onBackPressed()` to check if the WebView can navigate back before closing the activity.
- Prevents app crashes from malformed or unsupported color values.

### Design Considerations & Performance
- Focuses on minimalism and content immersion.
- Eliminates browser chrome and adapts UI to match the hosted website’s color scheme.
- Offers a native-like, fluid experience for both static and dynamic web applications.

---

## 🧠 Summary

| Component | Platform | Core Purpose | Key Technologies |
|------------|------------|---------------|------------------|
| Web App | HTML / JS / CSS | Fetches & displays live Google Sheets data | Vanilla JS, PapaParse, Fetch API |
| Android App | Kotlin | Hosts the web app in a fullscreen WebView with adaptive UI | WebView, WindowCompat, Color Parsing |

---

## 🚀 Deployment Notes

- **Web:** Upload HTML, CSS, and JS files to any static hosting platform (GitHub Pages, Netlify, Firebase Hosting, etc.).
- **Android:** Update `WebView.loadUrl()` in `MainActivity.kt` to point to the hosted web app URL.
- Ensure the hosted site uses HTTPS for compatibility with modern Android WebView security requirements.

---

## 📄 License
This project is open-source and free to use for educational and development purposes.
