package com.tibco.bx.emulation.ui.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

public class EmulationAssertionPropertySection extends AbstractEmulationPropertySection implements PropertyChangeListener{
	private Button accessibleButton;
	private Button unAccessibleButton;
	public EmulationAssertionPropertySection() {
		super(EmulationPackage.eINSTANCE.getAssertion());
	}
	
	@Override
	public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
		Composite composite = toolkit.createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		
		accessibleButton = getWidgetFactory().createButton(composite, Messages.getString("EnableAssertionAction_LABEL"), SWT.RADIO); //$NON-NLS-1$
		accessibleButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event) {
				getEditingDomain().getCommandStack().execute(getUpdateAssertionCommand(true));
			}
			
		});
		unAccessibleButton = getWidgetFactory().createButton(composite, Messages.getString("DisableAssertionAction_LABEL"), SWT.RADIO); //$NON-NLS-1$
		unAccessibleButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event) {
				getEditingDomain().getCommandStack().execute(getUpdateAssertionCommand(false));
			}
		});
		return composite;
	}

	protected void doRefresh(){
		if(getInput() != null){
			accessibleButton.setSelection(((Assertion)getInput()).isAccessible());
			unAccessibleButton.setSelection(!((Assertion)getInput()).isAccessible());
		}
	}
	
	private Command getUpdateAssertionCommand(boolean ass){
		EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getAssertion_Accessible();
		return SetCommand.create(getEditingDomain(), (Assertion)getInput(), eStructuralFeature, ass);
	}
	
	
	@Override
	public void dispose() {
		if((Assertion)getInput() != null){
			WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopyFor((Assertion)getInput());
			if(workingCopy != null)
				workingCopy.removeListener(this);
		}
		super.dispose();
	}

	public void propertyChange(PropertyChangeEvent event) {
		String propName = event.getPropertyName();
		if (propName.equals(WorkingCopy.PROP_DIRTY) || propName.equals(WorkingCopy.CHANGED)) {
			setInput(getPart(), getSelection());
			getPropertySheetPage().labelProviderChanged(null);
		}
	}
}
