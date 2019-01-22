/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.Arrays;
import java.util.Collection;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldContributor;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author nwilson
 */
public class ActivityDataFieldItemProviderWithContributions extends
        ActivityDataFieldItemProvider {
    
    public ActivityDataFieldItemProviderWithContributions(
            MappingDirection mappingDirection) {
        super(mappingDirection);
    }

    @Override
    public Object[] getElements(Object inputElement) {
        ConceptPath[] fields = getConceptPaths(inputElement);
        Activity activity = (Activity) inputElement;
        if (activity != null) {
            Process process = activity.getProcess();
            if (process != null) {
                Collection<ConceptPath> parameters = ConceptUtil
                        .getContributedFields(process, DataFieldContributor.CONTEXT_WEB_SERVICE_TASK);
                int size = parameters.size();
                if (size > 0) {
                    ConceptPath[] newFields = new ConceptPath[fields.length + size];
                    System.arraycopy(fields, 0, newFields, 0, fields.length);
                    System.arraycopy(parameters.toArray(), 0, newFields,
                            fields.length, size);
                    fields = newFields;
                }
            }
        }
        Arrays.sort(fields, new ConceptPathComparator());
        return fields;
    }

}
