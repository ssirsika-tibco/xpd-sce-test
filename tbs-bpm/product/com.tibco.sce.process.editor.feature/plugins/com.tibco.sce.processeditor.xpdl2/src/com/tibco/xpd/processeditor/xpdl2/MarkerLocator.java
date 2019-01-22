package com.tibco.xpd.processeditor.xpdl2;

import java.util.ArrayList;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Package;

/**
 * Utility class with methods that help getting EObject for selection from file
 * markers.
 * 
 * @deprecated Replaced with MarkerFinder in com.tibco.xpd.resources.ui.
 */
@Deprecated
public class MarkerLocator {

    /**
     * Returns objects referenced by the marker.
     * 
     * @param marker
     * @return
     */
    public static EObject[] getObject(IMarker marker) {
        ArrayList<EObject> list = new ArrayList<EObject>();
        XpdProjectResourceFactory factory =
                XpdResourcesPlugin.getDefault()
                        .getXpdProjectResourceFactory(marker.getResource()
                                .getProject());
        WorkingCopy workingCopy = factory.getWorkingCopy(marker.getResource());
        Package xpdlPackage = (Package) workingCopy.getRootElement();
        try {
            String location = (String) marker.getAttribute(IMarker.LOCATION);
            Resource resource = xpdlPackage.eResource();
            if (resource != null) {
                EObject target = resource.getEObject(location);
                list.add(target);
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }
        EObject[] objects = new EObject[list.size()];
        list.toArray(objects);
        return objects;
    }
}
