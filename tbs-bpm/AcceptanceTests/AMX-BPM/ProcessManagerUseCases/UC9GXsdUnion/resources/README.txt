Notes for UC9GXsdUnion
----------------------

Prerequisites for running the 3rd Party Web Service Process:

1. An instance of Tomcat 6 should be running on the same IP address as the AMX-BPM runtime
2. Copy the following .war file to the webapps folder in Tomcat

ClotheshopUnion.war

The war files for the web services required in this UAT are located in the resources folder. 
SOAP address binding in the WSDL currently uses localhost, hence the requirement for the 
web service to be deployed to the same machine as the AMX-BPM runtime.

3. Deploy UAT as normal.

