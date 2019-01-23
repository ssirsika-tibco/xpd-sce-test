/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.selector;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.uddi4j.UDDIException;
import org.uddi4j.client.UDDIProxy;
import org.uddi4j.datatype.Name;
import org.uddi4j.response.BusinessInfo;
import org.uddi4j.response.BusinessInfos;
import org.uddi4j.response.BusinessList;
import org.uddi4j.response.ServiceInfos;
import org.uddi4j.response.ServiceList;
import org.uddi4j.transport.TransportException;

import com.tibco.xpd.registry.IRegistryListener;
import com.tibco.xpd.registry.IRegistryManager;
import com.tibco.xpd.registry.IRegistryManagerListener;
import com.tibco.xpd.registry.Registry;
import com.tibco.xpd.registry.Search;
import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.resources.logger.Logger;

/**
 * @author nwilson
 */
public class Uddi4jContentProvider implements ITreeContentProvider,
        IRegistryManagerListener, IRegistryListener {

    /** The registry to provide content for. */
    private final Collection<Registry> registries;

    /** The viewer to provide content for. */
    private TreeViewer viewer;

    /**
     * Default constructor provides content for all known registries.
     */
    public Uddi4jContentProvider() {
        IRegistryManager registryManager = com.tibco.xpd.registry.Activator
                .getRegistryManager();
        registries = registryManager.getRegistries();
        registryManager.addRegistryManagerListener(this);
        for (Registry registry : registries) {
            registry.addRegistryListener(this);
        }
    }

    /**
     * Callback to indicate a change of input object.
     * 
     * @param viewer
     *            The viewer generating the callback
     * @param oldInput
     *            The old input object.
     * @param newInput
     *            The new input object.
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(
     *      org.eclipse.jface.viewers.Viewer, java.lang.Object,
     *      java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (viewer instanceof TreeViewer) {
            this.viewer = (TreeViewer) viewer;
        } else {
            viewer = null;
        }
    }

    /**
     * @param inputElement
     *            n/a.
     * @return An array of available registries.
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
     *      java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        return registries.toArray();
    }

    /**
     * @param element
     *            The element to check.
     * @return true if the element has children, otherwise false.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(
     *      java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof Registry) {
            hasChildren = ((Registry) element).getSearches().size() > 0;
        } else if (element instanceof RegistrySearch) {
            hasChildren = true;
            RegistrySearch registrySearch = ((RegistrySearch) element);
            registrySearch.setException(null);
            registrySearch.setChildren(null);
        } else if (element instanceof BusinessInfo) {
            ServiceInfos infos = ((BusinessInfo) element).getServiceInfos();
            if (infos != null && infos.size() > 0) {
                hasChildren = true;
            }
        }
        return hasChildren;
    }

    /**
     * @param element
     *            The element to get children for.
     * @return An array of child objects.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(
     *      java.lang.Object)
     */
    public Object[] getChildren(Object element) {
        Object[] children = new Object[0];
        if (element instanceof Registry) {
            Registry registry = (Registry) element;
            List<Search> searches = registry.getSearches();
            List<RegistrySearch> registrySearches = new ArrayList<RegistrySearch>();
            for (Search search : searches) {
                RegistrySearch registrySearch = new RegistrySearch(registry,
                        search, this);
                registrySearches.add(registrySearch);

            }
            children = registrySearches.toArray();
        } else if (element instanceof RegistrySearch) {
            RegistrySearch registrySearch = (RegistrySearch) element;
            Registry registry = registrySearch.getRegistry();
            Search search = registrySearch.getSearch();
            Vector<Name> names = new Vector<Name>();
            Logger logger = Activator.getDefault().getLogger();
            try {
                Properties mapped = new Properties();
                mapped.setProperty(UDDIProxy.TRANSPORT_CLASSNAME_PROPERTY,
                        "org.uddi4j.transport.ApacheAxisTransport"); //$NON-NLS-1$
                mapped.setProperty(UDDIProxy.INQUIRY_URL_PROPERTY, registry
                        .getQueryManagerUrl());
                // mapped.setProperty(UDDIProxy.PUBLISH_URL_PROPERTY, registry
                // .getLifeCycleManagerUrl());
                UDDIProxy proxy = new UDDIProxy(mapped);
                if (search.getType() == Search.FIND_BUSINESS) {
                    for (String criteria : search.getNameCriteria()) {
                        names.add(new Name(criteria));
                    }
                    BusinessList list = proxy.find_business(names, null, null,
                            null, null, null, 0);
                    BusinessInfos infos = list.getBusinessInfos();
                    Vector<?> vector = infos.getBusinessInfoVector();
                    children = vector.toArray();
                } else if (search.getType() == Search.FIND_SERVICE) {
                    for (String criteria : search.getNameCriteria()) {
                        names.add(new Name(criteria));
                    }
                    ServiceList list = proxy.find_service(null, names, null,
                            null, null, 0);
                    ServiceInfos infos = list.getServiceInfos();
                    if (infos != null) {
                        Vector<?> vector = infos.getServiceInfoVector();
                        children = vector.toArray();
                    }
                }
                registrySearch.setChildren(children);
                registrySearch.setException(null);
            } catch (UDDIException e) {
                registrySearch.setException(e);
                logger.error(e);
            } catch (TransportException e) {
                registrySearch.setException(e);
                logger.error(e);
            } catch (MalformedURLException e) {
                registrySearch.setException(e);
                logger.error(e);
            }
            registrySearch.update(true);
        } else if (element instanceof BusinessInfo) {
            BusinessInfo info = (BusinessInfo) element;
            ServiceInfos infos = info.getServiceInfos();
            if (infos != null) {
                Vector<?> vector = infos.getServiceInfoVector();
                children = vector.toArray();
            }
        }
        return children;
    }

    /**
     * @param element
     *            n/a.
     * @return null.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(
     *      java.lang.Object)
     */
    public Object getParent(Object element) {
        return null;
    }

    /**
     * Cleans up the content provider.
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
        IRegistryManager registryManager = com.tibco.xpd.registry.Activator
                .getRegistryManager();
        registryManager.removeReportListener(this);
        for (Registry registry : registries) {
            registry.removeRegistryListener(this);
        }
    }

    /**
     * @param registry
     *            The registry to which the search was added.
     * @param search
     *            The search added.
     * @see com.tibco.xpd.registry.IRegistryListener#searchAdded(
     *      com.tibco.xpd.registry.Registry, com.tibco.xpd.registry.Search)
     */
    public void searchAdded(Registry registry, Search search) {
        if (viewer != null) {
            TreePath path = new TreePath(new Object[] { registry });
            viewer.add(path, new RegistrySearch(registry, search, this));
        }
    }

    /**
     * @param registry
     *            The registry from which the search was removed.
     * @param search
     *            The search removed.
     * @see com.tibco.xpd.registry.IRegistryListener#searchRemoved(
     *      com.tibco.xpd.registry.Registry, com.tibco.xpd.registry.Search)
     */
    public void searchRemoved(Registry registry, Search search) {
        if (viewer != null) {
            TreePath path = new TreePath(new Object[] { registry,
                    new RegistrySearch(registry, search, this) });
            viewer.remove(path);
        }
    }

    /**
     * @param registry
     *            The registry added.
     * @see com.tibco.xpd.registry.IRegistryManagerListener#registryAdded(
     *      com.tibco.xpd.registry.Registry)
     */
    public void registryAdded(Registry registry) {
        if (viewer != null) {
            Object input = viewer.getInput();
            if (input != null) {
                viewer.add(input, registry);
                registry.addRegistryListener(this);
            }
        }
    }

    /**
     * @see com.tibco.xpd.registry.IRegistryManagerListener#registryChanged()
     */
    public void registryChanged() {
        if (viewer != null) {
            viewer.refresh();
        }
    }

    /**
     * @param registry
     *            registry The registry removed.
     * @see com.tibco.xpd.registry.IRegistryManagerListener#registryRemoved(
     *      com.tibco.xpd.registry.Registry)
     */
    public void registryRemoved(Registry registry) {
        registry.removeRegistryListener(this);
        if (viewer != null) {
            viewer.remove(registry);
        }
    }

    void updateRegistrySearch(RegistrySearch registrySearch, boolean labelsOnly) {
        if (viewer != null) {
            if (labelsOnly) {
                viewer.update(registrySearch, null);
            } else {
                viewer.refresh(registrySearch, true);
            }
        }
    }

}
