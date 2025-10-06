#!/bin/sh

# sentiary_upload.sh
#
# Usage: ./sentiary_upload.sh <language_code> <json_data>
#
# <language_code>: The language code for the import (e.g., en-US)
# <json_data>: The JSON data to upload, provided directly as a string argument (not a file path).
#
# Example:
# ./sentiary_upload.sh en-US '{"key": "value"}'
#
# Created by Lukáš Matuška on 27.06.2025.
#

SENTIARY_API_KEY="YOUR-API-KEY-HERE"
SENTIARY_PROJECT_ID="YOUR-PROJECT-ID-HERE"

LANGUAGE="$1"
JSON_DATA="$2"

if [ -z "$LANGUAGE" ] || [ -z "$JSON_DATA" ]; then
  echo "Usage: $0 <language_code> <json_data>"
  exit 1
fi

curl --location "https://api.sentiary.com/api/v1/batch/$SENTIARY_PROJECT_ID/import?languageId=$LANGUAGE&format=json" \
  --header "Authorization: Ribbon $SENTIARY_API_KEY" \
  --header 'User-Agent: sentiary_fetch script' \
  --header 'Content-Type: application/json' \
  --data "$JSON_DATA"
