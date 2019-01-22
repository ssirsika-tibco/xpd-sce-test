/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Immmutable content provider for the mapper is composed of 3 content
 * providers. Providers for:
 * <ul>
 * <li>sourceContent - the tree on the LHS of the mapper.</li>
 * <li>targetContent - the tree on the RHS of the mapper.</li>
 * <li>mappingContent - array of mappings from one to the other.</li>
 * </ul>
 * 
 * @author scrossle
 * 
 */
public final class MapperContentProvider implements IContentProvider {

    /**
     * Problem Marker Additional Info for data mapping problem Target object
     * uri.
     * <p>
     * The target of data mapping be added to Issue additional info with this as
     * the key and the URI of the target as the value.
     * <p>
     * With this in place the mapper section will show a problem marker on the
     * target end of the mapping line.
     */
    public static final String DATAMAPPING_TARGET_URI_ISSUEINFO =
            "com.tibco.xpd.mapper.type.target"; //$NON-NLS-1$

    /**
     * Problem Marker Additional Info for data mapping problem Sourece object
     * uri.
     * <p>
     * The source of data mapping be added to Issue additional info with this as
     * the key and the URI of the target as the value.
     * <p>
     * With this in place the mapper section will show a problem marker on the
     * source end of the mapping line.
     */
    public static final String DATAMAPPING_SOURCE_URI_ISSUEINFO =
            "com.tibco.xpd.mapper.type.source"; //$NON-NLS-1$

    /**
     * Problem Marker Additional Info for problems against mapping target tree
     * content.
     * <p>
     * The target of a mapping be added to Issue additional info with this as
     * the key and the URI of the target as the value.
     * <p>
     * With this in place the mapper section will show a problem marker in the
     * target data tree.
     */
    public static final String TARGET_URI_ISSUEINFO =
            "com.tibco.xpd.mapper.type.target.tree"; //$NON-NLS-1$

    /**
     * Problem Marker Additional Info for problems against mapping source tree
     * content.
     * <p>
     * The source of a mapping be added to Issue additional info with this as
     * the key and the URI of the source as the value.
     * <p>
     * With this in place the mapper section will show a problem marker in the
     * source data tree.
     */
    public static final String SOURCE_URI_ISSUEINFO =
            "com.tibco.xpd.mapper.type.source.tree"; //$NON-NLS-1$

    /**
     * Problem Marker Additional Info for problems that gives direction context
     * to the above source/targets.
     * <p>
     * This is optional but IF supplied it will allow the problem markers in
     * source/content trees in mapper NOT to be added to source/target objects
     * of the same name in different sections altogether.
     * <p>
     * Prior to this, whether a problem marker was shown on source/target
     * content was identified by the Actvity-URI and objectPath (i.e.
     * conceptPath name etc) alone.
     * <p>
     * That means if Mapping-In section on a task has a target with exactly the
     * same "path" (i.e. parameter name) as a target in the Mapping-Out section
     * then both sections will show a problem marker even though it only belongs
     * on one of them.
     */
    public static final String MAPPING_DIRECTION_ISSUEINFO =
            "com.tibco.xpd.mapper.mappingDirection"; //$NON-NLS-1$

    /** The source content provider. */
    private ITreeContentProvider sourceContent;

    /** The target content provider. */
    private ITreeContentProvider targetContent;

    /** The mapping content provider. */
    private IStructuredContentProvider mappingContent;

    /**
     * @param sourceContent
     *            The source content provider.
     * @param targetContent
     *            The target content provider.
     * @param mappingContent
     *            The mapping content provider.
     */
    public MapperContentProvider(ITreeContentProvider sourceContent,
            ITreeContentProvider targetContent,
            IStructuredContentProvider mappingContent) {
        super();
        this.sourceContent = sourceContent;
        this.targetContent = targetContent;
        this.mappingContent = mappingContent;
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose() {
        sourceContent.dispose();
        targetContent.dispose();
        mappingContent.dispose();
    }

    /**
     * @param viewer
     *            The viewer.
     * @param oldInput
     *            The old input.
     * @param newInput
     *            The new input.
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        MapperViewer mapViewer = (MapperViewer) viewer;
        MapperViewerInput oldMapInput =
                oldInput != null ? (MapperViewerInput) oldInput
                        : MapperViewerInput.NULL_INPUT;
        MapperViewerInput newMapInput =
                newInput != null ? (MapperViewerInput) newInput
                        : MapperViewerInput.NULL_INPUT;
        sourceContent.inputChanged(mapViewer.getSourceViewer(),
                oldMapInput.getSourceInput(),
                newMapInput.getSourceInput());
        targetContent.inputChanged(mapViewer.getTargetViewer(),
                oldMapInput.getTargetInput(),
                newMapInput.getTargetInput());
        mappingContent.inputChanged(viewer,
                oldMapInput.getMappingInput(),
                newMapInput.getMappingInput());

    }

    /**
     * @return the mappingContent
     */
    public IStructuredContentProvider getMappingContent() {
        return mappingContent;
    }

    /**
     * @return the sourceContent
     */
    public ITreeContentProvider getSourceContent() {
        return sourceContent;
    }

    /**
     * @return the targetContent
     */
    public ITreeContentProvider getTargetContent() {
        return targetContent;
    }

}
