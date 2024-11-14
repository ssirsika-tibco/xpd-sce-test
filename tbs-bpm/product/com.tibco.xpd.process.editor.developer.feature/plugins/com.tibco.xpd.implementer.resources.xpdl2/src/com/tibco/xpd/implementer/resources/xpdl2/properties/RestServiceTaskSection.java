/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;

import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter.RsoType;
import com.tibco.xpd.implementer.resources.xpdl2.utils.ProcessDeveloperUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.editorHandler.IDisplayEObject;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.resources.ui.util.ShowViewUtil;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.ui.components.RestMethodPicker;
import com.tibco.xpd.rsd.ui.editor.RsdEditorOpener;
import com.tibco.xpd.rsd.wc.RsdIndexProvider;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Property section for REST service tasks.
 * 
 * @author nwilson
 * @since 13 Feb 2015
 */
public class RestServiceTaskSection extends AbstractTransactionalSection {

    private Label service;

	private CLabel serviceLabel;

    private Label resource;

    private CLabel resourceLabel;

    private Hyperlink method;

	private Label methodText;

    private CLabel methodLabel;

    private Composite root;

    private Button clear;

    private CLabel endpointLabel;

    private Hyperlink endpoint;

    private Button browseEndpoint;

    private Button clearEndpoint;

    /**
	 * Attribute to store the type of the RSO aasociated with an activity.
	 */
	private RsoType rsoType;

	/**
	 * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
	 *      com.tibco.xpd.ui.properties.XpdFormToolkit)
	 * 
	 * @param parent
	 * @param toolkit
	 * @return
	 */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));

		CLabel operationLabel =
				toolkit.createCLabel(root,
                        Messages.RestServiceTaskSection_OperationLabel);
        operationLabel.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false,
                false));
        Composite operationPanel = toolkit.createComposite(root);
        operationPanel.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false,
                false));
        operationPanel.setLayout(new FillLayout());
        Button select =
                toolkit.createButton(operationPanel,
                        Messages.RestServiceTaskSection_SelectLabel,
                        SWT.PUSH);
        select.setToolTipText(Messages.RestServiceTaskSection_SelectTooltip);
        clear =
                toolkit.createButton(operationPanel,
                        Messages.RestServiceTaskSection_ClearLabel,
                        SWT.PUSH);

        serviceLabel =
				toolkit.createCLabel(root,
                        Messages.RestServiceTaskSection_ServiceLabel);
        serviceLabel.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false,
                false));
        service = toolkit.createLabel(root, ""); //$NON-NLS-1$
        service.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, true, false));

        resourceLabel =
				toolkit.createCLabel(root,
                        Messages.RestServiceTaskSection_ResourceLabel);
        resourceLabel.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false,
                false));
        resource = toolkit.createLabel(root, ""); //$NON-NLS-1$
        resource.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, true, false));

		methodLabel = toolkit.createCLabel(root, Messages.RestServiceTaskSection_MethodLabel);
		methodLabel.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false, false));

		// Nikita ACE-8267 For Swagger operation references, do not show a hyperlink because we do not have our own
		// specific swagger editor to navigate to
		Composite methodPanel = toolkit.createComposite(root);
		methodPanel.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false, false));
		GridLayout methodGL = new GridLayout();
		methodGL.marginTop = methodGL.marginBottom = methodGL.marginLeft = methodGL.marginRight = 0;
		methodPanel.setLayout(methodGL);

		// method or methodText will be shown optionally based on the selected method reference
		method = toolkit.createHyperlink(methodPanel, "", SWT.NONE); //$NON-NLS-1$
		methodText = toolkit.createLabel(methodPanel, "", SWT.NONE); //$NON-NLS-1$

		endpointLabel =
                toolkit.createCLabel(root,
                        Messages.RestServiceTaskSection_EndpointLabel);
        endpointLabel.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false,
                false));

        Composite endpointComposite = toolkit.createComposite(root, SWT.NONE);

        GridData gd = new GridData();

        endpointComposite.setLayoutData(gd);

        GridLayout gl = new GridLayout(3, false);
        gl.marginLeft = 0;
        endpointComposite.setLayout(gl);

        endpoint =
                toolkit.createHyperlink(endpointComposite,
                        Messages.RestServiceTaskSection_NoEndpointLabel,
                        SWT.NONE);

        browseEndpoint =
                toolkit.createButton(endpointComposite,
                        Messages.RestServiceTaskSection_EndpointBrowserButton,
                        SWT.PUSH);
        browseEndpoint.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false,
                false));
        clearEndpoint =
                toolkit.createButton(endpointComposite,
                        Messages.RestServiceTaskSection_EndpointClearButton,
                        SWT.PUSH);
        clearEndpoint.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false,
                false));

        manageControl(browseEndpoint);
        manageControl(clearEndpoint);

        /*
         * Hyperlink listener to select participant in project explorer.
         */
        endpoint.addHyperlinkListener(new HyperlinkAdapter() {

            @Override
            public void linkActivated(HyperlinkEvent e) {

                EObject input = getInput();

                if (input instanceof Activity) {

                    Activity activity = (Activity) input;

                    RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

                    if (rsta.isRestServiceImplementation(activity)) {

                        Package pkg = Xpdl2ModelUtil.getPackage(activity);

                        if (pkg != null) {

                            EList<Performer> performers =
                                    activity.getPerformerList();

                            if (performers.size() == 1) {

                                Performer performer = performers.get(0);

                                Participant participant =
                                        pkg.getParticipant(performer.getValue());

                                /*
                                 * Select the participant in project explorer.
                                 */

                                try {

                                    ShowViewUtil
                                            .selectItemInProjectExplorer(participant);

                                } catch (PartInitException e1) {

                                    Activator.getDefault().getLogger()
                                            .error(e1);

                                }
                            }
                        }
                    }
                }
            }
        });

        method.addHyperlinkListener(new HyperlinkAdapter() {

            @Override
            public void linkActivated(HyperlinkEvent e) {
                RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
                IndexerItem item = getMethodIndexerItem(rsta);
                IFile file = rsta.getFile(item);
                Method method = rsta.getRSOMethod(item);
                if (file != null && method != null) {
                    RsdEditorOpener opener = new RsdEditorOpener();
                    IEditorPart editor = opener.openEditor(file);
                    if (editor instanceof IDisplayEObject) {
                        IDisplayEObject go = (IDisplayEObject) editor;
                        go.gotoEObject(true, method);
                    }
                }
            }
        });

        select.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // Show RSD Method picker.
                RestMethodPicker picker = new RestMethodPicker();
				// Nikita-8267 The picker dialog will now return a PickerItem instead of the specific types
				PickerItem pickerItem = picker.pickRestMethod(getControl().getShell());
				if (pickerItem != null) {
					setRestMethod(pickerItem);
                }
            }
        });
        manageControl(clear);

        return root;
    }

    /**
	 * @param pickerItem
	 *            The picker item from REST Service method selection dialog
	 */
	protected void setRestMethod(final PickerItem pickerItem)
	{
        EObject input = getInput();
        CompoundCommand cmd = null;
        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
            cmd =
                    rsta.getSetMethodCommand(getEditingDomain(),
                            activity,
							pickerItem);

            /*
             * XPD-7739: If the current activity label is the default label then
             * set the Activity label to 'methodName - httpType resourcename'
             */
            if (doesTaskOrEventLabelStartWithDefaultLabel(activity)) {

                String displayName = Xpdl2ModelUtil.getDisplayName(activity);

                String internalName =
                        NameUtil.getInternalName(displayName, false);

                /*
                 * If the current name is generated from the current label then
                 * we should set the new name as well.
                 */
                boolean shouldSetName =
                        internalName != null
                                && internalName.equals(activity.getName());

				/*
				 * create a display label
				 */
				String activityDisplayName = String.format("%1$s - %2$s %3$s", //$NON-NLS-1$
						pickerItem.getName(), pickerItem.get("httpMethod"), pickerItem.get("resourceName")); //$NON-NLS-1$ //$NON-NLS-2$

				RestServiceTaskSection.setRestActivityLabelAndName(activity, activityDisplayName, activity.getProcess(),
						shouldSetName, cmd, getEditingDomain());

            }

            if (cmd != null && cmd.canExecute()) {
                getEditingDomain().getCommandStack().execute(cmd);
            }
        }
    }

    /**
     * Sets the Rest Activity label to 'methodName - httpType resourcename',
     * also generates the internal name and sets it as the activity name. This
     * method works for both activities attached to model and activities not
     * attached to model based on the value of the cmd passed.
     * 
     * @param activity
     *            the Rest Activity
     * @param method
     *            the REst Service Method
     * @param targetProcess
     *            the target process
     * @param shouldSetName
     *            pass <code>true</code> if we wish to generate internal name
     *            from the new display name and set it as the activity name.
     * @param cmd
     *            the compound command in case we wish to fire a command on the
     *            stack
     * @param ed
     */
    public static void setRestActivityLabelAndName(Activity activity,
			String displayName, Process targetProcess, boolean shouldSetName,
			CompoundCommand cmd, EditingDomain ed)
	{
        /*
         * make the label unique
         */
        String uniqueLabelInSet =
                Xpdl2ModelUtil.getUniqueLabelInSet(displayName,
                        Xpdl2ModelUtil.getAllActivitiesInProc(targetProcess));

        if (cmd != null && ed != null) {
            /*
             * If we have the command then append the command to change the
             * display name to the command.
             */
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(ed,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            uniqueLabelInSet));

        } else {

            Xpdl2ModelUtil
                    .setOtherAttribute(activity, XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName(), uniqueLabelInSet);
        }

        if (shouldSetName) {
            /*
             * set the activity name
             */
            String internalName =
                    NameUtil.getInternalName(uniqueLabelInSet, false);

            if (cmd != null && ed != null) {
                /*
                 * If we have the command then append the command to change the
                 * name to the command.
                 */
                cmd.append(SetCommand.create(ed,
                        activity,
                        Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                        internalName));

            } else {
                activity.setName(internalName);
            }
        }
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the passed event or task name starts with
     *         its default name, else returns <code>false</code>
     */
    public static boolean doesTaskOrEventLabelStartWithDefaultLabel(
            Activity activity) {

        String displayName = Xpdl2ModelUtil.getDisplayName(activity);

        if (displayName != null && !displayName.isEmpty()) {

            if (activity.getImplementation() != null) {
                /*
                 * for tasks get the Display name from task type
                 */
                TaskType taskTypeStrict =
                        TaskObjectUtil.getTaskTypeStrict(activity);

                if (taskTypeStrict != null
                        && displayName.startsWith(taskTypeStrict.toString())) {
                    return true;
                }

            } else if (activity.getEvent() != null) {
                /*
                 * get the default event name
                 */
                String defaultEventName =
                        ElementsFactory
                                .getDefaultNameForEventType(EventObjectUtil
                                        .getFlowType(activity), EventObjectUtil
                                        .getEventTriggerType(activity));

                if (displayName.startsWith(defaultEventName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        Object input = getInput();
        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
            RestServiceOperation rso = getRestServiceOperation(rsta);
            IndexerItem item = rsta.getMethodIndexerItem(rso);

			resetMethodControls();

			// ACE-8267 Optionally set either method or methodText components to be visible and enabled
            if (item != null) {

                service.setText(item.get(RsdIndexProvider.SERVICE_NAME));
				resource.setText(item.get(RsdIndexProvider.RESOURCE_NAME));
                setCLabelIcon(methodLabel,
                        null,
                        Messages.RestServiceTaskSection_RSOResourceMethod_Tooltip);

				if (item.getType().equals(RestMethodPicker.SWAGGER_METHOD_TYPE))
				{
					methodText.setText(item.getName() + " (" //$NON-NLS-1$
							+ item.get(RsdIndexProvider.HTTP_METHOD) + ")"); //$NON-NLS-1$

					showSelectedMethodControl(methodText, true);
				}
				else
				{
					method.setText(item.getName() + " (" //$NON-NLS-1$
							+ item.get(RsdIndexProvider.HTTP_METHOD) + ")"); //$NON-NLS-1$

					showSelectedMethodControl(method, true);
				}


            } else if (rso != null) {

                service.setText(Messages.RestServiceTaskSection_UnresolvedReferenceLabel);
                resource.setText(Messages.RestServiceTaskSection_UnresolvedReferenceLabel);
                method.setText(String
                        .format(Messages.RestServiceTaskSection_UnresolvedMethodReferenceLabel,
                                rso.getLocation()));

                setCLabelIcon(methodLabel,
                        Xpdl2UiPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2UiPlugin.IMG_ERROR),
                        Messages.RestServiceTaskSection_UnresolvedRSOReference_Tooltip);

				// ACE-8267 Set method component to be visible but it is not enabled
				showSelectedMethodControl(method, false);

            } else {

                service.setText(Messages.RestServiceTaskSection_NoRSOSelected_Label);
                resource.setText(Messages.RestServiceTaskSection_NoRSOSelected_Label);
                method.setText(Messages.RestServiceTaskSection_NoRSOSelected_Label);

                setCLabelIcon(methodLabel,
                        Xpdl2UiPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2UiPlugin.IMG_ERROR),
                        Messages.RestServiceTaskSection_NoRSOSelected_Tooltip);

				// ACE-8267 Set method component to be visible but it is not enabled
				showSelectedMethodControl(method, false);
            }

            String endpointName = ""; //$NON-NLS-1$
            Participant participant = getRestParticipant(activity);
            if (participant != null) {
                endpointName = participant.getName();
            }

            boolean isValidEndpoint =
                    endpointName != null && !endpointName.isEmpty();
            endpoint.setText(isValidEndpoint ? endpointName
                    : Messages.RestServiceTaskSection_NoEndpointLabel);

            endpoint.setEnabled(isValidEndpoint);

            if (isValidEndpoint) {

                setCLabelIcon(endpointLabel,
                        null,
                        Messages.RestServiceTaskSection_RSOEndpoint_Tooltip);

            } else {

                setCLabelIcon(endpointLabel,
                        Xpdl2UiPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2UiPlugin.IMG_ERROR),
                        Messages.RestServiceTaskSection_NoEndpointSelected_Tooltip);
            }

            /*
             * XPD-7727: Saket: Always enable the endpoint picker irrespective
             * of the activity selected.
             */

            root.layout();

			// Nikita ACE-8255 We need to refresh tabs ONLY IF if the RSO type has changed from Swagger Operation to RSD
			// Method and vice-versa
			// To check this use 'rsoType' flag's previous value and compare
			setRSOTypeAndRefresh(activity);
        }
    }

	/**
	 * Sets the value of the class-level flag 'rsoType'. If the value has changed refreshTabs() will be called - which
	 * essentially refreshes all sections in the property sheet
	 * 
	 * @param activity
	 * @param rsta
	 */
	private void setRSOTypeAndRefresh(Activity activity)
	{
		RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

		// If there was a previously set rso type
		if (null != rsoType)
		{
			// Get the current rso type for an activity
			RsoType activityRSOType = rsta.getRsoType(activity);

			// Check if it has changed
			if (!rsoType.equals(activityRSOType))
			{
				/*
				 * If yes, update the rsoType to the current type, then refresh tabs and exit
				 */
				rsoType = activityRSOType;
				Display.getCurrent().asyncExec(new Runnable()
				{
					@Override
					public void run()
					{
						refreshTabs();
					}
				});
				return;
			}

		}
		else
		{
			// Else simply set the rso type.
			rsoType = rsta.getRsoType(activity);
		}
	}

	/**
	 * Show the selected control by setting the visibility and layout data. Also sets the enabled/disabled state based
	 * on the value passed
	 * 
	 * @param aControl
	 * @param isEnabled
	 */
	private void showSelectedMethodControl(Control aControl, boolean isEnabled)
	{
		aControl.setVisible(true);
		aControl.setEnabled(isEnabled);
		aControl.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false, false));
	}
	
	/**
	 * Reset the method hyperlink(that shows RSD method) and methodText Label(that shows Swagger Operation) values and
	 * hide them by default
	 */
	private void resetMethodControls()
	{
		// Reset method
		method.setVisible(false);
		method.setEnabled(false);
		method.setText(null);

		// Reset methodText
		methodText.setVisible(false);
		methodText.setText(""); //$NON-NLS-1$

		// Hide both controls
		GridData gd = new GridData();
		gd.heightHint = 1;
		method.setLayoutData(gd);
		methodText.setLayoutData(gd);
	}

    /**
     * @return The indexer item for the currently selected method.
     */
    private IndexerItem getMethodIndexerItem(RestServiceTaskAdapter rsta) {
        RestServiceOperation rso = getRestServiceOperation(rsta);
        return rsta.getMethodIndexerItem(rso);
    }

    /**
     * @return The REST Service Operation for the currently selected method.
     */
    private RestServiceOperation getRestServiceOperation(
            RestServiceTaskAdapter rsta) {
        RestServiceOperation rso = null;
        EObject input = getInput();
        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            OtherElementsContainer rsoParent = rsta.getRSOContainer(activity);
            rso = rsta.getRSO(rsoParent);
        }
        return rso;
    }

    /**
     * @param activity
     *            The activity to check.
     * @return the REST participant or null.
     */
    private Participant getRestParticipant(Activity activity) {
        Participant restParticipant = null;
        for (Object next : activity.getPerformerList()) {
            if (next instanceof Performer) {
                Performer performer = (Performer) next;
                Object participantOrProcessData =
                        Xpdl2ModelUtil.getParticipantOrProcessData(activity
                                .getProcess(), performer);
                if (participantOrProcessData instanceof Participant) {
                    Participant participant =
                            (Participant) participantOrProcessData;
                    ParticipantTypeElem type = participant.getParticipantType();
                    if (ParticipantType.SYSTEM_LITERAL.equals(type.getType())) {
                        Object psrObject =
                                Xpdl2ModelUtil
                                        .getOtherElement(participant,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ParticipantSharedResource());
                        if (psrObject instanceof ParticipantSharedResource) {
                            ParticipantSharedResource psr =
                                    (ParticipantSharedResource) psrObject;
                            if (psr.getRestService() != null) {
                                restParticipant = participant;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return restParticipant;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        EObject input = getInput();
        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            final RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
            if (obj == clear) {
                final OtherElementsContainer rsoParent =
                        rsta.getRSOContainer(activity);
                if (rsoParent != null) {
                    cmd =
                            new RecordingCommand(
                                    (TransactionalEditingDomain) getEditingDomain(),
                                    Messages.RestServiceTaskSection_ClearCommandLabel) {

                                @Override
                                protected void doExecute() {
                                    RestServiceOperation rso =
                                            rsta.getRSO(rsoParent);
                                    if (rso != null) {
                                        Xpdl2ModelUtil
                                                .removeOtherElement(rsoParent,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_RestServiceOperation(),
                                                        rso);
                                    }
                                }

                            };
                }
            } else if (obj == clearEndpoint) {
                Performers existingPerformers = activity.getPerformers();
                if (existingPerformers != null) {
                    if (existingPerformers.getPerformers().size() > 0) {
                        Performers performers =
                                Xpdl2Factory.eINSTANCE.createPerformers();
                        CompoundCommand cc =
                                new CompoundCommand(
                                        Messages.RestServiceTaskSection_EndpointClearCommand);
                        cc.append(new SetCommand(
                                getEditingDomain(),
                                activity,
                                Xpdl2Package.eINSTANCE.getActivity_Performers(),
                                performers));
                        cmd = cc;
                    }
                }
            } else if (obj == browseEndpoint) {

                cmd =
                        ProcessDeveloperUtil
                                .getSetRESTServiceParticipantFromPickerCommand(getEditingDomain(),
                                        activity);
            }
        }
        return cmd;
    }
}
