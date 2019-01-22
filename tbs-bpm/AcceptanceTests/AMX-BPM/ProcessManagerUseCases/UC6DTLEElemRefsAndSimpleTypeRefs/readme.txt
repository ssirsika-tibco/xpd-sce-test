UAT Project:  UC6TLEElemRefsAndSimpleTypeRefs
------------------------------------------------------------
This UAT is used to test the "References.xsd", which uses 
element "refs" while supporting top level elements. 
____________________________________________________________


The problems with this UAT project
------------------------------------------------------------
1. WSDL to BOM transformation - 
	EvaluationException : java.lang.NullPointerException
	com::tibco::xpd::bom::xsdtransform::imports::template::TopLevelFuncs.ext[4837,239] on line 89 'addRootElementType(data,tmpDataType,element.name,isAnonymous,isBaseXSDType,element.id,element.fixed,element.final.toString(),element.block.toString(),element.nillable,element.abstract,element.substitutionGroup,element.default)'
	com::tibco::xpd::bom::xsdtransform::imports::template::Xsd2Bom.ext[161923,153] on line 2328 'createRootElementPrimitiveType(data,topLevelElement,false,isKnownXSDType(topLevelElement.type.localPart),schemaType,model,package)'
	
2. Cannot do Javascript mappings unless the WSDL to BOM transformation is resolved.
____________________________________________________________


WSDL structure description
------------------------------------------------------------
* The WSDL uses a non-inline XSD
* Output element created using refs.
	
____________________________________________________________	
  

