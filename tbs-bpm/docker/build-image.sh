#!/usr/bin/env bash
echo Building TIBCO BPM Studio docker image tibco/bpm-studio:$$IMAGE_TAG_VERSION$$ for automated CI/CD...
echo

function usage() {
  echo Usage: ${0} -acceptLGPL [installer-file] [hotfix-installer-file] [-h]
  echo Where:
  echo -e '\t -acceptLGPL = confirm acceptance of the LGPL license.'
  echo -e '\t installer-path = full path of the TIBCO Business Studio - BPM Edition installer.'
  echo -e '\t hotfix-installer-path = full path of the TIBCO Business Studio - BPM Edition hotfix installer.'
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
        if [ -z "$installFile" ]; then
          installFile=$1
        elif [ -z "$hotfixInstallFile" ]; then
          hotfixInstallFile=$1
        fi
      fi
  esac
  shift
done

# if licence not accepted
if [ ! $acceptLGPL ] ; then
  echo Building the docker image requires the download and installation of software
  echo under LGPL license.
  echo If you accept this download and license \(see ./licenses/lgpl-license.txt\) then
  echo you should add the parameter -acceptLGPL to the command line.
  echo
  exit 1
fi

# if install file provided on command line
if [[ ! -z "$installFile" ]] && [[ -n $installFile ]]; then
  echo Copying Studio Installer $installFile
  cp $installFile image_template/.
fi

# if hotfix install file provided on command line
if [[ ! -z "$hotfixInstallFile" ]] && [[ -n $hotfixInstallFile ]]; then
  echo Copying Hotfix Installer $hotfixInstallFile
  cp $hotfixInstallFile image_template/.
fi

# does the installer file exist
if [ ! -f ./image_template/TIB_business-studio-bpm-edition_?.?.?_linux*.zip ]; then
  echo You must provide a Linux version of the TIBCO Business Studio - BPM Edition installer.
  echo The installer\'s name follows the pattern TIB_business-studio-bpm-edition_?.?.?_linux*.zip
  echo and must be copied to the sub-folder image_template.
  echo
  echo Alternatively, specify the location of the file on the command line.
  echo
  echo Usage: ${0} -acceptLGPL [installer-file] [hotfix-installer-file] [-h]
  echo Where:
  echo -e '\t -acceptLGPL = confirm acceptance of the LGPL license.'
  echo -e '\t installer-path = full path of the TIBCO Business Studio - BPM Edition installer.'
  echo -e '\t hotfix-installer-path = full path of the TIBCO Business Studio - BPM Edition hotfix installer.'
  echo -e '\t -h display this usage message.'
  echo
  exit 1
fi

# Build Docker Image
docker build -t tibco/bpm-studio:$$IMAGE_TAG_VERSION$$ ./image_template

# Remove temporary install image if it exists
images=$(docker images -q --filter label=maintainer="TIBCO Software Inc" --filter label=image=bpm-studio-installation)

if [ -n "$images" ]; then
    echo Removing temporary install image...	
    docker rmi $images
fi
