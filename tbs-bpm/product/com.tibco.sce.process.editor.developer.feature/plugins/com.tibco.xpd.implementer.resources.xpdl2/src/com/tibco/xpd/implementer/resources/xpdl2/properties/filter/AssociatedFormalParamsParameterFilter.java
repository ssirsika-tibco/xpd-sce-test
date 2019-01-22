/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author nwilson
 */
public class AssociatedFormalParamsParameterFilter extends ViewerFilter {

    private AbstractMappingSection abstractMappingSection;

    private List<ModeType> supportedModes;

    public AssociatedFormalParamsParameterFilter(
            AbstractMappingSection abstractMappingSection,
            ModeType... modeTypes) {
        this.abstractMappingSection = abstractMappingSection;
        if (modeTypes == null) {
            throw new NullPointerException(
                    Messages.FormalParamsFilter_ModeTypesCannotBeNull_message);
        }
        if (modeTypes.length == 0) {
            throw new IllegalArgumentException(
                    Messages.FormalParamsFilter_ModeTypesCannotBeEmpty_message);
        }
        supportedModes = Arrays.asList(modeTypes);
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
                List<AssociatedParameter> associated =
                        ProcessInterfaceUtil
                                .getActivityAssociatedParameters(activity);
                List<ProcessRelevantData> formals =
                        ProcessInterfaceUtil
                                .getAssociatedProcessRelevantDataForActivity(activity);
                if (!formals.contains(data)) {
                    ok = false;
                } else if (associated.size() == 0) {
                    ok = doSelect(viewer, parentElement, element);
                }
            }
        }
        return ok;
    }

    public boolean doSelect(Viewer viewer, Object parentElement, Object element) {
        boolean result = false;
        if (element instanceof FormalParameter) {
            ModeType mode = ((FormalParameter) element).getMode();
            result = supportedModes.contains(mode);
        }
        return result;
    }

}
