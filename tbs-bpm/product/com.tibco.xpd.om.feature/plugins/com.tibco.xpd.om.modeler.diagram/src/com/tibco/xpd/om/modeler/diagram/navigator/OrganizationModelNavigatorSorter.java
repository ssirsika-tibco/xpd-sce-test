package com.tibco.xpd.om.modeler.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;

/**
 * @generated
 */
public class OrganizationModelNavigatorSorter extends ViewerSorter {

    /**
     * @generated
     */
    private static final int GROUP_CATEGORY = 5004;

    /**
     * @generated
     */
    public int category(Object element) {
        if (element instanceof OrganizationModelNavigatorItem) {
            OrganizationModelNavigatorItem item = (OrganizationModelNavigatorItem) element;
            return OrganizationModelVisualIDRegistry
                    .getVisualID(item.getView());
        }
        return GROUP_CATEGORY;
    }

}
