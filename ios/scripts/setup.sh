#!/bin/bash

# This ensures that relative paths are correct no matter where the script is executed
cd "$(dirname "$0")"

echo "⚙️  Checking file header"
if [ ! -f ../MateeStarter.xcworkspace/xcuserdata/`whoami`.xcuserdatad/IDETemplateMacros.plist ]; then
  echo "❌ File header is not set - setting now"
  echo -n "Enter your full name: "
  read fullname
  mkdir -p ../MateeStarter.xcworkspace/xcuserdata/`whoami`.xcuserdatad
  cp IDETemplateMacros.plist ../MateeStarter.xcworkspace/xcuserdata/`whoami`.xcuserdatad/
  sed -i "" -e "s/___FULLNAME___/$fullname/g" ../MateeStarter.xcworkspace/xcuserdata/`whoami`.xcuserdatad/IDETemplateMacros.plist
else
  echo "✅ File header is properly set"
fi

if [ ! -f ../../shared/core/src/commonMain/resources/MR/base/strings.xml ]; then
  echo "⚙️  Building Moko strings for the first time"
  ./generate-strings.sh
fi

if [ ! -d ../DomainLayer/KMPShared.xcframework ]; then
  echo "⚙️  Building KMP for the first time"
  ./build-kmp.sh
fi

echo "⚙️  Checking whether Homebrew is installed"
if command -v brew &> /dev/null; then
  echo "✅ Homebrew is installed"
else
  echo "❌ Homebrew is not installed"
  echo "Please visit https://brew.sh for more info"
  exit
fi

echo "⚙️  Checking whether SwiftLint is installed"
if command -v swiftlint &> /dev/null; then
  echo "✅ SwiftLint is installed"
else
  echo "❌ SwiftLint is not installed - installing now"
  brew install swiftlint
fi
