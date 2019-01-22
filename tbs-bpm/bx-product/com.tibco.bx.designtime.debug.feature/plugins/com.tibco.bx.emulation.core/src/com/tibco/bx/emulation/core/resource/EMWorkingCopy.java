package com.tibco.bx.emulation.core.resource;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;

public class EMWorkingCopy extends AbstractTransactionalWorkingCopy{
	
	public EMWorkingCopy(List<IResource> list)
    {
        super(list);
    }

	protected EObject getModelFromResource(Resource resource)
	  {
	    if ((resource != null) && (resource.getContents() != null))
	      for (EObject localEObject : resource.getContents()) {
	        if (!(localEObject instanceof EmulationData)) continue;
	        return localEObject;
	      }
	    return null;
	  }

	 public EPackage getWorkingCopyEPackage()
	  {
	    return EmulationPackage.eINSTANCE;
	  }
	 
}
