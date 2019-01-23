/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.resources.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

/**
 * 
 * Helper/Manager class for the BOM Migration extension contributions.
 * 
 * @author rgreen
 * 
 */
public class BOMMigrationExtensionHelper {

    private static String EXTENSION_POINT = "migration";

    /** Singleton instance. */
    private static final BOMMigrationExtensionHelper INSTANCE =
            new BOMMigrationExtensionHelper();

    private ArrayList<BOMMigrationExtension> contributions =
            new ArrayList<BOMMigrationExtension>();

    /**
     * Private constructor to prevent instantiation.
     */
    private BOMMigrationExtensionHelper() {

        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(BOMResourcesPlugin.PLUGIN_ID,
                                EXTENSION_POINT);
        Assert.isNotNull(extensionPoint,
                "Cannot find extension: " + EXTENSION_POINT); //$NON-NLS-1$

        if (extensionPoint != null) {
            IConfigurationElement[] elements =
                    extensionPoint.getConfigurationElements();
            if (elements != null) {
                for (IConfigurationElement element : elements) {

                    BOMMigrationExtension ext =
                            new BOMMigrationExtension(element);

                    if (ext != null) {
                        contributions.add(ext);
                    }

                }
            }
        }

    }

    public static BOMMigrationExtensionHelper getInstance() {
        return INSTANCE;
    }

    /**
     * 
     * Gets the migration command from the class specified in each BOM migration
     * extension. Only commands corresponding to the supplied version will be
     * returned. This version number is also specified in the extension.
     * 
     * @param TransactionalEditingDomain
     * @param Model
     * @param Integer
     *            version
     * @return List of {@link Command}s to migrate the BOM to the given version,
     *         empty list if no commands are registered.
     */
    public List<Command> getMigrationCommands(TransactionalEditingDomain ed,
            Model model, Integer version) {

        List<Command> lstCmds = new ArrayList<Command>();

        for (BOMMigrationExtension ext : contributions) {

            if (Integer.valueOf(ext.getVersion()) == version) {
                Object execExt = ext.getExecutableExtension();

                if (execExt instanceof IBOMMigration) {
                    IBOMMigration mig = (IBOMMigration) execExt;

                    Command cmd = mig.getMigrationCommand(ed, model);

                    if (cmd != null) {
                        lstCmds.add(cmd);
                    }
                }
            }
        }

        return lstCmds;
    }
}
