/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.wsdl.BindingOperation;
import javax.wsdl.Fault;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableError;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorFolder;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorTreeItem;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.wsdl.ui.IWsdlObjectWrapper;
import com.tibco.xpd.wsdl.ui.WsdlLabelProvider;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;

/**
 * Bpmn Catchable Error Provider for Web Service invoking activities (Service
 * Task, Send Task and Throw Message Intermediate Event.
 * 
 * @author aallway
 * @since 3.2
 */
public class BpmnWebServiceErrorsContributor implements
        IBpmnCatchableErrorsContributor {

    private WsdlLabelProvider wsdlLabelProvider = null;

    private ILabelProvider nonUIWsdlLabelProvider = null;

    /**
     * 
     */
    public BpmnWebServiceErrorsContributor() {
    }

    @Override
    public Collection<IBpmnCatchableErrorTreeItem> getCatchableErrorTreeItems(
            Activity errorThrowerTaskOrEvent) {

        List<IBpmnCatchableErrorTreeItem> items = Collections.emptyList();

        Operation wsdlOperation =
                Xpdl2WsdlUtil.getOperation(errorThrowerTaskOrEvent);

        if (wsdlOperation != null
                && wsdlOperation instanceof org.eclipse.wst.wsdl.Operation) {
            Map faults = wsdlOperation.getFaults();

            BpmnCatchableErrorFolder taskFolder = null;
            /*
             * XPD-6974: Add TimeoutException when has a SOAP JMS Consumer
             * Participant with message configuration timeouts set
             */
            if (CatchWsdlErrorEventUtil
                    .hasSoapJmsConsumerParticipant(errorThrowerTaskOrEvent)) {

                taskFolder =
                        new BpmnCatchableErrorFolder(
                                getActivityName(errorThrowerTaskOrEvent),
                                getActivityImage(errorThrowerTaskOrEvent));

                items = new ArrayList<IBpmnCatchableErrorTreeItem>();
                items.add(taskFolder);

                taskFolder.addChild(new BpmnCatchableError(
                        errorThrowerTaskOrEvent,
                        ErrorThrowerType.PROCESS_ACTIVITY,
                        CatchWsdlErrorEventUtil.TIMEOUT_EXCEPTION, this));
                /* Note did not externalise as this is the ErrorCode */
            }

            if (faults != null && faults.size() > 0) {

                if (taskFolder == null) {

                    taskFolder =
                            new BpmnCatchableErrorFolder(
                                    getActivityName(errorThrowerTaskOrEvent),
                                    getActivityImage(errorThrowerTaskOrEvent));
                    items = new ArrayList<IBpmnCatchableErrorTreeItem>();
                    items.add(taskFolder);
                }

                BpmnCatchableErrorFolder operationFolder =
                        new BpmnCatchableErrorFolder(getWsdlLabelProvider()
                                .getText(wsdlOperation), getWsdlLabelProvider()
                                .getImage(wsdlOperation));
                taskFolder.addChild(operationFolder);

                for (Iterator iterator = faults.entrySet().iterator(); iterator
                        .hasNext();) {
                    Entry entry = (Entry) iterator.next();

                    if (entry.getValue() instanceof Fault
                            && entry.getValue() instanceof org.eclipse.wst.wsdl.Fault) {
                        org.eclipse.wst.wsdl.Fault fault =
                                (org.eclipse.wst.wsdl.Fault) entry.getValue();

                        BpmnCatchableError error =
                                new BpmnCatchableError(errorThrowerTaskOrEvent,
                                        ErrorThrowerType.PROCESS_ACTIVITY,
                                        getWsdlLabelProvider().getText(fault),
                                        this);
                        operationFolder.addChild(error);
                    }
                }
                /*
                 * User application error is always available.
                 */

            }
        }

        return items;
    }

    /**
     * @return WSDL label provider
     */
    private synchronized ILabelProvider getWsdlLabelProvider() {
        /*
         * If running in UI thread then use the Wsdl label provider otherwise
         * use a locally defined label provider that does not load any UI
         * components. This is necessary as this contributor can be called from
         * a validation rule and if running in headless mode can cause a
         * deadlock with the main thread (for example in command-line DAA
         * generation).
         */
        if (Display.getCurrent() != null) {
            // Running in UI thread
            if (wsdlLabelProvider == null) {
                wsdlLabelProvider = new WsdlLabelProvider();
            }
            return wsdlLabelProvider;
        } else {
            if (nonUIWsdlLabelProvider == null) {
                nonUIWsdlLabelProvider = new ILabelProvider() {

                    @Override
                    public void addListener(ILabelProviderListener arg0) {
                    }

                    @Override
                    public void dispose() {
                    }

                    @Override
                    public boolean isLabelProperty(Object arg0, String arg1) {
                        return false;
                    }

                    @Override
                    public void removeListener(ILabelProviderListener arg0) {
                    }

                    @Override
                    public Image getImage(Object arg0) {
                        return null;
                    }

                    @Override
                    public String getText(Object element) {
                        if (element instanceof IWsdlObjectWrapper) {
                            return ((IWsdlObjectWrapper) element)
                                    .getWsdlObjectLocalName();
                        }

                        if (element instanceof Service) {
                            return ((Service) element).getQName()
                                    .getLocalPart();
                        } else if (element instanceof Port) {
                            return ((Port) element).getName();
                        } else if (element instanceof PortType) {
                            return ((PortType) element).getQName()
                                    .getLocalPart();
                        } else if (element instanceof Operation) {
                            return ((Operation) element).getName();
                        } else if (element instanceof BindingOperation) {
                            return ((BindingOperation) element).getName();
                        } else if (element instanceof Fault) {
                            return ((Fault) element).getName();
                        }
                        return ""; //$NON-NLS-1$
                    }
                };
            }

            return nonUIWsdlLabelProvider;
        }
    }

    @Override
    public Image getErrorImage(Object errorThrower, String errorId) {
        if (errorThrower instanceof Activity) { // will always be!!
            Operation wsdlOperation =
                    Xpdl2WsdlUtil.getOperation((Activity) errorThrower);

            if (wsdlOperation != null
                    && wsdlOperation instanceof org.eclipse.wst.wsdl.Operation) {
                Fault fault = wsdlOperation.getFault(errorId);

                return getWsdlLabelProvider().getImage(fault);
            }
        }
        return null;
    }

    public Collection<? extends Object> getErrorParameterContentProvider(
            Object errorThrower, String errorId) {
        // TODO Auto-generated method stub
        return Collections.emptyList();
    }

    @Override
    public String getErrorThrowerContainerId(Object errorThrower) {
        // Safe to assume that errorThrower is the one we returned which is
        // ALWAYS an activity.
        return ((Activity) errorThrower).getProcess().getId();
    }

    @Override
    public String getErrorThrowerId(Object errorThrower) {
        // Safe to assume that errorThrower is the one we returned which is
        // ALWAYS an activity.
        return ((Activity) errorThrower).getId();
    }

    @Override
    public boolean isApplicableErrorThrower(Activity errorThrower) {
        return CatchWsdlErrorEventUtil
                .isWsdlFaultThrowingActivityType(errorThrower);
    }

    private Image getActivityImage(Activity errorThrowerTaskOrEvent) {
        if (errorThrowerTaskOrEvent.getImplementation() instanceof Task) {
            return DiagramDropObjectUtils.getImageForTaskType(TaskObjectUtil
                    .getTaskType(errorThrowerTaskOrEvent));

        } else if (errorThrowerTaskOrEvent.getEvent() != null) {
            return DiagramDropObjectUtils.getImageForEventType(EventObjectUtil
                    .getFlowType(errorThrowerTaskOrEvent), EventObjectUtil
                    .getEventTriggerType(errorThrowerTaskOrEvent));
        }
        return null;
    }

    private String getActivityName(Activity errorThrowerTaskOrEvent) {
        String name = ProcessDataUtil.getActivityName(errorThrowerTaskOrEvent);
        if (name == null || name.length() == 0) {
            if (errorThrowerTaskOrEvent.getImplementation() instanceof Task) {
                name =
                        "<"     + TaskObjectUtil.getTaskType(errorThrowerTaskOrEvent) //$NON-NLS-1$
                                        .toString() + ">"; //$NON-NLS-1$

            } else if (errorThrowerTaskOrEvent.getEvent() != null) {
                name =
                        "<"     + EventObjectUtil.getFlowType(errorThrowerTaskOrEvent) //$NON-NLS-1$
                                        .toString() + ">"; //$NON-NLS-1$
            } else {
                name = "?"; //$NON-NLS-1$
            }
        }

        return name;
    }

}
