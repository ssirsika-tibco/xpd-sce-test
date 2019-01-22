/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.projectasset;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.PartInitException;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

/**
 * Asset configurator of the BOM asset.
 * 
 * @author njpatel
 */
public class BOMAssetConfigurator extends
        AbstractSpecialFolderAssetConfigurator implements
        IProjectAssetVersionProvider {

    @Override
    protected String getSpecialFolderKind() {
        return BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND;
    }

    @Override
    protected String getSpecialFolderName() {
        Object config = getConfiguration();
        if (config instanceof SpecialFolderAssetConfiguration) {
            String name =
                    ((SpecialFolderAssetConfiguration) config)
                            .getSpecialFolderName();

            if (name != null && name.length() > 0) {
                return name;
            }
        }

        return getDefaultFolderName();
    }

    @Override
    public String getDefaultFolderName() {
        return Messages.default_bomfolder_name;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.projectconfig.projectassets.
     * AbstractSpecialFolderAssetConfigurator
     * #configure(org.eclipse.core.resources.IProject, java.lang.Object)
     */
    @Override
    public void configure(IProject project, Object configuration)
            throws CoreException {
        super.configure(project, configuration);

        // XPD-5145 Set namespace file extension flag to true for new projects.
        QualifiedName key =
                new QualifiedName(
                        BOMResourcesPlugin.BOS_PREFERENCES_ID,
                        BOMResourcesPlugin.P_KEEP_NAMESPACE_FILE_EXTENSION_BOM_GENERATION);
        project.setPersistentProperty(key, Boolean.TRUE.toString());

        if (configuration instanceof BOMSpecialFolderAssetConfiguration) {

            BOMSpecialFolderAssetConfiguration bomConf =
                    (BOMSpecialFolderAssetConfiguration) configuration;

            if (bomConf.isCreateModel()) {
                String folderName = bomConf.getSpecialFolderName();
                // Create the folder
                if (folderName != null) {
                    IFolder folder = project.getFolder(folderName);

                    if (folder.exists()) {
                        // Create the sample bom model in this folder
                        IPath path = folder.getFullPath();
                        path = path.append(bomConf.getBomFileName());

                        URI uri =
                                URI.createPlatformResourceURI(path.toString(),
                                        true);

                        Resource diagram = null;

                        if (bomConf.getBomType() == null) {
                            // Create default BOM type
                            diagram =
                                    UMLDiagramEditorUtil.createDiagram(uri,
                                            new NullProgressMonitor());

                        } else {
                            // Create the first class profile BOM
                            IFirstClassProfileExtension extensionById =
                                    FirstClassProfileManager
                                            .getInstance()
                                            .getExtensionById(bomConf.getBomType());

                            diagram =
                                    UMLDiagramEditorUtil.createDiagram(uri,
                                            new NullProgressMonitor(),
                                            extensionById);
                        }

                        if (diagram != null) {
                            try {
                                UMLDiagramEditorUtil.openDiagram(diagram);
                            } catch (PartInitException e) {
                                BOMDiagramEditorPlugin.getInstance()
                                        .logError("Unable to open editor", e); //$NON-NLS-1$
                            }
                        }
                    }
                }

            }

        }

    }

    @Override
    public int getVersion(IProject project) {
        return Integer.parseInt(BOMResourcesPlugin.BOM_VERSION);
    }

}
