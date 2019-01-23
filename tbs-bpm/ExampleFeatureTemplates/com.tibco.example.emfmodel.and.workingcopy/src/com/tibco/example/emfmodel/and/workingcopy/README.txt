EXAMPLE EMF MODEL AND WORKING COPY FEATURE
===================================================================================================
- An Ecore Model generated from an XSD Schema
- Code generated from Ecore Model
- Working Copy Contribution for Model Serialisation and Manipulation.

TO CREATE THE INITIAL MODEL AND WORKING:
===================================================================================================
- Create appropriate XSD
- Right CLick -> New -> Ecore generator model. 
   -- Generate ecore model from XSD (with a single top-level-element for root of model).
   
   -- IF you want to have a single plugin for your model (with both MODEL code and 
   	  EDIT code (the code required for for using editing domain Commands) then
   	  open the .genmodel, go to Properties view for first element in tree and 
   	  CHANGE the "Edit Directory" and "Edit Plugin Id" to remove the ".edit" suffix.
   	  Edit code will be generated into the model code plugin.
   	  
   	  This is fairly normal practice for smaller design time only models.
   	  
   -- Set the "Copyright" property also.
   
   -- Set the File Extension 
      -- This defaults to a lowercase representation of the last camel-case word in XSD namespace. 
      -- In the properties for the "Model" element (first child item of top item in tree)
         set "File Extension" to the required model file extension (this controls the 
         extension_parser plugin.xml contribution. 
      -- The file extension should also agree with the WorkingCopyFactory implementation.
   
   -- Generate Model and Edit code from the generated genmodel file.
   
   -- The <YourModel>ResourceImpl needs to be amended to setTrackingModification(true) in it's constructor
      --- See ModelResourceImpl in this example for details.
      
   -- Create your WorkingCopy and WorkingCopyFactory and contribute them to plugin.xml
      -- See commentary in ModelWorkingCopy, ModelWorkingCopyFactory andp plugin.xml in this example
   
   -- In this example plugin AS IS, you can run TestExampleModel JUnit test to see how a working example functions. 
   
THINGS TO WATCH OUT FOR.
=====================================================================================================
- MODEL SAVED WITH "DocumentRoot" as named root elemetn (rather than root elemnt from XSD file). 
  -- Ecore model creation defaults to a lowercase representation of the last camel-case word in XSD namespace.  
  -- If you do change (as described above) the FileExtension property you will have ISSUES WITH SERIALISING model from working copy 
  	 (where root element is called "DocumentRoot"
  -- See initial instructions above for how to set the fileExtension and the update the ecore model using Right-Click->Reload... 
     
   