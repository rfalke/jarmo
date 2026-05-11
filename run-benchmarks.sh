#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ENV_FILE="$SCRIPT_DIR/.env"
BENCHMARK_JAR="$SCRIPT_DIR/jarmo-benchmark/target/benchmarks.jar"

if [[ ! -f "$ENV_FILE" ]]; then
    echo "Error: .env file not found at $ENV_FILE"
    echo "Create it with:"
    echo "  JDK8_HOME=/path/to/jdk8"
    echo "  JDK25_HOME=/path/to/jdk25"
    exit 1
fi

source "$ENV_FILE"

for var in JDK8_HOME JDK25_HOME; do
    if [[ -z "${!var:-}" ]]; then
        echo "Error: $var is not set in .env"
        exit 1
    fi
    if [[ ! -x "${!var}/bin/java" ]]; then
        echo "Error: ${!var}/bin/java not found or not executable"
        exit 1
    fi
done

if [[ ! -f "$BENCHMARK_JAR" ]]; then
    echo "Error: $BENCHMARK_JAR not found. Run 'make' first."
    exit 1
fi

TIMESTAMP="$(date +%Y%m%d_%H%M%S)"
REPORT="$SCRIPT_DIR/perf_report_${TIMESTAMP}.txt"
TMPOUT=$(mktemp)
trap 'rm -f "$TMPOUT"' EXIT

RESULT_JDK8=""
RESULT_JDK25=""

{
    echo "========================================"
    echo "Benchmark run: $(date)"
    echo "========================================"

    for JDK_LABEL in JDK8 JDK25; do
        VAR="${JDK_LABEL}_HOME"
        JAVA="${!VAR}/bin/java"
        VERSION="$("$JAVA" -version 2>&1 | head -1)"

        echo ""
        echo "----------------------------------------"
        echo "$JDK_LABEL: $VERSION"
        echo "JAVA_HOME: ${!VAR}"
        echo "----------------------------------------"
        echo ""

        "$JAVA" -jar "$BENCHMARK_JAR" "decodeOnly" \
            -wi 5 -i 5 -f 4 -gc true \
            | tee "$TMPOUT"
        RESULT_LINE="$(grep 'decodeOnly' "$TMPOUT" | tail -1)"
        eval "RESULT_${JDK_LABEL}=\"\$RESULT_LINE\""
    done

    HEADER="$(grep '^Benchmark' "$TMPOUT" | tail -1)"

    echo ""
    echo "========================================"
    echo "Comparison"
    echo "========================================"
    echo "       $HEADER"
    echo "JDK8:  $RESULT_JDK8"
    echo "JDK25: $RESULT_JDK25"
    echo "========================================"
    echo "Done: $(date)"
    echo "========================================"
} 2>&1 | tee "$REPORT"