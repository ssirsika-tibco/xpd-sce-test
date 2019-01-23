/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards;

import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class CreateTestWizardsConstants {

    public static final String IMG_CREATEBASETEST_WIZARD =
            "icons/createTestWizard.png"; //$NON-NLS-1$

    public static final String IMG_CREATECLASSAPITEST_WIZARD =
            "icons/createClassApiTestWizard.png"; //$NON-NLS-1$

    public static final String IMG_CREATEBASETEST_ICON =
            "icons/createTestWizard16.gif"; //$NON-NLS-1$

    public static final String IMG_DEFAULT_QUICKFIX =
            "icons/quickFixDefault.gif"; //$NON-NLS-1$

    public static final String IMG_WARNING_LARGE = "icons/warningLarge.gif"; //$NON-NLS-1$

    public static final String IMG_PROBLEM = "icons/problem.gif"; //$NON-NLS-1$

    public static final String IMG_OK = "icons/ok.gif"; //$NON-NLS-1$

    public static final String IMG_CLASS = "icons/class.gif"; //$NON-NLS-1$

    /**
     * @param id
     * @return Image for one of the id's listed in this class.
     */
    public static Image getImage(String id) {
        return CreateTestWizardsPlugin.getDefault().getImageRegistry().get(id);
    }
}
