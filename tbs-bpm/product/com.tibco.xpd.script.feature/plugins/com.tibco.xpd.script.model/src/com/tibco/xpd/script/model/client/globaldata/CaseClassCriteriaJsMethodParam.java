/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.script.model.client.globaldata;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.model.client.BaseJsMethodParam;

/**
 * Java script method parameter class that allows construction of parameters
 * specific to be used in cac create/findBy criteria methods.
 * 
 * Since Criteria is a static uml type defined in the uml model, dynamic
 * functions like cac create/findBy need to have the right case class context
 * where this Criteria type is being used which will be useful in validations
 * 
 * @author bharge
 * @since 18 Mar 2014
 */
public class CaseClassCriteriaJsMethodParam extends BaseJsMethodParam {

    private Class caseClassContext;

    /**
     * @param name
     * @param umlType
     * @param type
     * @param isReturnType
     * @param canRepeat
     * @param minOccurence
     * @param maxOccurence
     */
    public CaseClassCriteriaJsMethodParam(String name, Class umlType,
            String type, boolean isReturnType, boolean canRepeat,
            int minOccurence, int maxOccurence, Class caseClassContext) {

        super(name, null, type, isReturnType, canRepeat, minOccurence,
                maxOccurence);
        this.caseClassContext = caseClassContext;
    }

    /**
     * @return the caseClassContext
     */
    public Class getCaseClassContext() {

        return caseClassContext;
    }

}
