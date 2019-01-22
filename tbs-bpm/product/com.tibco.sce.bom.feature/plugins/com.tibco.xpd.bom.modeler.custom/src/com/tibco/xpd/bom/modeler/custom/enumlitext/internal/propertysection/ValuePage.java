/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.internal.propertysection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.part.Page;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.ui.properties.XpdFormToolkit;

/*
 * Abstract class that defines the section page to set an enumeration literal value.
 */
/*public */abstract class ValuePage extends Page {

	private final XpdFormToolkit toolkit;

	private Composite root;

	private EObject input;

	private final EditingDomain editingDomain;

	/**
	 * Section page to set the enumeration literal value.
	 * 
	 * @param toolkit
	 * @param editingDomain
	 */
	public ValuePage(XpdFormToolkit toolkit, EditingDomain editingDomain) {
		this.toolkit = toolkit;
		this.editingDomain = editingDomain;
	}

	/**
	 * Set the input of this page.
	 * 
	 * @param input
	 */
	public void setInput(EObject input) {
		this.input = input;
	}

	/**
	 * Get the input of this page.
	 * 
	 * @return
	 */
	protected EObject getInput() {
		return input;
	}

	/**
	 * Get the form toolkit.
	 * 
	 * @return
	 */
	protected XpdFormToolkit getToolkit() {
		return toolkit;
	}

	/**
	 * Get the editing domain.
	 * 
	 * @return
	 */
	protected EditingDomain getEditingDomain() {
		return editingDomain;
	}

	@Override
	public void createControl(Composite parent) {
		root = doCreateControl(parent);
	}

	/**
	 * Create the controls of this page.
	 * 
	 * @param parent
	 * @return root control.
	 */
	protected abstract Composite doCreateControl(Composite parent);

	protected final void doRefresh() {
		EObject input = getInput();
		if (input instanceof EnumerationLiteral) {
			doRefresh((EnumerationLiteral) input);
		}
	}

	/**
	 * Refresh the page with the given input.
	 * 
	 * @param input
	 */
	protected abstract void doRefresh(EnumerationLiteral input);

	/**
	 * Get the command to execute when the given widget is selected.
	 * 
	 * @param widget
	 * @param input
	 * @return
	 */
	protected Command doGetCommand(Widget widget, EnumerationLiteral input) {
		return null;
	}

	@Override
	public Control getControl() {
		return root;
	}

	/**
	 * Manage the given control. This will call
	 * {@link #doGetCommand(Widget, EnumerationLiteral)} when the given button
	 * is selected to get the command to execute to update the model.
	 * 
	 * @param btn
	 */
	protected void manageControl(final Button btn) {
		if (btn != null) {
			btn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (e.widget == btn) {
						EObject input = getInput();
						if (input instanceof EnumerationLiteral) {
							Command cmd = doGetCommand(btn,
									(EnumerationLiteral) input);
							if (cmd != null) {
								getEditingDomain().getCommandStack().execute(
										cmd);
							}
						}
					}
				}
			});
		}
	}

	/**
	 * Manage the given control. This will call
	 * {@link #doGetCommand(Widget, EnumerationLiteral)} when the given button
	 * is selected to get the command to execute to update the model.
	 * 
	 * @param txt
	 */
	protected void manageControl(final Text txt) {
		if (txt != null) {
			txt.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (e.widget == txt) {
						EObject input = getInput();
						if (input instanceof EnumerationLiteral) {
							Command cmd = doGetCommand(txt,
									(EnumerationLiteral) input);
							if (cmd != null) {
								getEditingDomain().getCommandStack().execute(
										cmd);
							}
						}
					}
				}
			});
		}
	}
}
