/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.destinations.DestinationsActivator;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.internal.Messages;

/**
 * Utility class for serialising and deserialising the global destination
 * environment preferences to and from a single string.
 * 
 * @author nwilson
 * 
 */
public class DestinationPreferences {

    private static final String DEFAULT_NAME =
            Messages.DestinationPreferences_NewDestinationDefault;

    private List<DestinationSetting> destinations;

    private List<DestinationPreferencesListener> listeners;

    public static final String PREFERENCE =
            com.tibco.xpd.resources.ui.internal.destination.GlobalDestinationUtil.GLOBAL_DESTINATION_PREFERENCE_ID;

    public DestinationPreferences() {
        destinations = getDefaultSettings();
        listeners = new ArrayList<DestinationPreferencesListener>();
    }

    public void parse(String preference) {
        destinations = DestinationPreferencesCodec.decode(preference);
        DestinationPreferencesEvent event =
                new DestinationPreferencesEvent(
                        DestinationPreferencesEvent.REFRESHED);
        fireChanged(event);
    }

    @Override
    public String toString() {
        return DestinationPreferencesCodec.encode(destinations);
    }

    public List<String> getGlobalDestinations() {
        List<String> names = new ArrayList<String>();
        for (DestinationSetting destination : destinations) {
            names.add(destination.getName());
        }
        return names;
    }

    public void renameGlobalDestination(String oldName, String newName) {
        DestinationSetting setting = getDestinationSetting(oldName);
        if (setting != null && newName != null) {
            newName = newName.trim();
            if (newName.length() != 0) {
                DestinationSetting duplicate = getDestinationSetting(newName);
                if (duplicate == null) {
                    setting.setName(newName);
                    DestinationPreferencesEvent event =
                            new DestinationPreferencesEvent(
                                    DestinationPreferencesEvent.CHANGED);
                    fireChanged(event);
                }
            }
        }
    }

    private DestinationSetting getDestinationSetting(String name) {
        DestinationSetting setting = null;
        if (name != null) {
            for (DestinationSetting destination : destinations) {
                if (name.equals(destination.getName())) {
                    setting = destination;
                    break;
                }
            }
        }
        return setting;
    }

    /**
     * Adds a new global destination with default settings.
     */
    public DestinationSetting addGlobalDestination() {
        String name = DEFAULT_NAME;
        int index = -1;
        for (DestinationSetting current : destinations) {
            String currentName = current.getName();
            if (currentName.startsWith(name)) {
                String suffix = currentName.substring(name.length());
                if (suffix.length() == 0) {
                    index = Math.max(0, index);
                } else {
                    try {
                        int currentIndex = Integer.parseInt(suffix);
                        index = Math.max(currentIndex, index);
                    } catch (NumberFormatException e) {
                        // Ignore
                    }
                }
            }
        }
        if (index != -1) {
            index++;
            name += index;
        }
        DestinationSetting setting = new DestinationSetting(name);
        destinations.add(setting);
        DestinationPreferencesEvent event =
                new DestinationPreferencesEvent(
                        DestinationPreferencesEvent.ADDED);
        fireChanged(event);
        return setting;
    }

    /**
     * Removees the global destination with the given name.
     * 
     * @param name
     *            The name to remove.
     */
    public void removeGlobalDestination(String name) {
        DestinationSetting setting = getDestinationSetting(name);
        if (setting != null) {
            destinations.remove(setting);
            DestinationPreferencesEvent event =
                    new DestinationPreferencesEvent(
                            DestinationPreferencesEvent.REMOVED);
            fireChanged(event);
        }
    }

    private void fireChanged(DestinationPreferencesEvent event) {
        for (DestinationPreferencesListener listener : listeners) {
            listener.destinationPreferencesChanged(event);
        }
    }

    public void addDestinationPreferencesListener(
            DestinationPreferencesListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeDestinationPreferencesListener(
            DestinationPreferencesListener listener) {
        listeners.remove(listener);
    }

    /**
     * Return the global destination id for the given prefs destination env
     * name.
     * 
     * @param destName
     * @return
     */
    public String getGlobalDestinationId(String destName) {
        String globalId = null;
        DestinationSetting setting = getDestinationSetting(destName);
        if (setting != null) {
            globalId = setting.getGlobalDestinationId();
        }
        return globalId;
    }

    public void setGlobalDestinationId(String name, String globalId) {
        DestinationSetting setting = getDestinationSetting(name);
        if (setting != null) {
            setting.setGlobalDestinationId(globalId);
            DestinationPreferencesEvent event =
                    new DestinationPreferencesEvent(
                            DestinationPreferencesEvent.CHANGED);
            fireChanged(event);
        }
    }

    public String getValidationDestinationId(String name, String componentId) {
        String validationId = null;
        DestinationSetting setting = getDestinationSetting(name);
        if (setting != null) {
            DestinationComponentSetting component =
                    setting.getComponent(componentId);
            if (component != null) {
                validationId = component.getValidationDestinationId();
            }
        }
        if (validationId == null) {
            validationId =
                    GlobalDestinationUtil
                            .getDefaultValdationDestinationId(componentId);
        }
        return validationId;
    }

    public void setValidationDestinationId(String name, String componentId,
            String validationDestinationId) {
        DestinationSetting setting = getDestinationSetting(name);
        if (setting != null) {
            DestinationComponentSetting component =
                    setting.getComponent(componentId);
            if (component == null) {
                component = new DestinationComponentSetting(componentId);
                setting.addComponent(component);
            }
            component.setValidationDestinationId(validationDestinationId);
            DestinationPreferencesEvent event =
                    new DestinationPreferencesEvent(
                            DestinationPreferencesEvent.CHANGED);
            fireChanged(event);
        }
    }

    public boolean isComponentEnabled(String name, String componentId) {
        boolean enabled = false;
        String validationId = getValidationDestinationId(name, componentId);
        if (validationId != null) {
            enabled =
                    !GlobalDestinationUtil
                            .isDisabledComponentVersion(validationId);
        }
        return enabled;
    }

    public String[] getEnabledComponents(String name) {
        List<String> components = new ArrayList<String>();
        String globalId = getGlobalDestinationId(name);
        if (globalId != null) {
            String[] componentIds =
                    GlobalDestinationUtil.getAllComponents(globalId);
            for (String componentId : componentIds) {
                if (isComponentEnabled(name, componentId)) {
                    components.add(componentId);
                }
            }
        }
        return components.toArray(new String[components.size()]);
    }

    public String[] getSelectedValidationDestinations(String name) {
        List<String> validationIds = new ArrayList<String>();
        String globalId = getGlobalDestinationId(name);
        if (globalId != null) {
            String[] componentIds =
                    GlobalDestinationUtil.getAllComponents(globalId);
            for (String componentId : componentIds) {
                String validationId =
                        getValidationDestinationId(name, componentId);
                validationIds.add(validationId);
            }
        }
        return validationIds.toArray(new String[validationIds.size()]);
    }

    public Set<String> getGlobalDestinationsForValidationDestination(String id) {
        Set<String> names = new HashSet<String>();
        if (id != null) {
            for (String name : getGlobalDestinations()) {
                for (String validationId : getSelectedValidationDestinations(name)) {
                    if (id.equals(validationId)) {
                        names.add(name);
                    }
                }
            }
        }
        return names;
    }

    public String getDefaultGlobalDestination(String globalDestinationId) {
        String name = null;
        if (globalDestinationId != null) {
            for (DestinationSetting setting : getDefaultSettings()) {
                if (globalDestinationId
                        .equals(setting.getGlobalDestinationId())) {
                    name = setting.getName();
                    break;
                }
            }
        }
        return name;
    }

    public boolean canDelete(String name) {
        boolean canDelete = true;
        if (name != null) {
            IExtensionPoint extensionPoint =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(DestinationsActivator.PLUGIN_ID,
                                    "globalDestinationEnvironment"); //$NON-NLS-1$
            for (IConfigurationElement element : extensionPoint
                    .getConfigurationElements()) {
                String elementName = element.getName();
                if ("defaultGlobalDestination".equals(elementName)) { //$NON-NLS-1$
                    String defaultName = element.getAttribute("name"); //$NON-NLS-1$
                    if (name.equals(defaultName)) {
                        canDelete = false;
                        break;
                    }
                }
            }
        }
        return canDelete;
    }

    private List<DestinationSetting> getDefaultSettings() {
        Map<String, DestinationSetting> nameToSetting =
                new HashMap<String, DestinationSetting>();
        List<DestinationSetting> defaults = new ArrayList<DestinationSetting>();
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(DestinationsActivator.PLUGIN_ID,
                                "globalDestinationEnvironment"); //$NON-NLS-1$
        for (IConfigurationElement element : extensionPoint
                .getConfigurationElements()) {
            String elementName = element.getName();
            if ("defaultGlobalDestination".equals(elementName)) { //$NON-NLS-1$
                String name = element.getAttribute("name"); //$NON-NLS-1$
                String globalDestinationId =
                        element.getAttribute("globalDestinationId"); //$NON-NLS-1$
                DestinationSetting setting = new DestinationSetting(name);
                setting.setGlobalDestinationId(globalDestinationId);
                defaults.add(setting);
                nameToSetting.put(name, setting);
            }
        }
        for (IConfigurationElement element : extensionPoint
                .getConfigurationElements()) {
            String elementName = element.getName();
            if ("defaultGlobalDestinationComponent".equals(elementName)) { //$NON-NLS-1$
                String name = element.getAttribute("name"); //$NON-NLS-1$
                DestinationSetting setting = nameToSetting.get(name);
                if (setting != null) {
                    String globalDestinationComponentId =
                            element.getAttribute("globalDestinationComponentId"); //$NON-NLS-1$
                    String validationDestinationId =
                            element.getAttribute("validationDestinationId"); //$NON-NLS-1$
                    DestinationComponentSetting component =
                            new DestinationComponentSetting(
                                    globalDestinationComponentId);
                    component
                            .setValidationDestinationId(validationDestinationId);
                    setting.addComponent(component);
                }
            }
        }
        return defaults;
    }

    /**
     * @param componentId
     * @return The default destination component version (representd by the
     *         validaiton destiantion id) or <code>null</code> if there is no
     *         default.
     */
    public DestinationComponentSetting getDefaultDestinationComponentVersion(
            String componentId) {
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(DestinationsActivator.PLUGIN_ID,
                                "globalDestinationEnvironment"); //$NON-NLS-1$

        for (IConfigurationElement element : extensionPoint
                .getConfigurationElements()) {
            String elementName = element.getName();
            if ("defaultGlobalDestinationComponent".equals(elementName)) { //$NON-NLS-1$
                String globalDestinationComponentId =
                        element.getAttribute("globalDestinationComponentId"); //$NON-NLS-1$
                if (componentId.equals(globalDestinationComponentId)) {
                    String validationDestinationId =
                            element.getAttribute("validationDestinationId"); //$NON-NLS-1$

                    DestinationComponentSetting component =
                            new DestinationComponentSetting(
                                    globalDestinationComponentId);
                    component
                            .setValidationDestinationId(validationDestinationId);
                    return component;

                }
            }
        }
        return null;
    }

    public void restoreDefaults() {
        List<DestinationSetting> defaults = getDefaultSettings();
        List<String> names = new ArrayList<String>();
        for (DestinationSetting setting : defaults) {
            names.add(setting.getName());
        }
        for (DestinationSetting setting : destinations) {
            String name = setting.getName();
            if (!names.contains(name)) {
                defaults.add(setting);
            }
        }
        destinations = defaults;
        DestinationPreferencesEvent event =
                new DestinationPreferencesEvent(
                        DestinationPreferencesEvent.REFRESHED);
        fireChanged(event);
    }

    public boolean isValidationDestinationSelected(String name,
            String validationDestinationId) {
        boolean enabled = false;
        for (String validationId : getSelectedValidationDestinations(name)) {
            if (validationDestinationId.equals(validationId)) {
                enabled = true;
                break;
            }
        }
        return enabled;
    }

    public void moveGlobalDestinationUp(String name) {
        DestinationSetting setting = getDestinationSetting(name);
        int index = destinations.indexOf(setting);
        if (index > 0) {
            DestinationSetting other = destinations.get(index - 1);
            destinations.set(index - 1, setting);
            destinations.set(index, other);
            DestinationPreferencesEvent event =
                    new DestinationPreferencesEvent(
                            DestinationPreferencesEvent.REFRESHED);
            fireChanged(event);
        }
    }

    public void moveGlobalDestinationDown(String name) {
        DestinationSetting setting = getDestinationSetting(name);
        int index = destinations.indexOf(setting);
        if (index < (destinations.size() - 1)) {
            DestinationSetting other = destinations.get(index + 1);
            destinations.set(index + 1, setting);
            destinations.set(index, other);
            DestinationPreferencesEvent event =
                    new DestinationPreferencesEvent(
                            DestinationPreferencesEvent.REFRESHED);
            fireChanged(event);
        }
    }

}
