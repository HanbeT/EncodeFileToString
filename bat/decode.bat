echo OFF

cd ..

set TARGET=
echo �ϊ��Ώۂ̃t�H���_�p�X�A�܂��̓t�@�C���p�X����͂��Ă��������B
set /P TARGET="���́F"

java -cp .;lib\* main.DecodeStringToFile %TARGET%

cd bat

pause
