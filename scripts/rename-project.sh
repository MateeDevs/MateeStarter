#!/bin/bash

# Script to rename the project from MateeStarter to a new name
# This handles Android and shared modules. For iOS, use ios/scripts/rename.sh

set -e

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

old_name="MateeStarter"
old_name_lowercase=$(echo "${old_name}" | tr '[:upper:]' '[:lower:]')
old_name_uppercase=$(echo "${old_name}" | tr '[:lower:]' '[:upper:]')

echo -n "Enter new project name (e.g., MyApp): "
read new_name
if [ -z "$new_name" ]; then
    echo "Error: Project name cannot be empty"
    exit 1
fi

new_name_lowercase=$(echo "${new_name}" | tr '[:upper:]' '[:lower:]')
new_name_uppercase=$(echo "${new_name}" | tr '[:lower:]' '[:upper:]')

# Convert to valid package name (lowercase, no spaces, no special chars except dots)
package_name=$(echo "${new_name_lowercase}" | sed 's/[^a-z0-9]//g')

echo ""
echo "Renaming project from '${old_name}' to '${new_name}'"
echo "Package name will be: ${package_name}"
echo ""
read -p "Continue? (y/n) " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "Aborted."
    exit 1
fi

cd "$PROJECT_ROOT"

echo "1. Updating settings.gradle.kts..."
sed -i '' "s/rootProject.name = \"${old_name}\"/rootProject.name = \"${new_name}\"/g" settings.gradle.kts

echo "2. Updating Application.kt..."
sed -i '' "s/const val id = \"cz.matee.starter.kmp.android\"/const val id = \"cz.matee.${package_name}.kmp.android\"/g" build-logic/convention/src/main/kotlin/constants/Application.kt
sed -i '' "s/const val appName = \"${old_name}\"/const val appName = \"${new_name}\"/g" build-logic/convention/src/main/kotlin/constants/Application.kt

echo "3. Updating libs.versions.toml plugin names..."
sed -i '' "s/mateeStarter-android-application-compose/${new_name_lowercase}-android-application-compose/g" gradle/libs.versions.toml
sed -i '' "s/mateeStarter-android-application-core/${new_name_lowercase}-android-application-core/g" gradle/libs.versions.toml
sed -i '' "s/mateeStarter-android-library-compose/${new_name_lowercase}-android-library-compose/g" gradle/libs.versions.toml
sed -i '' "s/mateeStarter-android-library-core/${new_name_lowercase}-android-library-core/g" gradle/libs.versions.toml
sed -i '' "s/mateeStarter-kmp-library-core/${new_name_lowercase}-kmp-library-core/g" gradle/libs.versions.toml
sed -i '' "s/mateeStarter-kmp-library-compose/${new_name_lowercase}-kmp-library-compose/g" gradle/libs.versions.toml
sed -i '' "s/mateeStarter-kmp-framework-library/${new_name_lowercase}-kmp-framework-library/g" gradle/libs.versions.toml

echo "4. Updating build-logic/convention/build.gradle.kts..."
sed -i '' "s/mateeStarter.android.application.compose/${new_name_lowercase}.android.application.compose/g" build-logic/convention/build.gradle.kts
sed -i '' "s/mateeStarter.android.application.core/${new_name_lowercase}.android.application.core/g" build-logic/convention/build.gradle.kts
sed -i '' "s/mateeStarter.android.library.compose/${new_name_lowercase}.android.library.compose/g" build-logic/convention/build.gradle.kts
sed -i '' "s/mateeStarter.android.library.core/${new_name_lowercase}.android.library.core/g" build-logic/convention/build.gradle.kts
sed -i '' "s/mateeStarter.kmp.library.core/${new_name_lowercase}.kmp.library.core/g" build-logic/convention/build.gradle.kts
sed -i '' "s/mateeStarter.kmp.library.compose/${new_name_lowercase}.kmp.library.compose/g" build-logic/convention/build.gradle.kts
sed -i '' "s/mateeStarter.kmp.framework.library/${new_name_lowercase}.kmp.framework.library/g" build-logic/convention/build.gradle.kts

echo "5. Updating build.gradle.kts files in modules..."
find . -name "build.gradle.kts" -type f ! -path "*/build/*" ! -path "*/build-logic/*" -exec sed -i '' "s/mateeStarter.android.application.compose/${new_name_lowercase}.android.application.compose/g" {} +
find . -name "build.gradle.kts" -type f ! -path "*/build/*" ! -path "*/build-logic/*" -exec sed -i '' "s/mateeStarter.android.application.core/${new_name_lowercase}.android.application.core/g" {} +
find . -name "build.gradle.kts" -type f ! -path "*/build/*" ! -path "*/build-logic/*" -exec sed -i '' "s/mateeStarter.android.library.compose/${new_name_lowercase}.android.library.compose/g" {} +
find . -name "build.gradle.kts" -type f ! -path "*/build/*" ! -path "*/build-logic/*" -exec sed -i '' "s/mateeStarter.android.library.core/${new_name_lowercase}.android.library.core/g" {} +
find . -name "build.gradle.kts" -type f ! -path "*/build/*" ! -path "*/build-logic/*" -exec sed -i '' "s/mateeStarter.kmp.library.core/${new_name_lowercase}.kmp.library.core/g" {} +
find . -name "build.gradle.kts" -type f ! -path "*/build/*" ! -path "*/build-logic/*" -exec sed -i '' "s/mateeStarter.kmp.library.compose/${new_name_lowercase}.kmp.library.compose/g" {} +
find . -name "build.gradle.kts" -type f ! -path "*/build/*" ! -path "*/build-logic/*" -exec sed -i '' "s/mateeStarter.kmp.framework.library/${new_name_lowercase}.kmp.framework.library/g" {} +

echo "6. Creating new README.md..."
cat > README.md << EOF
# ${new_name}

## Description

${new_name} is a Kotlin Multiplatform mobile application for Android and iOS.

## Architecture

The project uses Clean Architecture with shared business logic across platforms:
- **Shared**: Data layer, domain layer, view models, and Compose Multiplatform UI
- **Platform-specific**: Navigation only

## Getting Started

### Prerequisites

- Android Studio or IntelliJ IDEA
- Xcode (for iOS development)
- JDK 17 or higher

### Setup

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. For iOS: Open \`ios/${new_name}.xcworkspace\` in Xcode

### Building

#### Android

The project uses Android build variants with two dimensions:

1. **Build Type** (debug/release):
   - \`debug\` - Development builds with debug signing
   - \`release\` - Release builds with release signing

2. **API Variant** (alpha/production):
   - \`alpha\` - Connected to alpha/staging data sources (app name prefixed with "[A]")
   - \`production\` - Connected to production data sources

Available build variants:
- \`alphaDebug\` - Alpha API with debug build
- \`alphaRelease\` - Alpha API with release build
- \`productionDebug\` - Production API with debug build
- \`productionRelease\` - Production API with release build

Build specific variants:
\`\`\`bash
# Build alpha debug variant
./gradlew assembleAlphaDebug

# Build production release variant
./gradlew assembleProductionRelease
\`\`\`

#### iOS
Open the workspace in Xcode and build from there.

## Project Structure

- \`shared/\` - Shared Kotlin Multiplatform modules
  - \`base/\` - Base classes and utilities
  - \`umbrella/\` - Main shared module combining all features
  - \`samplefeature/\` - Example feature module
- \`android/\` - Android-specific modules
  - \`app/\` - Main Android application
  - \`samplefeature/\` - Example feature module
  - \`shared/\` - Shared Android code
- \`ios/\` - iOS project

## Convention Plugins

The project uses Gradle convention plugins (located in \`build-logic/convention\`) to standardize build configuration across modules. These plugins automatically apply common configurations, dependencies, and settings.

### Available Convention Plugins

#### Android Modules
- **\`android-application-compose\`** - For Android application modules with Compose support
  - Applies Android application plugin, Compose compiler, and Compose dependencies
  - Configures build variants (alpha/production), signing, and Twine string generation
- **\`android-application-core\`** - For Android application modules without Compose
  - Same as above but without Compose configuration
- **\`android-library-compose\`** - For Android library modules with Compose support
  - Applies Android library plugin and Compose dependencies
- **\`android-library-core\`** - For Android library modules without Compose
  - Applies Android library plugin with standard Android configuration

#### Kotlin Multiplatform Modules
- **\`kmp-library-core\`** - For KMP library modules
  - Configures Kotlin Multiplatform with Android and iOS targets
  - Applies Moko Resources for shared string resources
  - Sets up common dependencies and test configuration
- **\`kmp-library-compose\`** - For KMP library modules with Compose Multiplatform
  - Extends \`kmp-library-core\` and adds Compose Multiplatform support
  - Configures Compose compiler and dependencies
- **\`kmp-framework-library\`** - For KMP modules that generate iOS frameworks
  - Extends \`kmp-library-core\` and configures iOS framework generation
  - Used by \`:shared:umbrella\` module to generate the XCFramework for iOS

### Usage

Simply apply the convention plugin in your module's \`build.gradle.kts\`:

\`\`\`kotlin
plugins {
    alias(libs.plugins.${new_name_lowercase}.android.application.compose)
    // or
    alias(libs.plugins.${new_name_lowercase}.kmp.library.compose)
}
\`\`\`

The plugin IDs are defined in \`gradle/libs.versions.toml\` and can be customized after renaming the project.

## Technologies

- Kotlin Multiplatform
- Compose Multiplatform (Shared UI)
- Ktor (Networking)
- Koin (Dependency Injection for Android/Shared)
- Factory (Dependency Injection for iOS)

## License

[Add your license here]
EOF

echo ""
echo "✅ Renaming successful!"
echo ""
echo "⚠️  IMPORTANT: Manual steps required:"
echo "   1. Update google-services.json files:"
echo "      - android/app/src/alpha/google-services.json"
echo "      - android/app/src/production/google-services.json"
echo "   2. Update iOS project using: ios/scripts/rename.sh"
echo "   3. Review and update any Firebase/Google Services configuration"
echo "   4. Update package names in AndroidManifest.xml if needed"
echo "   5. Sync Gradle files in your IDE"
echo ""

