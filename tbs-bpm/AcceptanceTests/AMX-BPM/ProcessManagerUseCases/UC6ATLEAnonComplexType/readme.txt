UAT Project:  UC6TLEAnonComplexType
------------------------------------------------------------
This UAT is used to test the AnonComplex.xsd, which uses 
anonymous complex types while supporting top level elements. 
____________________________________________________________


The problems with this UAT project
------------------------------------------------------------
1. BOM to XSD transformation - the XSD generation isn't correct.
2. Usage of "refs" causes problems between WSDL and XSD
____________________________________________________________


WSDL structure description
------------------------------------------------------------
* The WSDL imports the XSD using the <wsdl:import> mechanism.
* The XSD is maintained in a separate file i.e it is not inlined
* To create the output elements for the part, the WSDL creates a 
	copy of the elements in the XSD, rather than using "refs"
	
____________________________________________________________	
  

