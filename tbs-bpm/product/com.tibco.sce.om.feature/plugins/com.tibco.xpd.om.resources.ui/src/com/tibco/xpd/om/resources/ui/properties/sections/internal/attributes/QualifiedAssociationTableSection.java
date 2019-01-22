/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.QualifiedAssociation;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;

/**
 * Abstract class for the {@link QualifiedAssociation} section tab. This will
 * allow the setting of the association and it's corresponding qualifier value.
 * 
 * @author njpatel
 * 
 * @param <T>
 *            object that extends {@link QualifiedAssociation} - the element
 *            input types of this table.
 */
public abstract class QualifiedAssociationTableSection<T extends QualifiedAssociation>
        extends AttributesTableSection {

    private final String columnTitle;

    /**
     * Abstract class for the {@link QualifiedAssociation} section tab.
     * 
     * @param heading
     *            section heading
     * @param columnTitle
     *            first column title
     */
    public QualifiedAssociationTableSection(String heading, String columnTitle) {
        super(heading);
        this.columnTitle = columnTitle;
    }

    /**
     * Get the type name to be printed in the first column for this element.
     * 
     * @param element
     *            associated element
     * @return display name of the element
     */
    protected abstract String getTypeName(T element);

    /**
     * Get the image of the type of element being associated with (image to go
     * in the first column of the table).
     * 
     * @param element
     *            assocation element
     * @return image to display in the first column, <code>null</code> if no
     *         image required.
     */
    protected abstract Image getTypeImage(T element);

    /**
     * Get qualifier {@link Attribute} from the qualified association.
     * 
     * @param element
     *            qualified association
     * @return <code>Attribute</code>.
     */
    protected abstract Attribute getQualifier(T element);

    /**
     * Get the qualified associations from the input to set as the elements of
     * this table.
     * 
     * @param input
     *            section input
     * @return qualified assocations
     */
    protected abstract EList<T> getElements(EObject input);

    /**
     * Get the {@link Command} to add a new qualified association element.
     * 
     * @param shell
     *            parent shell
     * @param input
     *            section input
     * @param ed
     *            editing domain
     * @return <code>Command</code> to add new element or <code>null</code> if
     *         no element should be added.
     */
    protected abstract Command getAddNewElementCommand(Shell shell,
            EObject input, EditingDomain ed);

    /**
     * Get the {@link Command} to delete the items selected in the table.
     * 
     * @param shell
     *            parent shell
     * @param input
     *            section input
     * @param ed
     *            editing domain
     * @param selection
     *            items selected in the table to delete
     * @return delete <code>Command</code> or <code>null</code> if no items
     *         should be deleted.
     */
    protected abstract Command getDeleteSelectionCommand(Shell shell,
            EObject input, EditingDomain ed, IStructuredSelection selection);

    /**
     * Get {@link Command} to move the given element by the given amount.
     * 
     * @param ed
     *            editing domain
     * @param element
     *            element to move
     * @param moveBy
     *            positive value to move down the table, and negative to move
     *            up.
     * @return Command to move the element, <code>null</code> if it cannot be
     *         moved.
     */
    protected abstract Command getMoveCommand(EditingDomain ed, T element,
            int moveBy);

    @Override
    protected Collection<ViewerAction> getActions(TableViewer viewer) {
        List<ViewerAction> actions = new ArrayList<ViewerAction>();
        actions.add(getAddAction(viewer));
        actions.add(getDeleteAction(viewer));
        actions.add(getMoveUpAction(viewer));
        actions.add(getMoveDownAction(viewer));
        actions.add(new ResetToDefaultAction(viewer));
        return actions;
    }

    /**
     * Get the {@link Command} to move the selected element up the table.
     * 
     * @param viewer
     *            table viewer
     * @return
     */
    private TableMoveUpAction getMoveUpAction(TableViewer viewer) {
        return new TableMoveUpAction(viewer) {

            @SuppressWarnings("unchecked")
            @Override
            protected void moveUp(Object element) {
                EditingDomain ed = getEditingDomain();
                if (ed != null && element instanceof QualifiedAssociation) {
                    Command cmd = getMoveCommand(ed, (T) element, -1);
                    if (cmd != null) {
                        ed.getCommandStack().execute(cmd);
                    }
                }
            }
        };
    }

    /**
     * Get the {@link Command} to move the selected element down the table.
     * 
     * @param viewer
     *            table viewer
     * @return
     */
    private TableMoveDownAction getMoveDownAction(TableViewer viewer) {
        return new TableMoveDownAction(viewer) {
            @SuppressWarnings("unchecked")
            @Override
            protected void moveDown(Object element) {
                EditingDomain ed = getEditingDomain();

                if (ed != null && element instanceof QualifiedAssociation) {
                    Command cmd = getMoveCommand(ed, (T) element, 1);
                    if (cmd != null) {
                        ed.getCommandStack().execute(cmd);
                    }
                }
            }
        };
    }

    /**
     * Get action to add new qualifier association.
     * 
     * @param viewer
     * @return
     */
    private TableAddAction getAddAction(TableViewer viewer) {
        return new TableAddAction(viewer) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                EditingDomain ed = getEditingDomain();
                EObject input = getInput();

                if (ed != null && input != null) {
                    Command cmd = getAddNewElementCommand(getShell(), input, ed);

                    if (cmd != null) {
                        ed.getCommandStack().execute(cmd);
                        Collection<?> results = cmd.getResult();

                        if (results != null && !results.isEmpty()) {
                            return results.iterator().next();
                        }
                    }
                }
                return null;
            }
        };
    }

    /**
     * Get action to delete a qualifier association.
     * 
     * @param viewer
     * @return
     */
    private TableDeleteAction getDeleteAction(TableViewer viewer) {
        return new TableDeleteAction(viewer) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                EObject input = getInput();
                EditingDomain ed = getEditingDomain();
                if (selection != null && ed != null && input != null) {
                    Command cmd = getDeleteSelectionCommand(getShell(), input,
                            ed, selection);
                    if (cmd != null) {
                        ed.getCommandStack().execute(cmd);
                    }
                }
            }

        };
    }

    @Override
    protected void addColumns(TableViewer viewer) {
        TableViewerColumn typeCol = createColumn(viewer, SWT.LEFT, columnTitle,
                150);
        typeCol.setLabelProvider(new ColumnLabelProvider() {
            @SuppressWarnings("unchecked")
            @Override
            public String getText(Object element) {
                if (element instanceof QualifiedAssociation) {
                    return getTypeName((T) element);
                }
                return null;
            }

            @SuppressWarnings("unchecked")
            @Override
            public Image getImage(Object element) {
                if (element instanceof QualifiedAssociation) {
                    return getTypeImage((T) element);
                }
                return null;
            }
        });
        new ValueColumn(getEditingDomain(), viewer);
    }

    @Override
    protected void doRefresh() {
        setElements(getElements(getInput()));
    }

    private class ValueColumn extends QualifiedValueColumn<T> {

        public ValueColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(
                    editingDomain,
                    viewer,
                    Messages.QualifiedAssociationTableSection_valueColumn_title,
                    200);
        }

        @Override
        protected Attribute getQualifier(T element) {
            return QualifiedAssociationTableSection.this.getQualifier(element);
        }
    }

    /**
     * Action to reset the selected qualifiers to their default values.
     * 
     * @author njpatel
     * 
     */
    private class ResetToDefaultAction extends ViewerAction {

        private IStructuredSelection selection;

        public ResetToDefaultAction(StructuredViewer viewer) {
            super(
                    viewer,
                    Messages.QualifiedAssociationTableSection_resetToDefault_action,
                    PlatformUI.getWorkbench().getSharedImages()
                            .getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
            setToolTipText(Messages.QualifiedAssociationTableSection_resetToDefault_action_tooltip);
            setEnabled(false);
            setAccelerator(SWT.CTRL | 'r');
        }

        @Override
        public void selectionChanged(IStructuredSelection selection) {
            this.selection = selection;
            setEnabled(selection != null && !selection.isEmpty());
        }

        @Override
        public void run() {
            EditingDomain ed = getEditingDomain();
            if (selection != null && ed != null) {
                // Cancel editing of a cell before resetting to default
                if (getViewer() instanceof ColumnViewer) {
                    ((ColumnViewer) getViewer()).cancelEditing();
                }

                CompoundCommand ccmd = new CompoundCommand();

                for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                    Object next = iter.next();

                    if (next instanceof QualifiedAssociation) {
                        QualifiedAssociation assoc = (QualifiedAssociation) next;

                        if (assoc.getQualifierValue() != null) {
                            ccmd
                                    .append(SetCommand
                                            .create(
                                                    ed,
                                                    assoc,
                                                    OMPackage.eINSTANCE
                                                            .getQualifiedAssociation_QualifierValue(),
                                                    SetCommand.UNSET_VALUE));
                        }
                    }
                }

                if (!ccmd.isEmpty()) {
                    ed.getCommandStack().execute(ccmd);
                }
            }
        }
    }
}
