#!/usr/bin/env bash

#
# Handle : entrypoint.sh generate-rascs
#
if [[ -n "$1" ]] && [[ "$1" == "generate-rascs" ]]; then
  # if workspace contains metadata folder
  if [[ -d "${WORKSPACE_DIR}/.metadata" ]]; then
    # remove workspace dir content
    find $WORKSPACE_DIR -mindepth 1 -delete
  fi
  
  # run the generate-rascs ant task
  exec ./ant-runner -f ./build.xml -data $WORKSPACE_DIR -Dworkspace.dir=$WORKSPACE_DIR -Dprojects.dir=$PROJECTS_DIR -Drascs.dir=$RASCS_DIR

#
# Handle : entrypoint.sh deploy-rascs <BPME host URL protocol and domain> <username>:<password>
#
elif [[ -n "$1" ]] && [[ "$1" == "deploy-rascs" ]]; then
  # Handover to the example basic authentication deploy script. 
  # For installations where certification is required, this can
  # be modified and/or moved to the preferred CI/CD pipeline tooling.
  ./deployrascs.sh $RASCS_DIR "$2" "$3"
  
  exit $?;
  
else
  # By default, start a studio session
  exec ./TIBCOBusinessStudio -data $WORKSPACE_DIR
fi
