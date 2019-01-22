/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
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
import com.tibco.n2.model.brm.BRMFactory;
import com.tibco.n2.model.brm.BRMWorkSpecification;
import com.tibco.n2.model.brm.ScriptDescriptors;
import com.tibco.n2.model.brm.WorkListAttributeFacade;
import com.tibco.n2.model.brm.WorkModels;
import com.tibco.n2.model.brm.WorkTypes;
import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.n2.brm.BRMGenerator;
import com.tibco.xpd.n2.brm.internal.Messages;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.n2.daa.utils.UnprotectedWriteOperation;
import com.tibco.xpd.n2.scriptdescriptor.utils.ScriptDescriptorUtils;

/**
 * @author kupadhya
 * 
 */
public class BRMCompositeContributor extends CompositeContributor {

    private static final String VERSION = "1.0.0"; //$NON-NLS-1$

    @Override
    public IStatus contributeToComposite(IProject project,
            IContainer stagingArea, Composite composite, URI compositeLocation,
            final String timeStamp, final boolean replaceWithTS,
            final IChangeRecorder changeRecorder, IProgressMonitor monitor) {

        try {
            monitor.beginTask(Messages.BRMCompositeContributor_CreateingComponents_message,
                    1);

            if (stagingArea instanceof IFolder) {
                IFolder stagingFolder = (IFolder) stagingArea;
                BRMGenerator.getInstance().generateBRMModules(project,
                        stagingFolder,
                        timeStamp);
                BRMGenerator.getInstance().generateWlfModel(project,
                        stagingFolder,
                        timeStamp);
                IFile wmModule = stagingFolder.getFile(BRMUtils.WM_FILE_NAME);
                IFile wtModule = stagingFolder.getFile(BRMUtils.WT_FILE_NAME);
                IFile wlfModule = stagingFolder.getFile(BRMUtils.WLF_FILE_NAME);
                IFile scriptDescriptorFile =
                        ScriptDescriptorUtils
                                .getScriptDescriptorFromStagingArea(project);

                if ((wmModule != null && wmModule.exists() && wtModule != null && wtModule
                        .exists()) || wlfModule.exists()) {

                    // create brmModel
                    BRMFactory brmFactory = BRMFactory.eINSTANCE;
                    BRMWorkSpecification specification =
                            brmFactory.createBRMWorkSpecification();
                    if (wmModule.exists()) {
                        WorkModels wm = brmFactory.createWorkModels();
                        wm.setLocation(wmModule.getFullPath()
                                .toPortableString());
                        wm.setVersion(BRMCompositeContributor.VERSION);
                        specification.setWorkModels(wm);
                    }
                    if (wtModule.exists()) {
                        WorkTypes wt = brmFactory.createWorkTypes();
                        wt.setLocation(wtModule.getFullPath()
                                .toPortableString());
                        wt.setVersion(BRMCompositeContributor.VERSION);
                        specification.setWorkTypes(wt);
                    }
                    if (wlfModule.exists()) {
                        WorkListAttributeFacade wlf =
                                brmFactory.createWorkListAttributeFacade();
                        wlf.setLocation(wlfModule.getFullPath()
                                .toPortableString());
                        wlf.setVersion(BRMCompositeContributor.VERSION);
                        specification.setWorkListAttributeFacade(wlf);
                    }

                    if (scriptDescriptorFile != null
                            && scriptDescriptorFile.exists()) {
                        ScriptDescriptors createScriptDescriptors =
                                brmFactory.createScriptDescriptors();
                        createScriptDescriptors
                                .setLocation(scriptDescriptorFile.getFullPath()
                                        .toPortableString());
                        createScriptDescriptors
                                .setVersion(BRMCompositeContributor.VERSION);
                        specification
                                .setScriptDescriptors(createScriptDescriptors);
                    }

                    IFile brmModule =
                            stagingFolder.getFile(BRMUtils.BRM_MODEL_FILENAME);
                    URI brmURI =
                            URI.createPlatformResourceURI(brmModule
                                    .getFullPath().toPortableString(), true);
                    Resource brmResource =
                            new ResourceSetImpl().createResource(brmURI);
                    brmResource.getContents().add(specification);
                    try {
                        brmResource.save(null);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    try {
                        brmModule.refreshLocal(IResource.DEPTH_ZERO, null);
                        brmModule.setDerived(true);
                    } catch (CoreException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    generateComponent(composite,
                            compositeLocation,
                            stagingArea,
                            project,
                            brmModule);
                }

            }
            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    public void generateComponent(final Composite composite,
            final URI compositeLocation, final IContainer stageFolder,
            final IProject project, final IFile brmModule) {
        final ComponentTypeActivator ctActivator =
                ComponentTypeActivator.getDefault();
        final CompositeModelBuilder modelBuilder =
                ctActivator.getModelBuilder();
        TransactionalEditingDomain editingDomain =
                TransactionUtil.getEditingDomain(composite);
        if (brmModule instanceof IFile && brmModule.exists()) {
            final String brmLoc = brmModule.getFullPath().toPortableString();
            final Implementation impl =
                    ctActivator.getComponentTypeService()
                            .createImplementationFromURL(brmLoc,
                                    compositeLocation);
            UnprotectedWriteOperation setContentsOp =
                    new UnprotectedWriteOperation(editingDomain) {
                        @Override
                        protected Object doExecute() {
                            Component comp =
                                    modelBuilder.createComponent(composite,
                                            impl);

                            comp.setName("BRM"); //$NON-NLS-1$
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
