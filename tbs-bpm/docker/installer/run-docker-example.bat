docker build -t tibco/sce-studio:5.0.0 .

docker run --rm -v //c/example/projects:/project -v //c/example/export:/export tibco/sce-studio:5.0.0

set DISPLAY=10.100.83.72:0.0
docker run -it --rm --name eclipse -e DISPLAY=%DISPLAY% -v//c/scratch/temp/eclipse/workspace:/workspace tibco/sce-studio:5.0.0

