#!/bin/bash
set -eu

echo "===== Java環境確認 ====="
echo "JAVA_HOME=${JAVA_HOME:-"(未設定)"}"
echo "which java: $(which java || echo 'なし')"
java -version || echo "java -version 実行不可"

echo "===== gradlew ビルド開始 ====="
chmod +x gradlew
./gradlew bootJar -x test -x check