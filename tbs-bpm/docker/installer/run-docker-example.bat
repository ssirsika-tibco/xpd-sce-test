docker build -t tibco/sce-studio:5.0.0 .

docker run --rm -v //c/eclipse/oxygen/workspaces/runtime-SCE:/project -v //c/scratch/temp/eclipse/export:/export tibco/sce-studio:5.0.0 export

set DISPLAY=10.100.83.72:0.0
docker run -it --rm --name eclipse -e DISPLAY=%DISPLAY% -v//c/eclipse/oxygen/workspaces/SCE-docker:/workspace -v //c/eclipse/oxygen/workspaces/runtime-SCE:/project tibco/sce-studio:5.0.0

