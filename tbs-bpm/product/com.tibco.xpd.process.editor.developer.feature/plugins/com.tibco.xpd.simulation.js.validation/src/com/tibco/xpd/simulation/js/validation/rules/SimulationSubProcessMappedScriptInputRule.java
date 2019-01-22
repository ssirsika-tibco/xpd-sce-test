/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.js.validation.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.js.validation.rules.AbstractSubProcessMappedScriptInputRule;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;

/**
 * This class only provides the error and warning IDs whereas the actual
 * validation is abstracted out in super classes, which enables reusing the rule
 * for different destinations. Additional validation can be performed in this
 * class, if required, by implementing
 * {@link #performAdditionalValidation(com.tibco.xpd.xpdl2.Expression, com.tibco.xpd.xpdl2.UniqueIdElement)}
 * 
 * 
 * @author agondal
 * @since 17 Sep 2013
 */
public class SimulationSubProcessMappedScriptInputRule extends
        AbstractSubProcessMappedScriptInputRule {

    /** The issue id. */
    private static final String ERROR_ID = "simulation.subProcessTaskScript"; //$NON-NLS-1$

    private static final String WARNING_ID =
            "simulation.warning.subProcessTaskScript"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractSubProcessMappedScriptInputRule#performAdditionalValidation(com.tibco.xpd.xpdl2.Expression,
     *      com.tibco.xpd.xpdl2.UniqueIdElement)
     * 
     * @param expression
     * @param expressionIssueHost
     */
    @Override
    protected void performAdditionalValidation(Expression expression,
            UniqueIdElement expressionIssueHost) {
        List<String> messages = new ArrayList<String>();
        String key = MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
        if (expression.eContainer() instanceof DataMapping) {
            DataMapping dataMapping = (DataMapping) expression.eContainer();
            addIssue(getAdditionalIssueId(),
                    dataMapping,
                    messages,
                    Collections.singletonMap(key,
                            DataMappingUtil.getTarget(dataMapping)));
        }
    }
    // @Override
    // protected void addAdditionalIssue(DataMapping dataMapping,
    // List<ErrorMessage> errorList, List<ErrorMessage> warningList) {
    // List<String> messages = new ArrayList<String>();
    // String key = MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
    // addIssue(getAdditionalIssueId(), dataMapping, messages, Collections
    // .singletonMap(key, DataMappingUtil.getTarget(dataMapping)));
    // }

}
