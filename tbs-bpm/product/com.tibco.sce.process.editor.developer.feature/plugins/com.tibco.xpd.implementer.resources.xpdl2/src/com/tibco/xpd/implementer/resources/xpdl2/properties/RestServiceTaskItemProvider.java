/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItemFactory;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestPayloadContainerTreeItem;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.xpdl2.Activity;

/**
 * REST Service mapping content provider.
 * 
 * @author jarciuch
 * @since 1 Apr 2015
 */
public class RestServiceTaskItemProvider implements ITreeContentProvider {

    private Object[] topLevelChildren = null;

    private final ConceptContentProvider conceptContentProvider;

    private final MappingDirection direction;

    /**
     * 
     */
    public RestServiceTaskItemProvider(MappingDirection direction) {
        this.direction = direction;
        final boolean isSourceMapping = true;
        conceptContentProvider =
                new ConceptContentProvider(direction, isSourceMapping, true);
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
        // System.out.println("Input changed: " + newInput);
        topLevelChildren = null;

    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof Activity) {
            Activity activity = (Activity) parentElement;
            RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
            if (rsta.isCatchEvent(activity)) {
                // May be a Catch for a REST fault.
                Activity thrower = rsta.getThrowerActivity(activity);
                if (thrower != null) {
                    if (topLevelChildren == null) {
                        ArrayList<RestMapperTreeItem> children =
                                getTopServiceCatchChildren(activity, thrower);

                        topLevelChildren = children.toArray();
                    }
                }
                return topLevelChildren;
            } else {
                Method rsoMethod = rsta.getRSOMethod(activity);
                if (rsoMethod != null) {
                    if (topLevelChildren == null) {
                        ArrayList<RestMapperTreeItem> children =
                                (direction == MappingDirection.IN) ? getTopServiceInChildren(activity)
                                        : getTopServiceOutChildren(activity);

                        topLevelChildren = children.toArray();
                    }
                    return topLevelChildren;
                }
            }

        } else if (parentElement instanceof RestMapperTreeItem) {
            return ((RestMapperTreeItem) parentElement).getChildren().toArray();
        } else if (parentElement instanceof ConceptPath) {
            return conceptContentProvider.getChildren(parentElement);
        }
        return new Object[0];
    }

    private ArrayList<RestMapperTreeItem> getTopServiceInChildren(
            Activity activity) {
        ArrayList<RestMapperTreeItem> children =
                new ArrayList<RestMapperTreeItem>();

        RestMapperTreeItemFactory factory =
                RestMapperTreeItemFactory.getInstance();

        // Add Path, Query and Header params containers.
        children.add(factory.createParamContainerTreeItem(activity,
                ParameterStyle.PATH,
                direction));
        children.add(factory.createParamContainerTreeItem(activity,
                ParameterStyle.QUERY,
                direction));
        children.add(factory.createParamContainerTreeItem(activity,
                ParameterStyle.HEADER,
                direction));

        // Payload container.
        children.add(factory
                .createPayloadContainerTreeItem(activity, direction));
        return children;
    }

    private ArrayList<RestMapperTreeItem> getTopServiceOutChildren(
            Activity activity) {
        ArrayList<RestMapperTreeItem> children =
                new ArrayList<RestMapperTreeItem>();

        RestMapperTreeItemFactory factory =
                RestMapperTreeItemFactory.getInstance();

        // Add Path, Query and Header params containers.
        children.add(factory.createParamContainerTreeItem(activity,
                ParameterStyle.HEADER,
                direction));

        // Payload container.
        children.add(factory
                .createPayloadContainerTreeItem(activity, direction));
        return children;
    }

    private ArrayList<RestMapperTreeItem> getTopServiceCatchChildren(
            Activity activity, Activity thrower) {
        ArrayList<RestMapperTreeItem> children =
                new ArrayList<RestMapperTreeItem>();

        RestMapperTreeItemFactory factory =
                RestMapperTreeItemFactory.getInstance();
        String code = factory.getCaughtErrorCode(activity);

        // Payload container.
        RestMapperTreeItem item =
                factory.createCatchPayloadContainerTreeItem(activity,
                        thrower,
                        code);
        if (item != null) {
            children.add(item);
        }
        return children;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Object getParent(Object element) {
        if (element instanceof RestMapperTreeItem) {
            return ((RestMapperTreeItem) element).getParent();
        } else if (element instanceof ConceptPath) {
            Object parent = conceptContentProvider.getParent(element);
            if (parent == null) {
                Object item = ((ConceptPath) element).getItem();
                if (item instanceof RestPayloadContainerTreeItem) {
                    return item;
                }
            }
            return parent;
        }
        return null;
    }

    /**
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof RestMapperTreeItem) {
            return ((RestMapperTreeItem) element).hasChildren();
        }
        return getChildren(element).length != 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        // Do nothing!
    }

}
