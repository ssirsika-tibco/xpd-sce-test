/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.LabelColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.MultiplicityColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.NameColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.StereotypesColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.TypeColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.general.OperationArgumentsTable;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMEditPartUtils;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * BOM properties section for <code>Class</code> {@link Operation Operations}.
 * This will display a table of all <code>Operation</code>s defined for the
 * selected {@link Class}.
 * 
 * @author njpatel
 * 
 */
public class OperationsTabSection extends AbstractTableSection {

    private static final EReference FEATURE =
            UMLPackage.eINSTANCE.getClass_OwnedOperation();

    private ArgumentsDialog argDialog;

    private StereotypesColumn stereotypesColumn;

    private IEditingDomainItemProvider provider;

    public OperationsTabSection() {
        super(Messages.OperationsTabSection_section_title, FEATURE);
    }

    @Override
    protected void addColumns(ColumnViewer viewer) {
        new LabelColumn(getEditingDomain(), viewer, true);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(getEditingDomain(), viewer, true);
        }
        new ReturnTypeColumn(getEditingDomain(), viewer);
        new MultiplicityColumn(getEditingDomain(), viewer) {

            @Override
            protected MultiplicityElement getMultiplicityElement(Object element) {
                MultiplicityElement ret = null;
                // Multiplicity in the column is for the operation's return
                // type
                if (element instanceof Operation) {
                    ret = getReturnParameter((Operation) element);
                }
                return ret;
            }
        };
        new ArgumentsColumn(getEditingDomain(), viewer, getToolkit());
        stereotypesColumn = new StereotypesColumn(getEditingDomain(), viewer);
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        EObject input = getInput();
        // Hide the stereotype column if this is a first-class profile model
        if (input instanceof Element) {
            setVisible(stereotypesColumn, !FirstClassProfileManager
                    .getInstance().isFirstClassProfileApplied(((Element) input)
                            .getModel()));

            provider =
                    (IEditingDomainItemProvider) XpdResourcesPlugin
                            .getDefault().getAdapterFactory().adapt(input,
                                    IEditingDomainItemProvider.class);
        }
    }

    @Override
    protected TableAddAction getAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer,
                Messages.OperationsTabSection_add_action,
                Messages.OperationsTabSection_add_action_tooltip) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                EObject input = getInput();
                if (provider != null && input instanceof Class) {
                    Collection<?> descriptors =
                            provider.getNewChildDescriptors(input,
                                    getEditingDomain(),
                                    null);
                    for (Object desc : descriptors) {
                        if (desc instanceof CommandParameter) {
                            CommandParameter param = (CommandParameter) desc;
                            if (param.getFeature() instanceof IHintedType
                                    && ((IHintedType) param.getFeature())
                                            .getEClass() == UMLPackage.eINSTANCE
                                            .getOperation()) {
                                Command cmd =
                                        CreateChildCommand
                                                .create(getEditingDomain(),
                                                        input,
                                                        desc,
                                                        Collections
                                                                .singletonList(input));
                                getEditingDomain().getCommandStack()
                                        .execute(cmd);

                                if (cmd.getResult() != null
                                        && !cmd.getResult().isEmpty()) {
                                    return cmd.getResult().iterator().next();
                                }
                            }
                        }
                    }

                    // Operation op = UMLFactory.eINSTANCE.createOperation();
                    // op.setName(UniquenessStrategy.getInstance()
                    // .getUniqueOperationName((Class) input));
                    //
                    // Command cmd =
                    // AddCommand.create(getEditingDomain(),
                    // input,
                    // UMLPackage.eINSTANCE
                    // .getClass_OwnedOperation(),
                    // op);
                    // ((AddCommand) cmd)
                    // .setLabel(Messages.
                    // OperationsTabSection_addOperation_command_label);
                    // getEditingDomain().getCommandStack().execute(cmd);
                    //
                    // return op;
                }
                return null;
            }
        };
    }

    @Override
    protected TableDeleteAction getDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.OperationsTabSection_remove_action,
                Messages.OperationsTabSection_remove_action_tooltip) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                EList<Operation> ops = new BasicEList<Operation>();
                EObject input = getInput();
                if (input instanceof Class && selection != null
                        && !selection.isEmpty()) {
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        Object next = iter.next();
                        if (next instanceof Operation) {
                            ops.add((Operation) next);
                        }
                    }

                    if (!ops.isEmpty()) {
                        Command cmd =
                                RemoveCommand.create(getEditingDomain(),
                                        input,
                                        UMLPackage.eINSTANCE
                                                .getClass_OwnedOperation(),
                                        ops);
                        ((RemoveCommand) cmd)
                                .setLabel(ops.size() > 1 ? Messages.OperationsTabSection_removeMultipleOperations_command_label
                                        : Messages.OperationsTabSection_removeSingleOperation_command_label);
                        getEditingDomain().getCommandStack().execute(cmd);
                    }
                }
            }
        };
    }

    public boolean select(Object toTest) {

        EObject input = resollveInput(toTest);
        boolean show = false;

        if (input instanceof Class) {
            Class cl = (Class) input;

            Model model = cl.getModel();

            if (FirstClassProfileManager.getInstance()
                    .isFirstClassProfileApplied(model)) {

                IFirstClassProfileExtension ext =
                        FirstClassProfileManager.getInstance()
                                .getAppliedFirstClassProfile(model);

                if (ext.showOperations()) {
                    show = true;
                }
            } else {
                show = true;
            }
        }

        return show;
    }

    @Override
    protected void doRefresh() {
        super.doRefresh();
        // If the argument dialog is available then refresh it
        if (argDialog != null) {
            argDialog.refresh();
        }
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        if (notifications != null) {
            for (Notification notification : notifications) {
                if (BOMEditPartUtils.isStereotypeBeingSet(notification)) {
                    Object featureObjectAffected =
                            BOMEditPartUtils
                                    .getFeatureObjectAffected(notification);
                    /*
                     * The object being affected will be an operation so check
                     * if it belongs to the input (Class)
                     */
                    if (featureObjectAffected instanceof EObject
                            && ((EObject) featureObjectAffected).eContainer() == getInput()) {
                        return true;
                    }
                }
            }
        }
        return super.shouldRefresh(notifications);
    }

    /**
     * Get all arguments of the given operation.
     * 
     * @param operation
     * @return
     */
    private EList<Parameter> getArguments(Operation operation) {
        EList<Parameter> args = new BasicEList<Parameter>();
        if (operation != null) {

            for (Parameter param : operation.getOwnedParameters()) {
                if (param.getDirection() != ParameterDirectionKind.RETURN_LITERAL) {
                    args.add(param);
                }
            }
        }
        return args;
    }

    /**
     * Get the return parameter of the operation.
     * 
     * @param operation
     * @return
     */
    private Parameter getReturnParameter(Operation operation) {
        EList<Parameter> ownedParams = operation.getOwnedParameters();

        // Look for the return type
        for (Parameter parameter : ownedParams) {
            if (parameter.getDirection() == ParameterDirectionKind.RETURN_LITERAL) {
                return parameter;
            }
        }
        return null;
    }

    /**
     * Return type picker column for the operations table.
     * 
     * @author njpatel
     * 
     */
    private class ReturnTypeColumn extends TypeColumn {

        public ReturnTypeColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer,
                    Messages.OperationsTabSection_returnType_column_title, 200,
                    true);
        }

        @Override
        protected Type getType(Object element) {
            return element instanceof Operation ? ((Operation) element)
                    .getType() : null;
        }

        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            Command cmd = null;
            if (element instanceof Operation) {
                if (value != null) {
                    // Set return type
                    cmd =
                            SetCommand.create(getEditingDomain(),
                                    element,
                                    UMLPackage.eINSTANCE.getOperation_Type(),
                                    value);
                    ((SetCommand) cmd)
                            .setLabel(Messages.OperationsTabSection_setReturnType_command_label);
                } else {
                    Parameter param = getReturnParameter((Operation) element);
                    if (param != null) {
                        // Remove return parameter
                        cmd =
                                RemoveCommand
                                        .create(getEditingDomain(),
                                                element,
                                                UMLPackage.eINSTANCE
                                                        .getBehavioralFeature_OwnedParameter(),
                                                param);
                        ((RemoveCommand) cmd)
                                .setLabel(Messages.OperationsTabSection_clearReturnType_command_label);

                    }
                }
            }
            return cmd;
        }
    }

    /**
     * Arguments column for the operations table.
     * 
     * @author njpatel
     * 
     */
    private class ArgumentsColumn extends AbstractColumn {

        private final ArgumentsCellEditor editor;

        public ArgumentsColumn(EditingDomain editingDomain,
                final ColumnViewer viewer, final XpdToolkit toolkit) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.OperationsTabSection_arguments_column_title, 140);

            editor =
                    new ArgumentsCellEditor((Composite) viewer.getControl(),
                            toolkit);
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof Operation) {
                editor.setInput((Operation) element);
                return editor;
            }
            return null;
        }

        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            // This will do nothing as the pop-up dialog will update the
            // arguments
            return null;
        }

        @Override
        protected String getText(Object element) {
            StringBuffer txt = new StringBuffer(""); //$NON-NLS-1$
            if (element instanceof Operation) {
                EList<Parameter> arguments = getArguments((Operation) element);
                if (arguments != null) {
                    for (Parameter param : arguments) {
                        if (txt.length() > 0) {
                            txt.append("; "); //$NON-NLS-1$
                        }
                        if (param.getType() != null) {
                            txt.append(String.format("%1$s (%2$s)", param //$NON-NLS-1$
                                    .getName(), param.getType().getName()));
                        } else {
                            txt.append(param.getName());
                        }
                    }
                }
            }
            return txt.toString();
        }

        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }
    }

    /**
     * Argument column's cell editor that will pop-up a dialog to set the
     * arguments of an operation.
     * 
     * @author njpatel
     * 
     */
    private class ArgumentsCellEditor extends DialogCellEditor {

        private final XpdToolkit toolkit;

        private Operation operation;

        private final Composite parent;

        public ArgumentsCellEditor(Composite parent, XpdToolkit toolkit) {
            super(parent);
            this.parent = parent;
            this.toolkit = toolkit;
        }

        /**
         * Set the input for the argument's table contained in the dialog.
         * 
         * @param operation
         */
        public void setInput(Operation operation) {
            this.operation = operation;
        }

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            if (operation != null) {
                if (argDialog == null) {
                    argDialog = new ArgumentsDialog(parent.getShell(), toolkit);
                }
                argDialog.open();
            }
            return null;
        }

    }

    /**
     * Dialog to set the operation arguments.
     * 
     * @author njpatel
     * 
     */
    private class ArgumentsDialog extends Dialog {

        private OperationArgumentsTable argsTable;

        private final XpdToolkit toolkit;

        private Operation operation;

        protected ArgumentsDialog(Shell parentShell, XpdToolkit toolkit) {
            super(parentShell);
            this.toolkit = toolkit;
            setShellStyle(SWT.ON_TOP | SWT.APPLICATION_MODAL | SWT.CLOSE
                    | SWT.RESIZE);
        }

        /**
         * Update the arguments table.
         */
        public void refresh() {
            BaseTableControl table = getTable();
            if (table != null) {
                TableViewer viewer = table.getTableViewer();

                if (viewer != null) {
                    IStructuredSelection selection =
                            (IStructuredSelection) viewer.getSelection();

                    if (selection.getFirstElement() instanceof Operation
                            && argsTable != null && !argsTable.isDisposed()) {
                        argsTable.getViewer().cancelEditing();
                        argsTable.getViewer().setInput(selection
                                .getFirstElement());
                    }
                }
            }
        }

        @Override
        protected void configureShell(Shell newShell) {
            super.configureShell(newShell);
            newShell
                    .setText(Messages.OperationsTabSection_setOperationArguments_command_label);
        }

        @Override
        protected Control createDialogArea(Composite parent) {
            Composite root = (Composite) super.createDialogArea(parent);

            CLabel lbl = new CLabel(root, SWT.NONE);
            GridData data = new GridData(SWT.FILL, SWT.CENTER, false, false);
            data.widthHint = 300;
            lbl.setLayoutData(data);
            lbl
                    .setText(shortenText(Messages.OperationsTabSection_setOperationArguments_command_label,
                            lbl));

            argsTable =
                    new OperationArgumentsTable(root, toolkit,
                            getEditingDomain(), operation);
            data = new GridData(SWT.FILL, SWT.FILL, true, true);
            data.heightHint = 150;
            argsTable.setLayoutData(data);
            argsTable.setBackground(root.getBackground());
            refresh();

            return root;
        }

        @Override
        protected void createButtonsForButtonBar(Composite parent) {
            createButton(parent,
                    IDialogConstants.OK_ID,
                    IDialogConstants.OK_LABEL,
                    true);
        }
    }
}
