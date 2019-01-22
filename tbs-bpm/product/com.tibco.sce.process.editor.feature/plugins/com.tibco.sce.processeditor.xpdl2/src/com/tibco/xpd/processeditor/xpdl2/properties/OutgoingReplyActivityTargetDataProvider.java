/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Content provider for API reply activity target data fields.
 * 
 * @author nwilson
 * @since 4 Apr 2014
 */
public class OutgoingReplyActivityTargetDataProvider implements
        ITreeContentProvider {

    private ConceptContentProvider umlContentProvider;

    private Activity activity;

    public OutgoingReplyActivityTargetDataProvider(MappingDirection direction) {
        umlContentProvider = new ConceptContentProvider(direction, true);
    }

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
     *      .lang.Object)
     */
    @Override
    public Object[] getElements(Object inputElement) {
        List<Object> parameters = new ArrayList<Object>();
        activity = (Activity) inputElement;
        if (activity != null) {
            Process process = activity.getProcess();
            if (process != null) {
                List<ConceptPath> paths = new ArrayList<ConceptPath>();
                Collection<ActivityInterfaceData> interfaceData =
                        ActivityInterfaceDataUtil
                                .getActivityInterfaceData(activity);
                for (ActivityInterfaceData data : interfaceData) {
                    addParameter(parameters, data.getData());
                }

                Collections.sort(paths, new ConceptPathComparator());
                parameters.addAll(paths);

            }
        }
        return parameters.toArray();
    }

    /**
     * @param parameters
     * @param next
     */
    private void addParameter(List<Object> parameters, ProcessRelevantData data) {
        if (data instanceof FormalParameter
                || (data instanceof DataField && !((DataField) data)
                        .isCorrelation())) {
            Class clss = ConceptUtil.getConceptClass(data);
            ConceptPath path = new ConceptPath(data, clss);
            parameters.add(path);
        }
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /**
     * @param viewer
     * @param oldInput
     * @param newInput
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    /**
     * @param parentElement
     * @return
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        Object[] items;

        if (umlContentProvider.getActivity() == null) {
            umlContentProvider.setActivity(activity);
        }
        items = umlContentProvider.getChildren(parentElement);

        return items;
    }

    /**
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    @Override
    public Object getParent(Object element) {
        if (umlContentProvider.getActivity() == null) {
            umlContentProvider.setActivity(activity);
        }
        return umlContentProvider.getParent(element);
    }

    /**
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    @Override
    public boolean hasChildren(Object element) {
        if (umlContentProvider.getActivity() == null) {
            umlContentProvider.setActivity(activity);
        }
        return umlContentProvider.hasChildren(element);
    }
}
