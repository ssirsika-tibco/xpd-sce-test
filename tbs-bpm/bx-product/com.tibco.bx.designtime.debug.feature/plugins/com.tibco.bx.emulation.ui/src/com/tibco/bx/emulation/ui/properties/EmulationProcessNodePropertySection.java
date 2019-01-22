package com.tibco.bx.emulation.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

public class EmulationProcessNodePropertySection extends AbstractEmulationPropertySection{
	private Text aliasText;
	private Text descText;
	public EmulationProcessNodePropertySection() {
		super(EmulationPackage.eINSTANCE.getProcessNode());
	}

	protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
		Composite composite = toolkit.createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		Label label = toolkit.createLabel(composite, Messages.getString("EmulationProcessNodePropertySection.aliasLabel.text")); //$NON-NLS-1$
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
		label.setLayoutData(gridData);
		
		aliasText = toolkit.createText(composite, "", SWT.FLAT);//$NON-NLS-1$
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		aliasText.setLayoutData(gridData);
		manageControl(aliasText);
		
		label = toolkit.createLabel(composite, Messages.getString("EmulationProcessNodePropertySection.descLabel.text")); //$NON-NLS-1$
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
		
		descText = toolkit.createText(composite, "", SWT.FLAT | SWT.MULTI);//$NON-NLS-1$
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		descText.setLayoutData(gridData);
		manageControl(descText);
		
		return composite;
	}

	@Override
	protected Command doGetCommand(Object obj) {
		if(obj == aliasText){
			EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Alias();
			return SetCommand.create(getEditingDomain(), getInput(), eStructuralFeature, aliasText.getText());
		}else if(obj == descText){
			EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Description();
			return SetCommand.create(getEditingDomain(), getInput(), eStructuralFeature, descText.getText());
		}
		return null;
	}

	@Override
	protected void doRefresh() {
		Object input = getInput();
		if (input == null) {
		      updateText(aliasText, "");//$NON-NLS-1$
		      updateText(descText, "");//$NON-NLS-1$
		      return;
		 }else if(input instanceof ProcessNode){
			 updateText(aliasText, ((ProcessNode)input).getAlias());
		     updateText(descText, ((ProcessNode)input).getDescription());
		 }
		return;
	}

}




