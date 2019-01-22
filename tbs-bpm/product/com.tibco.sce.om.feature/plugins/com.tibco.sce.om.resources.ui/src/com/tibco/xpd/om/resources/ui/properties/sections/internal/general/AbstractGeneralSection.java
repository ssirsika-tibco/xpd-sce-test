/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Abstract section used by the sections of the OM properties.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractGeneralSection extends
        AbstractTransactionalSection {

    /**
     * Abstract section used by the general tab of the OM properties.
     */
    public AbstractGeneralSection() {
        setMinimumHeight(SWT.DEFAULT);
        setShouldUseExtraSpace(false);
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof EditPart) {
            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        }

        return super.resollveInput(object);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#isInputRemoved(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean isInputRemoved(List<Notification> notifications) {

        EObject input = getInput();

        if (input != null) {

            for (Notification notification : notifications) {

                if (!notification.isTouch()) {

                    boolean isContainment = false;

                    if (notification.getFeature() instanceof EReference) {

                        EReference reference =
                                (EReference) notification.getFeature();
                        isContainment = reference.isContainment();
                    }
                    /*
                     * XPD-6821: In case of two way relationship (for instance
                     * Capability has reference to Category and Category has
                     * reference to Capability and similarly Privilege vs
                     * Category) it was removing the input completely from the
                     * properties section. We need to destroy the input only
                     * when the containment reference is removed, not on any
                     * reference.
                     * 
                     * PLEASE NOTE THAT this could have been done in the
                     * AbstractTransactionalSection base class, but to keep the
                     * effect minimal, doing it here!
                     */
                    if (notification.getEventType() == Notification.REMOVE
                            && (input.equals(notification.getOldValue()) && isContainment)) {

                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Set the layout of the control. This will set the {@link GridData} with
     * horizontal fill and top vertical alighment. It will also set width hint
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
}
