Build configuration for Target platform with just the base Eclipse platform and TPCL features required by Studio Core/BPM features.
------------------------------------------------------------------------------------------------------------------------------------

To build target platform locally...

- Open a shell in this folder
- Run this (or similar depending on where you want TP to be built and what Eclipse SDK you want to copy into in the target platform)

  ant -D"autobuild.dir.eclipse-default"="C:\builder\eclipse\4.19.0\eclipse" -D"env.WORKSPACE"="d:\src\temp" -D"env.BUILD_NUMBER"="sce-bpm-base-local-build" clean-install

