#!/bin/zsh

# Check if a name argument was provided
if [[ -z "$1" ]]; then
  echo "Usage: $0 \"Full Name\""
  exit 1
fi

AUTHOR_NAME="$1"
TODAYS_DATE=$(date +"%d.%m.%Y")
CURRENT_YEAR=$(date +"%Y")

# Output the formatted comment
echo "//"
echo "//  Created by $AUTHOR_NAME on $TODAYS_DATE"
echo "//  Copyright © $CURRENT_YEAR Matee. All rights reserved."
echo "//"
