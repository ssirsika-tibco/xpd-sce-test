/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.process.js.model.internal.script;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

import com.tibco.xpd.process.js.model.util.Messages;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ProcessRelevantData;


/**
 * @author mtorres
 */
public class ProcessAdditionalInfoLabelProvider {//implements IAdditionalInfoLabelProvider{
    
    private Object input;

    private IScriptRelevantData dataType;
    
    public ProcessAdditionalInfoLabelProvider(Object input, IScriptRelevantData dataType) {
        this.input = input;
        this.dataType = dataType;
    }
    
    public String getAdditionalInfoLabel(){
        if(input instanceof ProcessRelevantData){
            return getPRDAdditionalInfoLabel((ProcessRelevantData) input);
        } else if (input instanceof Participant){
            return getParticipantAdditionalInfoLabel((Participant) input);
        }
        return Messages.ProcessData;
    }
    
    public String getParticipantAdditionalInfoLabel(Participant participant) {
        if (participant != null) {
            String template =
                    Messages.ProcessAdditionalInfo_processData_template;
            List<String> additionalAttributes = new ArrayList<String>();
            //Add the name
            additionalAttributes.add(participant.getName());
            //Add the data type
            String dataTypeStr = "";//$NON-NLS-1$
            if(dataType != null){
                dataTypeStr = dataType.getType();
            }
            additionalAttributes.add(dataTypeStr);
            //ReadOnly
            String readOnly = Boolean.toString(false);
            additionalAttributes.add(readOnly);
            //Is Array
            String isArray = Boolean.toString(false);
            additionalAttributes.add(isArray);
            //Add the description
            String description = "";//$NON-NLS-1$
            if(participant.getDescription() != null && participant.getDescription().getValue() != null){
                description = participant.getDescription().getValue();
            }
            additionalAttributes.add(description);
            return formatMessage(template, additionalAttributes);
        }
        return null;
    }
    
    public String getPRDAdditionalInfoLabel(ProcessRelevantData prd) {
        if (prd != null) {
            String template =
                    Messages.ProcessAdditionalInfo_processData_template;
            List<String> additionalAttributes = new ArrayList<String>();
            //Add the name
            additionalAttributes.add(prd.getName());
            //Add the data type
            String dataTypeStr = "";//$NON-NLS-1$
            if(dataType != null){
                dataTypeStr = dataType.getType();
            }
            additionalAttributes.add(dataTypeStr);
            //ReadOnly
            String readOnly = Boolean.toString(prd.isReadOnly());
            additionalAttributes.add(readOnly);
            //Is Array
            String isArray = Boolean.toString(prd.isIsArray());
            additionalAttributes.add(isArray);
            //Add the description
            String description = "";//$NON-NLS-1$
            if(prd.getDescription() != null && prd.getDescription().getValue() != null){
                description = prd.getDescription().getValue();
            }
            additionalAttributes.add(description);
            return formatMessage(template, additionalAttributes);
        }
        return null;
    }
    
    protected String formatMessage(String template,
            List<String> additionalAttributes) {
        if (template != null && additionalAttributes != null) {
            try {
                String message =
                        String.format(template, additionalAttributes.toArray());
                return message;
            } catch (IllegalFormatException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    
    protected Object getInput() {
        return input;
    }

    protected IScriptRelevantData getDataType() {
        return dataType;
    }

}
