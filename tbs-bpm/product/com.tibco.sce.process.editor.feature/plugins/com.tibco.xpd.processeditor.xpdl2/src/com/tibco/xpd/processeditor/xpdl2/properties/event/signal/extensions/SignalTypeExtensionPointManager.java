/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;

/**
 * Utility class that manages the
 * <b>"com.tibco.xpd.processeditor.xpdl2.signalType"</b> extension point.
 * 
 * @author kthombar
 * @since Jan 28, 2015
 */
public class SignalTypeExtensionPointManager {

    /**
     * The extension point.
     */
    private static final String SIGNALTYPE_EXTENSIONPOINT =
            "signalTypeConfigurationSection"; //$NON-NLS-1$

    private List<SignalTypeExtensionPoint> signalTypeExtensions =
            new ArrayList<SignalTypeExtensionPoint>();

    /**
     * 
     */
    public SignalTypeExtensionPointManager() {
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                                SIGNALTYPE_EXTENSIONPOINT);

        if (point != null) {
            // Get all extensions of the signal type extension point
            IExtension[] exts = point.getExtensions();

            if (exts != null) {
                for (IExtension eachExtension : exts) {
                    IConfigurationElement[] elements =
                            eachExtension.getConfigurationElements();

                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {

                            this.signalTypeExtensions
                                    .add(new SignalTypeExtensionPoint(elem));

                        }
                    }
                }
            }
        }

        if (!signalTypeExtensions.isEmpty()) {
            Collections.sort(signalTypeExtensions, new ExtensionComparator());
        }
    }

    /**
     * 
     * @return list of SignalType extensions
     */
    public List<SignalTypeExtensionPoint> getSignalTypeExtensions() {
        return Collections.unmodifiableList(signalTypeExtensions);
    }

    /**
     * 
     * Comparator class to sort the extensions according to signal type button
     * sequence..
     * 
     * @author kthombar
     * @since Jan 30, 2015
     */
    private static class ExtensionComparator implements
            Comparator<SignalTypeExtensionPoint> {

        /**
         * Compare the button sequence of the two objects passed and return an
         * integer to sort them accordingly.
         * 
         * @param o1
         *            1st Object
         * @param o2
         *            2nd Object
         * @return A negative integer, zero, or a positive integer as the first
         *         argument is less than, equal to, or greater than the second.
         */
        @Override
        public int compare(SignalTypeExtensionPoint o1,
                SignalTypeExtensionPoint o2) {
            return Integer.valueOf(o1.getButtonSequence())
                    .compareTo(Integer.valueOf(o2.getButtonSequence()));
        }
    }
}
