/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.customer.api.iprocess.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.customer.api.plugin.BusinessStudioCustomerApiPlugin;

/**
 * Utility class that manages the iProcessBpmConversion extension point.
 * <p>
 * Use method <code>{@link #createInstance()}</code> to create an instance of
 * this class.
 * </p>
 * 
 * @author sajain
 * @since Mar 30, 2014
 */
public class IProcessToBPMConversionExtensionPointManager {

    /** Conversion Extension Point name */
    private static final String CONVERSION_EXTENSIONPOINT =
            "iProcessBpmConversion"; //$NON-NLS-1$

    /** Converter elements */
    private static final String CONVERTER_ELEMENT = "Converter"; //$NON-NLS-1$

    private List<IProcessToBPMConversionExtension> converterExtensions =
            new ArrayList<IProcessToBPMConversionExtension>();

    /** Lifecycle Conversion elements */
    private static final String LIFECYCLE_LISTENER_ELEMENT =
            "LifeCycleListener"; //$NON-NLS-1$

    private List<IProcessToBPMLifeCycleExtension> lifeCycleExtensions =
            new ArrayList<IProcessToBPMLifeCycleExtension>();

    /**
     * Default constructor.
     */
    public IProcessToBPMConversionExtensionPointManager() {
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(BusinessStudioCustomerApiPlugin.PLUGIN_ID,
                                CONVERSION_EXTENSIONPOINT);

        if (point != null) {
            // Get all extensions of the convert project extension point
            IExtension[] exts = point.getExtensions();

            if (exts != null) {
                for (IExtension eachExtension : exts) {
                    IConfigurationElement[] elements =
                            eachExtension.getConfigurationElements();

                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {
                            if (CONVERTER_ELEMENT.equals(elem.getName())) {
                                this.converterExtensions
                                        .add(new IProcessToBPMConversionExtension(
                                                elem));

                            } else if (LIFECYCLE_LISTENER_ELEMENT.equals(elem
                                    .getName())) {
                                this.lifeCycleExtensions
                                        .add(new IProcessToBPMLifeCycleExtension(
                                                elem));
                            }
                        }
                    }
                }
            }
        }

        if (!converterExtensions.isEmpty()) {
            Collections.sort(converterExtensions, new ExtensionComparator());
        }
    }

    /**
     * Get all Converter extensions of the migrate project extension point.
     * 
     * @return Unmodifiable list of extensions.
     */
    public List<IProcessToBPMConversionExtension> getConverterExtensions() {
        return Collections.unmodifiableList(converterExtensions);
    }

    /**
     * Get all LifeCycleListener extensions of the migrate project extension
     * point.
     * 
     * @return Unmodifiable list of extensions.
     */
    public List<IProcessToBPMLifeCycleExtension> getLifeCycleListenerExtensions() {
        return Collections.unmodifiableList(lifeCycleExtensions);
    }

    /**
     * Comparator class to sort the extensions according to their ordering
     * priority.
     * 
     * @author sajain
     * @since Mar 30, 2014
     */
    private static class ExtensionComparator implements
            Comparator<IProcessToBPMConversionExtension> {

        /**
         * Compare the ordering priority of the two objects passed and return an
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
        public int compare(IProcessToBPMConversionExtension o1,
                IProcessToBPMConversionExtension o2) {
            return Integer.valueOf(o1.getOrderingPriority())
                    .compareTo(Integer.valueOf(o2.getOrderingPriority()));
        }

    }

}
