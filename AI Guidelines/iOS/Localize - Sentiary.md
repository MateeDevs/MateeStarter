# View Localization Guidelines

This guide provides a step-by-step process for localizing strings in SwiftUI views, ensuring consistency and maintainability across the Lemio project.

---

## 1. **Identify Hardcoded Strings**

First, review the view and identify all user-facing strings that are currently hardcoded (e.g., `Text("Hello, World!")`, `Button("Submit")`). These are candidates for localization.

---

## 2. **Define String Identifiers**

For each hardcoded string, create a unique identifier. Follow these critical rules to ensure consistency:

### a. Naming Convention
-   Identifiers **must** be in `snake_case`.
-   The identifier should describe the string's **location and function**, not its content. This is because the text might change in the future, but its purpose in the UI will likely remain the same.

### b. Identifier Structure
A good structure to follow is:
`{feature_or_location}_{view}_{component}_{property}`

-   **`feature_or_location`**: The name of the feature module (e.g., `settings`, `onboarding`, `affiliate`).
-   **`view`**: The name of the view where the string appears (e.g., `profile`).
-   **`component`**: The specific UI element (e.g., `title`, `save_button`, `error_message`).
-   **`property`**: The part of the component being set (e.g., `label`, `title`, `placeholder`).

### c. Examples
-   **Good:** `L10n.settings_profile_save_button_label`
    -   *Location:* Settings
    -   *View:* Profile
    -   *Component:* Save Button
    -   *Property:* Label
-   **Bad:** `L10n.save_my_changes_to_profile` (Based on content, not location/function)

---

## 3. **Upload Strings to Sentiary**

Add the new string identifiers and their corresponding English text to our localization service using the `sentiary_upload.sh` script.

-   **Command:** 
    `./AI Guidelines/iOS/Scripts/sentiary_upload.sh 'en-US' $'{"identifier_one":"First string", "identifier_two":"Second string with a\nnewline"}'`
-   **Action:** Group all new strings for a view into a single JSON payload. Remember to escape special characters like newlines with `\n`. The `$'...'` syntax in `bash` ensures the `\n` is interpreted as a real newline character.

---

## 4. **Fetch Strings into the Project**

After the strings have been successfully uploaded, pull them down into the local project. This will update the auto-generated `L10n.swift` file.

-   **Command:**
    `./AI Guidelines/iOS/Scripts/sentiary_fetch.sh <absolute_path_to_project_root>/ios`
-   **Important:** You must provide the absolute path to the `ios` directory as the first argument.

---

## 5. **Implement in the View**

Finally, replace the hardcoded strings in your SwiftUI view with the new `L10n` identifiers.
