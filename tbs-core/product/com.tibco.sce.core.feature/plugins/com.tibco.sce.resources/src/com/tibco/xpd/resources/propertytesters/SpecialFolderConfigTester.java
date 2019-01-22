/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFolder;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;

/**
 * Property Testers for <code>IFolder</code>. The test includes:
 * <ul>
 * <li> <b>isOfKind</b>: Tests whether the given <code>IFolder</code> is
 * actually a <code>SpecialFolder</code> of a given kind.
 * </ul>
 * 
 * @author njpatel
 */
public class SpecialFolderConfigTester extends PropertyTester {

    public static final String PROP_ISOFKIND = "isOfKind"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        if (receiver instanceof IFolder) {
            IFolder folder = (IFolder) receiver;
            ProjectConfig config = XpdResourcesPlugin.getDefault()
                    .getProjectConfig(folder.getProject());

            if (config != null) {
                return config.getSpecialFolders().getFolder(folder,
                        (String) expectedValue) != null;
            }
        }

        return false;
    }

}
