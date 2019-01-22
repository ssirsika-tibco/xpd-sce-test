package com.tibco.xpd.om.modeler.subdiagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelVisualIDRegistry;

/**
 * @generated
 */
public class OrganizationModelSubNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 5003;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof OrganizationModelSubNavigatorItem) {
			OrganizationModelSubNavigatorItem item = (OrganizationModelSubNavigatorItem) element;
			return OrganizationModelVisualIDRegistry
					.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
