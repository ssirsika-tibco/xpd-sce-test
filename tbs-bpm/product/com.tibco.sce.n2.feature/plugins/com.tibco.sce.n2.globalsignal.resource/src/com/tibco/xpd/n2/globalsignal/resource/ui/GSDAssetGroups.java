/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;

/**
 * GSD asset groups (groups to be added under GSD file, right now adds only one:
 * GlobalSignalsGroup).
 * 
 * @author sajain
 * @since Feb 21, 2015
 */
public class GSDAssetGroups {

    /**
     * Owner of these groups
     */
    private final GlobalSignalDefinitions ownerLibrary;

    /**
     * The groups under the <code>Global Signal Definitions</code>
     */
    private final AbstractAssetGroup[] groups;

    /**
     * Map to hold the <code>GSDAssetGroups</code> associated with a
     * <code>Global Signal Definition</code>.
     */
    private static Map<GlobalSignalDefinitions, GSDAssetGroups> gsdsToGroupsMap =
            new HashMap<GlobalSignalDefinitions, GSDAssetGroups>();

    /**
     * GSD asset groups.
     * 
     * @param owner
     */
    public GSDAssetGroups(GlobalSignalDefinitions owner) {

        this.ownerLibrary = owner;

        if (owner != null) {

            /*
             * Define the GSD groups
             */
            groups =
                    new AbstractAssetGroup[] { new GlobalSignalsGroup(
                            ownerLibrary) };
        } else {

            groups = null;
        }
    }

    /**
     * Get the logical groups for the owner
     * <code>Global Signal Definitions</code>.
     * 
     * @return Array of <code>AbstractAssetGroup</code> for each expected
     *         logical group under a <code>Global Signal Definitions</code>. If
     *         no owner was defined then <b>null</b> will be returned.
     */
    public AbstractAssetGroup[] getGSDGroups() {

        return groups;
    }

    /**
     * Get the logical group that contains the given <code>EObject</code>.
     * 
     * @param eo
     * @return <code>AbstractAssetGroup</code> that contains the given object,
     *         <b>null</b> will be returned if unable to get the group.
     */
    public static AbstractAssetGroup getParentGroup(EObject eo) {

        AbstractAssetGroup group = null;

        if (eo != null) {

            EObject eContainer = eo.eContainer();

            AbstractAssetGroup[] assetGroups = null;

            if (eContainer instanceof GlobalSignalDefinitions) {

                assetGroups =
                        getGSDGroups((GlobalSignalDefinitions) eContainer);

            }

            if (assetGroups != null) {

                for (AbstractAssetGroup a : assetGroups) {

                    if (a.getChildren().contains(eo)) {

                        group = a;

                        break;
                    }
                }
            }
        }

        return group;
    }

    /**
     * Get an array of <code>AbstractAssetGroup</code> for each logical group
     * associated with the given <code>GSD</code>.
     * 
     * @param gsds
     * @return
     */
    public static AbstractAssetGroup[] getGSDGroups(GlobalSignalDefinitions gsds) {

        AbstractAssetGroup[] groups = null;
        GSDAssetGroups assetGroups = getGSDAssetGroups(gsds);

        if (assetGroups != null) {

            /*
             * Get the logical groups for the GSD
             */
            groups = assetGroups.getGSDGroups();
        }

        return groups;
    }

    /**
     * Get the <code>GSDAssetGroups</code> associated with the given
     * <code>GSD</code>.
     * 
     * @param gsds
     * @return
     */
    private static GSDAssetGroups getGSDAssetGroups(GlobalSignalDefinitions gsds) {

        GSDAssetGroups assetGroups = null;

        if (gsds != null) {

            if (gsdsToGroupsMap.containsKey(gsds)) {

                assetGroups = gsdsToGroupsMap.get(gsds);

            } else {

                /*
                 * Create new group
                 */
                assetGroups = new GSDAssetGroups(gsds);

                /*
                 * Update map
                 */
                gsdsToGroupsMap.put(gsds, assetGroups);
            }
        }

        return assetGroups;
    }
}
