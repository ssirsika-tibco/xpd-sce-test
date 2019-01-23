/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;

import com.tibco.xpd.ui.properties.advanced.AdvancedTabRefresherSection;

/**
 * Extension to the {@link AdvancedTabRefresherSection} that forces the advanced
 * section for the BOM to be refreshed when a stereotype application is updated.
 * This will ensure that changes to the Restricted types, for example, are
 * updated in the advanced section.
 * 
 * @author njpatel
 * 
 */
public class BOMAdvancedTabRefresherSection extends AdvancedTabRefresherSection {

    public BOMAdvancedTabRefresherSection() {
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean doRefresh = super.shouldRefresh(notifications);

        /*
         * Force the refresh of the advanced section when a stereotype
         * application is updated.
         */
        if (!doRefresh) {
            EObject input = getInput();
            if (input != null) {
                for (Notification notification : notifications) {
                    if (!notification.isTouch()) {
                        Object notifier = notification.getNotifier();
                        if (notifier instanceof DynamicEObjectImpl
                                && ((DynamicEObjectImpl) notifier).eResource() == input
                                        .eResource()) {
                            doRefresh = true;
                            break;
                        }
                    }
                }
            }
        }

        return doRefresh;
    }

}
