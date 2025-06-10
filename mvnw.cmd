@echo off
setlocal
set MVNW_REPOURL=https://repo.maven.apache.org/maven2
set BASE_DIR=%~dp0
java -jar "%BASE_DIR%\.mvn\wrapper\maven-wrapper.jar" %*
