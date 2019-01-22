/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldContributor;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author nwilson
 */
public class ContributedFieldFilter extends ViewerFilter {

    private AbstractMappingSection abstractMappingSection;

    public ContributedFieldFilter(AbstractMappingSection abstractMappingSection) {
        this.abstractMappingSection = abstractMappingSection;
    }

    /**
     * @param viewer
     * @param parentElement
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean ok = true;
        if (element instanceof ConceptPath) {
            element = ((ConceptPath) element).getItem();
        }
        if (element instanceof ProcessRelevantData) {
            ProcessRelevantData data = (ProcessRelevantData) element;
            EObject input = abstractMappingSection.getInput();
            if (input instanceof Activity) {
                Activity activity = (Activity) input;
                Process process = activity.getProcess();
                if (process != null) {
                    String context = DataFieldContributor.CONTEXT_ALL_POSSIBLE;
                    if (ProcessScriptUtil.isBwService(activity)) {
                        context = DataFieldContributor.CONTEXT_BW_SERVICE_TASK;
                    }
                    Collection<ConceptPath> parameters =
                            ConceptUtil.getContributedFields(process, context);
                    for (ConceptPath parameter : parameters) {
                        String path = parameter.getPath();
                        if (path != null && path.equals(data.getName())) {
                            ok = false;
                            break;
                        }
                    }
                }
            }
        }
        return ok;
    }

}
