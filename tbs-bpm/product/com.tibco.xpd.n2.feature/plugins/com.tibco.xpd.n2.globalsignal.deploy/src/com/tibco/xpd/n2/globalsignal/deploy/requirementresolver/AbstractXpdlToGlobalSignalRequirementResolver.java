/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.deploy.requirementresolver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.osgi.framework.Version;

import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.bx.composite.core.util.BxCompositeCoreUtil;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Xpdl to Global Signal Requirement Resolver.
 * 
 * @author kthombar
 * @since Mar 19, 2015
 */
public abstract class AbstractXpdlToGlobalSignalRequirementResolver implements
        IRequirementsResolver {

    /**
     * @see com.tibco.bx.composite.core.extensions.IRequirementsResolver#addImplementationRequirements(com.tibco.amf.sca.model.componenttype.Requirements,
     *      com.tibco.amf.sca.model.extensionpoints.Implementation)
     * 
     * @param arg0
     * @param arg1
     */
    @Override
    public void addImplementationRequirements(Requirements requirements,
            Implementation implementation) {

        Package xpdlPackage =
                getProcessPackageForBxImplementation(implementation);

        if (xpdlPackage != null) {
            /*
             * Cache that will store the project to the required capability for
             * the project map.
             */
            Map<IProject, RequiredCapability> capabilityCache =
                    new HashMap<IProject, RequiredCapability>();

            for (Process process : xpdlPackage.getProcesses()) {

                /*
                 * Sub-Class decides what's applicable.
                 */
                if (isApplicableProcess(process)) {

                    for (Activity activity : Xpdl2ModelUtil
                            .getAllActivitiesInProc(process)) {

                        /*
                         * Create and cache the requiredCapability for the given
                         * activity (if required)
                         */
                        cacheCapabilities(activity,
                                requirements,
                                capabilityCache);
                    }
                }
            }

            /*
             * If we have any capabilities then add them to ther DAA
             * requirements model.
             */
            if (capabilityCache.size() > 0) {
                /*
                 * Add the Required Capabilities.
                 */
                addRequiredCapabilities(requirements.getRequiredCapabilities(),
                        capabilityCache.values());
            }
        }
    }

    /**
     * Adds the Required Capabilities and the Withs info to the Factory Required
     * Capability.
     * 
     * @param existingRequiredCapabilities
     *            the list of existing required capabilities.
     * @param requiredCapabilitiesToAdd
     *            the list of capabilities to add.
     */
    private void addRequiredCapabilities(
            EList<RequiredCapability> existingRequiredCapabilities,
            Collection<RequiredCapability> requiredCapabilitiesToAdd) {

        RequiredCapability factoryRc = null;

        for (RequiredCapability requiredCapabilityToAdd : requiredCapabilitiesToAdd) {
            /*
             * Find the required capability in the existing list.
             */
            RequiredCapability findCapabilityWithId =
                    BxCompositeCoreUtil
                            .findCapabilityWithId(existingRequiredCapabilities,
                                    requiredCapabilityToAdd.getId());

            if (findCapabilityWithId == null) {

                if (factoryRc == null) {
                    /*
                     * get the Factory required capability.
                     */
                    factoryRc =
                            getOrCreateFactoryCapability(existingRequiredCapabilities);
                }
                /*
                 * If capability with the passed id is not found in the existing
                 * list then add the RC and add the Withs offset.
                 */
                existingRequiredCapabilities.add(requiredCapabilityToAdd);
                factoryRc.getWiths().add(requiredCapabilityToAdd);
            }
        }
    }

    /**
     * @param existingRequiredCapabilities
     *            the exisiting required capapbilities list.
     * @return the Factory {@link RequiredCapability} for the type of process
     *         for which the RC should be added.
     */
    protected abstract RequiredCapability getOrCreateFactoryCapability(
            EList<RequiredCapability> existingRequiredCapabilities);

    /**
     * @param process
     * @return <code>true</code> if this process is applicable to the subclass
     *         IRequirementsResolver component.
     */
    protected abstract boolean isApplicableProcess(Process process);

    /**
     * Caches the Project to the Required Capability in the passed
     * map'capabilityCache'.
     * 
     * @param activity
     *            the activity in focus
     * @param requirements
     * @param capabilityCache
     *            the cache that stores the project to required capability map.
     */
    private void cacheCapabilities(Activity activity,
            Requirements requirements,
            Map<IProject, RequiredCapability> capabilityCache) {

        if (GlobalSignalUtil.isGlobalSignalEvent(activity)) {
            /*
             * We are only interested in global signals.
             */
            GlobalSignal referencedGlobalSignal =
                    GlobalSignalUtil.getReferencedGlobalSignal(activity);

            if (referencedGlobalSignal != null) {
                /*
                 * get the parent project of the referenced global signal
                 */
                IProject gsdProject =
                        WorkingCopyUtil.getProjectFor(referencedGlobalSignal);

                if (gsdProject != null && gsdProject.isAccessible()
                        && !capabilityCache.containsKey(gsdProject)) {
                    /*
                     * get the project id
                     */
                    String projectId = ProjectUtil.getProjectId(gsdProject);

                    if (projectId != null && !projectId.isEmpty()) {
                        /*
                         * get the project version
                         */
                        String projectVersion =
                                ProjectUtil.getProjectVersion(gsdProject);

                        if (projectVersion != null && !projectVersion.isEmpty()) {

                            Version version = new Version(projectVersion);
                            /*
                             * XPD-7293: The BPM requires capability version
                             * range should be [majorVersion.0.0 ,
                             * majorVersion+1.0.0)' .
                             */
                            VersionRange versionRange =
                                    BxCompositeCoreUtil
                                            .createVersionRange(new Version(
                                                    version.getMajor(), 0, 0));

                            /*
                             * form the capability id, capability id =
                             * <gsdProjectId>.global.signal
                             */
                            String capabilityId =
                                    projectId
                                            + GsdRequirementResolver.GSD_CAPABILITY_ID_SUFFIX;
                            /*
                             * create the required capability.
                             */
                            RequiredCapability requiredCapability =
                                    ComponentTypeFactory.eINSTANCE
                                            .createRequiredCapability();

                            requiredCapability.setId(capabilityId);
                            requiredCapability.setType(CapabilityType.CUSTOM);
                            requiredCapability.setVersion(versionRange
                                    .toString());

                            capabilityCache.put(gsdProject, requiredCapability);
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param implementation
     * @return the xpdl package from the Bx model, else return <code>null</code>
     */
    private Package getProcessPackageForBxImplementation(
            Implementation implementation) {
        if ((implementation instanceof BxServiceImplementation)) {
            BxServiceImplementation bxImplementation =
                    (BxServiceImplementation) implementation;
            ServiceImplementation serviceModel =
                    bxImplementation.getServiceModel();
            if (serviceModel != null) {
                String xpdlFileName = serviceModel.getModuleName();
                if (xpdlFileName != null && xpdlFileName.length() > 0) {
                    IFile xpdlFile =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .getFile(new Path(xpdlFileName));

                    WorkingCopy workingCopy =
                            WorkingCopyUtil.getWorkingCopy(xpdlFile);

                    if (workingCopy instanceof Xpdl2WorkingCopyImpl) {
                        Xpdl2WorkingCopyImpl xpdlWC =
                                (Xpdl2WorkingCopyImpl) workingCopy;
                        Package xpdlPackage = (Package) xpdlWC.getRootElement();

                        return xpdlPackage;
                    }
                }
            }
        }
        return null;
    }
}
