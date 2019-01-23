/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.component;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.ProvidedCapability;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.composite.core.it.BxComponentTypeResolver;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.n2.pfe.PFESpecification;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.pe.util.PEN2Utils;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author kupadhya
 * 
 */
public class PFCComponentTypeResolver extends BxComponentTypeResolver {

    private final String DOCUMENT_MODEL_ACTIVITY_PACKAGE_ID =
            "com.tibco.amxbpm.documentactivity.model"; //$NON-NLS-1$

    @Override
    public String getImplementationTypeId() {

        return "implementation.pageflow"; //$NON-NLS-1$
    }

    @Override
    public Requirements getComponentRequirements(Implementation impl) {

        if (!(impl instanceof PFESpecification)) {

            return null;
        }
        PFESpecification pfeImpl = (PFESpecification) impl;
        Requirements reqs = super.getComponentRequirements(impl);
        addProvidedCapability(reqs, pfeImpl);
        // Add required feature dependency
        // addFeatureDependency(reqs, impl);

        /* XPD-6734: add document activity model import package */
        addImportPackageForDocumentActivity(pfeImpl, reqs);
        return reqs;
    }

    /**
     * Get the xpdl package from the implementation, add document activity model
     * to the import package list if there are any document service activities
     * in the xpdl package
     * 
     * @param impl
     * @param reqs
     */
    private void addImportPackageForDocumentActivity(PFESpecification impl,
            Requirements reqs) {

        ServiceImplementation serviceModel = impl.getServiceModel();
        String xpdlFileName = serviceModel.getModuleName();
        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(xpdlFileName));
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (workingCopy instanceof Xpdl2WorkingCopyImpl) {

            Xpdl2WorkingCopyImpl xpdlWC = (Xpdl2WorkingCopyImpl) workingCopy;
            Package xpdlPackage = (Package) xpdlWC.getRootElement();
            boolean documentServiceActivityFound =
                    isDocumentServiceActivityFound(xpdlPackage);
            if (documentServiceActivityFound) {

                ImportPackage activityModelImportPkg =
                        getDocumentActivityModelImportPkg();
                EList<ImportPackage> packageImports = reqs.getPackageImports();
                packageImports.add(activityModelImportPkg);
            }
        }
    }

    /**
     * Iterate thru each process in the given xpdl package to find if there is
     * any document service activity
     * 
     * @param xpdlPackage
     * @return <code>true</code> if any document service activity is found in
     *         any of the processes in the given xpdl package <code>false</code>
     *         otherwise
     */
    private boolean isDocumentServiceActivityFound(Package xpdlPackage) {

        EList<Process> processes = xpdlPackage.getProcesses();
        for (Process process : processes) {

            if (Xpdl2ModelUtil.isPageflow(process)) {

                Collection<Activity> activities =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);
                for (Activity activity : activities) {

                    DocumentOperation documentOperation = null;
                    TaskType taskType =
                            TaskObjectUtil.getTaskTypeStrict(activity);

                    if (TaskType.SERVICE_LITERAL.equals(taskType)) {

                        String extensionId =
                                TaskObjectUtil
                                        .getTaskImplementationExtensionId(activity);

                        if (TaskImplementationTypeDefinitions.DOCUMENT_OPERATIONS
                                .equals(extensionId)) {

                            TaskService taskService =
                                    ((Task) activity.getImplementation())
                                            .getTaskService();

                            Object otherElement =
                                    Xpdl2ModelUtil
                                            .getOtherElement(taskService,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_DocumentOperation());

                            if (otherElement instanceof DocumentOperation) {
                                documentOperation =
                                        (DocumentOperation) otherElement;
                            }
                        }

                        if (null != documentOperation) {

                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Create and return an instance of {@link ImportPackage}
     * 
     * @return {@link ImportPackage} instance with the import package id and
     *         version set
     */
    private ImportPackage getDocumentActivityModelImportPkg() {

        ImportPackage importPackage =
                OsgiFactory.eINSTANCE.createImportPackage();
        importPackage.setName(DOCUMENT_MODEL_ACTIVITY_PACKAGE_ID);

        VersionRange vr = OsgiFactory.eINSTANCE.createVersionRange();
        vr.setLower("1.0.0"); //$NON-NLS-1$
        vr.setLowerIncluded(true);
        vr.setUpper("2.0.0"); //$NON-NLS-1$
        vr.setUpperIncluded(false);
        importPackage.setRange(vr);

        return importPackage;
    }

    /**
     * Adds the feature dependency
     * 
     * @param reqs
     * @param impl
     */
    private void addFeatureDependency(Requirements reqs, Implementation impl) {

        if (null != reqs) {

            RequiredFeature modelPF = N2PENamingUtils.getBPMITModelPF();
            reqs.getFeatureDependencies().add(modelPF);
        }
    }

    /**
     * Creates and adds the pageflow capability to the provided capabilities
     * list
     * 
     * @param requirements
     * @param pfeImpl
     */
    private void addProvidedCapability(Requirements requirements,
            PFESpecification pfeImpl) {

        /*
         * SId XPD-7276 - if we're adding a providedCapability then we must
         * ensure that we add the capability factory.
         * 
         * Nominally, the factory always used to get added because eventually
         * PFCExtensionActivityResolver would add it (as it did soe when
         * processing any actiity in a pageflow).
         * 
         * HOWEVER, for the hidden pageflow created for Business Process
         * 'Publish as REST service' factory only got added to pageflow
         * component because of a accident in PE's EXtensionActivityResolver
         * that meant that PFCExtensionActivityResolver.handlActivity() was
         * called even for Business Process activities.
         * 
         * So now we add it specifically when adding the provided capability as
         * we always should have.
         */
        addFactoryCapability(requirements.getRequiredCapabilities());

        EList<ProvidedCapability> providedCapabilities =
                requirements.getProvidedCapabilities();
        IProject project = WorkingCopyUtil.getProjectFor(pfeImpl);
        String qualifierReplacer =
                PluginManifestHelper.getQualifierReplacer(pfeImpl);
        String moduleName = pfeImpl.getServiceModel().getModuleName();
        Path xpdlFilePath = new Path(moduleName);
        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(xpdlFilePath);
        String componentName =
                PEN2Utils.getComponentName(xpdlFile,
                        PFEUtil.PAGE_FLOW_APPEND.substring(1));
        ProvidedCapability providedCapability =
                PFEUtil.getPFCProvidedCapability(project,
                        qualifierReplacer,
                        componentName);
        providedCapabilities.add(providedCapability);
    }

    /**
     * Add the required capability factory if it is not already added yet.
     * 
     * @param requiredCapabilities
     * @return the (new or existing) factory for reference
     */
    private RequiredCapability addFactoryCapability(
            List<RequiredCapability> requiredCapabilities) {

        RequiredCapability factoryRc =
                PFEUtil.findCapabilityWithId(requiredCapabilities,
                        PFEUtil.PFE_REQUIRED_CAPABILITY_ID);

        if (factoryRc == null) {
            factoryRc = PFEUtil.createPFCRequiredCapability();
            requiredCapabilities.add(factoryRc);
        }

        return factoryRc;
    }

}
