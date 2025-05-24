#!/bin/bash
set -eu

echo "[start.sh] 開始: 環境変数を .env に書き出します..."
echo "DB_URL=${DB_URL}" > .env
echo "DB_USERNAME=${DB_USERNAME}" >> .env
echo "DB_PASSWORD=${DB_PASSWORD}" >> .env
echo "CLOUDINARY_API_KEY=${CLOUDINARY_API_KEY}" >> .env
echo "CLOUDINARY_API_SECRET=${CLOUDINARY_API_SECRET}" >> .env
echo "CLOUDINARY_CLOUD_NAME=${CLOUDINARY_CLOUD_NAME}" >> .env
echo "CLOUDINARY_URL=cloudinary://${CLOUDINARY_API_KEY}:${CLOUDINARY_API_SECRET}@${CLOUDINARY_CLOUD_NAME}" >> .env

echo "[start.sh] 開始：環境変数を読み込みます..."
source .env

echo "[start.sh] Spring Boot アプリケーションを起動します..."
java -jar build/libs/StudyLog.jar --spring.profiles.active=prod

echo "[start.sh] .env を削除します（セキュリティ対策）..."
rm .env