/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityPickerControl;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityPickerControl.IActivityPickerListener;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ActivityPickerFilters.ValidEventHandlerInitialiserActivityFilter;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * LHS property details section for intermediate event activity used at the
 * start of an event handler flow
 *
 * @author aallway
 * @since 26 Jul 2019
 */
public class EventTriggerTypeIntermediateRequestSection extends AbstractFilteredTransactionalSection {

    private Composite concurrentFlowsContainer;

    private Button serializeConcurrentFlows;

    private Button allowConcurrentFlows;

    private Group eventHandlerGroup;

	/*
	 * ACE-6836 Re-enable Correlation Data and Hence Event initialisers for Incoming Request Event handlers and Event
	 * Sub-processes
	 */
	private Composite	initialisersConatiner;

	private Label		initialisersLabel;

	private ActivityPickerControl	initialiserPickerControl;

    /**
     * Sid ACE-2388 Check box for "Correlate Immediately", available on correlating activities.
     */
    private Group correlationGroup;

    protected Button correlateImmediately;

    public EventTriggerTypeIntermediateRequestSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    protected Composite doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout(2, false);
        root.setLayout(gl);

        /*
         * Sid ACE-2388 Add correlate immediately controls
         */
        correlationGroup =
                toolkit.createGroup(root, Messages.EventTriggerTypeIntermediateRequestSection_CorrelationGroup_label);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 2;

        correlationGroup.setLayoutData(gridData);
        correlationGroup.setLayout(new GridLayout(1, false));

        correlateImmediately = toolkit.createButton(correlationGroup,
                Messages.EventTriggerTypeIntermediateRequestSection_CorrelateImmediately_checkbox,
                SWT.CHECK);
        correlateImmediately.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(correlateImmediately);

        /*
         * Create the grup for event handlers and sub-processes
         */
        eventHandlerGroup =
                toolkit.createGroup(root, Messages.EventTriggerTypeMessageSection_EventHandlerDetailGroupHeader_label);
        eventHandlerGroup.setBackground(root.getBackground());
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 2;
        eventHandlerGroup.setLayoutData(gridData);
        GridLayout gLayout = new GridLayout(1, false);
        gLayout.horizontalSpacing = 0;
        gLayout.verticalSpacing = 0;
        eventHandlerGroup.setLayout(gLayout);

        hideUIgroup(eventHandlerGroup, true);

        createConcurrentFlowsControls(toolkit, eventHandlerGroup);

		/*
		 * ACE-6836 Re-enable Correlation Data and Hence Event initialisers for Incoming Request Event handlers and
		 * Event Sub-processes
		 */
        createInitialisersControls(toolkit, eventHandlerGroup);

        return root;
    }

    /**
     * Control the presence/absence of an entire group in the UI
     * 
     * @param group
     *            group to hide/show
     * @param hide
     */
    private void hideUIgroup(Group group, boolean hide) {

        GridData data = (GridData) group.getLayoutData();

        if (data.exclude != hide) {

            group.setVisible(!(data.exclude = hide));
            group.setLayoutData(data);
            group.layout();
            group.getParent().layout();

        }
    }

    /**
     * Sid XPD-3518: Added Concurrency handling configuration for event
     * handlers.
     * 
     * @param toolkit
     * @param root
     */
    private void createConcurrentFlowsControls(XpdFormToolkit toolkit, Composite root) {

        concurrentFlowsContainer = toolkit.createComposite(root, SWT.NONE);
        GridData gdLabel = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gdLabel.verticalIndent = 7;
        GridData gdContainer = new GridData(GridData.FILL_HORIZONTAL);
        gdContainer.horizontalSpan = 2;

        concurrentFlowsContainer.setLayoutData(gdContainer);
        concurrentFlowsContainer.setLayout(new GridLayout(1, false));

        serializeConcurrentFlows = toolkit.createButton(concurrentFlowsContainer,
                Messages.EventTriggerTypeMessageSection_SerializeConcurrent_button,
                SWT.RADIO);
        serializeConcurrentFlows.setToolTipText(Messages.EventTriggerTypeMessageSection_SerializeConcurrent_tooltip);
        serializeConcurrentFlows.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(serializeConcurrentFlows);

        allowConcurrentFlows = toolkit.createButton(concurrentFlowsContainer,
                Messages.EventTriggerTypeMessageSection_AllowConcurrent_label,
                SWT.RADIO);
        allowConcurrentFlows.setToolTipText(Messages.EventTriggerTypeMessageSection_AllowConcurrent_tooltip);
        allowConcurrentFlows.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(allowConcurrentFlows);


    }

	/**
	 * 
	 * ACE-6836 Re-enable Correlation Data and Hence Event initialisers for Incoming Request Event handlers and Event
	 * Sub-processes
	 * 
	 * @param arrActivities1
	 * @param arrActivities2
	 * @return @return true if lists contain the exactly the same elements. Element order is ignored.
	 */
	boolean isEqualsActivityLists(Collection<Activity> arrActivities1, Collection<Activity> arrActivities2)
	{

		boolean arrActivities1IsNull = arrActivities1 == null;
		boolean arrActivities2IsNull = arrActivities2 == null;

		if (arrActivities1IsNull ^ arrActivities2IsNull)
		{
			return false;
		}
		else if (arrActivities1IsNull && arrActivities2IsNull)
		{
			return true;
		}

		if (arrActivities1 != null && arrActivities2 != null && (arrActivities1.size() == arrActivities2.size()))
		{

			int elemNum = arrActivities1.size();

			Comparator<Activity> comp = new Comparator<Activity>()
			{

				@Override
				public int compare(Activity o1, Activity o2)
				{
					if ((o1 != null) || (o2 != null))
					{
						return o1.getId().compareTo(o2.getId());
					}
					return 0;
				}

			};

			// put in sorted collections
			if (elemNum >= 1)
			{
				Queue<Activity> q1 = new PriorityQueue<Activity>(elemNum, comp);
				for (Activity act : arrActivities1)
				{
					if (act != null)
					{ // prevent NPE if iniializer list contain deleted activity
						q1.offer(act);
					}
				}
				Queue<Activity> q2 = new PriorityQueue<Activity>(elemNum, comp);
				for (Activity act : arrActivities2)
				{
					if (act != null)
					{ // prevent NPE if iniializer list contain deleted activity
						q2.offer(act);
					}
				}

				// compare element-by-element
				while (!q1.isEmpty() && !q2.isEmpty())
				{
					String q1_ID = q1.remove().getId();
					String q2_ID = q2.remove().getId();
					if (!q1_ID.equals(q2_ID))
					{
						return false;
					}
				}
			}

			return true;
		}

		return false;
	}

	/**
	 * Add configuration to event handler activities to specify a list of activities after which the event handler will
	 * be initialised.
	 * 
	 * textbox for delimited list of initialisers + dialog button for initialisers selection
	 * 
	 * @param toolkit
	 * @param parent
	 */
	private void createInitialisersControls(XpdFormToolkit toolkit, Composite parent)
	{

		initialisersConatiner = toolkit.createComposite(parent, SWT.NONE);
		GridData gdContainer = new GridData(GridData.FILL_HORIZONTAL);
		initialisersConatiner.setLayoutData(gdContainer);

		GridLayout gl = new GridLayout(2, false);
		gl.marginHeight = gl.marginWidth = 7;
		initialisersConatiner.setLayout(gl);

		initialisersLabel = toolkit.createLabel(initialisersConatiner,
				Messages.EventTriggerTypeMessageSection_Initializers_label1, SWT.NONE);
		initialisersLabel.setToolTipText(Messages.EventTriggerTypeMessageSection_EventHandlerInitializersLabel_tooltip);
		GridData gdLabel = new GridData(SWT.NONE, SWT.BEGINNING, true, false);
		gdLabel.horizontalSpan = 2;
		initialisersLabel.setLayoutData(gdLabel);

		initialiserPickerControl = new ActivityPickerControl(initialisersConatiner, toolkit,
				Messages.ActivityPickerInstantiator_EventHandlerInitialiser_ActivityPicker_title2, SWT.MULTI)
		{
			@Override
			protected IFilter createActivityFilter()
			{
				return new ValidEventHandlerInitialiserActivityFilter();
			}
		};

		initialiserPickerControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		initialiserPickerControl.addActivitiesPickedListener(new IActivityPickerListener()
		{

			@Override
			public void activitiesPicked(Collection<Activity> selectedActivities)
			{
				EditingDomain ed = getEditingDomain();
				Activity eventAct = getActivity();

				if (eventAct != null && eventAct.getEvent() != null && ed != null)
				{

					Command cmd = selectCorrelationInitialisersCommand(ed, eventAct, selectedActivities);

					if (cmd != null)
					{
						ed.getCommandStack().execute(cmd);
					}
				}
			}
		});
	}

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang
     * .Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        CompoundCommand cmd = null;

        EditingDomain ed = getEditingDomain();
        Activity activity = getActivity();

        if (activity != null && ed != null && activity.getEvent() instanceof IntermediateEvent) {

            IntermediateEvent intermediateEvent = (IntermediateEvent) activity.getEvent();

            if (obj == serializeConcurrentFlows) {
                cmd = new CompoundCommand(Messages.EventTriggerTypeMessageSection_SetEventHandlerFlowStrategy_menu);

                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        intermediateEvent,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy(),
                        EventHandlerFlowStrategy.SERIALIZE_CONCURRENT));

            } else if (obj == allowConcurrentFlows) {
                cmd = new CompoundCommand(Messages.EventTriggerTypeMessageSection_SetEventHandlerFlowStrategy_menu);

                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        intermediateEvent,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy(),
                        EventHandlerFlowStrategy.ALLOW_CONCURRENT));

            } else if (obj == correlateImmediately) {
                /*
                 * Sid ACE-2388 handle correlate immediately setting.
                 */
                boolean isCorrelateImmediately = correlateImmediately.getSelection();
                cmd = new CompoundCommand(isCorrelateImmediately
                        ? Messages.EventTriggerTypeIntermediateRequestSection_SetCorrelateImmediatley_menu
                        : Messages.EventTriggerTypeIntermediateRequestSection_UnsetCorrelateImmediatley_menu);

                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        intermediateEvent,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_CorrelateImmediately(),
                        isCorrelateImmediately));

            }
        }
        return cmd;
    }

	/**
	 * Select initialiser activities via a picker dialog and update the <b>EventHandlerInitialisers</b> element to
	 * reflect the selection
	 * 
	 * ACE-6836 Re-enable Correlation Data and Hence Event initialisers for Incoming Request Event handlers and Event
	 * Sub-processes
	 * 
	 * @param ed
	 *            Editing domain
	 * @param trm
	 * @param activity
	 * @return COmmand to reset initialisers or null if nothing to do.
	 */
	private CompoundCommand selectCorrelationInitialisersCommand(EditingDomain ed, 
			Activity activity, Collection<Activity> selectedInitialisingActivities)
	{

		if (activity.getProcess() != null)
		{

			// Get any current activities
			List<Activity> currentInitialisingActivities = Collections.<Activity> emptyList();

			EventHandlerInitialisers evtHdlInitialisers = (EventHandlerInitialisers) Xpdl2ModelUtil.getOtherElement(
					activity, XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerInitialisers());

			if (evtHdlInitialisers != null)
			{
				currentInitialisingActivities = getEvtHandlerInitialisingActivities(evtHdlInitialisers);
			}

			// Any model amendments

			if (!isEqualsActivityLists(selectedInitialisingActivities, currentInitialisingActivities))
			{

				CompoundCommand cmd = new CompoundCommand(
						Messages.EventTriggerTypeMessageSection_EventHandlerInitializersSetCommand_name);

				removeEventHandlerInitialisers(ed, activity, evtHdlInitialisers, cmd);

				if (!selectedInitialisingActivities.isEmpty())
				{
					evtHdlInitialisers = createEventHandlerInitialisers(ed, activity, cmd);

					for (ActivityRef actRef : getListOfActivityRef(selectedInitialisingActivities))
					{
						evtHdlInitialisers.getActivityRef().add(actRef);
					}
				}

				return cmd;
			}
		}

		return null;
	}

	/**
	 * Construct a list of <code>ActivityRef</code>s from a collection of <code>Activity</code>s.
	 * 
	 * @param activities
	 * @return List of <code>ActivityRef</code>s
	 */
	private List<ActivityRef> getListOfActivityRef(Collection<Activity> activities)
	{

		// create list of EObjects for activities
		List<ActivityRef> activityRefs = new ArrayList<ActivityRef>();

		for (Activity activity : activities)
		{
			ActivityRef activityRef = XpdExtensionFactory.eINSTANCE.createActivityRef();
			activityRef.setIdRef(activity.getId());
			activityRefs.add(activityRef);
		}

		return activityRefs;
	}
	/**
	 * @param ed
	 * @param evtHdlInitialisers
	 * @param cCmd
	 *            CompoundCommand
	 */
	private void removeEventHandlerInitialisers(EditingDomain ed, Activity activity,
			EventHandlerInitialisers evtHdlInitialisers, CompoundCommand cCmd)
	{

		if (evtHdlInitialisers != null)
		{
			cCmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed, activity,
					XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerInitialisers(), evtHdlInitialisers));
		}
	}

	/**
	 * @param ed
	 *            Editing domain
	 * @param trm
	 *            TriggerResultMessage to attach EventHandlerInitialiser to
	 */
	private EventHandlerInitialisers createEventHandlerInitialisers(EditingDomain ed, Activity activity,
			CompoundCommand cCmd)
	{

		// Add ext model <EventHandlerInitialisers> to ##OTHER
		EventHandlerInitialisers model = XpdExtensionFactory.eINSTANCE.createEventHandlerInitialisers();
		cCmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed, activity,
				XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerInitialisers(), model));

		return model;
	}

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {

        if (concurrentFlowsContainer == null || concurrentFlowsContainer.isDisposed() || getActivity() == null) {
            return;
        }

        /*
         * Only show event handler controls for event handler activity
         */
        Activity activity = getActivity();

        if (activity != null && activity.getEvent() instanceof IntermediateEvent) {
            /*
             * Sid ACE-2388 handle correlate immediately setting.
             */
            boolean isCorrelateImmediately = Xpdl2ModelUtil.getOtherAttributeAsBoolean(activity.getEvent(),
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_CorrelateImmediately());

            correlateImmediately.setSelection(isCorrelateImmediately);

            /*
             * Handle event handler options
             */
            boolean showEventHandlerOptions = Xpdl2ModelUtil.isEventHandlerActivity(activity)
                    && !Xpdl2ModelUtil.isPageflowOrSubType(activity.getProcess());

            hideUIgroup(eventHandlerGroup,
                    !showEventHandlerOptions || !ProcessDestinationUtil.isBPMDestinationSelected(activity.getProcess()));

            if (showEventHandlerOptions) {

                EventHandlerFlowStrategy flowStrategy =
                        (EventHandlerFlowStrategy) Xpdl2ModelUtil.getOtherAttribute(activity.getEvent(),
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy());

                if (EventHandlerFlowStrategy.ALLOW_CONCURRENT.equals(flowStrategy)) {
                    serializeConcurrentFlows.setSelection(false);
                    allowConcurrentFlows.setSelection(true);

                } else if (EventHandlerFlowStrategy.SERIALIZE_CONCURRENT.equals(flowStrategy)) {
                    serializeConcurrentFlows.setSelection(true);
                    allowConcurrentFlows.setSelection(false);

                } else {
                    serializeConcurrentFlows.setSelection(false);
                    allowConcurrentFlows.setSelection(false);
                }

				/*
				 * ACE-6836 Re-enable Correlation Data and Hence Event initialisers for Incoming Request Event handlers
				 * and Event Sub-processes
				 */
				// Get current initialiser activities
				List<Activity> currentInitialisingActivities = Collections.<Activity> emptyList();

				EventHandlerInitialisers evtHdlInitialisers = (EventHandlerInitialisers) Xpdl2ModelUtil.getOtherElement(
						activity, XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerInitialisers());

				if (evtHdlInitialisers != null)
				{
					currentInitialisingActivities = getEvtHandlerInitialisingActivities(evtHdlInitialisers);
				}

				initialiserPickerControl.setActivities(activity.getProcess(), currentInitialisingActivities);
            }
        }
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        if (super.shouldRefresh(notifications)) {
            return true;
        }

        Activity activity = getActivity();

        if (activity != null) {
            String activityId = activity.getId();

            for (Notification notification : notifications) {
                /*
                 * Refresh is connection made/removed from event (could change
                 * whether it is an event handler or not.
                 */
                if (Notification.SET == notification.getEventType()
                        && Xpdl2Package.eINSTANCE.getTransition_To().equals(notification.getFeature())
                        && notification.getNotifier() instanceof Transition) {

                    if (activityId.equals(notification.getOldValue())
                            || activityId.equals(notification.getNewValue())) {
                        return true;
                    }

                } else if (Notification.ADD == notification.getEventType()
                        && Xpdl2Package.eINSTANCE.getFlowContainer_Transitions().equals(notification.getFeature())
                        && notification.getNewValue() instanceof Transition) {

                    if (activityId.equals(((Transition) notification.getNewValue()).getTo())) {
                        return true;
                    }

                } else if (Notification.REMOVE == notification.getEventType()
                        && Xpdl2Package.eINSTANCE.getFlowContainer_Transitions().equals(notification.getFeature())
                        && notification.getOldValue() instanceof Transition) {

                    if (activityId.equals(((Transition) notification.getOldValue()).getTo())) {
                        return true;
                    }
                }

            }
        }

        return false;
    }

	/**
	 * ACE-6836 Re-enable Correlation Data and Hence Event initialisers for Incoming Request Event handlers and Event
	 * Sub-processes
	 * 
	 * @param evtHdlInitialisers
	 * @return the activities explicitly mentioned that an Event Handler may be initialised by. Null if no Event Handler
	 *         Initialising element present.
	 */
	private List<Activity> getEvtHandlerInitialisingActivities(EventHandlerInitialisers evtHdlInitialisers)
	{

		List<Activity> ret = Collections.<Activity> emptyList();

		if (evtHdlInitialisers != null)
		{

			List<ActivityRef> activityRefs = evtHdlInitialisers.getActivityRef();
			if (activityRefs != null && !activityRefs.isEmpty())
			{

				ret = new ArrayList<Activity>();
				for (ActivityRef activityRef : activityRefs)
				{
					ret.add(activityRef.getActivity());
				}
			}

		}

		return ret;
	}

}
