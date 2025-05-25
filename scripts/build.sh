#!/bin/bash
set -eu

chmod +x gradlew
./gradlew clean build -x test -x check