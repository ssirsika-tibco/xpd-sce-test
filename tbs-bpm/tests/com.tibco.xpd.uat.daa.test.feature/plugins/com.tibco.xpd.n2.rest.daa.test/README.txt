To run the UAT DAA comparison junit tests you need to do the following:

1. Edit the <<plugin_id>>.xml file that contains the list of projects used for DAA comparison
		 to point to the UAT projects location in your local sandbox. 
		 (They will be pointing to jenkins location).
2. Set up the run configuration -> follow the instructions on the confluence page ->
		http://confluence.tibco.com/pages/viewpage.action?pageId=48107100
		
3. How to create a new UAT project
		http://confluence.tibco.com/display/BPM/UAT+Test+Project+Creation
		
4. Sometimes the tests pass locally but fail on jenkins. This might need updating (clean) workspaces in jenkins. It can be done as follows:
	You can clear the Jenkins workspace through the Jenkins UI for the project you've committed the changed code to. 
	For example, if it's in N2:

	http://studiobuild/job/build-tbs-bpm-n2/workspace/

	When you're on the workspace for the project you'll see a "Wipe Out Current Workspace" link. 
	On the next build it will do a clean checkout from SVN.						