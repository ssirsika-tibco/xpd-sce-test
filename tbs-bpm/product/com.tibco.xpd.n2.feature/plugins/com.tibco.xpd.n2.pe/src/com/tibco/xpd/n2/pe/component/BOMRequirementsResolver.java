package com.tibco.xpd.n2.pe.component;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.dependency.osgi.RequiredBundle;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.bds.gd.internal.component.BDSCompositeUtil;
import com.tibco.xpd.n2.cds.utils.CDSDependencyUtils;
import com.tibco.xpd.n2.scriptdescriptor.utils.ScriptDescriptorUtils;

/**
 * Requirements resolver for BOM. Adds required capabilites, bundles and
 * features to the requirements list for items in the requirements descriptor
 * file.
 */
public abstract class BOMRequirementsResolver implements IRequirementsResolver {

    /**
     * @see com.tibco.bx.composite.core.extensions.IRequirementsResolver#addImplementationRequirements(com.tibco.amf.sca.model.componenttype.Requirements,
     *      com.tibco.amf.sca.model.extensionpoints.Implementation)
     * 
     * @param requirements
     *            The requirements to add to.
     * @param implementation
     *            a BxServiceImplementation instance.
     */
    public void addImplementationRequirements(Requirements requirements,
            Implementation implementation) {

        BxServiceImplementation bxImplementation =
                (BxServiceImplementation) implementation;
        ServiceImplementation serviceModel = bxImplementation.getServiceModel();

        if (serviceModel == null) {
            return;
        }
        String moduleName = serviceModel.getModuleName();

        if (moduleName == null || moduleName.equals("")) { //$NON-NLS-1$
            return;
        }

        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(moduleName));
        IProject xpdlProject = xpdlFile.getProject();
        EList<RequiredBundle> rbList = requirements.getRequiredBundles();
        String qualifierReplacer =
                PluginManifestHelper.getQualifierReplacer(bxImplementation);
        String portableXpdlPathString =
                xpdlFile.getFullPath().toPortableString();

        /*
         * XPD-5105: add bds capability as required capability on PE and
         * PageFlow components
         */
        EList<RequiredCapability> includedCapabilities =
                requirements.getRequiredCapabilities();

        List<RequiredCapability> bomReqCapList =
                BDSCompositeUtil.getBDSRequiredCapabilityList(xpdlProject,
                        includedCapabilities);

        includedCapabilities.addAll(bomReqCapList);

        if (!bomReqCapList.isEmpty()) {
            RequiredCapability reqCapFactory =
                    getOrCreateFactoryCapability(includedCapabilities);

            if (reqCapFactory != null) {
                /*
                 * XPD-5105 : add bds capability as withs capability on bx
                 * capability
                 */
                reqCapFactory.getWiths().addAll(bomReqCapList);
            }
        }

        /*
         * add all CDS plug-ins for BOM which are directly & indirectly needed
         * by the xpdl file
         */
        BDSCompositeUtil.addRequiredBundles(xpdlProject,
                requirements.getRequiredBundles(),
                qualifierReplacer);

        /*
         * add required bundles on all Plugin Projects referred from Java
         * Service task
         */
        CDSDependencyUtils.addReferredJavaServiceTaskBundles(xpdlProject,
                portableXpdlPathString,
                rbList,
                qualifierReplacer);
        /* XPD-1345 : add required features */
        /*
         * XPD-5472: admin throws an error message during process-app upgrade
         * wherein Admin tries to remove com.example.glob1.model.bds feature
         * when a new version of process-user-app is deployed.
         * 
         * The workaround(till Platform finds an appropriate solution) is not to
         * put 'feature-dependency', but just have a 'require-bundle' dependency
         * when a component references a feature that is not contained.
         * 
         * This fix might be required in BRMComponentTypeResolver and
         * WPComponentTypeResolver
         */
        /*
         * this one adds the feature dependency with version qualifier for any
         * local boms or java pojo projects
         */
        CDSDependencyUtils
                .addRequiredFeaturesForXpdlFile(portableXpdlPathString,
                        requirements,
                        xpdlProject,
                        qualifierReplacer);
        IFile scriptDescriptorFile =
                ScriptDescriptorUtils
                        .getScriptDescriptorFromStagingArea(xpdlProject);

        if (scriptDescriptorFile != null && scriptDescriptorFile.isAccessible()) {
            IPath scriptFilePath =
                    scriptDescriptorFile.getFullPath().removeFirstSegments(2);
            requirements.getIncludedResources()
                    .add(scriptFilePath.toPortableString());
        }

    }

    /**
     * Gets the RequiredCapability for this resolver, or creates it if it
     * doesn't exist.
     * 
     * @param requiredCapabilities
     *            Existing capabilities.
     * @return The capability for this resolver.
     */
    protected RequiredCapability getOrCreateFactoryCapability(
            List<RequiredCapability> requiredCapabilities) {
        String id = getFactoryCapabilityId();
        RequiredCapability factoryRc =
                findCapabilityWithId(requiredCapabilities, id);
        if (factoryRc == null) {
            factoryRc =
                    ComponentTypeFactory.eINSTANCE.createRequiredCapability();
            factoryRc.setId(id);
            factoryRc.setType(CapabilityType.FACTORY);
            factoryRc.setVersion("1.0.0"); //$NON-NLS-1$
            requiredCapabilities.add(factoryRc);
        }
        return factoryRc;
    }

    /**
     * Locates an existing capability in a list.
     * 
     * @param requiredCapabilities
     *            Existing capabilies.
     * @param capabilityId
     *            The ID of the capability to find.
     * @return The found existing capability, or null if it doesn't exist.
     */
    public RequiredCapability findCapabilityWithId(
            List<RequiredCapability> requiredCapabilities, String capabilityId) {
        RequiredCapability requiredCapability = null;
        if (requiredCapabilities == null || requiredCapabilities.isEmpty()
                || capabilityId == null || capabilityId.trim().length() < 1) {
            return requiredCapability;
        }
        for (RequiredCapability rc : requiredCapabilities) {
            if (capabilityId.equals(rc.getId())) {
                requiredCapability = rc;
                break;
            }
        }
        return requiredCapability;
    }

    /**
     * @return The capability ID for the specific resolver. This is used to find
     *         the associated RequiredCapability.
     */
    protected abstract String getFactoryCapabilityId();

}
