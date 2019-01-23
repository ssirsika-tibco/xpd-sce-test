UAT Project:  UC8_07
-------------------------------------------------------------------
This UAT is used to test the provided WSDL as per 
	http://wiki.tibco.com/StudioWiki/WsdlXsdBomTests 

The WSDL are provided in the zips from the wiki page 
	http://wiki.tibco.com/BIWiki/WSDL_Coverage_Across_Active_Matrix

This project follows the 
1. WSDL embeds Schema1, Schema1 imports Schema2, Schema1/Schema2 define the type  - 07.zip which is a supported construct.

_________________________________________________________________

Project construct:
_________________________________________________________________
1. The WSDL file has multiple operations - each operation is matched to 
	a provider process
2. There is just one provider package containing all the provider processes
3. There are multiple consumer packages - each process contains an invoke for 
	an operation in port type of the WSDL.
4. There are no user defined BOMs used in this project.

The problems with this UAT project
-------------------------------------------------------------------
* DAA generation - Not tested 
___________________________________________________________________


WSDL structure description
-------------------------------------------------------------------

___________________________________________________________________	


STATUS - UNFINISHED
