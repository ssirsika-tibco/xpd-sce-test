/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerEditAction;
import com.tibco.xpd.rsd.NamedElement;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterContainer;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.components.columns.NameColumn;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * UI control with a table viewer for the editing parameters.
 *
 * @author jarciuch
 * @since 6 Jan 2015
 */
public class ParamsControl extends BaseTableControl {

    private static final EReference FEATURE = RsdPackage.eINSTANCE
            .getParameterContainer_Parameters();

    private IStructuredContentProvider contentProvider;

    /**
     * Creates control.
     * 
     * @param parent
     *            the parent composite.
     * @param toolkit
     *            the toolkit used to create controls.
     */
    public ParamsControl(Composite parent, XpdToolkit toolkit) {
        super(parent, toolkit, null, true);
    }

    /**
     * Creates and initialises a new parameter.
     * 
     * @return a new parameter.
     */
    protected Parameter createNewParameter(ParameterContainer paramContainer) {
        Parameter param;
        param = com.tibco.xpd.rsd.RsdFactory.eINSTANCE.createParameter();
        String name =
                RsdEditingUtil
                        .getDefaultName(Messages.ParamsControl_NewParam_prefix,
                                paramContainer,
                                RsdPackage.eINSTANCE.getParameter());
        param.setName(name);
        return param;
    }

    /** {@inheritDoc} */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer,
                Messages.ParamsControl_AddParam_label,
                Messages.ParamsControl_AddParam_tooltip) {
            @Override
            protected Object addRow(StructuredViewer viewer) {
                EObject input = (EObject) viewer.getInput();
                Parameter param = null;
                if (input instanceof ParameterContainer) {
                    ParameterContainer paramContainer =
                            (ParameterContainer) input;
                    param = createNewParameter(paramContainer);

                    Command cmd =
                            AddCommand.create(getEditingDomain(),
                                    paramContainer,
                                    FEATURE,
                                    param);
                    ((AddCommand) cmd)
                            .setLabel(Messages.ParamsControl_AddParamCmd_label);
                    getEditingDomain().getCommandStack().execute(cmd);
                }
                return param;
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    protected ViewerEditAction createEditAction(ColumnViewer viewer) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    protected ViewerDeleteAction createDeleteAction(final ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.ParamsControl_RemoveParam_label,
                Messages.ParamsControl_RemoveParam_tooltip) {
            @Override
            protected void deleteRows(IStructuredSelection selection) {
                EObject input = (EObject) viewer.getInput();

                if (input instanceof ParameterContainer && selection != null
                        && !selection.isEmpty()) {
                    Command cmd =
                            RemoveCommand.create(getEditingDomain(),
                                    input,
                                    FEATURE,
                                    selection.toList());
                    ((RemoveCommand) cmd)
                            .setLabel(selection.size() == 1 ? Messages.ParamsControl_RemoveParamCmd_label
                                    : Messages.ParamsControl_RemoveParamsCmd_label);
                    getEditingDomain().getCommandStack().execute(cmd);
                }
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        if (movableFeatures == null) {
            movableFeatures = super.getMovableFeatures();
            // TODO Because of separate query and header parameter tables moving
            // will not work correctly.

            // movableFeatures.add(RsdPackage.eINSTANCE
            // .getParameterContainer_Parameters());
        }
        return movableFeatures;
    }

    /** {@inheritDoc} */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        /*
         * Subclasses should call super.addColumms(...) and could add more
         * columns after.
         */
        new NameColumn(XpdResourcesPlugin.getDefault().getEditingDomain(),
                viewer, true) {
            /** {@inheritDoc} */
            @Override
            protected String verifyName(NamedElement element, String nameText) {
                // Do base class validations.
                String baseValidations = super.verifyName(element, nameText);
                if (baseValidations != null) {
                    return baseValidations;
                }

                // Validate that parameters have different names. The name must
                // be unique within the same style of parameters.
                if (element instanceof Parameter && nameText != null) {
                    Parameter selectedParam = (Parameter) element;
                    if (selectedParam.eContainer() instanceof ParameterContainer) {
                        EList<Parameter> parameters =
                                ((ParameterContainer) selectedParam
                                        .eContainer()).getParameters();
                        for (Parameter p : parameters) {
                            if (p != selectedParam
                                    && nameText.equals(p.getName())
                                    && p.getStyle() == selectedParam.getStyle()) {
                                return Messages.ParamsControl_NotUniqueNames_message;
                            }
                        }
                    }
                }
                return null;
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    protected IContentProvider getViewerContentProvider() {
        if (contentProvider == null) {
            contentProvider = new IStructuredContentProvider() {
                /**
                 * Returns the elements in the input, which must be either an
                 * array or a <code>Collection</code>.
                 */
                @SuppressWarnings("unchecked")
                @Override
                public Object[] getElements(Object inputElement) {
                    if (inputElement instanceof ParameterContainer) {
                        final ParameterContainer paramsContainer =
                                (ParameterContainer) inputElement;
                        try {
                            Object result =
                                    getEditingDomain()
                                            .runExclusive(new RunnableWithResult.Impl<List<Parameter>>() {
                                                /** {@inheritDoc} */
                                                @Override
                                                public void run() {
                                                    setResult(paramsContainer
                                                            .getParameters());
                                                }
                                            });

                            if (result != null) {
                                return ((List<Parameter>) result).toArray();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return new Object[0];
                }

                /**
                 * This implementation does nothing.
                 */
                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                    // do nothing.
                }

                /**
                 * This implementation does nothing.
                 */
                @Override
                public void dispose() {
                    // do nothing.
                }
            };

        }
        return contentProvider;
    }

    /*
     * Helper method to get the global editing domain.
     */
    private TransactionalEditingDomain getEditingDomain() {
        return XpdResourcesPlugin.getDefault().getEditingDomain();
    }
}
