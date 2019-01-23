UAT Project:  UC9AChoice
-------------------------------------------------------------------
This UAT tests the usage of the XSD which is inlined in the WSDL - UsingAnonChoice.wsdl

The xsd schema contains elements which have choice, however, the choice has two elements, 
one of which is a complex type. 

_________________________________________________________________

Project construct:
_________________________________________________________________



The problems with this UAT project
-------------------------------------------------------------------
* Problem with how the BOM is created. Compositions don't have the xsd:choice annotation.
___________________________________________________________________


WSDL structure description
-------------------------------------------------------------------
* Multiple xsd schemas inlines in the WSDL  
* UsingAnonChoice.wsdl contains XSD which has a element with choice
   * the choice has two elements - one simple type and another complex type.
___________________________________________________________________	