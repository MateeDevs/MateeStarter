if [ "$ACTION" = "clean" ]; then
  echo "Running Gradle clean for Xcode clean"
  ./gradlew clean < /dev/null
  exit $?
fi

./gradlew :shared:umbrella:embedAndSignAppleFrameworkForXcode < /dev/null | ./ios/scripts/kmp-beautify.sh

# Copy the framework to indexer directory to support Xcode hinting/autocomplete
DERIVED_DATA_DIR="$(echo "${TARGET_BUILD_DIR}" | awk -F'/Build/' '{print $1}')"
INDEXER_DATA_DIR="${DERIVED_DATA_DIR}/Index.noindex/Build/Products/Debug-${PLATFORM_NAME}"
mkdir -p "$INDEXER_DATA_DIR"
cp -R "shared/umbrella/build/xcode-frameworks/$CONFIGURATION/$SDK_NAME/"* "${INDEXER_DATA_DIR}"
