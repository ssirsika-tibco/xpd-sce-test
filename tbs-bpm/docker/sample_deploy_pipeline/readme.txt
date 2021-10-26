==================================================================================
Product Name: TIBCO Business Studio™ - BPM Edition $$IMAGE_TAG_VERSION$$
 	
Jenkins pipeline job template configurations

@since Nov 2021
==================================================================================

<TIBCO Studio Home>/docker_cicd/sample_deploy_pipeline folder contains template 
files to create CI/CD Jenkins a pipeline job for that can build and deploy BPM 
applications. The 'deploy-pipeline-jenkins-job-sample.xml' file contains a job 
template with pipeline configurations that can be imported into Jenkins. The 
significant variables for the pipeline are parameterized in this job.

In short, the Jenkins pipeline job performs the following operations...
 --- SSH (Secure Shell) to Docker host and checkout the source BPM Studio projects
     from the configured SVN location
 --- Generate the deployment-artifacts (RASC) files using the automated CI/CD 
     docker image (tibco/bpm-studio:$$IMAGE_TAG_VERSION$$).
 --- Deploy the deployment-artifacts to BPM Enterprise Edition runtime

Prerequisites
----------------------------------------------------------------------------------
 --- The automated CI/CD docker image (tibco/bpm-studio) must have been built on 
     the Docker host (see ../readme.txt for more information).
 --- Jenkins must be able to connect to Docker Host machine via ssh (without 
     passing password).
 --- The user configured by the 'SRC_REPO_USER' and 'SRC_REPO_PWD' parameters 
     should have access to source project's SVN location configured using 
	 the 'SRC_REPO' parameter.
 --- BPM Enterprise Edition runtime should be running with administrator credentials 
     defined by the 'BPME_ADMIN_USER' and 'BPME_ADMIN_PWD' job parameters.


Description
----------------------------------------------------------------------------------

Automated CI/CD process has three main building blocks:

 ____________________	     ____________________________       __________________
 |	                |  SSH   |  	                    | HTTP  |                |
 | Jenkins Pipeline |------->|       Docker Host        |------>| BPM Enterprise |
 |                  |        |                          |       |    Runtime     |		
 |                  |        |  tibco/bpm-studio image  |       |                |		
 |                  |        |   Source BPM projects    |       |                |		
 |                  |        |  Deployment artifacts    |       |                |		
 |__________________|        |__________________________|       |________________|		


 --- Jenkins Pipeline : Import the deploy-pipeline-jenkins-job-sample.xml Jenkins
                        job into your Jenkins installation. The job will SSH
						onto the docker host to create source and target 
						folders, check-out the source BPM projects, build the
						deployment artifacts and then deploy them into the 
						BPM Enterprise Runtime.
						
 --- Docker host      : Build the tibco/bpm-studio docker image on your docker
                        host (see ../readme.txt for more information). This is 
						used by the build pipeline to generate and deploy the 
						deployment artifacts.
						
 --- BPM Enterprise
     Runtime          : The target BPM Enterprise Runtime installation.

Following stages are executed in the Jenkins Pipeline :
- Stage 1) Connect to the Docker host machine using SSH and create a source 
           folder and use the indicated SVN to check out the BPM Studio projects 
           into this folder. The SVN location provided should contain the
		   complete set of BPM Studio projects for your application.

- Stage 2) Use the tibco/bpm-studio image (which you must build on the docker host
           as a prerequisite) 'generate-rascs' command to generate the deployment
		   artifacts for the source BPM Studio projects.

- Stage 3) Use the tibco/bpm-studio image 'deploy-rascs' command to send the 
           generated deployment artifacts to the indicated BPM Enterprise
		   Runtime system. 
		   
More information regarding commands provided by the tibco/bpm-studio docker image
can be found in the ../readme.txt file.

-----------------------------------------------------------------------------------

For convenience this folder also contains the Jenkins pipeline file 'Jenkinsfile'
that is the pipeline script used within the Jenkins job described here.
This can be modified and used within your own build pipeline.


===================================================================================
