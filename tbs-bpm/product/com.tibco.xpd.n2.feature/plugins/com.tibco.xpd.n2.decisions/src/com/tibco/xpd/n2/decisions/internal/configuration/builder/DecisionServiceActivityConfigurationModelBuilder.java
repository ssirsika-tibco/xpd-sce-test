/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.n2.decisions.internal.configuration.builder;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.xpdl2bpel.extensions.ExtensionVariable;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.bx.xpdl2bpel.extensions.IActivityMappingDataModelProvider;
import com.tibco.bx.xpdl2bpel.extensions.IMappingDataModel;
import com.tibco.xpd.n2.decisions.N2DecisionsPlugin;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;

/**
 * 
 * This class implements an Eclipse extension point invoked at design time when
 * generating a .bpel file. This allows conversion of default XPDL EMF Model for
 * an Activity to a user defined EMF Model.
 * 
 * @author mtorres
 * 
 */
public class DecisionServiceActivityConfigurationModelBuilder implements
        IActivityConfigurationModelBuilder, IActivityMappingDataModelProvider {

    private DecisionServiceConfigurationModelProvider decisionServiceMappingDataProvider;
    
    public DecisionServiceActivityConfigurationModelBuilder() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.bpel.convertxpdltobpel.extensions.
     * IActivityConfigurationModelBuilder
     * #transformConfigModel(com.tibco.xpd.xpdl2.Activity, java.util.Map)
     */
    public EObject transformConfigModel(Activity activity,
            Map<String, Participant> participantMap) {
        decisionServiceMappingDataProvider = new DecisionServiceConfigurationModelProvider(activity);
        EObject result = null;
        try {            
            result = decisionServiceMappingDataProvider.convertToDecisionTaskDataModel();
        }
        catch (RuntimeException e) {
            // Catch any Runtime exceptions.  The following logger command
            // will print an error in the eclipse log with the one line
            // message displayed and then the full dialog allowing the
            // user to see the full exception
            N2DecisionsPlugin.getDefault().getLogger().error(e, e.getMessage());
            throw e;
        }

        return result;
    }

    @Override
    public List<IMappingDataModel> getInputToServiceMappingData() {
        if(decisionServiceMappingDataProvider != null){
            return decisionServiceMappingDataProvider.getInputToServiceMappingData();
        }
        return null;
    }

    @Override
    public List<IMappingDataModel> getOutputFromServiceMappingData() {
        if(decisionServiceMappingDataProvider != null){
            return decisionServiceMappingDataProvider.getOutputFromServiceMappingData();
        }
        return null;
    }
    
    @Override
    public ExtensionVariable[] getExtensionVariables() {
        if(decisionServiceMappingDataProvider != null){
            return decisionServiceMappingDataProvider.getExtensionVariables();
        }
        return null;
    }


}
