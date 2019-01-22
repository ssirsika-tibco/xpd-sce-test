/*
* ENVIRONMENT:    Java Generic
*
* COPYRIGHT:      (C) 2009 TIBCO Software Inc
*/
package com.tibco.n2.ut.resources.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;


public class UserActivityChainingOfferStateResolution implements IResolution
{

	public void run(IMarker marker) throws ResolutionException
	{
        try
        {
            XpdProjectResourceFactory factory = XpdResourcesPlugin.getDefault().getXpdProjectResourceFactory(
            						marker.getResource().getProject());
            WorkingCopy workingCopy = factory.getWorkingCopy(marker.getResource());
            String location = (String) marker.getAttribute(IMarker.LOCATION);
            Package xpdlPackage = (Package) workingCopy.getRootElement();
            Resource resource = xpdlPackage.eResource();
            if (resource != null)
            {
                EObject target = resource.getEObject(location);
                if (target != null)
                {
                    if (target instanceof Activity)
                    {
                    	setActivityAsOfferToAll((Activity) target);
                    }
                }
            }
        } 
        catch (CoreException e)
        {
            throw new ResolutionException(e);
        }
        
        return;
	}

    /*
     * =====================================================
     * METHOD : setActivityAsOfferToAll
     * =====================================================
     */
    /**
     * Sets a given offer state in an activity to "Offer To All"
     *
     * @param activity	Activity to set the value on
     */
    private void setActivityAsOfferToAll(Activity activity)
    {
    	Object objARP = Xpdl2ModelUtil.getOtherElement(activity,
	  	    		   XpdExtensionPackage.eINSTANCE.getDocumentRoot_ActivityResourcePatterns());

	  	// Make sure a resource pattern is specified
    	if( objARP instanceof ActivityResourcePatterns )
    	{
    		AllocationStrategy allStrategy = ((ActivityResourcePatterns)objARP).getAllocationStrategy();

    		// Create the operation that will set the offer value to true
            EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(activity);
            Command command = SetCommand.create(editingDomain, allStrategy,
            		XpdExtensionPackage.eINSTANCE.getAllocationStrategy_Offer(),
            		AllocationType.OFFER_ALL);
         
            if (command.canExecute())
            {
            	editingDomain.getCommandStack().execute(command);
            }
    	}
    }
}
