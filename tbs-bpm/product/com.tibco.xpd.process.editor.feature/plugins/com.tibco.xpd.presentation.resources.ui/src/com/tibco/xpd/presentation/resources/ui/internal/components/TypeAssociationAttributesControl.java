/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.components;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.Attribute;
import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerEditAction;

/**
 * Control for managing attributes for type associations channels.
 * <p>
 * <i>Created: 27 Mar 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class TypeAssociationAttributesControl extends BaseTableControl
        implements ISelectionChangedListener {

    /**
     * Creates control.
     * 
     * @param parent
     *            the parent composite.
     * @param toolkit
     *            the toolkit used to create controls.
     */
    public TypeAssociationAttributesControl(Composite parent, XpdToolkit toolkit) {
        super(parent, toolkit);
    }

    /** {@inheritDoc} */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    protected ViewerEditAction createEditAction(ColumnViewer viewer) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        if (movableFeatures == null) {
            movableFeatures = super.getMovableFeatures();
            movableFeatures
                    .add(ChannelsPackage.Literals.TYPE_ASSOCIATION__ATTRIBUTE_VALUES);
        }
        return movableFeatures;
    }

    /** {@inheritDoc} */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        new AttributeNameColumn(XpdResourcesPlugin.getDefault()
                .getEditingDomain(), viewer);
        new AttributeValueColumn(XpdResourcesPlugin.getDefault()
                .getEditingDomain(), viewer, Messages.TypeAssociationAttributesControl_valueColumn_header, 200);
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
        return new IStructuredContentProvider() {
            /**
             * Returns the elements in the input, which must be either an array
             * or a <code>Collection</code>.
             */
            public Object[] getElements(Object inputElement) {
                if (inputElement instanceof TypeAssociation) {
                    final TypeAssociation typeAssoc =
                            (TypeAssociation) inputElement;
                    try {
                        Object result =
                                XpdResourcesPlugin
                                        .getDefault()
                                        .getEditingDomain()
                                        .runExclusive(new RunnableWithResult.Impl<List<Attribute>>() {
                                            /** {@inheritDoc} */
                                            public void run() {
                                                setResult(typeAssoc
                                                        .getChannelType()
                                                        .getAttributes());
                                            }
                                        });

                        if (result != null) {
                            return ((List<Attribute>) result).toArray();
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
            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
                // do nothing.
            }

            /**
             * This implementation does nothing.
             */
            public void dispose() {
                // do nothing.
            }
        };
    }
}
