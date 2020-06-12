@ECHO OFF
SETLOCAL EnableDelayedExpansion

ECHO Building TIBCO BPM Studio docker image tibco/bpm-studio:$$IMAGE_TAG_VERSION$$ for automated CI/CD...
ECHO.

REM look for command line parameters and translate into "option<arg>"
REM also identify installFile (parameter without leading '-')

FOR %%a IN (%*) DO (
  SET arg=%%a
  IF "!arg:~0,1!" EQU "-" ( SET "option!arg!=%%a" ) ELSE ( SET installFile=!arg! )
)

REM if help requested
IF DEFINED option-h (
  ECHO Usage: %0 -acceptLGPL [installer-file] [-h]
  ECHO Where:
  ECHO   -acceptLGPL = confirm acceptance of the LGPL license.
  ECHO   installer-file = full path of the TIBCO Business Studio - BPM Edition installer.
  ECHO   -h display this usage message.
  ECHO.
  EXIT /b -1
)

REM if LGPL not accepted
IF NOT DEFINED option-acceptLGPL (
  ECHO Building the docker image requires the download and installation of software
  ECHO under LGPL license.
  ECHO If you accept this download and license ^(see ./licenses/lgpl-license.txt^) then
  ECHO you should add the parameter -acceptLGPL to the command line.
  EXIT /b -1
)

REM was installer supplied on command line args
IF DEFINED installFile (
  ECHO Copying Studio Installer %!installFile!
  COPY %!installFile! image_template\
)

REM does the installer file exist
IF NOT EXIST .\image_template\TIB_business-studio-bpm-edition_?.?.?_linux*.zip (
  ECHO You must provide a Linux version of the TIBCO Business Studio - BPM Edition installer.
  ECHO The installer's name follows the pattern TIB_business-studio-bpm-edition_?.?.?_linux*.zip
  ECHO and must be copied to the sub-folder image_template.
  ECHO.
  ECHO Alternatively, specify the location of the file on the command line.
  ECHO Usage: %0 -acceptLGPL [installer-file] [-h]
  ECHO Where:
  ECHO   -acceptLGPL = confirm acceptance of the LGPL license.
  ECHO   installer-file = full path of the TIBCO Business Studio - BPM Edition installer.
  ECHO   -h display this usage message.
  ECHO.
  EXIT /b -1
)

docker build -t tibco/bpm-studio:$$IMAGE_TAG_VERSION$$ --build-arg version=5.0 .\image_template

ECHO Removing temporary install image...	
docker images -q --filter label=maintainer="TIBCO Software Inc" --filter label=image=bpm-studio-installation > .install_images.tmp
FOR /f %%i in (.install_images.tmp) DO ( docker rmi %%i )
DEL .install_images.tmp