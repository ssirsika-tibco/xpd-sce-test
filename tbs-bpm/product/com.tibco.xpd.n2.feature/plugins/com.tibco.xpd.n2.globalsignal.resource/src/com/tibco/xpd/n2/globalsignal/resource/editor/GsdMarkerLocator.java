/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.editor;

import java.util.ArrayList;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Utility class with methods that help getting EObject for selection from gsd
 * file markers.
 * 
 * @author sajain
 * @since Apr 20, 2015
 */
public class GsdMarkerLocator {

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

        GlobalSignalDefinitions gsds =
                (GlobalSignalDefinitions) workingCopy.getRootElement();

        try {

            String location = (String) marker.getAttribute(IMarker.LOCATION);

            Resource resource = gsds.eResource();

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
