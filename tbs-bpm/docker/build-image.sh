#!/usr/bin/env bash
echo Copyright TIBCO Software Inc 2004 - 2019. All rights reserved.
echo

function usage() {
  echo Usage: ${0} -acceptLGPL [installer-file] [-h]
  echo Where:
  echo -e '\t -acceptLGPL = confirm acceptance of the LGPL license.'
  echo -e '\t installer-path = full path of the TIBCO Business Studio - Cloud BPM Edition installer.'
  echo -e '\t -h display this usage message.'
  echo
}

# look for command line parameters
while [ "$1" != "" ]; do
  case "$1" in
    -h )
      usage
      exit
      ;;

    -acceptLGPL )
      acceptLGPL=true
      ;;

    * )
      if [ -r $1 ]; then
        installFile=$1
      fi
  esac
  shift
done

# if licence not accepted
if [ ! $acceptLGPL ] ; then
  echo Building the docker image requires the download and installation of software
  echo under LGPL license.
  echo If you accept this download and license \(see ./licenses/lgpl-license.txt\) then
  echo you should add the parameter -acceptLGPL to the command line described above.
  echo
  exit 1
fi

# if install file provided on command line
if [[ ! -z "$installFile" ]] && [[ -n $installFile ]]; then
  echo Copying Studio Installer $installFile
  cp $installFile image_template/.
fi

# does the installer file exist
if [ ! -f ./image_template/TIB_business-studio-cloud-bpm-edition_?.?.?_linux*.zip ]; then
  echo You must provide a Linux version of the TIBCO Business Studio - Cloud BPM Edition installer.
  echo The installer\'s name follows the pattern TIB_business-studio-cloud-bpm-edition_?.?.?_linux*.zip
  echo and must be copied to the sub-folder image_template.
  echo
  echo Alternatively, specify the location of the file on the command line.
  usage
  exit 1
fi

# Building Docker Image
docker build -t tibco/sce-studio --build-arg version=5.0 ./image_template
