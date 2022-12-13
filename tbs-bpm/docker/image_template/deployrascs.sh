#!/usr/bin/env bash

# 
# Example script for deployment of all deployment artifacts (.rasc files) 
# created using the generate-rascs docker run command.
#
# Command: 
#  deployrascs.sh <generated rascs folder> <host URL> <username>:<password>
#
#  e.g. deployrascs.sh /build/bpme/rascs http://myorg.bpme.com tibco-admin:secret
#
# The script uses the deploy.manifest file created by the generate-rascs docker
# run command in order to deploy the identified rasc files in the correct 
# dependency order. The deploy.manifest 'Projects' property lists the deployment 
# artifacts in the order that they should be deployed.
#
# The script then uses cURL with plain-test authorization to push each rasc
# file to the BPME Deployment Manager. This can be modified to suite your
# own security certification requirements.
#

DEPLOY_ARTIFACTS_FOLDER=$1
DEPLOY_HOST_URL=$2
DEPLOY_USER_PWD=$3
DEPLOY_MANIFEST=deploy.manifest


# Check if parameters are set.
if [[ -z $DEPLOY_ARTIFACTS_FOLDER ]]; then
  1>&2 echo "The <generated rascs folder> parameter is not specified.";
  exit 1;
fi

if [[ -z $DEPLOY_HOST_URL ]]; then
  1>&2 echo "The <host URL> parameter for runtime services end-point is not specified (expected protocol and domain of URL e.g. 'http://my-bpm-domain').";
  exit 1;
fi

if [[ -z $DEPLOY_USER_PWD ]]; then
  1>&2 echo "The <username>:<password> parameter for runtime services authorization is not specified.";
  exit 1;
fi

# Check we have a manifest of the project rascs
if [[ ! -e $DEPLOY_ARTIFACTS_FOLDER/$DEPLOY_MANIFEST ]]; then
  1>&2 echo "Missing project manifest '$DEPLOY_MANIFEST' in rascs folder ($DEPLOY_ARTIFACTS_FOLDER).";
  exit 2;
fi

# Grab the list of rasc's from the manifest.
DEPLOY_LIST=$(grep "^Projects: " $DEPLOY_ARTIFACTS_FOLDER/$DEPLOY_MANIFEST | cut -c 10- | sed 's/^[ \t]*//;s/[ \t]*$//');
DEPLOY_DISPLAY_LIST=$(echo $DEPLOY_LIST | sed 's/,/, /g');

# Deploy the rasc files listed in the manifest...
echo "Starting deploy of: [$DEPLOY_DISPLAY_LIST]";
echo -e '  from:\t'$DEPLOY_ARTIFACTS_FOLDER;
echo -e '  to:\t'$DEPLOY_HOST_URL;
echo -e '-------------------------------------------------------------------------------';

for RASC in $(echo $DEPLOY_LIST | tr "," " "); do
  printf "Deploying %s..." $RASC;

  if [[ ! -e $DEPLOY_ARTIFACTS_FOLDER/$RASC ]]; then
    1>&2 echo "Deployment artifact '$RASC' does not exist.";
    exit 3;
  fi

  # Post the deploy request to the deployment manager after clearing any previous response.
  rm -f ./.deploy_output;
  
  RESPONSE_CODE=$(curl -X POST \
        -F "appContents=@"$DEPLOY_ARTIFACTS_FOLDER/$RASC";type=application/x-zip-compressed" \
        -H "Content-Type: multipart/form-data" \
        -u "$DEPLOY_USER_PWD" \
		-w "%{response_code}" \
		-o ./.deploy_output \
		-s \
        $DEPLOY_HOST_URL"/bpm/deploy/v1/deployments");
		
  # Deal with errors
  if [[ "$RESPONSE_CODE" -lt 200 ]] || [[ "$RESPONSE_CODE" -ge 300 ]]; then

    if [[ -e ./.deploy_output ]]; then
        ERROR_CODE=$(cat ./.deploy_output | python -c "import sys, json; print json.load(sys.stdin)['errorCode']"  2> /dev/null);
        ERROR_MSG=$(cat ./.deploy_output | python -c "import sys, json; print json.load(sys.stdin)['errorMsg']" 2> /dev/null);
		
	    if [[ "$ERROR_CODE" == "DEM_DUPLICATE_DEPLOYMENT" ]]; then
		    # Duplicate deployment is an error we can ignore (just means the given rasc is already deployed)
			echo " Already deployed at this version";
			
	    elif [[ "$ERROR_CODE" == "DEM_SHARED_RESOURCE_MISSING" ]]; then
		    # Missing shared resource error
			MISSING_RES=$(cat ./.deploy_output | python -c "import sys, json; print json.load(sys.stdin)['contextAttributes'][0]['value']" 2> /dev/null);
			MISSING_RES_TYPE=$(cat ./.deploy_output | python -c "import sys, json; print json.load(sys.stdin)['contextAttributes'][1]['value']" 2> /dev/null);
			echo " At least one shared resource does not exist on target system: $MISSING_RES ($MISSING_RES_TYPE)";
			exit 4

	    elif [[ "$ERROR_CODE" == "DEM_DEPLOYMENT_INVALID_VERSION" ]]; then
		    # Missing shared resource error
			echo " The deployment artifact is for a later version than the target system supports";
			exit 4

		else
			# Fatal error - stop deploying
			if [[ "$ERROR_MSG" != "" ]]; then
				1>&2 echo " Deploy request error - ($RESPONSE_CODE) $ERROR_MSG";
			else 
				1>&2 echo " Deploy request error - $RESPONSE_CODE";
			fi
		
			exit 4;
		fi
  
	else
		# Fatal error - stop deploying
		1>&2 echo " Deploy request error - $RESPONSE_CODE";
		exit 4;
	fi

  else 
	echo  " Done";
  fi

done

echo '-------------------------------------------------------------------------------';
echo 'Deployment successful';

exit 0;
