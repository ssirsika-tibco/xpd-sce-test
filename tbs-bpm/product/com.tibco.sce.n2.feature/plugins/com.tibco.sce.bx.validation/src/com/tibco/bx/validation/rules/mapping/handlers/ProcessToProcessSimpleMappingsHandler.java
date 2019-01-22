/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.handlers;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.bx.validation.rules.mapping.helpers.BaseMappingsIssuesHelper;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * This class is responsible for handling the case between process data to
 * process data. Don't know whether it will reach here, because there can't be
 * Process to Process data types mapped for WS
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class ProcessToProcessSimpleMappingsHandler implements
        IntfSimpleMappingsIssuesHandler {

    private Activity activity;

    private ConceptPath source;

    private ConceptPath target;

    /**
     * @param activity
     * @param source
     * @param target
     */
    public ProcessToProcessSimpleMappingsHandler(Activity activity,
            ConceptPath source, ConceptPath target) {
        super();
        this.activity = activity;
        this.source = source;
        this.target = target;
    }

    /**
     * @see com.tibco.bx.validation.rules.mapping.handlers.IntfSimpleMappingsIssuesHandler#getIssues(com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath,
     *      com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath)
     * 
     * @param activity
     * @param source
     * @param target
     * @return
     */
    public WebServiceJavaScriptMappingIssue[] getIssues() {

        if (source.getItem() instanceof ProcessRelevantData
                && target.getItem() instanceof ProcessRelevantData) {

            ProcessRelevantData sourceItem =
                    (ProcessRelevantData) source.getItem();
            ProcessRelevantData targetItem =
                    (ProcessRelevantData) target.getItem();

            boolean sameBasicType = false;

            BasicType srcBasicType =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(sourceItem);
            if (srcBasicType != null) {
                BasicType tgtBasicType =
                        BasicTypeConverterFactory.INSTANCE
                                .getBasicType(targetItem);

                if (tgtBasicType != null) {
                    if (srcBasicType.getType().equals(tgtBasicType.getType())) {
                        sameBasicType = true;
                    }
                }
            }

            if (!sameBasicType) {
                return BaseMappingsIssuesHelper
                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
            }
        }

        return null;
    }

}
