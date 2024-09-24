#!/bin/zsh -l

# This fixes the issue with the script not being able to find the swiftlint command on Apple Silicon
if [[ "$(uname -m)" == arm64 ]]
then
    export PATH="/opt/homebrew/bin:$PATH"
fi

echo "Running SwiftLint"
swiftlint --fix --config .swiftlint.yml
swiftlint --config .swiftlint.yml