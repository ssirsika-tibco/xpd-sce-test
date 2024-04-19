/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.process.js.model.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.text.IDocument;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.BaseProcessScriptSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TestTimeType;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script Details provider for the JavaScript editor section. This provides
 * context specific commands, and data required for the editor section
 * 
 * @author rsomayaj
 * 
 */
public class ProcessJavaScriptInfoProvider extends AbstractSaveableProcessScriptInfoProvider
{

    private static final String SINGLE_LINE_COMMENT = "//"; //$NON-NLS-1$"

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#executeSaveCommand(org.eclipse.jface.text.IDocument)
     * 
     * @param document
     */
    @Override
	public void doExecuteSaveCommand(IDocument document)
	{
        String modifiedScript = document.get();
        if (getInput() == null) {
            return;
        }
        EObject eObject = getInput();
        if (eObject != null) {
            EditingDomain editingDomain =
                    WorkingCopyUtil.getEditingDomain(eObject);
            if (editingDomain == null && eObject instanceof ScriptInformation) {
                Activity act = ((ScriptInformation) eObject).getActivity();
                if (act != null) {
                    editingDomain = WorkingCopyUtil.getEditingDomain(act);
                }
            }
            if (editingDomain != null) {
                Command setTaskScriptCommand =
                        getSetScriptCommand(editingDomain,
                                eObject,
                                modifiedScript,
                                getScriptGrammar());
                if (setTaskScriptCommand != null
                        && setTaskScriptCommand.canExecute()) {
                    editingDomain.getCommandStack()
                            .execute(setTaskScriptCommand);
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getComplexScriptRelevantData()
     * 
     * @return
     */
    @Override
    public List getComplexScriptRelevantData() {
        List<IScriptRelevantData> srdList = Collections.EMPTY_LIST;
        try {
            srdList =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getComplexScriptRelevantData(getProcessDestinations(getInput()),
                                    getScriptContext(),
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    getInput(),
                                    ProcessJsConsts.JSCRIPT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return srdList;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getDecriptionLabel()
     * 
     * @return
     */
    @Override
    public String getDecriptionLabel() {
        String scriptContext = getScriptContext();
        String toReturn = null;
        if (scriptContext.equals(ProcessScriptContextConstants.SEQUENCE_FLOW)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_SeqFlowScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SCRIPT_TASK)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_TaskScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.ADHOC_PRECONDITION)) {

            AdHocTaskConfigurationType adhocTaskConfigurationType =
                    getAdhocTaskConfigurationType();
            /*
             * XPD-6861 : The Adhoc Activity Precondition Script label should be
             * specific to the Adhoc Task Execution Type(i.e. Manual or
             * Automatic)
             */
            if (adhocTaskConfigurationType != null
                    && AdHocExecutionTypeType.MANUAL
                            .equals(adhocTaskConfigurationType
                                    .getAdHocExecutionType())) {
                toReturn =
                        Messages.ProcessJavaScriptInfoProvider_ManualAdhocTaskPreconditionScriptEnablement_label;
            } else {

                toReturn =
                        Messages.ProcessJavaScriptInfoProvider_AutomaticAdhocTaskPreconditionScriptEnablement_label;
            }

        } else if (scriptContext

        .equals(ProcessScriptContextConstants.PERFORMER_DATA_FIELD)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_ParticSelectScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.TIMER_EVENT)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_TimeoutScriptScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_RescheduleTimerScriptheader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.OPEN_USER_TASK)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_UTOpenScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.CLOSE_USER_TASK)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_UTCloseScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SUBMIT_USER_TASK)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_UTSubmitScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.WEB_SERVICE_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.JAVA_SERVICE_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.SUB_PROCESS_TASK)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_ServTaskScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_LOOP_EXPR)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_NumInstLoopScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_COMPLEX_EXIT_EXPR)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_ComplexExitLoopScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_ADDITIONAL_INSTANCES_LOOP_EXPR)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_AddInstsLoopScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.INITIATED_SCRIPT_TASK)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_InitiatedScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_CompletedScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_DeadloineExpiredScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_CancelledScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SCHEDULE_USER_TASK)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_ScheduledScriptHeader_label;
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.RESCHEDULE_USER_TASK)) {
            toReturn =
                    Messages.ProcessJavaScriptInfoProvider_RescheduleScrptHeader_label;
        }

        return toReturn;
    }

    /**
     * 
     * @return the {@link AdHocTaskConfigurationType} extension element if the
     *         Input of this provider is an Adhoc Activity else return
     *         <code>null</code>
     */
    private AdHocTaskConfigurationType getAdhocTaskConfigurationType() {

        AdHocTaskConfigurationType adhocConfigType = null;

        EObject input = getInput();

        if (input instanceof Activity) {

            Activity activity = (Activity) input;

            Object adhocConfig =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration());

            if (adhocConfig instanceof AdHocTaskConfigurationType) {

                adhocConfigType = (AdHocTaskConfigurationType) adhocConfig;
            }
        }
        return adhocConfigType;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getDescriptionLabel(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public String getDescriptionLabel(EObject input) {
        String scriptDesc = null;
        String scriptContext = getScriptContext();

        if (ProcessScriptContextConstants.STD_LOOP_EXPR.equals(scriptContext)) {
            /* Default to description for "evaluate before" */
            scriptDesc =
                    Messages.ProcessJavaScriptInfoProvider_StdLoopBefore_label;

            if (input instanceof Activity) {
                Loop loop = ((Activity) input).getLoop();

                if (loop != null) {
                    LoopStandard loopStandard = loop.getLoopStandard();

                    if (loopStandard != null) {
                        if (TestTimeType.AFTER_LITERAL.equals(loopStandard
                                .getTestTime())) {
                            scriptDesc =
                                    Messages.ProcessJavaScriptInfoProvider_StdLoopAfter_label;
                        }
                    }
                }
            }
        }

        if (scriptDesc == null) {
            scriptDesc = super.getDescriptionLabel(input);
        }

        return scriptDesc != null ? scriptDesc : ""; //$NON-NLS-1$    }
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getRelevantEObject(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public EObject getRelevantEObject(EObject input) {
        String scriptContext = getScriptContext();
        if (scriptContext.equals(ProcessScriptContextConstants.SEQUENCE_FLOW)) {
            return getTransitionEObject();
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SCRIPT_TASK)) {
            return getScriptTaskEObject();
        } else if (scriptContext
				.equals(ProcessScriptContextConstants.PROCESS_SCRIPT_LIBRARY_FUNCTION))
		{
            return getScriptTaskEObject();
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.ADHOC_PRECONDITION)) {
            return getAdhocScriptEObject();
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.PERFORMER_DATA_FIELD)) {
            return getScriptPerformerEObject();
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.TIMER_EVENT)) {
            return getTimerEventEObject();
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT)) {
            return getRescheduleTimerEventEObject();
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.OPEN_USER_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.CLOSE_USER_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.SUBMIT_USER_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.SCHEDULE_USER_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.RESCHEDULE_USER_TASK)) {
            return getUserTaskScriptEObject();
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.WEB_SERVICE_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.JAVA_SERVICE_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.SUB_PROCESS_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING)
                || scriptContext
                        .equals(ProcessScriptContextConstants.GLOBAL_THROW_SIGNAL_EVENTMAPPING)
                || scriptContext
                        .equals(ProcessScriptContextConstants.GLOBAL_CATCH_SIGNAL_EVENTMAPPING)
                || scriptContext.equals(ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING)) {
            return getInput();
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.STD_LOOP_EXPR)) {
            return getStandardLoopExpressionEObject();
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_LOOP_EXPR)
                || scriptContext
                        .equals(ProcessScriptContextConstants.MI_COMPLEX_EXIT_EXPR)
                || scriptContext
                        .equals(ProcessScriptContextConstants.MI_ADDITIONAL_INSTANCES_LOOP_EXPR)) {
            return getMultiInstanceLoopExpressionEObject();
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.INITIATED_SCRIPT_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK)
                || scriptContext
                        .equals(ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK)) {
            return Xpdl2ModelUtil.getParentActivity(getInput());
        }
        return super.getRelevantEObject(input);
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getScript(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public String getScript(EObject input) {
        String scriptContext = getScriptContext();
        if (scriptContext.equals(ProcessScriptContextConstants.SEQUENCE_FLOW)) {
            return ProcessScriptUtil.getSeqFlowScript((Transition) getInput());

        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SCRIPT_TASK)) {
            return ProcessScriptUtil.getScriptTaskScript((Activity) getInput());

        } else if (scriptContext
				.equals(ProcessScriptContextConstants.PROCESS_SCRIPT_LIBRARY_FUNCTION))
		{
        	return ProcessScriptUtil.getScriptTaskScript((Activity) getInput());
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.ADHOC_PRECONDITION)) {
            return ProcessScriptUtil
                    .getAdhocTaskPreconditionScript((Activity) getInput());

        } else if (scriptContext
                .equals(ProcessScriptContextConstants.PERFORMER_DATA_FIELD)) {
            return ProcessScriptUtil
                    .getScriptPerformerScript((ProcessRelevantData) getInput());

        } else if (scriptContext
                .equals(ProcessScriptContextConstants.TIMER_EVENT)) {
            return ProcessScriptUtil.getTimerEventScript((Activity) getInput());

        } else if (scriptContext
                .equals(ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT)) {
            return ProcessScriptUtil
                    .getRescheduleTimerEventScript((Activity) getInput());

        } else if (scriptContext
                .equals(ProcessScriptContextConstants.OPEN_USER_TASK)) {
            return ProcessScriptUtil
                    .getUserTaskOpenScript((Activity) getInput());

        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SCHEDULE_USER_TASK)) {
            return ProcessScriptUtil
                    .getUserTaskScheduleScript((Activity) getInput());

        } else if (scriptContext
                .equals(ProcessScriptContextConstants.RESCHEDULE_USER_TASK)) {
            return ProcessScriptUtil
                    .getUserTaskRescheduleScript((Activity) getInput());

        } else if (scriptContext
                .equals(ProcessScriptContextConstants.CLOSE_USER_TASK)) {
            return ProcessScriptUtil
                    .getUserTaskCloseScript((Activity) getInput());

        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SUBMIT_USER_TASK)) {
            return ProcessScriptUtil
                    .getUserTaskSubmitScript((Activity) getInput());

        }
        /*
         * else if (scriptContext
         * .equals(ProcessScriptContextConstants.WEB_SERVICE_TASK) ||
         * scriptContext
         * .equals(ProcessScriptContextConstants.JAVA_SERVICE_TASK) ||
         * scriptContext
         * .equals(ProcessScriptContextConstants.SUB_PROCESS_TASK)) { EObject
         * input = getInput(); if (input instanceof ScriptInformation) { return
         * Xpdl2WsdlUtil .getWebServiceTaskScript((ScriptInformation)
         * getInput()); } else { return ""; //$NON-NLS-1$ } }
         */

        else if (scriptContext
                .equals(ProcessScriptContextConstants.STD_LOOP_EXPR)) {
            return ProcessScriptUtil
                    .getStdLoopExpressionScript((Activity) getInput());
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_LOOP_EXPR)) {
            return ProcessScriptUtil
                    .getMILoopExpressionScript((Activity) getInput());
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_COMPLEX_EXIT_EXPR)) {
            return ProcessScriptUtil
                    .getMIComplexExitExpressionScript((Activity) getInput());
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_ADDITIONAL_INSTANCES_LOOP_EXPR)) {
            return ProcessScriptUtil
                    .getMIAdditionalLoopExpressionScript((Activity) getInput());
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.INITIATED_SCRIPT_TASK)) {
            return ProcessScriptUtil.getInitiatedScript((Activity) getInput());
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK)) {
            return ProcessScriptUtil.getCompletedScript((Activity) getInput());
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK)) {
            return ProcessScriptUtil
                    .getDeadlineExpiredScript((Activity) getInput());
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK)) {
            return ProcessScriptUtil.getCancelledScript((Activity) getInput());
        }
        return null;

    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getScriptRelevantData()
     * 
     * @return
     * @throws CoreException
     * @throws CoreException
     */
    @Override
    public List<IScriptRelevantData> getScriptRelevantData() {
        if (getCachedSrdList() == null) {
            setCachedSrdList(Collections.EMPTY_LIST);
            try {
                setCachedSrdList(ScriptGrammarContributionsUtil.INSTANCE
                        .getScriptRelevantData(getProcessDestinations(getInput()),
                                getScriptContext(),
                                ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                getInput(),
                                ProcessJsConsts.JSCRIPT_DESTINATION));
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
        }
        return getCachedSrdList();
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getEnabledDestinations(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    public List<String> getProcessDestinations(EObject input) {
        List<String> enabledDestinations = new ArrayList<String>();
        com.tibco.xpd.xpdl2.Process process = Xpdl2ModelUtil.getProcess(input);
        if (process == null) {
            /**
             * if a data field is created at package level then it would not
             * have a process associated with it which results in process=null
             * and hence the data field script drop down will not have any
             * grammar added to it . hence we check if the parent of the data
             * field is package and add grammar to the data field script drop
             * down
             */
            EObject object = getInput();
            EObject parent = null;
            if (object instanceof ProcessRelevantData) {
                parent = object.eContainer();
            }
            if (null == parent)
                return Collections
                        .singletonList(BaseProcessScriptSection.BPMN1_VALIDATION_DESTINATION);
        } else {
            enabledDestinations.addAll(DestinationUtil
                    .getEnabledValidationDestinations(process));

            // BPMN1 destination is not added to the list of destinations. By
            // default, this should be added.
            if (!enabledDestinations
                    .contains(BaseProcessScriptSection.BPMN1_VALIDATION_DESTINATION)) {
                enabledDestinations
                        .add(BaseProcessScriptSection.BPMN1_VALIDATION_DESTINATION);
            }
        }
        return enabledDestinations;

    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getSetScriptCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.String)
     * 
     * @param ed
     * @param input
     * @param script
     * @param grammar
     * @return
     */
    @Override
    public Command getSetScriptCommand(EditingDomain editingDomain,
            EObject eObject, String strScript, String grammar) {
        String oldScript = getScript(eObject);
        if (strScript != null && !strScript.equals(oldScript)) {
            String scriptContext = getScriptContext();
            if (scriptContext
                    .equals(ProcessScriptContextConstants.SEQUENCE_FLOW)) {
                return ProcessScriptUtil
                        .getSetSequenceFlowScriptCommand(editingDomain,
                                strScript,
                                (Transition) eObject,
                                getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.SCRIPT_TASK)) {
                return ProcessScriptUtil
                        .getSetScriptTaskScriptCommand(editingDomain,
                                strScript,
                                (Activity) eObject,
                                getScriptGrammar());

            } else if (scriptContext
					.equals(ProcessScriptContextConstants.PROCESS_SCRIPT_LIBRARY_FUNCTION))
			{
				return ProcessScriptUtil.getSetPSLFunctionScriptCommand(editingDomain, strScript, (Activity) eObject,
						getScriptGrammar());

			}
			else if (scriptContext
                    .equals(ProcessScriptContextConstants.ADHOC_PRECONDITION)) {
                return ProcessScriptUtil
                        .getSetAdhocScriptCommand(editingDomain,
                                strScript,
                                (Activity) eObject,
                                getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.PERFORMER_DATA_FIELD)) {
                return ProcessScriptUtil
                        .getSetScriptPerfomerScriptCommand(editingDomain,
                                strScript,
                                (ProcessRelevantData) eObject,
                                getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.TIMER_EVENT)) {
                return ProcessScriptUtil
                        .getSetTimerEventScriptCommand(editingDomain,
                                strScript,
                                (Activity) eObject,
                                getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT)) {
                return ProcessScriptUtil
                        .getSetRescheduleTimerEventScriptCommand(editingDomain,
                                strScript,
                                (Activity) eObject,
                                getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.OPEN_USER_TASK)) {
                return ProcessScriptUtil
                        .getSetUserTaskOpenScriptCommand(editingDomain,
                                strScript,
                                eObject,
                                getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.SCHEDULE_USER_TASK)) {
                return ProcessScriptUtil
                        .getSetUserTaskScheduleScriptCommand(editingDomain,
                                strScript,
                                eObject,
                                getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.RESCHEDULE_USER_TASK)) {
                return ProcessScriptUtil
                        .getSetUserTaskRescheduleScriptCommand(editingDomain,
                                strScript,
                                eObject,
                                getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.CLOSE_USER_TASK)) {
                return ProcessScriptUtil
                        .getSetUserTaskCloseScriptCommand(editingDomain,
                                strScript,
                                eObject,
                                getScriptGrammar());

            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.SUBMIT_USER_TASK)) {
                return ProcessScriptUtil
                        .getSetUserTaskSubmitScriptCommand(editingDomain,
                                strScript,
                                eObject,
                                getScriptGrammar());

            }
            /*
             * else if (scriptContext
             * .equals(ProcessScriptContextConstants.WEB_SERVICE_TASK) ||
             * scriptContext
             * .equals(ProcessScriptContextConstants.JAVA_SERVICE_TASK) ||
             * scriptContext
             * .equals(ProcessScriptContextConstants.SUB_PROCESS_TASK)) { if
             * (eObject instanceof ScriptInformation) { return Xpdl2WsdlUtil
             * .getSetWebServiceTaskScriptCommand(editingDomain, strScript,
             * (ScriptInformation) eObject, getScriptGrammar()); } else { return
             * Xpdl2WsdlUtil
             * .getCreateWebServiceTaskScriptCommand(editingDomain, strScript,
             * (ProcessRelevantData) eObject, getScriptGrammar()); }
             * 
             * }
             */

            else if (scriptContext
                    .equals(ProcessScriptContextConstants.STD_LOOP_EXPR)) {
                return ProcessScriptUtil
                        .getSetStdLoopExpressionScriptCommand(editingDomain,
                                strScript,
                                (Activity) eObject,
                                getScriptGrammar());
            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.MI_LOOP_EXPR)) {
                return ProcessScriptUtil
                        .getSetMILoopExprScriptCommand(editingDomain,
                                strScript,
                                (Activity) eObject,
                                getScriptGrammar());
            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.MI_COMPLEX_EXIT_EXPR)) {
                return ProcessScriptUtil
                        .getSetMIComplexExitExprScriptCommand(editingDomain,
                                strScript,
                                (Activity) eObject,
                                getScriptGrammar());
            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.MI_ADDITIONAL_INSTANCES_LOOP_EXPR)) {
                return ProcessScriptUtil
                        .getSetMIAdditionalInstancesExprScriptCommand(editingDomain,
                                strScript,
                                (Activity) eObject,
                                getScriptGrammar());
            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.INITIATED_SCRIPT_TASK)) {
                return ProcessScriptUtil
                        .getSetInitiatedScriptCommand(editingDomain,
                                strScript,
                                eObject,
                                getScriptGrammar());
            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK)) {
                return ProcessScriptUtil
                        .getSetCompletedScriptCommand(editingDomain,
                                strScript,
                                eObject,
                                getScriptGrammar());
            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK)) {
                return ProcessScriptUtil
                        .getSetDeadlineExpiredScriptCommand(editingDomain,
                                strScript,
                                eObject,
                                getScriptGrammar());
            } else if (scriptContext
                    .equals(ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK)) {
                return ProcessScriptUtil
                        .getSetCancelledScriptCommand(editingDomain,
                                strScript,
                                eObject,
                                getScriptGrammar());
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getSetScriptGrammarCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String)
     * 
     * @param editingDomain
     * @param scriptContainer
     * @param scriptGrammar
     * @return
     */
    @Override
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject scriptContainer, String scriptGrammar) {
        String scriptContext = getScriptContext();
        Command toReturn = null;
        if (scriptContext.equals(ProcessScriptContextConstants.SEQUENCE_FLOW)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetSequenceFlowScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SCRIPT_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetScriptTaskGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,
                                    scriptContext);
        } else if (scriptContext
				.equals(ProcessScriptContextConstants.PROCESS_SCRIPT_LIBRARY_FUNCTION))
		{
        	toReturn =
                    ProcessScriptUtil
                            .getSetScriptTaskGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,
                                    scriptContext);

		} else if (scriptContext
                .equals(ProcessScriptContextConstants.ADHOC_PRECONDITION)) {
            toReturn =
                    ProcessScriptUtil.getSetAdhocGrammarCommand(editingDomain,
                            scriptGrammar,
                            scriptContainer,

                            true);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.PERFORMER_DATA_FIELD)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetPerformerScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,
                                    true);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.TIMER_EVENT)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetTimerEventScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,
                                    true);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetRescheduleTimerEventScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,
                                    true,
                                    scriptContext);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.OPEN_USER_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetOpenUserTaskScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,
                                    scriptContext);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SCHEDULE_USER_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetScheduleUserTaskScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,
                                    scriptContext);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.RESCHEDULE_USER_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetRescheduleUserTaskScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,
                                    scriptContext);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.CLOSE_USER_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetCloseUserTaskScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,
                                    scriptContext);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.SUBMIT_USER_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetSubmitUserTaskScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,
                                    scriptContext);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.STD_LOOP_EXPR)) {
            if (scriptContainer instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getSetStandardLoopExpressionGrammarCommand(editingDomain,
                                        scriptGrammar,
                                        (Activity) scriptContainer,

                                        true);
            }
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_LOOP_EXPR)) {
            if (scriptContainer instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getSetMILoopExprScriptGrammarCommand(editingDomain,
                                        scriptGrammar,
                                        (Activity) scriptContainer,
                                        true);
            }
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_COMPLEX_EXIT_EXPR)) {
            if (scriptContainer instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getSetMIComplexExitScriptGrammarCommand(editingDomain,
                                        scriptGrammar,
                                        (Activity) scriptContainer,

                                        true);
            }
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.MI_ADDITIONAL_INSTANCES_LOOP_EXPR)) {
            if (scriptContainer instanceof Activity) {
                toReturn =
                        ProcessScriptUtil
                                .getSetMIAdditionalInstancesScriptGrammarCommand(editingDomain,
                                        scriptGrammar,
                                        (Activity) scriptContainer,

                                        true);
            }
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.INITIATED_SCRIPT_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetInitiatedScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,
                                    scriptContext);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetCompletedScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,
                                    scriptContext);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetDeadlineExpiredScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,

                                    scriptContext);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetCancelledScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,
                                    scriptContext);
        } else if (scriptContext
                .equals(ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetCancelledScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    scriptContainer,

                                    true,
                                    scriptContext);
        }
        return toReturn;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#isNewScriptInformation(org.eclipse.emf.ecore.EObject)
     * 
     * @param object
     * @return
     */
    @Override
    public Boolean isNewScriptInformation(EObject object) {
        boolean isNewSI = false;
        if (object instanceof ScriptInformation) {
            ScriptInformation scriptInformation = (ScriptInformation) object;
            if (scriptInformation.eContainer() == null) {
                isNewSI = true;
            }
        }
        return isNewSI;
    }

    protected EObject getStandardLoopExpressionEObject() {
        EObject input = getInput();
        if (input instanceof Activity) {
            return ProcessScriptUtil.getLoopStandard((Activity) input);
        }
        return null;
    }

    protected EObject getMultiInstanceLoopExpressionEObject() {
        EObject input = getInput();
        if (input instanceof Activity) {
            return ProcessScriptUtil.getMILoopScripts((Activity) input);
        }
        return null;
    }

    protected EObject getTransitionEObject() {
        EObject input = getInput();
        if (input instanceof Transition) {
            return input;
        }
        return null;
    }

    protected EObject getScriptTaskEObject() {
        EObject input = getInput();
        if (input instanceof Activity) {
            return input;
        }
        return null;
    }

    protected EObject getAdhocScriptEObject() {
        EObject input = getInput();
        if (input instanceof Activity) {
            return input;
        }
        return null;
    }

    protected EObject getScriptPerformerEObject() {
        EObject input = getInput();
        if (input instanceof ProcessRelevantData) {
            return input;
        }
        return null;
    }

    protected EObject getUserTaskScriptEObject() {
        EObject input = getScriptTaskEObject();
        return input;
    }

    protected EObject getTimerEventEObject() {
        Activity act = (Activity) getInput();
        if (act == null) {
            return null;
        }
        List listOfDl = act.getDeadline();
        if (listOfDl != null && listOfDl.size() > 0) {
            return (Deadline) listOfDl.get(0);
        } else {
            return null;
        }
    }

    /**
     * 
     * @return The EObject that is the reschedule timer script.
     */
    protected EObject getRescheduleTimerEventEObject() {
        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();

            return EventObjectUtil.getRescheduleTimerScript(activity);
        }
        return null;
    }

}
