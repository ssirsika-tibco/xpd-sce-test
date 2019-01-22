/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider;

/**
 * Project Explorer label provider
 * 
 * @author njpatel
 * 
 */
public class BpmContentLabelProvider extends ProjectExplorerLabelProvider {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element) {

        if (element instanceof INavigatorGroup) {

            return ((INavigatorGroup) element).getText();
        }

        return super.getText(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Image getImage(Object element) {
        Object treeItem = element;

        /*
         * If the element passed in is a TreeItem then get the object from it.
         * This will be the case when the Properties view is acquiring the image
         * for the title
         */
        if (element instanceof ITreeSelection) {
            ITreeSelection selection = (ITreeSelection) element;

            if (selection != null) {
                treeItem = selection.getFirstElement();
            }
        }

        if (treeItem instanceof INavigatorGroup) {
            // Get the navigator group image
            return ((INavigatorGroup) treeItem).getImage();
        }

        return super.getImage(treeItem);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IDescriptionProvider#getDescription(java.lang
     * .Object)
     */
    @Override
    public String getDescription(Object anElement) {
        if (anElement instanceof INavigatorGroup) {
            String text = getText(anElement);

            INavigatorGroup group = (INavigatorGroup) anElement;
            if (group.getParent() instanceof EObject) {
                EObject eObject = (EObject) group.getParent();

                if (WorkingCopyUtil.isReadOnly(eObject)) {

                    text +=
                            " "     + Messages.BpmContentLabelProvider_ReadOnly_label; //$NON-NLS-1$

                }
            }
            return text;

        }

        /*
         * XPD-1140: Distinguishing between Business, Pageflow and Task Library
         * process type name is now done in Xpdl2WortkingCompyImpl .
         */
        return super.getDescription(anElement);
    }
}
