@ECHO OFF
ECHO Copyright ^(c^) TIBCO Software Inc 2004 - 2019. All rights reserved.
ECHO.

REM look for command line parameters and translate into "option<arg>"
REM also identify installFile (parameter without leading '-')
SETLOCAL EnableDelayedExpansion
FOR %%a IN (%*) DO (
  SET arg=%%a
  IF "!arg:~0,1!" EQU "-" ( SET "option!arg!=%%a" ) ELSE ( SET installFile=!arg! )
)

REM if help requested
IF DEFINED option-h (
  ECHO Usage: %0 -acceptLGPL installer-path [-h]
  ECHO Where:
  ECHO   -acceptLGPL = confirm acceptance of the LGPL license.
  ECHO   installer-path = location of the TIBCO Business Studio - Cloud BPM Edition installer.
  ECHO   -h display this usage message.
  ECHO.
  EXIT /b -1
)

REM if LGPL not accepted
IF NOT DEFINED option-acceptLGPL (
  ECHO Building the docker image requires the download and installation of software
  ECHO under LGPL license.
  ECHO If you accept this download and license ^(see ./licenses/lgpl-license.txt^) then
  ECHO you should add the parameter -acceptLGPL to the command line described above.
  EXIT /b -1
)

SET installFile

REM was installer supplied on command line args
IF DEFINED installFile (
  ECHO Copying Studio Installer %!installFile!
  COPY %!installFile! image_template\
)

REM does the installer file exist
IF NOT EXIST .\image_template\TIB_business-studio-cloud-bpm-edition_?.?.?_linux*.zip (
  ECHO You must provide a Linux version of the TIBCO Business Studio - Cloud BPM Edition installer.
  ECHO The installer's name follows the pattern TIB_business-studio-cloud-bpm-edition_?.?.?_linux*.zip
  ECHO and must be copied to the sub-folder image_template.
  ECHO.
  ECHO Alternatively, specify the location of the file on the command line.
  ECHO Usage: %0 -acceptLGPL installer-path [-h]
  ECHO Where:
  ECHO   -acceptLGPL = confirm acceptance of the LGPL license.
  ECHO   installer-path = location of the TIBCO Business Studio - Cloud BPM Edition installer.
  ECHO   -h display this usage message.
  ECHO.
  EXIT /b -1
)

ECHO Building Docker Image
docker build -t tibco/sce-studio --build-arg version=5.0 .\image_template
