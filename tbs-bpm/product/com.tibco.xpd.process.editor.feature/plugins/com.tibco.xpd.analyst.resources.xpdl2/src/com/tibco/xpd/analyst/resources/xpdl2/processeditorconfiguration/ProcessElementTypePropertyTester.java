/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration;

import java.util.Set;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Simple property tester for whether any of the element types above have been
 * excluded via contributions to the processEditorConfiguration ext point.
 * <p>
 * <li>Property to test: IsProcessElementExcluded</li>
 * <li>Arguments: Enumeration literal name from {@link ProcessEditorElementType}
 * </li>
 * <li>Value: "true" or "false"
 * </p>
 * 
 * 
 * 
 * @author aallway
 * @since 12 Dec 2011
 */
public class ProcessElementTypePropertyTester extends PropertyTester {

    public static final String IS_PROCESS_ELEMENT_ENABLED =
            "IsProcessElementExcluded"; //$NON-NLS-1$

    /**
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     * 
     * @param receiver
     * @param property
     * @param args
     * @param expectedValue
     * @return
     */
    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        EObject procOrIfcOrPkg =
                getProcessOrInterfacePackageFromReceiver(receiver);

        if (procOrIfcOrPkg != null && args != null && args.length == 1) {

            Boolean booleanExpectedValue =
                    getBooleanExpectedValue(expectedValue);

            Set<ProcessEditorElementType> excludedTypes =
                    ProcessEditorConfigurationUtil
                            .getExcludedElementTypes(procOrIfcOrPkg);

            boolean isExcluded = false;

            for (ProcessEditorElementType excludedType : excludedTypes) {
                if (excludedType.name().equals(args[0])) {
                    isExcluded = true;
                    break;
                }
            }

            return isExcluded == booleanExpectedValue;
        }

        return false;
    }

    /**
     * @param receiver
     * @return the host process or package or process interface for the given
     *         test target object.
     */
    private EObject getProcessOrInterfacePackageFromReceiver(Object receiver) {
        EObject retObject = null;

        if (receiver instanceof AbstractAssetGroup) {
            receiver = ((AbstractAssetGroup) receiver).getParent();
        }

        if (receiver instanceof EObject) {
            EObject eObject = (EObject) receiver;

            retObject = Xpdl2ModelUtil.getProcess(eObject);

            if (retObject == null) {
                retObject =
                        Xpdl2ModelUtil.getAncestor(eObject,
                                ProcessInterface.class);

                if (retObject == null) {
                    retObject =
                            Xpdl2ModelUtil.getAncestor(eObject, Package.class);
                }
            }

        } else if (receiver instanceof IResource) {
            WorkingCopy wc =
                    WorkingCopyUtil.getWorkingCopy((IResource) receiver);

            if (wc != null && wc.getRootElement() instanceof Package) {
                retObject = wc.getRootElement();
            }

        }
        return retObject;
    }

    /**
     * Normally eclipse coerces the value "true" / "false" to boolean but that
     * won't happen if you use mixed case, so will do that
     * 
     * @param expectedValue
     * @return
     */
    protected Boolean getBooleanExpectedValue(Object expectedValue) {
        Boolean booleanExpectedValue;

        if (expectedValue instanceof Boolean) {
            booleanExpectedValue = (Boolean) expectedValue;

        } else if (expectedValue instanceof String
                && "TRUE".equalsIgnoreCase((String) expectedValue)) { //$NON-NLS-1$
            booleanExpectedValue = true;

        } else if (expectedValue instanceof String
                && "FALSE".equalsIgnoreCase((String) expectedValue)) { //$NON-NLS-1$
            booleanExpectedValue = false;

        } else {
            booleanExpectedValue = false;
        }
        return booleanExpectedValue;
    }

}
