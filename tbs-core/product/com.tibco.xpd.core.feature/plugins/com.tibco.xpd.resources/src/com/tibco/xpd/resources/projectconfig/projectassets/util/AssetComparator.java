/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets.util;

import java.util.Comparator;

/**
 * <code>Comparator</code> for the project asset types. The asset dependencies
 * will be used to determine the order.
 * 
 * @author njpatel
 * 
 */
public class AssetComparator implements Comparator<ProjectAssetElement> {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(ProjectAssetElement asset1, ProjectAssetElement asset2) {

        if (!asset1.equals(asset2)) {

            /*
             * Check the dependencies
             */
            String[] dependencies = asset1.getDependencies();

            /*
             * if asset1 depends on asset2 then asset2 should be executed before
             * asset1
             */
            for (String id : dependencies) {
                if (id.equals(asset2.getId())) {
                    return 1;
                }
            }

            /*
             * if asset2 depends on asset1 then asset1 should be executed before
             * asset2
             */
            dependencies = asset2.getDependencies();

            for (String id : dependencies) {
                if (id.equals(asset1.getId())) {
                    return -1;
                }
            }

        } else {
            return 0;
        }

        return 1;
    }

}