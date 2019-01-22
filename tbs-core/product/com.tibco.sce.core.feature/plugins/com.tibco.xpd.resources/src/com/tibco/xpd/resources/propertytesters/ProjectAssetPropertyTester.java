/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.resources.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;

/**
 * Project Asset property tester. This provides the following tests:
 * <ul>
 * <li><strong>hasAssets</strong> - check if a project has the given asset
 * configured (can be a comma-separated list of assets).</li>
 * </ul>
 * 
 * @author njpatel
 */
public class ProjectAssetPropertyTester extends PropertyTester {

    public static final String HAS_ASSETS = "hasAssets"; //$NON-NLS-1$

    public ProjectAssetPropertyTester() {
    }

    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (receiver instanceof IProject) {
            if (HAS_ASSETS.equals(property)) {
                if (expectedValue instanceof String) {
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig((IProject) receiver);
                    if (config != null) {
                        String[] assetIds = ((String) expectedValue).split(","); //$NON-NLS-1$

                        for (String assetId : assetIds) {
                            if (!config.hasAssetType(assetId.trim())) {
                                return false;
                            }
                        }

                        // Test passed if we got here
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
