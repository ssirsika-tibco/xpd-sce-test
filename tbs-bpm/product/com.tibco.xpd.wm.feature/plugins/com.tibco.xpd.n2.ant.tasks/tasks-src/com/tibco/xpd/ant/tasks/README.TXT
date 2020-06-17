

AFTER changing classes here then YOU MUST rebuild com.tibco.xpd.n2.ant.tasks/lib/bpm-antTaskDefinitions.jar  

  - Otherwise you changes will not be apparent in product.
  
  - Double-click com.tibco.xpd.n2.ant.tasks/createAntTasksJar.jardesc
    -- Press [Finish] leaving all options default.
    -- You should see that com.tibco.xpd.n2.ant.tasks/lib/bpm-antTaskDefinitions.jar has been updated.
    
  - Commit all changes in the project INCLUDING com.tibco.xpd.n2.ant.tasks/lib/bpm-antTaskDefinitions.jar
  
  
  
  - DEBUG: If you are trying to DEBUG changes then...
    -- Do the above FIRST.
    -- Then rebuild, download and use a new TARGET-PLATFORM
    
    -- If you do not do this then when you try to use the debug configuration
         (which uses 'Run an application: org.eclipse.ant.core.antRunner')
       then the code stepping will not match the source code here because
       the debug always uses the target platform com.tibco.xpd.n2.ant.tasks plugin.
       
       Rebuilding and using the target platform means that the source and binaries match up.   
         
      