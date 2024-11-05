

AFTER changing classes here then YOU MUST rebuild com.tibco.xpd.core.ant.task/lib/coreAntTaskDefinitions.jar  

  - Otherwise you changes will not be apparent in product.
  
  - Double-click com.tibco.xpd.core.ant.task/ant/createAntTasksJar.jardesc
    -- Press [Finish] leaving all options default.
    -- You should see that com.tibco.xpd.core.ant.task/lib/coreAntTaskDefinitions.jar has been updated.
    
  - Commit all changes in the project INCLUDING com.tibco.xpd.core.ant.task/lib/coreAntTaskDefinitions.jar
  
  
  
  - DEBUG: If you are trying to DEBUG changes then...
    -- Do the above FIRST.
    -- THEN rebuild, download and use a new TARGET-PLATFORM
    
       --- If you do not do this then when you try to use the debug configuration
           (which uses 'Run an application: org.eclipse.ant.core.antRunner')
            then the code stepping will not match the source code here because
           the debug always uses the target platform com.tibco.xpd.core.ant.tasks plugin.
       
           Rebuilding and using the target platform means that the source and binaries match up.   
       
    -- THEN setup a new debug/run configuration which has this configuration
       --- "Run an Application" = org.eclipse.ant.core.antRunner
       --- In 'Arguments' tab --> Program Arguments just add this to end of the default args...
       
              -f <location of an ant build.xml file that triggers the Studio ant task you want to run/debug>
              
       --- The build.xml might contain something like...
             
             <?xml version="1.0" encoding="UTF-8"?>
				<project default="importProjects">
 
				    <target name="importProjects" description="Imports projects from the given location into the workspace.">
				        <echo>Importing projects</echo>
						<tbs.importProjects dir="D:\src\temp\ws560_V218" copyProjects="true"/>       
				    </target>
				 
				
				</project>
				            
				         
      