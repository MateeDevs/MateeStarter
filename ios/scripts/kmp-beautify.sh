#!/bin/bash

SCRIPT=${1:-pre-action}
TARGET=${2:-KMP}

if [[ -z "$CI" ]]; then
    function format_line {
      # Don't format locally
      echo "$1"
    }
else
  function format_line {
    echo -n "PhaseScriptExecution "
    echo -n "$1"
    echo " / $SCRIPT.sh (in target: $TARGET)"
  }
fi

while IFS= read -r line; do
  format_line "$line"
done

