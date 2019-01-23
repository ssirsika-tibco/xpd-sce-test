/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.handlers;

import org.eclipse.uml2.uml.Classifier;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.bx.validation.rules.mapping.helpers.BaseMappingsIssuesHelper;
import com.tibco.bx.validation.rules.mapping.helpers.BaseScriptToConceptPathMappingsHelper;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * This class is responsible for handling mappings between script and process
 * data
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class ScriptToProcessMappingsHandler implements
        IntfScriptMappingsIssuesHandler {

    private Activity activity;

    private IScriptRelevantData type;

    private ConceptPath target;

    /**
     * @param activity
     * @param type
     * @param target
     */
    public ScriptToProcessMappingsHandler(Activity activity,
            IScriptRelevantData type, ConceptPath target) {
        super();
        this.activity = activity;
        this.type = type;
        this.target = target;
    }

    /**
     * @see com.tibco.bx.validation.rules.mapping.handlers.IntfScriptMappingsIssuesHandler#getIssues(com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.script.model.client.IScriptRelevantData,
     *      com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath)
     * 
     * @param activity
     * @param type
     * @param target
     * @return
     */
    public WebServiceJavaScriptMappingIssue[] getIssues() {

        Classifier sourceType =
                BaseScriptToConceptPathMappingsHelper
                        .getSourceScriptTypeClassifier(type);
        String sourceTypeStr =
                BaseScriptToConceptPathMappingsHelper.getSourceTypeStr(type,
                        sourceType);

        if (target.getItem() instanceof ProcessRelevantData) {

            ProcessRelevantData targetItem =
                    (ProcessRelevantData) target.getItem();
            BasicType tgtBasicType =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(targetItem);

            if (!BaseScriptToConceptPathMappingsHelper
                    .doesScriptTypeMatch(target, sourceTypeStr, tgtBasicType)) {

                return BaseMappingsIssuesHelper
                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
            }
        }

        return null;
    }

}
