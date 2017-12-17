echo OFF

cd ..\src

javac -encoding UTF8 -cp . main\*.java
javac -encoding UTF8 -cp . common\*.java

jar -cvf ..\lib\encoder.jar *

cd ..\build

pause