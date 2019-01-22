/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPathComparator;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Concept path item provider for Process Data Mapper for all data in scope of
 * the given activity for Process Manager executed Scripts
 * 
 * @author Ali
 * @since 19 Jan 2015
 */
public class ProcessDataMapperConceptPathProvider implements
        ITreeContentProvider {

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    public void dispose() {
        // TODO Auto-generated method stub

    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // TODO Auto-generated method stub

    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    public Object[] getElements(Object inputElement) {
        List<ConceptPath> processData = new ArrayList<ConceptPath>();
        if (inputElement instanceof Activity) {

            Activity activity = (Activity) inputElement;
            createConceptPath(processData, getInScopeProcessData(activity));

        }
        ConceptPath[] conceptPath =
                processData.toArray(new ConceptPath[processData.size()]);
        Arrays.sort(conceptPath, new ConceptPathComparator());

        return conceptPath;
    }

    /**
     * @param activity
     * @return The process data set in scope for this content provider.
     */
    protected List<ProcessRelevantData> getInScopeProcessData(Activity activity) {
        return ProcessInterfaceUtil
                .getAllAvailableRelevantDataForActivity(activity);
    }

    /**
     * Create a Concept Path of the Process data.
     * 
     * @param processData
     * @param allDataDefinedInProcess
     */
    protected void createConceptPath(List<ConceptPath> processData,
            Collection<ProcessRelevantData> allDataDefinedInProcess) {

        for (ProcessRelevantData processRelevantData : allDataDefinedInProcess) {
            Class clss = ConceptUtil.getConceptClass(processRelevantData);
            ConceptPath path = new ConceptPath(processRelevantData, clss);
            processData.add(path);
        }
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    public Object[] getChildren(Object parentElement) {
        Object[] children = null;

        if (parentElement instanceof ChoiceConceptPath) {
            ChoiceConceptPath choiceConceptPath =
                    (ChoiceConceptPath) parentElement;

            List<ConceptPath> childList;
            childList = ConceptUtil.getConceptPathChildren(choiceConceptPath);
            children = childList.toArray();

        } else if (parentElement instanceof ConceptPath) {

            ConceptPath path = (ConceptPath) parentElement;
            List<ConceptPath> childList;
            childList = ConceptUtil.getConceptPathChildren(path, true);
            children = childList.toArray();
        }
        return children;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    public Object getParent(Object element) {
        Object parent = null;
        if (element instanceof ConceptPath) {
            parent = ((ConceptPath) element).getParent();
        }
        return parent;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    public boolean hasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof ConceptPath) {
            ConceptPath path = (ConceptPath) element;
            List<?> attributes = path.getChildren();
            if (attributes != null && attributes.size() > 0) {
                hasChildren = true;
            } else if (path.isArrayHeader()) {
                hasChildren = true;
            }
        }
        return hasChildren;
    }
}
