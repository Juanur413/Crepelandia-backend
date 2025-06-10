#!/bin/sh
set -e
MVNW_REPOURL="https://repo.maven.apache.org/maven2"
BASE_DIR=$(dirname "$0")
exec "$BASE_DIR/.mvn/wrapper/maven-wrapper.jar" "$@"
