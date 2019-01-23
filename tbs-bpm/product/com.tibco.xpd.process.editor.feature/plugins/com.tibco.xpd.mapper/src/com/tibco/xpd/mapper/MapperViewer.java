/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchSite;

/**
 * This viewer wraps the Mapper UI widget. It acts as a viewer for the mapping
 * information.
 * 
 * @author scrossle
 * 
 */
public class MapperViewer extends StructuredViewer {

    /** Following Mapper menu group IDs can be used to add menu actions */
    public static final String MAPPER_MENU_GROUP_1_ID = "MapperMenuGroup1"; //$NON-NLS-1$

    public static final String MAPPER_MENU_GROUP_2_ID = "MapperMenuGroup2"; //$NON-NLS-1$

    public static final String MAPPER_MENU_GROUP_3_ID = "MapperMenuGroup3"; //$NON-NLS-1$

    public static final String MAPPER_MENU_GROUP_4_ID = "MapperMenuGroup4"; //$NON-NLS-1$

    public static final String MAPPER_DELETE_MENU_GROUP_ID =
            "MapperDeleteMenuGroup"; //$NON-NLS-1$

    /** The mapper control. */
    private Mapper mapperControl;

    /**
     * @param parent
     *            The parent component.
     */
    public MapperViewer(Composite parent) {
        this(parent, SWT.NONE);
    }

    /**
     * @param parent
     *            The parent component.
     * @param style
     *            The component style.
     */
    public MapperViewer(Composite parent, int style) {
        mapperControl = new Mapper(parent, style) {
            /**
             * @see com.tibco.xpd.mapper.Mapper#fillContextMenu(org.eclipse.jface.action.IMenuManager)
             * 
             * @param manager
             */
            @Override
            protected void fillContextMenu(IMenuManager manager) {
                super.fillContextMenu(manager);
                fillAdditionalMenuContent(manager);
            }
        };

        hookControl(mapperControl);
    }

    /**
     * @param mapper
     *            The mapper control.
     */
    public MapperViewer(Mapper mapper) {
        mapperControl = mapper;
        hookControl(mapperControl);
    }

    /**
     * @return The mapper control.
     * @see org.eclipse.jface.viewers.Viewer#getControl()
     */
    @Override
    public Control getControl() {
        return mapperControl;
    }

    /**
     * @param contentProvider
     *            The MapperContentProvider content provider object.
     * @see org.eclipse.jface.viewers.StructuredViewer#setContentProvider(org.eclipse.jface.viewers.IContentProvider)
     */
    @Override
    public void setContentProvider(IContentProvider contentProvider) {
        super.setContentProvider(contentProvider);
        MapperContentProvider mapperContentProvider;
        mapperContentProvider = (MapperContentProvider) contentProvider;
        mapperControl.setSourceContentProvider(mapperContentProvider
                .getSourceContent());
        mapperControl.setTargetContentProvider(mapperContentProvider
                .getTargetContent());
    }

    /**
     * @param provider
     *            The content provider.
     * @see org.eclipse.jface.viewers.StructuredViewer#
     *      assertContentProviderType(org.eclipse.jface.viewers.IContentProvider)
     */
    @Override
    protected void assertContentProviderType(IContentProvider provider) {
        Assert.isLegal(provider instanceof MapperContentProvider);
    }

    /**
     * @param input
     *            The new input.
     * @param oldInput
     *            The old input.
     * @see org.eclipse.jface.viewers.Viewer#inputChanged(java.lang.Object,
     *      java.lang.Object)
     */
    @Override
    protected void inputChanged(Object input, Object oldInput) {
        super.inputChanged(input, oldInput);
        if (input != null) {
            MapperViewerInput mapInput = (MapperViewerInput) input;
            MapperContentProvider mapContentProvider;
            mapContentProvider = (MapperContentProvider) getContentProvider();
            Object mappingInput = mapInput.getMappingInput();
            IStructuredContentProvider mappingContent =
                    mapContentProvider.getMappingContent();
            Object[] rawInputArray = mappingContent.getElements(mappingInput);
            Object[] mappingArray = filter(rawInputArray);
            List<Mapping> mappings = new ArrayList<Mapping>();
            for (Object o : mappingArray) {
                if (o instanceof Mapping) {
                    mappings.add((Mapping) o);
                }
            }

            MapperWidgetInput widgetInput =
                    new MapperWidgetInput(mapInput.getSourceInput(),
                            mapInput.getTargetInput(), mappings);
            mapperControl.setInput(widgetInput);
        } else {
            mapperControl.setInput(null);
        }
    }

    /**
     * @return The source tree viewer.
     */
    public TreeViewer getSourceViewer() {
        return mapperControl.getSourceViewer();
    }

    /**
     * @return The target tree viewer.
     */
    public TreeViewer getTargetViewer() {
        return mapperControl.getTargetViewer();
    }

    /**
     * @param labelProvider
     *            The mapper label provider.
     */
    public void setLabelProvider(MapperLabelProvider labelProvider) {
        mapperControl.setSourceLabelProvider(labelProvider
                .getSourceLabelProvider());
        mapperControl.setTargetLabelProvider(labelProvider
                .getTargetLabelProvider());
    }

    /**
     * @param validator
     *            The transfer validator.
     */
    public void setTransferValidator(IMappingTransferValidator validator) {
        mapperControl.setTransferValidator(validator);
    }

    /**
     * @param listener
     *            The mapping listener to add.
     */
    public void addMappingListener(IMappingListener listener) {
        mapperControl.addMappingListener(listener);
    }

    /**
     * @param errorProvider
     *            The mapping error provider.
     */
    public void setErrorProvider(IErrorProvider errorProvider) {
        mapperControl.setErrorProvider(errorProvider);
    }

    /**
     * @param element
     *            The element to find.
     * @return null, not supported.
     * @see org.eclipse.jface.viewers.StructuredViewer#doFindInputItem(java.lang.Object)
     */
    @Override
    protected Widget doFindInputItem(Object element) {
        return null;
    }

    /**
     * @param element
     *            The element to find.
     * @return null, not supported.
     * @see org.eclipse.jface.viewers.StructuredViewer#doFindItem(java.lang.Object)
     */
    @Override
    protected Widget doFindItem(Object element) {
        return null;
    }

    /**
     * @param item
     *            The control to update.
     * @param element
     *            The element to update.
     * @param fullMap
     *            true if mappings are added or removed.
     * @see org.eclipse.jface.viewers.StructuredViewer#doUpdateItem(org.eclipse.swt.widgets.Widget,
     *      java.lang.Object, boolean)
     */
    @Override
    protected void doUpdateItem(Widget item, Object element, boolean fullMap) {
    }

    /**
     * @return The selection.
     * @see org.eclipse.jface.viewers.StructuredViewer#getSelectionFromWidget()
     */
    @Override
    protected List<?> getSelectionFromWidget() {
        ISelection selection = mapperControl.getSelection();
        if (selection instanceof StructuredSelection) {
            StructuredSelection structured = (StructuredSelection) selection;
            return structured.toList();
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @see org.eclipse.jface.viewers.StructuredViewer#handleDispose(org.eclipse.swt.events.DisposeEvent)
     * 
     * @param event
     */
    @Override
    protected void handleDispose(DisposeEvent event) {
        mapperControl.dispose();
        super.handleDispose(event);
    }

    /**
     * @param element
     *            The element to refresh.
     * @see org.eclipse.jface.viewers.StructuredViewer#internalRefresh(java.lang.Object)
     */
    @Override
    protected void internalRefresh(Object element) {
        if (element instanceof MapperViewerInput) {
            MapperViewerInput viewInput = (MapperViewerInput) element;
            if (viewInput.getSourceInput() != null) {
                refreshSource(viewInput.getSourceInput());
            }
            if (viewInput.getTargetInput() != null) {
                refreshTarget(viewInput.getTargetInput());
            }
            if (viewInput.getMappingInput() != null) {
                refreshMappings(viewInput.getMappingInput());
            }
        } else {
            refreshSource(null);
            refreshTarget(null);
            refreshMappings(null);
        }

    }

    public void setRedraw(boolean redraw) {
        if (mapperControl != null && !mapperControl.isDisposed()) {
            mapperControl.setRedraw(redraw);
        }
    }

    /**
     * @param targetInput
     *            The target input.
     */
    private void refreshTarget(Object targetInput) {
        mapperControl.targetViewerRefresh(null);

    }

    /**
     * @param sourceInput
     *            The source input.
     */
    private void refreshSource(Object sourceInput) {
        mapperControl.sourceViewerRefresh(null);

    }

    /**
     * @param filter
     *            The filter to add to the target tree.
     */
    public void targetViewerAddFilter(ViewerFilter filter) {
        mapperControl.targetViewerAddFilter(filter);
    }

    /**
     * @param filter
     *            The filter to add to the source tree.
     */
    public void sourceViewerAddFilter(ViewerFilter filter) {
        mapperControl.sourceViewerAddFilter(filter);
    }

    /**
     * @param filter
     *            The filter to remove from the target tree.
     */
    public void targetViewerRemoveFilter(ViewerFilter filter) {
        mapperControl.sourceViewerAddFilter(filter);
    }

    /**
     * @param filter
     *            The filter to remove from the source tree.
     */
    public void sourceViewerRemoveFilter(ViewerFilter filter) {
        mapperControl.sourceViewerAddFilter(filter);
    }

    /**
     * @param mappingInput
     *            The mapping input.
     */
    private void refreshMappings(Object mappingInput) {
        if (getInput() instanceof MapperViewerInput) {
            MapperViewerInput mapInput = (MapperViewerInput) getInput();

            if (mapInput.getMappingInput() != null) {
                MapperContentProvider mapContentProvider;
                mapContentProvider =
                        (MapperContentProvider) getContentProvider();
                List<?> newMappings =
                        Arrays.asList(mapContentProvider.getMappingContent()
                                .getElements(mapInput.getMappingInput()));

                List<Mapping> oldMappings =
                        Arrays.asList(mapperControl.getMapping());
                for (Object iter : newMappings) {
                    Mapping mapping = (Mapping) iter;
                    if (!oldMappings.contains(mapping)) {
                        mapperControl.addMapping(mapping);
                    }
                }
                for (Mapping mapping : oldMappings) {
                    if (!newMappings.contains(mapping)) {
                        mapperControl.removeMapping(mapping);
                    }
                }
            }
        }
    }

    /**
     * @param element
     *            The element to reveal.
     * @see org.eclipse.jface.viewers.StructuredViewer#reveal(java.lang.Object)
     */
    @Override
    public void reveal(Object element) {
    }

    /**
     * @param selection
     *            The selection.
     * @param reveal
     *            true to reveal.
     * @see org.eclipse.jface.viewers.StructuredViewer#setSelectionToWidget(java.util.List,
     *      boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void setSelectionToWidget(List selection, boolean reveal) {
    }

    /**
     * @param transform
     *            The transform section to use.
     */
    public void setTransformSection(ITransformSection transform) {
        mapperControl.setTransformSection(transform);
    }

    /**
     * 
     */
    public void update() {
        mapperControl.updateMarkers();
    }

    /**
     * @param id
     *            The mapper id.
     */
    public void setId(String id) {
        mapperControl.setId(id);
    }

    /**
     * You must set the workbench site if you wish to contribute to the mapper
     * menu.
     * 
     * @param site
     *            The site.
     */
    public void setSite(IWorkbenchSite site) {
        mapperControl.setSite(site);
    }

    /**
     * Sets the cell modifier for the source tree.
     * 
     * @param modifier
     *            The cell modifier to set.
     */
    public void setSourceCellModifier(ICellModifier modifier) {
        getSourceViewer().setCellModifier(modifier);
    }

    /**
     * @param editingSupport
     *            The editing support.
     */
    public void setSourceEditingSupport(EditingSupport editingSupport) {
        mapperControl.setSourceEditingSupport(editingSupport);
    }

    public void setDeleteDisable(boolean disable) {
        mapperControl.setDeleteDisabled(disable);
    }

    /**
     * This can be used to add menu actions to the given manager for the mapper
     * context menu
     * 
     * @param manager
     */
    protected void fillAdditionalMenuContent(IMenuManager manager) {
    }

    /**
     * @return The selected mappings.
     */
    public Collection<Mapping> getSelectedMappings() {
        if (mapperControl != null && !mapperControl.isDisposed()) {
            return new ArrayList<>(mapperControl.getSelectedMappings());
        }
        return Collections.emptyList();
    }

    /**
     * @return the mappings
     */
    public Collection<Mapping> getMappings() {
        if (mapperControl != null && !mapperControl.isDisposed()) {
            return new ArrayList<>(mapperControl.getMappings());
        }
        return Collections.emptyList();
    }
}
