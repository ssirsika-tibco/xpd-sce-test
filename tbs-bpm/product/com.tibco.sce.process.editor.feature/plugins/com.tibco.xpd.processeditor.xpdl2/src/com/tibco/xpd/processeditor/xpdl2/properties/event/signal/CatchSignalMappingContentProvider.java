/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.signal;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.TriggerResultSignal;

/**
 * Mapping content provider.
 * 
 * @author aallway
 * @since 26 Apr 2012
 */
public class CatchSignalMappingContentProvider implements
        IStructuredContentProvider {

    private CatchSignalMapperSourceContentProvider sourceContentProvider;

    /**
     * @param sourceContentProvider
     */
    public CatchSignalMappingContentProvider(
            CatchSignalMapperSourceContentProvider sourceContentProvider) {
        this.sourceContentProvider = sourceContentProvider;
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

            Activity catchSignalEvent = (Activity) inputElement;

            if (catchSignalEvent.getEvent() != null
                    && catchSignalEvent.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {

                SignalData signalData =
                        EventObjectUtil
                                .getSignalDataExtensionElement(catchSignalEvent);

                if (signalData != null) {
                    EList<DataMapping> dataMappings =
                            signalData.getDataMappings();

                    if (dataMappings != null) {
                        return CatchSignalMappingUtil
                                .getMappings(catchSignalEvent,
                                        sourceContentProvider
                                                .getDataElements(inputElement));

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
