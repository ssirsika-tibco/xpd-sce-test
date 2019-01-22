UAT Project:  UC6TLENameClases
-------------------------------------------------------------------
This UAT is used to test the "MatchElemAndComplex.xsd", the XSD 
contains an element and a complex type with the same name
___________________________________________________________________


The problems with this UAT project
-------------------------------------------------------------------
* Cannot transform WSDL to BOM because of the validation problem that 
	XML Schema contains duplicate Attribute/Element names within the same Complex Type.
	Rob Green : This restriction will not be removed, and so this construct will not be supported.
___________________________________________________________________


WSDL structure description
-------------------------------------------------------------------
* The WSDL uses a non-inline XSD
* The XSD is only imported using an xsd:import and does not have any wsdl:import

___________________________________________________________________	
  

