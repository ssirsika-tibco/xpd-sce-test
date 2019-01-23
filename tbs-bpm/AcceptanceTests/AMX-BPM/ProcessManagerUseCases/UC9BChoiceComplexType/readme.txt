UAT Project:  UC9BChoice
-------------------------------------------------------------------
This UAT tests the usage of the XSD which is inlined in the WSDL - UsingComplexChoice.wsdl

The xsd schema contains elements which have choice, however, the choice has two elements, 
both of them are complex type elements.

_________________________________________________________________

Project construct:
_________________________________________________________________


The problems with this UAT project
-------------------------------------------------------------------
* Problem with how the BOM is created. Compositions don't have the xsd:choice annotation.
* Cannot create DAA from this project.
___________________________________________________________________


WSDL structure description
-------------------------------------------------------------------
* Multiple XSDs inline in the WSDL  
* UsingComplexChoice.wsdl contains XSD which has a element with choice
   * the choice has two elements - both of them are complex types.
___________________________________________________________________	