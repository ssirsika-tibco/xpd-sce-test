/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.properties.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.TextFieldVerifier;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.LibraryFunctionUseIn;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * General property section for Process Script Library Function
 * 
 *
 * @author cbabar
 * @since Jan 9, 2024
 */
public class PslFunctionGeneralPropertySection extends AbstractTransactionalSection
		implements IFilter, TextFieldVerifier
{

	/**
	 * PSL function name label.
	 */
	private CLabel lblPSLFunctionName;

	/**
	 * PSL use in label.
	 */
	private CLabel				lblPSLUseIn;

	/**
	 * Text control for PSL function name.
	 */
	private Text				txtPSLFunctionName;

	private Button				useIn_Any_RadioBtn;

	private Button				useIn_ProcessManager_RadioBtn;

	private Button				useIn_WorkManager_RadioBtn;

	private CLabel				lblPSLParameter;
	
	private CLabel              lblPSLReturnType;

	/**
	 * Flag to inidicate whether PSL function name is valid or not.
	 */
	protected boolean			pslFunctionNameValid	= true;

	/**
	 * 
	 */
	private DataFieldTable	pslParametersTable;
	
	
	/**
	 * 
	 */
	private DataFieldTable	returnTypeTable;

	/**
	 * Error message.
	 */
	protected String			err					= null;

	/**
	 * General property section for PSL script function.
	 */
	public PslFunctionGeneralPropertySection()
	{
    }

	/**
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 *
	 * @param toTest
	 * @return
	 */
	@Override
	public boolean select(Object toTest)
	{
		return PslEditorUtil.isScriptLibraryFunction(toTest);
	}

	/**
	 * @see com.tibco.xpd.ui.properties.TextFieldVerifier#verifyText(org.eclipse.swt.widgets.Event)
	 *
	 * @param event
	 */
	@Override
	public void verifyText(Event event)
	{
		Text textControl = ((Text) event.widget);
		String text = textControl.getText(0, event.start - 1) + event.text
				+ textControl.getText(event.end, textControl.getCharCount() - 1);

		verifyPSLFunctionName(text);
	}

	/**
	 * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java.util.Collection)
	 *
	 * @param items
	 */
	@Override
	public void setInput(Collection< ? > items)
	{
		List<EObject> inputList = new ArrayList<EObject>();
		for (Object obj : items) {
			/** Add all activities except gateways to the list of inputs */
			EObject eo = null;

			if (obj instanceof EObject) {
				eo = (EObject) obj;
			} else if (obj instanceof IAdaptable) {
				eo = ((IAdaptable) obj).getAdapter(EObject.class);
			}

			if (eo instanceof Activity) {
				Activity activity = (Activity) eo;
				/** data fields tab must not be shown for gateways */
				if (activity.getRoute() == null) {
					inputList.add(eo);
				}
			}
		}

		super.setInput(inputList);
	}


	/**
	 * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
	 *
	 */
	@Override
	protected void doRefresh()
	{
		EObject input = getInput();
		if (pslParametersTable != null && pslParametersTable.getViewer() != null)
		{
			pslParametersTable.getViewer().cancelEditing();
			pslParametersTable.getViewer().setInput(input);
			pslParametersTable.reloadDeclaredTypes();
		}

		if (returnTypeTable != null && returnTypeTable.getViewer() != null)
		{
			returnTypeTable.getViewer().cancelEditing();
			returnTypeTable.getViewer().setInput(input);
			returnTypeTable.reloadDeclaredTypes();
		}

		/*
		 * Get selected Activity
		 */
		Activity act = getActivity();

		if (act != null && hasPSLFunctionNameChanged())
		{
			
			if (null != act.getName())
			{
				updateText(txtPSLFunctionName, act.getName());
			}
			else
			{
				updateText(txtPSLFunctionName, ""); //$NON-NLS-1$
			}
			
		}

		
		if (hasUseInStatusChanged())
		{
			/*
			 * Set "useIn" property
			 */
			LibraryFunctionUseIn useIn = (LibraryFunctionUseIn) Xpdl2ModelUtil.getOtherAttribute(act,
					XpdExtensionPackage.eINSTANCE.getDocumentRoot_UseIn());

			updateUseIn(useIn);
			
		}

	}

	/**
	 * Update the UI control raido buttons using the passed <object>LibraryFunctionUseIn<object> Enum.
	 * 
	 * If passed <object>LibraryFunctionUseIn<object> is null , we default it to "Any".
	 * 
	 * @param useIn
	 */
	private void updateUseIn(LibraryFunctionUseIn useIn)
	{

		boolean useInAny = useIn_Any_RadioBtn.getSelection();
		boolean useInProcessManager = useIn_ProcessManager_RadioBtn.getSelection();
		boolean useInWorkManager = useIn_WorkManager_RadioBtn.getSelection();

		if (useIn != null)
		{
			switch (useIn)
			{
				case ALL:
					useInAny = true;
					useInProcessManager = false;
					useInWorkManager = false;
					break;

				case PROCESS_MANAGER:
					useInProcessManager = true;
					useInAny = false;
					useInWorkManager = false;
					break;

				case WORK_MANAGER:
					useInWorkManager = true;
					useInAny = false;
					useInProcessManager = false;
					break;
			}
		}
		else
		{
			useInAny = true;
			useInProcessManager = false;
			useInWorkManager = false;
		}

		setUseInStatus(useIn_Any_RadioBtn, useInAny);
		setUseInStatus(useIn_ProcessManager_RadioBtn, useInProcessManager);
		setUseInStatus(useIn_WorkManager_RadioBtn, useInWorkManager);
	}





	/**
	 * Verify that the specified name text follows all naming conventions.
	 * 
	 * @param nameText
	 */
	protected void verifyPSLFunctionName(String nameText)
	{
		pslFunctionNameValid = true;

		if ((nameText == null || nameText.length() == 0))
		{

			pslFunctionNameValid = false;
			err = Messages.PSLPropertySection_NameEmpty;

		}
		else if (!NameUtil.isValidName(nameText, false))
		{

			pslFunctionNameValid = false;
			err = Messages.PSLElements_invalidNameErrorMessage;

		}
		else
		{
			if (getInputContainer() instanceof FlowContainer)
			{

				if (getDuplicateNameObject(((FlowContainer) getInputContainer()).getActivities(),
						getInput(), nameText) != null)
				{

					pslFunctionNameValid = false;
					err = Messages.PSLPropertySection_NameMustBeUnique;

				}

			}
		}

		if (!pslFunctionNameValid)
		{

			lblPSLFunctionName.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry().get(Xpdl2UiPlugin.IMG_ERROR));

			lblPSLFunctionName.setToolTipText(err);
			lblPSLFunctionName.getParent().layout(true);
			setCanFinish(false, err);

		}
		else
		{

			lblPSLFunctionName.setImage(null);
			lblPSLFunctionName.setToolTipText(""); //$NON-NLS-1$
			lblPSLFunctionName.getParent().layout(true);
			setCanFinish(true, Messages.PSLPropertyPanel_PAGEDESCRIPTION);
		}

	}



	/**
	 * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
	 *      com.tibco.xpd.ui.properties.XpdFormToolkit)
	 *
	 * @param parent
	 * @param toolkit
	 * @return
	 */
	@Override
	protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit)
	{

		GridData gridData = null;

		// Create a ScrolledComposite to contain the root container

		ScrolledComposite scrolledComposite = toolkit.createScrolledComposite(parent, SWT.V_SCROLL);
		scrolledComposite.setLayout(new GridLayout(1, true));
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Content root container composite within the ScrolledComposite
		Composite rootContainer = toolkit.createComposite(scrolledComposite, SWT.NONE);
		rootContainer.setLayout(new GridLayout(1, true));

		/*
		 * Create Layout Wrapper for all components.
		 */
		Composite layoutWrapper = toolkit.createComposite(rootContainer);
		GridLayout layoutWrapperLayout = new GridLayout(3, false);
		layoutWrapperLayout.verticalSpacing = 10;
		layoutWrapper.setLayout(layoutWrapperLayout);
		layoutWrapper.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		/*
		 * Label for PSL function name.
		 */
		lblPSLFunctionName = toolkit.createCLabel(layoutWrapper, Messages.PSLPropertyPanel_ScriptFunctionName_label);
		lblPSLFunctionName.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));
		lblPSLFunctionName.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry().get(Xpdl2UiPlugin.IMG_ERROR));

		/*
		 * Text box for PSL function name.
		 */
		txtPSLFunctionName = toolkit.createText(layoutWrapper, ""); //$NON-NLS-1$
		gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
		txtPSLFunctionName.setLayoutData(gridData);

		Composite spacer1 = toolkit.createComposite(layoutWrapper);
		GridData girdData1 = new GridData();
		girdData1.minimumWidth = 18;
		girdData1.widthHint = 18;
		/*
		 * SID: When creating a spacer, need to set heighHint, else layout will use an algorithm that gives spacer more
		 * height than the other controls on row - which we don't want.
		 */
		girdData1.heightHint = 2;
		spacer1.setLayoutData(girdData1);

		
		/**
		 * 
		 * manageControlUpdateOnDeactivate(<item>) method manages passed <item> state and resources. Adds the passed
		 * item to internal list of managed control section which enables the section to perform batch operations on
		 * these controls later.
		 * 
		 */
		manageControlUpdateOnDeactivate(txtPSLFunctionName);


		/*
		 * Label for PSL use in.
		 */
		lblPSLUseIn = toolkit.createCLabel(layoutWrapper, Messages.PSLPropertyPanel_UseIn_Label);
		lblPSLUseIn.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));
		lblPSLUseIn.setToolTipText(Messages.PSLPropertyPanel_UseIn_ToolTip);

		/*
		 * Create wrapper for Radio Button option for PSL Use in.
		 */
		Composite useInRaidoBtns = toolkit.createComposite(layoutWrapper);
		GridData gridData2 = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
		gridData2.horizontalSpan = 2;
		useInRaidoBtns.setLayoutData(gridData2);
		GridLayout gl = layoutWrapperLayout;
		useInRaidoBtns.setLayout(gl);

		/*
		 * Raido Btn for Use in: Any
		 */
		useIn_Any_RadioBtn = toolkit.createButton(useInRaidoBtns, Messages.PSLPropertyPanel_Any_Label, SWT.RADIO,
				"useInAny"); //$NON-NLS-1$
		useIn_Any_RadioBtn.setLayoutData(new GridData());
		manageControl(useIn_Any_RadioBtn);

		/*
		 * Raido Btn for Use in: Process Manager
		 */
		useIn_ProcessManager_RadioBtn = toolkit.createButton(useInRaidoBtns, Messages.PSLPropertyPanel_ProcessManager_Label,
				SWT.RADIO, "useInProcessManager"); //$NON-NLS-1$
		useIn_ProcessManager_RadioBtn.setLayoutData(new GridData());
		manageControl(useIn_ProcessManager_RadioBtn);

		/*
		 * Raido Btn for Use in: Work Manager
		 */
		useIn_WorkManager_RadioBtn = toolkit.createButton(useInRaidoBtns, Messages.PSLPropertyPanel_WorkManager_Label,
				SWT.RADIO, "useInWorkManager"); //$NON-NLS-1$
		useIn_WorkManager_RadioBtn.setLayoutData(new GridData());
		manageControl(useIn_WorkManager_RadioBtn);

		/*
		 * Label for Return Type.
		 */
		lblPSLReturnType = toolkit.createCLabel(layoutWrapper, "Wg");
		int approxLineHgt = lblPSLReturnType.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		lblPSLReturnType.setText(Messages.PSLPropertyPanel_ReturnType_Label);
		lblPSLReturnType.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		
		/*
		 * Create wrapper container for return field table section.
		 */

		returnTypeTable = new PslFunctionReturnTypeTable(layoutWrapper, toolkit, getEditingDomain());

		GridData gridData3 = new GridData(SWT.FILL, SWT.FILL, true, false); // Take all horizontal space.
		gridData3.heightHint = (approxLineHgt * 2) + (returnTypeTable.getBorderWidth() * 4) + (4 * 2);
		returnTypeTable.setLayoutData(gridData3);

		Composite spacer = toolkit.createComposite(layoutWrapper);
		GridData gridData4 = new GridData();
		gridData4.minimumWidth = 16;
		gridData4.widthHint = 16;
		/*
		 * SID: When creating a spacer, need to set heighHint, else layout will use an algorithm that gives spacer more
		 * height than the other controls on row - which we don't want.
		 */
		gridData4.heightHint = 2;
		spacer.setLayoutData(gridData4);

		/*
		 * Label for PSL Parameters.
		 */
		lblPSLParameter = toolkit.createCLabel(layoutWrapper, Messages.PSLPropertyPanel_Parameters_Label);
		lblPSLParameter.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		pslParametersTable = new PslFunctionParameterTable(layoutWrapper, toolkit, getEditingDomain());
		
		GridData gridData5 = new GridData(SWT.FILL, SWT.FILL, true, true); // Take all horizontal and vertical space.
		gridData5.minimumHeight = 100;
		gridData5.horizontalSpan = 2;
		pslParametersTable.setLayoutData(gridData5);


		// Set the content composite as the content of the ScrolledComposite
		scrolledComposite.setContent(rootContainer);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setMinSize(rootContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		return scrolledComposite;
	}

	/**
	 * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
	 *
	 * @param obj
	 * @return
	 */
	@Override
	protected Command doGetCommand(Object obj)
	{
		Command cmd = null;

		EditingDomain ed = getEditingDomain();

		Activity act = getActivity();

		if (obj == txtPSLFunctionName && pslFunctionNameValid && hasPSLFunctionNameChanged())
		{

			/*
			 * Handle PSL function name text control.
			 */
			if (txtPSLFunctionName.getText() != null)
			{

				cmd = new CompoundCommand("Set function name");

				((CompoundCommand) cmd).append(SetCommand.create(ed, act,
						Xpdl2Package.eINSTANCE.getNamedElement_Name(), txtPSLFunctionName.getText()));
			}

		}
		
		
		else if (obj == useIn_Any_RadioBtn && hasUseInStatusChanged())
		{
			if (getInput() instanceof OtherAttributesContainer)
			{
				return Xpdl2ModelUtil.getSetOtherAttributeCommand(getEditingDomain(),
						((OtherAttributesContainer) getInput()),
						XpdExtensionPackage.eINSTANCE.getDocumentRoot_UseIn(), LibraryFunctionUseIn.ALL);
			}
		}

		else if (obj == useIn_ProcessManager_RadioBtn && hasUseInStatusChanged())
		{
			if (getInput() instanceof OtherAttributesContainer)
			{
				return Xpdl2ModelUtil.getSetOtherAttributeCommand(getEditingDomain(),
						((OtherAttributesContainer) getInput()), XpdExtensionPackage.eINSTANCE.getDocumentRoot_UseIn(),
						LibraryFunctionUseIn.PROCESS_MANAGER);
			}
		}

		else if (obj == useIn_WorkManager_RadioBtn && hasUseInStatusChanged())
		{
			if (getInput() instanceof OtherAttributesContainer)
			{
				return Xpdl2ModelUtil.getSetOtherAttributeCommand(getEditingDomain(),
						((OtherAttributesContainer) getInput()), XpdExtensionPackage.eINSTANCE.getDocumentRoot_UseIn(),
						LibraryFunctionUseIn.WORK_MANAGER);
			}
		}

		return cmd;
	}

	/**
	 * Get Activity from the input.
	 * 
	 * @return <code>Activity</code> if input is valid, <b>null</b> otherwise.
	 */
	private Activity getActivity()
	{

		Object o = getInput();

		if (o instanceof Activity)
		{

			Activity act = (Activity) o;

			return act;
		}
		return null;
	}

	/**
	 * Get a duplicate named object in the specified list of Activities (if it exists), else return null.
	 * 
	 * @param activities
	 *            List of Activities to look in.
	 * @param input
	 *            EObject whose duplicate is to found out.
	 * @param nameText
	 *            Name text
	 * 
	 * @return a duplicate named object in the specified list of global signals (if it exists), else return null.
	 */
	private EObject getDuplicateNameObject(EList<Activity> activities, EObject input, String nameText)
	{

		/*
		 * Search for duplicate.
		 */

		for (Iterator iter = activities.iterator(); iter.hasNext();)
		{

			Activity eachAct = (Activity) iter.next();
			Activity act = (Activity) input;

			if (eachAct != act)
			{

				if (nameText.equals(eachAct.getName()))
				{

					return eachAct;
				}
			}
		}

		return null;

	}


	/**
	 * Function to determine the function name from UI TextControl and model are same or different.
	 * 
	 * @return true if the the Function name from UI and modal are different or else false.
	 */
	private boolean hasPSLFunctionNameChanged()
	{
		String functionNameText = txtPSLFunctionName.getText();
		String functionNameModelText = getActivity() != null ? getActivity().getName() : null;

		if (functionNameText == null)
		{
			functionNameText = ""; //$NON-NLS-1$
		}

		if (functionNameModelText == null)
		{
			functionNameModelText = ""; //$NON-NLS-1$
		}


		return !functionNameText.equals(functionNameModelText);
	}

	/**
	 * Function to determine the UseIn status from UI Raido Buttons and model are same or different.
	 * 
	 * @return true if the the UseIn status from UI Raido Button and modal are different or else false, if none of radio
	 *         button is selected we default it as a UseIn status has changed and return true.
	 */
	private boolean hasUseInStatusChanged()
	{
		// Getting the model value of useIn
		LibraryFunctionUseIn useIn = (LibraryFunctionUseIn) Xpdl2ModelUtil.getOtherAttribute(getActivity(),
				XpdExtensionPackage.eINSTANCE.getDocumentRoot_UseIn());
		
		
		if (useIn_Any_RadioBtn.getSelection())
		{
			return !LibraryFunctionUseIn.ALL.equals(useIn);
		}
		
		if (useIn_ProcessManager_RadioBtn.getSelection())
		{
			return !LibraryFunctionUseIn.PROCESS_MANAGER.equals(useIn);
		}

		if (useIn_WorkManager_RadioBtn.getSelection())
		{
			return !LibraryFunctionUseIn.WORK_MANAGER.equals(useIn);
		}

		// If none of radio button is selected we default it as a UseIn status has changed and return true.
		return true;
	}

	/**
	 * Set the passed useIn <object>status</object> for the raidoBtn, if passed status is not equal to current status.
	 * 
	 * @param radioBtn
	 * @param status
	 */
	private void setUseInStatus(Button radioBtn, boolean status)
	{
		if (radioBtn.getSelection() != status)
		{
			radioBtn.setSelection(status);
		}
	}


}
