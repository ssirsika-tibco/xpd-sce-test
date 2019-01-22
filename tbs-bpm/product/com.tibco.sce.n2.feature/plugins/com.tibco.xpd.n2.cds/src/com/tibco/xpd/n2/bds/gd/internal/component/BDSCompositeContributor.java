/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.n2.bds.gd.internal.component;

import java.io.IOException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.CompositeModelBuilder;
import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.sca.model.composite.ComponentProperty;
import com.tibco.amf.sca.model.composite.ComponentReference;
import com.tibco.amf.sca.model.composite.ComponentService;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.bds.BDSFactory;
import com.tibco.n2.model.bds.BDSModelDescriptor;
import com.tibco.n2.model.bds.BDSSpecification;
import com.tibco.n2.model.bds.util.BDSResourceFactoryImpl;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.daa.utils.UnprotectedWriteOperation;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Contributes the BDS (global data) component to the composite.
 * 
 * @author Jan Arciuchiewicz
 */
public class BDSCompositeContributor extends
        com.tibco.xpd.daa.CompositeContributor {

    /** BDS component name. */
    private static final String BDS_COMPONENT_NAME = "BDS"; //$NON-NLS-1$

    private static final Logger LOG = CDSActivator.getDefault().getLogger();

    private static final String VERSION = "1.0.0"; //$NON-NLS-1$

    public static final String BDS_SPEC_FILENAME = "bds-spec.bds"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.n2.daa.CompositeContributor#contributeToComposite(org.eclipse
     * .core.resources.IProject, org.eclipse.core.resources.IContainer,
     * com.tibco.amf.sca.model.composite.Composite,
     * org.eclipse.emf.common.util.URI, java.lang.String, boolean)
     */
    @Override
    public IStatus contributeToComposite(IProject project,
            IContainer stagingArea, Composite composite, URI compositeLocation,
            String timeStamp, boolean replaceWithTS,
            final IChangeRecorder changeRecorder) {

        if (stagingArea instanceof IFolder) {

            IFolder stagingFolder = (IFolder) stagingArea;
            IStatus descStatus =
                    BDSDescriptorGenerator.getInstance()
                            .generateBSDDescriptor(project, stagingFolder);
            if (descStatus.getSeverity() != IStatus.OK) {

                return descStatus;
            }
            IFile caseModelFile =
                    stagingFolder
                            .getFile(BDSDescriptorGenerator.CASE_MODEL_FILE_NAME);
            if (caseModelFile != null && caseModelFile.exists()) {

                /* create brmModel */
                BDSFactory brmFactory = BDSFactory.eINSTANCE;
                BDSSpecification specification =
                        brmFactory.createBDSSpecification();
                BDSModelDescriptor bdsDescriptor =
                        brmFactory.createBDSModelDescriptor();
                bdsDescriptor.setLocation(caseModelFile.getFullPath()
                        .toPortableString());
                bdsDescriptor.setVersion(VERSION);

                specification.setBdsModelDescriptor(bdsDescriptor);

                IFile bdsSpecFile = stagingFolder.getFile(BDS_SPEC_FILENAME);
                URI bdsURI =
                        URI.createPlatformResourceURI(bdsSpecFile.getFullPath()
                                .toPortableString(), true);
                Resource bdsResource =
                        new BDSResourceFactoryImpl().createResource(bdsURI);
                bdsResource.getContents().add(specification);
                try {

                    bdsResource.save(null);
                } catch (IOException e) {

                    LOG.error(e);
                    return new Status(IStatus.ERROR, CDSActivator.PLUGIN_ID,
                            "BDS specification model save error.", e); //$NON-NLS-1$
                }

                try {

                    bdsSpecFile.refreshLocal(IResource.DEPTH_ZERO, null);
                    bdsSpecFile.setDerived(true);
                } catch (CoreException e) {

                    LOG.error(e);
                    return e.getStatus();
                }
                generateComponent(composite,
                        compositeLocation,
                        stagingArea,
                        project,
                        bdsSpecFile);
            }

        }
        return Status.OK_STATUS;
    }

    /**
     * @see com.tibco.xpd.daa.CompositeContributor#resetCompositeName(com.tibco.amf.sca.model.composite.Composite,
     *      org.eclipse.core.resources.IProject)
     * 
     * @param composite
     * @param project
     */
    @Override
    public void resetCompositeName(Composite composite, IProject project) {
        /*
         * XPD-5280:
         * "Append major version number to deploy name of Global Data BOM-only projects"
         */

        if (BOMUtils.isBusinessDataProject(project)) {

            /*
             * for a business data project the composite/app name will have
             * project id suffixed with "-" + <major version of the project>. so
             * constructing the new app name.
             * 
             * for instance for a project with id
             * "com.example.firstbizdataproject" and version "1.0.0.qualifier"
             * say, app name will be "com.example.firstbizdataproject-1"
             */
            String globalDataFormatedName =
                    BOMUtils.getAppName(project, composite.getName());

            composite.setName(globalDataFormatedName);
        }
    }

    /**
     * Generates the BDS Component details for the composite
     * 
     * @param composite
     * @param compositeLocation
     * @param stageFolder
     * @param project
     * @param bdsSpecFile
     */
    public void generateComponent(final Composite composite,
            final URI compositeLocation, final IContainer stageFolder,
            final IProject project, final IFile bdsSpecFile) {

        final ComponentTypeActivator ctActivator =
                ComponentTypeActivator.getDefault();
        final CompositeModelBuilder modelBuilder =
                ctActivator.getModelBuilder();
        TransactionalEditingDomain editingDomain =
                TransactionUtil.getEditingDomain(composite);

        if (bdsSpecFile instanceof IFile && bdsSpecFile.exists()) {

            final String bdsSpecFileLoc =
                    bdsSpecFile.getFullPath().toPortableString();
            final Implementation impl =
                    ctActivator.getComponentTypeService()
                            .createImplementationFromURL(bdsSpecFileLoc,
                                    compositeLocation);

            UnprotectedWriteOperation setContentsOp =
                    new UnprotectedWriteOperation(editingDomain) {
                        @Override
                        protected Object doExecute() {

                            Component comp =
                                    modelBuilder.createComponent(composite,
                                            impl);
                            comp.setName(BDS_COMPONENT_NAME);
                            comp.setVersion(CompositeUtil
                                    .getVersionNumber(project));

                            for (ComponentService cs : comp.getServices()) {

                                modelBuilder.promoteService(cs);
                            }
                            for (ComponentReference cs : comp.getReferences()) {

                                modelBuilder.promoteReference(cs);
                            }
                            for (ComponentProperty cs : comp.getProperties()) {

                                modelBuilder.promoteProperty(cs);
                            }
                            return comp;
                        }
                    };
            setContentsOp.execute();
        }
    }
}
