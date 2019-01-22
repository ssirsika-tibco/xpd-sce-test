/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * Wrapper for <b>"com.tibco.xpd.processeditor.xpdl2.signalType"</b> extension
 * elements.
 * 
 * @author kthombar
 * @since Jan 28, 2015
 */
public class SignalTypeExtensionPoint {

    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    private static final String ATTR_LABEL = "label";//$NON-NLS-1$

    private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

    private static final String ATTR_BUTTONSEQUENCE = "buttonSequence";//$NON-NLS-1$

    private final IConfigurationElement element;

    private AbstractSignalTypeSection contributedClass;

    /**
     * @param elem
     */
    public SignalTypeExtensionPoint(IConfigurationElement element) {
        this.element = element;
        Object obj = null;

        try {
            obj = element.createExecutableExtension(ATTR_CLASS);
            if (obj instanceof AbstractSignalTypeSection) {
                contributedClass = (AbstractSignalTypeSection) obj;
            } else {
                throw (new Exception(
                        "Contributed class is not instanceof com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection")); //$NON-NLS-1$
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get id of the extension.
     * 
     * @return ID
     */
    public String getId() {
        String id = null;

        if (element != null) {
            id = element.getAttribute(ATTR_ID);
        }

        return id != null ? id : ""; //$NON-NLS-1$
    }

    /**
     * 
     * @return label of the extension signaltype.
     */
    public String getLabel() {
        String label = null;

        if (element != null) {
            label = element.getAttribute(ATTR_LABEL);
        }

        return label != null && !label.isEmpty() ? label : "No Label"; //$NON-NLS-1$
    }

    /**
     * 
     * @return the contributed section class for the extension point.
     */
    public AbstractSignalTypeSection getSignalTypeSection() {

        return contributedClass;
    }

    /**
     * 
     * @return the sequence of the signal type button.
     */
    public int getButtonSequence() {
        String op = null;

        if (element != null) {

            op = element.getAttribute(ATTR_BUTTONSEQUENCE).trim();
        }

        /*
         * Need to make sure that Button sequence is an integer.
         */
        if (op.matches("-?\\d+$")) { //$NON-NLS-1$
            return Integer.parseInt(op);
        } else {
            try {
                throw new Exception(
                        "Invalid value for Button Sequence, should be a number only"); //$NON-NLS-1$
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return (-1);
    }
}
