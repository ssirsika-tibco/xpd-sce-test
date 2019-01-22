/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.processeditor.xpdl2.properties.event.EventImplementationSection;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;

/**
 * 
 * Representation of the message event implementation template element. This
 * will provide all information set by the extension of the
 * messageEventImplementation extension point.
 * 
 * @author scrossle
 */
public final class EventImplementationElement implements Comparable,
        IPluginContribution {

    public static final String ATT_NAME = "name"; //$NON-NLS-1$

    public static final String ATT_EVENT_TYPE = "eventType"; //$NON-NLS-1$

    public static final String ATT_TRIGGER_RESULT_TYPE = "triggerResultType"; //$NON-NLS-1$

    public static final String ATT_CATCH_THROW = "catchThrow"; //$NON-NLS-1$

    public static final String VAL_CATCH = "Catch"; //$NON-NLS-1$

    public static final String VAL_THROW = "Throw"; //$NON-NLS-1$

    public static final String ATT_CLASS = "class"; //$NON-NLS-1$

    public static final String ATT_FILTER = "filter"; //$NON-NLS-1$

    private IConfigurationElement configElement = null;

    private ISection iSectionClass = null;

    private IFilter filterClass = null;

    private EventType eventType = null;

    private String name;

    private EventTriggerType triggerResultType;

    /**
     * Constructor reads values from configuration element (except class which
     * is loaded lazily).
     * 
     * @param configElement
     */
    public EventImplementationElement(IConfigurationElement configElement) {
        this.configElement = configElement;

        name = getAttribute(ATT_NAME);

        eventType = getEventType(configElement);

        String triggerResultString = getAttribute(ATT_TRIGGER_RESULT_TYPE);
        String catchThrow = getAttribute(ATT_CATCH_THROW);
        if (triggerResultString != null) {
            if (triggerResultString.equals("None")) { //$NON-NLS-1$
                triggerResultType = EventTriggerType.EVENT_NONE_LITERAL;
            } else if (triggerResultString.equals("Message")) { //$NON-NLS-1$
                if (VAL_THROW.equals(catchThrow)) {
                    triggerResultType =
                            EventTriggerType.EVENT_MESSAGE_THROW_LITERAL;
                } else {
                    triggerResultType =
                            EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL;
                }
            } else if (triggerResultString.equals("Timer")) { //$NON-NLS-1$
                triggerResultType = EventTriggerType.EVENT_TIMER_LITERAL;
            } else if (triggerResultString.equals("Rule")) { //$NON-NLS-1$
                triggerResultType = EventTriggerType.EVENT_CONDITIONAL_LITERAL;
            } else if (triggerResultString.equals("Link")) { //$NON-NLS-1$
                if (VAL_THROW.equals(catchThrow)) {
                    triggerResultType =
                            EventTriggerType.EVENT_LINK_THROW_LITERAL;
                } else {
                    triggerResultType =
                            EventTriggerType.EVENT_LINK_CATCH_LITERAL;
                }
            } else if (triggerResultString.equals("Multiple")) { //$NON-NLS-1$
                if (VAL_THROW.equals(catchThrow)) {
                    triggerResultType =
                            EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL;
                } else {
                    triggerResultType =
                            EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL;
                }
            } else if (triggerResultString.equals("Error")) { //$NON-NLS-1$
                triggerResultType = EventTriggerType.EVENT_ERROR_LITERAL;
            } else if (triggerResultString.equals("Cancel")) { //$NON-NLS-1$
                triggerResultType = EventTriggerType.EVENT_CANCEL_LITERAL;
            } else if (triggerResultString.equals("Compensation")) { //$NON-NLS-1$
                if (VAL_THROW.equals(catchThrow)) {
                    triggerResultType =
                            EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL;
                } else {
                    triggerResultType =
                            EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL;
                }
            } else if (triggerResultString.equals("Signal")) { //$NON-NLS-1$
                if (VAL_THROW.equals(catchThrow)) {
                    triggerResultType =
                            EventTriggerType.EVENT_SIGNAL_THROW_LITERAL;
                } else {
                    triggerResultType =
                            EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL;
                }
            } else if (triggerResultString.equals("Terminate")) { //$NON-NLS-1$
                triggerResultType = EventTriggerType.EVENT_TERMINATE_LITERAL;
            }
        }
    }

    public static EventType getEventType(IConfigurationElement configElement) {

        EventType eventType = null;

        if (configElement != null) {

            String eventTypeString = configElement.getAttribute(ATT_EVENT_TYPE);

            if (eventTypeString != null) {
                if (eventTypeString.equals("Start")) { //$NON-NLS-1$
                    eventType = EventType.START;
                } else if (eventTypeString.equals("Intermediate")) { //$NON-NLS-1$
                    eventType = EventType.INTERMEDIATE;
                } else if (eventTypeString.equals("End")) { //$NON-NLS-1$
                    eventType = EventType.END;
                } else if (eventTypeString.equals("Any")) { //$NON-NLS-1$
                    eventType = EventType.ANY;
                } else if (eventTypeString.equals("StartMethod")) { //$NON-NLS-1$
                    eventType = EventType.STARTMETHOD;
                } else if (eventTypeString.equals("IntermediateMethod")) { //$NON-NLS-1$
                    eventType = EventType.INTERMEDIATEMETHOD;
                }
            }
        }

        return eventType;
    }

    /**
     * Get the extension configuration element
     * 
     * @return
     */
    public IConfigurationElement getConfigElement() {
        return configElement;
    }

    /**
     * Get the <i>name</i> set in this extension
     * 
     * @return Extension name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the <i>eventType</i> set in this extension
     * 
     * @return selected type <code>EventType</code>
     */
    public EventType getEventType() {

        return eventType;
    }

    /**
     * @return the triggerResultType
     */
    public EventTriggerType getTriggerResultType() {
        return triggerResultType;
    }

    /**
     * Get the <i>class</i> set in the extension
     * 
     * @return <code>ISection</code> object defined in the extension
     * @throws CoreException
     */
    public ISection getISectionExec() throws CoreException {
        if (configElement != null && iSectionClass == null) {
            // Section may be older deprecated processwidget
            // EventImplementationSection or newer processeditor.xpdl2 one.
            // Either way, it doesn't matter, we'll handle the differences
            // internally.
            iSectionClass =
                    (ISection) configElement
                            .createExecutableExtension(ATT_CLASS);
        }

        return iSectionClass;
    }

    /**
     * Get the <i>filter</i> set in the extension (if any).
     * 
     * @return <code>IFilter</code> object defined in the extension or null.
     */
    public IFilter getFilterExec() {

        if (configElement != null && filterClass == null) {
            try {
                filterClass =
                        (IFilter) configElement
                                .createExecutableExtension(ATT_FILTER);
            } catch (CoreException e) {
                // ignore, this filter is optional
            }
        }

        return filterClass;
    }

    /**
     * Get the given attribute from the configuration element
     * 
     * @param attr
     * @return Attribute
     */
    private String getAttribute(String attr) {
        if (configElement != null) {
            return configElement.getAttribute(attr);
        }

        return null;
    }

    /**
     * Comparison based on priority, then default, then event type, and then
     * name.
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        EventImplementationElement other = (EventImplementationElement) o;

        int result = this.eventType.compareTo(other.eventType);
        if (result != 0)
            return result;

        if (this.triggerResultType.getValue() != other.triggerResultType
                .getValue()) {
            return -1;
        }

        return this.name.compareTo(other.name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result =
                PRIME
                        * result
                        + ((configElement == null) ? 0 : configElement
                                .hashCode());
        result =
                PRIME * result
                        + ((eventType == null) ? 0 : eventType.hashCode());
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        result =
                PRIME
                        * result
                        + ((triggerResultType == null) ? 0 : triggerResultType
                                .hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final EventImplementationElement other =
                (EventImplementationElement) obj;
        if (configElement == null) {
            if (other.configElement != null)
                return false;
        } else if (!configElement.equals(other.configElement))
            return false;
        if (eventType == null) {
            if (other.eventType != null)
                return false;
        } else if (!eventType.equals(other.eventType))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (triggerResultType == null) {
            if (other.triggerResultType != null)
                return false;
        } else if (!triggerResultType.equals(other.triggerResultType))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    public String getLocalId() {
        if (iSectionClass == null) {
            loadISection();
        }

        // ISection may be old process widget one or new processeditor one
        if (iSectionClass instanceof EventImplementationSection) {
            return ((EventImplementationSection) iSectionClass).getLocalId();
        } else if (iSectionClass instanceof com.tibco.xpd.processwidget.properties.EventImplementationSection) {
            return ((com.tibco.xpd.processwidget.properties.EventImplementationSection) iSectionClass)
                    .getLocalId();
        }

        return ""; //$NON-NLS-1$
    }

    public String getPluginId() {
        // ISection may be old process widget one or new processeditor one
        if (iSectionClass instanceof EventImplementationSection) {
            return ((EventImplementationSection) iSectionClass).getPluginId();

        } else if (iSectionClass instanceof com.tibco.xpd.processwidget.properties.EventImplementationSection) {
            return ((com.tibco.xpd.processwidget.properties.EventImplementationSection) iSectionClass)
                    .getPluginId();
        }

        return ""; //$NON-NLS-1$
    }

    private void loadISection() {
        try {
            getISectionExec();
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

}
