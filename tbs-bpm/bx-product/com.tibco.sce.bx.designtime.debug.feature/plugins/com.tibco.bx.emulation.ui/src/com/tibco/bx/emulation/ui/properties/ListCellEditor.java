package com.tibco.bx.emulation.ui.properties;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

import com.tibco.bx.emulation.core.common.IVariableElementList;
import com.tibco.bx.emulation.ui.EmulationUIActivator;

public class ListCellEditor extends DialogCellEditor implements TraverseListener{

	
	Button imgButton;
	IVariableElementList elementList;
	
	
	
	public ListCellEditor(Composite parent, IVariableElementList elementList) {
		super(parent);
		this.elementList= elementList;
	}
	/**
	 * return null, never show a Dialog
	 */
	protected Object openDialogBox(Control cellEditorWindow) {
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.DialogCellEditor#createButton(org.eclipse.swt.widgets.Composite)
	 */
	protected Button createButton(Composite parent) {
		imgButton = super.createButton(parent);
		imgButton.setText(""); //$NON-NLS-1$
		imgButton.setImage(EmulationUIActivator.getDefault().getImageRegistry().get(EmulationUIActivator.IMG_EM_ADD_EN));
		imgButton.addTraverseListener(this);
		return imgButton;
	}
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.DialogCellEditor#createControl(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createControl(Composite parent) {
		Control control = super.createControl(parent);
		imgButton.addSelectionListener(new SelectionAdapter(){

			public void widgetSelected(SelectionEvent e) {
				elementList.createVariableElement();
				fireApplyEditorValue();
			}
			
		});
		return control;
	}
	
	@Override
	public void keyTraversed(TraverseEvent e) {
		Event event = new Event();   
        event.time = e.time;   
        event.character = e.character;   
        getControl().notifyListeners(SWT.Traverse, event);   
	}

	
}
