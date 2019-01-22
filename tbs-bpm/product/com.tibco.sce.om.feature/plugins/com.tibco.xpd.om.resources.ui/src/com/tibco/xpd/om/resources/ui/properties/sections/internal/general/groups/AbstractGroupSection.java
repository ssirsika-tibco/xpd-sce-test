/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general.groups;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.provider.TransientItemProvider;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Abstract section for all groups. This will display a table of all children of
 * the section's input.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractGroupSection extends AbstractTransactionalSection {

    private String heading;

    private Font tableHeaderFont;

    private AbstractTableControl table;

    private ItemProviderAdapter itemProvider;

    private XpdFormToolkit toolkit;

    public AbstractGroupSection() {
        this(null);
    }

    /**
     * Abstract section for all group general tabs.
     * 
     * @param heading
     * @param eAttr
     *            model attribute to add new element to
     */
    public AbstractGroupSection(String heading) {
        setHeading(heading);
    }

    /**
     * Set the heading of this section.
     * 
     * @param heading
     */
    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
     * Get the item provider for this section's input.
     * 
     * @return item provider or <code>null</code> if one is not found.
     */
    protected ItemProviderAdapter getItemProvider() {
        return itemProvider;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        /* Not used as changes handled by the table */
        return null;
    }

    @Override
    protected EObject resollveInput(Object object) {

        if (object instanceof EditPart) {
            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        }

        return super.resollveInput(object);
    }

    /**
     * Get the toolkit used for creating the controls.
     * 
     * @return toolkit or <code>null</code> if one is not yet available.
     */
    public XpdFormToolkit getToolkit() {
        return toolkit;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        this.toolkit = toolkit;

        if (heading != null) {
            root.setLayout(new GridLayout());
            Label label = toolkit.createLabel(root, heading);
            GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
            data.horizontalIndent = 5;
            label.setLayoutData(data);
            label.setForeground(ColorConstants.darkGray);
            tableHeaderFont = new Font(root.getDisplay(), "Arial", 10, //$NON-NLS-1$
                    SWT.BOLD);
            label.setFont(tableHeaderFont);

            Control area = createClientArea(root, toolkit);
            if (area != null) {
                area
                        .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                                true));
            }
        } else {
            FillLayout layout = new FillLayout();
            layout.marginHeight = 5;
            layout.marginWidth = 5;
            root.setLayout(layout);
            createClientArea(root, toolkit);
        }

        return root;
    }

    @Override
    public void setInput(Collection<?> items) {
        if (items != null && !items.isEmpty()) {
            Object next = items.iterator().next();

            if (next instanceof TransientItemProvider) {
                itemProvider = (TransientItemProvider) next;
                items = Collections.singleton(itemProvider.getTarget());
            } else if (next instanceof EObject || next instanceof EditPart) {
                IEditingDomainItemProvider provider =
                        next instanceof EObject ? getItemProvider((EObject) next)
                                : getItemProvider((EditPart) next);
                if (provider instanceof ItemProviderAdapter) {
                    itemProvider = (ItemProviderAdapter) provider;
                }
            }
        }
        super.setInput(items);
    }

    /**
     * @param editPart
     * @return
     */
    protected IEditingDomainItemProvider getItemProvider(EditPart editPart) {
        return null;
    }

    /**
     * @param eo
     * @return
     */
    protected final IEditingDomainItemProvider getItemProvider(EObject eo) {
        return (IEditingDomainItemProvider) XpdResourcesPlugin.getDefault()
                .getAdapterFactory()
                .adapt(eo, IEditingDomainItemProvider.class);
    }

    /**
     * Get the table control.
     * 
     * @return
     */
    protected AbstractTableControl getTable() {
        return table;
    }

    /**
     * Get the elements from the input to display in this section. Default
     * implementation gets all children of the section's transient item
     * provider. Subclasses may override to provide a different set of elements.
     * 
     * @param input
     *            section input
     * @return
     */
    protected Collection<?> getElements(EObject input) {
        if (input != null && itemProvider != null) {
            return itemProvider.getChildren(input);
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (table != null && !table.isDisposed()) {
            table.getTableViewer().cancelEditing();
            table.setElements(getElements(input));
        }
    }

    @Override
    public void dispose() {
        if (tableHeaderFont != null) {
            tableHeaderFont.dispose();
            tableHeaderFont = null;
        }
        super.dispose();
    }

    /**
     * Get the label of a new element to be added to this group. The default
     * implementation will get all children from this group's item provider and
     * get the next available name base on the
     * {@link #getNewElementLabelPrefix() prefix} provided.
     * 
     * @see OMUtil#getDefaultName(String, Collection)
     * 
     * @return new element label
     */
    protected String getNewElementLabel() {
        String label = ""; //$NON-NLS-1$
        if (itemProvider != null) {
            CommandParameter descriptor = getNewChildDescriptor(itemProvider);
            if (descriptor != null
                    && descriptor.getValue() instanceof NamedElement) {
                label = ((NamedElement) descriptor.getValue()).getDisplayName();
            }
        }

        return label;
    }

    /**
     * Get the new child descriptor for this object.
     * 
     * @param provider
     *            input item provider
     * @return
     */
    protected CommandParameter getNewChildDescriptor(
            ItemProviderAdapter provider) {
        Collection<?> descriptors =
                provider.getNewChildDescriptors(getInput(),
                        getEditingDomain(),
                        null);
        if (descriptors != null) {
            for (Object descriptor : descriptors) {
                if (descriptor instanceof CommandParameter
                        && isRequiredChildDescriptor((CommandParameter) descriptor)) {
                    return (CommandParameter) descriptor;
                }
            }
        }
        return null;
    }

    /**
     * Check if this is the correct child being added in this group.
     * 
     * @param descriptor
     * @return <code>true</code> if this is the child being maintained by this
     *         group, <code>false</code> otherwise.
     */
    protected abstract boolean isRequiredChildDescriptor(
            CommandParameter descriptor);

    /**
     * Add new element in response to the Add action.
     * 
     * @param firstCellVal
     *            new element name, <code>null</code> if no name was provided
     * @return element created or <code>null</code> if no element was created.
     */
    private Object addNewElement(String firstCellVal) {
        EditingDomain ed = getEditingDomain();

        if (ed != null) {
            Command cmd = getCreateNewElementCommand(ed, firstCellVal);

            if (cmd != null) {
                ed.getCommandStack().execute(cmd);
                Collection<?> result = cmd.getResult();

                if (result != null && !result.isEmpty()) {
                    return result.iterator().next();
                }
            }
        }

        return null;
    }

    /**
     * Delete the selection from the table in response to the Remove action.
     * 
     * @param selection
     */
    protected void deleteSelection(IStructuredSelection selection) {
        EditingDomain ed = getEditingDomain();

        if (ed != null && selection != null && !selection.isEmpty()) {
            Command cmd = getDeleteElementsCommand(ed, selection.toList());

            if (cmd != null) {
                ed.getCommandStack().execute(cmd);
            }
        }
    }

    /**
     * Get {@link Command} to create a new element in this group.
     * 
     * @param editingDomain
     * @param elementName
     * @return <code>Command</code> to create new element or <code>null</code>
     *         if the element cannot be created.
     */
    protected Command getCreateNewElementCommand(EditingDomain editingDomain,
            String elementName) {
        if (itemProvider != null && itemProvider.getTarget() != null) {
            CommandParameter descriptor = getNewChildDescriptor(itemProvider);
            return getCreateNewElementCommand(editingDomain,
                    itemProvider,
                    descriptor,
                    elementName);
        }
        return null;
    }

    /**
     * Get {@link Command} to create a new element in this group.
     * 
     * @param editingDomain
     * @param elementName
     * @return <code>Command</code> to create new element or <code>null</code>
     *         if the element cannot be created.
     */
    protected Command getCreateNewElementCommand(EditingDomain editingDomain,
            ItemProviderAdapter itemProvider, CommandParameter descriptor,
            String elementName) {
        if (descriptor != null && descriptor.getValue() instanceof NamedElement) {
            NamedElement elem = (NamedElement) descriptor.getValue();
            if (elementName != null
                    && !elementName.equals(elem.getDisplayName())) {
                elem.setDisplayName(elementName);
            }

            Object owner =
                    itemProvider instanceof TransientItemProvider ? itemProvider
                            : getInput();

            return CreateChildCommand.create(editingDomain,
                    owner,
                    descriptor,
                    Collections.singleton(owner));

        }
        return null;
    }

    /**
     * Get the {@link Command} to move the given element up in the collection.
     * 
     * @param editingDomain
     * @param element
     *            element to move
     * @return <code>Command</code> or <code>null</code> if this action should
     *         not be performed.
     */
    protected Command getMoveElementUpCommand(EditingDomain editingDomain,
            Object element) {
        return getMoveElementCommand(editingDomain, element, -1);
    }

    /**
     * Get the {@link Command} to move the given element down in the collection.
     * 
     * @param editingDomain
     * @param element
     *            element to move
     * @return <code>Command</code> or <code>null</code> if this action should
     *         not be performed.
     */
    protected Command getMoveElementDownCommand(EditingDomain editingDomain,
            Object element) {
        return getMoveElementCommand(editingDomain, element, 1);
    }

    /**
     * Move the element in the table by the given moveBy value.
     * 
     * @param editingDomain
     * @param element
     *            element to move
     * @param moveBy
     *            positive value to move down the list and negative value to
     *            move up the list
     * @return Move command or <code>null</code> if error.
     */
    private Command getMoveElementCommand(EditingDomain editingDomain,
            Object element, int moveBy) {
        EObject input = getInput();

        if (itemProvider != null && input != null
                && editingDomain instanceof TransactionalEditingDomain
                && element instanceof EObject && moveBy != 0) {
            EObject eo = (EObject) element;

            Collection<?> elements = getElements(input);
            if (elements instanceof List<?>) {
                List<?> list = (List<?>) elements;
                int idx = list.indexOf(element);

                if ((moveBy > 0 && idx >= 0) || (moveBy < 0 && idx > 0)) {
                    CommandParameter param =
                            new CommandParameter(input, eo
                                    .eContainmentFeature(), eo, idx + moveBy);

                    return itemProvider.createCommand(eo,
                            editingDomain,
                            MoveCommand.class,
                            param);
                }
            }
        }

        return null;
    }

    /**
     * Get {@link Command} to remove the selected items from the group.
     * 
     * @param editingDomain
     * @param elements
     * @return
     */
    protected Command getDeleteElementsCommand(EditingDomain editingDomain,
            Collection<?> elements) {
        return DeleteCommand.create(editingDomain, elements);
    }

    /**
     * Check if the given selection can be deleted. Default implementation
     * returns <code>true</code>, subclasses may override if the selection needs
     * to be checked.
     * 
     * @param selection
     *            table selection
     * @return <code>true</code> if the selection can be deleted,
     *         <code>false</code> otherwise.
     */
    protected boolean canDelete(IStructuredSelection selection) {
        return true;
    }

    /**
     * Create the main area of this section. This will include the group table
     * for this section.
     * 
     * @param parent
     *            parent container
     * @param toolkit
     *            form toolkit
     * @return created control
     */
    protected Control createClientArea(Composite parent, XpdToolkit toolkit) {

        table =
                new AbstractTableControl(parent, toolkit, SWT.MULTI
                        | SWT.FULL_SELECTION) {

                    @Override
                    protected void addColumns(TableViewer viewer) {
                        AbstractGroupSection.this.addColumns(viewer);
                    }

                    @Override
                    protected TableAddAction getAddAction(TableViewer viewer) {
                        return new TableAddAction(viewer) {
                            @Override
                            protected Object addRow(StructuredViewer viewer) {
                                String label = getNewElementLabel();
                                return addNewElement(label);
                            }

                        };
                    }

                    @Override
                    protected TableDeleteAction getDeleteAction(
                            TableViewer viewer) {
                        return new TableDeleteAction(viewer) {

                            @Override
                            protected void deleteRows(
                                    IStructuredSelection selection) {
                                deleteSelection(selection);
                            }

                            @Override
                            protected boolean canDelete(
                                    IStructuredSelection selection) {
                                return AbstractGroupSection.this
                                        .canDelete(selection);
                            }
                        };
                    }

                    @Override
                    protected TableMoveUpAction getMoveUpAction(
                            TableViewer viewer) {
                        return new TableMoveUpAction(viewer) {

                            @Override
                            protected void moveUp(Object element) {
                                EditingDomain ed = getEditingDomain();

                                if (ed != null) {
                                    Command cmd =
                                            getMoveElementUpCommand(ed, element);

                                    if (cmd != null) {
                                        ed.getCommandStack().execute(cmd);
                                    }
                                }
                            }
                        };
                    }

                    @Override
                    protected TableMoveDownAction getMoveDownAction(
                            TableViewer viewer) {
                        return new TableMoveDownAction(viewer) {

                            @Override
                            protected void moveDown(Object element) {
                                EditingDomain ed = getEditingDomain();

                                if (ed != null) {
                                    Command cmd =
                                            getMoveElementDownCommand(ed,
                                                    element);

                                    if (cmd != null) {
                                        ed.getCommandStack().execute(cmd);
                                    }
                                }
                            }
                        };
                    }
                };

        return table;
    }

    protected abstract void addColumns(TableViewer viewer);

}
