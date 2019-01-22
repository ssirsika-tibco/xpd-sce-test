STEPS TO CREATE A NEW IMPORT WIZARD TEMPLATE FOR THE IMPORT WIZARD GENERATOR:

1.	Create a new plugin (doesn't need the Activator class).
2. 	Add provider (in manifest).  This is required to create a placeholder in the 
	manifest file that the plugin generator will fill from the data provided by
	the user.
3.	In the dependencies add "com.tibco.xpd.importexport".
4.	In the extension create an extension for extension point "org.eclipse.ui.importWizards".
5.	Create a wizard in the extension.
6. 	In the name for the wizard add "%wizard.name" (without the double-quotes).
7.	Add description to the wizard and add "%wizard.description" as the description.
8.	For the icon add "%wizard.icon".
9.  For the category add "%wizard.category".
10.	For the class, add "com.tibco.xpd.importexport.imports.custom.CustomImportWizard.java". 
11.	Finally export the plugin as a deployable plugin.  The created Jar file can then be used
	as the template by the import wizard generator.
	
The generator will use this Jar file as a template to create custom plugins
based on the information provided by the user in the generator wizard.  The generator will
update the manifest.mf file in the jar to update the plug-in information.  It will also create
a "plugin.properties" file that will be populated by info provided in the wizard, such as the 
wizard name, description, xslt to use for transformation, icon location and also the file 
extension filter (for the source files to select in the wizard), etc.  XSLT and the icon image file
will also be inserted into the jar.

	
STEPS TO CREATE A NEW EXPORT WIZARD TEMPLATE FOR THE EXPORT WIZARD GENERATOR:	

Follow the steps as above but extend the extension point "org.eclipse.ui.exportWizards" and use 
the class "com.tibco.xpd.importexport.exports.custom.customExportWizard.java".