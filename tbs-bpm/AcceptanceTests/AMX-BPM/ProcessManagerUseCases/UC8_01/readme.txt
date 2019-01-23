UAT Project:  UC8_01
-------------------------------------------------------------------
This UAT is used to test the provided WSDL as per 
	http://wiki.tibco.com/StudioWiki/WsdlXsdBomTests 

The WSDL are provided in the zips from the wiki page 
	http://wiki.tibco.com/BIWiki/WSDL_Coverage_Across_Active_Matrix

This project follows the 
1. Schema(XSD) defines the type - 01.zip which is a supported construct.

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
* DAA generation problem - 
___________________________________________________________________


WSDL structure description
-------------------------------------------------------------------
* WSDL contains operations with one port type and multiple operations.
* Each operation has just one input and output but with different permutations
	of simple and complex types. 
___________________________________________________________________	