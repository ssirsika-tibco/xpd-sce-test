
To build target platform locally...

** COMMIT any changes to the base tbs-bpm\TargetPlatform-tbs-bpm\requestedReleaseUnits.xml
   -- As this feature will use the release units from this file on SVN **NOT** local build.
   
- Open a shell in this folder
- Run this (or similar depending on where you want TP to be built and what Eclipse SDK you want to copy into in the target platform)

  ant -f tp-build.xml -D"autobuild.dir.eclipse-default"="C:\builder\eclipse\4.19.0\eclipse" -D"env.WORKSPACE"="d:\src\temp" -D"use.local.bpm.tp"="true" -D"zipFilePrefix"="bpm-studio-junit-" -D"env.BUILD_NUMBER"="sce-local-build" 

