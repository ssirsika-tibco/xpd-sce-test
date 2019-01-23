/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.component;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.osgi.framework.Version;

import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.composite.xpdl.core.extensions.ExtensionActivityRequirementsResolver;
import com.tibco.n2.pfe.PFESpecification;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.pe.util.PEN2Utils;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.wm.pageflow.component.PFEUtil.CapabilityIdVersion;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Deals with the addition of requirements needed for various Tasks and
 * Sub-Process invocations.
 * <p>
 * This class deals with tasks in Pageflow (and derivative) prcoesses and
 * Service Processes with Pageflow Engine deployment target set.
 * </p>
 * 
 * @author kupadhya
 * 
 */
public class PFCExtensionActivityResolver extends
        ExtensionActivityRequirementsResolver {

    @Override
    public void addImplementationRequirements(Requirements requirements,
            Implementation implementation) {
        if (implementation instanceof PFESpecification) {
            super.addImplementationRequirements(requirements, implementation);
        }
    }

    @Override
    protected void handleActivity(Activity activity, Requirements requirements,
            Implementation implementation) {

        List<RequiredCapability> requiredCapabilities =
                requirements.getRequiredCapabilities();
        RequiredCapability factoryCapability =
                PFEUtil.findCapabilityWithId(requiredCapabilities,
                        PFEUtil.PFE_REQUIRED_CAPABILITY_ID);
        if (factoryCapability == null) {
            /*
             * XPD-7201 may as well use the create(and add)FactoryCapability()
             * method we now have to override sub-class one, so we're doing
             * things consistently.
             */
            createFactoryCapability(requiredCapabilities);
        }

        super.handleActivity(activity, requirements, implementation);
    }

    /**
     * Our version of the capability factory is the PFE factory. This factory
     * should be capable of handling both PFE and PE capabilities (using
     * propagation) so is used for both references to pageflows and business
     * process references
     * 
     * @see com.tibco.bx.composite.xpdl.core.extensions.ExtensionActivityRequirementsResolver#createFactoryCapability(java.util.List)
     * 
     * @param requiredCapabilities
     * @return
     */
    @Override
    protected RequiredCapability createFactoryCapability(
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

    /**
     * Add any necessary requirements related to sub-process invocations from
     * pageflow (or service process for pageflow engine deploy target)
     * 
     * @see com.tibco.bx.composite.xpdl.core.extensions.ExtensionActivityRequirementsResolver#findSubFlowRequirements(com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.amf.sca.model.componenttype.Requirements,
     *      com.tibco.amf.sca.model.extensionpoints.Implementation)
     * 
     * @param activity
     * @param requirements
     * @param implementation
     */
    @Override
    protected void findSubFlowRequirements(Activity activity,
            Requirements requirements, Implementation implementation) {
        if (activity == null) {
            return;
        }

        SubFlow subFlow = (SubFlow) activity.getImplementation();
        if (subFlow == null) {
            return;
        }

        Process subProcess = SubProcUtil.getSubProcess(subFlow);
        if (subProcess == null) {
            return;
        }

        /**
         * XPD-7201 - For sub-process invocations in a different component we
         * need to add a requiresCapability upon that component. This will be so
         * if...
         * 
         * For the first two we deal with them here as they're PFE to PFE (and
         * underlying BX class doesn't deal with pageflow capabilities).
         * 
         * - Pageflow/PFE Service Process -> Pageflow in different package
         * 
         * - Pageflow/PFE Service Process -> Service Process (Deployed to PFE
         * i.e. not asynchronous call to PE service process) in different
         * package.
         * 
         * For the second two we call the super as BX class is responsible for
         * dealing with PE capability.
         * 
         * 
         * All other scenarios should be validated out.
         */

        if (Xpdl2ModelUtil.isBusinessProcess(subProcess)) {

            /*
             * XPD-7456: (as Pageflow/PFE Service Process -> Business process
             * (will always be async detatched ) Now we do not add requires
             * capability to PE via PFE factory capability. As we hit problems
             * on deploy because pageflow was not propagating requiresCapability
             * for PE. When this was attempted it failed because it set up a
             * cyclic dependency (Pageflow -> PE -> BRM -> WP -> Pageflow).
             */
            return;
        }

        if (ProcessInterfaceUtil.isProcessEngineServiceProcess(subProcess)
                && AsyncExecutionMode.DETACHED.equals(Xpdl2ModelUtil
                        .getOtherAttribute(subFlow,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AsyncExecutionMode()))) {
            /*
             * XPD-7456: Now we do not add requires capability to PE via PFE
             * factory capability. As we hit problems on deploy because pageflow
             * was not propagating requiresCapability for PE. When this was
             * attempted it failed because it set up a cyclic dependency
             * (Pageflow -> PE -> BRM -> WP -> Pageflow).
             */
            return;
        }

        /*
         * If we've got here then (restricted by validation) this must be a
         * synchronous invocation of a pageflow or a PFE service process.
         * 
         * This will either be in same PFE component (1 per XPDL) or a different
         * one. If it's a different one then we need to add a requiresCapability
         * for it.
         * 
         * If it's in same package it will be in same component and hence not
         * need requiresCapability.
         */

        if (activity.getProcess().getPackage().equals(subProcess.getPackage())) {
            /*
             * For pageflow invokes pageflow (or service process in pageflow
             * engine) we don't need requiresCapability if theyr'e in same
             * package.
             */
            return;
        }

        /*
         * Add the requiresCapability for the PFE component for referenced
         * pageflow/PFE service process.
         */

        String subFlowFilePath =
                subProcess.getPackage().eResource().getURI()
                        .toPlatformString(true);
        IFile subFlowXPDL =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(subFlowFilePath));
        IProject subFlowProject = subFlowXPDL.getProject();
        // know calling xpdl file & its project
        PFESpecification pfeImpl = (PFESpecification) implementation;
        String callingXPDLFilePath = pfeImpl.getServiceModel().getModuleName();
        IFile callingXPDL =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(callingXPDLFilePath));
        IProject callingXPDLProject = callingXPDL.getProject();
        // know calling xpdl file & its project
        String componentName =
                PEN2Utils.getComponentName(subFlowXPDL,
                        PFEUtil.PAGE_FLOW_APPEND.substring(1));
        String qualifierReplacer =
                PluginManifestHelper
                        .getQualifierReplacer((PFESpecification) implementation);

        CapabilityIdVersion providedCapabilityId =
                PFEUtil.getProvidedCapabilityId(subFlowProject,
                        qualifierReplacer,
                        componentName);
        String capabilityId = providedCapabilityId.getId();

        List<RequiredCapability> requiredCapabilities =
                requirements.getRequiredCapabilities();

        RequiredCapability subFlowCapability =
                PFEUtil.findCapabilityWithId(requiredCapabilities, capabilityId);

        RequiredCapability factoryCapability =
                createFactoryCapability(requiredCapabilities);

        if (subFlowCapability != null) {
            EList<RequiredCapability> withs = factoryCapability.getWiths();
            RequiredCapability findCapabilityWithId =
                    PFEUtil.findCapabilityWithId(withs, capabilityId);
            if (findCapabilityWithId != null) {
                return;
            }
        }
        // capability does not exists
        RequiredCapability rcForSubProcess =
                ComponentTypeFactory.eINSTANCE.createRequiredCapability();
        rcForSubProcess.setId(capabilityId);
        rcForSubProcess.setType(CapabilityType.CUSTOM);
        if (callingXPDLProject.getName().equals(subFlowProject)) {
            // sub flow xpdl & calling xpdl project are same so we can
            // use all the four parts of OSGI version
            rcForSubProcess.setVersion(providedCapabilityId.getVersion());
        } else {
            // as sub flow project is different to calling project, we
            // can only use first three part of versions as we do not
            // know the timestamp when the sub flow project was
            // generated
            String strVersion = providedCapabilityId.getVersion();
            Version providedCapabilityVersion = new Version(strVersion);
            VersionRange versionRange =
                    OsgiFactory.eINSTANCE.createVersionRange();
            Version subProjectLowerVersion =
                    new Version(providedCapabilityVersion.getMajor(),
                            providedCapabilityVersion.getMinor(),
                            providedCapabilityVersion.getMicro());
            versionRange.setLower(subProjectLowerVersion.toString());
            versionRange.setLowerIncluded(true);
            Version subProjectUpperVersion =
                    new Version(providedCapabilityVersion.getMajor() + 1,
                            providedCapabilityVersion.getMinor(),
                            providedCapabilityVersion.getMicro());
            versionRange.setUpper(subProjectUpperVersion.toString());
            versionRange.setUpperIncluded(false);
            rcForSubProcess.setVersion(versionRange.toString());
        }
        factoryCapability.getWiths().add(rcForSubProcess);
        requiredCapabilities.add(rcForSubProcess);

    }
}
