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
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Item Provider for actual parameters. Can be used as source or target, but you
 * have to say which. This does not alter the content based on the mappings so
 * it is quite straight forward.
 * 
 * @author scrossle TODO Listen for notifications for additions of parameters
 *         (then can provide an action to add them from the mapper).
 */
public class IncomingRequestActivityTargetDataProvider implements
        ITreeContentProvider {

    private ConceptContentProvider umlContentProvider;

    private Activity activity;

    public IncomingRequestActivityTargetDataProvider(MappingDirection direction) {
        umlContentProvider = new ConceptContentProvider(direction, false);
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

                // XPD-6043 Changed to only consider API data. Mappings to
                // non-API data will then be picked up as validation errors.
                List<ConceptPath> paths = new ArrayList<ConceptPath>();
                Collection<ActivityInterfaceData> interfaceData =
                        ActivityInterfaceDataUtil
                                .getActivityInterfaceData(activity);
                for (ActivityInterfaceData data : interfaceData) {
                    addParameter(parameters, data.getData());
                }

                Collections.sort(paths, new ConceptPathComparator());
                parameters.addAll(paths);

                if (getCorrelationDataFields(activity).size() != 0) {
                    CorrelationDataFolder container =
                            new CorrelationDataFolder(process);
                    parameters.add(container);
                }
            }
        }
        return parameters.toArray();
    }

    /**
     * @param activity
     *            the activity to set
     */
    protected void setActivity(Activity activity) {
        this.activity = activity;
    }

    private List<DataField> getCorrelationDataFields(Activity activity) {
        /*
         * Get only the correlation data associated with activity (want to use
         * provider for more than just UI (like rules) and therefore need this
         * list filtered to what it should be anyway (without relying on ui
         * viewer filter in section code).
         */
        return ProcessInterfaceUtil
                .getAssociatedCorrelationDataForActivity(activity);
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
        if (parentElement instanceof CorrelationDataFolder) {
            List<DataField> fields = getCorrelationDataFields(activity);
            List<ConceptPath> children = new ArrayList<ConceptPath>();
            for (DataField next : fields) {
                if (next.isCorrelation()) {
                    Class clss = ConceptUtil.getConceptClass(next);
                    ConceptPath path = new ConceptPath(next, clss);
                    children.add(path);
                }
            }

            Collections.sort(children, new ConceptPathComparator());

            items = children.toArray();

        } else {
            if (umlContentProvider.getActivity() == null) {
                umlContentProvider.setActivity(activity);
            }
            items = umlContentProvider.getChildren(parentElement);
        }
        return items;
    }

    /**
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    @Override
    public Object getParent(Object element) {
        Object parent = null;
        if (isCorrelationDataField(element)) {
            if (element instanceof ConceptPath) {
                ConceptPath path = (ConceptPath) element;
                Object item = path.getItem();
                if (item instanceof DataField) {
                    DataField field = (DataField) item;
                    Process process = Xpdl2ModelUtil.getProcess(field);
                    if (process != null) {
                        parent = new CorrelationDataFolder(process);
                    }
                }
            }
        }
        if (parent == null) {
            if (umlContentProvider.getActivity() == null) {
                umlContentProvider.setActivity(activity);
            }
            parent = umlContentProvider.getParent(element);
        }
        return parent;
    }

    private boolean isCorrelationDataField(Object element) {
        boolean isCorrelation = false;
        if (element instanceof ConceptPath) {
            ConceptPath path = (ConceptPath) element;
            Object item = path.getItem();
            if (item instanceof DataField) {
                DataField field = (DataField) item;
                isCorrelation = field.isCorrelation();
            }
        }
        return isCorrelation;
    }

    /**
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    @Override
    public boolean hasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof CorrelationDataFolder) {
            hasChildren = getCorrelationDataFields(activity).size() != 0;
        } else {
            if (umlContentProvider.getActivity() == null) {
                umlContentProvider.setActivity(activity);
            }
            hasChildren = umlContentProvider.hasChildren(element);
        }
        return hasChildren;
    }
}
