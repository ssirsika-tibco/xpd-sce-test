/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.globaldata.resources;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Property Testers for BOM Folder. The tests include:
 * <ul>
 * <li><b>bomHasGlobalData</b>: Tests whether the given <code>IResource</code>
 * has BOM file with global data elements.
 * 
 * @author agondal
 * 
 */
public class GlobalDataBOMTester extends PropertyTester {

    /**
     * To test whether a BOM folder has files with global data
     * 
     */
    public static final String PROP_BOM_HASGLOBALDATA = "bomHasGlobalData"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        if (property.equalsIgnoreCase(PROP_BOM_HASGLOBALDATA)) {
            if (receiver instanceof IContainer) {
                IContainer container = (IContainer) receiver;

                IProject project = container.getProject();
                if (container != null && project != null) {
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(project);

                    if (BOMUtils.isBusinessDataProject(project)) {

                        if (config != null
                                && config.getSpecialFolders() != null) {

                            if (expectedValue != null) {

                                String kind = expectedValue.toString();
                                EList<SpecialFolder> specialFolders =
                                        config.getSpecialFolders()
                                                .getFoldersOfKind(kind);
                                /*
                                 * check for the members of the special folder
                                 * has any non-empty bom folders
                                 */

                                for (SpecialFolder specialFolder : specialFolders) {

                                    try {

                                        if (isNonEmptyBomFolder(specialFolder,
                                                args)) {

                                            return true;
                                        }
                                    } catch (Exception e) {
                                        XpdResourcesPlugin
                                                .getDefault()
                                                .getLogger()
                                                .error(e,
                                                        "Error in BOM Global Data Property Tester"); //$NON-NLS-1$
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check if the given folder has bom files
     * 
     * @param container
     * @return
     * @throws CoreException
     */
    private boolean isNonEmptyBomFolder(SpecialFolder specialFolder,
            Object... bomExts) {

        if (specialFolder.getFolder() != null
                && specialFolder.getFolder().isAccessible()) {

            Set<String> validExts = new HashSet<String>();
            for (Object bomExt : bomExts) {
                if (bomExt instanceof String) {
                    validExts.add((String) bomExt);
                }
            }

            return BOMGlobalDataUtils.hasFileWithExtension(specialFolder
                    .getFolder(), validExts);
        }
        return false;
    }

}
