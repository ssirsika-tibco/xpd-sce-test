/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class ConceptContentProvider implements ITreeContentProvider {

    /** The mapping direction. */
    private MappingDirection direction;

    /**
     * True to indicate that source mappings should be taken into account when
     * expanding array items, false to indicate that target mappings should be
     * used.
     */
    private boolean isSourceMapping;

    private boolean expandArrayTypes;

    /** The activity. */
    private Activity activity;

    public ConceptContentProvider(MappingDirection direction) {
        this(direction, MappingDirection.IN.equals(direction));
    }

    /**
     * @param direction
     *            Whether the mapping is into or out of the task.
     * @param isSourceMapping
     *            true to take source mappings into account for array
     *            expansions, false to use target mappings.
     */
    public ConceptContentProvider(MappingDirection direction,
            boolean isSourceMapping) {
        this(direction, isSourceMapping, false);
    }

    /**
     * @param direction
     *            Whether the mapping is into or out of the task.
     * @param isSourceMapping
     *            true to take source mappings into account for array
     *            expansions, false to use target mappings.
     * @param expandArrayTypes
     *            true to expand the child fields of array types.
     */
    public ConceptContentProvider(MappingDirection direction,
            boolean isSourceMapping, boolean expandArrayTypes) {
        this.direction = direction;
        this.isSourceMapping = isSourceMapping;
        this.expandArrayTypes = expandArrayTypes;
    }

    /**
     * @param parentElement
     * @return
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        Object[] children = null;
        if (parentElement instanceof ChoiceConceptPath) {
            children =
                    ConceptUtil
                            .getConceptPathChildren((ChoiceConceptPath) parentElement)
                            .toArray();
        }

        else if (parentElement instanceof ConceptPath) {
            ConceptPath path = (ConceptPath) parentElement;
            List<ConceptPath> childList;

            // XDP-3499: added array field expansion for CIM. This must be
            // enabled through the preference setting.
            if (Xpdl2ResourcesPlugin.shouldExpandMapperFields()
                    && path.isArrayHeader()) {
                childList = new ArrayList<ConceptPath>();
                int maxIndex = getMaxMappedIndex(path);
                for (int i = 0; i < (maxIndex + 2); i++) {
                    ConceptPath child =
                            new ConceptPath(path, path.getItem(),
                                    path.getType(), i);
                    childList.add(child);
                }
            } else {
                childList =
                        ConceptUtil.getConceptPathChildren(path,
                                expandArrayTypes);
            }
            children = childList.toArray();
        }

        return children;

    }

    /**
     * Works out the highest index of mapped items under the given path.
     * 
     * @param path
     *            The path to check.
     * @return The highest mapped array index under the given path.
     */
    private int getMaxMappedIndex(ConceptPath path) {
        int max = -1;
        String matchPath = path.getPath() + '[';
        List<DataMapping> mappings = getDataMappings();
        for (DataMapping mapping : mappings) {
            String target = DataMappingUtil.getTarget(mapping);
            String script = DataMappingUtil.getScript(mapping);
            String grammar = DataMappingUtil.getGrammar(mapping);
            if (target != null && script != null && grammar != null) {
                String name = null;
                if (isSourceMapping) {
                    if (!DataMappingUtil.isScripted(mapping)) {
                        name = script;
                    }
                } else {
                    name = target;
                }
                if (name != null) {
                    int index = getMatchIndex(matchPath, name);
                    max = Math.max(max, index);
                }
            }
        }
        return max;
    }

    /**
     * @param matchPath
     *            The path to match.
     * @param script
     *            The script.
     * @return The index from the script or -1.
     */
    private int getMatchIndex(String matchPath, String script) {
        int index = -1;
        if (script.startsWith(matchPath)) {
            int close = script.indexOf(']', matchPath.length());
            if (close != -1) {
                String indexString =
                        script.substring(matchPath.length(), close);
                try {
                    index = Integer.valueOf(indexString);
                } catch (NumberFormatException e) {
                    // Ignore
                }
            }
        }
        return index;
    }

    /**
     * @param wso
     *            The web service operation.
     * @return A list of data mappings.
     */
    private List<DataMapping> getDataMappings() {
        List<DataMapping> result = new ArrayList<DataMapping>();
        // System.out.println(MappingDirection.IN.equals(direction) + ":"
        // + isSourceMapping);
        boolean in =
                MappingDirection.IN.equals(direction) && !isSourceMapping
                        || MappingDirection.OUT.equals(direction)
                        && isSourceMapping;
        if (MappingDirection.IN.equals(direction)) {
            result.addAll(Xpdl2ModelUtil.getDataMappings(activity,
                    DirectionType.IN_LITERAL));
        } else if (MappingDirection.OUT.equals(direction)) {
            result.addAll(Xpdl2ModelUtil.getDataMappings(activity,
                    DirectionType.OUT_LITERAL));
        }
        result.addAll(Xpdl2ModelUtil.getDataMappings(activity,
                DirectionType.INOUT_LITERAL));
        return result;
    }

    /**
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    @Override
    public Object getParent(Object element) {
        Object parent = null;
        if (element instanceof ConceptPath) {
            parent = ((ConceptPath) element).getParent();
        }
        return parent;
    }

    /**
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    @Override
    public boolean hasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof ConceptPath) {
            ConceptPath path = (ConceptPath) element;
            List<?> attributes = path.getChildren();
            if (attributes != null && attributes.size() > 0) {
                hasChildren = true;
            } else if (Xpdl2ResourcesPlugin.shouldExpandMapperFields()
                    && path.isArrayHeader()) {
                hasChildren = true;
            }
        }
        return hasChildren;
    }

    /**
     * @param inputElement
     * @return
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
