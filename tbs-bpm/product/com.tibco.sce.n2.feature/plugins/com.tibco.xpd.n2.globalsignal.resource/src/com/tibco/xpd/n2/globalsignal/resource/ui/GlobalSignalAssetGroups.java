/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui;

import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;

/**
 * Global Signal asset groups (groups to be added under Global Signal, right now
 * adds only one: PayloadDataGroup).
 * 
 * @author sajain
 * @since Feb 24, 2015
 */
public class GlobalSignalAssetGroups {

    /**
     * Owner of these groups
     */
    private final GlobalSignal ownerLibrary;

    /**
     * The groups under the <code>Global Signal</code>
     */
    private final AbstractAssetGroup[] groups;

    /**
     * Map to hold the <code>GlobalSignalAssetGroups</code> associated with a
     * <code>Global Signal</code>.
     */
    private static Map<GlobalSignal, GlobalSignalAssetGroups> globalSignalsToGroupMap =
            new HashMap<GlobalSignal, GlobalSignalAssetGroups>();

    /**
     * Global Signal asset groups.
     * 
     * @param owner
     */
    public GlobalSignalAssetGroups(GlobalSignal owner) {

        this.ownerLibrary = owner;

        if (owner != null) {

            /*
             * Define the Global Signal groups
             */
            groups =
                    new AbstractAssetGroup[] { new PayloadDataGroup(
                            ownerLibrary) };
        } else {

            groups = null;
        }
    }

    /**
     * Get the logical groups for the owner <code>Global Signals</code>.
     * 
     * @return Array of <code>AbstractAssetGroup</code> for each expected
     *         logical group under a <code>Global Signals</code>. If no owner
     *         was defined then <b>null</b> will be returned.
     */
    public AbstractAssetGroup[] getGlobalSignalGroups() {

        return groups;
    }

    /**
     * Get an array of <code>AbstractAssetGroup</code> for each logical group
     * associated with the given <code>Global Signal</code>.
     * 
     * @param gs
     *            Global signal.
     * 
     * @return An array of <code>AbstractAssetGroup</code> for each logical
     *         group associated with the given <code>Global Signal</code>.
     */
    public static AbstractAssetGroup[] getGlobalSignalGroups(GlobalSignal gs) {

        AbstractAssetGroup[] groups = null;
        GlobalSignalAssetGroups assetGroups = getGSDAssetGroups(gs);

        if (assetGroups != null) {

            /*
             * Get the logical groups for the global signal.
             */
            groups = assetGroups.getGlobalSignalGroups();
        }

        return groups;
    }

    /**
     * Get the <code>GlobalSignalAssetGroups</code> associated with the given
     * <code>Global Signal</code>.
     * 
     * @param gs
     *            Global signal.
     * 
     * @return The <code>GlobalSignalAssetGroups</code> associated with the
     *         given <code>Global Signal</code>.
     */
    private static GlobalSignalAssetGroups getGSDAssetGroups(GlobalSignal gs) {

        GlobalSignalAssetGroups assetGroups = null;

        if (gs != null) {

            if (globalSignalsToGroupMap.containsKey(gs)) {

                assetGroups = globalSignalsToGroupMap.get(gs);

            } else {

                /*
                 * Create new group
                 */
                assetGroups = new GlobalSignalAssetGroups(gs);

                /*
                 * Update map
                 */
                globalSignalsToGroupMap.put(gs, assetGroups);
            }
        }

        return assetGroups;
    }

}
