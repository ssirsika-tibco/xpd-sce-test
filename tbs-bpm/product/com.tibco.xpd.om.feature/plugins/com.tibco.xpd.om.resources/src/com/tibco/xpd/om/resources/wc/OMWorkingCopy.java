/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.resources.wc;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.om.core.om.BaseOrgModel;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.provider.OMItemProviderAdapterFactory;
import com.tibco.xpd.om.core.om.provider.OrganisationModelEditPlugin;
import com.tibco.xpd.om.core.om.provider.OrganisationModelEditPlugin.Implementation;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.resources.wc.InvalidVersionException;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;

/**
 * Working copy for Organisation Model.
 * <p>
 * <i>Created: 21 Nov 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class OMWorkingCopy extends AbstractGMFWorkingCopy {

    /**
     * 
     */
    private static final Implementation EDIT_PLUGIN =
            OrganisationModelEditPlugin.getPlugin();

    public static final String JAVA_PACKAGE_SEPARATOR = "."; //$NON-NLS-1$

    public static final String UML_PACKAGE_SEPARATOR = "::"; //$NON-NLS-1$

    /**
     * OM Working copy.
     * 
     * @param resources
     *            list of <code>IResource</code>s to be maintained by this
     *            working copy.
     */
    public OMWorkingCopy(List<IResource> resources) {
        super(resources);
        registerResourceProvider(getWorkingCopyEPackage(), EDIT_PLUGIN);
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#createAdapterFactory()
     * 
     * @return
     */
    @Override
    protected AdapterFactory createAdapterFactory() {
        AdapterFactory af = super.createAdapterFactory();
        /*
         * XPD-5300: Need to register the adapter factory now, as otherwise will
         * not be able to get the label provider for OM models until one of the
         * OM models is first loaded. This way the OM adapter factory is
         * available when this adapter factory is used before an Org Model
         * resource is loaded (e.g. by WorkingCopyUtil.getText).
         */
        if (af instanceof ComposedAdapterFactory) {
            ((ComposedAdapterFactory) af)
                    .addAdapterFactory(new OMItemProviderAdapterFactory());
        }

        return af;
    }

    @Override
    protected EObject getModelFromResource(Resource res) {
        // Get the BaseOrgModel from the resource
        if (res != null && res.getContents() != null) {
            for (EObject content : res.getContents()) {
                if (content instanceof BaseOrgModel) {
                    return content;
                }
            }
        }
        return null;
    }

    @Override
    public EPackage getWorkingCopyEPackage() {
        return OMPackage.eINSTANCE;
    }

    /**
     * Produce fully qualified name of the concept. It differ from uml qualified
     * name as it doesn't use model name as prefix, and use '.' as section
     * separator.
     * 
     * @param cl
     * @return
     */
    public static String getQualifiedName(NamedElement cl) {
        return getQualifiedName(cl.getQualifiedName());
    }

    public static String getQualifiedName(String rawName) {
        if (rawName == null)
            return ""; //$NON-NLS-1$
        // removed, because it looks like we don't need this any more
        // rawName = rawName.substring(rawName.indexOf(UML_PACKAGE_SEPARATOR) +
        // 2);

        rawName =
                rawName.replace(UML_PACKAGE_SEPARATOR, JAVA_PACKAGE_SEPARATOR);
        return rawName;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#getMetaText(org.eclipse.emf.ecore.EObject)
     * 
     * @param eo
     * @return
     */
    @Override
    public String getMetaText(EObject eo) {
        /*
         * XPD-5300: Need to change label for dynamic organizations
         */
        if (eo instanceof Organization && ((Organization) eo).isDynamic()) {
            return EDIT_PLUGIN.getString("_UI_DynamicOrganisation_type"); //$NON-NLS-1$
        }
        return super.getMetaText(eo);
    }

    @Override
    protected Resource loadResource(IResource resource)
            throws InvalidFileException {
        Resource res = super.loadResource(resource);

        EObject model = getModelFromResource(res);
        if (model instanceof BaseOrgModel) {
            // get the project configuration
            IProject project = resource.getProject();
            ProjectConfig projectConfig = XpdResourcesPlugin.getDefault().getProjectConfig(project);

            // loook for an OM asset
            for (AssetType assetType : projectConfig.getAssetTypes()) {
                // If the OM asset version is less than the current value
                if ("com.tibco.xpd.asset.om".equals(assetType.getId())) { //$NON-NLS-1$
                    if (assetType.getVersion() < OMResourcesActivator.OM_FILE_VERSION) {
                        // Unload the resource and throw exception to create validation marker and trigger migration
                        res.unload();
                        throw new InvalidVersionException(Messages.OMWorkingCopy_OrgModelVersionProblem_message);
                    }

                    break; // there should be only one OM asset
                }
            }
        }

        return res;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#doMigrateToLatestVersion()
     *
     * @throws CoreException
     */
    @Override
    protected void doMigrateToLatestVersion() throws CoreException {
        /*
         * Sid ACE-1354 - GIVEN that there is a validation rule that has always
         * ensured that the organisation version exactly matches the project
         * version THEN we can get rid of the organisation version altogether
         * and use the parent Project version instead.
         */
        try {
            Resource resource = super.loadResource(getFirstResource());
            if (resource != null) {
                BaseOrgModel model = null;
                for (EObject eo : resource.getContents()) {
                    if (eo instanceof BaseOrgModel) {
                        model = (BaseOrgModel) eo;
                        break;
                    }
                }

                if (model != null) {
                    final BaseOrgModel orgModel = model;

                    if (orgModel instanceof OrgModel) {
                        RecordingCommand cmd = new RecordingCommand((TransactionalEditingDomain) getEditingDomain()) {
                            @Override
                            protected void doExecute() {
                                // perform System Action migrations
                                new SystemActionMigration().migrate((OrgModel) orgModel);
                            }
                        };
                        getEditingDomain().getCommandStack().execute(cmd);
                    }

                    if (model.getVersion() != null) {
                        RecordingCommand cmd = new RecordingCommand((TransactionalEditingDomain) getEditingDomain()) {
                            @Override
                            protected void doExecute() {
                                orgModel.setVersion(null);
                            }
                        };
                        getEditingDomain().getCommandStack().execute(cmd);
                    }

                    resource.save(null);
                }
            }
        } catch (Exception e) {
            if (e.getCause() instanceof CoreException) {
                throw (CoreException) e.getCause();
            } else {
                throw new CoreException(new Status(IStatus.ERROR,
                        OMResourcesActivator.PLUGIN_ID, e.getLocalizedMessage(),
                        e.getCause()));
            }
        }
    }
}
