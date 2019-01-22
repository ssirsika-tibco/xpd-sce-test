/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.actions;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author nwilson
 */
public class RenameScriptAction implements IViewActionDelegate {

    /** The selection. */
    private ISelection selection;

    private Control control;

    private TreeEditor editor;

    /**
     * @param view
     * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
     */
    @Override
    public void init(IViewPart view) {
    }

    /**
     * @param action
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @Override
    public void run(IAction action) {
        if (selection instanceof StructuredSelection) {
            StructuredSelection structured = (StructuredSelection) selection;
            if (structured.size() == 1) {
                Object item = structured.getFirstElement();
                if (item instanceof ScriptInformation) {
                    final ScriptInformation information =
                            (ScriptInformation) item;
                    if (control instanceof Tree) {
                        Tree tree = (Tree) control;
                        if (editor != null) {
                            Control old = editor.getEditor();
                            if (old != null) {
                                old.dispose();
                            }
                        }
                        editor = new TreeEditor(tree);
                        editor.horizontalAlignment = SWT.LEFT;
                        editor.grabHorizontal = true;
                        editor.minimumWidth = 50;
                        final Text text = new Text(tree, SWT.NONE);
                        TreeItem[] items = tree.getSelection();
                        if (items.length == 1) {
                            final String oldValue = items[0].getText();
                            text.setText(oldValue);
                            text.selectAll();
                            text.setFocus();
                            text.addFocusListener(new FocusAdapter() {
                                @Override
                                public void focusLost(FocusEvent e) {
                                    updateName(information,
                                            oldValue,
                                            text.getText());
                                    text.dispose();
                                }
                            });
                            text.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyPressed(KeyEvent e) {
                                    if (e.character == '\r') {
                                        updateName(information,
                                                oldValue,
                                                text.getText());
                                        text.dispose();
                                    }
                                }
                            });
                            editor.setEditor(text, items[0]);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param information
     * @param text
     */
    protected void updateName(ScriptInformation information, String oldValue,
            String value) {
        if (!value.equals(oldValue)) {
            CompoundCommand command =
                    new CompoundCommand(
                            Messages.RenameScriptAction_EditScriptNameCommand);
            EditingDomain ed = WorkingCopyUtil.getEditingDomain(information);
            if (null != ed) {
                if (null != information.eContainer()) {
                    command.append(SetCommand.create(ed,
                            information,
                            Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                            value));
                }
                if (command != null && command.canExecute()) {
                    ed.getCommandStack().execute(command);
                }
            }
        }
    }

    /**
     * @param action
     * @param selection
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
        control = Display.getDefault().getFocusControl();
    }

}
