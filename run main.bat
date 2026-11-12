@echo off
setlocal

cd /d "%~dp0"

set "JAVAFX_LIB=%~dp0lib\javafx-sdk-25.0.5\lib"
set "JAVAFX_BIN=%~dp0lib\javafx-sdk-25.0.5\bin"
set "OUT=%~dp0bin"
set "SRC=%~dp0src"

where java >nul 2>&1
if errorlevel 1 (
    echo Java was not found on PATH. Install a JDK and try again.
    pause
    exit /b 1
)

where javac >nul 2>&1
if errorlevel 1 (
    echo javac was not found on PATH. Install a JDK ^(not only a JRE^) and try again.
    pause
    exit /b 1
)

if not exist "%JAVAFX_LIB%" (
    echo JavaFX SDK libraries not found:
    echo   %JAVAFX_LIB%
    pause
    exit /b 1
)

if not exist "%OUT%" mkdir "%OUT%"

rem Native JavaFX DLLs must be findable on Windows.
set "PATH=%JAVAFX_BIN%;%PATH%"

rem javac does not expand "*.java" on a full path; list app sources explicitly
rem (omit *Test.java so JUnit is not required to run the app).
set "SOURCES=%TEMP%\cs2114-sources.txt"
(
    echo %SRC%\Main.java
    echo %SRC%\Controller.java
    echo %SRC%\View.java
    echo %SRC%\Restaurant.java
    echo %SRC%\Party.java
    echo %SRC%\Table.java
    echo %SRC%\WaitlistQueue.java
) > "%SOURCES%"

echo Compiling sources...
javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml -d "%OUT%" @"%SOURCES%"
if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b 1
)

echo Starting Main...
java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics -cp "%OUT%" Main
set "EXIT_CODE=%ERRORLEVEL%"

if not "%EXIT_CODE%"=="0" (
    echo Program exited with code %EXIT_CODE%.
    pause
    exit /b %EXIT_CODE%
)

endlocal
