/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.filters;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectInclusionFilter;

/**
 * Filter that will include only objects from the list of provided EClasses, it
 * will also exclude all categories that do not contain that objects. This only
 * applies to the objects from the given model. This extends
 * <code>{@link EObjectInclusionFilter}</code>.
 * 
 * @see EObjectInclusionFilter
 * 
 * @author wzurek
 */
public class EObjAssetGroupFilter extends
        EObjectInclusionFilter {

    /**
     * Constructor.
     * 
     * @param inclusions
     */
    public EObjAssetGroupFilter(Set<EClass> inclusions) {
        super(inclusions);
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean ret = false;

        // If the element is an asset group then get the reference EClass from
        // it
        if (element instanceof AbstractAssetGroup) {
            // Don't include empty lists headers.
            List children = ((AbstractAssetGroup) element).getChildren();
            if (children != null && children.size() > 0) {
                boolean validChild = false;
                for (Iterator iter = children.iterator(); iter.hasNext();) {
                    Object obj = (Object) iter.next();
                    
                    if (obj instanceof EObject) {
                        if (extraValidation((EObject)obj)) {
                            // At least one child is valid.
                            validChild = true;
                        }
                    }
                }
                
                if (validChild) {
                    ret = validEClass(((AbstractAssetGroup) element).getReferenceType());
                }
            }
        } else {
            ret = super.select(viewer, parentElement, element);
        }

        return ret;
    }
}
