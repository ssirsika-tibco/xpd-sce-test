/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.Visibility;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class ActivityAssociatedParameterSection extends
        AssociatedParameterSection {

    private Button visibilityPrivate;

    private Button visibilityPublic;

    private Button overwriteWorkItemDataButton;

    private Composite overwriteBtnContainer;

    private Label workItemLabel;

    /**
     * 
     */
    public ActivityAssociatedParameterSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    public ActivityAssociatedParameterSection(EClass clas1) {
        super(clas1);
    }

    @Override
    public boolean select(Object toTest) {

        EObject eo = null;
        if (toTest instanceof EObject) {
            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {
            eo = ((IAdaptable) toTest).getAdapter(EObject.class);
        }
        if (eo != null) {
            if (Xpdl2Package.eINSTANCE.getActivity().isSuperTypeOf(eo.eClass())) {
                Activity activity = (Activity) eo;

				Xpdl2WorkingCopyImpl xpdl2Wc = (Xpdl2WorkingCopyImpl) WorkingCopyUtil.getWorkingCopyFor(activity);

                if (activity.getRoute() != null) {
                    return false;
                } else if (activity.getEvent() != null) {
                    Event event = activity.getEvent();

                    /*
                     * SDA-187: Don't show interface tab on Start/End events.
                     */
                    if (DecisionFlowUtil.isDecisionFlow(activity.getProcess())) {
                        return false;
                    }

                    if (event instanceof StartEvent) {
                        StartEvent startEvent = (StartEvent) event;
                        switch (startEvent.getTrigger().getValue()) {
							/*
							 * ACE-6836 Re-enable Correlation Data and Hence Event initialisers for Incoming Request
							 * Event handlers and Event Sub-processes
							 */
							case TriggerType.NONE:
							if (EventObjectUtil.isEventSubProcessStartEvent(activity))
							{
								return false;
							}
                            return true;

                        case TriggerType.TIMER:
                        case TriggerType.CONDITIONAL:
                        case TriggerType.MULTIPLE:
                            return true;

                        default:
                            return false;
                        }
                    } else if (event instanceof IntermediateEvent) {
                        IntermediateEvent intermediateEvent =
                                (IntermediateEvent) event;
                        switch (intermediateEvent.getTrigger().getValue()) {
                        /*
						 * Sid ACE-6338 Go back to param AND correlation data association for incoming request events as
						 * biz data correlation is now supported for both. So here we return false to disable this 'just
						 * parameters' section for all incoming request intermediate events.
						 */
						case TriggerType.NONE:
							return false;

                        case TriggerType.TIMER:
                        case TriggerType.CONDITIONAL:
                        case TriggerType.MULTIPLE:
                        case TriggerType.ERROR:
                            return true;
                        case TriggerType.SIGNAL:
                            /*
                             * Enable for throw signals or catch signal's not
                             * attached to task (if attached to task then things
                             * should use attached-to-task data associations
                             */
                            if (CatchThrow.THROW.equals(EventObjectUtil
                                    .getCatchThrowType(activity))
                                    || !EventObjectUtil
                                            .isAttachedToTask(activity)) {
                                return true;
                            }
                            return false;

                        case TriggerType.MESSAGE:
                            if (intermediateEvent.getTriggerResultMessage() != null) {
                                if (!CatchThrow.CATCH.equals(intermediateEvent
                                        .getTriggerResultMessage()
                                        .getCatchThrow())) {
                                    return true;
                                }
                            }
                            return false;

                        default:
                            return false;
                        }

                    } else if (event instanceof EndEvent) {
                        EndEvent endEvent = (EndEvent) event;
                        switch (endEvent.getResult().getValue()) {
                        case ResultType.MESSAGE:
                        case ResultType.MULTIPLE:
                        case ResultType.NONE:
                        case ResultType.ERROR:
                        case ResultType.SIGNAL:
                            return true;
                        default:
                            return false;
                        }
                    }
                }
                switch (TaskObjectUtil.getTaskType(activity).getValue()) {
                case TaskType.USER:
                case TaskType.SUBPROCESS:
                case TaskType.SCRIPT:
            		/**
            		 * Show the ActivityAssociatedParameterSection (i.e. Interface tab in the UI) tab properties section for below file types extensions. 
            		 * 
            		 * - xpdl 
            		 * - tasks 
            		 * - dflow.
            		 * 
            		 * These conditions were required to be added as part of ACE-7362 i.e. creating property panel for the
            		 * Script Function Type Selection from .psl file (i.e. Process Script Library)
            		 */
					return xpdl2Wc.isOneOfXpdl2FileType(new Xpdl2FileType[]{Xpdl2FileType.PROCESS,
							Xpdl2FileType.DECISION_FLOW, Xpdl2FileType.TASK_LIBRARY});
                case TaskType.SEND:
                case TaskType.SERVICE:
                case TaskType.NONE:
                case TaskType.MANUAL:
                case TaskType.DTABLE:
                    /*
                     * Sid ACE-6338 Go back to param AND correlation data association incoming request events. So
                     * disable this 'just parameters' section
                     */
                    // case TaskType.RECEIVE:

                    return true;
                default:
                    return false;

                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.AssociatedParameterSection#createCompositeAboveTable(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param root2
     * @param toolkit
     */
    @Override
    protected void createCompositeAboveTable(Composite root2,
            XpdFormToolkit toolkit) {

        Composite interfaceTabBtns = toolkit.createComposite(root2);
        GridLayout glayout = new GridLayout(5, false);
        glayout.marginHeight = 0;
        interfaceTabBtns.setLayout(glayout);
        interfaceTabBtns.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_BEGINNING));

        Label createLabel =
                toolkit.createLabel(interfaceTabBtns,
                        Messages.AssociatedParameterSection_Visibility_label);
        createLabel
                .setToolTipText(Messages.AssociatedParameterSection_VisibilityToolTip_longmsg);

        Composite visibilityBtns =
                toolkit.createComposite(interfaceTabBtns, SWT.BORDER);
        visibilityBtns.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_BEGINNING));
        GridLayout gl = new GridLayout(2, false);
        gl.marginHeight = 0;
        visibilityBtns.setLayout(gl);

        visibilityPrivate =
                toolkit.createButton(visibilityBtns,
                        Messages.AssociatedParameterSection_VisibilityPrivate_label,
                        SWT.RADIO,
                        "visibilityPrivateRadio"); //$NON-NLS-1$
        visibilityPrivate.setLayoutData(new GridData());
        manageControl(visibilityPrivate);

        visibilityPublic =
                toolkit.createButton(visibilityBtns,
                        Messages.AssociatedParameterSection_VisibilityPublic_label,
                        SWT.RADIO,
                        "visibilityPublicRadio"); //$NON-NLS-1$
        visibilityPublic.setLayoutData(new GridData());
        manageControl(visibilityPublic);

        createOverwriteWorkItemDataComposite(interfaceTabBtns, toolkit);

    }

    /**
     * 
     * <li>Adds the 'Overwrite Data Already Modified in Work Item' button to
     * User task-> Interface tab</li><li>Important: The 'Overwrite Data Already
     * Modified in Work Item' button from User Task-> Advanced tab and Catch
     * Signal Event->Map from Signal tab have been removed.</li>
     * 
     * @param root2
     * @param toolkit
     */
    protected void createOverwriteWorkItemDataComposite(Composite root2,
            XpdFormToolkit toolkit) {

        workItemLabel =
                toolkit.createLabel(root2,
                        Messages.ActivityAssociatedParameterSection_OverwriteData_label);
        workItemLabel
                .setToolTipText(Messages.ActivityAssociatedParameterSection_OverwriteButtonToolTip_message1);

        overwriteBtnContainer = toolkit.createComposite(root2, SWT.BORDER);
        GridLayout gl = new GridLayout(2, false);
        gl.marginHeight = 0;
        gl.horizontalSpacing = 0;
        overwriteBtnContainer.setLayout(gl);

        overwriteWorkItemDataButton =
                toolkit.createButton(overwriteBtnContainer,
                        Messages.ActivityAssociatedParameterSection_OverwriteWorkItemDataButton_button,
                        SWT.CHECK);
        overwriteWorkItemDataButton.setLayoutData(new GridData());
        manageControl(overwriteWorkItemDataButton);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.AssociatedParameterSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        super.doRefresh();
        //
        // A reply activity for a request activity that is auto generated
        // gets a copy of the request activity's associations we should not
        // therefore allow user to edit them here.
        boolean isReplyToGeneratedRequest = false, isEventImplemented = false;
        boolean isReplyActivity = false;
        boolean isGeneratedRequest = false;
        boolean isUserTask = false;
        boolean isDestinationBPM = false;
        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();

            /*
             * Enables the overwriteWorkItemDataButton only when User task is
             * selected and the Destination environment is BPM
             */
            if (overwriteWorkItemDataButton != null
                    && !overwriteWorkItemDataButton.isDisposed()
                    && activity.getEvent() == null) {
                if (TaskObjectUtil.getTaskTypeStrict(activity).getValue() == TaskType.USER) {
                    isUserTask = true;
                    if (com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil
                            .isBPMDestinationSelected(activity.getProcess())) {
                        isDestinationBPM = true;
                    }
                }
                if (isUserTask && isDestinationBPM) {
                    workItemLabel.setVisible(true);
                    overwriteBtnContainer.setVisible(true);

                    boolean isOverwriteWorkItemData = false;
                    isOverwriteWorkItemData =
                            Xpdl2ModelUtil
                                    .getOtherAttributeAsBoolean(activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_OverwriteAlreadyModifiedTaskData());
                    overwriteWorkItemDataButton
                            .setSelection(isOverwriteWorkItemData);
                } else {
                    workItemLabel.setVisible(false);
                    overwriteBtnContainer.setVisible(false);
                }
            } else {
                workItemLabel.setVisible(false);
                overwriteBtnContainer.setVisible(false);
            }
            isEventImplemented =
                    ProcessInterfaceUtil.isEventImplemented(activity);

            isReplyActivity = ReplyActivityUtil.isReplyActivity(activity);

            if (isReplyActivity) {
                Activity requestAct =
                        ReplyActivityUtil
                                .getRequestActivityForReplyActivity(activity);
                if (requestAct != null
                        && Xpdl2ModelUtil
                                .isGeneratedRequestActivity(requestAct)) {
                    isReplyToGeneratedRequest = true;
                }
            } else {
                if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                    isGeneratedRequest = true;
                }
            }
            Visibility visibility = getVisibility(activity);
            if (Visibility.PRIVATE.equals(visibility)) {
                visibilityPrivate.setSelection(true);
                visibilityPublic.setSelection(false);
            } else if (Visibility.PUBLIC.equals(visibility)) {
                visibilityPrivate.setSelection(false);
                visibilityPublic.setSelection(true);
            }
        }
        boolean addActionEnabled = true;
        if (isReplyToGeneratedRequest) {
            visibilityPrivate.setEnabled(false);
            visibilityPublic.setEnabled(false);
            if (assocParamTable != null) {
                assocParamTable.setEnabled(false);
                addActionEnabled = false;
            }
        } else if (isEventImplemented) {
            visibilityPrivate.setEnabled(false);
            visibilityPublic.setEnabled(false);

            if (assocParamTable != null) {
                /*
                 * SID Fix - Regression: used to be able to add formal params to
                 * interface params for non-message events!
                 */
                InterfaceMethod interfaceMethod =
                        ProcessInterfaceUtil
                                .getImplementedMethod((Activity) getInput());
                if (interfaceMethod == null
                        || TriggerType.MESSAGE_LITERAL.equals(interfaceMethod
                                .getTrigger())) {
                    assocParamTable.setEnabled(false);
                    addActionEnabled = false;
                }
            }
        } else {
            if (assocParamTable != null) {
                assocParamTable.setEnabled(true);
            }
        }

        if (assocParamTable != null) {
            if (assocParamTable.getAddAction() != null) {
                assocParamTable.getAddAction().setEnabled(addActionEnabled);
            }

            Color col = ColorConstants.black;
            String labelText =
                    Messages.AssociatedParamHandler_SelectAssocData_longdesc;

            Label tableLabel = getTableLabel();

            if (isReplyActivity) {

                if (isReplyToGeneratedRequest) {
                    col = ColorConstants.blue;
                    labelText =
                            Messages.ActivityAssociatedParameterSection_BothInAndOutSetInReuqest_description;
                }

            } else if (isEventImplemented) {
                col = ColorConstants.blue;
                labelText =
                        Messages.ActivityAssociatedParameterSection_AcessibleDataSetByProcessInterface_description;
            } else if (isGeneratedRequest) {
                col = ColorConstants.blue;
                labelText =
                        Messages.ActivityAssociatedParameterSection_SetInOutParamsForDefautlGeneratedService_description;
            }

            tableLabel.setForeground(col);
            setTableLabel(labelText);

        }

    }

    /**
     * @param activity
     * @return
     */
    private Visibility getVisibility(Activity activity) {
        Object otherAttribute =
                Xpdl2ModelUtil.getOtherAttribute(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Visibility());
        if (otherAttribute instanceof Visibility) {
            return (Visibility) otherAttribute;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.AssociatedParameterSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        if (obj == visibilityPrivate) {
            if (getInput() instanceof OtherAttributesContainer) {
                return Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(getEditingDomain(),
                                ((OtherAttributesContainer) getInput()),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Visibility(),
                                Visibility.PRIVATE);
            }
        } else if (obj == visibilityPublic) {
            if (getInput() instanceof OtherAttributesContainer) {
                return Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(getEditingDomain(),
                                ((OtherAttributesContainer) getInput()),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Visibility(),
                                Visibility.PUBLIC);
            }

        } else if (obj == overwriteWorkItemDataButton
                && getInput() instanceof Activity
                && overwriteWorkItemDataButton != null) {
            Activity activity = (Activity) getInput();
            return Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(getEditingDomain(),
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_OverwriteAlreadyModifiedTaskData(),
                            overwriteWorkItemDataButton.getSelection());
        }
        return super.doGetCommand(obj);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.AssociatedParameterSection#isDisableImplicitAssociation()
     * 
     * @return
     */
    @Override
    protected boolean isDisableImplicitAssociation() {
        if (getInput() instanceof Activity) {
            return ProcessInterfaceUtil
                    .isImplicitAssociationDisabled((Activity) getInput());
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.AssociatedParameterSection#getSetDisableImplicitAssociationCommand(java.lang.Boolean)
     * 
     * @param disableImplicitAssociation
     * @return
     */
    @Override
    protected Command getSetDisableImplicitAssociationCommand(
            Boolean disableImplicitAssociation) {
        if (getInput() instanceof Activity) {
            Activity activity = (Activity) getInput();

            CompoundCommand cmd =
                    new CompoundCommand(
                            disableImplicitAssociation ? Messages.ActivityAssociatedParameterSection_DisableImplicitAssociation_menu
                                    : Messages.ActivityAssociatedParameterSection_EnableImplicitAssociation_menu);

            AssociatedParameters assocParams =
                    (AssociatedParameters) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AssociatedParameters());
            if (assocParams == null) {
                assocParams =
                        XpdExtensionFactory.eINSTANCE
                                .createAssociatedParameters();
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(getEditingDomain(),
                                activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedParameters(),
                                assocParams));
            }

            cmd.append(SetCommand
                    .create(getEditingDomain(),
                            assocParams,
                            XpdExtensionPackage.eINSTANCE
                                    .getAssociatedParameters_DisableImplicitAssociation(),
                            disableImplicitAssociation));

            if (disableImplicitAssociation) {
                EList<AssociatedParameter> associatedParameters =
                        assocParams.getAssociatedParameter();
                if (associatedParameters.size() > 0) {
                    cmd.append(RemoveCommand.create(getEditingDomain(),
                            associatedParameters));
                }
            }
            return cmd;

        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.AssociatedParameterSection#showDisableImplicitButton()
     * 
     * @return
     */
    @Override
    protected boolean showDisableImplicitButton() {
        if (getInput() instanceof Activity) {
            if (ProcessInterfaceUtil.isEventImplemented((Activity) getInput())) {
                return false;
            }
        }
        return true;
    }
}
