#!/bin/bash

SCRIPT=${1:-pre-action}
TARGET=${2:-KMP}

if [[ -z "$CI" ]]; then
    # format_line echoes its first argument unchanged (used when not running in CI).
    function format_line {
      # Don't format locally
      echo "$1"
    }
else
  # format_line outputs a single formatted line for CI: it prefixes "PhaseScriptExecution ", appends its first argument as the message, and suffixes " / <SCRIPT>.sh (in target: <TARGET>)".
  function format_line {
    echo -n "PhaseScriptExecution "
    echo -n "$1"
    echo " / $SCRIPT.sh (in target: $TARGET)"
  }
fi

while IFS= read -r line; do
  format_line "$line"
done

