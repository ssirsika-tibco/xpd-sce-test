/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.om.core.om.AssociableWithResources;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.core.om.ResourceAssociation;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.LabelColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.NameColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.TypeColumn;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.SameResourceFilter;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Associated Resources tab section. This allows association {@link Resource}s
 * to {@link AssociableWithResources} objects.
 * 
 * @author njpatel
 * 
 */
public class ResourceAssocTabSection extends AbstractTransactionalSection {

    private ResourceTable table;

    private Font tableHeaderFont;

    private static final EReference RESOURCE_ASSOCIATION = OMPackage.eINSTANCE
            .getAssociableWithResources_ResourceAssociation();

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);

        root.setLayout(new GridLayout());
        Label label =
                toolkit.createLabel(root,
                        Messages.ResourceAssocTabSection_resources_label);
        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalIndent = 5;
        label.setLayoutData(data);
        label.setForeground(ColorConstants.darkGray);
        tableHeaderFont = new Font(root.getDisplay(), "Arial", 10, //$NON-NLS-1$
                SWT.BOLD);
        label.setFont(tableHeaderFont);

        table =
                new ResourceTable(root, toolkit, SWT.MULTI | SWT.FULL_SELECTION);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        return root;
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof EditPart) {
            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        }

        return super.resollveInput(object);
    }

    @Override
    public void dispose() {
        if (tableHeaderFont != null) {
            tableHeaderFont.dispose();
            tableHeaderFont = null;
        }
        super.dispose();
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Update of model handled by the table
        return null;
    }

    @Override
    protected void doRefresh() {
        List<Resource> resources = getResources();

        if (resources != null && table != null && !table.isDisposed()) {
            table.setElements(resources);
        }
    }

    /**
     * Update the {@link ResourceAssociation}s for the input in response to the
     * selection in the picker.
     * 
     * @param currResources
     *            currently associated resources
     * @param newResources
     *            the new selection from the picker
     * @return all newly added resource associations
     */
    private Collection<ResourceAssociation> updateResourceAssociations(
            Collection<Resource> currResources,
            Collection<Resource> newResources) {
        EObject input = getInput();
        EditingDomain ed = getEditingDomain();
        Collection<ResourceAssociation> newAssocs =
                new HashSet<ResourceAssociation>();
        if (ed != null && input instanceof AssociableWithResources
                && newResources != null) {
            CompoundCommand cmd = new CompoundCommand();

            EList<ResourceAssociation> associations =
                    ((AssociableWithResources) input).getResourceAssociation();

            if (associations != null && !associations.isEmpty()) {
                // Check if any resource were un-selected and if so remove from
                // the associations
                Set<ResourceAssociation> assocsToRemove =
                        new HashSet<ResourceAssociation>();
                for (ResourceAssociation assoc : associations) {
                    if (!newResources.contains(assoc.getResource())) {
                        // Removed
                        assocsToRemove.add(assoc);
                    }
                }

                if (!assocsToRemove.isEmpty()) {
                    cmd.append(RemoveCommand.create(ed,
                            input,
                            RESOURCE_ASSOCIATION,
                            assocsToRemove));
                }
            }

            // Add new assocations
            if (!newResources.isEmpty()) {
                Set<ResourceAssociation> assocsToAdd =
                        new HashSet<ResourceAssociation>();

                for (Resource res : newResources) {
                    if (currResources == null || !currResources.contains(res)) {
                        ResourceAssociation assoc =
                                OMFactory.eINSTANCE.createResourceAssociation();
                        assoc.setResource(res);
                        assocsToAdd.add(assoc);
                    }
                }

                if (!assocsToAdd.isEmpty()) {
                    newAssocs = assocsToAdd;
                    cmd.append(AddCommand.create(ed,
                            input,
                            RESOURCE_ASSOCIATION,
                            assocsToAdd));
                }

            }

            if (!cmd.isEmpty()) {
                ed.getCommandStack().execute(cmd);
            }
        }
        return newAssocs;
    }

    /**
     * Get the associated resources.
     * 
     * @return collection of association Resources. Can be empty if no resources
     *         are associated.
     */
    private List<Resource> getResources() {
        EObject input = getInput();
        List<Resource> resources = new ArrayList<Resource>();
        if (input instanceof AssociableWithResources) {
            EList<ResourceAssociation> association =
                    ((AssociableWithResources) input).getResourceAssociation();

            if (association != null) {
                for (ResourceAssociation assoc : association) {
                    if (assoc.getResource() != null) {
                        resources.add(assoc.getResource());
                    }
                }
            }

            return resources;
        }
        return null;
    }

    /**
     * The table that allows addition/removal of associated resources in this
     * section.
     * 
     * @author njpatel
     * 
     */
    private class ResourceTable extends AbstractTableControl {

        /**
         * Resource association table.
         * 
         * @param parent
         *            parent control
         * @param toolkit
         *            form toolkit
         * @param style
         *            {@link SWT} style
         */
        public ResourceTable(Composite parent, XpdFormToolkit toolkit, int style) {
            super(parent, toolkit, style);
        }

        @Override
        protected TableAddAction getAddAction(TableViewer viewer) {
            return new TableAddAction(viewer) {

                @Override
                protected Object addRow(StructuredViewer viewer) {
                    List<Resource> currResources = getResources();
                    Object[] selection =
                            PickerService
                                    .getInstance()
                                    .openMultiPickerDialog(getShell(),
                                            new PickerTypeQuery[] { new OMTypeQuery(
                                                    OMTypeQuery.TYPE_ID_RESOURCE) },
                                            null,
                                            null,
                                            null,
                                            new IFilter[] { new SameResourceFilter(
                                                    getInput()) },
                                            currResources.toArray());
                    List<Resource> newResources = new ArrayList<Resource>();
                    if (selection != null) {
                        for (Object sel : selection) {
                            if (sel instanceof Resource) {
                                newResources.add((Resource) sel);
                            }
                        }
                    }
                    return updateResourceAssociations(currResources,
                            newResources);
                }
            };
        }

        @Override
        protected TableDeleteAction getDeleteAction(TableViewer viewer) {
            return new TableDeleteAction(viewer) {

                @Override
                protected void deleteRows(IStructuredSelection selection) {
                    EObject input = getInput();
                    EditingDomain ed = getEditingDomain();
                    if (input instanceof AssociableWithResources && ed != null
                            && selection != null && !selection.isEmpty()) {
                        EList<ResourceAssociation> associations =
                                ((AssociableWithResources) input)
                                        .getResourceAssociation();
                        Set<ResourceAssociation> assocsToRemove =
                                new HashSet<ResourceAssociation>();
                        for (Iterator<?> iter = selection.iterator(); iter
                                .hasNext();) {
                            Object next = iter.next();

                            if (next instanceof Resource) {
                                for (ResourceAssociation assoc : associations) {
                                    if (next.equals(assoc.getResource())) {
                                        assocsToRemove.add(assoc);
                                        break;
                                    }
                                }
                            }
                        }

                        if (!assocsToRemove.isEmpty()) {
                            ed.getCommandStack()
                                    .execute(RemoveCommand.create(ed,
                                            input,
                                            RESOURCE_ASSOCIATION,
                                            assocsToRemove));
                        }
                    }
                }
            };
        }

        @Override
        protected TableMoveUpAction getMoveUpAction(final TableViewer viewer) {
            return new TableMoveUpAction(viewer) {

                @Override
                protected void moveUp(Object element) {
                    moveElement(element, -1);
                }

            };
        }

        @Override
        protected TableMoveDownAction getMoveDownAction(final TableViewer viewer) {
            return new TableMoveDownAction(viewer) {

                @Override
                protected void moveDown(Object element) {
                    moveElement(element, 1);
                }

            };
        }

        @Override
        protected void addColumns(TableViewer viewer) {
            new LabelColumn(getEditingDomain(), viewer, true);
            if (CapabilityUtil.isDeveloperActivityEnabled()) {
                new NameColumn(getEditingDomain(), viewer, true);
            }
            new TypeColumn(getEditingDomain(), viewer, null, true);
        }

        /**
         * Move the given element in the table by the given relative position.
         * 
         * @param element
         * @param moveBy
         */
        private void moveElement(Object element, int moveBy) {
            EObject input = getInput();
            EditingDomain ed = getEditingDomain();
            int idx = -1;

            if (element != null && input instanceof AssociableWithResources) {
                EList<ResourceAssociation> assocs =
                        ((AssociableWithResources) input)
                                .getResourceAssociation();
                ResourceAssociation assocToMove = null;
                if (assocs != null && !assocs.isEmpty()) {
                    for (int x = 0; x < assocs.size() && idx < 0
                            && assocToMove == null; x++) {
                        if (assocs.get(x).getResource() == element) {
                            idx = x;
                            assocToMove = assocs.get(x);
                        }
                    }

                    if (idx >= 0) {
                        idx += moveBy;
                    }

                    if (idx >= 0 && idx < assocs.size() && assocToMove != null) {
                        ed.getCommandStack().execute(MoveCommand.create(ed,
                                input,
                                RESOURCE_ASSOCIATION,
                                assocToMove,
                                idx));
                    }
                }
            }
        }

    }

}
