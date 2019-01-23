/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin implements IRegistryManager,
        IRegistryListener {
    /**
     * Before 2.1 the registries were persisted in a file with this name.
     * Because they are old they require a migration.
     */
    private static final String OLD_REGISTRIES_LOCATION = ".registries"; //$NON-NLS-1$

    /** The filename to persist registries. */
    private static final String REGISTRIES = ".registries21"; //$NON-NLS-1$

    /** The plug-in ID. */
    public static final String PLUGIN_ID = "com.tibco.xpd.registry"; //$NON-NLS-1$

    /** The shared instance. */
    private static Activator plugin;

    /** The collection of currently defined registries. */
    private final Collection<Registry> registries;

    /** The folder in which registry definitions are stored. */
    private File registryFolder;

    /** A set of listeners for changes to the registry list. */
    private final HashSet<IRegistryManagerListener> registryListeners;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor.
     */
    public Activator() {
        plugin = this;
        registries = new ArrayList<Registry>();
        registryListeners = new HashSet<IRegistryManagerListener>();
    }

    /**
     * @param context
     *            the bundle context.
     * @throws Exception
     *             if there was a problem starting the plugin.
     * @see org.eclipse.core.runtime.Plugins#start(
     *      org.osgi.framework.BundleContext)
     */
    @Override
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        loadRegistries();
    }

    /**
     * @param context
     *            the bundle context.
     * @throws Exception
     *             if there was a problem starting the plugin.
     * @see org.eclipse.core.runtime.Plugins#start(
     *      org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(final BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * @return The registry manage.
     */
    public static IRegistryManager getRegistryManager() {
        return plugin;
    }

    /**
     * Adds a new registry definition.
     * 
     * @param registry
     *            The new registry.
     * @see com.tibco.xpd.registry.IRegistryManager#addRegistry(
     *      com.tibco.xpd.registry.Registry)
     */
    public void addRegistry(final Registry registry) {
        registries.add(registry);
        registry.addRegistryListener(this);
        fireRegistryAdded(registry);
        saveRegisters();
    }

    /**
     * @return A collection of available registries.
     * @see com.tibco.xpd.registry.IRegistryManager#getRegistries()
     */
    public Collection<Registry> getRegistries() {
        return Collections.unmodifiableCollection(registries);
    }

    /**
     * Removes a registry definition and deletes the definition file.
     * 
     * @param registry
     *            The registry to remove.
     * @see com.tibco.xpd.registry.IRegistryManager#removeRegistry(
     *      com.tibco.xpd.registry.Registry)
     */
    public void removeRegistry(final Registry registry) {
        registry.removeRegistryListener(this);
        File file = new File(registryFolder, registry.getName());
        file.delete();
        registries.remove(registry);
        fireRegistryRemoved(registry);
        saveRegisters();
    }

    /**
     * Loads the registry definition files.
     */
    private void loadRegistries() {
        File newRegistryFile = getStateLocation().append(REGISTRIES).toFile();
        if (newRegistryFile.exists() && newRegistryFile.isFile()) {
            try {
                FileInputStream input = new FileInputStream(newRegistryFile);
                ObjectInputStream decoder = new ObjectInputStream(input);
                Object object = decoder.readObject();
                decoder.close();
                if (object instanceof Registry[]) {
                    for (Registry registry : (Registry[]) object) {
                        registries.add(registry);
                        registry.addRegistryListener(this);
                    }
                }
                fireRegistryChanged();
            } catch (Exception exception) {
                getLogger().error(exception);
            }
            return;
        }

        // old registries loading
        registryFolder = getStateLocation().append(OLD_REGISTRIES_LOCATION)
                .toFile();
        if (registryFolder.exists()) {

            File[] registryFiles = registryFolder.listFiles();
            if (registryFiles != null) {
                for (int i = 0; i < registryFiles.length; i++) {
                    try {
                        FileInputStream input = new FileInputStream(
                                registryFiles[i]);
                        ObjectInputStream decoder = new ObjectInputStream(input);
                        Object object = decoder.readObject();
                        decoder.close();
                        if (object instanceof Registry) {
                            Registry registry = (Registry) object;
                            registries.add(registry);
                            registry.addRegistryListener(this);
                        }
                    } catch (Exception exception) {
                        getLogger().error(exception);
                    }
                }
                fireRegistryChanged();
            }
        }

    }

    /**
     * Saves the registry definition file.
     * 
     * @param registry
     *            The registry definition to save.
     * @return true if the save was successful, otherwise false.
     * @deprecated use {@see Activator#saveRegisters()} instead.
     */
    @SuppressWarnings("unused")
    @Deprecated
    private boolean saveRegistry(final Registry registry) {
        boolean ok = false;
        if (registry != null && registry.getName() != null) {
            try {
                File file = new File(registryFolder, registry.getName());
                FileOutputStream output = new FileOutputStream(file);
                ObjectOutputStream encoder = new ObjectOutputStream(output);
                encoder.writeObject(registry);
                encoder.flush();
                encoder.close();
                ok = true;
            } catch (Exception exception) {
                getLogger().error(exception);
            }
        }
        return ok;
    }

    /**
     * Adds a new registry manager listener.
     * 
     * @param listener
     *            The new registry manager listener.
     * @see com.tibco.xpd.registry.IRegistryManager#addRegistryManagerListener(
     *      com.tibco.xpd.registry.IRegistryManagerListener)
     */
    public void addRegistryManagerListener(
            final IRegistryManagerListener listener) {
        registryListeners.add(listener);
    }

    /**
     * Removes a registry manager listener.
     * 
     * @param listener
     *            The listener to remove.
     * @see com.tibco.xpd.registry.IRegistryManager#removeReportListener(
     *      com.tibco.xpd.registry.IRegistryManagerListener)
     */
    public void removeReportListener(final IRegistryManagerListener listener) {
        registryListeners.remove(listener);
    }

    /**
     * Notifies registry manager listeners of changes to the registry list.
     */
    private void fireRegistryChanged() {
        for (IRegistryManagerListener listener : registryListeners) {
            listener.registryChanged();
        }
    }

    /**
     * Notifies registry manager listeners of changes to the registry list.
     * 
     * @param registry
     *            The registry added.
     */
    private void fireRegistryAdded(Registry registry) {
        for (IRegistryManagerListener listener : registryListeners) {
            listener.registryAdded(registry);
        }
    }

    /**
     * Notifies registry manager listeners of changes to the registry list.
     * 
     * @param registry
     *            The registry removed.
     */
    private void fireRegistryRemoved(Registry registry) {
        for (IRegistryManagerListener listener : registryListeners) {
            listener.registryRemoved(registry);
        }
    }

    /**
     * @param registry
     *            The registry to which the search was added.
     * @param search
     *            The search.
     * @see com.tibco.xpd.registry.IRegistryListener#searchAdded(
     *      com.tibco.xpd.registry.Registry, com.tibco.xpd.registry.Search)
     */
    public void searchAdded(Registry registry, Search search) {
        saveRegisters();
    }

    /**
     * @param registry
     *            The registry from which the search was removed.
     * @param search
     *            The search.
     * @see com.tibco.xpd.registry.IRegistryListener#searchRemoved(
     *      com.tibco.xpd.registry.Registry, com.tibco.xpd.registry.Search)
     */
    public void searchRemoved(Registry registry, Search search) {
        saveRegisters();
    }

    public void updateRegistry(Registry registry) {
        fireRegistryChanged();
    }

    /**
     * Returns logger for the plug-in.
     * 
     * @return the plug-in logger.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Persist the the current array of Registry objects.
     */
    public void saveRegisters() {
        File newRegistryFile = getStateLocation().append(REGISTRIES).toFile();
        try {
            FileOutputStream output = new FileOutputStream(newRegistryFile);
            ObjectOutputStream encoder = new ObjectOutputStream(output);
            encoder.writeObject(registries.toArray(new Registry[registries
                    .size()]));
            encoder.flush();
            encoder.close();
        } catch (IOException e) {
            getLogger().error(e, "Cannot persist registers."); //$NON-NLS-1$
        }
    }
}
