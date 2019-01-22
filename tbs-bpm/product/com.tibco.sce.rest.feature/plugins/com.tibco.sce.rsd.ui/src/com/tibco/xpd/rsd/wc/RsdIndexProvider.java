/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.wc;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.rest.schema.ui.RestConstants;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.ui.components.RsdEditingUtil;
import com.tibco.xpd.rsd.util.RsdSwitch;

/**
 * Provides index for REST Service Descriptor working copies.
 * 
 * @author jarciuch
 * @since 19 Feb 2015
 */
public class RsdIndexProvider implements WorkingCopyIndexProvider {

    /**
     * RSD indexer ID.
     */
    public static final String INDEXER_ID = "com.tibco.xpd.rsd.ui.indexer.rsd"; //$NON-NLS-1$

    /**
     * Indexer property key for http method (GET, POST,...).
     */
    public static final String HTTP_METHOD = "httpMethod"; //$NON-NLS-1$

    /**
     * Indexer property key for resource name.
     */
    public static final String RESOURCE_NAME = "resourceName"; //$NON-NLS-1$

    /**
     * Indexer property key for service name.
     */
    public static final String SERVICE_NAME = "serviceName"; //$NON-NLS-1$

    /**
     * Indexer property key for method id.
     */
    public static final String METHOD_ID = "methodId"; //$NON-NLS-1$

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        if (isInSpecialFolder(wc)) {
            EObject root = wc.getRootElement();
            if (root instanceof Service) {
                return getIndexerItems((Service) root);
            }
        }
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAffectingEvent(PropertyChangeEvent event) {
        if (event != null
                && event.getNewValue() instanceof ResourceSetChangeEvent) {
            ResourceSetChangeEvent changeEvent =
                    (ResourceSetChangeEvent) event.getNewValue();
            List<?> notifications = changeEvent.getNotifications();
            for (Object n : notifications) {
                if (n instanceof Notification) {
                    Notification notification = (Notification) n;
                    int eventType = notification.getEventType();
                    Object feature = notification.getFeature();

                    Object container = null;
                    if (feature instanceof EReference) {
                        container = ((EReference) feature).getContainerClass();
                    }
                    Object notifier = notification.getNotifier();
                    Object newValue = notification.getNewValue();
                    boolean isRelevant = false;
                    boolean isSet = false;

                    if (feature instanceof EAttribute) {
                        EAttribute att = (EAttribute) feature;
                        isRelevant =
                                att == RsdPackage.Literals.NAMED_ELEMENT__NAME
                                        || att == RsdPackage.Literals.METHOD__HTTP_METHOD;
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
                        if (isInterestingObject(container)) {
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

                    if (notifier instanceof Service) {
                        if (isSet && isRelevant) {
                            return true;
                        }
                    } else if (notifier instanceof com.tibco.xpd.rsd.Resource) {
                        if (isSet && isRelevant) {
                            return true;
                        }
                    } else if (notifier instanceof Method) {
                        if (isSet && isRelevant) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * If the object is interesting for RSD indexer.
     * 
     * @param object
     * @return
     */
    private boolean isInterestingObject(Object object) {
        if (object == Service.class) {
            return true;
        }
        if (object instanceof Service
                || object instanceof com.tibco.xpd.rsd.Resource
                || object instanceof Method) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the working copy is in the rest special folder.
     */
    private boolean isInSpecialFolder(WorkingCopy wc) {
        IResource eclipseResource = wc.getEclipseResources().get(0);
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault()
                        .getProjectConfig(eclipseResource.getProject());

        if (projectConfig != null) {
            SpecialFolders specialFolders = projectConfig.getSpecialFolders();
            SpecialFolder sf =
                    specialFolders.getFolderContainer(eclipseResource);
            return sf != null
                    && RestConstants.REST_SPECIAL_FOLDER_KIND.equals(sf
                            .getKind());
        }
        return false;
    }

    /**
     * Returns the indexer items for a given RSD root element (service)
     * 
     * @param service
     *            the RSD service.
     * @return a collection of indexer items for the service.
     */
    private Collection<IndexerItem> getIndexerItems(final Service service) {
        TreeIterator<Object> serviceContent =
                EcoreUtil.getAllContents(service, false);
        RsdSwitch<IndexerItem> rsdIndexSwitch = new RsdSwitch<IndexerItem>() {
            @Override
            public IndexerItem caseMethod(Method method) {
                String name = method.getName();
                String type = method.eClass().getInstanceTypeName();
                String uri = getURI(method);
                com.tibco.xpd.rsd.Resource resource =
                        RsdEditingUtil.getParentOfType(method,
                                com.tibco.xpd.rsd.Resource.class);

                Map<String, String> additionalAttributes = new HashMap<>();
                additionalAttributes.put(HTTP_METHOD, method.getHttpMethod()
                        .getLiteral());
                additionalAttributes.put(RESOURCE_NAME,
                        resource != null ? resource.getName() : null);
                additionalAttributes.put(SERVICE_NAME, service.getName());
                additionalAttributes.put(METHOD_ID, method.getId());

                return new IndexerItemImpl(name, type, uri,
                        additionalAttributes);
            }
        };
        List<IndexerItem> items = new ArrayList<>();
        while (serviceContent.hasNext()) {
            EObject eo = (EObject) serviceContent.next();
            IndexerItem item = rsdIndexSwitch.doSwitch(eo);
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    /**
     * Returns the URI (with fragment part) for a given EObject.
     */
    private String getURI(EObject oEobject) {
        Resource r = oEobject.eResource();
        URI uri = r.getURI().appendFragment(r.getURIFragment(oEobject));
        return uri.toString();
    }
}
