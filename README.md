# 学習日報アプリ

## アプリ概要

本アプリは、エンジニアや学習者が日々の学習内容を記録し、可視化できる**学習日報アプリ**です。

- 学習の習慣化を支援
- 日々の積み上げを見える化

対象ユーザー：
- 学習ログの振り返りや自主学習を継続したい個人
---

## 操作マニュアル
[学習日報アプリ(操作マニュアル)](https://kirias1996.github.io/studyLog/manual/studyLog_manual.html)


##  機能一覧

-  **ユーザー登録 / 認証**
   - メールアドレスとパスワードで新規登録
   - Spring Security によるログイン・ログアウト機能
   - パスワードはハッシュ化してDB保存
   - 初期アイコン,ユーザ名を設定


-  **プロフィール編集**
   - ユーザー名、自己紹介文の編集
   - プロフィール画像のアップロード（Cloudinary連携）
   - ファイル形式/サイズ制限あり（例：PNG/JPG、最大2MB）

-  **学習日報管理**
   - 日報の投稿 / 編集 / 削除
   - 学習内容、学習時間（時・分）、タグ、自由記述が可能
   - タグはオートコンプリート対応（既存候補を表示）

-  **ダッシュボード機能**
   - 累計学習時間（時間＋分の合計）
   - 累計投稿日数（通算投稿数）
   - 連続投稿日数（中断なしの最大連続日数）

-  **タグ入力機能**
   - 入力補助機能（最大10件候補を自動表示）
   - 表示時は先頭大文字化（例：java → Java）

-  **バリデーション・エラー対応**
   - 学習時間未入力、タグ未入力、画像形式不正などをサーバサイドでチェック
   - クライアント側でも画像サイズなどを即時検出
   - 他ユーザの日報へのアクセス時、エラー画面を表示
   - 存在しない日報へのアクセス時、エラー画面を表示

-  **Cloudinary連携**
   - プロフィール画像をクラウドにアップロード
   - DB保存失敗時にはアップロード画像を削除するロールバック機能あり


---

##  技術スタック
- **言語 / フレームワーク**：Java 21, Spring Boot 3.4.4, Thymeleaf
- **フロントエンド**：HTML, CSS, JavaScript（バニラ）
- **データベース**：PostgreSQL16（JPA/Hibernate）
- **クラウドストレージ**：Cloudinary
- **デプロイ**：Render（本番環境対応）
- **その他**：Spring Security（認証）、Validation、ExceptionHandler、GitHub Actions

---

### 各種ツールのインストール
1. JDK21のインストール  
以下の公式ガイドに従って、JDK 21 をインストールしてください。  
環境変数（`JAVA_HOME`）の設定まで行っておくとスムーズです。  
※JAVA_HOMEは、GradleやSpring Bootの内部処理でJavaコンパイラや実行環境を特定するために利用されます。パス設定後は、java -version や echo $JAVA_HOME で反映確認してください。  
 [Oracle公式インストールガイド（日本語）](https://docs.oracle.com/javase/jp/21/install/index.html)

---
2. PostgreSQLのインストール  
以下の手順に従って PostgreSQL（バージョン15以上推奨）をインストールしてください。  
デフォルト設定でも問題ありません。DB名などは後ほど設定できます。  
[日本PostgreSQLユーザ会：インストールガイド](https://lets.postgresql.jp/map/install)

---

3. Git Bash（Windows用Bash）のインストール  
Windows PCを利用している場合、`.sh` ファイルを実行するために、Git Bash を導入してください。  
インストール後、プロジェクトディレクトリで「Git Bash Here」を選択できます。  
[Git Bashをインストールする方法（Scrapbox記事）](https://scrapbox.io/interaction-lab-git/Git_Bash_%E3%82%92%E3%82%A4%E3%83%B3%E3%82%B9%E3%83%88%E3%83%BC%E3%83%AB%E3%81%99%E3%82%8B%E6%96%B9%E6%B3%95)

---
### セットアップ手順(ローカル環境) 
Windowsをご利用のかたは「Git Bash」Macをご利用の方は「ターミナル」を起動してから下記の手順を実行してください。

---

1. studyLogプロジェクトをクローン
```bash
git clone https://github.com/kirias1996/studyLog.git
```
---
2. プロパティファイル作成（必要に応じて）
```bash
cd studyLog
cp src/main/resources/application-sample.properties src/main/resources/application-local.properties
```
---
3. DB作成  
DB名/ユーザ名/パスワードを任意の名前で設定してください。  
`.env`を使用し接続情報を読み込みます。

---
4. Cloudinaryアカウント作成  
[Cloudinary公式ページ](https://cloudinary.com/)でアカウント作成をしてください。  
※アカウント作成しなくても学習日報アプリはご利用できます。  
ユーザプロフィールのアイコン画像アップロードのみ利用できません。

---
5. Cloudinary・DB接続情報・画像アップロードフォルダなどを application-local.properties に記述  
※`.env`を使用した環境変数読み込みを推奨  
環境変数読み込みの記述方法  
```プロパティ名=${環境変数名}```
---
6. `.env`ファイルの作成  
下記コマンドで.envファイルを作成
```bash
cp ./.env.sample ./.env
```  
```
記述例
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
CLOUDINARY_URL=your_cloudinary_url
```
---
7. DB初期設定  
psqlが実行できない場合は、環境変数PATHにPostgreSQL の bin ディレクトリが含まれているか確認してください。  
```bash
#psql実行確認コマンド
psql --version
```

```bash
#DDL文の実行
psql -U "ユーザ名" -d "DB名" -f src/main/resources/schema.sql
#パスワードを入力すると実行されます。

#tagsテーブル初期データ登録
psql -U "ユーザ名" -d "DB名" -f src/main/resources/tags_insert.sql
#パスワードを入力すると実行されます。
```


---
8. jarファイルの生成  
下記のコマンドを実行して「BUILD SUCCESSFUL in ○○s」と表示されると成功です。  
```bash
./scripts/build.sh
```

---
9. アプリケーション起動  
下記のコマンドを実行して「Started StudyLogApplication」のログが出力されたら、ブラウザで(http://localhost:8080/login)にアクセスし、ログイン画面が表示されることを確認してください。
```bash
./scripts/start.sh
```
---



