#!/usr/bin/env bash

# if the argument "generate-rascs" is passed on the command-line
if [[ -n "$1" ]] && [[ "$1" == "generate-rascs" ]]; then
  # if workspace contains metadata folder
  if [[ -d "${WORKSPACE_DIR}/.metadata" ]]; then
    # remove workspace dir content
    find $WORKSPACE_DIR -mindepth 1 -delete
  fi
  
  # run the generate-rascs ant task
  exec ./ant-runner -f ./build.xml -data $WORKSPACE_DIR -Dworkspace.dir=$WORKSPACE_DIR -Dprojects.dir=$PROJECTS_DIR -Drascs.dir=$RASCS_DIR

else
  # start a studio session
  exec ./TIBCOBusinessStudio -data $WORKSPACE_DIR
fi