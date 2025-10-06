#!/usr/bin/env bash

# Sentiary fetch
#
# Fetches strings from Sentiary
#
# @param 1 Project root - the absolute path to the root of the iOS project
#

PROJECT_ROOT="$1"
if [ -z ${1+x} ]; then
  PROJECT_ROOT=$(pwd)
fi

# Specify list of supported languages
#LANGUAGES=("cs-CZ:cs" "en-US:en" "pl-PL:pl" "sk-SK:sk")
LANGUAGES=("en-US:en")
SENTIARY_API_KEY="YOUR-API-TOKEN-HERE"
SENTIARY_PROJECT_ID="YOUR-PROJECT-ID-HERE"

IOS_RES_FOLDER="$PROJECT_ROOT/PresentationLayer/UIToolkit/Sources/UIToolkit/Resources/Localizable"

function download() {
    OUT_FILE="$1"
    FORMAT="$2"
    LANGUAGE="$3"

   curl \
        -G "https://api.sentiary.com/api/v1/batch/$SENTIARY_PROJECT_ID/export" \
        -A 'sentiary_fetch script' \
        -d "languageId=$LANGUAGE" \
        -d "format=$FORMAT" \
        -H "Authorization: Ribbon $SENTIARY_API_KEY" \
        -# \
        > "$OUT_FILE"
}

function download_iOS() {
  for language in "${LANGUAGES[@]}"; do
    CODE="${language%%:*}"
    NAME="${language##*:}"
    echo "[iOS] Downloading $NAME"
    mkdir -p "$IOS_RES_FOLDER/$NAME.lproj/"
    download "$IOS_RES_FOLDER/$NAME.lproj/Localizable.strings" "apple" "$CODE"
  done

  # === Move english resources to base directory ===

  echo "[iOS] Copying en to Base"
  mkdir -p "$IOS_RES_FOLDER/Base.lproj"
  cp "$IOS_RES_FOLDER/en.lproj/Localizable.strings" "$IOS_RES_FOLDER/Base.lproj/Localizable.strings"
}

download_iOS
