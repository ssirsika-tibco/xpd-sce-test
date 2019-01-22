/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.xpdl2.impl;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl.BasicFeatureMapEntry;

import com.tibco.xpd.xpdl2.OtherElementsContainer;

/**
 * Container for all operations in XPDL2 model.
 * 
 * @author wzurek
 */
public class Xpdl2Operations {

    /**
     * Operation defined in OtherElementsContainer.
     * 
     * @see OtherElementsContainer#getOtherElement(String)
     * 
     * @param self
     *            the instance of OtherElementsContainer
     * @param elementName
     * @return
     */
    public static EObject getOtherElement(OtherElementsContainer self,
            String elementName) {
        EList eList = self.getOtherElements();

        if (elementName != null) {
            if (!eList.isEmpty()) {
                for (Iterator iter = eList.iterator(); iter.hasNext();) {
                    Object obj = (Object) iter.next();

                    if (obj instanceof BasicFeatureMapEntry) {
                        BasicFeatureMapEntry entry = (BasicFeatureMapEntry) obj;
                        String entryName = entry.getEStructuralFeature()
                                .getName();

                        if (elementName.equals(entryName)) {
                            Object eobj = self.eGet(entry
                                    .getEStructuralFeature());

                            if (eobj instanceof EObject) {
                                return (EObject) eobj;
                            } else if (eobj instanceof EList) {
                                if (((EList) eobj).get(0) instanceof EObject) {
                                    return (EObject) ((EList) eobj).get(0);
                                }
                            }
                        }
                    }

                }
            }
        }
        return null;
    }

}
