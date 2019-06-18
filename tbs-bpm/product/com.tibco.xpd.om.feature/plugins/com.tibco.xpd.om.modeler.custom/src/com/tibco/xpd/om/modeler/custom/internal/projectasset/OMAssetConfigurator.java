package com.tibco.xpd.om.modeler.custom.internal.projectasset;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.PartInitException;

import com.tibco.xpd.om.modeler.custom.internal.Messages;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorUtil;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

public class OMAssetConfigurator extends AbstractSpecialFolderAssetConfigurator
        implements IProjectAssetVersionProvider {

    @Override
    protected String getSpecialFolderKind() {
        return OMResourcesActivator.OM_SPECIAL_FOLDER_KIND;
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
        return Messages.OMAssetConfigurator_DefaultFolderName;
    }

    @Override
    public void configure(IProject project, Object configuration)
            throws CoreException {
        super.configure(project, configuration);

        if (configuration instanceof OMSpecialFolderAssetConfiguration) {
            OMSpecialFolderAssetConfiguration omConf =
                    (OMSpecialFolderAssetConfiguration) configuration;

            String folderName = omConf.getSpecialFolderName();

            if (folderName != null) {
                IFolder folder = project.getFolder(folderName);

                if (folder.exists()) {
                    if (omConf.isCreateModel()) {
                        // Create the sample om model in this folder
                        IPath path = folder.getFullPath();
                        path = path.append(omConf.getOmFileName());

                        URI uri =
                                URI.createPlatformResourceURI(path.toString(),
                                        true);

                        Map<String, Object> params =
                                new HashMap<String, Object>();
                        params
                                .put(OrganizationModelDiagramEditorUtil.CREATE_DEFAULT_METAMODEL_PARAM,
                                        omConf.isCreateSchema() ? Boolean.TRUE
                                                : Boolean.FALSE);
                        params
                                .put(OrganizationModelDiagramEditorUtil.APPLY_STANDARD_TYPE_PARAM,
                                        omConf.isApplyType() ? Boolean.TRUE
                                                : Boolean.FALSE);

                        Resource diagram =
                                OrganizationModelDiagramEditorUtil
                                        .createDiagram(uri,
                                                new NullProgressMonitor(),
                                                params);

                        if (diagram != null) {
                            try {
                                OrganizationModelDiagramEditorUtil
                                        .openDiagram(diagram);
                            } catch (PartInitException ex) {
                                OrganizationModelDiagramEditorPlugin
                                        .getInstance()
                                        .logError("Unable to open editor", ex); //$NON-NLS-1$
                            }

                        }

                    }
                }
            }
        }

    }

    /**
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider#getVersion(org.eclipse.core.resources.IProject)
     *
     * @param project
     * @return
     */
    @Override
    public int getVersion(IProject project) {
        return OMResourcesActivator.OM_FILE_VERSION;
    }

}
