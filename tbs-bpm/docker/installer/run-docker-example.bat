docker build -t tibco/sce-studio:5.0.0 .

docker run --rm -v //c/example/projects:/project -v //c/example/export:/export tibco/sce-studio:5.0.0
