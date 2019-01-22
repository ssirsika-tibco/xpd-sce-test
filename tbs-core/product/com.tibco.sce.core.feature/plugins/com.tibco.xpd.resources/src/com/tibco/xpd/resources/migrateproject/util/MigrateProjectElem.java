/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.resources.migrateproject.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.migrateproject.MigrateProject;

/**
 * Wrapper for the migrate project extension.
 * 
 * @author njpatel
 */
public class MigrateProjectElem {

    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

    private static final String ELEM_DEPENDSON = "dependsOn"; //$NON-NLS-1$

    private final IConfigurationElement element;

    /**
     * Constructor
     * 
     * @param element
     *            Extension configuration element.
     */
    public MigrateProjectElem(IConfigurationElement element) {
        this.element = element;
    }

    /**
     * Get id of the extension.
     * 
     * @return ID
     */
    public String getId() {
        String id = null;

        if (element != null) {
            id = element.getAttribute(ATTR_ID);
        }

        return id != null ? id : ""; //$NON-NLS-1$
    }

    /**
     * Get the class attribute of the extension
     * 
     * @return <code>MigrateProject</code> object.
     * @throws CoreException
     */
    public MigrateProject getMigrateProject() throws CoreException {
        MigrateProject obj = null;

        if (element != null) {
            obj = (MigrateProject) element
                    .createExecutableExtension(ATTR_CLASS);
        }

        return obj;
    }

    /**
     * Get a list of migrate extension ids that this extension depends on
     * 
     * @return List of ids. If this extension doesn't depend on other extensions
     *         then an empty list will be returned.
     */
    public List<String> getDependsOnIds() {
        List<String> ids = new ArrayList<String>();

        if (element != null) {
            IConfigurationElement[] children = element
                    .getChildren(ELEM_DEPENDSON);

            if (children != null) {
                for (IConfigurationElement child : children) {
                    String id = child.getAttribute(ATTR_ID);

                    if (id != null) {
                        ids.add(id);
                    }
                }
            }
        }

        return ids;
    }

    @Override
    public String toString() {
        try {
            MigrateProject migrateProject = getMigrateProject();

            if (migrateProject != null) {
                return migrateProject.toString();
            }
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }

        return super.toString();
    }
}
