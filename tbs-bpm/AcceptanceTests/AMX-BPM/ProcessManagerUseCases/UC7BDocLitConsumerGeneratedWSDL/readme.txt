UAT Project:  UC7BDocLit
------------------------------------------------------------
This UAT is used to test the generation of WSDLs of Document-
Literal types when the "Generate" button on the Web Service 
task is used.

To implement the web service, a provider process is added, and
all mappings done as required.

* This project tests only process simple types and no BOM references.
* All simple process types are tested in this project.

____________________________________________________________


WSDL Document literal 
------------------------------------------------------------
* An element is created for each parameter and all the parts
	are element references.
____________________________________________________________	

Important Note:: WSDL is generated using the Service task (not the service provider process) & the process formal parameter refers to Simple types

PROBLEMS
____________________________________________________________
* Doesn't run on the runtime V6