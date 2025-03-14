/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.signal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPathComparator;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil.GetSignalPayloadException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Catch Signal mapper source tree content provider
 * 
 * @author aallway
 * @since 26 Apr 2012
 */
public class CatchSignalMapperSourceContentProvider extends
        ActivityDataFieldItemProvider {

    /**
     * Id given to dummy parameter created on problem accessing payload.
     */
    public static final String CANT_ACCESS_SIGNAL_PAYLOAD_PARAM_ID =
            "$$_CANT_ACCESS_PAYLOAD"; //$NON-NLS-1$

    private boolean wantScripts;

    private ActivityScriptContentProvider scriptContentProvider;

    /**
     * @param direction
     */
    public CatchSignalMapperSourceContentProvider(boolean wantScripts) {
        super(MappingDirection.OUT);

        this.wantScripts = wantScripts;
        scriptContentProvider =
                new ActivityScriptContentProvider(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        Object[] elements = null;

        Object[] dataElements = internal_getElements(inputElement);

        if (wantScripts) {
            /*
             * Don't cache script content as this can change mid session with
             * creation of scripts.
             */
            Object[] scriptElements =
                    scriptContentProvider.getElements(inputElement);

            if (scriptElements != null && scriptElements.length > 0) {
                elements =
                        new Object[scriptElements.length + dataElements.length];

                int e = 0;
                for (int i = 0; i < scriptElements.length; i++, e++) {
                    elements[e] = scriptElements[i];
                }

                for (int i = 0; i < dataElements.length; i++, e++) {
                    elements[e] = dataElements[i];
                }
            }

        } else {
            elements = dataElements;
        }

        return elements;
    }

    /**
     * @param inputElement
     * @return The normal source content for catch signal mapper (i.e. the
     *         throwm signal's payload.
     */
    public List<ProcessRelevantData> getDataElements(Object inputElement) {

        List<ProcessRelevantData> dataElements =
                new ArrayList<ProcessRelevantData>();

        Object[] elements = getElements(inputElement);

        for (Object element : elements) {
            if (element instanceof ConceptPath) {
                Object data = ((ConceptPath) element).getItem();

                if (data instanceof ProcessRelevantData) {
                    if (!CANT_ACCESS_SIGNAL_PAYLOAD_PARAM_ID
                            .equals(((ProcessRelevantData) data).getId())) {
                        dataElements.add((ProcessRelevantData) data);
                    }
                }
            }
        }

        return dataElements;
    }

    /**
     * @param inputElement
     * @return
     */
    private Object[] internal_getElements(Object inputElement) {
        /*
         * The input element will be the catch error event, we then need to
         * redirect this to the throw signal event to pick up IT's associated
         * data.
         */
        if (inputElement instanceof Activity
                && ((Activity) inputElement).getProcess() != null) {
            Activity catchSignalEvent = (Activity) inputElement;

            if (catchSignalEvent.getEvent() != null
                    && catchSignalEvent.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {

                try {
                    Collection<ActivityInterfaceData> signalPayload =
                            EventObjectUtil.getSignalPayload(catchSignalEvent);

                    if (!signalPayload.isEmpty()) {
                        ConceptPath[] conceptPathPayload =
                                new ConceptPath[signalPayload.size()];

                        int i = 0;
                        for (ActivityInterfaceData signalData : signalPayload) {
                            ProcessRelevantData processData =
                                    signalData.getData();
                            conceptPathPayload[i] =
                                    ConceptUtil.getConceptPath(processData);
                            i++;
                        }

                        Arrays.sort(conceptPathPayload,
                                new ConceptPathComparator());

                        return conceptPathPayload;

                    } else {
                        /*
                         * Create a dummy formal parameter for
                         * "no data associated with signal"
                         */
                        FormalParameter param =
                                Xpdl2Factory.eINSTANCE.createFormalParameter();
                        param.eSet(Xpdl2Package.eINSTANCE
                                .getUniqueIdElement_Id(),
                                CANT_ACCESS_SIGNAL_PAYLOAD_PARAM_ID);
                        param.setName(Messages.CatchSignalMapperSourceContentProvider_NoDataAssociatedWithThrowSignal_message);
                        param.setMode(ModeType.OUT_LITERAL);

                        return new Object[] { new ConceptPath(param, null) };
                    }

                } catch (GetSignalPayloadException e) {
                    /*
                     * Create a dummy formal parameter with the payload access
                     * problem description in
                     */
                    FormalParameter param =
                            Xpdl2Factory.eINSTANCE.createFormalParameter();
                    param.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                            CANT_ACCESS_SIGNAL_PAYLOAD_PARAM_ID);

                    param.setName(e.getMessage());
                    param.setMode(ModeType.OUT_LITERAL);

                    return new Object[] { new ConceptPath(param, null) };
                }

            }
        }

        return new Object[0];
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        super.inputChanged(viewer, oldInput, newInput);
        scriptContentProvider.inputChanged(viewer, oldInput, newInput);
    }
}
