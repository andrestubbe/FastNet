@echo off
chcp 65001 >nul
call mvn clean install -DskipTests -q
if %ERRORLEVEL% NEQ 0 exit /b %ERRORLEVEL%
cd examples\Demo
call mvn compile exec:java -Dexec.mainClass=fastnet.TcpLoopbackDemo -q
cd ..\..
pause
