#!/bin/bash
set -e

echo "[start.sh] 開始：環境変数を読み込みます..."
source .env

echo "[start.sh] Spring Boot アプリケーションを起動します..."
java -jar build/libs/StudyLog.jar --spring.profiles.active=local

# echo "[start.sh] .env を削除します（セキュリティ対策）..."
# rm .env