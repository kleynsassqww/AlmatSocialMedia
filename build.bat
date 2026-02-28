@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-17.0.18
set PATH=%JAVA_HOME%\bin;%PATH%
cd /d C:\SocialMediaAlmat
call mvnw.cmd package -DskipTests > C:\SocialMediaAlmat\pkg.log 2>&1
echo BUILD_DONE >> C:\SocialMediaAlmat\pkg.log

