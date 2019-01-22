/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.n2.process.globalsignal.util.GlobalSignalMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.TriggerResultSignal;

/**
 * Mapping Content provider for Throw Global Signal Events.
 * 
 * @author kthombar
 * @since Feb 3, 2015
 */
public class ThrowGlobalSignalMappingContentProvider implements
        IStructuredContentProvider {

    /**
     * @param payloadDataMapperContentProvider
     * @param sourceContentProvider
     */
    public ThrowGlobalSignalMappingContentProvider() {

    }

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof Activity
                && ((Activity) inputElement).getProcess() != null) {

            Activity throwSignalEvent = (Activity) inputElement;

            if (throwSignalEvent.getEvent() != null
                    && throwSignalEvent.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {

                SignalData signalData =
                        EventObjectUtil
                                .getSignalDataExtensionElement(throwSignalEvent);

                if (signalData != null) {
                    EList<DataMapping> dataMappings =
                            signalData.getDataMappings();

                    EList<DataMapping> correlationMappings = null;

                    if (signalData.getCorrelationMappings() != null) {
                        correlationMappings =
                                signalData.getCorrelationMappings()
                                        .getDataMappings();
                    }

                    if (dataMappings != null || correlationMappings != null) {

                        return GlobalSignalMappingUtil
                                .getThrowGlobalSignalMappings(throwSignalEvent);

                    }
                }
            }
        }
        return new Object[0];
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
