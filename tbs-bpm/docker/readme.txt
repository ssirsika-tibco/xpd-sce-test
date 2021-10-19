================================================================================
Product Name: TIBCO Business Studio™ - BPM Edition $$IMAGE_TAG_VERSION$$
 	
Configuring BPM Studio docker for Automated CI/CD
================================================================================
The automated CI/CD features of BPM Studio provide capabilities such as the
generation of deployment artifacts (RASC files) for a set of BPM 
Studio projects.

This capability is based upon a docker image into which BPM Studio is installed.
This docker image provides entry points that support automated CI/CD tasks.
This image must be built locally before it can be used.


Prerequisites
-------------------------------------------------------------------------------
These features require a working Docker environment. For details of how to 
install and set up Docker on the specific operating system, refer to Docker 
documentation on: https://docs.docker.com.

You will need a zip archive for the current Linux version of the TIBCO Business 
Studio BPM-Edition installation set, for example...
  TIB_business-studio-bpm-edition_$$IMAGE_TAG_VERSION$$_linux_x86_64.zip

This installation set should be added to the folder...
  <TIBCO Studio Home>/docker_cicd/image_template
  
  Note: this is the default location for the install set. The docker image
        build utility will use this by default (version-insensitive). If
		desired this can be located elsewhere and supplied as a parameter
		to the image build utility.


Building the automated CI/CD docker image
-------------------------------------------------------------------------------
The automated CI/CD docker image (tibco/bpm-studio) must first be built. This
is done using the docker image build utility located in this folder. This will
add a docker image called tibco/bpm-studio to your docker environment.

Where the local environment is Linux, use:
  ./build-image.sh [<studio-installer>]

Where the local environment is Microsoft Windows, use:
  build-image.bat [<studio-installer>]

where:
  <studio-installer> is the full path of the Linux TIBCO Business Studio - 
  BPM Edition Installer file. This is optional if you have already copied the 
  installation set archive zip file to the image_template folder.

** IMPORTANT **
  Building the docker image requires the download and installation of software
  under LGPL license. To use this feature you must accept this license by 
  adding the parameter -acceptLGPL to the command line. For LGPL license 
  details (see ./licenses/lgpl-license.txt).
  

Using the automated CI/CD docker image
================================================================================

Generating deployment-artifact (RASC) files
-------------------------------------------------------------------------------
After you have built the tibco/bpm-studio docker image you can use it to
generate deployment artifacts (RASC files) from BPM Studio projects. 

To do this you use the 'generate-rascs' docker entry point and provide volume 
mappings to the docker host environment for the source BPM Studio projects 
and target generated deployment artifacts folders.

** The target generated deployment artifacts folders should be pre-created on the 
   docker host environment with the appropriate permissions *before* the 
   command is executed. Failure to do so can cause docker to create the folder
   automatically with incorrect permissions.

Where the local environment is Linux, use the docker command:
  # Generate artifacts into the local environment folder 
  #    /usr/bpm-app/deployment-artifacts 
  # for the BPM Studio projects located in the folder
  #    /usr/bpm-app/source-projects

  docker run --rm -v /usr/bpm-app/source-projects:/projects 
    -v /usr/bpm-app/deployment-artifacts:/rascs 
	tibco/bpm-studio:$$IMAGE_TAG_VERSION$$ generate-rascs

Where the local environment is Microsoft Windows, use the docker command:
  # Generate artifacts into the local environment folder 
  #    c:\bpm-app\deployment-artifacts 
  # for the BPM Studio projects located in the folder
  #    c:\bpm-app\source-projects

  docker run --rm -v //c/bpm-app/source-projects:/projects 
    -v //c/bpm-app/deployment-artifacts:/rascs 
	tibco/bpm-studio:$$IMAGE_TAG_VERSION$$ generate-rascs

On successful completion, the target deployment artifacts folder will contain 
the deployment artifacts for all projects in the source folder that require deployment.

The target deployment artifacts folder will also contain the following files:
  deploy.manifest  - Lists the generated deployment artifacts in the order that
                     they must be deployed.

  deploy.info      - Contains additional information gathered during the generation
                     of deployment artifacts (in JSON format)...
                     - Lists the shared-resource definitions that are required on
                       any BPM Enterprise Edition runtime that you wish to deploy
                       the artifacts to. Each entry details the name and type
                       of the required shared-resource definition.
  

Deploying deployment-artifacts to BPM Enterprise Edition runtime
-------------------------------------------------------------------------------
On installations using basic authentication you can deploy RASC files
previously generated with the generate-rascs command described above.

To do this you use the 'deploy-rascs' docker entry point and provide volume 
mappings to generated deployment artifacts folders, and specify the 
BPME Edition runtime host URL and username:password as parameters...
 
  docker run -it --rm --name studio -v <generate rascs folder>:/rascs 
    tibco/bpm-studio:$$IMAGE_TAG_VERSION$$ deploy-rascs <host URL> <username>:<password>

For example...
  docker run -it --rm --name studio -v /usr/bpm-app/deployment-artifacts:/rascs 
    tibco/bpm-studio:$$IMAGE_TAG_VERSION$$ deploy-rascs http://mydomain.bpme.com tibco-admin:secret
	
	
In order to support SSL security requirements of your CI/CD pipleline and 
installation, the self-contained deployrascs.sh script included in this CI/CD
feature.

This can be found in the <TIBCO Studio Home>/docker_cicd/image_template folder.

This script uses cURL commands to send deployment requests to the BPME Deployment 
Manager, ensures that the rasc deployment-artifacts are deployed in the correct 
dependency order and then parses the result appropriately to show progress 
and report success or failures appropriately.


You can also use the docker image in the following ways...
-------------------------------------------------------------------------------
Run the docker image and open a command shell for it; with the source project
and deployment artifacts folders mapped. This can be useful to verify the
correct configuration of the mapped folders etc...

  docker run -it --rm --name studio -v /usr/bpm-app/source-projects:/projects 
   -v /usr/bpm-app/deployment-artifacts:/rascs --entrypoint sh 
   tibco/bpm-studio:$$IMAGE_TAG_VERSION$$


Run the BPM Studio UI within the docker image (this will require correct 
configuration display IP address for you system and so on)...

  docker run --rm --name studio -e DISPLAY=10.100.83.72:0.0 
    -v /usr/bpm-workspace:/workspace tibco/bpm-studio:$$IMAGE_TAG_VERSION$$


Troubleshooting
================================================================================

generate-rascs: Permission denied error whilst writing deployment artifact.
-------------------------------------------------------------------------------
  The following error is seen when executing a generate-rascs command...
    [bpm.generateRASC] Deployment Artifact generation failed for project: Processes
    [bpm.generateRASC]   Cause: Exception was thrown while generating Deployment Artifact for project Processes (/rascs/Processes.rasc (Permission denied))
	
  This can  occur when the target deployment artifacts folder is not pre-created in
  the docker host environment prior to running a generate-rascs command. The folder
  on the docker host is created by the docker with incorrect permissions.
  
  Solution: Remove the target deployment artifacts folder on the docker host and
            re-create the folder on the docker host with the correct permissions.


================================================================================

