/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.Feature;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property section for the setting of a {@link Feature}. This will be available
 * for an {@link OrgUnit} or a {@link Position}.
 * 
 * @author njpatel
 */
public class FeatureSection extends AbstractGeneralSection implements IFilter {

    private CCombo featureCmb;

    private ComboViewer cViewer;

    private boolean noElements = false;

    private boolean listenToChanges = false;

    private static final String NO_ELEMENTS_AVAILABLE =
            Messages.FeatureSection_noElementsAvailable_shortdesc;

    private static final String NO_ELEMENTS_AVAILABLE_FOR_ORGUNIT =
            Messages.FeatureSection_noElementsAvailable_OrgUnit_shortdesc;

    private static final String NO_ELEMENTS_AVAILABLE_FOR_ORGUNIT_WITH_PARENT =
            Messages.FeatureSection_noElementsAvailable_OrgUnit_WithParent_shortdesc;

    private static final String NO_ELEMENTS_AVAILABLE_FOR_POSITION =
            Messages.FeatureSection_noElementsAvailable_Position_shortdesc;

    private Label typeLabel;

    public FeatureSection() {

    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        typeLabel =
                createLabel(root, toolkit, Messages.FeatureSection_type_label);
        featureCmb = toolkit.createCCombo(root, "feature-list"); //$NON-NLS-1$
        featureCmb
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        cViewer = new ComboViewer(featureCmb);
        cViewer.setContentProvider(new ArrayContentProvider());
        cViewer.setLabelProvider(new FeatureLabelProvider());

        cViewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                if (listenToChanges) {
                    ISelection selection = event.getSelection();
                    EObject input = getInput();
                    if (input != null
                            && selection instanceof IStructuredSelection
                            && !selection.isEmpty()) {
                        Object element =
                                ((IStructuredSelection) selection)
                                        .getFirstElement();
                        Object value = SetCommand.UNSET_VALUE;
                        if (element instanceof Feature) {
                            value = element;
                        }

                        getEditingDomain().getCommandStack().execute(SetCommand
                                .create(getEditingDomain(),
                                        input,
                                        getEReference(input),
                                        value));
                        // Update the tabs so that the attribute value tab can
                        // be shown/hidden
                        refreshTabs();
                    }
                }
            }
        });

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Handled by selection listener on the combo viewer
        return null;
    }

    @Override
    protected void doRefresh() {
        listenToChanges = false;
        try {
            EObject input = getInput();

            if ((input instanceof OrgUnit || input instanceof Position)
                    && cViewer != null && !cViewer.getControl().isDisposed()) {
                Feature feat = null;
                if (input instanceof OrgUnit) {
                    feat = ((OrgUnit) input).getFeature();
                } else {
                    feat = ((Position) input).getFeature();
                }

                if (noElements) {
                    featureCmb.setEnabled(false);
                    cViewer.getCCombo().select(0);
                } else {
                    featureCmb.setEnabled(true);
                    cViewer.setSelection(feat != null ? new StructuredSelection(
                            feat) : null);
                }
            }
        } finally {
            listenToChanges = true;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = resollveInput(toTest);
        /*
         * XPD-5300: Don't show for Dynamic OrgUnit
         */
        if (eo instanceof DynamicOrgUnit) {
            return false;
        }

        return eo instanceof OrgUnit || eo instanceof Position;
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        EObject input = getInput();
        EList<? extends Feature> viewerInput = null;

        if (input instanceof OrgUnit) {
            viewerInput = getOrgUnitFeatures((OrgUnit) input);
            updateTypeLabel(Messages.FeatureSection_unitType_label);
        } else if (input instanceof Position) {
            viewerInput = getPositionFeatures((Position) input);
            updateTypeLabel(Messages.FeatureSection_positionType_label);
        }

        if (viewerInput != null && !viewerInput.isEmpty()) {
            noElements = false;
            List<Object> objs = new ArrayList<Object>();
            objs.add(""); //$NON-NLS-1$
            objs.addAll(viewerInput);
            cViewer.setInput(objs);
        } else {
            // No features found so set message
            noElements = true;
            String msg = NO_ELEMENTS_AVAILABLE;
            if (input instanceof OrgUnit) {

                OrgUnit ou = (OrgUnit) input;

                if (ou.getIncomingHierachicalRelationship() == null) {
                    msg = NO_ELEMENTS_AVAILABLE_FOR_ORGUNIT;

                } else {
                    msg = NO_ELEMENTS_AVAILABLE_FOR_ORGUNIT_WITH_PARENT;
                }

            } else if (input instanceof Position) {
                msg = NO_ELEMENTS_AVAILABLE_FOR_POSITION;
            }

            cViewer.setInput(new String[] { msg });
        }
    }

    /**
     * Update the type label.
     * 
     * @param label
     */
    private void updateTypeLabel(String label) {
        if (label != null && typeLabel != null && !typeLabel.isDisposed()) {
            typeLabel.setText(label);
        }
    }

    /**
     * get the {@link OrgUnitFeature}s from the parent of this unit..
     * 
     * @param unit
     *            section input
     * @return org unit features or <code>null</code> if none available.
     */
    private EList<OrgUnitFeature> getOrgUnitFeatures(OrgUnit unit) {

        Organization org = (Organization) unit.eContainer();
        EList<OrgUnitFeature> features = new BasicEList<OrgUnitFeature>();

        OrganizationType organizationType = org.getOrganizationType();
        if (organizationType != null) {
            OrgUnit parentOrgUnit = unit.getParentOrgUnit();
            if (parentOrgUnit != null) {
                OrgUnitType parentType = (OrgUnitType) parentOrgUnit.getType();
                if (parentType != null) {
                    // sorting
                    ArrayList<OrgUnitFeature> orgUnitFeaturesList =
                            new ArrayList<OrgUnitFeature>(
                                    parentType.getOrgUnitFeatures());
                    Collections.sort(orgUnitFeaturesList,
                            new Comparator<OrgUnitFeature>() {
                                @Override
                                public int compare(OrgUnitFeature o1,
                                        OrgUnitFeature o2) {
                                    return o1.getDisplayName().compareTo(o2
                                            .getDisplayName());
                                }
                            });
                    features.addAll(orgUnitFeaturesList);
                }
            } else { // no parent
                features.addAll(organizationType.getOrgUnitFeatures());
                Set<OrgUnitType> orgUnitTypes = new HashSet<OrgUnitType>();
                // Collect all OrgUnitTypes
                for (OrgUnitFeature f : organizationType.getOrgUnitFeatures()) {
                    OrgUnitType type = f.getFeatureType();
                    if ((type != null) && !orgUnitTypes.contains(type)) {
                        orgUnitTypes.add(type);
                        addSubtypes(type, orgUnitTypes);
                    }
                }

                // sorting
                ArrayList<OrgUnitType> orgUnitTypesList =
                        new ArrayList<OrgUnitType>(orgUnitTypes);

                if (orgUnitTypesList.size() > 1) {
                    Collections.sort(orgUnitTypesList,
                            new Comparator<OrgUnitType>() {
                                @Override
                                public int compare(OrgUnitType o1,
                                        OrgUnitType o2) {
                                    return o1.getDisplayName().compareTo(o2
                                            .getDisplayName());
                                }

                            });
                }

                for (OrgUnitType ouType : orgUnitTypesList) {
                    // sorting
                    ArrayList<OrgUnitFeature> orgUnitFeaturesList =
                            new ArrayList<OrgUnitFeature>(
                                    ouType.getOrgUnitFeatures());
                    Collections.sort(orgUnitFeaturesList,
                            new Comparator<OrgUnitFeature>() {
                                @Override
                                public int compare(OrgUnitFeature o1,
                                        OrgUnitFeature o2) {
                                    return o1.getDisplayName().compareTo(o2
                                            .getDisplayName());
                                }
                            });
                    features.addAll(orgUnitFeaturesList);
                }
            }
        }

        return features;
    }

    private void addSubtypes(OrgUnitType orgUnitType,
            Set<OrgUnitType> orgUnitTypes) {
        if (orgUnitType != null && orgUnitTypes != null) {
            for (OrgUnitFeature f : orgUnitType.getOrgUnitFeatures()) {
                OrgUnitType t = f.getFeatureType();
                if ((t != null) && !orgUnitTypes.contains(t)) {
                    orgUnitTypes.add(t);
                    addSubtypes(t, orgUnitTypes);
                }
            }
        }
    }

    /**
     * Get the position features from the parent of this Position.
     * 
     * @param position
     *            section input
     * @return position features, or <code>null</code> if none available.
     */
    private EList<PositionFeature> getPositionFeatures(Position position) {
        EObject parent = position.eContainer();
        EList<PositionFeature> features = new BasicEList<PositionFeature>();

        if (parent != null) {
            ItemProviderAdapter provider = getItemProvider(parent);

            if (provider != null) {
                Collection<?> descriptors =
                        provider.getNewChildDescriptors(parent,
                                getEditingDomain(),
                                null);
                if (descriptors != null) {
                    for (Object descriptor : descriptors) {
                        if (descriptor instanceof CommandParameter) {
                            Object feature =
                                    ((CommandParameter) descriptor)
                                            .getFeature();
                            if (feature instanceof PositionFeature) {
                                features.add((PositionFeature) feature);
                            }
                        }
                    }
                }
            }
        }
        return features;
    }

    /**
     * Get the item provider for the given <code>EObject</code>.
     * 
     * @param eo
     * @return item provider or <code>null</code> if not set.
     */
    private ItemProviderAdapter getItemProvider(EObject eo) {
        if (eo != null) {
            IEditingDomainItemProvider provider =
                    (IEditingDomainItemProvider) XpdResourcesPlugin
                            .getDefault().getAdapterFactory()
                            .adapt(eo, IEditingDomainItemProvider.class);
            if (provider instanceof ItemProviderAdapter) {
                return (ItemProviderAdapter) provider;
            }
        }
        return null;
    }

    /**
     * Get the input model's {@link EReference} to set the feature.
     * 
     * @param input
     * @return <code>EReference</code> or <code>null</code> if the input is not
     *         recognised.
     */
    private EReference getEReference(EObject input) {
        EReference ref = null;

        if (input instanceof OrgUnit) {
            ref = OMPackage.eINSTANCE.getOrgUnit_Feature();
        } else if (input instanceof Position) {
            ref = OMPackage.eINSTANCE.getPosition_Feature();
        }

        return ref;
    }

    /**
     * Label provider for the feature selection combo viewer.
     * 
     * @author njpatel
     * 
     */
    private class FeatureLabelProvider extends LabelProvider {

        @Override
        public String getText(Object element) {
            String label = null;
            if (element instanceof String) {
                label = (String) element;
            } else if (element instanceof Feature) {
                Feature f = (Feature) element;
                EObject container = f.eContainer();
                label = ""; //$NON-NLS-1$

                if (container instanceof OrgElementType) {
                    label = ((OrgElementType) container).getDisplayName() + "."; //$NON-NLS-1$
                }
                label += f.getDisplayName();
            }
            return label != null ? label : ""; //$NON-NLS-1$
        }

    }
}
