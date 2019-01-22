/**
 * SetEventTypeAction.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.commands.ChangeEventTriggerTypeCommand;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.EventEditPart;

/**
 * SetEventTypeAction
 * 
 * @author aallway
 */
public class SetEventTypeAction implements IActionDelegate {

    private final EventTriggerType eventType;

    private List eventAdapters;

    private List<EditPart> editParts;

    private EditPartViewer viewer;

    /**
     * Use one the static subclasses for specific type
     */
    private SetEventTypeAction(EventTriggerType type) {
        super();
        this.eventType = type;
    }

    /*
     * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        // do nothing
    }

    /*
     * @see IActionDelegate#run(IAction)
     */
    public void run(IAction action) {
        if (eventAdapters != null) {
            EventTriggerType newEventType;
            if (action.isChecked()) {
                newEventType = eventType;
            } else {
                newEventType = EventTriggerType.EVENT_NONE_LITERAL;
            }

            if (eventAdapters.size() > 0) {
                EObject eo =
                        (EObject) ((EventAdapter) eventAdapters.get(0))
                                .getTarget();
                IEditingDomainProvider ep =
                        (IEditingDomainProvider) EcoreUtil
                                .getExistingAdapter(eo.eResource(),
                                        IEditingDomainProvider.class);
                EditingDomain ed = ep.getEditingDomain();
                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.SetEventTypeAction_menu);

                for (Iterator iter = eventAdapters.iterator(); iter.hasNext();) {
                    EventAdapter eventAdapter = (EventAdapter) iter.next();

                    cmd.append(ChangeEventTriggerTypeCommand.create(ed,
                            (EObject) eventAdapter.getTarget(),
                            newEventType));

                }

                ed.getCommandStack().execute(cmd);
                
                //
                // Sid:
                // There can be a problem (not of our making) with
                // property sheets for the selected object (say a task).
                //
                // If there are different properties tabs for the new
                // task type then the General Tab normally detects the
                // change in task type and performs a refreshTabs().
                //
                // However if the last tab selected for a task was NOT
                // general and the task is deselected and reselected
                // then the general section is not necessarily reloaded.
                //
                // This means that when we change the typ[e the tabs
                // don't get changed. Therefore in order to force a
                // refresh we will perform force a reselect of the edipt
                // part which should filter up thru the system.
                
                // In order for selection change not to be ignored we
                // have to unset and reset the selection.
                viewer.getSelectionManager()
                        .setSelection(new StructuredSelection());
                viewer.getSelectionManager()
                        .setSelection(new StructuredSelection(editParts
                                .toArray()));
            }
        }
    }

    /*
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() > 0) {
                eventAdapters = new ArrayList();
                editParts = new ArrayList<EditPart>();

                EventTriggerType tt = null;

                for (Iterator iter = sel.iterator(); iter.hasNext();) {
                    Object obj = iter.next();
                    if (obj instanceof EventEditPart) {
                        viewer = ((EditPart) obj).getViewer();

                        EventAdapter adapter =
                                (EventAdapter) ((EventEditPart) obj)
                                        .getModelAdapter();

                        tt = adapter.getEventTriggerType();

                        if (!eventType.equals(tt)) {
                            eventAdapters.add(adapter);
                            editParts.add((EditPart) obj);
                        }

                    } else {
                        eventAdapters = null;
                        action.setEnabled(false);
                        action.setChecked(false);
                        return;
                    }
                }

                //
                // DOn't enable if there are events that implement interface
                // events.
                boolean enabled = true;
                for (Iterator iter = eventAdapters.iterator(); iter.hasNext();) {
                    EventAdapter eventAdapter = (EventAdapter) iter.next();

                    if (eventAdapter.isImplementingEvent()) {
                        enabled = false;
                    }
                }
                
                action.setEnabled(enabled);

                // If there are multiple selections then don't check any.
                if (sel.size() > 1) {
                    action.setChecked(false);
                } else if (tt != null) {
                    action.setChecked(eventType.equals(tt));
                }
                return;
            }
        }
        action.setEnabled(false);
        action.setChecked(false);
    }

    /**
     * ================================================================
     * <p>
     * Set to specific event type actions...
     * </p>
     * ================================================================
     */

    public static class NoneEvent extends SetEventTypeAction {
        public NoneEvent() {
            super(EventTriggerType.EVENT_NONE_LITERAL);
        }
    }

    public static class MessageCatchEvent extends SetEventTypeAction {
        public MessageCatchEvent() {
            super(EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);
        }
    }

    public static class MessageThrowEvent extends SetEventTypeAction {
        public MessageThrowEvent() {
            super(EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);
        }
    }

    public static class TimerEvent extends SetEventTypeAction {
        public TimerEvent() {
            super(EventTriggerType.EVENT_TIMER_LITERAL);
        }
    }

    public static class ErrorEvent extends SetEventTypeAction {
        public ErrorEvent() {
            super(EventTriggerType.EVENT_ERROR_LITERAL);
        }
    }

    public static class CancelEvent extends SetEventTypeAction {
        public CancelEvent() {
            super(EventTriggerType.EVENT_CANCEL_LITERAL);
        }
    }

    public static class CompensationCatchEvent extends SetEventTypeAction {
        public CompensationCatchEvent() {
            super(EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL);
        }
    }

    public static class CompensationThrowEvent extends SetEventTypeAction {
        public CompensationThrowEvent() {
            super(EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL);
        }
    }

    public static class ConditionalEvent extends SetEventTypeAction {
        public ConditionalEvent() {
            super(EventTriggerType.EVENT_CONDITIONAL_LITERAL);
        }
    }

    public static class MultipleCatchEvent extends SetEventTypeAction {
        public MultipleCatchEvent() {
            super(EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL);
        }
    }

    public static class MultipleThrowEvent extends SetEventTypeAction {
        public MultipleThrowEvent() {
            super(EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL);
        }
    }

    public static class TerminateEvent extends SetEventTypeAction {
        public TerminateEvent() {
            super(EventTriggerType.EVENT_TERMINATE_LITERAL);
        }
    }

    public static class LinkCatchEvent extends SetEventTypeAction {
        public LinkCatchEvent() {
            super(EventTriggerType.EVENT_LINK_CATCH_LITERAL);
        }
    }

    public static class LinkThrowEvent extends SetEventTypeAction {
        public LinkThrowEvent() {
            super(EventTriggerType.EVENT_LINK_THROW_LITERAL);
        }
    }

    public static class SignalCatchEvent extends SetEventTypeAction {
        public SignalCatchEvent() {
            super(EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL);
        }
    }

    public static class SignalThrowEvent extends SetEventTypeAction {
        public SignalThrowEvent() {
            super(EventTriggerType.EVENT_SIGNAL_THROW_LITERAL);
        }
    }

}
