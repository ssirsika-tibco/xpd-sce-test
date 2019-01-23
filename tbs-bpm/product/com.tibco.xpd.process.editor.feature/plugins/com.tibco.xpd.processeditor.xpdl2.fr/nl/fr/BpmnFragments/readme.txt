Update the release version file once you have updated the format version each time.

The default fragment is identified with the following id "_default_initial_display_process_id". 
The changes need to propagate to the image file name, the reffering pool name and the process name.

Basically if a change occurs in the zip file and we add another system fragment then we increment the
version in the ProcessFragmentContributor class file and also in the releaseVersion.properties file (within the zip).

