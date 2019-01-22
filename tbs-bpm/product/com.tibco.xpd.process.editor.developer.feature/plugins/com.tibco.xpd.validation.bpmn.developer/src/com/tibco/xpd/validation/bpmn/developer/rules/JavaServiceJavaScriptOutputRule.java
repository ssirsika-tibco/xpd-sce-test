/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingUtil;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class JavaServiceJavaScriptOutputRule extends ActivityValidationRule {

    /** The issue id. */
    public static final String ID = "bpmn.dev.mappingError"; //$NON-NLS-1$

    /** Wrong mode issue id. */
    private static final String WRONG_MODE = "bpmn.dev.mappingModeError"; //$NON-NLS-1$

    private static final String PARAM_MAP_MODE_UNCONFIRM =
            "bpmn.dev.mapModeUndecidedWarning"; //$NON-NLS-1$

    /**
     * @param activity
     *            The activity.
     * @see com.tibco.xpd.validation.xpdl2.rules.DataMappingValidationRule#
     *      validate(com.tibco.xpd.xpdl2.DataMapping)
     */
    @Override
    public void validate(Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskService service = task.getTaskService();
            if (service != null) {
                String type =
                        (String) Xpdl2ModelUtil.getOtherAttribute(service,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementationType());
                if ("Java".equals(type)) { //$NON-NLS-1$
                    Message message = service.getMessageOut();
                    if (message != null) {
                        checkDataMappings(activity, message.getDataMappings());
                    }
                }
            }
        }
    }

    /**
     * @param activity
     *            The activity containing the assignments.
     * @param mappings
     *            The data mappings
     */
    private void checkDataMappings(Activity activity, List<?> mappings) {
        for (Object next : mappings) {
            DataMapping mapping = (DataMapping) next;
            validateMappingDirection(activity, mapping);
            String target = DataMappingUtil.getTarget(mapping);
            String script = DataMappingUtil.getScript(mapping);
            String grammar = DataMappingUtil.getGrammar(mapping);
            if (target != null && script != null && grammar != null) {
                if (target.startsWith("process.")) { //$NON-NLS-1$
                    target = target.substring(8);
                }
                ScriptMappingCompositorFactory factory =
                        ScriptMappingCompositorFactory
                                .getCompositorFactory(grammar,
                                        DirectionType.OUT_LITERAL,
                                        JavaServiceMappingUtil.SCRIPT_CONTEXT);
                if (factory != null) {
                    ScriptMappingCompositor compositor =
                            factory.getCompositor(activity, target, script);
                    if (compositor instanceof SingleMappingCompositor) {
                        SingleMappingCompositor scriptMapping =
                                (SingleMappingCompositor) compositor;
                        try {
                            Collection<String> parameters =
                                    scriptMapping.getSourceItemNames();
                            if (parameters.size() != 1) {
                                throw new UncheckedTypeException();
                            }
                        } catch (UncheckedTypeException e) {
                            List<String> messages = new ArrayList<String>();
                            String key =
                                    MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                            addIssue(BpmnTypeUtil.CANNOT_CHECK_TYPES,
                                    mapping,
                                    messages,
                                    Collections.singletonMap(key, target));
                        }
                    }
                }
            }
        }
    }

    /**
     * @param activity
     *            The activity..
     * @param mapping
     *            The mapping.
     */
    private void validateMappingDirection(Activity activity, DataMapping mapping) {
        if (mapping != null && mapping.getActual() != null
                && !DataMappingUtil.isScripted(mapping)) {
            boolean dontWarn = false;
            String target = DataMappingUtil.getTarget(mapping);
            if (target != null) {
                if (target.startsWith("process.")) { //$NON-NLS-1$
                    target = target.substring(8);
                }

                /*
                 * When checking mode of parameter need to check the root of the
                 * path (otherwise we will always complain when child of complex
                 * is mapped).
                 */
                String rootName = target;
                ConceptPath conceptPath =
                        ConceptUtil.resolveConceptPath(activity, target);
                if (conceptPath != null) {
                    ConceptPath rootPath = conceptPath.getRoot();
                    if (rootPath != null) {
                        rootName = rootPath.getPath();
                    }
                }

                List<AssociatedParameter> associatedParameters =
                        ProcessInterfaceUtil
                                .getActivityAssociatedParameters(activity);
                if (associatedParameters != null
                        && !associatedParameters.isEmpty()) {
                    for (AssociatedParameter associatedParameter : associatedParameters) {
                        if (associatedParameter.getFormalParam()
                                .equals(rootName)) {
                            dontWarn = true;

                            /*
                             * SID XPD-225: don't check for read only mappings
                             * now that this is done elsewhere in other mapping
                             * rules.
                             */
                            // ProcessRelevantData
                            // processRelevantDataFromAssociatedParam =
                            // ProcessInterfaceUtil
                            // .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                            //
                            // if (processRelevantDataFromAssociatedParam
                            // .isReadOnly()) {
                            // List<String> messages = new ArrayList<String>();
                            // messages.add(target);
                            // String key =
                            // MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                            // addIssue(READONLY_DISSALLOWED,
                            // mapping,
                            // messages,
                            // Collections.singletonMap(key, target));
                            // }
                            if (ModeType.IN_LITERAL == ProcessInterfaceUtil
                                    .getAssocParamModeType(associatedParameter)) {
                                List<String> messages = new ArrayList<String>();
                                messages.add(ModeType.IN_LITERAL.getName());
                                messages.add(target);
                                String key =
                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                addIssue(WRONG_MODE,
                                        mapping,
                                        messages,
                                        Collections.singletonMap(key, target));
                                return;
                            }
                        }
                    }
                    if (!dontWarn) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(activity.getName());
                        String key =
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                        addIssue(PARAM_MAP_MODE_UNCONFIRM,
                                mapping,
                                messages,
                                Collections.singletonMap(key, target));
                    }
                } else {
                    List<ProcessRelevantData> associatedPRD =
                            ProcessInterfaceUtil
                                    .getAssociatedProcessRelevantDataForActivity(activity);
                    for (ProcessRelevantData processRelevantData : associatedPRD) {
                        if (processRelevantData.getName().equals(rootName)) {

                            dontWarn = true;

                            /*
                             * SID XPD-225: don't check for read only mappings
                             * now that this is done elsewhere in other mapping
                             * rules.
                             */
                            // if (processRelevantData.isReadOnly()) {
                            // List<String> messages = new ArrayList<String>();
                            // messages.add(target);
                            // String key =
                            // MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                            // addIssue(READONLY_DISSALLOWED,
                            // mapping,
                            // messages,
                            // Collections.singletonMap(key, target));
                            // }
                            if (processRelevantData instanceof FormalParameter) {
                                FormalParameter formalParameter =
                                        (FormalParameter) processRelevantData;
                                if (formalParameter.getMode().getName()
                                        .equals(ModeType.IN_LITERAL.getName())) {
                                    List<String> messages =
                                            new ArrayList<String>();
                                    messages.add(ModeType.IN_LITERAL.getName());
                                    messages.add(target);
                                    String key =
                                            MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                                    addIssue(WRONG_MODE,
                                            mapping,
                                            messages,
                                            Collections.singletonMap(key,
                                                    target));
                                    return;
                                }
                            }
                        }
                    }
                    if (!dontWarn) {
                        List<String> messages = new ArrayList<String>();
                        messages.add(activity.getName());
                        String key =
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                        addIssue(PARAM_MAP_MODE_UNCONFIRM,
                                mapping,
                                messages,
                                Collections.singletonMap(key, target));
                    }
                }
            }
        }
    }
}
