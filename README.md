This front-end application is a statically served HTML document that leverages JavaScript and a CSV parsing library to consume data from an external cloud-based spreadsheet and render it into structured tabular formats within the DOM.

Architectural Overview:  
Upon window load, an initialization function is automatically executed to trigger the data retrieval and rendering process.  
A user-interactive **Refresh** button allows manual reloading of data without refreshing the page, while a **Redirect** button opens the live Google Sheets document in a new browser tab.

External Data Source Integration:  
The application connects directly to a publicly accessible Google Sheets document through a CSV export endpoint.  
This ensures up-to-date data retrieval from the cloud without any server-side components.

Data Acquisition Layer:  
The `fetch()` API is used to request data in CSV format.  
If successful, the CSV content is passed to the parsing layer; if not, a graceful fallback hides the loader, alerts the user, and logs an error message to the console.

Client-Side CSV Parsing:  
The application employs **PapaParse**, a lightweight client-side CSV parsing library, to transform raw CSV text into a structured array for seamless integration with the DOM rendering logic.

DOM Rendering Logic:  
All content generation is handled dynamically using vanilla JavaScript.  
Before new data is displayed, previously rendered content is cleared to maintain a clean and consistent view.  
No external frameworks are required for DOM manipulation.

Dynamic UI Composition:  
Two main data tables are rendered dynamically to represent portfolio details and summary information.  
This separation maintains clarity and organization of the displayed data.

Responsive Styling & UX Enhancements:  
The interface uses a clean, responsive layout defined in an external CSS file.  
A full-screen loader animation provides visual feedback during data fetching.  
Accessibility considerations are included through ARIA attributes and intuitive button placement.

Error Handling & Fallbacks:  
If a network issue or data access error occurs, the application hides the loader, displays a user-facing alert, and logs technical details to assist with debugging.

Caching Considerations:  
To prevent outdated files from being loaded, developers can append version query parameters to CSS or JS file URLs (e.g., `script.js?v=2`) to bypass browser caching and ensure the latest assets are fetched.
