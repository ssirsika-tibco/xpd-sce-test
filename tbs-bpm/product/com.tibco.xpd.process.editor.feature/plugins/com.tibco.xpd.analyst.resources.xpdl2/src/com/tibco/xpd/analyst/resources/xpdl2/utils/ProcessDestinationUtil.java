/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Utility for Destination specifics.
 * 
 * @author rsomayaj
 * 
 */
public class ProcessDestinationUtil {

    /**
     * @deprecated This definition is marked as deprecated IMMEDIATELY because
     *             the user MUST only use this when there is no other choice
     *             because there really should NOT be anything iProcess specific
     *             outside of the iProcess feature. We do not want too much
     *             bleed of iProcess specific stuff into underlying features.
     */
    @Deprecated
    public static final String IPROCESS_GLOBAL_DESTINATION =
            "com.tibco.xpd.iprocess.globalDestination"; //$NON-NLS-1$

    /**
     * Validation destination id for the XPDL2WSDL Gneration destination
     * component (the inclusion of which within a global destination causes
     * inclusion of XPDL -> WSDL gneration).
     */
    public static final String XPDL2WSDL_GENERATION_VALIDATION_DESTINATION =
            "com.tibco.xpd.xpdl2wsdl.validation.destination"; //$NON-NLS-1$

    /**
     * @param process
     * @return true if the process should be having WSDL generated for process
     *         API activities according to the destinations set on the process.
     *         If the process has a destination that includes an enabled
     *         component with the
     *         "com.tibco.xpd.xpdl2wsdl.validation.destination" then WSDL should
     *         be being generated.
     */
    public static Boolean shouldGenerateWSDLForProcessDestinations(
            Process process) {
        boolean shouldGenerateWsdl =
                DestinationUtil.isValidationDestinationEnabled(process,
                        XPDL2WSDL_GENERATION_VALIDATION_DESTINATION);
        if (!shouldGenerateWsdl) {
            /*
             * If xpdl2wsdl not enabled by selected destinations, then check the
             * "Enable WSDL Export / Generation" in project or workspace
             * preferences.
             */
            // JA:SCE: To remove.
            // if (isWsdlGenerationPreferenceEnabled(process)) {
            // shouldGenerateWsdl = true;
            // }
        }
        return shouldGenerateWsdl;
    }

    /**
     * @param processInterface
     * 
     * @return true if the processInterface should be having WSDL generated for
     *         processInterface API methods according to the destinations set on
     *         the process. If the processInterface has a destination that
     *         includes an enabled component with the
     *         "com.tibco.xpd.xpdl2wsdl.validation.destination" then WSDL should
     *         be being generated.
     */
    public static Boolean shouldGenerateWSDLForProcessInterfaceDestinations(
            ProcessInterface processInterface) {
        boolean shouldGenerateWsdl =
                DestinationUtil.isValidationDestinationEnabled(processInterface,
                        XPDL2WSDL_GENERATION_VALIDATION_DESTINATION);
        if (!shouldGenerateWsdl) {
            /*
             * If xpdl2wsdl not enabled by selected destinations, then check the
             * "Enable WSDL Export / Generation" in project or workspace
             * preferences.
             */
            // JA:SCE: To remove.
            // if (isWsdlGenerationPreferenceEnabled(processInterface)) {
            // shouldGenerateWsdl = true;
            // }
        }
        return shouldGenerateWsdl;
    }

    // JA:SCE: To remove.
    // /**
    // * @param process
    // * @return true if the project specific or workspace setting for
    // * "Business Object Modeler -> WSDL & XSD Export/Generation -> Enable WSDL
    // Generation"
    // * setting is ON.
    // */
    // public static boolean isWsdlGenerationPreferenceEnabled(EObject eo) {
    // IProject project = WorkingCopyUtil.getProjectFor(eo);
    // if (project != null) {
    // IBOMValidationPreferenceManager preferenceManager =
    // BOMValidatorActivator.getDefault().getPreferenceStore();
    // if (preferenceManager != null) {
    // /*
    // * PLEASE NOTE that the use of the term "validation" in the
    // * IBOMValidationPreferenceManager isn't really valid anymore.
    // * It isn't really about validaiton, it is about enabling
    // * various auto-genration / export capabilities. Validation
    // * enablement should just only be a consequence of wanting to
    // * generate so haveing to validate first!
    // */
    // if (preferenceManager.isValidationEnabled(project,
    // ValidationDestination.WSDL)) {
    // return true;
    // }
    // }
    // }
    // return false;
    // }

    /**
     * @deprecated This definition is marked as deprecated IMMEDIATELY because
     *             the user MUST only use this when there is no other choice
     *             because there really should NOT be anything AMX BPM specific
     *             outside of the N2 feature. We do not want too much bleed of
     *             AMX BPM specific stuff into underlying features.
     */
    @Deprecated
    public static final String BPM_DESTINATION_ID =
            "com.tibco.xpd.n2.core.n2GlobalDestination"; //$NON-NLS-1$

    /**
     * There are some things we are __currently__ forced into conditionalising
     * upon whether the AMX BPM destination is set on a given process.
     * <p>
     * For instance where there is some part of the process-editor-feature
     * contributed UI that is actually AMX BPM specific but it needed to be
     * up-front and center for AMX BPM users.
     * 
     * @param process
     * 
     * @deprecated This method is marked as deprecated IMMEDIATELY because the
     *             user MUST only use this when there is no other choice because
     *             there really should NOT be anything AMX BPM specific outside
     *             of the N2 feature. We do not want too much bleed of AMX BPM
     *             specific stuff into underlying features.
     */
    @Deprecated
    public static boolean isBPMDestinationSelected(Process process) {
        return GlobalDestinationHelper.isGlobalDestinationEnabled(process,
                BPM_DESTINATION_ID);
    }

    /**
     * There are some things we are __currently__ forced into conditionalising
     * upon whether the AMX BPM destination is set on a given project.
     * <p>
     * For instance where there is some part of the process-editor-feature
     * contributed UI that is actually AMX BPM specific but it needed to be
     * up-front and center for AMX BPM users.
     * 
     * @param project
     * 
     * @deprecated This method is marked as deprecated IMMEDIATELY because the
     *             user MUST only use this when there is no other choice because
     *             there really should NOT be anything AMX BPM specific outside
     *             of the N2 feature. We do not want too much bleed of AMX BPM
     *             specific stuff into underlying features.
     */
    @Deprecated
    public static boolean isBPMDestinationSelected(IProject project) {
        return GlobalDestinationUtil.isGlobalDestinationEnabled(project,
                BPM_DESTINATION_ID);
    }

    /**
     * Checks is iProcess destination is selected for given package/process.
     * Current implementation takes care of only Package and process, returns
     * false otherwise.
     * 
     * @param eObject
     * @return
     * @deprecated This method is marked as deprecated IMMEDIATELY because the
     *             user MUST only use this when there is no other choice because
     *             there really should NOT be anything iProcess specific outside
     *             of the iProcess feature. We do not want too much bleed of
     *             iProcess specific stuff into underlying features.
     */
    @Deprecated
    public static boolean isIProcessDestinationSelected(EObject eObject) {

        if (eObject instanceof Package) {
            return GlobalDestinationHelper.isGlobalDestinationEnabled(
                    (Package) eObject,
                    IPROCESS_GLOBAL_DESTINATION);
        } else if (eObject instanceof Process) {
            return GlobalDestinationHelper.isGlobalDestinationEnabled(
                    (Process) eObject,
                    IPROCESS_GLOBAL_DESTINATION);
        }
        return false;

    }

    /**
     * @deprecated This definition is marked as deprecated IMMEDIATELY because
     *             the user MUST only use this when there is no other choice
     *             because there really should NOT be anything AMX DECISIONS
     *             specific outside of the N2 feature. We do not want too much
     *             bleed of AMX DECISIONS specific stuff into underlying
     *             features.
     */
    @Deprecated
    public static final String DECISIONS_DESTINATION_ID =
            "com.tibco.xpd.decisions.globalDestination";//$NON-NLS-1$

    /**
     * There are some things we are __currently__ forced into conditionalising
     * upon whether the AMX DECISIONS destination is set on a given process.
     * 
     * @param process
     * 
     * @deprecated This method is marked as deprecated IMMEDIATELY because the
     *             user MUST only use this when there is no other choice because
     *             there really should NOT be anything AMX DECISIONS specific
     *             outside of the N2 feature. We do not want too much bleed of
     *             AMX DECISIONS specific stuff into underlying features.
     */
    @Deprecated
    public static boolean isDecisionsDestinationSelected(Process process) {
        return GlobalDestinationHelper.isGlobalDestinationEnabled(process,
                DECISIONS_DESTINATION_ID);
    }

    /**
     * There are some things we are __currently__ forced into conditionalising
     * upon whether the AMX DECISIONS destination is set on a given process.
     * 
     * @param project
     * 
     * @deprecated This method is marked as deprecated IMMEDIATELY because the
     *             user MUST only use this when there is no other choice because
     *             there really should NOT be anything AMX DECISIONS specific
     *             outside of the N2 feature. We do not want too much bleed of
     *             AMX DECISIONS specific stuff into underlying features.
     */
    @Deprecated
    public static boolean isDecisionsDestinationSelected(IProject project) {
        return GlobalDestinationUtil.isGlobalDestinationEnabled(project,
                DECISIONS_DESTINATION_ID);
    }

}
