/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.specialfolder;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.navigator.CommonNavigator;

import com.tibco.xpd.ui.projectexplorer.util.SpecialFolderPresentationUtil;
import com.tibco.xpd.ui.projectexplorer.util.SpecialFolderPresentationUtil.PresentationType;

/**
 * Action to set the special folder presentation. The presentation can be
 * Project Level or Normal.
 * <ul>
 * <li>Project Level - all special folders are displayed directly under the
 * project,</li>
 * <li>Normal - the special folders are displayed in their respective
 * locations.</li>
 * </ul>
 * 
 * @author njpatel
 */
public class SpecialFolderPresentationAction extends ActionDelegate implements
		IViewActionDelegate {

	private static final String PROJECT_LEVEL_ID = "com.tibco.xpd.resources.action.projectlevel"; //$NON-NLS-1$

	private static final String NORMAL_ID = "com.tibco.xpd.resources.action.normal"; //$NON-NLS-1$

	private CommonNavigator navigator = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
	 */
	public void init(IViewPart view) {
		if (view instanceof CommonNavigator) {
			navigator = (CommonNavigator) view;
		}
	}

	@Override
	public void init(IAction action) {
		PresentationType pref = SpecialFolderPresentationUtil
				.getPreferenceValue();

		if (pref == PresentationType.PROJECTLEVEL) {
			if (action.getId().equals(PROJECT_LEVEL_ID)) {
				action.setChecked(true);
			}
		} else if (pref == PresentationType.NORMAL) {
			if (action.getId().equals(NORMAL_ID)) {
				action.setChecked(true);
			}
		}
	}

	@Override
	public void run(IAction action) {
		if (action.isChecked()) {
			PresentationType type = null;
			if (action.getId().equals(PROJECT_LEVEL_ID)) {
				type = PresentationType.PROJECTLEVEL;
			} else if (action.getId().equals(NORMAL_ID)) {
				type = PresentationType.NORMAL;
			}

			// Set the preference value
			if (type != null) {
				SpecialFolderPresentationUtil.setPreferenceValue(type);
			}

			if (navigator != null) {
				navigator.getCommonViewer().refresh();
			}
		}
	}

}
