#!/bin/bash
set -e

# echo "[start.sh] 環境変数TEST確認"
# echo "[DEBUG] TEST (printenv) = $(printenv TEST)"
# echo "[start.sh] 変数表示&ファイル出力"
# echo "${TEST}"
# echo "[start.sh] 変数表示&ファイル出力2"
# echo "$TEST"
# echo "$TEST" > test.txt
# cat test.txt

echo "[start.sh] 開始：環境変数を読み込みます..."
# source .env

echo "[start.sh] Spring Boot アプリケーションを起動します..."
java -jar build/libs/StudyLog.jar --spring.profiles.active=prod

echo "[start.sh] .env を削除します（セキュリティ対策）..."
rm .env