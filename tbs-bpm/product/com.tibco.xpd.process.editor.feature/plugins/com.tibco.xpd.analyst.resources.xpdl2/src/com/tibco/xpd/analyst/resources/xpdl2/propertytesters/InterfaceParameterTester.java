/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.navigator.CommonNavigator;

import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.FormalParameter;

/**
 * @author rsomayaj
 * 
 * 
 */
public class InterfaceParameterTester extends PropertyTester {

    public InterfaceParameterTester() {
    }

    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue3) {
        if (org.eclipse.ui.PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage() != null
                && org.eclipse.ui.PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage()
                        .getActivePart() instanceof CommonNavigator
                && receiver instanceof FormalParameter) {
            CommonNavigator navigator =
                    (CommonNavigator) org.eclipse.ui.PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow().getActivePage()
                            .getActivePart();
            TreeItem[] selectionList =
                    navigator.getCommonViewer().getTree().getSelection();
            for (TreeItem item : selectionList)
                if (item.getParentItem() != null
                        && item.getParentItem().getParentItem() != null
                        && item.getParentItem().getParentItem().getData() instanceof com.tibco.xpd.xpdl2.Process
                        && ((EObject) item.getData()).eContainer() instanceof ProcessInterface)
                    return false;

        }
        return true;
    }
}
