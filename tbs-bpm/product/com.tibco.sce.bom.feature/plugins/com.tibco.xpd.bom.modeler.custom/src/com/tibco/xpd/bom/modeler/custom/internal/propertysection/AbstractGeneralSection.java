/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.uml2.uml.Association;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMEditPartUtils;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Abstract section used by the sections of the BOM properties.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractGeneralSection extends
        AbstractTransactionalSection implements IFilter {

    public static final String UNRESOLVED_REFERENCE =
            Messages.AbstractGeneralSection_unresolvedReference_label;

    /**
     * This event is created to be used to "trick" the property view to refresh
     * when a stereotype change is detected in the model so that the view
     * header's title and image can be updated (for first-class profile
     * support).
     */
    private static final LabelProviderChangedEvent ev =
            new LabelProviderChangedEvent(new LabelProvider());

    /**
     * Abstract section used by the general tab of the OM properties.
     */
    public AbstractGeneralSection() {
        setMinimumHeight(SWT.DEFAULT);
        setShouldUseExtraSpace(false);
    }

    /**
     * Force the update of the property view's header. This will recalculate the
     * name and icon to show in the header.
     */
    protected void refreshHeader() {
        TabbedPropertySheetPage page = getPropertySheetPage();
        if (page != null && !page.getControl().isDisposed()) {
            page.labelProviderChanged(ev);
        }
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof IAdaptable) {
            EObject eo =
                    (EObject) ((IAdaptable) object).getAdapter(EObject.class);

            // If association source or target role then select the Property as
            // the input
            if (object instanceof AssociationSourceLabelEditPart
                    || object instanceof AssociationCardinalityAtSourceLabelEditPart) {

                // Association source role
                if (eo instanceof Association
                        && ((Association) eo).getMemberEnds().get(0) != null) {
                    eo = ((Association) eo).getMemberEnds().get(0);
                }
            } else if (object instanceof AssociationTargetLabelEditPart
                    || object instanceof AssociationCardinalityAtTargetLabelEditPart) {

                // association target role
                if (eo instanceof Association
                        && ((Association) eo).getMemberEnds().get(1) != null) {
                    eo = ((Association) eo).getMemberEnds().get(1);
                }
            }
            return eo;
        }
        return super.resollveInput(object);
    }

    /**
     * Set the layout of the control. This will set the {@link GridData} with
     * horizontal fill and top vertical alignment. It will also set width hint
     * to 30.
     * 
     * @param ctl
     */
    protected void setLayoutData(Control ctl) {
        if (ctl != null) {
            GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
            data.widthHint = 30;
            ctl.setLayoutData(data);
        }
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(2, false));
        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        EObject eo = resollveInput(toTest);
        return eo != null && shouldDisplay(eo);
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {

        boolean refresh = super.shouldRefresh(notifications);

        if (refresh) {
            return refresh;
        }

        // Also need to refresh if a stereotype has changed
        if (notifications != null) {
            for (Notification notification : notifications) {
                if (!(notification.isTouch())) {
                    if (BOMEditPartUtils.isStereotypeBeingSet(notification)) {
                        refresh = true;
                        break;
                    }
                }
            }
        }

        return refresh;
    }

    /**
     * Check if this section should display for the given object. This is called
     * by {@link #select(Object) IFilter.select}.
     * 
     * @param eo
     * @return <code>true</code> if this section should be displayed,
     *         <code>false</code> otherwise.
     */
    protected abstract boolean shouldDisplay(EObject eo);

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#createLabel(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit, java.lang.String)
     * 
     * @param parent
     * @param toolkit
     * @param text
     * @return
     */
    // TODO: THIS SUPPORT IS CURRENTLY DISABLED XPD-6593
    // TO ENABLE RENAME _createLabel TO createLabel
    protected Label _createLabel(Composite parent, XpdFormToolkit toolkit,
            String text) {
        Label createLabel = super.createLabel(parent, toolkit, text);
        // For the general section we need to increase the width used
        // for the label
        Object layoutData = createLabel.getLayoutData();
        if (layoutData instanceof GridData) {
            GridData gridData = (GridData) layoutData;
            gridData.widthHint = 120;
            createLabel.setLayoutData(layoutData);
        }

        return createLabel;
    }
}
