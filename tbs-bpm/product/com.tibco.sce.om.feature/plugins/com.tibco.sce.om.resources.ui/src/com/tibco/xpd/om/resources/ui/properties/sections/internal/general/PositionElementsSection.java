/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.om.core.om.MultipleFeature;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section to set the Position Elements (table view).
 * 
 * @author njpatel
 * 
 */
public class PositionElementsSection extends AbstractGeneralSection {

    private PositionFeaturesTable positionFeaturesTable;

    private ItemProviderAdapter itemProvider;

    /**
     * Position element table section.
     */
    public PositionElementsSection() {
        setShouldUseExtraSpace(true);
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        EObject input = getInput();
        if (input != null) {
            IEditingDomainItemProvider provider =
                    (IEditingDomainItemProvider) XpdResourcesPlugin
                            .getDefault().getAdapterFactory().adapt(input,
                                    IEditingDomainItemProvider.class);
            if (provider instanceof ItemProviderAdapter) {
                itemProvider = (ItemProviderAdapter) provider;
            }
        }
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        Label lbl =
                createLabel(root,
                        toolkit,
                        Messages.PositionElementsSection_positionElements_label);
        GridData gData = new GridData(SWT.LEFT, SWT.TOP, false, false);
        gData.widthHint = STANDARD_LABEL_WIDTH;
        lbl.setLayoutData(gData);

        positionFeaturesTable =
                new PositionFeaturesTable(root, toolkit, SWT.FULL_SELECTION
                        | SWT.MULTI, getEditingDomain());

        positionFeaturesTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));
        positionFeaturesTable.setBackground(root.getBackground());

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Table handles this
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof OrgUnitType) {
            positionFeaturesTable.getViewer().cancelEditing();
            positionFeaturesTable.getViewer().setInput(getInput());
        }
    }

    /**
     * Get the position features of the input
     * 
     * @return
     */
    private EList<PositionFeature> getFeatures(EObject input) {
        EList<PositionFeature> features = new BasicEList<PositionFeature>();
        if (input != null && itemProvider != null) {
            Collection<?> children = itemProvider.getChildren(input);

            if (children != null) {
                for (Object child : children) {
                    if (child instanceof PositionFeature) {
                        features.add((PositionFeature) child);
                    }
                }
            }
        }
        return features;
    }

    /**
     * Position feature table to allow user to add/remove
     * {@link PositionFeature}s.
     * 
     * @author njpatel
     * 
     */
    private class PositionFeaturesTable extends MultipleFeatureTable {

        public PositionFeaturesTable(Composite parent, XpdToolkit toolkit,
                int style, EditingDomain domain) {
            super(parent, toolkit);
        }

        @Override
        protected String getPickerType() {
            return OMTypeQuery.TYPE_ID_POSITION_TYPE;
        }

        @Override
        protected OrgElementType getElementType(MultipleFeature element) {
            if (element instanceof PositionFeature) {
                return ((PositionFeature) element).getFeatureType();
            }
            return null;
        }

        @Override
        protected Set<EStructuralFeature> getDeletableFeatures() {
            Set<EStructuralFeature> features = super.getDeletableFeatures();
            features.add(OMPackage.Literals.ORG_UNIT_TYPE__POSITION_FEATURES);
            return features;
        }

        @Override
        protected Set<EStructuralFeature> getMovableFeatures() {
            Set<EStructuralFeature> features = super.getMovableFeatures();
            features.add(OMPackage.Literals.ORG_UNIT_TYPE__POSITION_FEATURES);
            return features;
        }

        @Override
        protected ViewerAddAction createAddAction(final ColumnViewer viewer) {
            return new ViewerAddAction(viewer,
                    Messages.PositionElementsSection_add_action,
                    Messages.PositionElementsSection_add_action_tooltip) {
                @Override
                public void run() {
                    EditingDomain ed = getEditingDomain();
                    EObject input = getInput();
                    if (ed != null && input != null && itemProvider != null) {
                        Collection<?> descriptors =
                                itemProvider.getNewChildDescriptors(getInput(),
                                        ed,
                                        null);

                        if (descriptors != null) {
                            for (Object desc : descriptors) {
                                if (desc instanceof CommandParameter
                                        && ((CommandParameter) desc).getValue() instanceof PositionFeature) {
                                    Command cmd =
                                            CreateChildCommand
                                                    .create(getEditingDomain(),
                                                            input,
                                                            desc,
                                                            Collections
                                                                    .singleton(input));

                                    if (cmd != null) {
                                        viewer.cancelEditing();
                                        ed.getCommandStack().execute(cmd);

                                        Collection<?> result = cmd.getResult();

                                        if (result != null) {
                                            Object obj =
                                                    result.iterator().next();
                                            viewer.editElement(obj, 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            };
        }

        @Override
        protected Command getSetTypeCommand(EditingDomain editingDomain,
                MultipleFeature feature, OrgElementType type) {
            if (feature instanceof PositionFeature) {
                return SetCommand.create(editingDomain,
                        feature,
                        OMPackage.eINSTANCE.getPositionFeature_FeatureType(),
                        type);
            }
            return null;
        }

        @Override
        protected IContentProvider getViewerContentProvider() {
            return new IStructuredContentProvider() {

                public void dispose() {
                    // Nothing to do
                }

                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                    // Nothing to do
                }

                public Object[] getElements(Object inputElement) {
                    EList<PositionFeature> features =
                            getFeatures((EObject) inputElement);

                    return features != null ? features.toArray()
                            : new Object[0];
                }
            };
        }
    }
}
