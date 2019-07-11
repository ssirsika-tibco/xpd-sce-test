docker build -t tibco/sce-studio .\image_template

# run ANT export on projects within c:\eclipse\oxygen\workspaces\runtime-SCE - output to c:\scratch\temp\eclipse\export
docker run --rm -v //c/eclipse/oxygen/workspaces/runtime-SCE:/projects -v //c/scratch/temp/eclipse/export:/exports tibco/sce-studio export

# start SCE studio within docker image - open workspace c:\eclipse\oxygen\workspaces\runtime-SCE and exports folder mapped
docker run --rm --name studio -e DISPLAY=10.100.83.72:0.0 -v//c/eclipse/oxygen/workspaces/runtime-SCE:/workspace -v //c/scratch/temp/eclipse/export:/exports tibco/sce-studio

# start SCE docker container in bash shell - with workspace and exports folders mapped
docker run -it --rm --name studio -v//c/eclipse/oxygen/workspaces/runtime-SCE:/workspace //c/scratch/temp/eclipse/export:/exports --entrypoint bash tibco/sce-studio

