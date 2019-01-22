/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
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
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.components.columns.NameColumn;
import com.tibco.xpd.rsd.ui.components.columns.PayloadArrayColumn;
import com.tibco.xpd.rsd.ui.components.columns.PayloadTypeColumn;
import com.tibco.xpd.rsd.ui.components.columns.StatusCodeColumn;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * UI Component for the editing of the metod's errors.
 * 
 * @author jarciuch
 * @since 6 Jan 2015
 */
public class FaultsControl extends BaseTableControl {

    private static final EReference FEATURE = RsdPackage.eINSTANCE
            .getMethod_Faults();

    private IStructuredContentProvider contentProvider;

    /**
     * Creates control.
     * 
     * @param parent
     *            the parent composite.
     * @param toolkit
     *            the toolkit used to create controls.
     */
    public FaultsControl(Composite parent, XpdToolkit toolkit) {
        super(parent, toolkit, null, true);
    }

    /** {@inheritDoc} */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer,
                Messages.FaultsControl_AddFault_label,
                Messages.FaultsControl_AddFault_tooltip) {
            @Override
            protected Object addRow(StructuredViewer viewer) {
                EObject input = (EObject) viewer.getInput();
                Fault fault = null;
                if (input instanceof Method) {
                    Method method = (Method) input;
                    fault =
                            com.tibco.xpd.rsd.RsdFactory.eINSTANCE
                                    .createFault();
                    String defaultName =
                            RsdEditingUtil
                                    .getDefaultName(Messages.FaultsControl_NewFault_prefix,
                                            method,
                                            RsdPackage.eINSTANCE.getFault());
                    fault.setName(defaultName);
                    Command cmd =
                            AddCommand.create(getEditingDomain(),
                                    input,
                                    FEATURE,
                                    fault);
                    ((AddCommand) cmd)
                            .setLabel(Messages.FaultsControl_AddFaultCmd_label);
                    getEditingDomain().getCommandStack().execute(cmd);
                }
                return fault;
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
                Messages.FaultsControl_RemoveFault_label,
                Messages.FaultsControl_RemoveFault_tooltip) {
            @Override
            protected void deleteRows(IStructuredSelection selection) {
                EObject input = (EObject) viewer.getInput();

                if (input instanceof Method && selection != null
                        && !selection.isEmpty()) {
                    Command cmd =
                            RemoveCommand.create(getEditingDomain(),
                                    input,
                                    FEATURE,
                                    selection.toList());
                    ((RemoveCommand) cmd)
                            .setLabel(selection.size() == 1 ? Messages.FaultsControl_RemoveFaultCmd_label
                                    : Messages.FaultsControl_RemoveFaultsCmd_label);
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
            movableFeatures.add(RsdPackage.eINSTANCE.getMethod_Faults());
        }
        return movableFeatures;
    }

    /** {@inheritDoc} */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        new NameColumn(ed, viewer, /* editable = */true);
        new StatusCodeColumn(ed, viewer, /* editable = */true);
        new PayloadTypeColumn(ed, viewer);
        new PayloadArrayColumn(ed, viewer);
        setColumnProportions(new float[] { .4f, .2f, .3f, .1f });
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
                @Override
                public Object[] getElements(Object inputElement) {
                    if (inputElement instanceof Method) {
                        final Method method = (Method) inputElement;
                        try {
                            @SuppressWarnings("unchecked")
                            List<Fault> result =
                                    (List<Fault>) getEditingDomain()
                                            .runExclusive(new RunnableWithResult.Impl<List<Fault>>() {
                                                /** {@inheritDoc} */
                                                @Override
                                                public void run() {
                                                    setResult(method
                                                            .getFaults());
                                                }
                                            });

                            if (result != null) {
                                return result.toArray();
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
