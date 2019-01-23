/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.LabelColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.MultiplicityColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.NameColumn;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.TypeColumn;
import com.tibco.xpd.bom.resources.utils.UniquenessStrategy;
import com.tibco.xpd.bom.types.PrimitivesUtil;
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
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Table used to edit arguments of an {@link Operation}.
 * 
 * @author njpatel
 * 
 */
public class OperationArgumentsTable extends BaseTableControl {

    private final EditingDomain editingDomain;

    /**
     * Operation arguments' table.
     * 
     * @param parent
     * @param toolkit
     * @param editingDomain
     */
    public OperationArgumentsTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        this(parent, toolkit, editingDomain, null);
    }

    /**
     * Operation arguments' table.
     * 
     * @param parent
     * @param toolkit
     * @param editingDomain
     * @param viewerInput
     *            table input
     */
    public OperationArgumentsTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain, Object viewerInput) {
        super(parent, toolkit, viewerInput, false);
        this.editingDomain = editingDomain;
        createContents(parent, toolkit, viewerInput);
    }

    @Override
    protected IContentProvider getViewerContentProvider() {
        return new IStructuredContentProvider() {

            public Object[] getElements(Object inputElement) {
                Object[] values = null;
                // Get the operation's arguments
                if (inputElement instanceof Operation) {
                    EList<Parameter> arguments =
                            getArguments((Operation) inputElement);
                    if (arguments != null) {
                        values = arguments.toArray();
                    }
                }
                return values != null ? values : new Object[0];
            }

            public void dispose() {
            }

            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
            }

        };
    }

    @Override
    protected void addColumns(ColumnViewer viewer) {
        new LabelColumn(editingDomain, viewer, true);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(editingDomain, viewer, true);
        }
        new TypeColumn(editingDomain, viewer,
                Messages.OperationArgumentsTable_typeColumn_title, 280) {

            @Override
            protected Type getType(Object element) {
                return element instanceof TypedElement ? ((TypedElement) element)
                        .getType()
                        : null;
            }

            @Override
            protected Command getSetValueCommand(Object element, Object value) {
                if (element instanceof TypedElement && value != null) {
                    return SetCommand.create(getEditingDomain(),
                            element,
                            UMLPackage.eINSTANCE.getTypedElement_Type(),
                            value);
                }
                return null;
            }

        };
        new MultiplicityColumn(editingDomain, viewer) {
            @Override
            protected MultiplicityElement getMultiplicityElement(Object element) {
                MultiplicityElement ret = null;
                if (element instanceof Parameter) {
                    ret = (Parameter) element;
                }
                return ret;
            }
        };
    }

    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer,
                Messages.OperationArgumentsTable_add_action,
                Messages.OperationArgumentsTable_add_action_tooltip) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                Object input = viewer.getInput();
                Assert
                        .isNotNull(input,
                                "No input provided to the operation's arguments table."); //$NON-NLS-1$
                Assert
                        .isTrue(input instanceof Operation,
                                "Operation expected as input for the operation's arguments table."); //$NON-NLS-1$

                Operation op = (Operation) input;

                String name =
                        UniquenessStrategy.getInstance()
                                .getUniqueParameterName(op);

                Parameter param = UMLFactory.eINSTANCE.createParameter();
                param.setName(name);
                param.setType(PrimitivesUtil.getDefaultPrimitiveType(op
                        .eResource().getResourceSet()));
                editingDomain.getCommandStack().execute(AddCommand
                        .create(editingDomain, op, UMLPackage.eINSTANCE
                                .getBehavioralFeature_OwnedParameter(), param));
                return param;
            }
        };
    }

    @Override
    protected ViewerDeleteAction createDeleteAction(final ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.OperationArgumentsTable_delete_action,
                Messages.OperationArgumentsTable_delete_action_tooltip) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                Object input = viewer.getInput();
                Assert
                        .isNotNull(input,
                                "No input provided to the operation's arguments table."); //$NON-NLS-1$
                Assert
                        .isTrue(input instanceof Operation,
                                "Operation expected as input for the operation's arguments table."); //$NON-NLS-1$
                if (selection != null) {
                    EList<Parameter> params = new BasicEList<Parameter>();
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        Object next = iter.next();
                        if (next instanceof Parameter) {
                            params.add((Parameter) next);
                        }
                    }

                    if (!params.isEmpty()) {
                        editingDomain
                                .getCommandStack()
                                .execute(RemoveCommand
                                        .create(editingDomain,
                                                input,
                                                UMLPackage.eINSTANCE
                                                        .getBehavioralFeature_OwnedParameter(),
                                                params));
                    }
                }
            }
        };
    }

    @Override
    protected ViewerMoveUpAction createMoveUpAction(final ColumnViewer viewer) {
        return new TableMoveUpAction(viewer,
                Messages.OperationArgumentsTable_moveUp_action,
                Messages.OperationArgumentsTable_moveUp_action_tooltip) {

            @Override
            protected void moveUp(Object element) {
                Object input = viewer.getInput();
                Assert
                        .isNotNull(input,
                                "No input provided to the operation's arguments table."); //$NON-NLS-1$
                Assert
                        .isTrue(input instanceof Operation,
                                "Operation expected as input for the operation's arguments table."); //$NON-NLS-1$
                Operation op = (Operation) input;
                EList<Parameter> parameters = (op).getOwnedParameters();
                int idx = parameters.indexOf(element);
                if (idx > 0) {
                    // If the previous parameter in the list is the return type
                    // then we need to move over that
                    if (parameters.get(idx - 1).getDirection() == ParameterDirectionKind.RETURN_LITERAL) {
                        --idx;
                    }
                    editingDomain.getCommandStack().execute(new MoveCommand(
                            editingDomain, op, UMLPackage.eINSTANCE
                                    .getBehavioralFeature_OwnedParameter(),
                            element, --idx));
                }
            }
        };
    }

    @Override
    protected ViewerMoveDownAction createMoveDownAction(
            final ColumnViewer viewer) {
        return new TableMoveDownAction(viewer,
                Messages.OperationArgumentsTable_moveDown_action,
                Messages.OperationArgumentsTable_moveDown_action_tooltip) {

            @Override
            protected void moveDown(Object element) {
                Object input = viewer.getInput();
                Assert
                        .isNotNull(input,
                                "No input provided to the operation's arguments table."); //$NON-NLS-1$
                Assert
                        .isTrue(input instanceof Operation,
                                "Operation expected as input for the operation's arguments table."); //$NON-NLS-1$
                Operation op = (Operation) input;
                EList<Parameter> parameters = (op).getOwnedParameters();
                int idx = parameters.indexOf(element);
                if (idx < parameters.size()) {
                    // If the next parameter in the list is the return type
                    // then we need to move over that
                    if (parameters.get(idx + 1).getDirection() == ParameterDirectionKind.RETURN_LITERAL) {
                        ++idx;
                    }
                    editingDomain.getCommandStack().execute(new MoveCommand(
                            editingDomain, op, UMLPackage.eINSTANCE
                                    .getBehavioralFeature_OwnedParameter(),
                            element, ++idx));
                }
            }
        };
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
}
