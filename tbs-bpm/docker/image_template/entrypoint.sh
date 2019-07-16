#!/usr/bin/env bash

# if the argument "export" is passed on the command-line
if [[ -n "$1" ]] && [[ "$1" == "export" ]]; then
  # if workspace contains metadata folder
  if [[ -d "${WORKSPACE_DIR}/.metadata" ]]; then
    # remove workspace dir content
    find $WORKSPACE_DIR -mindepth 1 -delete
  fi
  
  # run the export ant task
  exec ./ant-runner -f ./build.xml -data $WORKSPACE_DIR -Dworkspace.dir=$WORKSPACE_DIR -Dprojects.dir=$PROJECTS_DIR -Dexports.dir=$EXPORTS_DIR

else
  # start a studio session
  exec ./TIBCOBusinessStudio -data $WORKSPACE_DIR
fi