/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Content Provider for Global Signal Payload Data.
 * 
 * @author kthombar
 * @since Feb 3, 2015
 */
public class PayloadDataMapperContentProvider implements ITreeContentProvider {

    /**
     * the Object for which this section is available
     */
    private Activity selectedActivity;

    /**
     * specifies if scripts are required.
     */
    private boolean wantScripts;

    /**
     * Script content provider.
     */
    private ActivityScriptContentProvider scriptContentProvider;

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
        /*
         * do nothing here.
         */
    }

    /**
     * 
     * @param wantScripts
     *            pass true if we want to create script contents
     * @param direction
     *            the mapping direction.
     */
    public PayloadDataMapperContentProvider(boolean wantScripts,
            MappingDirection direction) {
        this.wantScripts = wantScripts;
        scriptContentProvider = new ActivityScriptContentProvider(direction);
    }

    /**
     * 
     */
    public PayloadDataMapperContentProvider() {

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
        scriptContentProvider.inputChanged(viewer, oldInput, newInput);
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {

        if (inputElement instanceof Activity) {

            this.selectedActivity = (Activity) inputElement;

            List<Object> treeElements = new LinkedList<Object>();

            if (wantScripts) {
                /*
                 * If we want scripts then add all the script elements.
                 */
                Object[] scriptElements =
                        scriptContentProvider.getElements(inputElement);

                if (scriptElements != null && scriptElements.length > 0) {

                    for (int i = 0; i < scriptElements.length; i++) {
                        treeElements.add(scriptElements[i]);
                    }
                }
            }

            /*
             * get all referenced payload concept path.
             */
            PayloadConceptPath[] referencedGlobalSignalPayloadData =
                    getReferencedGlobalSignalPayloadConceptPath((Activity) inputElement);

            if (referencedGlobalSignalPayloadData != null
                    && referencedGlobalSignalPayloadData.length != 0) {

                /*
                 * add dummy Correlation Folder which will be the parent root..
                 */
                treeElements.add(new CorrelationDataFolder(null));

                /*
                 * Add all the non-correlation playload data to the list which
                 * returns the root elements.
                 */
                for (PayloadConceptPath eachPayloadDataCP : referencedGlobalSignalPayloadData) {

                    if (!eachPayloadDataCP.isCorrelationPayloadData()) {
                        treeElements.add(eachPayloadDataCP);
                    }
                }

            } else {
                /*
                 * If referenced global signal has no payload data.
                 */
                treeElements
                        .add(Messages.PayloadDataMapperContentProvider_NoPayloadToMap_label);
            }
            return treeElements.toArray();
        }

        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        /*
         * If the parent is the dummy Correlation Folder we created then add all
         * correlation payloads as its children
         */
        if (parentElement instanceof CorrelationDataFolder) {

            PayloadConceptPath[] referencedGlobalSignalPayloadConceptPath =
                    getReferencedGlobalSignalPayloadConceptPath(selectedActivity);

            if (referencedGlobalSignalPayloadConceptPath != null
                    && referencedGlobalSignalPayloadConceptPath.length != 0) {
                List<Object> children = new ArrayList<Object>();

                for (PayloadConceptPath eachPayloadCP : referencedGlobalSignalPayloadConceptPath) {

                    if (eachPayloadCP.isCorrelationPayloadData()) {
                        children.add(eachPayloadCP);
                    }
                }
                return children.toArray();
            }

        }
        return new Object[0];
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Object getParent(Object element) {

        if (element instanceof PayloadConceptPath) {
            PayloadConceptPath payloadCP = (PayloadConceptPath) element;

            if (payloadCP.isCorrelationPayloadData()) {
                /*
                 * Only Correlation Payloads have parent and it is the dummy
                 * Correlation Folder as its parent.
                 */
                return new CorrelationDataFolder(null);
            }
        }
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof CorrelationDataFolder) {

            /*
             * Only the dummy correlation Folder has children and they should be
             * visible
             */
            return true;
        }
        return false;
    }

    /**
     * 
     * @param activity
     *            the activity in focus
     * @return list of Payload data in the global signal referenced by the
     *         passed activity wrapped in PayloadConceptPath.
     */
    public PayloadConceptPath[] getReferencedGlobalSignalPayloadConceptPath(
            Activity activity) {

        GlobalSignal referencedGlobalSignal =
                GlobalSignalUtil.getReferencedGlobalSignal(activity);

        if (referencedGlobalSignal != null) {
            EList<PayloadDataField> payloadDataFields =
                    referencedGlobalSignal.getPayloadDataFields();

            List<PayloadConceptPath> paylodaConceptPath =
                    new ArrayList<PayloadConceptPath>();

            for (PayloadDataField payloadDataField : payloadDataFields) {
                /*
                 * Wrap the payload in payload concept path.
                 */
                paylodaConceptPath
                        .add(new PayloadConceptPath(payloadDataField));
            }

            if (!paylodaConceptPath.isEmpty()) {

                PayloadConceptPath paylodaConceptPathArray[] =
                        paylodaConceptPath
                                .toArray(new PayloadConceptPath[paylodaConceptPath
                                        .size()]);

                Arrays.sort(paylodaConceptPathArray,
                        new PayloadConceptPathComparator());

                return paylodaConceptPathArray;
            }
        }
        return null;
    }

    /**
     * Comparator for sorting Payload Concept Path alphabetically.
     * 
     * 
     * @author kthombar
     * @since Feb 19, 2015
     */
    private class PayloadConceptPathComparator implements
            Comparator<PayloadConceptPath> {

        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         * 
         * @param paramT1
         * @param paramT2
         * @return
         */
        @Override
        public int compare(PayloadConceptPath paramT1,
                PayloadConceptPath paramT2) {

            if (paramT1 instanceof PayloadConceptPath
                    && paramT2 instanceof PayloadConceptPath) {
                /*
                 * Sort the Payload Concept Paths alphabetically
                 */
                PayloadDataField payloadDf1 = paramT1.getPayloadDataField();
                PayloadDataField payloadDf2 = paramT2.getPayloadDataField();

                if (payloadDf1 != null && payloadDf2 != null) {

                    String payloadText1 = WorkingCopyUtil.getText(payloadDf1);
                    String payloadText2 = WorkingCopyUtil.getText(payloadDf2);

                    if (payloadText1 != null && payloadText1.length() != 0
                            && payloadText2 != null
                            && payloadText2.length() != 0) {
                        return payloadText1.compareToIgnoreCase(payloadText2);
                    }
                }
            }
            return 0;
        }
    }
}
