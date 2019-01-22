/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.ui;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.services.IServiceLocator;

/**
 * Simple util for accessing current selection from eclipse UI service locators.
 * 
 * @author aallway
 * @since 15 Jan 2013
 */
public class OpenspaceGadgetDevSelectionUtil {

    public static Object getSelectionFirstElement(IServiceLocator serviceLocator) {
        if (serviceLocator != null) {
            ISelectionService selectionService =
                    (ISelectionService) serviceLocator
                            .getService(ISelectionService.class);

            if (selectionService != null) {
                ISelection selection = selectionService.getSelection();

                if (selection instanceof IStructuredSelection) {
                    return ((IStructuredSelection) selection).getFirstElement();
                }
            }
        }
        return null;
    }
}
