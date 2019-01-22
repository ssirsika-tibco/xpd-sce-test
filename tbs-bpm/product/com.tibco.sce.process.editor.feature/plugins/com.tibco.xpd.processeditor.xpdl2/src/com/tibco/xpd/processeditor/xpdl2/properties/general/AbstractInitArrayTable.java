/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * AbstractInitArrayTable
 *
 *
 * @author bharge
 * @since 3.3 (27 Nov 2009)
 */
public abstract class AbstractInitArrayTable extends BaseTableControl {

    /**
     * @param parent
     * @param toolkit
     */
    public AbstractInitArrayTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain, boolean b) {
        super(parent, toolkit, null, false);
        this.editingDomain = editingDomain;
    }

    private EditingDomain editingDomain;
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getViewerContentProvider()
     */
    @Override
    protected IContentProvider getViewerContentProvider() {
        return new InitialValuesContentProvider();
    }
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new InitialValueAddNewRowAction(viewer,
                Messages.InitialValueTable_Add_Label,
                Messages.InitialValueTable_Add_Tooltip, editingDomain); 
    }
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return new InitialValueDeleteRowAction(viewer,
                Messages.InitialValueTable_Delete_Label,
                Messages.InitialValueTable_Delete_Tooltip, editingDomain); 
    }
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer) {
        return new TableMoveDownAction(viewer, Messages.InitialValueTable_MoveDown_Label,
                Messages.InitialValueTable_MoveDown_Tooltip) {  

            @Override
            protected void moveDown(Object element) {
                Command cmd =
                    getMoveParameterCommand(editingDomain,                            
                            new StructuredSelection(element),
                            false);

                // If there is a command the execute it
                if (cmd != null) {
                    editingDomain.getCommandStack().execute(cmd);
                }
            }
            
        };
    }
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveUpAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerMoveUpAction createMoveUpAction(ColumnViewer viewer) {
        return new TableMoveUpAction(viewer, Messages.InitialValueTable_MoveUp_Label,
                Messages.InitialValueTable_MoveUp_Tooltip) {

            @Override
            protected void moveUp(Object element) {
                Command cmd =
                    getMoveParameterCommand(editingDomain,                            
                            new StructuredSelection(element),
                            true);

                // If there is a command the execute it
                if (cmd != null) {
                    editingDomain.getCommandStack().execute(cmd);
                }
            }
            
        };
    }
    
    /**
     * Get <code>Command</code> to move the parameter up or down in the table.
     * 
     * @param ed
     *            Editing domain
     * @param selection
     *            Selection in the table
     * @param moveUp
     *            Set to <b>true</b> if the parameter needs moving up and
     *            <b>false</b> if the parameter needs moving down
     * @return <code>Command</code> to update the parameter listing
     */
    private Command getMoveParameterCommand(EditingDomain ed,
            IStructuredSelection selection, boolean moveUp) {
        CompoundCommand cmd =
            new CompoundCommand() {
                @Override
                public boolean canExecute() {
                    return true;
                }
            };

        if (ed != null && selection != null
                && selection.getFirstElement() instanceof String) {
            String element = (String) selection.getFirstElement();
            ProcessRelevantData data = (ProcessRelevantData) getInput();
            
            String modelValue =
                    ProcessDataUtil
                            .convertUIInitialValueToModelFormat(data,
                                    (String) element);
            
            InitialValues initialValues =
                (InitialValues) Xpdl2ModelUtil.getOtherElement(data,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InitialValues());
            
            int index = getItemIndex(modelValue, data);
            if (moveUp && index > 0) {
                cmd.append(RemoveCommand.create(ed,
                                initialValues,
                                XpdExtensionPackage.eINSTANCE.getInitialValues_Value(),
                                modelValue));
                cmd.append(AddCommand.create(editingDomain,
                        initialValues,
                        XpdExtensionPackage.eINSTANCE.getInitialValues_Value(),
                        modelValue,
                        index - 1));
            } else if (!moveUp && index < initialValues.getValue().size()) {
                cmd.append(RemoveCommand.create(ed,
                        initialValues,
                        XpdExtensionPackage.eINSTANCE.getInitialValues_Value(),
                        modelValue));
                cmd.append(AddCommand.create(editingDomain,
                        initialValues,
                        XpdExtensionPackage.eINSTANCE.getInitialValues_Value(),
                        modelValue,
                        index + 1));
            }
        }
        return cmd;
    }
    
    /**
     * Get the input of this table.
     * 
     * @return
     */
    private EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }
    
    private int getItemIndex(String modelValue, ProcessRelevantData data) {
        List<String> values = null;
        InitialValues oldInitialValues =
            (InitialValues) Xpdl2ModelUtil.getOtherElement(data,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_InitialValues());
        if (oldInitialValues != null) {
            values = oldInitialValues.getValue();
            int i = 0;
            for (Iterator<String> iterator = values.iterator(); iterator.hasNext();) {
                String string = (String) iterator.next();
                if (string.equals(modelValue)) {
                    return i;
                }
                i++;
            }
        }
        
        return -1;
    }
    
}
