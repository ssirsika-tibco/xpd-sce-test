echo Copyright TIBCO Software Inc 2004 - 2019. All rights reserved.

# is this a call for help
if [ "$1" == "-h" ]; then
  echo Usage: %0 installer-path
  echo Where:
  echo   installer-path = location of the TIBCO Business Studio - Cloud BPM Edition installer.
  exit 1
fi

# was installer supplied on command line args
if [ -n "$1" ]; then
  echo Copying Studio Installer $1
  cp $1 image_template/.
fi

# does the installer file exist
if [[ ! -e .\image_template\TIB_business-studio-cloud-bpm-edition_?.?.?_linux*.zip ]]; then
  echo You must provide a Linux version of the TIBCO Business Studio - Cloud BPM Edition installer.
  echo The installer\'s name follows the pattern TIB_business-studio-cloud-bpm-edition_?.?.?_linux*.zip
  echo and must be copied to the sub-folder image_template. Alternatively, specify the location of the file on the command line.
  exit 1
fi

# Building Docker Image
docker build -t tibco/sce-studio:5.0.0 ./image_template
