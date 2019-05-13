#!/usr/bin/env bash
echo ./ant-runner -f ./build.xml -data $WORKSPACE_DIR -Dworkspace.dir=$WORKSPACE_DIR -Dproject.dir=$PROJECT_DIR -Dexport.dir=$EXPORT_DIR
exec ./ant-runner -f ./build.xml -data $WORKSPACE_DIR -Dworkspace.dir=$WORKSPACE_DIR -Dproject.dir=$PROJECT_DIR -Dexport.dir=$EXPORT_DIR
