/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client.globaldata;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.model.client.BaseJsMethodParam;
import com.tibco.xpd.script.model.client.IScriptRelevantData;

/**
 * Java script method parameter class that allows construction of parameters
 * specific to be used in cac and case ref js class methods
 * 
 * @author bharge
 * @since 31 Oct 2013
 */
public class CaseJsMethodParam extends BaseJsMethodParam {

    private IScriptRelevantData scriptRelevantData;

    private Class umlClass;

    /**
     * Use this constructor for a method parameter representing a case ref js
     * class type in cac or case ref methods.
     * 
     * @param name
     * @param umlType
     * @param type
     * @param isReturnType
     * @param canRepeat
     * @param minOccurence
     * @param maxOccurence
     * @param scriptRelevantData
     */
    public CaseJsMethodParam(String name, Class umlType, String type,
            boolean isReturnType, boolean canRepeat, int minOccurence,
            int maxOccurence, IScriptRelevantData scriptRelevantData) {

        super(name, umlType, type, isReturnType, canRepeat, minOccurence,
                maxOccurence);
        this.umlClass = umlType;
        this.scriptRelevantData = scriptRelevantData;
    }

    /**
     * @see com.tibco.xpd.script.model.client.BaseJsMethodParam#getScriptRelevantData()
     * 
     * @return
     */
    @Override
    public IScriptRelevantData getScriptRelevantData() {

        return this.scriptRelevantData;
    }

    /**
     * @return the umlClass
     */
    public Class getUmlClass() {

        return umlClass;
    }
}
