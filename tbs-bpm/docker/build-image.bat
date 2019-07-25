@ECHO OFF
ECHO Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.
ECHO.

REM is this a call for help
IF -%1-==--h- (
  ECHO Usage: %0 installer-path
  ECHO Where:
  ECHO   installer-path = location of the TIBCO Business Studio - Cloud BPM Edition installer.
  EXIT /b -1
)

REM was installer supplied on command line args
IF NOT -%1-==-- (
  ECHO Copying Studio Installer %1
  COPY %1 image_template\
)

REM does the installer file exist
IF NOT EXIST .\image_template\TIB_business-studio-cloud-bpm-edition_?.?.?_linux*.zip (
  ECHO You must provide a Linux version of the TIBCO Business Studio - Cloud BPM Edition installer.
  ECHO The installer's name follows the pattern TIB_business-studio-cloud-bpm-edition_?.?.?_linux*.zip
  ECHO and must be copied to the sub-folder image_template. Alternatively, specify the location of the file on the command line.
  EXIT /b -1
)

ECHO Building Docker Image
docker build -t tibco/sce-studio --build-arg version=5.0 .\image_template