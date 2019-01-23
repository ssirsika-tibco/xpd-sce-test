/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author nwilson
 */
public class MapperActivator extends AbstractUIPlugin {

    /** The plug-in ID. */
    public static final String PLUGIN_ID = "com.tibco.xpd.mapper"; //$NON-NLS-1$

    /** The shared instance. */
    private static MapperActivator plugin;

    /** The image cache. */
    private ImageCache imageCache;

    /** A map of ids to Mapper components. */
    private Map<String, Mapper> mappers;

    /** A map of ids to IMappingListener components. */
    private Map<String, Set<MapperItemListener>> listeners;

    /**
     * The constructor.
     */
    public MapperActivator() {
        plugin = this;
        mappers = new HashMap<String, Mapper>();
        listeners = new HashMap<String, Set<MapperItemListener>>();
    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there is a problem.
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        imageCache = new ImageCache();
    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there is a problem.
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        imageCache.dispose();
        plugin = null;
        super.stop(context);
    }

    /**
     * Registers a ScriptableMapper component so that it can generate events for
     * other components to listen to.
     * 
     * @param id
     *            The mapper id.
     * @param mapper
     *            The ScriptableMapper to register.
     */
    public void registerMapper(String id, Mapper mapper) {
        mappers.put(id, mapper);
    }

    /**
     * Unregisters a ScriptableMapper component. It will not generate any more
     * events.
     * 
     * @param id
     *            The mapper id.
     */
    public void unregisterMapper(String id) {
        mappers.remove(id);
    }

    /**
     * Adds a listener to receive events from the ScriptableMapper component
     * with the given id.
     * 
     * @param id
     *            The mapper id.
     * @param listener
     *            The listener to add.
     */
    public void addMappingListener(String id, MapperItemListener listener) {
        Set<MapperItemListener> listenerSet = listeners.get(id);
        if (listenerSet == null) {
            listenerSet = new HashSet<MapperItemListener>();
            listeners.put(id, listenerSet);
        }
        listenerSet.add(listener);
    }

    /**
     * Removes a listener. It will not receive any more events from the
     * ScriptableMapper component with the given id.
     * 
     * @param id
     *            The mapper id.
     * @param listener
     *            The listener to remove.
     */
    public void removeMappingListener(String id, MapperItemListener listener) {
        Set<MapperItemListener> listenerSet = listeners.get(id);
        if (listenerSet != null) {
            listenerSet.remove(listener);
        }
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance.
     */
    public static MapperActivator getDefault() {
        return plugin;
    }

    /**
     * @return The image cache.
     */
    public ImageCache getImageCache() {
        return imageCache;
    }

    /**
     * @param id
     *            The id of the mapper.
     * @param src
     *            the object that generated the event.
     * @param items
     *            The selected items.
     */
    public void fireSourceSelectionChanged(String id, Object src, Object[] items) {
        Set<MapperItemListener> listenerSet = listeners.get(id);
        if (listenerSet != null) {
            for (MapperItemListener listener : listenerSet) {
                listener.sourceItemsSelected(src, items);
            }
        }
    }

}
