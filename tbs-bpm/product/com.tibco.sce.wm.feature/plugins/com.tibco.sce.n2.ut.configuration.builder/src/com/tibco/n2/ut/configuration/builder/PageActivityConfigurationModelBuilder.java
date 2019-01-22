/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.configuration.builder;

import java.util.List;

import com.tibco.n2.pageactivity.model.DocumentRoot;
import com.tibco.n2.pageactivity.model.ModeType;
import com.tibco.n2.pageactivity.model.PageActivityDataModelType;
import com.tibco.n2.pageactivity.model.PageActivityFactory;
import com.tibco.n2.pageactivity.model.PageActivityParameterType;
import com.tibco.n2.pageactivity.model.PageActivityParametersType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Class to handle the conversions of Page Activities to EMF Page Activity
 * Models
 * 
 */
public class PageActivityConfigurationModelBuilder {

    /**
     * Generates the appropriate EMF model for a Page Activity
     * 
     * @param activity
     *            Activity to convert
     * @return
     */
    public PageActivityDataModelType convertToPageActivityDataModel(
            Activity activity) {

        PageActivityFactory factory = PageActivityFactory.eINSTANCE;
        DocumentRoot root = factory.createDocumentRoot();
        PageActivityDataModelType pageActivityDataModel =
                factory.createPageActivityDataModelType();

        // Convert parameters
        pageActivityDataModel
                .setPageActivityParameters(convertParameters(activity));

        root.setPageActivityDataModel(pageActivityDataModel);

        return root.getPageActivityDataModel();
    }

    /**
     * Extracts the parameter details from the supplied XPDL Activity and
     * constructs an equivalent representation for our EMF model.
     * 
     * @param activity
     * @return our EMF representation
     */
    private PageActivityParametersType convertParameters(Activity activity) {
        // Create a container for the parameters in our model
        PageActivityParametersType paParams =
                PageActivityFactory.eINSTANCE
                        .createPageActivityParametersType();

        // Get the activity's associated parameters from the XPDL
        Object apsObj =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());

        if (apsObj instanceof AssociatedParameters
                && ((AssociatedParameters) apsObj).getAssociatedParameter()
                        .size() > 0) {
            // For each parameter...
            for (Object apObj : ((AssociatedParameters) apsObj)
                    .getAssociatedParameter()) {
                if (apObj instanceof AssociatedParameter) {
                    // Create a parameter object and populate with
                    // details from the XPDL.
                    // Note: We're not interested in the parameter's description
                    AssociatedParameter ap = (AssociatedParameter) apObj;
                    PageActivityParameterType paParam =
                            PageActivityFactory.eINSTANCE
                                    .createPageActivityParameterType();
                    paParam.setName(ap.getFormalParam());
                    paParam.setMode(convertToPageActivityModeType(ap.getMode()));
                    paParam.setMandatory(ap.isMandatory());
                    // Add it to our list
                    paParams.getPageActivityParameter().add(paParam);
                }
            }
        } else {
            /*
             * Sid XPD-2087: Only use all data implicitly if implicit
             * association has not been disabled.
             */
            if (!ProcessInterfaceUtil.isImplicitAssociationDisabled(activity)) {
                // This is an indication that implicit process
                // data scoping needs to be done

                /*
                 * Sid XPD-2087: This used to get all data for process (ignoring
                 * activity data), I don't think that was correct.
                 */
                List<ProcessRelevantData> allProcessRelevantData =
                        ProcessInterfaceUtil
                                .getAllAvailableRelevantDataForActivity(activity);

                // ProcessInterfaceUtil.getAllProcessRelevantData(xpdlProcess);

                convertPRDListToPageActivityParameters(paParams,
                        allProcessRelevantData);
            }
        }
        return paParams;
    }

    /**
     * Iterates through list of process relevant data list & create
     * UserTaskParameter
     * 
     * @param utParams
     * @param associatedProcessData
     */
    private void convertPRDListToPageActivityParameters(
            PageActivityParametersType utParams,
            List<ProcessRelevantData> associatedProcessData) {
        for (ProcessRelevantData processRelevantData : associatedProcessData) {
            if (processRelevantData instanceof DataField) {
                convertDataFieldToPageActivityParameter(utParams,
                        (DataField) processRelevantData);
            } else if (processRelevantData instanceof FormalParameter) {
                convertFormalParameterToPageActivityParameter(utParams,
                        (FormalParameter) processRelevantData);
            }
        }
    }

    /**
     * Converts DataFields to Page Activity Parameters
     * 
     * @param utParams
     * @param dataFields
     */
    private void convertDataFieldToPageActivityParameter(
            PageActivityParametersType paParams, DataField df) {
        PageActivityParameterType paParam =
                PageActivityFactory.eINSTANCE.createPageActivityParameterType();
        paParam.setName(df.getName());
            
        // DataFields does not have Mode.Hence inout is assumed as default
        // PageActivity can not have data reference on its interface
        paParam.setMode(com.tibco.n2.pageactivity.model.ModeType.INOUT);

        // DataFields does not have 'mandatory' flag.Hence we assume false.
        paParam.setMandatory(false);
        paParams.getPageActivityParameter().add(paParam);

    }

    /**
     * Converts FormalParameters to UserTask Parameters
     * 
     * @param utParams
     * @param formalParameters
     */
    private void convertFormalParameterToPageActivityParameter(
            PageActivityParametersType paParams, FormalParameter param) {
        PageActivityParameterType paParam =
                PageActivityFactory.eINSTANCE.createPageActivityParameterType();
        paParam.setName(param.getName());
        paParam.setMode(convertToPageActivityModeType(param.getMode()));
        paParam.setMandatory(param.isRequired());
        paParams.getPageActivityParameter().add(paParam);

    }

    /**
     * Converts an XPDL representation of parameter mode (in/out/inout) to one
     * for our EMF model.
     * 
     * @param mode
     *            The mode to map
     * @return
     */
    private ModeType convertToPageActivityModeType(
            com.tibco.xpd.xpdl2.ModeType mode) {
        ModeType utMode = null;
        // Each value for one enumeration maps to one in the other.
        switch (mode.getValue()) {
        case com.tibco.xpd.xpdl2.ModeType.IN:
            utMode = com.tibco.n2.pageactivity.model.ModeType.IN;
            break;
        case com.tibco.xpd.xpdl2.ModeType.OUT:
            utMode = com.tibco.n2.pageactivity.model.ModeType.OUT;
            break;
        case com.tibco.xpd.xpdl2.ModeType.INOUT:
            utMode = com.tibco.n2.pageactivity.model.ModeType.INOUT;
            break;
        }

        // The value will have already been verified in UserActivityModeTypeRule
        // to ensure that it was one of the values listed above, so in theory
        // we should never get to this point without a value being set
        return utMode;
    }
}
