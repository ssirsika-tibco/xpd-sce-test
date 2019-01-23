/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.systemActions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Bundle;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.internal.Messages;

/**
 * A manager class that manages the system action extensions. Use
 * {@link #getInstance()} to create a new instance of this class.
 * 
 * @author njpatel
 * 
 */
public final class SystemActionManager {

    private static final String SYSTEM_ACTIONS_EXTENSION_POINT =
            "systemActions"; //$NON-NLS-1$

    private static final String ELEM_COMPONENT = "component"; //$NON-NLS-1$

    private static final String ELEM_ACTIONS = "actions"; //$NON-NLS-1$

    private static final String ELEM_ACTION = "action"; //$NON-NLS-1$

    private static final String ELEM_APPLIES_TO = "appliesTo"; //$NON-NLS-1$

    private static final String ATTR_COMPONENT_ID = "componentId"; //$NON-NLS-1$

    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    private static final String ATTR_NAME = "name"; //$NON-NLS-1$

    private static final String ATTR_DEFAULT_VALUE = "default"; //$NON-NLS-1$

    private static final String ATTR_DESCRIPTION = "description"; //$NON-NLS-1$

    private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

    private final Set<ISystemActionComponent> components;

    private final Map<String /* component id */, Set<SystemAction>> actions;

    /**
     * Private constructor.
     */
    private SystemActionManager() {
        IExtensionPoint extPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(OMResourcesActivator.PLUGIN_ID,
                                SYSTEM_ACTIONS_EXTENSION_POINT);
        components = new TreeSet<ISystemActionComponent>();
        actions = new HashMap<String, Set<SystemAction>>();

        if (extPoint != null) {
            loadExtensions(extPoint);
        }
    }

    /**
     * Create an instance of this manager class.
     * 
     * @return
     */
    public static final SystemActionManager getInstance() {
        return new SystemActionManager();
    }

    /**
     * Get all registered components.
     * 
     * @return Collection of {@link ISystemActionComponent components}.
     */
    public Collection<ISystemActionComponent> getComponents() {
        return components;
    }

    /**
     * Get the component with the given id
     * 
     * @param id
     *            component id
     * @return {@link ISystemActionComponent} if found, <code>null</code>
     *         otherwise.
     */
    public ISystemActionComponent getComponent(String id) {
        if (id != null && components != null) {
            for (ISystemActionComponent component : components) {
                if (id.equals(component.getId())) {
                    return component;
                }
            }
        }
        return null;
    }

    /**
     * Get all registered actions. This will contain actions from all
     * components.
     * 
     * @return Collection of {@link ISystemAction actions}.
     */
    public Collection<ISystemAction> getActions() {
        Set<ISystemAction> actionList = new TreeSet<ISystemAction>();

        if (actions != null) {
            for (Set<SystemAction> compActions : actions.values()) {
                if (compActions != null && !compActions.isEmpty()) {
                    actionList.addAll(compActions);
                }
            }
        }

        return actionList;
    }

    /**
     * Get all actions that belong to the component with the given id.
     * 
     * @param componentId
     *            component id
     * @return Collection of {@link ISystemAction actions} belonging to the
     *         component, or empty list if not found.
     */
    public Collection<ISystemAction> getActions(String componentId) {
        Set<ISystemAction> actions = new TreeSet<ISystemAction>();
        Set<SystemAction> sysActions = this.actions.get(componentId);
        if (sysActions != null && !sysActions.isEmpty()) {
            actions.addAll(sysActions);
        }
        return actions;
    }

    /**
     * Get all actions that apply to the given Organization Model element.
     * 
     * @param eo
     *            if none apply to the given element.
     */
    public Collection<ISystemAction> getActionsFor(EObject eo) {
        Set<ISystemAction> actionList = new TreeSet<ISystemAction>();

        if (eo != null) {
            Collection<ISystemAction> allActions = getActions();

            if (allActions != null) {
                for (ISystemAction action : allActions) {
                    if (action.canApplyTo(eo)) {
                        actionList.add(action);
                    }
                }
            }
        }

        return actionList;
    }

    /**
     * Get all actions that apply to the given Organization Model element.
     * 
     * @param component
     * @param eo
     *            if none apply to the given element.
     */
    public Collection<ISystemAction> getActionsForComponent(
            ISystemActionComponent component, EObject eo) {
        Set<ISystemAction> actionList = new TreeSet<ISystemAction>();

        if (eo != null && component != null) {
            Collection<ISystemAction> allActions = component.getActions();

            if (allActions != null) {
                for (ISystemAction action : allActions) {
                    if (action.canApplyTo(eo)) {
                        actionList.add(action);
                    }
                }
            }
        }

        return actionList;
    }

    public Collection<ISystemActionComponent> getSystemActionComponentsFor(
            EObject eo) {
        Set<ISystemActionComponent> componentList =
                new TreeSet<ISystemActionComponent>();

        if (eo != null) {

            for (ISystemActionComponent component : getComponents()) {
                /* Only add component if has applicable actions. */
                for (ISystemAction action : component.getActions()) {
                    if (action.canApplyTo(eo)) {
                        componentList.add(component);
                        break;
                    }
                }
            }
        }

        return componentList;
    }

    /**
     * Load the system actions extensions.
     * 
     * @param extPoint
     */
    private void loadExtensions(IExtensionPoint extPoint) {
        IConfigurationElement[] elements = extPoint.getConfigurationElements();

        if (elements != null && elements.length > 0) {
            for (IConfigurationElement elem : elements) {
                if (ELEM_COMPONENT.equals(elem.getName())) {
                    components.add(new SystemActionComponent(elem));
                } else if (ELEM_ACTIONS.equals(elem.getName())) {
                    String componentId = elem.getAttribute(ATTR_COMPONENT_ID);
                    if (componentId != null) {
                        Set<SystemAction> actionList = actions.get(componentId);
                        if (actionList == null) {
                            actionList = new HashSet<SystemAction>();
                            actions.put(componentId, actionList);
                        }

                        IConfigurationElement[] children =
                                elem.getChildren(ELEM_ACTION);
                        if (children != null) {
                            for (IConfigurationElement child : children) {
                                actionList.add(new SystemAction(child,
                                        componentId));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * System action component.
     * 
     * @author njpatel
     */
    private class SystemActionComponent implements ISystemActionComponent {

        private final String id;

        private final String name;

        /**
         * System action component.
         * 
         * @param element
         */
        private SystemActionComponent(IConfigurationElement element) {
            id = element.getAttribute(ATTR_ID);
            name = element.getAttribute(ATTR_NAME);
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.om.resources.systemActions.ISystemActionComponent#
         * getActions()
         */
        @Override
        public Collection<ISystemAction> getActions() {
            Set<ISystemAction> actionList = new TreeSet<ISystemAction>();

            if (actions != null) {
                Set<SystemAction> sysActions = actions.get(getId());
                if (sysActions != null && !sysActions.isEmpty()) {
                    actionList.addAll(sysActions);
                }
            }
            return actionList;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.om.resources.systemActions.ISystemActionComponent#getId
         * ()
         */
        @Override
        public String getId() {
            return id;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.om.resources.systemActions.ISystemActionComponent#getName
         * ()
         */
        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            if (name != null) {
                return "System Action Category: " + name; //$NON-NLS-1$
            }
            return super.toString();
        }

        @Override
        public int hashCode() {
            int code = super.hashCode();
            if (name != null) {
                code += name.hashCode();
            }
            return code;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ISystemActionComponent) {
                ISystemActionComponent other = (ISystemActionComponent) obj;
                if (id != null && id.equals(other.getId())) {
                    if (name != null && name.equals(other.getName())) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public int compareTo(ISystemActionComponent o) {
            if (name != null && o.getName() != null) {
                return name.compareTo(o.getName());
            }
            return 0;
        }
    }

    /**
     * System action.
     * 
     * @author njpatel
     */
    private class SystemAction implements ISystemAction,
            Comparable<ISystemAction> {

        private final String id;

        private final String name;

        private final String defaultValue;

        private final String description;

        private List<Class<?>> applyTo;

        private ISystemActionComponent component;

        private final String componentId;

        /**
         * System action
         * 
         * @param elem
         * @param componentId
         */
        private SystemAction(IConfigurationElement elem, String componentId) {
            this.componentId = componentId;
            id = elem.getAttribute(ATTR_ID);
            name = elem.getAttribute(ATTR_NAME);
            defaultValue = elem.getAttribute(ATTR_DEFAULT_VALUE);
            description = elem.getAttribute(ATTR_DESCRIPTION);
            try {
                applyTo = getAppliesTo(elem);
            } catch (InvalidRegistryObjectException e) {
                OMResourcesActivator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format(Messages.SystemActionManager_errorLoadingAction_message,
                                        id));
            } catch (Exception e) {
                OMResourcesActivator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format(Messages.SystemActionManager_errorLoadingAction_message,
                                        id));
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.om.resources.systemActions.ISystemAction#canApplyTo
         * (org.eclipse.emf.ecore.EObject)
         */
        @Override
        public boolean canApplyTo(EObject eo) {
            if (applyTo != null && eo != null) {
                if (applyTo.isEmpty()) {
                    // If no applyTo set then can apply only to the Organization
                    // Model
                    return eo instanceof OrgModel;
                }

                for (Class<?> clazz : applyTo) {
                    if (clazz.isInstance(eo)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.om.resources.systemActions.ISystemAction#getDefaultValue
         * ()
         */
        @Override
        public String getDefaultValue() {
            return defaultValue;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.om.resources.systemActions.ISystemAction#getDescription
         * ()
         */
        @Override
        public String getDescription() {
            return description;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tibco.xpd.om.resources.systemActions.ISystemAction#getId()
         */
        @Override
        public String getId() {
            return id;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.tibco.xpd.om.resources.systemActions.ISystemAction#getName()
         */
        @Override
        public String getName() {
            return name;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.om.resources.systemActions.ISystemAction#getComponent()
         */
        @Override
        public ISystemActionComponent getComponent() {
            if (component == null && componentId != null) {
                // Find the parent component
                component = SystemActionManager.this.getComponent(componentId);
            }
            return component;
        }

        /**
         * Load the "appliesTo" values from the extension.
         * 
         * @param elem
         * @return
         * @throws InvalidRegistryObjectException
         * @throws Exception
         */
        private List<Class<?>> getAppliesTo(IConfigurationElement elem)
                throws InvalidRegistryObjectException, Exception {
            List<Class<?>> classes = new ArrayList<Class<?>>();

            IConfigurationElement[] children =
                    elem.getChildren(ELEM_APPLIES_TO);

            if (children != null && children.length > 0) {
                Bundle bundle =
                        Platform.getBundle(elem.getContributor().getName());
                if (bundle != null) {
                    for (IConfigurationElement child : children) {
                        String className = child.getAttribute(ATTR_CLASS);
                        if (className != null) {
                            Class<?> clazz = bundle.loadClass(className);
                            if (clazz != null) {
                                classes.add(clazz);
                            }
                        }
                    }
                } else {
                    throw new Exception(
                            String.format(Messages.SystemActionManager_cannotFindBundle_error_message,
                                    elem.getContributor().getName()));
                }
            }
            return classes;
        }

        @Override
        public int hashCode() {
            int code = super.hashCode();
            if (name != null) {
                code += name.hashCode();
            }
            return code;
        }

        @Override
        public String toString() {
            if (name != null) {
                return "System Action: " + name; //$NON-NLS-1$
            }
            return super.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ISystemAction) {
                ISystemAction other = (ISystemAction) obj;
                if (id != null && id.equals(other.getId())) {
                    if (name != null && name.equals(other.getName())) {
                        if (getComponent() != null
                                && getComponent().equals(other.getComponent())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public int compareTo(ISystemAction o) {
            if (name != null && o.getName() != null) {
                return name.compareTo(o.getName());
            }
            return 0;
        }
    }
}
