/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchBpmnErrorMapperSection;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.internal.IScriptGrammarFilter;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarDestinationBindingElement.ScriptGrammarReferenceElement;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarElement;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class ScriptGrammarFactory {

    public static final String JAVASCRIPT = "JavaScript"; //$NON-NLS-1$

    public static final String RQL = "RQL"; //$NON-NLS-1$

    public static final String XPATH = "XPath"; //$NON-NLS-1$

    public static final String TRANSFORM_GRAMMAR = "Transform"; //$NON-NLS-1$

    public static final String DATAMAPPER = "DataMapper"; //$NON-NLS-1$

    public static final String MAPPER_GRAMMAR_PROVIDER_ACTIVITY_TYPE_ATTRIBUTE =
            "activityType"; //$NON-NLS-1$

    public static final String MAPPER_GRAMMAR_PROVIDER_GRAMMAR_RESOLVER_ATTRIBUTE =
            "grammarTypeResolver"; //$NON-NLS-1$

    /**
     * @return A list of available certified grammars.
     * @throws CoreException
     */
    public static List<ScriptGrammarElement> getGrammars(String context)
            throws CoreException {
        //
        // Note that the extension point was originally in processwidget but has
        // been
        // deprecated and moved here to processeditor. We still support
        // contributions to
        // the deprecated processwidget extension point so must handle both.
        // TODO - Ravi - WHY?
        List<String> supported = getSupportedGrammars(null, context);

        List<ScriptGrammarElement> grammars = getConfiguredGrammars(supported);

        return grammars;
    }

    /**
     * @param activity
     * @param context
     * @return
     * @throws CoreException
     */
    public static List<ScriptGrammarElement> getEnabledGrammars(
            Activity activity, String context) throws CoreException {
        Process process = Xpdl2ModelUtil.getProcess(activity);

        Collection<String> enabledDestinations =
                DestinationUtil.getEnabledValidationDestinations(process);

        List<String> supported =
                getSupportedGrammars(activity, context, enabledDestinations);

        supported = filterGrammars(activity, supported);

        List<ScriptGrammarElement> grammars = getConfiguredGrammars(supported);

        return grammars;
    }

    /**
     * @param activity
     * @param supported
     * @return
     * @throws CoreException
     */
    private static List<String> filterGrammars(Activity activity,
            List<String> supported) throws CoreException {
        List<String> filtered = new ArrayList<String>(supported);

        Collection<IScriptGrammarFilter> scriptGrammarFilters =
                ScriptGrammarContributionsUtil.INSTANCE
                        .getScriptGrammarFilters(supported);

        for (IScriptGrammarFilter filter : scriptGrammarFilters) {
            for (String grammar : supported) {
                if (!filter.select(activity, grammar)) {
                    filtered.remove(grammar);
                }
            }
        }
        return filtered;
    }

    /**
     * Get script grammar elements for the provided script grammars.
     * 
     * @param supported
     * @return
     */
    private static List<ScriptGrammarElement> getConfiguredGrammars(
            List<String> supported) {

        Map<String, ScriptGrammarElement> contributedScriptGrammars =
                ScriptGrammarContributionsUtil.INSTANCE
                        .getContributedScriptGrammars();

        List<ScriptGrammarElement> scriptGrammarElements =
                new ArrayList<ScriptGrammarElement>();
        for (String supportedGrammar : supported) {
            scriptGrammarElements.add(contributedScriptGrammars
                    .get(supportedGrammar));
        }
        return scriptGrammarElements;
    }

    /**
     * @param context
     * @param supported
     * @throws CoreException
     */
    private static List<String> getSupportedGrammars(Activity activity,
            String context) throws CoreException {
        return getSupportedGrammars(activity, context, Collections.EMPTY_LIST);
    }

    /**
     * For a given list of destinations and context, provide the list of
     * grammars
     * 
     * @param context
     * @param supported
     * @throws CoreException
     */
    private static List<String> getSupportedGrammars(Activity activity,
            String context, Collection<String> destinations)
            throws CoreException {
        // TODO - Ravi - Dont think this is correct - Need to find the
        // destinations on the activity and provide on those non-repeating
        // grammars
        if (destinations.isEmpty()) {
            // destinations =
            // DestinationUtil.getEnabledValidationDestinations(activity);

            destinations =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getContributedDestinations();
        }
        List<String> supportedGrammars = new ArrayList<String>();
        for (String destination : destinations) {
            Set<ScriptGrammarReferenceElement> grammarForGivenContextAndDestination =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getGrammarForGivenContextAndDestination(destination,
                                    context);
            for (ScriptGrammarReferenceElement grammarElement : grammarForGivenContextAndDestination) {
                IScriptGrammarFilter scriptGrammarFilter =
                        grammarElement.getScriptGrammarFilter();
                if (scriptGrammarFilter != null) {
                    boolean b =
                            scriptGrammarFilter.select(activity,
                                    grammarElement.getGrammar());
                    if (b
                            && !supportedGrammars.contains(grammarElement
                                    .getGrammar())) {
                        supportedGrammars.add(grammarElement.getGrammar());
                    }
                }

                // TODO - Ravi - not sure whether to return as supported grammar
                // if there are no filters present

                else if (!supportedGrammars.contains(grammarElement
                        .getGrammar())) {
                    supportedGrammars.add(grammarElement.getGrammar());
                }

            }
        }
        return supportedGrammars;
    }

    /**
     * @param activity
     *            The activity. TODO: This method is hardcoded, the default
     *            Script grammar should be contributed
     * @return The default script grammar for the activity.
     */
    public static String getDefaultScriptGrammar(Activity activity) {
        String scriptContext = null;

        String grammar = JAVASCRIPT;
        if (activity != null) {

            boolean isReply = ReplyActivityUtil.isReplyActivity(activity);
            boolean isGenerated = false;

            if (isReply) {
                Activity requestAct =
                        ReplyActivityUtil
                                .getRequestActivityForReplyActivity(activity);
                if (requestAct != null
                        && Xpdl2ModelUtil
                                .isGeneratedRequestActivity(requestAct)) {
                    isGenerated = true;
                }
            } else {
                if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                    isGenerated = true;
                }
            }

            /*
             * If this is a reply activity or throw fault error event then we
             * should default to the script grammar of the incoming request
             * activity.
             */
            Activity deferToRequestActivity = null;
            if (isReply) {
                deferToRequestActivity =
                        ReplyActivityUtil
                                .getRequestActivityForReplyActivity(activity);
            } else if (ThrowErrorEventUtil
                    .isThrowFaultMessageErrorEvent(activity)) {
                deferToRequestActivity =
                        ThrowErrorEventUtil.getRequestActivity(activity);

                if (Xpdl2ModelUtil
                        .isGeneratedRequestActivity(deferToRequestActivity)) {
                    isGenerated = true;
                }
            }

            if (deferToRequestActivity != null
            /* just in case cos we're recursing! */
            && deferToRequestActivity != activity) {
                /*
                 * DataMapping DirectionType is always OUT (from service into
                 * process) for reuqest activities, so get the grammar according
                 * to that
                 */
                String requestGrammar =
                        getScriptGrammar(deferToRequestActivity,
                                DirectionType.OUT_LITERAL);
                if (requestGrammar == null) {
                    requestGrammar =
                            getDefaultScriptGrammar(deferToRequestActivity);
                }

                return requestGrammar;
            }

            /*
             * Not a reply activity / throw fault; for request activity so carry
             * on. (or at least not configured as one yet.
             */
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;

                //
                // Service Task.
                //
                if (task.getTaskService() != null) {
                    TaskService service = task.getTaskService();

                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(service,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (TaskImplementationTypeDefinitions.WEB_SERVICE
                            .equals(type)
                            || TaskImplementationTypeDefinitions.WEB_SERVICE_V2_0
                                    .equals(type)) {
                        grammar = XPATH;
                        scriptContext =
                                ProcessScriptContextConstants.WEB_SERVICE_TASK;

                    } else if (TaskImplementationTypeDefinitions.BW_SERVICE
                            .equals(type)) {
                        grammar = XPATH;
                        scriptContext =
                                ProcessScriptContextConstants.BW_SERVICE_TASK;
                    } else if (TaskImplementationTypeDefinitions.JAVA_SERVICE
                            .equals(type)) {
                        grammar = JAVASCRIPT;
                        scriptContext =
                                ProcessScriptContextConstants.JAVA_SERVICE_TASK;
                    }

                }
                //
                // Receive Task
                //
                else if (task.getTaskReceive() != null) {
                    TaskReceive receive = task.getTaskReceive();

                    grammar = XPATH;

                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(receive,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (TaskImplementationTypeDefinitions.BW_SERVICE
                            .equals(type)) {
                        scriptContext =
                                ProcessScriptContextConstants.BW_SERVICE_TASK;
                    } else {
                        scriptContext =
                                ProcessScriptContextConstants.WEB_SERVICE_TASK;
                    }

                }
                //
                // Send Task
                //
                else if (task.getTaskSend() != null) {
                    TaskSend send = task.getTaskSend();
                    grammar = XPATH;

                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(send,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (TaskImplementationTypeDefinitions.BW_SERVICE
                            .equals(type)) {
                        scriptContext =
                                ProcessScriptContextConstants.BW_SERVICE_TASK;
                    } else {
                        scriptContext =
                                ProcessScriptContextConstants.WEB_SERVICE_TASK;
                    }

                }
                //
                // Script Task
                //
                else if (task.getTaskScript() != null) {
                    TaskScript script = task.getTaskScript();

                    if (script.getScript() != null
                            && script.getScript().getScriptGrammar() != null
                            && script.getScript().getScriptGrammar()
                                    .equals(TRANSFORM_GRAMMAR)) {
                        grammar = XPATH;
                        scriptContext =
                                ProcessScriptContextConstants.TRANSFORM_SCRIPT_TASK;

                    } else {
                        grammar = JAVASCRIPT;
                        scriptContext =
                                ProcessScriptContextConstants.SCRIPT_TASK;
                    }
                }
                //
                // User Task
                //
                else if (task.getTaskUser() != null
                        || task.getTaskManual() != null) {
                    grammar = JAVASCRIPT;
                    scriptContext =
                            ProcessScriptContextConstants.OPEN_USER_TASK;
                }

            }
            //
            // Sub-Process Task
            //
            else if (implementation instanceof SubFlow) {
                grammar = DATAMAPPER;
                scriptContext = ProcessScriptContextConstants.SUB_PROCESS_TASK;
            }
            //
            // Events
            //
            else if (activity.getEvent() != null) {
                Event event = activity.getEvent();

                //
                // SID MR 40446 -Undid Ravi's change that forced return of XPATH
                // grammar.
                // This made a whole bunch of stuff in the mapper fail because
                // "catch all", "catch by name" and "catch specific embedded /
                // sub-process error" use the StandardMappingUtil class (because
                // either unspecific or are mappings in / out of sub-process) -
                // the standard mapping util cannot handle XPATH - it assumes
                // and defaults to JavaScript.
                //
                // So to improve on both of these aspects we can be a little
                // more fussy about the error events.
                //
                // If the Error event catches all or unspecific by name or
                // sub-proc error then use javascript. Else use XPath.
                if (event instanceof IntermediateEvent
                        && TriggerType.ERROR_LITERAL
                                .equals(((IntermediateEvent) event)
                                        .getTrigger())) {
                    // The following method will return NON-Null if the error is
                    // NOT catch all / catch by name / catch sub-process error.
                    Object catchTypeOrSubProcEventThrower =
                            CatchBpmnErrorMapperSection
                                    .getCatchTypeOrSpecificErrorEndEvent(activity);
                    if (catchTypeOrSubProcEventThrower == null) {
                        // Its not one of the internally handled types,
                        // probably then it is a service call of some kind so
                        // default to standard xpath.
                        grammar = XPATH;
                        scriptContext =
                                ProcessScriptContextConstants.WEB_SERVICE_TASK;

                    } else {
                        // It's an intra-process or unspecific catch error type
                        // - use the same context as sub-process invoke.
                        grammar = DATAMAPPER;
                        scriptContext =
                                ProcessScriptContextConstants.SUB_PROCESS_TASK;
                    }

                } else {
                    //
                    // Non - error events.
                    //
                    grammar = XPATH;

                    if (event.getEventTriggerTypeNode() instanceof TriggerTimer) {
                        scriptContext =
                                ProcessScriptContextConstants.TIMER_EVENT;

                    } else if (event.getEventTriggerTypeNode() instanceof TriggerResultMessage) {
                        String type =
                                (String) Xpdl2ModelUtil
                                        .getOtherAttribute(event,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType());
                        if (TaskImplementationTypeDefinitions.BW_SERVICE
                                .equals(type)) {
                            scriptContext =
                                    ProcessScriptContextConstants.BW_SERVICE_TASK;
                        } else {
                            scriptContext =
                                    ProcessScriptContextConstants.WEB_SERVICE_TASK;
                        }
                    } else if (event instanceof EndEvent
                            && ResultType.ERROR_LITERAL
                                    .equals(((EndEvent) event).getResult())) {
                        if (ThrowErrorEventUtil
                                .isThrowFaultMessageErrorEvent(activity)) {
                            /*
                             * When end error event is configured to throw a
                             * fault message then use the web service script
                             * context.
                             */
                            scriptContext =
                                    ProcessScriptContextConstants.WEB_SERVICE_TASK;
                        } else {
                            grammar = JAVASCRIPT;
                            scriptContext =
                                    ProcessScriptContextConstants.SUB_PROCESS_TASK;
                        }

                    } else if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                            .equals(EventObjectUtil
                                    .getEventTriggerType(activity))
                            && isGlobalSignalEvent(activity)) {
                        grammar = DATAMAPPER;
                        scriptContext =
                                ProcessScriptContextConstants.GLOBAL_CATCH_SIGNAL_EVENTMAPPING;

                    } else if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                            .equals(EventObjectUtil
                                    .getEventTriggerType(activity))
                            && EventObjectUtil.isLocalSignalEvent(activity)) {
                        grammar = DATAMAPPER;
                        scriptContext = ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING;

                    } else if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                            .equals(EventObjectUtil
                                    .getEventTriggerType(activity))) {
                        grammar = JAVASCRIPT;
                        scriptContext =
                                ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING;

                    } else if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                            .equals(EventObjectUtil
                                    .getEventTriggerType(activity))
                            && isGlobalSignalEvent(activity)) {
                        grammar = DATAMAPPER;
                        scriptContext =
                                ProcessScriptContextConstants.GLOBAL_THROW_SIGNAL_EVENTMAPPING;
                    }
                }
            }

            /*
             * XPD-8117: Saket: Default grammar for generated activities must be
             * JavaScript.
             */
            if (isGenerated) {
                return JAVASCRIPT;
            }

        }

        if (scriptContext != null && scriptContext.length() > 0) {
            /*
             * Having come up with the hard-coded constant grammar, if we
             * managed to guess a script context then look to see if there is a
             * default grammar defined for the script type.
             */
            Process process = Xpdl2ModelUtil.getProcess(activity);

            if (process != null) {
                Collection<String> enabledDestinations =
                        DestinationUtil
                                .getEnabledValidationDestinations(process);

                /*
                 * Get ScriptGrammarDestinationBinding contributions enabled for
                 * this activity's process.
                 * 
                 * Find one that has is bound to the given script context and
                 * set to default and use that as an alternate to the hard coded
                 * one.
                 */
                for (String destination : enabledDestinations) {
                    Set<ScriptGrammarReferenceElement> grammars =
                            ScriptGrammarContributionsUtil.INSTANCE
                                    .getGrammarForGivenContextAndDestination(destination,
                                            scriptContext);

                    for (ScriptGrammarReferenceElement grammarRefElement : grammars) {
                        if (grammarRefElement.isDefault()) {
                            grammar = grammarRefElement.getGrammar();
                            break;
                        }
                    }
                }

            }

        }

        return grammar;
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the passed activity is an is an Global
     *         signal event.
     */
    private static boolean isGlobalSignalEvent(Activity activity) {
        Event event = activity.getEvent();
        if (event != null) {
            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {
                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;

                Object otherAttribute =
                        Xpdl2ModelUtil.getOtherAttribute(signal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalType());

                if (otherAttribute instanceof SignalType) {
                    SignalType sigType = (SignalType) otherAttribute;
                    return SignalType.GLOBAL.equals(sigType) ? true : false;
                }
            }
        }
        return false;
    }

    /**
     * @param activity
     *            The activity.
     * @param dir
     *            The mapping direction.
     * @return The grammar if a single grammar found, null if none or
     *         duplicates.
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties
     *      .AbstractEditableMappingSection#getGrammar()
     */
    public static String getScriptGrammar(Activity activity, DirectionType dir) {

        String grammar = null;
        if (activity != null) {

            /*
             * Get explicitly selected grammar.
             */
            grammar = getExplicitlySelectedGrammar(activity, dir, grammar);

            if (grammar != null) {
                return grammar;
            }

            List<?> others =
                    Xpdl2ModelUtil.getOtherElementList(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            for (Object other : others) {
                if (other instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) other;
                    if (dir.equals(information.getDirection())) {
                        Expression expression = information.getExpression();
                        if (expression != null) {
                            String current = expression.getScriptGrammar();
                            if (current != null) {
                                if (grammar == null) {
                                    grammar = current;
                                } else {
                                    if (!grammar.equals(current)) {
                                        return null;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            String mappingsGrammar =
                    getGrammarFromMappings(Xpdl2ModelUtil.getDataMappings(activity,
                            dir),
                            dir,
                            grammar);
            if (mappingsGrammar != null) {
                grammar = mappingsGrammar;
            }

        }
        return grammar;
    }

    /**
     * Get explicitly selected grammar if any, return <code>null</code>
     * otherwise.
     * 
     * @param activity
     * @param dir
     * @param grammar
     * 
     * @return Explicitly selected grammar if any, return <code>null</code>
     *         otherwise.
     */
    private static String getExplicitlySelectedGrammar(Activity activity,
            DirectionType dir, String grammar) {

        Class<?> actClass = null;

        if (activity.getImplementation() != null) {
            actClass = activity.getImplementation().getClass();
        } else if (activity.getEvent() != null) {
            actClass = activity.getEvent().getClass();
        }

        if (actClass != null) {

            IExtensionPoint extensionPoint =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                                    "mapperGrammarProvider"); //$NON-NLS-1$

            IConfigurationElement[] configs =
                    extensionPoint.getConfigurationElements();

            for (IConfigurationElement config : configs) {

                try {

                    String activityType =
                            config.getAttribute(MAPPER_GRAMMAR_PROVIDER_ACTIVITY_TYPE_ATTRIBUTE);

                    Class<?> cls =
                            Class.forName(activityType, false, activity
                                    .getClass().getClassLoader());

                    if (cls.isAssignableFrom(actClass)) {

                        Object obj =
                                config.createExecutableExtension(MAPPER_GRAMMAR_PROVIDER_GRAMMAR_RESOLVER_ATTRIBUTE);

                        if (obj instanceof IMapperGrammarTypeResolver) {

                            IMapperGrammarTypeResolver mappingGrammarResolver =
                                    (IMapperGrammarTypeResolver) obj;

                            grammar =
                                    mappingGrammarResolver
                                            .getMapperGrammarType(activity, dir);
                        }
                    }
                } catch (CoreException e) {
                    Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
                } catch (ClassNotFoundException e) {
                    Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
                }
            }
        }
        return grammar;
    }

    /**
     * Wrapper for <code>getScriptGrammar(activity, direction)</code> and if
     * that's null, then we return
     * <code>getDefaultScriptGrammar(activity)</code>.
     * 
     * @param activity
     * @param direction
     * 
     * @return <code>getScriptGrammar(activity, direction)</code> and if that's
     *         null, then we return
     *         <code>getDefaultScriptGrammar(activity)</code>.
     */
    public static String getGrammarToUse(Activity activity,
            DirectionType direction) {

        String currentScriptGrammar = getScriptGrammar(activity, direction);
        if (null != currentScriptGrammar) {
            return currentScriptGrammar;
        } else {
            return getDefaultScriptGrammar(activity);
        }
    }

    /**
     * @param currentGrammar
     * @param current
     * @param grammar
     * @param message
     * @return
     */
    private static String getGrammarFromMappings(
            List<DataMapping> datamappings, DirectionType mappingDirection,
            String currentGrammar) {
        String grammar = currentGrammar;
        for (Object next : datamappings) {
            if (next instanceof DataMapping) {
                DataMapping mapping = (DataMapping) next;
                if (mappingDirection == null
                        || (mappingDirection != null && mappingDirection
                                .equals(mapping.getDirection()))) {
                    Expression expression;
                    Object other =
                            Xpdl2ModelUtil.getOtherElement(mapping,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Expression());
                    if (other instanceof Expression) {
                        expression = (Expression) other;
                    } else {
                        expression = mapping.getActual();
                    }
                    if (expression != null) {
                        String current = expression.getScriptGrammar();
                        if (current != null) {
                            if (grammar == null) {
                                grammar = current;
                            } else {
                                if (!grammar.equals(current)) {
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }
        return grammar;
    }
}
