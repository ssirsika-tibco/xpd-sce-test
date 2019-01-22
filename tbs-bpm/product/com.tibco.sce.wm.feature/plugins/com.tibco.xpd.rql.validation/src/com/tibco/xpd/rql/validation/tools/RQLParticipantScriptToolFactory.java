/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.validation.tools;


import com.tibco.xpd.rql.parser.util.RQLParserUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IPreProcessorFactory;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author Miguel Torres
 * 
 */
public class RQLParticipantScriptToolFactory implements IPreProcessorFactory {

	protected String getScriptGrammar() {
		return RQLParserUtil.RQL_GRAMMAR;
	}

    public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
        IPreProcessor processor = null;
        if(isValidParticipant(input)){
        if (input instanceof Participant) {
            processor = new RQLParticipantScriptTool((Participant) input);
        } else if (input instanceof DataField) {            
                    processor =
                            new RQLParticipantScriptTool((DataField) input);
        }
        }
        return processor;
    }

    public Class<? extends IPreProcessor> getToolClass() {
        return RQLParticipantScriptTool.class;
    }
    
    private boolean isValidParticipant(Object input) {
        if (input instanceof Participant) {
            Participant participant = (Participant) input;
            ParticipantTypeElem participantType =
                    participant.getParticipantType();
            Object objParticipantQ =
                    Xpdl2ModelUtil.getOtherElement(participantType,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ParticipantQuery());

            if (objParticipantQ instanceof Expression) {
                Expression participantQuery = (Expression) objParticipantQ;
                if (participantQuery.getScriptGrammar() != null
                        && participantQuery.getScriptGrammar()
                                .equals(getScriptGrammar())) {
                    return true;
                }
            }
        } else if (input instanceof DataField) {
            DataField dataField = (DataField) input;
            DataType dataType = dataField.getDataType();
            if (dataType instanceof BasicType) {
                BasicType bt = (BasicType) dataType;
                if (bt.getType() != null
                        && bt.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {
                    Object objParticipantQ =
                            Xpdl2ModelUtil
                                    .getOtherElement(bt,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantQuery());

                    if (objParticipantQ instanceof Expression) {
                        Expression participantQuery =
                                (Expression) objParticipantQ;
                        if (participantQuery.getScriptGrammar() != null
                                && participantQuery.getScriptGrammar()
                                        .equals(getScriptGrammar())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
