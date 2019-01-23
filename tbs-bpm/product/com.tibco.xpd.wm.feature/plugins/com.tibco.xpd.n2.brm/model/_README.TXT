
How to update the BRM ecore related files from latest supplied by BRM.
19/07/2012
=========================================================================

- Import the com.tibco.n2.common.wsdls plugin from the latest target platform (with the new schemas in)...
  	- Import->Plugins&Fragments... 
  		Select @ The activate target platform... [Next]-> 
  		Filter for com.tibco.n2.commonm.wsdls and add it to right hand list
  		[Finish].
  	
	- Go to Windows explorer for imported <workspace>\com.tibco.n2.common.wsdls\wsdls folder.
		- Select each .XSD that is already in com.tibco.xpd.n2.brm\model and copy it.

	- Still in windows explorer, go to the <workspace>\com.tibco.xpd.n2.brm\model folder and paste the new XSD's in.

	- Go back to com.tibco.xpd.n2.brm\model in Eclipse project explorer and refresh.

- Reload ECORE models.
	- The com.tibco.xpd.n2.brm\model\brmN2.genmodel contains imports for all the necessary ecores and schemas.
	- Right-click and select "Reload..."
	  	- Select "XML Schema" in first page, [Next]->
	  	
	  	- A WARNING dialog will be displayed this can be safely IGNORED (we do not need that scriptDescriptors.xsd schema)...
	  		Problems were detected while validating and converting the XML schemas...
				Warning: XSD: The location of scriptdescriptor.xsd cannot be resolved.
				
		- Press the [Load] button. [Next]->
		
		- [Finish]
		
- Regenerate the model code...
	- Open the brmN2.genmodel editor, right click top level element and select "Generate Model Code"
	
	
That's it.
----------------------------
		
		
				
	  		 
	  

	