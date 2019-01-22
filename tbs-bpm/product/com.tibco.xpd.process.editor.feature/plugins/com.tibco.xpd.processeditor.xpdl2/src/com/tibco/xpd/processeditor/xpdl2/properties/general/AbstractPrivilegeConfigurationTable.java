/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * AbstractPrivilegeConfigurationTable provides a single column table that shows
 * the available organisation model privileges. It provides add, remove, up,
 * down buttons to add or remove the privileges and move the privileges up/down
 * within the table. The commands to add/remove the privilege model element to
 * the input model must be provided by the implementation classes.
 * 
 * @author bharge
 * @since 19 Aug 2014
 */
public abstract class AbstractPrivilegeConfigurationTable extends
        BaseTableControl {

    private EditingDomain editingDomain;

    private IContentProvider contentProvider;

    private String columnLabel;

    /**
     * @param parent
     * @param toolkit
     * @param columnLabel
     * @param viewerInput
     */
    public AbstractPrivilegeConfigurationTable(Composite parent,
            XpdToolkit toolkit, EditingDomain editingDomain, String columnLabel) {

        super(parent, toolkit, null, false);
        this.editingDomain = editingDomain;
        this.columnLabel = columnLabel;
        createContents(parent, toolkit, null);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {

        new RequiredPrivilegesColumn(editingDomain, viewer, columnLabel);
        this.setColumnProportions(new float[] { 1.0f });
    }

    /**
     * Get the input of this table.
     * 
     * @return
     */
    private EObject getInput() {

        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getViewerContentProvider()
     * 
     * @return
     */
    @Override
    protected IContentProvider getViewerContentProvider() {

        if (null == contentProvider) {

            contentProvider = new PrivilegesContentProvider();
        }
        return contentProvider;
    }

    /**
     * Abstract method to get the <code>RequiredAccessPrivileges</code> model
     * element based on the implementation class specific input
     * 
     * @return <code>RequiredAccessPrivileges</code>
     */
    protected abstract RequiredAccessPrivileges getRequiredAccessPrivileges();

    /**
     * Command to create <code>RequiredAccessPrivileges</code> model element
     * based on the implementation class specific input
     * 
     * @param cmd
     *            Command to append creation to.
     * 
     * @return {@link RequiredAccessPrivileges}
     */
    protected abstract RequiredAccessPrivileges getCreatePrivilegesContainerCmd(
            CompoundCommand cmd);

    /**
     * Command to remove <code>RequiredAccessPrivileges</code> model element
     * based on the implementation class specific input
     * 
     * @return command
     */
    protected abstract Command getRemovePrivilegesContainerCmd();

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {

        return new TableAddAction(viewer,
                Messages.PrivilegesConfigurationTable_add_label,
                Messages.PrivilegesConfigurationTable_add_tooltip_desc) {

            @Override
            protected Object addRow(StructuredViewer viewer) {

                EObject input = getInput();
                if (null != input) {

                    CompoundCommand cmpdCmd =
                            new CompoundCommand(
                                    Messages.PrivilegesConfigurationTable_add_command_label);

                    IProject processProject =
                            WorkingCopyUtil.getProjectFor(input);

                    RequiredAccessPrivileges accessPrivileges =
                            getRequiredAccessPrivileges();

                    if (accessPrivileges == null) {
                        accessPrivileges =
                                getCreatePrivilegesContainerCmd(cmpdCmd);
                    }

                    List<Privilege> privilegeList = new ArrayList<>();

                    if (null != accessPrivileges) {

                        EList<ExternalReference> privilegeReference =
                                accessPrivileges.getPrivilegeReference();
                        for (ExternalReference externalReference : privilegeReference) {

                            String xref = externalReference.getXref();
                            EObject eObject = OMUtil.getEObjectByID(xref);
                            if (eObject instanceof Privilege) {

                                privilegeList.add((Privilege) eObject);
                            }
                        }
                    }

                    Object[] selection =
                            PickerService
                                    .getInstance()
                                    .openMultiPickerDialog(getShell(),
                                            new PickerTypeQuery[] { new OMTypeQuery(
                                                    OMTypeQuery.TYPE_ID_PRIVILEGE) },
                                            null,
                                            null,
                                            null,
                                            null,
                                            privilegeList.toArray());

                    /*
                     * Get the list of new privileges i.e., the privileges which
                     * user has selected.
                     */
                    List<Privilege> newPrivilegeList =
                            getListOfNewPrivileges(selection);

                    if (null != selection && selection.length > 0) {

                        /*
                         * Get command to remove the contents which are there in
                         * the model, but are not selected by the user.
                         */
                        Command cmd =
                                getCommandToRemoveUnnecessaryContents(privilegeList,
                                        newPrivilegeList,
                                        accessPrivileges);

                        if (cmd != null) {
                            cmpdCmd.append(cmd);
                        }

                        /*
                         * XPD-6970: Stores the list of all the OM projects that
                         * must be referenced in order to externally reference
                         * the privilege.
                         */
                        Set<IProject> allOmProjectsThatMustBeReferenced =
                                new HashSet<IProject>();

                        for (Privilege privilege : newPrivilegeList) {
                            IProject omProjectToReference =
                                    WorkingCopyUtil.getProjectFor(privilege);

                            if (omProjectToReference != null) {
                                allOmProjectsThatMustBeReferenced
                                        .add(omProjectToReference);
                            }
                        }

                        if (ProcessUIUtil
                                .checkAndAddProjectReference(getShell(),
                                        processProject,
                                        allOmProjectsThatMustBeReferenced)) {
                            /*
                             * XPD-6970: Add project reference(by asking the
                             * user) if not already added, if the user selects
                             * no to add project reference then do no add any
                             * privileges.
                             */

                            for (Privilege privilege : newPrivilegeList) {

                                String xrefId = privilege.getId();
                                /*
                                 * Check if an already existing privilege is
                                 * being added. If yes, don't add/update the
                                 * model/UI
                                 */
                                EObject findInList = null;
                                if (null != accessPrivileges) {

                                    findInList =
                                            EMFSearchUtil
                                                    .findInList(accessPrivileges
                                                            .getPrivilegeReference(),
                                                            Xpdl2Package.eINSTANCE
                                                                    .getExternalReference_Xref(),
                                                            xrefId);
                                }
                                if (null == findInList) {

                                    ExternalReference extReference =
                                            Xpdl2Factory.eINSTANCE
                                                    .createExternalReference();

                                    String location =
                                            WorkingCopyUtil.getFile(privilege)
                                                    .getName();
                                    extReference.setLocation(location);
                                    extReference
                                            .setNamespace(OMPackage.eNS_URI);
                                    extReference.setXref(xrefId);

                                    cmpdCmd.append(AddCommand
                                            .create(editingDomain,
                                                    accessPrivileges,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getRequiredAccessPrivileges_PrivilegeReference(),
                                                    extReference));
                                }
                            }
                        }
                    } else if (null != selection && selection.length == 0) {

                        /*
                         * Remove privileges container if user hasn't selected
                         * any privilege.
                         */
                        cmpdCmd.append(getRemovePrivilegesContainerCmd());

                    }

                    if (null != editingDomain && cmpdCmd.canExecute()) {

                        editingDomain.getCommandStack().execute(cmpdCmd);
                    }
                }
                return null;
            }

            /**
             * Get command to remove the contents which are there in the model,
             * but are not selected by the user.
             * 
             * @param privilegeList
             * @param newPrivilegeList
             * @param accessPrivileges
             * @return
             */
            private Command getCommandToRemoveUnnecessaryContents(
                    List<Privilege> privilegeList,
                    List<Privilege> newPrivilegeList,
                    RequiredAccessPrivileges accessPrivileges) {

                List<ExternalReference> externalRefsToBeRemoved =
                        new ArrayList<ExternalReference>();

                for (Privilege eachPrivilege : privilegeList) {

                    if (!newPrivilegeList.contains(eachPrivilege)) {

                        ExternalReference extRef =
                                getExternalReferenceForPrivilege(eachPrivilege,
                                        accessPrivileges);

                        if (extRef != null) {
                            externalRefsToBeRemoved.add(extRef);
                        }
                    }
                }

                RequiredAccessPrivileges reqdAccessPrivileges =
                        getRequiredAccessPrivileges();

                if (reqdAccessPrivileges != null) {

                    return RemoveCommand
                            .create(editingDomain,
                                    reqdAccessPrivileges,
                                    XpdExtensionPackage.eINSTANCE
                                            .getRequiredAccessPrivileges_PrivilegeReference(),
                                    externalRefsToBeRemoved);

                }

                return null;
            }

            /**
             * Get external reference for the specified privilege,
             * <code>null</code> if it's not there.
             * 
             * @param privilege
             *            Privilege whose external reference is to be figured
             *            out.
             * @param accessPrivileges
             *            Required access privileges.
             * 
             * @return external reference for the specified privilege,
             *         <code>null</code> if it's not there.
             */
            private ExternalReference getExternalReferenceForPrivilege(
                    Privilege privilege,
                    RequiredAccessPrivileges accessPrivileges) {

                for (Iterator<?> iterator =
                        accessPrivileges.getPrivilegeReference().iterator(); iterator
                        .hasNext();) {

                    ExternalReference externalReference =
                            (ExternalReference) iterator.next();

                    String xref = externalReference.getXref();
                    EObject eObject = OMUtil.getEObjectByID(xref);
                    if (eObject instanceof Privilege) {

                        Privilege priv = (Privilege) eObject;

                        if (priv.equals(privilege))

                            return externalReference;
                    }
                }

                return null;

            }

            /**
             * Get the list of new privileges according to what user has
             * selected.
             * 
             * @param selection
             *            Selection made by the user.
             * @return List of new privileges according to what user has
             *         selected.
             */
            private List<Privilege> getListOfNewPrivileges(Object[] selection) {

                List<Privilege> newPrivilegeList = new ArrayList<Privilege>();

                if (selection != null) {

                    for (Object sel : selection) {

                        if (sel instanceof Privilege) {

                            Privilege privilege = (Privilege) sel;

                            newPrivilegeList.add(privilege);
                        }
                    }
                }

                return newPrivilegeList;
            }

        };
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {

        return new TableDeleteAction(viewer,
                Messages.PrivilegesConfigurationTable_delete_label,
                Messages.PrivilegesConfigurationTable_delete_tooltip_desc) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {

                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.PrivilegesConfigurationTable_delete_command_label);
                if (null != selection && !selection.isEmpty()) {

                    RequiredAccessPrivileges requiredAccessPrivileges =
                            getRequiredAccessPrivileges();

                    if (requiredAccessPrivileges != null) {
                        /* If removing last imtesd then delete container. */
                        if (selection.size() == requiredAccessPrivileges
                                .getPrivilegeReference().size()) {
                            cmd.append(getRemovePrivilegesContainerCmd());

                        } else {
                            cmd.append(RemoveCommand
                                    .create(editingDomain,
                                            requiredAccessPrivileges,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getRequiredAccessPrivileges_PrivilegeReference(),
                                            selection.toList()));

                        }
                    }
                }

                if (null != editingDomain && cmd.canExecute()) {

                    editingDomain.getCommandStack().execute(cmd);
                }
            }
        };
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer) {

        return new TableMoveDownAction(viewer,
                Messages.PrivilegesConfigurationTable_move_down_label,
                Messages.PrivilegesConfigurationTable_move_down_tooltip_desc) {

            @Override
            protected void moveDown(Object element) {

                Command cmd =
                        getMoveParameterCommand(editingDomain,
                                new StructuredSelection(element),
                                false);

                if (cmd != null) {
                    editingDomain.getCommandStack().execute(cmd);
                }
            }
        };
    }

    /**
     * @param editingDomain2
     * @param selection
     * @param moveUp
     * @return
     */
    protected Command getMoveParameterCommand(EditingDomain editingDomain2,
            StructuredSelection selection, boolean moveUp) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.PrivilegesConfigurationTable_move_command_label) {
                    @Override
                    public boolean canExecute() {

                        return true;
                    }
                };

        if (null != selection
                && selection.getFirstElement() instanceof ExternalReference) {

            ExternalReference externalReference =
                    (ExternalReference) selection.getFirstElement();
            EObject input = getInput();
            if (null != input) {

                RequiredAccessPrivileges accessPrivileges =
                        getRequiredAccessPrivileges();
                int size = accessPrivileges.getPrivilegeReference().size();
                int index = 0;
                int i = 0;
                for (Iterator<?> iterator =
                        accessPrivileges.getPrivilegeReference().iterator(); iterator
                        .hasNext();) {

                    ExternalReference type =
                            (ExternalReference) iterator.next();
                    if (type.getXref().equals(externalReference.getXref())) {

                        index = i;
                        break;
                    }
                    i++;
                }
                if (moveUp && index > 0) {

                    cmd.append(RemoveCommand
                            .create(editingDomain,
                                    accessPrivileges,
                                    XpdExtensionPackage.eINSTANCE
                                            .getRequiredAccessPrivileges_PrivilegeReference(),
                                    externalReference));
                    cmd.append(AddCommand
                            .create(editingDomain,
                                    accessPrivileges,
                                    XpdExtensionPackage.eINSTANCE
                                            .getRequiredAccessPrivileges_PrivilegeReference(),
                                    externalReference,
                                    index - 1));
                } else if (!moveUp && index < size) {

                    cmd.append(RemoveCommand
                            .create(editingDomain,
                                    accessPrivileges,
                                    XpdExtensionPackage.eINSTANCE
                                            .getRequiredAccessPrivileges_PrivilegeReference(),
                                    externalReference));
                    cmd.append(AddCommand
                            .create(editingDomain,
                                    accessPrivileges,
                                    XpdExtensionPackage.eINSTANCE
                                            .getRequiredAccessPrivileges_PrivilegeReference(),
                                    externalReference,
                                    index + 1));
                }
            }
        }

        return cmd;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveUpAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerMoveUpAction createMoveUpAction(ColumnViewer viewer) {

        return new TableMoveUpAction(viewer,
                Messages.PrivilegesConfigurationTable_move_up_label,
                Messages.PrivilegesConfigurationTable_move_up_tooltip_desc) {

            @Override
            protected void moveUp(Object element) {

                Command cmd =
                        getMoveParameterCommand(editingDomain,
                                new StructuredSelection(element),
                                true);

                if (cmd != null) {
                    editingDomain.getCommandStack().execute(cmd);
                }
            }
        };
    }

    /**
     * Privileges content provider provides the content for privileges table
     * 
     * @author bharge
     * @since 5 Aug 2014
     */
    private class PrivilegesContentProvider implements
            IStructuredContentProvider {

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         * 
         */
        @Override
        public void dispose() {
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         * 
         * @param viewer
         * @param oldInput
         * @param newInput
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

        /**
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         * 
         * @param inputElement
         * @return
         */
        @Override
        public Object[] getElements(Object inputElement) {

            Object[] children = new Object[0];
            if (null != inputElement) {

                RequiredAccessPrivileges requiredAccessPrivileges =
                        getRequiredAccessPrivileges();

                if (null != requiredAccessPrivileges) {

                    EList<ExternalReference> privilegeReference =
                            requiredAccessPrivileges.getPrivilegeReference();
                    children = privilegeReference.toArray();
                }
            }
            return children;
        }

    }

    /**
     * Required Privileges Column that displays the organisation model
     * privileges
     * 
     * @author bharge
     * @since 31 Jul 2014
     */
    private class RequiredPrivilegesColumn extends AbstractColumn {

        private TextCellEditor textCellEditor;

        /**
         * @param editingDomain
         * @param viewer
         * @param columnLabel
         * @param style
         * @param heading
         * @param width
         */
        public RequiredPrivilegesColumn(EditingDomain editingDomain,
                ColumnViewer viewer, String columnLabel) {

            super(editingDomain, viewer, SWT.NONE, columnLabel, 100);

            textCellEditor =
                    new TextCellEditor((Composite) getViewer().getControl(),
                            SWT.READ_ONLY);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getShowImage()
         * 
         * @return
         */
        @Override
        protected boolean getShowImage() {

            return true;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected Image getImage(Object element) {

            final AdapterFactoryLabelProvider labelProvider;
            if (element instanceof ExternalReference) {

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(getInput());
                if (null != wc) {

                    ExternalReference externalReference =
                            (ExternalReference) element;
                    String xref = externalReference.getXref();
                    EObject eObject = OMUtil.getEObjectByID(xref);
                    if (eObject instanceof Privilege) {

                        labelProvider =
                                new AdapterFactoryLabelProvider(
                                        wc.getAdapterFactory());
                        return labelProvider.getImage(eObject);
                    }
                }
            }
            return null; // super.getImage(element);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected String getText(Object element) {

            if (element instanceof ExternalReference) {

                ExternalReference externalReference =
                        (ExternalReference) element;
                String xref = externalReference.getXref();
                EObject eObject = OMUtil.getEObjectByID(xref);
                if (null == eObject) {

                    /*
                     * privilege is removed from Organisation Model but still
                     * exists in the xpdl model
                     */
                    return Messages.PrivilegeConfigurationTable_MissingPrivilege_text;
                }
                if (eObject instanceof Privilege) {

                    return ((Privilege) eObject).getDisplayName();
                }
            }
            return ""; //$NON-NLS-1$
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected CellEditor getCellEditor(Object element) {

            return textCellEditor;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected Object getValueForEditor(Object element) {

            return getText(element);
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

    }
}
