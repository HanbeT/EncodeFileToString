echo OFF

cd ..

set TARGET=
echo 変換対象のフォルダパス、またはファイルパスを入力してください。
set /P TARGET="入力："

java -cp .;lib\* main.EncodeFileToString %TARGET%

cd bat

pause
