/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.indexing;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.BaseOrgModel;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.QualifiedOrgElement;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.resources.ui.types.OMTypesFactory;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.ui.types.TypeInfo;

/**
 * This class provides information about a working copy. This information is
 * then used by the IndexerService to store it in the resource database. All
 * ResourceIndexerProvider implementations mus extends the abstract class
 * AbstractWorkingCopyIndexProvider and implement the interface
 * WorkingCopyIndexProvider.
 * 
 * @author rassisi
 * 
 */
public class OMResourceIndexProvider implements WorkingCopyIndexProvider {

    public static final String INDEXER_ID =
            "com.tibco.xpd.om.resources.indexing.omIndexer"; //$NON-NLS-1$

    public final static String GROUP_ID = "group_id"; //$NON-NLS-1$

    public final static String ID = "id"; //$NON-NLS-1$

    public final static String DISPLAY_NAME = "display_name"; //$NON-NLS-1$

    /**
     * This method parses all EObjects in the working copy and returns a
     * collection of descriptors (IndexerItem) about them.
     * 
     * @param wc
     * @param resource
     */
    @Override
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        Collection<IndexerItem> items = new ArrayList<IndexerItem>();
        EObject root = wc.getRootElement();
        if (root != null) {

            // add the actual root org model to indexer so we can validate
            // against its name across packages later on
            if (root instanceof BaseOrgModel) {
                addItem(items, (BaseOrgModel) root);
            }

            TreeIterator<EObject> iterator = root.eAllContents();
            while (iterator.hasNext()) {
                EObject element = iterator.next();
                if (element instanceof NamedElement) {
                    addItem(items, (NamedElement) element);
                }
            }
        }
        return items;
    }

    /**
     * @param items
     * @param element
     * @param additionalAttributes
     */
    void addItem(Collection<IndexerItem> items, NamedElement element) {
        String name = OMWorkingCopy.getQualifiedName(element);
        String uri = OMUtil.getURI(element);
        TypeInfo typeInfo = OMTypesFactory.getType(element);

        if (typeInfo != null) {
            String groupId = typeInfo.getGroupId();
            String id = OMUtil.getID(element);
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes.put(GROUP_ID, groupId);
            additionalAttributes.put(ID, id);
            additionalAttributes.put(DISPLAY_NAME, element.getDisplayName());
            items.add(OMTypesFactory.createIndexerItem(typeInfo,
                    name,
                    uri,
                    additionalAttributes));
        }
    }

    /**
     * @param resource
     * @return
     */
    protected WorkingCopy getWorkingCopy(IResource resource) {
        return XpdResourcesPlugin.getDefault().getWorkingCopy(resource);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#isAffectingEvent
     * (java.beans.PropertyChangeEvent)
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean isAffectingEvent(PropertyChangeEvent event) {
        if (event != null
                && event.getNewValue() instanceof ResourceSetChangeEvent) {
            ResourceSetChangeEvent changeEvent =
                    (ResourceSetChangeEvent) event.getNewValue();
            List notifications = changeEvent.getNotifications();
            for (Object object : notifications) {
                if (object instanceof Notification) {
                    Notification notification = (Notification) object;
                    if (notification.isTouch()) {
                        continue;
                    }
                    int eventType = notification.getEventType();
                    Object feature = notification.getFeature();

                    Object container = null;
                    if (feature instanceof EReference) {
                        container = ((EReference) feature).getContainerClass();
                    }
                    Object notifier = notification.getNotifier();
                    Object newValue = notification.getNewValue();
                    Object oldValue = notification.getOldValue();
                    boolean isName = false;
                    boolean isSet = false;

                    if (feature instanceof EAttribute) {
                        EAttribute att = (EAttribute) feature;
                        isName =
                                (att == OMPackage.eINSTANCE
                                        .getNamedElement_Name() || att == OMPackage.eINSTANCE
                                        .getNamedElement_DisplayName());
                    }

                    switch (eventType) {
                    case Notification.ADD:
                        if (isInterestingObject(newValue)) {
                            return true;
                        }
                        break;
                    case Notification.ADD_MANY:
                        if (isInterestingObject(newValue)) {
                            return true;
                        }
                        break;
                    case Notification.MOVE:
                        if (isInterestingObject(newValue)) {
                            return true;
                        }
                        break;
                    case Notification.REMOVE:
                        if (isInterestingObject(oldValue)) {
                            return true;
                        }
                        break;
                    case Notification.SET:
                        isSet = true;
                        break;
                    case Notification.UNSET:
                        isSet = true;
                        break;
                    }

                    if (notifier instanceof OrgTypedElement
                            || notifier instanceof OrgElementType
                            || notifier instanceof QualifiedOrgElement
                            || notifier instanceof Attribute
                            || notifier instanceof NamedElement) {
                        if (isSet && isName) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return
     */
    boolean isInterestingObject(Object object) {
        if (object instanceof OrgTypedElement) {
            return true;
        }
        if (object instanceof OrgElementType) {
            return true;
        }
        if (object instanceof QualifiedOrgElement) {
            return true;
        }
        if (object instanceof Attribute) {
            return true;
        }
        if (object instanceof NamedElement) {
            return true;
        }

        return false;
    }

}
