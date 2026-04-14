#!/bin/zsh -l

# This ensures that relative paths are correct no matter where the script is executed
cd "$(dirname "$0")"

cd ../..

if [[ "$ACTION" == "clean" ]]; then
  echo "Skipping string generation during Xcode clean"
  exit 0
fi

echo "Generating MR resources from .xml files"
./gradlew :shared:base:generateMRcommonMain < /dev/null
