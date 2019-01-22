
The current EMF version where the original files were extracted is:
	org.eclipse.emf.codegen.ecore_2.7.0.v20120130-0943.jar

During code generation, EMF will use our custom templates, where available. In cases where we have not
supplied a custom template, it will fall back to using the default template distributed within the
org.eclipse.emf.codegen.ecore plugin.

Our custom templates were created by extracting the appropriate template from the given version of
the org.eclipse.emf.codegen.ecore plugin and modifying accordingly.  I am not aware of a technique
for partially overriding the behaviour of a template other than completely replacing it. There are
some hooks into existing templates, these are the "javajetinc" files.

The directory "bdsutil" contains some utility code that needs to be stored with the EMF bundle when
it is build, this needs to be copied over as part of the EMF generation.

	In the bdsutil is a FeatureMap Wrapper interface and class, this will need to be
	activated on the genmodel in order to be picked up and used.  The bdsutil classes
	and interfaces may at a later date be moved into an BDS EMF Common bundle.

The class "TemplateManager" is responsible for dealing with all things related to templates.

In order to work around the bug detailed:
	http://www.eclipse.org/forums/index.php?t=rview&goto=651583

We need to pre-compile our templates into a new project and reference them.  This project is zipped
in the following location: resources/EMF-codegen-templates/bdsTemplates.zip It is version controlled
by placing a unique identifier in the comments section in the ".project" file, this needs to match
TemplateManager.bdsTemplateVersion

In order to regenerate the bdsTemplates project.
	Using the standard templates, generate the source from a GenModel. i.e. 
	If the project has already been generated, delete its Manifest.MF and the contents
	of the src folder, but NOT including the bdsutil folder.
	Set the
		template directory to '/<name of .bds project>/templates' and right-click, 
		generate model code on the root of the genmodel. Also, comment out the 
		template manager code that adds .bdsTemplates to the target project's classpath.
		Change the value Activator.forceEmfJetTemplateCopy to true
	This will create a .JetEmitters project in your workspace
	Edit the .project file and add the next unique ID to the comment section
	Rename all of the Java classes from the underscore version to one without
		i.e. Class_.java => Class.java
	Then rename the project to ".bdsTemplates" and recompile
	Then disable the Java Builder on the project
	Make sure that the .classpath file does not contain any of the _EMF_ variables
	Now Right click on the project and Export->Archive File
	Use the generated Zip to override the one in the BDS project
	(Make sure that the unique ID in TemplateManager.bdsTemplateVersion is updated

