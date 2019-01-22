/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.refactor;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.process.datamapper.ProcessDataMapperPlugin;
import com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider.ContextSide;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

/**
 * This helper reads the
 * com.tibco.xpd.process.datamapper.dataReferenceContextProvider extension point
 * for the DataReferenceContext provider.
 * 
 * 
 * @author ssirsika
 * @since 07-Apr-2016
 */
public class DataReferenceContextProviderHelper {

    private static final Set<IDataReferenceContextProvider> dataReferenceContextProviders =
            new HashSet<IDataReferenceContextProvider>();

    private static final String DATA_REFERENCE_CONTEXT_PROVIDER_EXT_POINT_ID =
            "dataReferenceContextProvider"; //$NON-NLS-1$

    private static Set<String> unrecognisedScriptDataMapperContexts =
            new HashSet<String>();

    private static final String PROVIDER_CLASS_ID_ATTRIBUTE = "providerClass"; //$NON-NLS-1$
    static {
        readExtensionPointContributions();
    }

    /**
     * Read the extension point contributions and store it in
     * dataReferenceContextProviders
     */
    private static void readExtensionPointContributions() {
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(ProcessDataMapperPlugin.PLUGIN_ID,
                                DATA_REFERENCE_CONTEXT_PROVIDER_EXT_POINT_ID);
        if (point != null) {
            IConfigurationElement[] contributions =
                    point.getConfigurationElements();
            for (IConfigurationElement contribution : contributions) {

                Object provider;
                try {
                    provider =
                            contribution
                                    .createExecutableExtension(PROVIDER_CLASS_ID_ATTRIBUTE);
                    if (provider instanceof IDataReferenceContextProvider) {
                        dataReferenceContextProviders
                                .add((IDataReferenceContextProvider) provider);
                    }
                } catch (CoreException e) {
                    ProcessDataMapperPlugin
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    "Error in reading implementation class of IDataReferenceContextProvider from extension");
                }
            }
        }
    }

    /**
     * Return the {@link DataReferenceContext} for passed context and
     * {@link ContextSide} combination.
     * 
     * @param context
     *            context in {@link String} form
     * @param side
     *            either of {@link ContextSide} values
     * @return {@link DataReferenceContext} and null if don't find anything
     */
    public static DataReferenceContext getDataReferenceContext(String context,
            ContextSide side) {
        for (IDataReferenceContextProvider provider : dataReferenceContextProviders) {
            DataReferenceContext contextObj =
                    provider.getDataReferenceContext(context, side);
            if (!DataReferenceContext.CONTEXT_UNKNOWN.equals(contextObj)) {
                return contextObj;
            }
        }

        /**
         * This error log entry is purely here to ensure that if we add
         * additionally script data mapper use cases that we make sure we
         * consider these.
         * 
         * When you see this error log, decide whether the context in question
         * is one that has data from the
         * ActivityInterfaceDataMapperContentContributor
         * .ACTIVITY_INTERFACE_CONTRIBUTOR_ID or
         * ProcessDataMapperContentContributor.PROCESS_DATA_CONTRIBUTOR_ID
         * datamapper contributors
         * 
         * If it does then add the context to condition in caller of
         * getDataReferenceContext(..) method or using extension point
         * com.tibco.xpd.process.datamapper.dataReferenceContextProvider and
         * return the appropriate DataReferenceContext.
         * 
         * If it does NOT then add the context to condition above and return
         * null.
         */
        if (!unrecognisedScriptDataMapperContexts.contains(context)) {
            /*
             * Only complain once per session! As data reference framework is
             * called al LOT
             */
            unrecognisedScriptDataMapperContexts.add(context);
            ProcessDataMapperPlugin
                    .getDefault()
                    .getLogger()
                    .error(new Exception(),
                            "ProcessDataMapperDataReferenceUtil: Unrecognised ScriptDataMapper context '" //$NON-NLS-1$
                                    + context + "'"); //$NON-NLS-1$
        }
        return null;
    }
}
