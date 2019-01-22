/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPathComparator;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerResultSignal;

/**
 * 
 * Target Content provider for Catch Global Signal.
 * 
 * @author kthombar
 * @since Feb 5, 2015
 */
public class CatchGlobalSignalMapperTargetContentProvider extends
        ActivityDataFieldItemProvider {

    /**
     * the Object for which this section is available
     */
    private Object selectedObject;

    /**
     * @param direction
     */
    public CatchGlobalSignalMapperTargetContentProvider() {
        super(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        selectedObject = inputElement;

        Object[] associatedData = getActivityAssociatedData(selectedObject);

        /*
         * If there are associated data's then return the dummy correlation
         * Folder and all the non correlation data as they will form the root
         * elements.
         */
        List<Object> targetElements = new ArrayList<Object>();

        /*
         * The Dummy Correlation Folder will be the First element in the tree.
         */
        targetElements.add(new CorrelationDataFolder(null));

        if (associatedData != null && associatedData.length > 0) {

            /*
             * Add all the non-correlation data to the list which returns the
             * root elements.
             */
            for (Object element : associatedData) {
                if (element instanceof ConceptPath) {
                    Object data = ((ConceptPath) element).getItem();

                    if (data instanceof DataField) {
                        /*
                         * if its datafield then the non-correlation data fields
                         * form the root.
                         */
                        DataField dField = (DataField) data;
                        if (!dField.isCorrelation()) {
                            targetElements.add(element);
                        }
                    } else if (data instanceof FormalParameter) {
                        /*
                         * if its a formal parameter so cannot have correlation.
                         */
                        targetElements.add(element);
                    }
                }
            }
        }
        return targetElements.toArray();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessDataConceptPathProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Object getParent(Object element) {

        if (element instanceof ConceptPath) {

            Object item = ((ConceptPath) element).getItem();

            if (item instanceof DataField) {

                if (((DataField) item).isCorrelation()) {
                    /*
                     * All correlation Data will have The Correlation Data
                     * Folder as their parnet.
                     */
                    return new CorrelationDataFolder(null);
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {

        if (parentElement instanceof CorrelationDataFolder) {
            /*
             * The Correlation Data will be the childer on Correlation Data
             * Folder.
             */
            Object[] activityAssociatedData =
                    getActivityAssociatedData(selectedObject);
            List<Object> children = new ArrayList<Object>();

            for (Object element : activityAssociatedData) {

                if (element instanceof ConceptPath) {

                    Object data = ((ConceptPath) element).getItem();

                    if (data instanceof DataField) {

                        DataField dField = (DataField) data;
                        if (dField.isCorrelation()) {
                            children.add(element);
                        }
                    }
                }
            }
            return children.toArray();
        }
        return super.getChildren(parentElement);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessDataConceptPathProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof CorrelationDataFolder) {
            /*
             * The correlation Data folder will have children.
             */
            return true;
        }
        return false;
    }

    /**
     * 
     * @param inputElement
     * @return the data associated with catch global signal events(selected via
     *         interface tab)
     */
    private Object[] getActivityAssociatedData(Object inputElement) {

        if (inputElement instanceof Activity
                && ((Activity) inputElement).getProcess() != null) {
            Activity catchGlobalSignalEvent = (Activity) inputElement;

            if (catchGlobalSignalEvent.getEvent() != null
                    && catchGlobalSignalEvent.getEvent()
                            .getEventTriggerTypeNode() instanceof TriggerResultSignal) {

                Collection<ActivityInterfaceData> associatedData =
                        ActivityInterfaceDataUtil
                                .getActivityInterfaceData(catchGlobalSignalEvent);

                if (!associatedData.isEmpty()) {
                    ConceptPath[] conceptPathPayload =
                            new ConceptPath[associatedData.size()];

                    int i = 0;
                    for (ActivityInterfaceData eachAssociatedData : associatedData) {
                        ProcessRelevantData processData =
                                eachAssociatedData.getData();
                        conceptPathPayload[i] =
                                ConceptUtil.getConceptPath(processData);

                        i++;
                    }

                    Arrays.sort(conceptPathPayload, new ConceptPathComparator());

                    return conceptPathPayload;

                }
            }
        }
        return new Object[0];
    }
}
