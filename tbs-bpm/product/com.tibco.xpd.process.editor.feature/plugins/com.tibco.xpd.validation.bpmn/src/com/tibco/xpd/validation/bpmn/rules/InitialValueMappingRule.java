/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class InitialValueMappingRule extends ProcessValidationRule {

    /** Mapping must be from an initial value. */
    private static final String INITIAL_REQUIRED =
            "bpmn.initialValueMappingRequired"; //$NON-NLS-1$

    private static final String ALLOWED_VALUE_VALID =
            "bpmn.allowedValueInMappingValid"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Object next : activities) {
            if (next instanceof Activity) {
                List<String> tokens = new ArrayList<String>();
                Activity activity = (Activity) next;
                List<ConceptPath> parameters =
                        SubProcUtil.getSubProcessParameters(activity,
                                MappingDirection.IN);
                List<String> initials = new ArrayList<String>();
                for (ConceptPath parameter : parameters) {
                    Object item = parameter.getItem();
                    if (item instanceof FormalParameter) {
                        FormalParameter formal = (FormalParameter) item;
                        Object other =
                                Xpdl2ModelUtil
                                        .getOtherElement(formal,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_InitialValues());
                        if (other instanceof InitialValues) {
                            InitialValues values = (InitialValues) other;
                            tokens.addAll(values.getValue());
                            if (tokens.size() > 0) {
                                initials.add(formal.getName());
                            }
                        }
                    }
                }
                List<DataMapping> mappings =
                        Xpdl2ModelUtil.getDataMappings(activity,
                                DirectionType.IN_LITERAL);
                for (DataMapping mapping : mappings) {
                    String target = DataMappingUtil.getTarget(mapping);
                    if (initials.contains(target)) {
                        boolean isInitialValueMapping = false;
                        Object other =
                                Xpdl2ModelUtil
                                        .getOtherAttribute(mapping,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_InitialValueMapping());
                        if (other instanceof Boolean) {
                            isInitialValueMapping =
                                    ((Boolean) other).booleanValue();
                        }
                        if (!isInitialValueMapping) {
                            List<String> messages = new ArrayList<String>();
                            Map<String, String> info =
                                    new HashMap<String, String>();
                            info.put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO, target);
                            addIssue(INITIAL_REQUIRED, mapping, messages, info);
                        } else {
                            // check whether the value is one of those in the
                            // token
                            String script = DataMappingUtil.getScript(mapping);
                            if (!tokens.contains(script)) {
                                List<String> messages = new ArrayList<String>();
                                Map<String, String> info =
                                        new HashMap<String, String>();
                                info.put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO, target);
                                addIssue(ALLOWED_VALUE_VALID,
                                        mapping,
                                        messages,
                                        info);
                            }
                        }

                    }
                }
            }
        }
    }

}
