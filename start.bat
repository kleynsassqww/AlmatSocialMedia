@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-17.0.18
set PATH=%JAVA_HOME%\bin;%PATH%
cd /d C:\SocialMediaAlmat
call mvnw.cmd spring-boot:run > C:\SocialMediaAlmat\app.log 2>&1

