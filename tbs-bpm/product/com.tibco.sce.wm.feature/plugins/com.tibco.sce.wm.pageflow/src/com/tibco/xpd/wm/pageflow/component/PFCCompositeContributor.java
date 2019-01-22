/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.component;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.pfe.PFESpecification;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.daa.utils.UnprotectedWriteOperation;
import com.tibco.xpd.n2.pe.component.PECompositeContributor;
import com.tibco.xpd.n2.pe.util.RestBindingUtil;
import com.tibco.xpd.n2.pe.util.SoapBindingUtil;
import com.tibco.xpd.wm.pageflow.WMPageflowPlugin;

/**
 * @author kupadhya
 * 
 */
public class PFCCompositeContributor extends PECompositeContributor {
    /**
     * Returns the folder where all the bpel files are located
     * 
     * @param project
     * @return
     */
    @Override
    protected IFolder getBpelOutFolder(IProject project) {
        return BPELN2Utils.getBpelPageFlowOutDestFolder(project);
    }

    @Override
    protected String getFilePathAppendString() {
        return PFEUtil.PAGE_FLOW_APPEND;
    }

    @Override
    protected IFile[] getBpelFiles(IFile xpdlFile) {
        IFile[] bpelFiles = BPELN2Utils.getPageFlowBpelFiles(xpdlFile);
        return bpelFiles;
    }

    @Override
    protected void promoteComponent(Component component, IProject project,
            String timeStamp) {
        Implementation compImplementation = component.getImplementation();
        if (!(compImplementation instanceof PFESpecification)) {
            return;
        }
        super.promoteComponent(component, project, timeStamp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatus contributeToComposite(final IProject project,
            IContainer stagingArea, final Composite composite,
            URI compositeLocation, final String timeStamp,
            final boolean replaceWithTS, final IChangeRecorder changeRecorder,
            IProgressMonitor monitor) {

        IStatus status =
                super.contributeToComposite(project,
                        stagingArea,
                        composite,
                        compositeLocation,
                        timeStamp,
                        replaceWithTS,
                        changeRecorder,
                        monitor);

        configureSecurityPolicies(project, stagingArea, composite);
        configureSecurityRestPolicies(project, stagingArea, composite);
        configureThreadingPolicy(project, stagingArea, composite);

        // JA: If bindings are created before adding policies. It will usually
        // behaves incorrectly not recognizing policy and using an embeded one.
        TransactionalEditingDomain editingDomain =
                TransactionUtil.getEditingDomain(composite);
        UnprotectedWriteOperation promoteComponentsOp =
                new UnprotectedWriteOperation(editingDomain) {
                    @Override
                    protected Object doExecute() {
                        SoapBindingUtil
                                .configCompositeInterfaceBindings(project,
                                        composite,
                                        timeStamp,
                                        replaceWithTS);
                        RestBindingUtil.configCompositeRestBindings(project,
                                composite,
                                timeStamp,
                                replaceWithTS);
                        return Status.OK_STATUS;
                    }
                };
        promoteComponentsOp.execute();
        return status;
    }

    /**
     * @see com.tibco.xpd.n2.pe.component.PECompositeContributor#prepareProject(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param monitor
     * @return
     */
    @Override
    public IStatus prepareProject(IProject project, IProgressMonitor monitor) {
        /*
         * SID XPD-2613: The super class
         * (PECompositeContributor.prepareProject()) runs the
         * BPELOnDemandBuilder for this project and that creates all the
         * business process AND all the pageflow processes that we need - so no
         * need to execute it again.
         */
        return new Status(IStatus.OK, WMPageflowPlugin.PLUGIN_ID, "ok"); //$NON-NLS-1$
    }
}
