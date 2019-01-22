/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Item Provider for actual parameters. Can be used as source or target, but you
 * have to say which. This does not alter the content based on the mappings so
 * it is quite straight forward.
 * 
 * @author scrossle TODO Listen for notifications for additions of parameters
 *         (then can provide an action to add them from the mapper).
 */
public class ActivityDataFieldItemProvider extends
        AbstractProcessDataConceptPathProvider {

    private Activity activity;

    public ActivityDataFieldItemProvider(MappingDirection direction) {
        super(direction);
    }

    @Override
    protected ConceptPath[] getConceptPaths(Object inputElement) {
        List<ConceptPath> parameters = new ArrayList<ConceptPath>();
        Activity activity = (Activity) inputElement;
        if (activity != null) {
            this.activity = activity;

            addParameters(parameters,
                    ProcessInterfaceUtil
                            .getAssociatedProcessRelevantDataForActivity(activity));

        }
        ConceptPath[] fields =
                parameters.toArray(new ConceptPath[parameters.size()]);
        Arrays.sort(fields, new ConceptPathComparator());

        return fields;
    }

    /**
     * @param parameters
     * @param formalParameters
     */
    private void addParameters(List<ConceptPath> parameters, List toAdd) {
        for (Object next : toAdd) {
            if (next instanceof ProcessRelevantData) {
                ProcessRelevantData data = (ProcessRelevantData) next;
                Class clss = ConceptUtil.getConceptClass(data);
                ConceptPath path = new ConceptPath(data, clss);
                parameters.add(path);
            } else if (next instanceof ConceptPath) {
                parameters.add((ConceptPath) next);
            }
        }
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (activity != null && umlContentProvider.getActivity() == null) {
            umlContentProvider.setActivity(activity);
        }
        return super.getChildren(parentElement);
    }

}
