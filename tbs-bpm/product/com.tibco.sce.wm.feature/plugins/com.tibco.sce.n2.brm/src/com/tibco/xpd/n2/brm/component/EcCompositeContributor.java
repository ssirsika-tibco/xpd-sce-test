/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.brm.component;

import java.io.IOException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
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
import com.tibco.n2.model.ec.ECFactory;
import com.tibco.n2.model.ec.ECWorkSpecification;
import com.tibco.n2.model.ec.WorkListAttributeFacade;
import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.n2.brm.BRMActivator;
import com.tibco.xpd.n2.brm.BRMGenerator;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.n2.daa.utils.UnprotectedWriteOperation;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Contributes Event Collector component to the composite for a project.
 * 
 * @author jarciuch
 * @since 10 Jan 2014
 */
public class EcCompositeContributor extends CompositeContributor {

    private static final Logger LOG = BRMActivator.getDefault().getLogger();

    private static final String VERSION = "1.0.0"; //$NON-NLS-1$

    @Override
    public IStatus contributeToComposite(IProject project,
            IContainer stagingArea, Composite composite, URI compositeLocation,
            final String timeStamp, final boolean replaceWithTS,
            final IChangeRecorder changeRecorder, IProgressMonitor monitor) {

        try {
            monitor.beginTask("Creating Event Collector Components", 1);

            if (stagingArea instanceof IFolder) {
                IFolder stagingFolder = (IFolder) stagingArea;
                BRMGenerator.getInstance().generateWlfModel(project,
                        stagingFolder,
                        timeStamp);
                IFile wlfModule = stagingFolder.getFile(BRMUtils.WLF_FILE_NAME);
                if (wlfModule.exists()) {

                    // create brmModel
                    ECFactory ecFactory = ECFactory.eINSTANCE;
                    ECWorkSpecification specification =
                            ecFactory.createECWorkSpecification();
                    WorkListAttributeFacade wlf =
                            ecFactory.createWorkListAttributeFacade();
                    wlf.setLocation(wlfModule.getFullPath().toPortableString());
                    wlf.setVersion(VERSION);
                    specification.setWorkListAttributeFacade(wlf);

                    IFile ecModule =
                            stagingFolder.getFile(BRMUtils.EC_MODEL_FILENAME);
                    URI ecURI =
                            URI.createPlatformResourceURI(ecModule
                                    .getFullPath().toPortableString(), true);
                    Resource brmResource =
                            new ResourceSetImpl().createResource(ecURI);
                    brmResource.getContents().add(specification);
                    try {
                        brmResource.save(null);
                    } catch (IOException e) {
                        LOG.error(e);
                    }

                    try {
                        ecModule.refreshLocal(IResource.DEPTH_ZERO, null);
                        ecModule.setDerived(true);
                    } catch (CoreException e) {
                        LOG.error(e);
                    }
                    generateEcComponent(composite,
                            compositeLocation,
                            stagingArea,
                            project,
                            ecModule);
                }

            }
            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    private void generateEcComponent(final Composite composite,
            final URI compositeLocation, final IContainer stageFolder,
            final IProject project, final IFile ecModule) {
        final ComponentTypeActivator ctActivator =
                ComponentTypeActivator.getDefault();
        final CompositeModelBuilder modelBuilder =
                ctActivator.getModelBuilder();
        TransactionalEditingDomain editingDomain =
                TransactionUtil.getEditingDomain(composite);
        if (ecModule instanceof IFile && ecModule.exists()) {
            final String ecLoc = ecModule.getFullPath().toPortableString();
            final Implementation impl =
                    ctActivator.getComponentTypeService()
                            .createImplementationFromURL(ecLoc,
                                    compositeLocation);
            UnprotectedWriteOperation setContentsOp =
                    new UnprotectedWriteOperation(editingDomain) {
                        @SuppressWarnings("restriction")
                        @Override
                        protected Object doExecute() {
                            Component comp =
                                    modelBuilder.createComponent(composite,
                                            impl);

                            comp.setName("EC"); //$NON-NLS-1$
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
