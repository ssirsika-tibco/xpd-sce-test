IMPORTANT note when performing code regeneration on Eclipse Kepler dev env'.
****************************************************************************************************

When you regenerate, the new EMF ecore code generator will create a CYCLIC DEPENDENCY
from...
  -  com.tibco.xpd.xpdl2 back to ITSELF
  -  com.tibco.xpd.xpdl2.edit back to ITSELF

AFTER GENERATION you must REMOVE cyclic dependency on itself from com.tibco.xpd.xpdl2/MANIFEST.MF and com.tibco.xpd.xpdl2/MANIFEST.MF    
I suspect that this is due to the fact that XpdExtension.ecore import Xpdl2.ecore 
and hence auto-adds the dependency for xpdl2.ecore's bundles.
But it doesn't take into account that they are both in same bundle.

  