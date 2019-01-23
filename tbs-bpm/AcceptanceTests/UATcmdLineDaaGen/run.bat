rem Studio installation directory, e.g., C:\apps\TIBCO\studio-bpm-3.6
set studioInstallationDir="C:\apps\TIBCO\studio-bpm-3.6"

rem set base directory for script build-daa.xml
set basedir="C:\src\Temp\AcceptanceTests\UATcmdLineDaaGen"
	
rem Go to the folder with studio execs.
cd /D %studioInstallationDir%\studio\3.6\eclipse

rem Invoke build-daa.xml ant script and redirect output to result.txt file.
amx_eclipse_ant.exe -f %basedir%\build-daa.xml  > %basedir%\log.txt