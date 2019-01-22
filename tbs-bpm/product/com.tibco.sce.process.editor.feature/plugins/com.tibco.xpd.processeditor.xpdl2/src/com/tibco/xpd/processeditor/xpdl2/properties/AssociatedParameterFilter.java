/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author nwilson
 */
public class AssociatedParameterFilter extends ViewerFilter {

    private AbstractMappingSection abstractMappingSection;

    private MappingDirection mappingDirection;

    /**
     * @param abstractMappingSection
     */
    public AssociatedParameterFilter(
            AbstractMappingSection abstractMappingSection) {
        this.abstractMappingSection = abstractMappingSection;
    }

    public AssociatedParameterFilter(
            AbstractMappingSection abstractMappingSection,
            MappingDirection mappingDirection) {
        this.abstractMappingSection = abstractMappingSection;
        this.mappingDirection = mappingDirection;
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
            if (ok) {
                EObject input = getActivityAssociatedParametersContainer();
                if (input instanceof Activity) {
                    Activity activity = (Activity) input;
                    List<ProcessRelevantData> formals = null;
                    if (mappingDirection == null) {
                        formals =
                                ProcessInterfaceUtil
                                        .getAssociatedProcessRelevantDataForActivity(activity);
                    } else {
                        ModeType modeType = ModeType.IN_LITERAL;
                        if (mappingDirection.equals(MappingDirection.OUT)) {
                            modeType = ModeType.OUT_LITERAL;
                        }
                        formals =
                                ProcessInterfaceUtil
                                        .getAssociatedProcessRelevantDataForActivity(activity,
                                                modeType,
                                                true);
                    }
                    if (formals.size() > 0 && !formals.contains(data)) {
                        ok = false;
                    }
                }
            }
        }
        return ok;
    }

    /**
     * @return The activity to be used for the associated data specification.
     */
    protected EObject getActivityAssociatedParametersContainer() {
        EObject input = abstractMappingSection.getInput();
        return input;
    }

}
