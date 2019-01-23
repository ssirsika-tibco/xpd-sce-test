/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.components;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.presentation.channels.ChannelsFactory;
import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.presentation.channels.ExtendedAttribute;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteEMFAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerEditAction;

/**
 * Control for managing extended attributes for type associations channels.
 * <p>
 * <i>Created: 27 Mar 2009</i>
 * </p>
 * 
 * @author glewis
 */
public class TypeAssociationExtendedAttributesControl extends BaseTableControl
        implements ISelectionChangedListener {

    private TypeAssociation selectedTypeAssociation = null;

    /**
     * Creates control.
     * 
     * @param parent
     *            the parent composite.
     * @param toolkit
     *            the toolkit used to create controls.
     */
    public TypeAssociationExtendedAttributesControl(Composite parent,
            XpdToolkit toolkit) {
        super(parent, toolkit);
    }

    /** {@inheritDoc} */
    @Override
    protected ViewerAddAction createAddAction(final ColumnViewer viewer) {
        return new ViewerAddAction(viewer) {
            /** {@inheritDoc} */
            @Override
            public void run() {
                if (selectedTypeAssociation != null) {
                    TransactionalEditingDomain ed =
                            XpdResourcesPlugin.getDefault().getEditingDomain();
                    ed.getCommandStack().execute(new RecordingCommand(ed) {
                        @Override
                        protected void doExecute() {
                            ChannelsFactory f = ChannelsFactory.eINSTANCE;
                            ExtendedAttribute extendedAttribute =
                                    f.createExtendedAttribute();
                            extendedAttribute
                                    .setName(Messages.TypeAssociationExtendedAttributesControl_defaultName_literal);
                            extendedAttribute
                                    .setValue(Messages.TypeAssociationExtendedAttributesControl_defaultValue_literal);

                            selectedTypeAssociation.getExtendedAttributes()
                                    .add(extendedAttribute);
                        }
                    });
                    viewer.refresh();
                }
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
        return new ViewerDeleteEMFAction(viewer, Collections
                .singleton(ChannelsPackage.eINSTANCE
                        .getTypeAssociation_ExtendedAttributes())) {
            /**
             * {@inheritDoc}
             */
            @Override
            protected Command createDeleteCommand(
                    TransactionalEditingDomain ed, final List<?> selectedObjects) {
                return new RecordingCommand(ed) {
                    @Override
                    protected void doExecute() {
                        selectedTypeAssociation.getExtendedAttributes()
                                .removeAll(selectedObjects);
                    }
                };
            }

        };
    }

    /** {@inheritDoc} */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        if (movableFeatures == null) {
            movableFeatures = super.getMovableFeatures();
            movableFeatures
                    .add(ChannelsPackage.Literals.TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES);
        }
        return movableFeatures;
    }

    /** {@inheritDoc} */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        new ExtendedAttributeNameColumn(
                XpdResourcesPlugin.getDefault().getEditingDomain(),
                viewer,
                Messages.TypeAssociationExtendedAttributesControl_nameColumn_header,
                200);
        new ExtendedAttributeValueColumn(
                XpdResourcesPlugin.getDefault().getEditingDomain(),
                viewer,
                Messages.TypeAssociationExtendedAttributesControl_valueColumn_header,
                200);
    }

    /** {@inheritDoc} */
    public void selectionChanged(SelectionChangedEvent event) {
        if (!(event.getSelection() instanceof IStructuredSelection)) {
            return;
        }
        IStructuredSelection s = (IStructuredSelection) event.getSelection();
        if (!s.isEmpty() && s.getFirstElement() instanceof TypeAssociation) {
            final TypeAssociation typeAssoc =
                    (TypeAssociation) s.getFirstElement();
            getViewer().setInput(typeAssoc);
            return;
        }

        getViewer().setInput(null);

    }

    /** {@inheritDoc} */
    @Override
    protected IContentProvider getViewerContentProvider() {
        return new TransactionalAdapterFactoryContentProvider(
                XpdResourcesPlugin.getDefault().getEditingDomain(),
                XpdResourcesPlugin.getDefault().getAdapterFactory()) {
            /**
             * Returns the elements in the input, which must be either an array
             * or a <code>Collection</code>.
             */
            @Override
            public Object[] getElements(Object inputElement) {
                if (inputElement instanceof TypeAssociation) {
                    selectedTypeAssociation = (TypeAssociation) inputElement;
                    // addAction.setEnabled(true);
                    // deleteAction.setEnabled(true);
                    update();
                    try {
                        Object result =
                                XpdResourcesPlugin
                                        .getDefault()
                                        .getEditingDomain()
                                        .runExclusive(new RunnableWithResult.Impl<List<ExtendedAttribute>>() {
                                            /** {@inheritDoc} */
                                            public void run() {
                                                setResult(selectedTypeAssociation
                                                        .getExtendedAttributes());
                                            }
                                        });

                        if (result != null) {
                            return ((List<ExtendedAttribute>) result).toArray();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return new Object[0];
            }
        };
    }
}
