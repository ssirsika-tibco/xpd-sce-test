/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataReferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Column for process data reference contexts
 * <p>
 * Expects input (row) element to be {@link ProcessDataReferenceAndContexts} or
 * {@link IAdaptable} to that.
 * 
 * @author aallway
 * @since 25 Jun 2012
 */
public class DataReferencesByDataContextsColumn extends AbstractColumn {

    private DialogCellEditor cellEditor;

    public DataReferencesByDataContextsColumn(EditingDomain editingDomain,
            ColumnViewer viewer, String header, int width) {
        super(editingDomain, viewer, header, width);

        cellEditor = new DialogCellEditor((Composite) viewer.getControl()) {

            @Override
            protected Object openDialogBox(Control cellEditorWindow) {
                ProcessDataReferenceAndContexts dataRef =
                        getProcessDataReferenceAndContexts(this.getValue());

                if (dataRef != null) {
                    final GroupReferencesByDataContextsListDialog dialog =
                            new GroupReferencesByDataContextsListDialog(
                                    getViewer().getControl().getShell(),
                                    dataRef);

                    dialog.open();
                }

                return null;
            }

            /**
             * @see org.eclipse.jface.viewers.DialogCellEditor#updateContents(java.lang.Object)
             * 
             * @param value
             */
            @Override
            protected void updateContents(Object value) {
                super.updateContents(getContextListLabel(value));
            }

        };

    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected String getText(Object element) {
        return getContextListLabel(element);
    }

    /**
     * @param element
     * @return Command separate list of contexts from the input element.
     */
    public String getContextListLabel(Object element) {
        ProcessDataReferenceAndContexts dataRef =
                getProcessDataReferenceAndContexts(element);

        if (dataRef != null) {
            ArrayList<DataReferenceContext> sortedList =
                    getSortedDataReferences(dataRef
                            .getUniquelyLabelledContexts());

            StringBuilder sb = new StringBuilder();

            for (DataReferenceContext context : sortedList) {
                if (sb.length() > 0) {
                    sb.append(", "); //$NON-NLS-1$
                }
                sb.append(context.getLabel());
            }

            return sb.toString();
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * @param contexts
     * @return List of data reference contexts sorted by their sortingKey.
     */
    public ArrayList<DataReferenceContext> getSortedDataReferences(
            Collection<DataReferenceContext> contexts) {
        ArrayList<DataReferenceContext> sortedList =
                new ArrayList<DataReferenceContext>(contexts);

        Collections.sort(sortedList, new Comparator<DataReferenceContext>() {

            @Override
            public int compare(DataReferenceContext o1, DataReferenceContext o2) {
                return o1.getSortingKey().compareTo(o2.getSortingKey());
            }
        });
        return sortedList;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected CellEditor getCellEditor(Object element) {
        ProcessDataReferenceAndContexts dataRef =
                getProcessDataReferenceAndContexts(element);

        if (dataRef != null) {
            return cellEditor;
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected Object getValueForEditor(Object element) {
        return element;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param element
     * @param value
     * @return
     */
    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        return null;
    }

    /**
     * This method MUST be used rather than checking input element instanceof
     * {@link ProcessDataReferenceAndContexts} as this column is used fore input
     * that is {@link ProcessDataReferenceAndContexts} <b>or is
     * {@link IAdaptable} to {@link ProcessDataReferenceAndContexts}.</b>
     * 
     * @param element
     * @return The {@link ProcessDataReferenceAndContexts} for the given input.
     */
    private ProcessDataReferenceAndContexts getProcessDataReferenceAndContexts(
            Object element) {
        if (element instanceof ProcessDataReferenceAndContexts) {
            return (ProcessDataReferenceAndContexts) element;
        } else if (element instanceof IAdaptable) {
            return (ProcessDataReferenceAndContexts) ((IAdaptable) element)
                    .getAdapter(ProcessDataReferenceAndContexts.class);
        }

        return null;
    }

    /**
     * List of contexts.
     * 
     * @author aallway
     * @since 25 Jun 2012
     */
    private class GroupReferencesByDataContextsListDialog extends Dialog {

        private ProcessDataReferenceAndContexts dataRef;

        protected GroupReferencesByDataContextsListDialog(Shell parentShell,
                ProcessDataReferenceAndContexts dataRef) {
            super(parentShell);
            this.dataRef = dataRef;
        }

        /**
         * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
         * 
         * @param newShell
         */
        @Override
        protected void configureShell(Shell newShell) {
            super.configureShell(newShell);

            newShell.setText(Messages.ReferenceContextsColumn_DataReferences_title);
        }

        /**
         * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         * @return
         */
        @Override
        protected Control createDialogArea(Composite parent) {

            Composite mainContainer =
                    (Composite) super.createDialogArea(parent);

            XpdWizardToolkit toolkit = new XpdWizardToolkit(mainContainer);

            Composite container =
                    toolkit.createComposite(mainContainer, SWT.NONE);
            container.setLayoutData(new GridData(GridData.FILL_BOTH));
            GridLayout gl = new GridLayout(1, false);
            gl.marginHeight = 0;
            gl.marginWidth = 0;
            container.setLayout(gl);

            Label label =
                    toolkit.createLabel(container,
                            String.format(Messages.ReferenceContextsColumn_DataReferences_description,
                                    Xpdl2ModelUtil.getDisplayNameOrName(dataRef
                                            .getReferencedData())));
            label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            ArrayList<DataReferenceContext> sortedContexts =
                    getSortedDataReferences(dataRef
                            .getUniquelyLabelledContexts());

            List list =
                    toolkit.createList(container, SWT.V_SCROLL | SWT.BORDER);

            for (DataReferenceContext context : sortedContexts) {
                list.add(context.getLabel());
            }

            GridData gd = new GridData(GridData.FILL_BOTH);
            gd.heightHint = 150;
            gd.widthHint = 350;

            list.setLayoutData(gd);

            return mainContainer;
        }

        /**
         * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         */
        @Override
        protected void createButtonsForButtonBar(Composite parent) {
            createButton(parent,
                    IDialogConstants.OK_ID,
                    IDialogConstants.OK_LABEL,
                    true);
        }
    }

}