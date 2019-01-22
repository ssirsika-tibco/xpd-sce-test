package com.tibco.xpd.n2.globalsignal.deploy.requirementresolver;

import org.osgi.framework.Version;

import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.ProvidedCapability;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxGlobalSignalImplementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.bx.composite.core.util.BxCompositeCoreUtil;
import com.tibco.bx.core.model.BxGlobalSignalModel;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;

/**
 * Requirement resolver fot GSD Implementation model. Adds the provided
 * capability by the GSD model.
 * 
 * 
 * @author kthombar
 * @since Mar 12, 2015
 */
public class GsdRequirementResolver implements IRequirementsResolver {
    /**
     * The suffix that will be appended to the GSD capability Id.
     */
    public static String GSD_CAPABILITY_ID_SUFFIX = ".global.signal"; //$NON-NLS-1$

    public GsdRequirementResolver() {
        // do nothing here.
    }

    @Override
    public void addImplementationRequirements(Requirements requirements,
            Implementation implementation) {

        if (implementation instanceof BxGlobalSignalImplementation) {

            BxGlobalSignalImplementation bxGlobalSignalImplementation =
                    (BxGlobalSignalImplementation) implementation;

            /*
             * Add the BX required Capability
             */
            addBxRequiredCapability(requirements);
            /*
             * Add the provided capability by gsd project
             */
            addProvidedCapability(bxGlobalSignalImplementation, requirements);

            /*
             * Add the com.tibco.amx.bpm.it.model.product.feature
             * featureDependency
             */
            RequiredFeature modelPF = N2PENamingUtils.getBPMITModelPF();
            requirements.getFeatureDependencies().add(modelPF);

            /*
             * Add the productApplicationDeployment deploymentConstraint
             */
            requirements
                    .getDeploymentConstraints()
                    .add(ComponentTypeFactory.eINSTANCE.createProductApplicationDeployment());

        }
    }

    /**
     * Adds the BX required capability.
     * 
     * @param requirements
     */
    private void addBxRequiredCapability(Requirements requirements) {
        RequiredCapability createBxRequiredCapability =
                BxCompositeCoreUtil.createBxRequiredCapability();
        requirements.getRequiredCapabilities().add(createBxRequiredCapability);
    }

    /**
     * Adds the provided capability.
     * 
     * @param bxGlobalSignalImplementation
     * @param requirements
     */
    private void addProvidedCapability(
            BxGlobalSignalImplementation bxGlobalSignalImplementation,
            Requirements requirements) {
        BxGlobalSignalModel globalSignalModel =
                bxGlobalSignalImplementation.getGlobalSignalModel();

        if (globalSignalModel != null && globalSignalModel.getName() != null
                && globalSignalModel.getVersion() != null) {
            /*
             * form the capability id, capability id =
             * <gsdProjectId>.global.signal
             */
            String providedCapabilityId =
                    globalSignalModel.getName() + GSD_CAPABILITY_ID_SUFFIX;

            ProvidedCapability createProvidedCapability =
                    ComponentTypeFactory.eINSTANCE.createProvidedCapability();

            createProvidedCapability.setId(providedCapabilityId);
            createProvidedCapability.setType(CapabilityType.CUSTOM);

            /*
             * XPD-7293: The GSD provided capability version should be
             * 'majorVersion.0.0' .
             */
            Version version = new Version(globalSignalModel.getVersion());

            Version unqualifiedVersion = new Version(version.getMajor(), 0, 0);

            createProvidedCapability.setVersion(unqualifiedVersion.toString());

            requirements.getProvidedCapabilities()
                    .add(createProvidedCapability);
        }
    }
}
