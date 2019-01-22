/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;

/**
 * 
 * <p>
 * <i>Created: 27 Mar 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class JsSubProcessMappedScriptInputRule extends
        AbstractSubProcessMappedScriptInputRule {

    /** The issue id. */
    private static final String ERROR_ID = "js.validateScriptTask"; //$NON-NLS-1$

    private static final String WARNING_ID = "js.warning.validateScriptTask"; //$NON-NLS-1$

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

}
