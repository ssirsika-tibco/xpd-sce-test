/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.XYAnchor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.IPageSite;

import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.misc.AbstractTreeItemToolTipProvider;
import com.tibco.xpd.ui.projectexplorer.decorator.OverlayImageDescriptor;

/**
 * Widget to allow mappings between arbitrary objects. Objects for the source
 * and target trees should be provided by the user via ITreeContentProvider
 * classes.
 * 
 * @author nwilson
 */
public class Mapper extends Composite implements ISelectionProvider {

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /** The corner radius of the transform panel. */
    private static final int TRANSFORM_PANEL_RADIUS = 10;

    /** Default width of the source tree. */
    private static final int DEFAULT_SOURCE_WIDTH = 200;

    /** Default width of the source tree. */
    private static final int DEFAULT_CROSSOVER_WIDTH = 220;

    /** Offset of first bend point from the right of the tree control. */
    private static final int BEND_POINT_1_OFFSET = 40;

    /** Offset distance to the left of the target item. */
    private static final int TARGET_X_OFFSET = 32;

    /** Offset distance to the right of bend point 2. */
    private static final int TRANSFORM_X_OFFSET = 16;

    /** Offset distance to the right of bend point 2. */
    private static final int ERROR_X_OFFSET = 8;

    /** Width of the link lines. */
    private static final int LINK_WIDTH = 2;

    /** The width of the border round the transform icon. */
    private static final int TRANSFORM_BORDER_WIDTH = 1;

    /** Offset to place link in centre of link end. */
    private static final int LINK_END_Y_OFFSET = 4;

    /** The size width of the link end circle figure. */
    private static final int LINK_END_WIDTH = 9;

    private static final int LINK_END_DECORATION_SIZE = LINK_END_WIDTH - 2;

    /** The filtered mapping alpha value. */
    public static final int FILTERED = 45;

    /** The menu id. */
    private static final String MENU_ID = "mapperControlMenu"; //$NON-NLS-1$

    /**
     * Cache of error-decorated icons (original image to image combined with
     * problem marker)
     */
    private static Map<Image, Image> errorImageRegistry =
            new HashMap<Image, Image>();

    private static Image errorDecoratorImage = MapperActivator.getDefault()
            .getImageCache().getImage(ImageCache.ERROR);

    /** Cache of error-decorated icons (original image, to combined image) */
    private static Map<Image, Image> warningImageRegistry =
            new HashMap<Image, Image>();

    private static Image warningDecoratorImage = MapperActivator.getDefault()
            .getImageCache().getImage(ImageCache.WARNING);

    //
    // In order for polyline connections to work properly they have to have
    // an anchor (even if the connection router doesn't need it!).
    //
    private XYAnchor dummyAnchor = new XYAnchor(new Point(-1, -1));

    /** The source tree view. */
    private TreeViewer sourceViewer;

    /** separator line above crossover figure. */
    private Label crossOverSeparator;

    /** Composite for drawing the crossover lines. */
    private Crossover crossover;

    /** The target tree view. */
    private TreeViewer targetViewer;

    /** A list of Mapping objects. */
    private List<Mapping> mappings;

    /** A list of Mapping objects. */
    private Collection<Mapping> mappingSelection;

    /** The registered mapping listener. */
    private Collection<IMappingListener> mappingListeners;

    /** Validator for the target object. */
    private IMappingTransferValidator targetValidator;

    /** The transform section to use. */
    private ITransformSection transformSection;

    /** The error provider. */
    private IErrorProvider errorProvider;

    /** Flag to turn visual link filtering on. */
    private boolean filteringOn;

    /** Supported transfer types. */
    private Transfer[] transferTypes;

    /** The currently active control. */
    private Object active;

    /** Flag to ignore selection events. */
    private boolean ignoreSelectionEvents;

    /** Context menu for links. */
    private Menu menu;

    /** The id against which events are generated. */
    private String id;

    /** A reference to the plugin activator. */
    private MapperActivator activator;

    /** The workbench site containing the mapper (if set). */
    private IWorkbenchSite site;

    /** The menu manager. */
    private MenuManager menuManager;

    /** The selection listeners. */
    private Set<ISelectionChangedListener> selectionListeners;

    /** The source column. */
    private TreeViewerColumn sourceColumn;

    /** Delete Disabled flag. */
    private boolean deleteDisabled = false;

    // XPD-5414
    /**
     * Track status [shown/hidden] of unmapped contents
     */
    private boolean showOnlyMappedContents = false;

    /**
     * Target Tree filter to show/hide mapped content.
     */
    private ViewerFilter targetMappedContentFilter =
            new MappedDataFilter(false).buildFilter();

    /**
     * Source Tree filter to show/hide mapped content
     */
    private ViewerFilter sourceMappedContentFilter = new MappedDataFilter(true)
            .buildFilter();

    private MapperLayout mapperLayout;

    /**
     * Check Button used to hide or Show unmapped data in the Mapper.
     */
    private Button showOrHideUnMappedContentsButton;

    /**
     * MR 39782: Serious Studio Mapper control performance problems.
     * 
     * Little data class for findItem() contentData -> TreeItem caching
     * 
     * 
     * @author aallway
     * @since 3.3 (23 Dec 2009)
     */
    private static class TreeViewerTreeItemCache {
        TreeViewer treeViewer;

        Map<Object, TreeItem> treeItemCache = new HashMap<Object, TreeItem>();

        /**
         * @param treeViewer
         */
        public TreeViewerTreeItemCache(TreeViewer treeViewer) {
            super();
            this.treeViewer = treeViewer;
        }

        public void reset() {
            treeItemCache = new HashMap<Object, TreeItem>();
            return;
        }

    }

    /**
     * MR 39782: Serious Studio Mapper control performance problems -
     * 
     * cache of data->TreeItem caches for source/target viewer
     */
    private Map<TreeViewer, TreeViewerTreeItemCache> treeViewerItemCaches =
            new HashMap<TreeViewer, TreeViewerTreeItemCache>();

    /**
     * MR 39782: Serious Studio Mapper control performance problems -
     * 
     * The parent tree item cache (mapping content data object to the closet
     * parent object that has had a TreeItem cached)
     */
    private Map<TreeViewer, TreeViewerTreeItemCache> treeViewerParentItemCaches =
            new HashMap<TreeViewer, TreeViewerTreeItemCache>();

    /**
     * MR 39782: Serious Studio Mapper control performance problems - cache
     * problem severity for any given element in sourceTree.
     */
    private Map<Object, ErrorSeverity> sourceErrorSeverityCache =
            new HashMap<Object, ErrorSeverity>();

    private Map<Object, String> sourceErrorMessagesCache =
            new HashMap<Object, String>();

    /**
     * MR 39782: Serious Studio Mapper control performance problems - cache
     * problem severity for any given element in targetTree.
     */
    private Map<Object, ErrorSeverity> targetErrorSeverityCache =
            new HashMap<Object, ErrorSeverity>();

    private Map<Object, String> targetErrorMessagesCache =
            new HashMap<Object, String>();

    private boolean loggingOn = false;

    /*
     * Use MapperFilteredTree's that draw a separator line above tree control to
     * match the crossoverSeparator.
     */
    private MapperFilteredTree sourceFilteredTree;

    private MapperFilteredTree targetFilteredTree;

    private Label mappedContentSeperatorPre;

    private Label mappedContentSeperatorPost;

    /**
     * @param id
     *            The mapper id to listen to.
     * @param listener
     *            The listener.
     */
    public static void addMappingListener(String id, MapperItemListener listener) {
        MapperActivator.getDefault().addMappingListener(id, listener);
    }

    /**
     * @param id
     *            The id to stop listening to.
     * @param listener
     *            The listener to remove.
     */
    public static void removeMappingListener(String id,
            MapperItemListener listener) {
        MapperActivator.getDefault().removeMappingListener(id, listener);
    }

    /**
     * @param parent
     *            The parent composite for this widget.
     * @param style
     *            The style bits for this widget.
     */
    public Mapper(final Composite parent, final int style) {
        super(parent, style);

        setBackground(ColorConstants.white);

        activator = MapperActivator.getDefault();
        filteringOn = true;
        mappings = new ArrayList<Mapping>();
        mappingListeners = new HashSet<IMappingListener>();
        mappingSelection = new HashSet<Mapping>();
        selectionListeners = new HashSet<ISelectionChangedListener>();

        mapperLayout = new MapperLayout();
        setLayout(mapperLayout);
        transferTypes =
                new Transfer[] { LocalTransfer.getInstance(),
                        new SerializableTransferType() };

        sourceFilteredTree =
                new MapperFilteredTree(this, SWT.FLAT | SWT.MULTI,
                        new PatternFilter());

        sourceViewer = sourceFilteredTree.getViewer();
        sourceFilteredTree
                .addFilterTextChangeListener(filterTextChangeListener);
        sourceFilteredTree.setBackground(parent.getDisplay()
                .getSystemColor(SWT.COLOR_LIST_BACKGROUND));

        ColumnViewerEditorActivationStrategy editorActivationStrategy;
        editorActivationStrategy =
                new ColumnViewerEditorActivationStrategy(sourceViewer);
        TreeViewerEditor.create(sourceViewer,
                editorActivationStrategy,
                ColumnViewerEditor.DEFAULT);
        sourceColumn = new TreeViewerColumn(sourceViewer, SWT.LEFT);
        sourceColumn.getColumn().setWidth(DEFAULT_SOURCE_WIDTH);
        // XPD-5414- Button to enable hide/show unmapped data in the mapper.

        mappedContentSeperatorPre =
                new Label(this, SWT.SEPARATOR | SWT.VERTICAL);

        showOrHideUnMappedContentsButton = new Button(this, SWT.CHECK);

        mappedContentSeperatorPost =
                new Label(this, SWT.SEPARATOR | SWT.VERTICAL);

        showOrHideUnMappedContentsButton.setBackground(ColorConstants.white);
        showOrHideUnMappedContentsButton.setText(Messages
                .getString("Mapper.ShowOnlyMappedContentButton")); //$NON-NLS-1$
        showOrHideUnMappedContentsButton
                .addSelectionListener(new SelectionListener() {

                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        select(e.getSource());
                    }

                    @Override
                    public void widgetDefaultSelected(SelectionEvent e) {

                        select(e.getSource());
                    }

                    private void select(Object object) {
                        showOnlyMappedContents(((Button) object).getSelection());
                    }
                });

        /*
         * Create a separator line for above the mapping lines cross-over
         * control (to prevent the lines from appearing to be cut off in mid air
         * when trees and hence mappings are scrolled.
         */
        crossOverSeparator = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);

        crossover = new Crossover(this, SWT.NONE);

        targetFilteredTree =
                new MapperFilteredTree(this, SWT.FLAT | SWT.MULTI,
                        new PatternFilter());

        targetFilteredTree
                .addFilterTextChangeListener(filterTextChangeListener);
        targetViewer = targetFilteredTree.getViewer();

        targetFilteredTree.setBackground(parent.getDisplay()
                .getSystemColor(SWT.COLOR_LIST_BACKGROUND));

        targetViewer.setColumnProperties(new String[] { "name" }); //$NON-NLS-1$
        CellEditor targetEditor = new TextCellEditor(targetViewer.getTree());
        targetViewer.setCellEditors(new CellEditor[] { targetEditor });

        sourceViewer.addDragSupport(DND.DROP_LINK,
                transferTypes,
                new DragAdapter(sourceViewer));
        targetViewer.addDropSupport(DND.DROP_LINK,
                transferTypes,
                new DropAdapter(targetViewer));
        targetViewer.addDragSupport(DND.DROP_LINK,
                transferTypes,
                new DragAdapter(targetViewer));
        sourceViewer.addDropSupport(DND.DROP_LINK,
                transferTypes,
                new DropAdapter(sourceViewer));

        configureListeners();

        menuManager = new MenuManager("", MENU_ID); //$NON-NLS-1$
        menuManager.setRemoveAllWhenShown(true);
        menuManager.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                fillContextMenu(manager);
            }
        });
        menu = menuManager.createContextMenu(this);

        setMenu(menu);
        sourceViewer.getTree().setMenu(menu);
        targetViewer.getTree().setMenu(menu);
        crossover.setMenu(menu);

        new TreeItemProblemTooltipProvider(sourceViewer.getTree(), true);
        new TreeItemProblemTooltipProvider(targetViewer.getTree(), false);

        /*
         * MR 39782: Serious Studio Mapper control performance problems -
         * 
         * The parent tree item cache (mapping content data object to the
         * closest parent object that has had a TreeItem cached) MUST be reset
         * whenever an expand is performed - in order that next time 'closest
         * parent ancestor' is got (findParentItem()) the parent will be
         * recalculated.
         */
        sourceViewer.getTree()
                .addTreeListener(new ParentTreeItemCacheResetListener(
                        sourceViewer));
        targetViewer.getTree()
                .addTreeListener(new ParentTreeItemCacheResetListener(
                        targetViewer));

    }

    /**
     * This is a listener to the filter text box on the mapper. This listener is
     * added so that when any text is entered in the text box, the tree should
     * expand to the size according to its content.
     * 
     * This is applied only to the sourceViewer because there is a column added
     * programatically so that it has an editor for Script tasks and enum
     * drop-downs. The target viewer doesn't have anything of the sort.
     * 
     * XPD-1278 - has a picture which depicts the problems exactly for which the
     * listeners were added.
     */
    private ITextChangedListener filterTextChangeListener =
            new ITextChangedListener() {

                @Override
                public void update(String filterString) {
                    layout(true);
                }
            };

    /**
     * @param editingSupport
     *            The editing support.
     */
    public void setSourceEditingSupport(EditingSupport editingSupport) {
        sourceColumn.setEditingSupport(editingSupport);
    }

    /**
     * You must set the workbench site if you wish to contribute to the mapper
     * menu. This method should only be called once. Calling it a second time
     * will do nothing.
     * 
     * @param site
     *            The site.
     */
    public void setSite(IWorkbenchSite site) {
        if (this.site == null) {
            this.site = site;
            if (site != null) {
                if (site instanceof IPageSite) {
                    ((IPageSite) site).registerContextMenu(MENU_ID,
                            menuManager,
                            this);
                } else if (site instanceof IWorkbenchPartSite) {
                    ((IWorkbenchPartSite) site)
                            .registerContextMenu(menuManager, this);
                }
            }
        }
    }

    /**
     * @param listener
     *            The listener to add.
     * @see org.eclipse.jface.viewers.ISelectionProvider#
     *      addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
     */
    @Override
    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        selectionListeners.add(listener);
    }

    /**
     * @return The current selection.
     * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
     */
    @Override
    public ISelection getSelection() {
        ISelection selection = null;
        try {
            if (active == sourceViewer) {
                selection = sourceViewer.getSelection();
            } else if (active == targetViewer) {
                selection = targetViewer.getSelection();
            }
        } catch (NullPointerException e) {
            // AbstractTreeViewer.getSelection can sometimes throw a
            // NullPointerException. Ignore it.
        }

        return selection;
    }

    /**
     * @param listener
     *            The listener to remove.
     * @see org.eclipse.jface.viewers.ISelectionProvider#
     *      removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
     */
    @Override
    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        selectionListeners.remove(listener);
    }

    /**
     * @param selection
     *            The selection to set.
     * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
     */
    @Override
    public void setSelection(ISelection selection) {
    }

    /**
     * @param manager
     *            The menu manager.
     */
    protected void fillContextMenu(IMenuManager manager) {
        Action deleteAction = new Action() {
            @Override
            public boolean isEnabled() {
                return !shouldDisableDelete();
            }

            @Override
            public ImageDescriptor getImageDescriptor() {
                return MapperActivator
                        .imageDescriptorFromPlugin(MapperActivator.PLUGIN_ID,
                                ImageCache.REMOVE);
            }

            @Override
            public String getText() {
                return Messages.getString("Mapper.MenuDelete"); //$NON-NLS-1$
            }

            @Override
            public void run() {
                removeSelectedMappings();
            }
        };

        // Create and add menu groups so that actions could be added from
        // different places using the menu group IDs
        Separator menuGroup1 =
                new Separator(MapperViewer.MAPPER_MENU_GROUP_1_ID);
        manager.add(menuGroup1);
        Separator menuGroup2 =
                new Separator(MapperViewer.MAPPER_MENU_GROUP_2_ID);
        manager.add(menuGroup2);
        Separator menuGroup3 =
                new Separator(MapperViewer.MAPPER_MENU_GROUP_3_ID);
        manager.add(menuGroup3);
        Separator menuGroup4 =
                new Separator(MapperViewer.MAPPER_MENU_GROUP_4_ID);
        manager.add(menuGroup4);

        // delete group should appear at the end
        Separator deleteGroup =
                new Separator(MapperViewer.MAPPER_DELETE_MENU_GROUP_ID);
        manager.add(deleteGroup);
        manager.appendToGroup(deleteGroup.getGroupName(), deleteAction);
    }

    /**
     * @param id
     *            The id for this mapper.
     */
    public void setId(String id) {
        this.id = id;
        activator.registerMapper(id, this);
    }

    /**
     * Configures the mapper listeners.
     */
    private void configureListeners() {
        sourceViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        updateSelectionFromSource();
                        fireSourceSelectionChanged();
                        redrawTrees();
                    }
                });
        targetViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        updateSelectionFromTarget();
                        redrawTrees();
                    }
                });

        sourceViewer.getTree().addPaintListener(new PaintListener() {
            @Override
            public void paintControl(final PaintEvent e) {
                if (!e.widget.isDisposed()) {
                    drawSourceLinks(e.gc);
                }
            }
        });
        targetViewer.getTree().addPaintListener(new PaintListener() {
            @Override
            public void paintControl(final PaintEvent e) {
                if (!e.widget.isDisposed()) {
                    drawTargetLinks(e.gc);
                }
            }
        });

        /*
         * Listen to modification of the source tree filter text and refresh the
         * mappings accordingly.
         */
        sourceFilteredTree.getFilterControl()
                .addModifyListener(new ModifyListener() {
                    @Override
                    public void modifyText(ModifyEvent e) {
                        /*
                         * When the filter changes we _must_ throw away our tree
                         * item caches! Otherwise when filter is remvoed it will
                         * have a load of disposed TreeItem's in :o(
                         */
                        resetTreeItemCache(sourceViewer);
                        crossover.update();
                    }
                });

        /*
         * Listen to modification of the target tree filter text and refresh the
         * mappings accordingly.
         */
        targetFilteredTree.getFilterControl()
                .addModifyListener(new ModifyListener() {
                    @Override
                    public void modifyText(ModifyEvent e) {
                        /*
                         * When the filter changes we _must_ throw away our tree
                         * item caches! Otherwise when filter is remvoed it will
                         * have a load of disposed TreeItem's in :o(
                         */
                        resetTreeItemCache(targetViewer);
                        crossover.update();
                    }
                });

        sourceViewer.getTree().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                active = sourceViewer;
                redrawTrees();
            }
        });
        targetViewer.getTree().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                active = targetViewer;
                redrawTrees();
            }
        });
        crossover.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                active = crossover;
                redrawTrees();
            }
        });

        sourceViewer.getTree().addTreeListener(new TreeListener() {

            @Override
            public void treeCollapsed(TreeEvent e) {
                //
                // When an item is expanded in source tree then recalculate the
                // column width as the widest current tree item so that
                // horizontal scroll bar works properly).
                //
                if (e.item instanceof TreeItem) {
                    // Tell the layout to treat the item being collapsed AS
                    // expanded (it's internal flag won't get set until after it
                    // has fired all the listeners
                    final TreeItem treeItem = (TreeItem) e.item;

                    // This has to be done ASYNCH because the tree items don't
                    // get their bounds set until after the layout has
                    // completed.
                    Display.getCurrent().asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mapperLayout.overrideTreeItem = treeItem;
                                mapperLayout.overrideItemState = false;

                                layout();

                            } finally {
                                mapperLayout.overrideTreeItem = null;
                                mapperLayout.overrideItemState = false;
                            }
                        }
                    });

                }

                return;
            }

            @Override
            public void treeExpanded(TreeEvent e) {
                //
                // When an item is expanded in source tree then recalculate the
                // column width as the widest current tree item so that
                // horizontal scroll bar works properly).
                //
                if (e.item instanceof TreeItem) {
                    // Tell the layout to treat the item being expanded AS
                    // expanded (it's internal flag won't get set until after it
                    // has fired all the listeners
                    final TreeItem treeItem = (TreeItem) e.item;

                    // This has to be done ASYNCH because the tree items don't
                    // get their bounds set until after the layout has
                    // completed.
                    Display.getCurrent().asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mapperLayout.overrideTreeItem = treeItem;
                                mapperLayout.overrideItemState = true;

                                layout();

                            } finally {
                                mapperLayout.overrideTreeItem = null;
                                mapperLayout.overrideItemState = false;
                            }
                        }
                    });
                }

                return;
            }
        });

        sourceViewer.addTreeListener(new ITreeViewerListener() {
            @Override
            public void treeCollapsed(TreeExpansionEvent event) {
                redrawTrees();
            }

            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                redrawTrees();
            }
        });

        targetViewer.addTreeListener(new ITreeViewerListener() {
            @Override
            public void treeCollapsed(TreeExpansionEvent event) {
                redrawTrees();
            }

            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                redrawTrees();
            }
        });
        addControlListener(new ControlAdapter() {
            @Override
            public void controlResized(final ControlEvent e) {
                redrawTrees();
            }

        });
        sourceViewer.getTree().getVerticalBar()
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        redrawTrees();
                    }
                });
        targetViewer.getTree().getVerticalBar()
                .addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        redrawTrees();
                    }
                });
        KeyListener linkListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.character == SWT.DEL && !shouldDisableDelete()) {
                    removeSelectedMappings();
                    // XDP-5945 Prevent other listeners from actioning the same
                    // event.
                    e.doit = false;
                }
            }
        };
        sourceViewer.getTree().addKeyListener(linkListener);
        targetViewer.getTree().addKeyListener(linkListener);
        crossover.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.character == SWT.DEL && !shouldDisableDelete()) {
                    removeSelectedMappings();
                    // XDP-5945 Prevent other listeners from actioning the same
                    // event.
                    e.doit = false;
                }
            }
        });

    }

    /**
     * 
     * @return <code>true</code> if the deletion of mapping should be disabled
     *         if it is not editable or no mapping is selected at all, else
     *         return <code>false</code> if the mapping can be deleted.
     */
    private boolean shouldDisableDelete() {
        if (mappingSelection != null && !mappingSelection.isEmpty()) {

            for (Mapping mapping : mappingSelection) {
                if (!mapping.isEditable()) {
                    return true;
                }
            }
        } else {

            return true;
        }
        return false;
    }

    /**
     * Fires source selection change events.
     */
    protected void fireSourceSelectionChanged() {
        if (id != null) {
            ISelection selection = null;
            try {
                selection = sourceViewer.getSelection();
            } catch (NullPointerException e) {
                // AbstractTreeViewer.getSelection can sometimes throw a
                // NullPointerException. Ignore it.
            }
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection structured =
                        (IStructuredSelection) selection;
                Object[] sourceItems = structured.toArray();
                activator.fireSourceSelectionChanged(id, this, sourceItems);
            }
        }
        ISelection selection = getSelection();
        if (selection != null) {
            SelectionChangedEvent event =
                    new SelectionChangedEvent(this, selection);
            for (ISelectionChangedListener listener : selectionListeners) {
                listener.selectionChanged(event);
            }
        }
    }

    /**
     * Update the source viewer selection from the target.
     */
    protected void updateSelectionFromSource() {
        if (!ignoreSelectionEvents) {
            ignoreSelectionEvents = true;

            ISelection selection = sourceViewer.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection structured =
                        (IStructuredSelection) selection;

                /*
                 * Sid XPD-7399 : Found that we could get same object in
                 * selection list multiple times. Because mappingSelection
                 * starts life as a hashSet and then it was reset as an
                 * ArrayList here!
                 */
                List<?> sourceItems = structured.toList();
                Set<Object> targetItems = new HashSet<Object>();
                Collection<Mapping> crossoverItems = new HashSet<Mapping>();

                for (Mapping mapping : mappings) {
                    if (sourceItems.contains(mapping.getSource())) {
                        targetItems.add(mapping.getTarget());
                        crossoverItems.add(mapping);
                    }
                }
                structured = new StructuredSelection(targetItems);
                targetViewer.setSelection(structured);
                mappingSelection = crossoverItems;
            }

            ignoreSelectionEvents = false;
        }
    }

    @Override
    public void setRedraw(boolean redraw) {
        if (!sourceViewer.getTree().isDisposed()) {
            sourceViewer.getTree().setRedraw(redraw);
        }
        if (!targetViewer.getTree().isDisposed()) {
            targetViewer.getTree().setRedraw(redraw);
        }
    }

    /**
     * Update the source viewer selection from the target.
     */
    protected void updateSelectionFromCrossover() {
        if (!ignoreSelectionEvents) {
            ignoreSelectionEvents = true;

            List<Object> sourceItems = new ArrayList<Object>();
            List<Object> targetItems = new ArrayList<Object>();
            for (Mapping mapping : mappingSelection) {
                sourceItems.add(mapping.getSource());
                targetItems.add(mapping.getTarget());
            }
            ISelection sourceSelection = new StructuredSelection(sourceItems);
            ISelection targetSelection = new StructuredSelection(targetItems);
            sourceViewer.setSelection(sourceSelection);
            targetViewer.setSelection(targetSelection);

            ignoreSelectionEvents = false;
        }
    }

    /**
     * Update the source viewer selection from the target.
     */
    protected void updateSelectionFromTarget() {
        if (!ignoreSelectionEvents) {
            ignoreSelectionEvents = true;

            ISelection selection = targetViewer.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection structured =
                        (IStructuredSelection) selection;

                /*
                 * Sid XPD-7399 : Found that we could get same object in
                 * selection list multiple times. Because mappingSelection
                 * starts life as a hashSet and then it was reset as an
                 * ArrayList here!
                 */
                List<?> sourceItems = structured.toList();
                Set<Object> targetItems = new HashSet<Object>();
                Collection<Mapping> crossoverItems = new HashSet<Mapping>();

                for (Mapping mapping : mappings) {
                    if (sourceItems.contains(mapping.getTarget())) {
                        targetItems.add(mapping.getSource());
                        crossoverItems.add(mapping);
                    }
                }
                structured = new StructuredSelection(targetItems);
                sourceViewer.setSelection(structured);
                mappingSelection = crossoverItems;
            }

            ignoreSelectionEvents = false;
        }
    }

    /**
     * @param contentProvider
     *            The source content provider.
     */
    public void setSourceContentProvider(
            final ITreeContentProvider contentProvider) {
        sourceViewer.setContentProvider(contentProvider);
    }

    /**
     * @param contentProvider
     *            The target content provider.
     */
    public void setTargetContentProvider(
            final ITreeContentProvider contentProvider) {
        targetViewer.setContentProvider(contentProvider);
    }

    /**
     * @param labelProvider
     *            The source label provider.
     */
    public void setSourceLabelProvider(final ILabelProvider labelProvider) {
        sourceViewer.setLabelProvider(new SourceOrTargetLabelWrapper(
                labelProvider, true));
    }

    /**
     * @param labelProvider
     *            The target label provider.
     */
    public void setTargetLabelProvider(final ILabelProvider labelProvider) {
        targetViewer.setLabelProvider(new SourceOrTargetLabelWrapper(
                labelProvider, false));
    }

    /**
     * @param input
     *            The input objects.
     */
    public void setInput(MapperWidgetInput input) {
        /*
         * MR 39782: Serious Studio Mapper control performance problems.
         * 
         * Although the fact that it seems TreeViewer will preserve TreeItem for
         * any given content data until the input is reset, we will be extra
         * sure that we're not caching old TreeItems by mistake and throw cache
         * away before refresh.
         * 
         * Should still be a massive performance improvement anyway!.
         */
        resetTreeItemCache(sourceViewer);
        resetTreeItemCache(targetViewer);

        sourceViewer.setInput(input.getSourceInput());
        targetViewer.setInput(input.getTargetInput());

        mappings = input.getMappings();
        redrawTrees();
        crossover.refresh();
    }

    /**
     * All mapper inputs.
     * 
     * @return Input.
     */
    public MapperWidgetInput getInput() {
        return new MapperWidgetInput(sourceViewer.getInput(),
                targetViewer.getInput(), mappings);
    }

    /**
     * @return The map of source objects to target objects.
     */
    public Mapping[] getMapping() {
        Mapping[] mappingArray = new Mapping[mappings.size()];
        mappings.toArray(mappingArray);
        return mappingArray;
    }

    /**
     * @param mapping
     *            The new mapping.
     */
    public void addMapping(final Mapping mapping) {
        mappings.add(mapping);
        if (active == sourceViewer) {
            ISelection selection = sourceViewer.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection structured =
                        (IStructuredSelection) selection;
                List<?> sourceItems = structured.toList();
                if (sourceItems.contains(mapping.getSource())) {
                    mappingSelection.add(mapping);
                }
            }
        } else if (active == targetViewer) {
            ISelection selection = targetViewer.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection structured =
                        (IStructuredSelection) selection;
                List<?> targetItems = structured.toList();
                if (targetItems.contains(mapping.getTarget())) {
                    mappingSelection.add(mapping);
                }
            }
        }
        crossover.refresh();
        redrawTrees();
    }

    /**
     * @param mapping
     *            Mapping to remove.
     */
    public void removeMapping(final Mapping mapping) {
        mappingSelection.remove(mapping);
        mappings.remove(mapping);
        crossover.refresh();
        redrawTrees();
    }

    /**
     * Removes all selected mappings.
     */
    private void removeSelectedMappings() {
        Collection<Mapping> toRemove = new ArrayList<Mapping>(mappingSelection);
        for (Mapping mapping : toRemove) {
            fireMappingRemoved(mapping);
            // Set the source viewer selection as the current selection
            // Needed to avoid problems removing the Script
            if (active == crossover) {
                ISelection selection = sourceViewer.getSelection();
                if (selection != null) {
                    SelectionChangedEvent event =
                            new SelectionChangedEvent(this, selection);
                    for (ISelectionChangedListener listener : selectionListeners) {
                        listener.selectionChanged(event);
                    }
                }
            }
        }
        mappingSelection.clear();
    }

    /**
     * @param target
     *            The target to check.
     * @return true if the target is already mapped, otherwise false.
     */
    private boolean targetMapped(Object target) {
        boolean mapped = false;
        if (target != null) {
            for (Iterator<Mapping> i = mappings.iterator(); i.hasNext();) {
                Mapping mapping = i.next();

                /*
                 * Sid XPD-7399. Don't count virtually mapped target as really
                 * mapped.
                 */
                if (target.equals(mapping.getTarget()) && !mapping.isVirtual()) {
                    mapped = true;
                    break;
                }
            }
        }
        return mapped;
    }

    /**
     * Redraws the tree controls and links.
     */
    private void redrawTrees() {
        sourceViewer.getTree().redraw();
        targetViewer.getTree().redraw();
        crossover.update();
    }

    /**
     * @param gc
     *            The graphics context for the source tree.
     */
    protected void drawSourceLinks(GC gc) {
        drawSourceLinks(gc, false);
        // drawSourceLinks(gc, true);

        crossover.update();
    }

    private void drawSourceLinks(GC gc, boolean enableAntiAliasAndAlpha) {

        for (Iterator<Mapping> i = mappings.iterator(); i.hasNext();) {
            Mapping mapping = i.next();

            /*
             * Sid XPD-7399 Hide links when source and target are both in
             * collapsed tree and mapping is configure to not show in collapsed
             * tree.
             */
            Object source = mapping.getSource();
            TreeItem sourceItem = null;
            Object target = mapping.getTarget();
            TreeItem targetItem = null;

            try {
                sourceItem = findItem(sourceViewer, source);
                if (sourceItem == null) {
                    sourceItem = findParentItem(sourceViewer, source);
                }
                targetItem = findItem(targetViewer, target);
                if (targetItem == null) {
                    targetItem = findParentItem(targetViewer, target);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // Ignore, it will get refreshed again.
            }

            /**
             * Get the Source and Target tree items that will act as the source
             * and target of the link (bearing in mind that if source or target
             * is in collapsed part of tree then link item will be the nearest
             * visible ancestor).
             */
            TreeItem sourceLinkItem = null;
            boolean sourceTreeItemVisible = false;

            if (sourceItem != null) {
                sourceLinkItem = getExpandedItem(sourceViewer, sourceItem);
                if (sourceLinkItem != null) {
                    sourceTreeItemVisible =
                            source.equals(sourceLinkItem.getData());
                }
            }

            TreeItem targetLinkItem = null;
            boolean targetTreeItemVisible = false;

            if (targetItem != null) {
                targetLinkItem = getExpandedItem(targetViewer, targetItem);
                if (targetLinkItem != null) {
                    targetTreeItemVisible =
                            target.equals(targetLinkItem.getData());
                }
            }

            if (!sourceTreeItemVisible && !targetTreeItemVisible) {
                /*
                 * Sid XPD-7399 - THis is a link to an invisible collapsed item
                 * (on both sides) If there is physical mapping for the parent
                 * object then don't show that there are collapsed mappings as
                 * it just looks very confusing.
                 * 
                 * This is especialy the case where parent and child tree
                 * content mappings exist for same parent item (this happens all
                 * the time for dat amapper 'Like Mappings'
                 */
                if (treeItemsAreMapped(sourceLinkItem, targetLinkItem)) {
                    continue;
                }
            }

            /*
             * When tree is filtered TreeItems can become disposed. So we should
             * behave as if the source/target object for mapping could not be
             * found (and hide the middle and source/target parts of line)
             */
            if (sourceItem != null && !sourceItem.isDisposed()) {
                int oldAlpha = gc.getAlpha();
                Color oldFG = gc.getForeground();

                if (!mappingSelection.contains(mapping)
                        && mapping.getColor() != null) {
                    gc.setForeground(mapping.getColor());

                } else if (filteringOn
                        && isFilteredOut(Collections.singleton(mapping))) {
                    gc.setForeground(ColorConstants.lightGray);

                } else if (active == crossover
                        && !Collections.disjoint(mappingSelection, mappings)) {
                    gc.setForeground(PlatformUI.getWorkbench().getDisplay()
                            .getSystemColor(SWT.COLOR_BLUE));
                }

                int oldAA = gc.getAntialias();
                int oldLineWidth = gc.getLineWidth();

                if (enableAntiAliasAndAlpha) {
                    gc.setAntialias(SWT.ON);
                }

                gc.setLineWidth(LINK_WIDTH);

                Rectangle area = sourceViewer.getTree().getBounds();

                Rectangle sourceBounds =
                        sourceLinkItem != null ? sourceLinkItem.getBounds()
                                : new Rectangle(0, 10, area.width - 30, 1);

                Tree tree = sourceViewer.getTree();
                ScrollBar scroll = tree.getHorizontalBar();
                int scrollSize = scroll.getMaximum();

                int x1 = Math.max(scrollSize, area.width);
                int startX = sourceBounds.x + sourceBounds.width;
                int startY =
                        sourceBounds.y - LINK_END_Y_OFFSET
                                + sourceBounds.height
                                / (sourceTreeItemVisible ? 2 : 1);

                gc.setLineStyle(SWT.LINE_DASH);

                Point from =
                        new Point(startX + LINK_END_WIDTH, startY
                                + LINK_END_Y_OFFSET);
                Point to = new Point(x1, startY + LINK_END_Y_OFFSET);

                gc.drawLine(from.x, from.y, to.x, to.y);

                if (enableAntiAliasAndAlpha) {
                    gc.setAntialias(oldAA);
                    gc.setAlpha(oldAlpha);
                }

                gc.setLineWidth(oldLineWidth);
                gc.setLineStyle(SWT.LINE_SOLID);
                gc.setForeground(oldFG);
            }
        }

    }

    /**
     * Recurses up the tree from the current item and finds the first visible
     * node (the first whose parent is expanded, or the root item).
     * 
     * @param viewer
     *            The viewer containing the item.
     * @param item
     *            The item to check from.
     * @return The first visible item.
     */
    private TreeItem getExpandedItem(TreeViewer viewer, TreeItem item) {
        if (item.isDisposed()) {
            return null;
        }
        return getExpandedItem(viewer, item, item);
    }

    /**
     * Recurses up the tree from the current item and finds the first visible
     * node (the first whose parent is expanded, or the root item).
     * 
     * @param viewer
     *            The viewer containing the item.
     * @param current
     *            The item to check from.
     * @param valid
     *            The last valid item.
     * @return The first visible item.
     */
    private TreeItem getExpandedItem(TreeViewer viewer, TreeItem current,
            TreeItem valid) {
        TreeItem linkItem;
        TreeItem parent = current.getParentItem();
        if (parent != null) {
            /*
             * MR 39782: Serious Studio Mapper control performance problems.
             * 
             * Used to use viewer.getExpandedState(parent.getData()) - this was
             * a very bad idea because all that did was spend most of it's time
             * looking for the TreeItem for the data (which is always gonna be
             * the 'parent' variable we ALREADY HAVE - it would then call
             * item.getExpanded() - whcih we can do here and now and save a
             * whole heap-o-time.
             */
            if (!parent.getExpanded()) {
                /* Parent not expanded, check it's parent. */
                linkItem = getExpandedItem(viewer, parent, parent);
            } else {
                linkItem = getExpandedItem(viewer, parent, valid);
            }
        } else {
            linkItem = valid;
        }
        return linkItem;
    }

    /**
     * @param gc
     *            The graphics context for the target tree.
     */
    private void drawTargetLinks(final GC gc) {
        drawTargetLinks(gc, false);
        // drawTargetLinks(gc, true);

        crossover.update();
    }

    private void drawTargetLinks(final GC gc, boolean enableAntiAliasAndAlpha) {
        for (Iterator<Mapping> i = mappings.iterator(); i.hasNext();) {
            Mapping mapping = i.next();
            /*
             * Sid XPD-7399 Hide links when source and target are both in
             * collapsed tree and mapping is configure to not show in collapsed
             * tree.
             */
            Object source = mapping.getSource();
            TreeItem sourceItem = null;
            Object target = mapping.getTarget();
            TreeItem targetItem = null;

            try {
                sourceItem = findItem(sourceViewer, source);
                if (sourceItem == null) {
                    sourceItem = findParentItem(sourceViewer, source);
                }
                targetItem = findItem(targetViewer, target);
                if (targetItem == null) {
                    targetItem = findParentItem(targetViewer, target);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // Ignore, it will get refreshed again.
            }

            /**
             * Get the Source and Target tree items that will act as the source
             * and target of the link (bearing in mind that if source or target
             * is in collapsed part of tree then link item will be the nearest
             * visible ancestor).
             */
            TreeItem sourceLinkItem = null;
            boolean sourceTreeItemVisible = false;

            if (sourceItem != null) {
                sourceLinkItem = getExpandedItem(sourceViewer, sourceItem);
                if (sourceLinkItem != null) {
                    sourceTreeItemVisible =
                            source.equals(sourceLinkItem.getData());
                }
            }

            TreeItem targetLinkItem = null;
            boolean targetTreeItemVisible = false;

            if (targetItem != null) {
                targetLinkItem = getExpandedItem(targetViewer, targetItem);
                if (targetLinkItem != null) {
                    targetTreeItemVisible =
                            target.equals(targetLinkItem.getData());
                }
            }

            if (!sourceTreeItemVisible && !targetTreeItemVisible) {
                /*
                 * Sid XPD-7399 - THis is a link to an invisible collapsed item
                 * (on both sides) If there is physical mapping for the parent
                 * object then don't show that there are collapsed mappings as
                 * it just looks very confusing.
                 * 
                 * This is especialy the case where parent and child tree
                 * content mappings exist for same parent item (this happens all
                 * the time for dat amapper 'Like Mappings'
                 */
                if (treeItemsAreMapped(sourceLinkItem, targetLinkItem)) {
                    continue;
                }
            }

            /*
             * When tree is filtered TreeItems can become disposed. So we should
             * behave as if the source/target object for mapping could not be
             * found (and hide the middle and source/target parts of line)
             */
            if (targetItem != null && !targetItem.isDisposed()) {
                int oldAlpha = gc.getAlpha();
                Color oldFG = gc.getForeground();

                if (!mappingSelection.contains(mapping)
                        && mapping.getColor() != null) {
                    gc.setForeground(mapping.getColor());

                } else if (filteringOn
                        && isFilteredOut(Collections.singleton(mapping))) {
                    gc.setForeground(ColorConstants.lightGray);

                } else if (active == crossover
                        && !Collections.disjoint(mappingSelection, mappings)) {
                    gc.setForeground(PlatformUI.getWorkbench().getDisplay()
                            .getSystemColor(SWT.COLOR_BLUE));
                }

                int oldAA = gc.getAntialias();
                int oldLineWidth = gc.getLineWidth();

                if (enableAntiAliasAndAlpha) {
                    gc.setAntialias(SWT.ON);
                }
                Rectangle area = targetViewer.getTree().getBounds();
                Rectangle targetBounds =
                        targetLinkItem != null ? targetLinkItem.getBounds()
                                : new Rectangle(50, 10, area.width - 30, 1);
                int endY =
                        targetBounds.y - LINK_END_Y_OFFSET
                                + targetBounds.height
                                / (targetTreeItemVisible ? 2 : 1);
                gc.setLineWidth(LINK_WIDTH);
                gc.setLineStyle(SWT.LINE_DASH);
                gc.drawLine(0, endY + LINK_END_Y_OFFSET, targetBounds.x
                        - TARGET_X_OFFSET, endY + LINK_END_Y_OFFSET);

                if (enableAntiAliasAndAlpha) {
                    gc.setAntialias(oldAA);
                    gc.setAlpha(oldAlpha);
                }

                gc.setLineWidth(oldLineWidth);
                gc.setLineStyle(SWT.LINE_SOLID);
                gc.setForeground(oldFG);
            }
        }
    }

    /**
     * @param viewer
     *            The viewer to search.
     * @param data
     *            The data object to find the parent item for.
     * @return The parent item, or null if one could not be found.
     */
    private TreeItem findParentItem(TreeViewer viewer, Object data) {
        /*
         * MR 39782: Serious Studio Mapper control performance problems.
         * 
         * The findParentItem() algorithm used to be quite inefficient
         * especially with many levels of collapsed tree. It USED to get the
         * TreeItem for the parent of the given content-data and if that could
         * not be found would return it's parent and so on. BUT findItem always
         * attempts to find items from the top of the tree down! So the
         * algorithm was N-Squared at best - and findParentItem is called a hell
         * of a lot!
         * 
         * This was done this way because this method is supposed to return the
         * closest EXPANDED parent item (i.e. the closesest ancestor that has a
         * treeitem). So now we will refine the algorithm by searching forwards
         * for each item in the tree instead and give up on the first
         * null-result (i.e. sarch from root of path). Then go down until an
         * ancestor without a treeItem is found.
         */

        /* But even before any of that let's see if we cached it already! */
        TreeViewerTreeItemCache parentTreeItemCache =
                getParentTreeItemCache(viewer);
        TreeItem item = parentTreeItemCache.treeItemCache.get(data);

        if (item != null) {
            return item;
        }

        ITreeContentProvider contentProvider =
                (ITreeContentProvider) viewer.getContentProvider();

        /* Build a list of all the ancestors of the data-hierarchy */
        List<Object> ancestors = new ArrayList<Object>();

        Object parent = contentProvider.getParent(data);
        while (parent != null) {
            ancestors.add(parent);
            parent = contentProvider.getParent(parent);
        }

        /* Go thru in reverse order (i.e. be root element down */
        for (int i = (ancestors.size() - 1); i >= 0; i--) {
            TreeItem next = findItem(viewer, ancestors.get(i));
            if (next == null) {
                /* We've gone down as far as we can go, so may as well quit. */
                break;
            }

            /*
             * Ok, remember this item as 'as far down as we can go so far' and
             * carry on
             */
            item = next;
        }

        /*
         * MR 39782: Serious Studio Mapper control performance problems.
         * 
         * Cache the result for next time in.
         */
        if (item != null) {
            parentTreeItemCache.treeItemCache.put(data, item);
        }
        return item;
    }

    /**
     * MR 39782: Serious Studio Mapper control performance problems.
     * <p>
     * Get or add tree item cache class for given viewer.
     * 
     * @param treeViewer
     * 
     * @return tree item cache guaranteed not null
     */
    private TreeViewerTreeItemCache getTreeItemCache(TreeViewer treeViewer) {
        TreeViewerTreeItemCache cache = treeViewerItemCaches.get(treeViewer);
        if (cache == null) {
            cache = new TreeViewerTreeItemCache(treeViewer);
            treeViewerItemCaches.put(treeViewer, cache);
        }
        return cache;
    }

    /**
     * MR 39782: Serious Studio Mapper control performance problems.
     * <p>
     * Get or add parent tree item cache class for given viewer.
     * <p>
     * NOTE: The parent tree item cache stores the 'closest currently available
     * ancestor TreeItem' - therefore has to be reset on any
     * refresh/expand/collapse event.
     * 
     * @param treeViewer
     * 
     * @return tree item cache guaranteed not null
     */
    private TreeViewerTreeItemCache getParentTreeItemCache(TreeViewer treeViewer) {
        TreeViewerTreeItemCache cache =
                treeViewerParentItemCaches.get(treeViewer);
        if (cache == null) {
            cache = new TreeViewerTreeItemCache(treeViewer);
            treeViewerParentItemCaches.put(treeViewer, cache);
        }
        return cache;
    }

    /**
     * MR 39782: Serious Studio Mapper control performance problems.
     * <p>
     * De-caches data->TreeItem cachre forgiven vieweer.
     * 
     * @param treeViewer
     */
    private void resetTreeItemCache(TreeViewer treeViewer) {
        getTreeItemCache(treeViewer).reset();
        getParentTreeItemCache(treeViewer).reset();

        if (treeViewer == sourceViewer) {
            sourceErrorSeverityCache = new HashMap<Object, ErrorSeverity>();
            sourceErrorMessagesCache = new HashMap<Object, String>();
        } else if (treeViewer == targetViewer) {
            targetErrorSeverityCache = new HashMap<Object, ErrorSeverity>();
            targetErrorMessagesCache = new HashMap<Object, String>();
        }
        return;
    }

    /**
     * @param viewer
     *            The viewer in which to find the item.
     * @param data
     *            The element to find.
     * @return The matching TreeItem.
     */
    private TreeItem findItem(final TreeViewer viewer, final Object data) {
        /*
         * MR 39782: Serious Studio Mapper control performance problems.
         * 
         * Find TreeItem in cache if possible.
         */
        TreeViewerTreeItemCache cache = getTreeItemCache(viewer);
        TreeItem treeItem = cache.treeItemCache.get(data);
        if (treeItem != null) {
            if (!treeItem.isDisposed()) {
                return treeItem;
            }

            /* Urk! throw it away and find again. */
            cache.treeItemCache.remove(data);

        }

        TreeItem[] items = viewer.getTree().getItems();
        treeItem = findItem(data, items, cache);
        return treeItem;
    }

    /**
     * @param data
     *            The element to find.
     * @param items
     *            The items to check.
     * @param treeItemCache
     * @return The item if found, otherwise null.
     */
    private TreeItem findItem(final Object data, TreeItem[] items,
            TreeViewerTreeItemCache treeItemCache) {
        TreeItem item = null;

        for (int i = 0; i < items.length; i++) {
            if (!items[i].isDisposed()) {
                Object itemData = items[i].getData();

                /* Cache this data->treeItem pair for later. */
                if (itemData != null) {
                    treeItemCache.treeItemCache.put(itemData, items[i]);
                }

                if (item == null) {
                    /*
                     * Not found yet so set if found right one - but carry on
                     * and caceh all!
                     */

                    if (itemData != null && itemData.equals(data)) {
                        item = items[i];
                    } else {
                        /* Check child content. */
                        item =
                                findItem(data,
                                        items[i].getItems(),
                                        treeItemCache);
                    }
                }
            }
        }
        return item;
    }

    /**
     * @param validator
     *            The target validator.
     */
    public void setTransferValidator(IMappingTransferValidator validator) {
        targetValidator = validator;
    }

    /**
     * Refresh the source viewer.
     * 
     * @param element
     *            Where to start refresh.
     * @see StructuredViewer#refresh(Object)
     */
    public void sourceViewerRefresh(Object element) {
        if (!sourceViewer.getTree().isDisposed()) {
            /*
             * MR 39782: Serious Studio Mapper control performance problems.
             * 
             * Although the fact that it seems TreeViewer will preserve TreeItem
             * for any given content data until the input is reset, we will be
             * extra sure that we're not caching old TreeItems by mistake and
             * throw cache away before refresh.
             * 
             * Should still be a massive performance improvement anyway!.
             */
            resetTreeItemCache(sourceViewer);

            sourceViewer.refresh(element);
        }
    }

    /**
     * Reveal an object in the source viewer.
     * 
     * @param element
     *            What to reveal.
     * @see StructuredViewer#reveal(Object)
     */
    public void sourceViewerReveal(Object element) {
        sourceViewer.reveal(element);
    }

    /**
     * Refresh the target viewer.
     * 
     * @param element
     *            Where to start refresh.
     * @see StructuredViewer#refresh(Object)
     */
    public void targetViewerRefresh(Object element) {
        if (!targetViewer.getTree().isDisposed()) {
            /*
             * MR 39782: Serious Studio Mapper control performance problems.
             * 
             * Although the fact that it seems TreeViewer will preserve TreeItem
             * for any given content data until the input is reset, we will be
             * extra sure that we're not caching old TreeItems by mistake and
             * throw cache away before refresh.
             * 
             * Should still be a massive performance improvement anyway!.
             */
            resetTreeItemCache(targetViewer);

            targetViewer.refresh(element);
        }
    }

    /**
     * Reveal an object in the target viewer.
     * 
     * @param element
     *            What to reveal.
     * @see StructuredViewer#reveal(Object)
     */
    public void targetViewerReveal(Object element) {
        targetViewer.reveal(element);
    }

    /**
     * @param filter
     *            The filter to add.
     */
    public void targetViewerAddFilter(ViewerFilter filter) {
        targetViewer.addFilter(filter);
    }

    /**
     * @param filter
     *            The filter to add.
     */
    public void sourceViewerAddFilter(ViewerFilter filter) {
        sourceViewer.addFilter(filter);
    }

    /**
     * @param filter
     *            The filter to remove.
     */
    public void targetViewerRemoveFilter(ViewerFilter filter) {
        targetViewer.removeFilter(filter);
    }

    /**
     * @param filter
     *            The filter to remove.
     */
    public void sourceViewerRemoveFilter(ViewerFilter filter) {
        sourceViewer.removeFilter(filter);
    }

    /**
     * @param errorProvider
     *            The new error provider.
     */
    public void setErrorProvider(IErrorProvider errorProvider) {
        this.errorProvider = errorProvider;
    }

    /**
     * Unregister the mapper component as an event source.
     * 
     * @see org.eclipse.swt.widgets.Widget#dispose()
     */
    @Override
    public void dispose() {
        if (id != null) {
            activator.unregisterMapper(id);
        }
        crossover.dispose();
        super.dispose();
    }

    /**
     * Sid XPD-7601: we previously broke moving source end of mapping because we
     * prevented creating a new mapping dropped onto an existing mapping and the
     * check for that (is target of the given pair already mapped) will always
     * be true if we're moving just the source end of mapping. So aded some
     * parameters so that we can make better judgements.
     * 
     * @param source
     *            The source viewer item.
     * @param target
     *            The target viewer item.
     * @param direction
     *            The drag direction.
     * @param isMoveMapping
     *            <code>true</code> If the validate is for moving an existing
     *            mapping.
     * @param isDropOnSourceViewer
     *            <code>true</code> if the validate is for a drop onto
     *            sourceViewer <code>false</code> for target viewer.
     * 
     * @return true if the drop is valid.
     */
    private boolean validateMapping(Object source, Object target,
            DragDirection direction, boolean isMoveMapping,
            boolean isDropOnSourceViewer) {
        if (source != null && target != null) {

            // Check that mapping between these 2 same objects does not
            // already exist.
            for (Mapping mapping : mappings) {
                if (source.equals(mapping.getSource())
                        && target.equals(mapping.getTarget())
                        /*
                         * Sid XPD-7399. Don't count virtually mapped target as
                         * really mapped.
                         */
                        && !mapping.isVirtual()) {
                    return false;
                }
            }

            /*
             * Sid XPD-7601: we previously broke moving source end of mapping
             * because we prevented creating a new mapping dropped onto an
             * existing mapping and the check for that (is target of the given
             * pair already mapped) will always be true if we're moving just the
             * source end of mapping. So aded some parameters so that we can
             * make better judgements.
             */
            boolean shouldDisallow = false;
            if (!isMoveMapping) {
                /*
                 * For creation of new mapping always fail if target is already
                 * mapped as we don't support concat mapping anymore.
                 */
                if (targetMapped(target)) {
                    shouldDisallow = true;
                }

            } else {
                /*
                 * For moving of mapping, then only if we are moving the target
                 * end should we disallow because we don't support concatenation
                 */
                if (!isDropOnSourceViewer) {
                    if (targetMapped(target)) {
                        shouldDisallow = true;
                    }
                }
            }

            if (!shouldDisallow) {
                if (targetValidator == null) {
                    return true;
                } else {
                    if (targetValidator.isValidTransfer(source, target)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param target
     *            The target object.
     * @return true if it has multiple inputs.
     */
    public boolean hasMultipleInputs(Object target) {
        boolean hasMultiple = false;
        boolean hasOne = false;
        for (Mapping mapping : mappings) {
            if (target.equals(mapping.getTarget())) {
                if (hasOne) {
                    hasMultiple = true;
                    break;
                }
                hasOne = true;
            }
        }
        return hasMultiple;
    }

    /**
     * @param listener
     *            The listener to add.
     */
    public void addMappingListener(IMappingListener listener) {
        mappingListeners.add(listener);
    }

    /**
     * @param listener
     *            The listener to remove.
     */
    public void removeMappingListener(IMappingListener listener) {
        mappingListeners.remove(listener);
    }

    /**
     * @param mapping
     *            The mapping added.
     */
    private void fireMappingAdded(Mapping mapping) {
        for (Iterator<IMappingListener> i = mappingListeners.iterator(); i
                .hasNext();) {
            IMappingListener listener = i.next();
            listener.mappingAdded(mapping);
        }
    }

    /**
     * @param mapping
     *            The mapping removed.
     */
    private void fireMappingRemoved(Mapping mapping) {
        for (Iterator<IMappingListener> i = mappingListeners.iterator(); i
                .hasNext();) {
            IMappingListener listener = i.next();
            listener.mappingRemoved(mapping);
        }
    }

    /**
     * Sid XPD-7601: Allow listeners to deal with moves if they wish (so that
     * they can preserve attributes.
     * 
     * @param mappingDelta
     */
    private void fireMappingMoved(MappingDelta mappingDelta) {
        for (Iterator<IMappingListener> i = mappingListeners.iterator(); i
                .hasNext();) {
            IMappingListener listener = i.next();

            if (mappingDelta.getBefore() != null
                    && mappingDelta.getBefore().isEditable()) {
                if (listener instanceof IMoveableMappingListener) {
                    /*
                     * Listener supports move
                     */
                    ((IMoveableMappingListener) listener)
                            .mappingMoved(mappingDelta.getBefore(),
                                    mappingDelta.getAfter());

                } else {
                    /*
                     * Listener does not support move, do old fashioned remove
                     * then add
                     */
                    fireMappingRemoved(mappingDelta.getBefore());
                    fireMappingAdded(mappingDelta.getAfter());
                }
            }
        }
    }

    /**
     * @param changes
     *            The mapping changes.
     */
    /*
     * private void fireMappingChanged(Collection<MappingDelta> changes) { for
     * (Iterator<IMappingListener> i = mappingListeners.iterator(); i
     * .hasNext();) { IMappingListener listener = i.next();
     * listener.mappingChanged(changes); } }
     */

    /**
     * Displays the transform panel.
     * 
     * @param mapping
     *            The mapping associated with the transform.
     * @param anchor
     *            The anchor point for the panel.
     */
    private void showTransformPanel(Mapping mapping,
            org.eclipse.swt.graphics.Point anchor) {
        if (transformSection != null) {
            ITransformShell shell = TransformShell.create();
            org.eclipse.swt.graphics.Point size =
                    transformSection.getPreferredSize();
            Rectangle display = Display.getDefault().getBounds();
            int x = (anchor.x * (display.width - size.x)) / display.width;
            int y = (anchor.y * (display.height - size.y)) / display.height;
            shell.setBounds(x, y, size.x, size.y, TRANSFORM_PANEL_RADIUS);
            shell.setSection(transformSection);
            transformSection.setInput(mapping);

            shell.open();
        }
    }

    /**
     * @param transformSection
     *            The transform section to use.
     */
    public void setTransformSection(ITransformSection transformSection) {
        this.transformSection = transformSection;
    }

    /**
     * @param mappings
     *            The mappings to check.
     * @return true if it should be filtered out.
     */
    public boolean isFilteredOut(Collection<Mapping> mappings) {
        boolean srcFiltered = true;

        List<?> sourceSelected = getSelected(sourceViewer);
        if (sourceSelected != null) {
            for (Mapping mapping : mappings) {
                if (sourceSelected.contains(mapping.getSource())) {
                    srcFiltered = false;
                    break;
                }
            }
        }

        boolean tgtFiltered = true;
        List<?> targetSelected = getSelected(targetViewer);
        if (targetSelected != null) {
            for (Mapping mapping : mappings) {
                if (targetSelected.contains(mapping.getTarget())) {
                    tgtFiltered = false;
                    break;
                }
            }
        }

        if (active == sourceViewer) {
            return srcFiltered;
        } else if (active == targetViewer) {
            return tgtFiltered;
        } else if (active == crossover) {
            return tgtFiltered && srcFiltered;
        }

        return true;
    }

    /**
     * @param viewer
     *            The viewer
     * @return The list of selected items.
     */
    private List<?> getSelected(TreeViewer viewer) {
        List<?> list = null;
        if (viewer != null) {
            try {
                ISelection selection = viewer.getSelection();
                if (selection instanceof IStructuredSelection) {
                    list = ((IStructuredSelection) selection).toList();
                }
            } catch (NullPointerException e) {
                // AbstractTreeViewer.getSelection can sometimes throw a
                // NullPointerException. Ignore it.
            }
        }
        return list;
    }

    /**
     * @return The source viewer.
     */
    TreeViewer getSourceViewer() {
        return sourceViewer;
    }

    /**
     * @return The target viewer.
     */
    TreeViewer getTargetViewer() {
        return targetViewer;
    }

    /**
     * @param filteringOn
     *            The filtering flag value.
     */
    public void setFilteringOn(boolean filteringOn) {
        this.filteringOn = filteringOn;
    }

    /**
     * Update error markers.
     */
    public void updateMarkers() {
        crossover.updateMarkers();
    }

    /**
     * @param disabled
     *            true to disable delete menu item.
     */
    public void setDeleteDisabled(boolean disabled) {
        deleteDisabled = disabled;
    }

    /**
     * @param element
     * 
     * @return Get the problem marker severity level for te given element.
     */
    private ErrorSeverity getMapperTreeContentSeverity(Object element,
            ErrorSeverity highestSeverity, boolean isSourceViewer,
            int recursionDepth, Set<Object> ancestorTypesForRecusionComparison) {
        /*
         * We need to stop recursion properly by checking whether we have
         * already dealt with a particular type which somewhere underneath
         * includes itself as a descendant.
         */
        Object typeForRecursionComparison;
        if (isSourceViewer) {
            typeForRecursionComparison =
                    errorProvider
                            .getSourceObjectTypeForRecursionComparison(element);
        } else {
            typeForRecursionComparison =
                    errorProvider
                            .getTargetObjectTypeForRecursionComparison(element);
        }

        // String s = element.toString();
        //
        // if (s.length() > 80) {
        // String t = s;
        // s = t.substring(0, 36);
        //            s += "..."; //$NON-NLS-1$
        // s += t.substring(t.length() - 40);
        // }
        //        log("  Level %d: %s", recursionDepth, s); //$NON-NLS-1$

        if (typeForRecursionComparison != null) {
            if (ancestorTypesForRecusionComparison
                    .contains(typeForRecursionComparison)) {

                //                log("  Mapper.getMapperTreeContentSeverity(%s): recursionDepth: %d  Type: %s", //$NON-NLS-1$
                // s,
                // recursionDepth,
                // typeForRecursionComparison);
                return highestSeverity;
            }
        }

        //        System.out.print("."); //$NON-NLS-1$

        /* Get the severity for the passed tree element content */
        ErrorSeverity severity = null;
        if (isSourceViewer) {
            /*
             * MR 39782: Serious Studio Mapper control performance problems.
             * 
             * Attempt to load from cache first.
             */
            severity = sourceErrorSeverityCache.get(element);
            if (severity != null) {
                return severity;
            }

            severity = errorProvider.getSeverityForSourceObject(element);

        } else {
            /*
             * MR 39782: Serious Studio Mapper control performance problems.
             * 
             * Attempt to load from cache first.
             */
            severity = targetErrorSeverityCache.get(element);
            if (severity != null) {
                return severity;
            }

            severity = errorProvider.getSeverityForTargetObject(element);
        }
        //      System.out.println("."); //$NON-NLS-1$

        if (severity == null) {
            /* there has been an error just quit. */
            return null;
        }

        /*
         * If it's higher than the current highest then reset the highest.
         */
        if (severityToInt(severity) > severityToInt(highestSeverity)) {
            highestSeverity = severity;
        }

        if (!ErrorSeverity.ERROR.equals(highestSeverity)) {
            /*
             * If not highest level of error already then ask children see
             * whether they have a higher one.
             */
            ITreeContentProvider contentProvider = null;

            if (isSourceViewer) {
                if (sourceViewer.getContentProvider() instanceof ITreeContentProvider) {
                    contentProvider =
                            (ITreeContentProvider) sourceViewer
                                    .getContentProvider();
                }
            } else {
                contentProvider =
                        (ITreeContentProvider) targetViewer
                                .getContentProvider();
            }

            /*
             * Get children
             */
            if (contentProvider != null) {
                Object[] children = contentProvider.getChildren(element);
                if (children != null && children.length > 0) {

                    /*
                     * Add current element's type for recursion comparison
                     * checking before recursing.
                     * 
                     * We have to then make sure it's removed (so that current
                     * element's siblings don't get disallowed).
                     */
                    if (typeForRecursionComparison != null) {
                        ancestorTypesForRecusionComparison
                                .add(typeForRecursionComparison);
                    }

                    for (Object child : children) {
                        /* Recurs and check severity. */
                        ErrorSeverity childSeverity =
                                getMapperTreeContentSeverity(child,
                                        severity,
                                        isSourceViewer,
                                        recursionDepth + 1,
                                        ancestorTypesForRecusionComparison);

                        if (childSeverity == null) {
                            /* Urk - an error has occurred. */
                            return null;
                        }

                        if (severityToInt(childSeverity) > severityToInt(highestSeverity)) {
                            /*
                             * If child has a higher severity then reset the
                             * highest severity.
                             */
                            highestSeverity = childSeverity;

                            if (ErrorSeverity.ERROR.equals(highestSeverity)) {
                                /*
                                 * No point carrying on if a child has highest
                                 * possible.
                                 */
                                break;
                            }
                        }
                    }

                    if (typeForRecursionComparison != null) {
                        ancestorTypesForRecusionComparison
                                .remove(typeForRecursionComparison);
                    }
                }
            }
        }

        if (highestSeverity == null) {
            highestSeverity = ErrorSeverity.NONE;
        }

        /*
         * MR 39782: Serious Studio Mapper control performance problems.
         * 
         * Cache the error severity for next time quick lookup.
         */
        if (isSourceViewer) {
            sourceErrorSeverityCache.put(element, highestSeverity);
        } else {
            targetErrorSeverityCache.put(element, highestSeverity);
        }

        return highestSeverity;
    }

    private int severityToInt(ErrorSeverity severity) {
        if (severity != null) {
            if (ErrorSeverity.ERROR.equals(severity)) {
                return 3;
            } else if (ErrorSeverity.WARNING.equals(severity)) {
                return 2;
            } else if (ErrorSeverity.INFO.equals(severity)) {
                return 1;
            }
        }
        return 0;

    }

    /**
     * @author nwilson
     */
    class DragAdapter extends DragSourceAdapter {
        /** The source viewer. */
        private Viewer viewer;

        /**
         * @param viewer
         *            The source viewer.
         */
        protected DragAdapter(final Viewer viewer) {
            this.viewer = viewer;
        }

        /**
         * @param event
         *            The drag event.
         * @see org.eclipse.swt.dnd.DragSourceAdapter#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
         */
        @Override
        public void dragSetData(final DragSourceEvent event) {
            TreeSelection selection = (TreeSelection) viewer.getSelection();
            if (selection.size() == 1) {
                event.data =
                        new DragItem(
                                selection.getFirstElement(),
                                viewer == sourceViewer ? DragDirection.LEFT_TO_RIGHT
                                        : DragDirection.RIGHT_TO_LEFT);
            } else {
                event.data =
                        new DragItem(
                                selection.toList(),
                                viewer == sourceViewer ? DragDirection.LEFT_TO_RIGHT
                                        : DragDirection.RIGHT_TO_LEFT);
            }
        }

    }

    /**
     * @author nwilson
     */
    enum DragDirection {
        /** Drag directions. */
        LEFT_TO_RIGHT, RIGHT_TO_LEFT, FROM_CENTER
        // FROM_CENTER used for instances when Drag starts at centre and is
        // transformed to LEFT_To_RIGHT OR RIGHT_TO_LEFT depending on, it is
        // dragged towards Right OR LEFT.
    };

    /**
     * Container for dragged item data.
     * 
     * @author nwilson
     */
    static class DragItem {
        /** Indicates if this is a source or target item. */
        private DragDirection direction;

        /** The item. */
        private Object item;

        /**
         * @param item
         *            The item.
         * @param direction
         *            the drag direction.
         */
        public DragItem(Object item, DragDirection direction) {
            this.direction = direction;
            this.item = item;
        }

        /**
         * @return The item.
         */
        public Object getItem() {
            return item;
        }

        /**
         * @return true for a source item, false for a target.
         */
        public DragDirection getDragDirection() {
            return direction;
        }

    }

    /**
     * @author nwilson
     */
    class DropAdapter extends ViewerDropAdapter {
        /**
         * @param event
         *            The drop target event.
         * @see org.eclipse.jface.viewers.ViewerDropAdapter#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
         */
        @Override
        public void dragEnter(DropTargetEvent event) {
            event.detail = DND.DROP_LINK;
            super.dragEnter(event);
        }

        /**
         * @param event
         *            The drop target event.
         * @see org.eclipse.jface.viewers.ViewerDropAdapter#
         *      dragOperationChanged(org.eclipse.swt.dnd.DropTargetEvent)
         */
        @Override
        public void dragOperationChanged(DropTargetEvent event) {
            event.detail = DND.DROP_LINK;
            super.dragOperationChanged(event);
        }

        /** The target viewer. */
        private Object target;

        /**
         * @param viewer
         *            The target viewer.
         */
        protected DropAdapter(final Viewer viewer) {
            super(viewer);
            setFeedbackEnabled(false);
        }

        /**
         * @param sourceItem
         *            The source data.
         * @return true.
         * @see org.eclipse.jface.viewers.ViewerDropAdapter#performDrop(java.lang.Object)
         */
        @Override
        public boolean performDrop(final Object sourceItem) {
            if (sourceItem instanceof DragItem) {
                DragItem dragItem = (DragItem) sourceItem;
                Object item = dragItem.getItem();
                DragDirection direction = dragItem.getDragDirection();
                if (!isValidDrop(item, target, direction)) {
                    return false;
                }

                setRedraw(false);

                Object source = null;
                if (item instanceof Collection) {
                    Collection<?> items = (Collection<?>) item;
                    Collection<Mapping> updatedSelection =
                            new ArrayList<Mapping>(mappingSelection);
                    for (Object next : items) {
                        if (next instanceof Mapping) {
                            Mapping oldMapping = (Mapping) next;
                            Mapping mapping;
                            if (targetViewer == getViewer()) {
                                source = oldMapping.getSource();
                                mapping = new Mapping(source, target);
                            } else {
                                source = oldMapping.getTarget();
                                mapping = new Mapping(target, source);
                            }
                            if (updatedSelection.contains(oldMapping)
                                    && oldMapping.isEditable()) {
                                updatedSelection.remove(oldMapping);
                                updatedSelection.add(mapping);
                            }

                            /*
                             * Sid XPD-7601: Allow listeners to deal with moves
                             * if they wish (so that they can preserve
                             * attributes.
                             */
                            fireMappingMoved(new MappingDelta(oldMapping,
                                    mapping));

                        } else {
                            source = next;
                            Mapping mapping = null;
                            if (targetViewer == getViewer()) {
                                mapping = new Mapping(source, target);
                            } else if (sourceViewer == getViewer()) {
                                mapping = new Mapping(target, source);
                            }
                            if (mapping != null) {
                                fireMappingAdded(mapping);
                            }
                            if (targetViewer == getViewer()) {
                                sourceViewer.getTree().setFocus();
                            } else if (sourceViewer == getViewer()) {
                                targetViewer.getTree().setFocus();
                            }
                        }
                    }

                    mappingSelection = updatedSelection;
                    updateSelectionFromCrossover();
                } else {
                    source = item;
                    Mapping mapping = null;
                    if (targetViewer == getViewer()) {
                        mapping = new Mapping(source, target);
                    } else if (sourceViewer == getViewer()) {
                        mapping = new Mapping(target, source);
                    }
                    if (mapping != null) {
                        fireMappingAdded(mapping);
                    }
                    if (targetViewer == getViewer()) {
                        sourceViewer.getTree().setFocus();
                    } else if (sourceViewer == getViewer()) {
                        targetViewer.getTree().setFocus();
                    }
                }

                setRedraw(true);
            }
            return true;
        }

        /**
         * @param target
         *            The target object.
         * @param operation
         *            The operation type.
         * @param transferType
         *            The transfer type.
         * @return true if the drop is valid, otherwise false.
         * @see org.eclipse.jface.viewers.ViewerDropAdapter#validateDrop(java.lang.Object,
         *      int, org.eclipse.swt.dnd.TransferData)
         */
        @Override
        public boolean validateDrop(final Object target, final int operation,
                final TransferData transferType) {
            if (target == null) {
                return false;
            }
            this.target = target;
            Object sourceItem =
                    LocalTransfer.getInstance().nativeToJava(transferType);
            if (sourceItem == null) {
                // sourceItem is null for Linux as the source is not available
                // until the performDrop method.
                return true;
            }
            if (sourceItem instanceof DragItem) {
                DragItem dragItem = (DragItem) sourceItem;
                DragDirection direction = dragItem.getDragDirection();
                Object item = dragItem.getItem();
                return isValidDrop(item, target, direction);
            }
            return false;
        }

        /**
         * @param item
         *            The source item.
         * @param target
         *            The target item.
         * @param direction
         *            The drag direction.
         * @return true if the mapping is valid.
         */
        private boolean isValidDrop(Object item, Object target,
                DragDirection direction) {
            Object source;
            if (isMappingCollection(item)) {
                Collection<?> items = (Collection<?>) item;
                // Defect 32885 Re-instating multiple items to one target. If
                // the
                // user cares about the order they need to use a script.
                // if (items.size() > 1 && targetViewer == getViewer()) {
                // Defect 30856 - prevent dragging multiple items to
                // one target as it may re-order them.
                // return false;
                // }
                for (Object next : items) {
                    if (next instanceof Mapping) {
                        Mapping mapping = (Mapping) next;
                        if (!mapping.isEditable()) {
                            return false;
                        }
                        if (!DragDirection.RIGHT_TO_LEFT.equals(direction)
                                && targetViewer == getViewer()) {
                            source = mapping.getSource();

                            /*
                             * Sid XPD-7601: tell validateDrop it is a move
                             * mapping and whether if so whether it is source or
                             * target side.
                             */
                            if (!validateMapping(source,
                                    target,
                                    DragDirection.LEFT_TO_RIGHT,
                                    true,
                                    false)) {
                                return false;
                            }
                        } else if (!DragDirection.LEFT_TO_RIGHT
                                .equals(direction)
                                && sourceViewer == getViewer()) {
                            source = mapping.getTarget();

                            // Defect 30856 - Prevent dragging items to
                            // a new source if their target has multiple
                            // inputs as it may cause re-ordering.

                            /*
                             * Sid XPD-7601: tell validateDrop it is a move
                             * mapping and whether if so whether it is source or
                             * target side.
                             */
                            if (!validateMapping(target,
                                    source,
                                    DragDirection.RIGHT_TO_LEFT,
                                    true,
                                    true) || hasMultipleInputs(source)) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        source = next;
                        if (DragDirection.LEFT_TO_RIGHT.equals(direction)
                                && targetViewer == getViewer()) {
                            if (!validateMapping(source,
                                    target,
                                    direction,
                                    false,
                                    false)) {
                                return false;
                            }
                        } else if (DragDirection.RIGHT_TO_LEFT
                                .equals(direction)
                                && targetViewer != getViewer()) {
                            if (!validateMapping(target,
                                    source,
                                    direction,
                                    false,
                                    true)) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            } else {
                source = item;
                if (DragDirection.LEFT_TO_RIGHT.equals(direction)
                        && targetViewer == getViewer()) {
                    return validateMapping(source,
                            target,
                            direction,
                            false,
                            false);
                } else if (DragDirection.RIGHT_TO_LEFT.equals(direction)
                        && targetViewer != getViewer()) {
                    return validateMapping(target,
                            source,
                            direction,
                            false,
                            true);
                }
            }
            return false;
        }

        /**
         * @param item
         *            The item to check.
         * @return true if it is a collection of Mapping objects.
         */
        private boolean isMappingCollection(Object item) {
            boolean mappingCollection = false;
            if (item instanceof Collection) {
                boolean allMappings = true;
                for (Object next : (Collection<?>) item) {
                    if (!(next instanceof Mapping)) {
                        allMappings = false;
                        break;
                    }
                }
                if (allMappings) {
                    mappingCollection = true;
                }
            }
            return mappingCollection;
        }

    }

    /**
     * @author nwilson
     */
    class Crossover extends FigureCanvas {

        /** The base figure used to draw the crossover links. */
        private CrossoverFigure figure;

        /** The drag source. */
        private DragSource drag;

        /**
         * @param parent
         *            The parent composite.
         * @param style
         *            The canvas style.
         */
        public Crossover(Composite parent, int style) {
            super(parent, style);
            setScrollBarVisibility(NEVER);
            setBackground(PlatformUI.getWorkbench().getDisplay()
                    .getSystemColor(SWT.COLOR_WHITE));
            figure = new CrossoverFigure(this);
            setContents(figure);
            drag = new DragSource(this, DND.DROP_LINK);
            drag.setTransfer(transferTypes);
            drag.addDragListener(new DragSourceListener() {

                private DragItem item;

                @Override
                public void dragStart(DragSourceEvent event) {
                    DragItem current = figure.getDragItem(event.x, event.y);
                    if (current == null) {
                        event.doit = false;
                    } else {
                        item = current;
                        if (figure != null) {
                            figure.updateCursor(false);
                        }
                    }
                }

                @Override
                public void dragSetData(DragSourceEvent event) {
                    if (item != null) {
                        event.data = item;
                    }
                }

                @Override
                public void dragFinished(DragSourceEvent event) {
                    item = null;
                    if (figure != null) {
                        figure.updateCursor(true);
                    }
                }

            });
        }

        /**
         * Reloads the mappings.
         */
        public void refresh() {
            if (figure != null) {
                figure.refresh();
            }
        }

        /**
         * Reloads the mappings.
         */
        @Override
        public void update() {
            if (figure != null) {
                figure.revalidate();
                figure.invalidateTree();
            }
        }

        /**
         * @see org.eclipse.swt.widgets.Widget#dispose()
         */
        @Override
        public void dispose() {
            if (figure != null) {
                figure.dispose();
            }
            drag.dispose();
            super.dispose();
        }

        /**
         * 
         */
        public void updateMarkers() {
            if (figure != null) {
                figure.updateMarkers();
            }
        }

        public List<Mapping> getMappings() {
            return mappings;
        }

        /**
         * @see org.eclipse.draw2d.FigureCanvas#scrollToY(int)
         * 
         * @param vOffset
         */
        @Override
        public void scrollToY(int vOffset) {
            /*
             * Sid XPD-2195. Ignore explicit calls to resize. EVEN though we
             * hide the scroll bars, IF the user resizes the property sheet
             * vertically smaller then it changes the scroll model and we then
             * start receiving generated mousewheel scroll events.
             * 
             * The crossover control should only be scrolled as a result of teh
             * source or target tree's being scrolled.
             */
            // super.scrollToY(vOffset);
        }
    }

    /**
     * @author nwilson
     */
    class CrossoverFigure extends ConnectionLayer implements IMappingListener {

        /** The crossove canvas. */
        private Crossover crossover;

        /** Map of Mapping objects to line end figures. */
        private HashMap<Object, CrossoverLineFigureStart> lineStartMap;

        /** Map of Mapping objects to line figures. */
        private HashMap<Mapping, CrossoverLineFigure> lineMap;

        /** Map of Mapping objects to line end figures. */
        private HashMap<Object, CrossoverLineFigureEnd> lineEndMap;

        /** The hand cursor. */
        private Cursor hand;

        /**
         * Constructor.
         * 
         * @param crossover
         *            The crossover canvas.
         */
        public CrossoverFigure(final Crossover crossover) {
            this.crossover = crossover;
            lineStartMap = new HashMap<Object, CrossoverLineFigureStart>();
            lineMap = new HashMap<Mapping, CrossoverLineFigure>();
            lineEndMap = new HashMap<Object, CrossoverLineFigureEnd>();
            hand = new Cursor(Display.getDefault(), SWT.CURSOR_HAND);
            // addMappingListener(this);
            // setLayoutManager(new StackLayout());
            addMouseListener(new MouseListener() {

                @Override
                public void mouseDoubleClicked(MouseEvent me) {
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    crossover.setFocus();
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }

            });

            this.setConnectionRouter(new MappingConnectionRouter());
            this.setAntialias(SWT.ON);

        }

        /**
         * @param enable
         *            Enable the hand cursor.
         */
        public void updateCursor(boolean enable) {
            for (IFigure figure : lineStartMap.values()) {
                figure.setCursor(enable ? hand : null);
            }
            for (IFigure figure : lineMap.values()) {
                figure.setCursor(enable ? hand : null);
            }
            for (IFigure figure : lineEndMap.values()) {
                figure.setCursor(enable ? hand : null);
            }
        }

        /**
         * 
         */
        public void updateMarkers() {
            for (CrossoverLineFigureEnd lineEnd : lineEndMap.values()) {
                lineEnd.updateMarker();
            }

        }

        /**
         * Clean up.
         */
        public void dispose() {
            hand.dispose();
        }

        /**
         * @param x
         *            The x position.
         * @param y
         *            The y position.
         * @return The drag item at that position or null.
         */
        public DragItem getDragItem(int x, int y) {
            DragItem item = null;
            Collection<Mapping> items = new ArrayList<Mapping>();
            for (CrossoverLineFigure figure : lineMap.values()) {
                Mapping current = figure.getDragItem(x, y);
                if (current != null) {
                    items.add(current);
                }
            }
            if (items.size() == 0) {
                for (CrossoverLineFigureEnd figure : lineEndMap.values()) {
                    Collection<Mapping> current = figure.getDragItem(x, y);
                    if (current != null) {
                        items.addAll(current);
                    }
                }
                if (items.size() == 0) {
                    for (CrossoverLineFigureStart figure : lineStartMap
                            .values()) {
                        Collection<Mapping> current = figure.getDragItem(x, y);
                        if (current != null) {
                            items.addAll(current);
                        }
                    }
                    if (items.size() == 1) {
                        item = new DragItem(items, DragDirection.FROM_CENTER);
                    } else if (items.size() > 1) {
                        item = new DragItem(items, DragDirection.RIGHT_TO_LEFT);
                    }
                } else {
                    if (items.size() == 1) {
                        item = new DragItem(items, DragDirection.FROM_CENTER);
                    } else if (items.size() > 1) {
                        item = new DragItem(items, DragDirection.LEFT_TO_RIGHT);
                    }
                }
            } else {
                item = new DragItem(items, DragDirection.FROM_CENTER);
            }
            return item;
        }

        /**
         * Reloads the mappings and creates new line figures.
         */
        public void refresh() {
            for (CrossoverLineFigureEnd line : lineEndMap.values()) {
                line.dispose();
            }
            for (CrossoverLineFigureStart line : lineStartMap.values()) {
                line.dispose();
            }
            removeAll();
            lineStartMap = new HashMap<Object, CrossoverLineFigureStart>();
            lineMap = new HashMap<Mapping, CrossoverLineFigure>();
            lineEndMap = new HashMap<Object, CrossoverLineFigureEnd>();
            for (Mapping mapping : mappings) {
                if (!lineMap.containsKey(mapping)) {
                    CrossoverLineFigure line =
                            new CrossoverLineFigure(crossover, mapping);
                    lineMap.put(mapping, line);
                    add(line);
                    line.setCursor(hand);
                }

                Object source = mapping.getSource();
                CrossoverLineFigureStart lineStart = lineStartMap.get(source);
                if (lineStart == null) {
                    lineStart = new CrossoverLineFigureStart(crossover);
                    lineStartMap.put(source, lineStart);
                    add(lineStart);
                }
                lineStart.addMapping(mapping);
                lineStart.setCursor(hand);

                Object target = mapping.getTarget();
                CrossoverLineFigureEnd lineEnd = lineEndMap.get(target);
                if (lineEnd == null) {
                    lineEnd = new CrossoverLineFigureEnd(crossover);
                    lineEndMap.put(target, lineEnd);
                    add(lineEnd);
                }
                lineEnd.addMapping(mapping);
                lineEnd.setCursor(hand);
            }
        }

        /**
         * @param mapping
         *            The mapping added.
         * @see com.tibco.xpd.mapper.IMappingListener#mappingAdded(com.tibco.xpd.mapper.Mapping)
         */
        @Override
        public void mappingAdded(Mapping mapping) {
            if (!lineMap.containsKey(mapping)) {
                CrossoverLineFigure line =
                        new CrossoverLineFigure(crossover, mapping);
                lineMap.put(mapping, line);
                add(line);
                line.setCursor(hand);
            }

            Object source = mapping.getSource();
            CrossoverLineFigureStart lineStart = lineStartMap.get(source);
            if (lineStart == null) {
                lineStart = new CrossoverLineFigureStart(crossover);
                lineStartMap.put(source, lineStart);
                add(lineStart);
            }
            lineStart.addMapping(mapping);
            lineStart.setCursor(hand);

            Object target = mapping.getTarget();
            CrossoverLineFigureEnd lineEnd = lineEndMap.get(target);
            if (lineEnd == null) {
                lineEnd = new CrossoverLineFigureEnd(crossover);
                lineEndMap.put(target, lineEnd);
                add(lineEnd);
            }
            lineEnd.addMapping(mapping);
            lineEnd.setCursor(hand);
        }

        /**
         * @param mapping
         *            The mapping removed.
         * @see com.tibco.xpd.mapper.IMappingListener#mappingRemoved(com.tibco.xpd.mapper.Mapping)
         */
        @Override
        public void mappingRemoved(Mapping mapping) {
            CrossoverLineFigure line = lineMap.get(mapping);
            if (line != null) {
                remove(line);
                lineMap.remove(mapping);
            }

            Object source = mapping.getSource();
            CrossoverLineFigureStart lineStart = lineStartMap.get(source);
            if (lineStart != null) {
                lineStart.removeMapping(mapping);
                if (!lineStart.hasMappings()) {
                    remove(lineStart);
                    lineStartMap.remove(source);
                    lineStart.dispose();
                }
            }

            Object target = mapping.getTarget();
            CrossoverLineFigureEnd lineEnd = lineEndMap.get(target);
            if (lineEnd != null) {
                lineEnd.removeMapping(mapping);
                if (!lineEnd.hasMappings()) {
                    remove(lineEnd);
                    lineEndMap.remove(target);
                    lineEnd.dispose();
                }
            }
        }

        /**
         * @param changes
         *            The mapping changes.
         * @deprecated This method is never called by the Mapper (which uses
         *             mappingRemoved -> mappingAdded
         */
        @Override
        @Deprecated
        public void mappingChanged(Collection<MappingDelta> changes) {
            for (MappingDelta delta : changes) {
                mappingRemoved(delta.getBefore());
                mappingAdded(delta.getAfter());
            }
        }
    }

    /**
     * MappingConnectionRouter
     * <p>
     * Connection router for each part of each mapping line.
     * 
     * @author aallway
     */
    private class MappingConnectionRouter implements ConnectionRouter {

        @Override
        public void route(Connection connection) {
            if (sourceFilteredTree.isDisposed()
                    || targetFilteredTree.isDisposed()
                    || sourceViewer.getTree().isDisposed()
                    || targetViewer.getTree().isDisposed()) {
                return;
            }

            /*
             * Crossover is now at same height as source trere - hence no need
             * to offset lines.
             */
            int sourceYOffset = 0; // srcTreeLoc.y - topLoc.y;

            int targetYOffset = 0;// tgtTreeLoc.y - topLoc.y;

            /*
             * Sid XPD-7399: And if both sides are collapsed then don't show at
             * all if the mapping is configured not to show.
             */

            /*
             * Sid XPD-7399 bunch of duplicate stuff moved up here.
             */
            Mapping mapping = null;
            if (connection instanceof CrossoverLineFigure) {
                mapping = ((CrossoverLineFigure) connection).mapping;

            } else if (connection instanceof CrossoverLineFigureStart) {
                if (!((CrossoverLineFigureStart) connection).mappings.isEmpty()) {
                    mapping =
                            ((CrossoverLineFigureStart) connection).mappings
                                    .iterator().next();
                }
            } else if (connection instanceof CrossoverLineFigureEnd) {
                if (!((CrossoverLineFigureEnd) connection).mappings.isEmpty()) {
                    mapping =
                            ((CrossoverLineFigureEnd) connection).mappings
                                    .iterator().next();
                }
            }

            if (mapping == null) {
                return;
            }

            /*
             * Get source and target tree items (and first collapsed ancestor)
             */
            Object source = mapping.getSource();
            Object target = mapping.getTarget();
            TreeItem sourceItem = findItem(sourceViewer, source);
            TreeItem targetItem = findItem(targetViewer, target);

            if (sourceItem == null) {
                sourceItem = findParentItem(sourceViewer, source);
            }

            if (targetItem == null) {
                targetItem = findParentItem(targetViewer, target);
            }

            TreeItem sourceLinkItem = null;
            if (sourceItem != null && !sourceItem.isDisposed()) {
                sourceLinkItem = getExpandedItem(sourceViewer, sourceItem);
            }

            TreeItem targetLinkItem = null;
            if (targetItem != null && !targetItem.isDisposed()) {
                targetLinkItem = getExpandedItem(targetViewer, targetItem);
            }

            /*
             * Figure out if source and target tree items are visibl eor in a
             * collapsed part of tree.
             */
            boolean sourceTreeItemVisible = false;
            if (sourceLinkItem != null) {
                sourceTreeItemVisible = source.equals(sourceLinkItem.getData());
            }

            boolean targetTreeItemVisible = false;
            if (targetLinkItem != null) {
                targetTreeItemVisible = target.equals(targetLinkItem.getData());
            }

            /*
             * Sid XPD-7399 Hide links when source and target are both in
             * collapsed tree and mapping is configure to not show in collapsed
             * tree.
             */
            if (!sourceTreeItemVisible && !targetTreeItemVisible) {
                /*
                 * Sid XPD-7399 - THis is a link to an invisible collapsed item
                 * (on both sides) If there is physical mapping for the parent
                 * object then don't show that there are collapsed mappings as
                 * it just looks very confusing.
                 * 
                 * This is especialy the case where parent and child tree
                 * content mappings exist for same parent item (this happens all
                 * the time for dat amapper 'Like Mappings'
                 */
                if (treeItemsAreMapped(sourceLinkItem, targetLinkItem)) {
                    if (connection.isVisible()) {
                        connection.setVisible(false);
                    }
                    return;
                }
            }

            if (connection instanceof CrossoverLineFigure) {
                CrossoverLineFigure figure = (CrossoverLineFigure) connection;

                /*
                 * Sid XPD-7852 - did some fairly extensive shuffling around of
                 * code to figure out start and end point of line to tag a
                 * little extra onto the line to carry it into the centre of
                 * crossover more that the bend-point location WHEN the source
                 * or target is not available.
                 * 
                 * This is so that a broken mapping from a source item that has
                 * other mappings won't be completely invisibel underneath the
                 * valid mappings.
                 * 
                 * Basic premise is that as long as either source or target is
                 * set then we will figure out the start or end of the line and
                 * then if other end is missing we will offset it from the valid
                 * end.
                 */

                /*
                 * When tree is filtered TreeItems can become disposed. So we
                 * should behave as if the source/target object for mapping
                 * could not be found (and hide the middle and source/target
                 * parts of line)
                 */
                if ((sourceItem == null || sourceItem.isDisposed())
                        && (targetItem == null || targetItem.isDisposed())) {
                    if (connection.isVisible()) {
                        connection.setVisible(false);
                    }

                } else {
                    /*
                     * XPD-6203: On Mac OS crossover lines weren't being
                     * re-drawn correctly on scroll.
                     * 
                     * Now we convert top of crossover to display coordinates
                     * and same for tree item location so that we can calculate
                     * offset between these two things (rather than tree item
                     * and it's parent ) which are both offset by scrolling when
                     * on Mac as it does on Windows, so changed it to use
                     * crossover control to calculate yOffset
                     */
                    Rectangle crossoverAreaBounds = crossover.getBounds();
                    org.eclipse.swt.graphics.Point topOfCrossoverDisplayCoords =
                            crossover
                                    .toDisplay(new org.eclipse.swt.graphics.Point(
                                            crossoverAreaBounds.x,
                                            crossoverAreaBounds.y));

                    /*
                     * At least one end of connection is found.
                     * 
                     * If one end is null we will extend the conneciton line
                     * into the middle a little further so that when there are
                     * multiple mappings to/from source/target then there is at
                     * least a little extra part that sticks out past the valid
                     * mapping.
                     */
                    Rectangle sourceBounds = null;
                    if (sourceItem != null && !sourceItem.isDisposed()) {
                        sourceBounds =
                                sourceLinkItem != null ? sourceLinkItem
                                        .getBounds()
                                        : new Rectangle(1, 1, 1, 1);

                        org.eclipse.swt.graphics.Point sourceItemLocation =
                                sourceViewer
                                        .getControl()
                                        .toDisplay(new org.eclipse.swt.graphics.Point(
                                                sourceBounds.x, sourceBounds.y));

                        sourceYOffset =
                                sourceItemLocation.y
                                        - topOfCrossoverDisplayCoords.y;
                    }

                    Rectangle targetBounds = null;
                    if (targetItem != null && !targetItem.isDisposed()) {
                        targetBounds =
                                targetLinkItem != null ? targetLinkItem
                                        .getBounds()
                                        : new Rectangle(1, 1, 1, 1);

                        org.eclipse.swt.graphics.Point targetItemLocation =
                                targetViewer
                                        .getControl()
                                        .toDisplay(new org.eclipse.swt.graphics.Point(
                                                targetBounds.x, targetBounds.y));

                        targetYOffset =
                                targetItemLocation.y
                                        - topOfCrossoverDisplayCoords.y;
                    }

                    /*
                     * Calculate final coordinates.
                     */
                    int x1, y1, x2, y2;

                    if (sourceBounds != null) {
                        x1 = BEND_POINT_1_OFFSET;
                        y1 =
                                sourceYOffset + crossoverAreaBounds.y
                                        + sourceBounds.height / 2;
                    } else {
                        /*
                         * IF source not found then align source of line to the
                         * left of target.
                         */
                        x1 =
                                figure.getParent().getBounds().width
                                        - BEND_POINT_1_OFFSET - 50;
                        y1 =
                                targetYOffset + crossoverAreaBounds.y
                                        + targetBounds.height / 2;
                    }

                    if (targetBounds != null) {
                        x2 =
                                figure.getParent().getBounds().width
                                        - BEND_POINT_1_OFFSET;
                        y2 =
                                targetYOffset + crossoverAreaBounds.y
                                        + targetBounds.height / 2;

                    } else {
                        /* Likewise for when target not found. */
                        x2 = BEND_POINT_1_OFFSET + 50;
                        y2 =
                                sourceYOffset + crossoverAreaBounds.y
                                        + sourceBounds.height / 2;

                    }

                    PointList pts = figure.getPoints();
                    pts.removeAllPoints();
                    pts.addPoint(x1, y1);
                    pts.addPoint(x2, y2);

                    if (!connection.isVisible()) {
                        connection.setVisible(true);
                    }

                }

            } else if (connection instanceof CrossoverLineFigureStart) {
                CrossoverLineFigureStart figure =
                        (CrossoverLineFigureStart) connection;

                Collection<Mapping> mappings = figure.mappings;
                if (mappings.size() > 0) {
                    if (sourceItem != null && !sourceItem.isDisposed()) {
                        Rectangle sourceBounds =
                                sourceLinkItem != null ? sourceLinkItem
                                        .getBounds()
                                        : new Rectangle(1, 1, 1, 1);

                        /*
                         * XPD-6203: On Mac OS crossover lines weren't being
                         * drawn correctly on scroll.
                         * 
                         * Now we convert top of crossover to display
                         * coordinates and same for tree item location so that
                         * we can calculate offset between these two things
                         * (rather than tree item and it's parent ) which are
                         * both offset by scrolling when on Mac as it does on
                         * Windows, so changed it to use crossover control to
                         * calculate yOffset
                         */

                        org.eclipse.swt.graphics.Point sourceItemLocation =
                                sourceViewer
                                        .getControl()
                                        .toDisplay(new org.eclipse.swt.graphics.Point(
                                                sourceBounds.x, sourceBounds.y));

                        Rectangle crossoverAreaBounds = crossover.getBounds();

                        org.eclipse.swt.graphics.Point topOfCrossoverDisplayCoords =
                                crossover
                                        .toDisplay(new org.eclipse.swt.graphics.Point(
                                                crossoverAreaBounds.x,
                                                crossoverAreaBounds.y));

                        sourceYOffset =
                                sourceItemLocation.y
                                        - topOfCrossoverDisplayCoords.y;

                        int x1 = LINK_END_WIDTH;
                        int x2 = BEND_POINT_1_OFFSET;
                        int y =
                                sourceYOffset + crossoverAreaBounds.y
                                        - LINK_END_Y_OFFSET
                                        + sourceBounds.height / 2;

                        PointList pts = figure.getPoints();
                        pts.removeAllPoints();
                        pts.addPoint(x1, y + LINK_END_Y_OFFSET);
                        pts.addPoint(x2, y + LINK_END_Y_OFFSET);

                        if (!connection.isVisible()) {
                            connection.setVisible(true);
                        }

                    } else {
                        if (connection.isVisible()) {
                            connection.setVisible(false);
                        }
                    }

                }

            } else if (connection instanceof CrossoverLineFigureEnd) {
                CrossoverLineFigureEnd figure =
                        (CrossoverLineFigureEnd) connection;

                Collection<Mapping> mappings = figure.mappings;
                if (mappings.size() > 0) {

                    if (targetItem != null && !targetItem.isDisposed()) {
                        Rectangle targetBounds =
                                targetLinkItem != null ? targetLinkItem
                                        .getBounds()
                                        : new Rectangle(1, 1, 1, 1);

                        /*
                         * XPD-6203: On Mac OS crossover lines weren't being
                         * drawn correctly on scroll.
                         * 
                         * Now we convert top of crossover to display
                         * coordinates and same for tree item location so that
                         * we can calculate offset between these two things
                         * (rather than tree item and it's parent ) which are
                         * both offset by scrolling when on Mac as it does on
                         * Windows, so changed it to use crossover control to
                         * calculate yOffset
                         */
                        org.eclipse.swt.graphics.Point targetItemLocation =
                                targetViewer
                                        .getControl()
                                        .toDisplay(new org.eclipse.swt.graphics.Point(
                                                targetBounds.x, targetBounds.y));

                        Rectangle crossoverAreaBounds = crossover.getBounds();

                        org.eclipse.swt.graphics.Point topOfCrossoverDisplayCoords =
                                crossover
                                        .toDisplay(new org.eclipse.swt.graphics.Point(
                                                crossoverAreaBounds.x,
                                                crossoverAreaBounds.y));

                        targetYOffset =
                                targetItemLocation.y
                                        - topOfCrossoverDisplayCoords.y;

                        int x1 =
                                figure.getParent().getBounds().width
                                        - BEND_POINT_1_OFFSET;
                        int x2 =
                                figure.getParent().getBounds().width
                                        - LINK_END_WIDTH;
                        int y =
                                targetYOffset + crossoverAreaBounds.y
                                        - LINK_END_Y_OFFSET
                                        + targetBounds.height / 2;

                        PointList pts = figure.getPoints();
                        pts.removeAllPoints();
                        pts.addPoint(x1, y + LINK_END_Y_OFFSET);
                        pts.addPoint(x2, y + LINK_END_Y_OFFSET);

                        if (!connection.isVisible()) {
                            connection.setVisible(true);
                        }

                    } else {
                        if (connection.isVisible()) {
                            connection.setVisible(false);
                        }
                    }
                }
            }
        }

        @Override
        public Object getConstraint(Connection connection) {
            return null;
        }

        @Override
        public void invalidate(Connection connection) {
        }

        @Override
        public void remove(Connection connection) {
        }

        @Override
        public void setConstraint(Connection connection, Object constraint) {
        }

    }

    /**
     * Common code for start and end mapping line figure.
     * 
     * 
     * @author aallway
     * @since 3.3 (10 Jun 2010)
     */
    abstract class BaseLineStartEndFigure extends PolylineConnection {
        /** The mapping object associated with this line. */
        Collection<Mapping> mappings;

        /** The distance a click can be away from the actual line. */
        private int margin;

        /** The error control. */
        protected ImageFigure error;

        /** The error control. */
        protected ImageFigure warning;

        private IFigure annotation;

        BaseLineStartEndFigure(final Crossover crossover) {
            super();

            mappings = new ArrayList<Mapping>();

            margin = LINK_WIDTH / 2 + 1;

            setLineWidth(LINK_WIDTH);
            setTolerance(margin);

            // Must have AN anchor set in order for router to get called for the
            // connection line.
            // NOTE that each individual part of the line is a separate polyline
            // connection at the moment (in reality each mapping should have a
            // separate polyline but don't have time to do that right now.
            setSourceAnchor(dummyAnchor);
            setTargetAnchor(dummyAnchor);

            addMouseListener(new MouseListener() {

                @Override
                public void mouseDoubleClicked(MouseEvent me) {
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    if ((me.getState() & MouseEvent.CONTROL) == 0) {
                        mappingSelection.clear();
                    }
                    mappingSelection.addAll(mappings);
                    crossover.setFocus();
                    updateSelectionFromCrossover();
                    redrawTrees();
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }

            });
        }

        /**
         * @param x
         *            The x position.
         * @param y
         *            The y position.
         * @return The DragItem at that position or null.
         */
        public Collection<Mapping> getDragItem(int x, int y) {
            Collection<Mapping> item = null;
            if (containsPoint(x, y)) {
                item = mappings;
            }
            return item;
        }

        /**
         * @return true if the figure has mappings.
         */
        public boolean hasMappings() {
            return mappings.size() > 0;
        }

        /**
         * @param mapping
         *            The mapping to remove.
         */
        public void removeMapping(Mapping mapping) {
            mappings.remove(mapping);
        }

        /**
         * @param mapping
         *            The mapping to add.
         */
        public void addMapping(Mapping mapping) {
            if (!mappings.contains(mapping)) {
                mappings.add(mapping);
            }
        }

        /**
         * @return the error
         */
        public ImageFigure getError() {
            return error;
        }

        /**
         * @return the warning
         */
        public ImageFigure getWarning() {
            return warning;
        }

        /**
         * @param mapping
         * @return max severity of markers associated with mapping startr/end in
         *         quesiton.
         */
        protected abstract ErrorSeverity getMarkerSeverity(Mapping mapping);

        /**
         * 
         */
        public void updateMarker() {
            if (errorProvider != null) {
                ErrorSeverity severity = null;
                for (Mapping mapping : mappings) {
                    long b4 = System.currentTimeMillis();
                    ErrorSeverity current = getMarkerSeverity(mapping);

                    if (current == null) {
                        current = ErrorSeverity.NONE;
                    }

                    if (ErrorSeverity.ERROR.equals(current)) {
                        severity = current;
                        break;
                    } else if (ErrorSeverity.WARNING.equals(current)) {
                        severity = current;
                    }
                }
                if (ErrorSeverity.ERROR.equals(severity)) {
                    addError();
                } else {
                    if (error != null) {
                        remove(error);
                        error = null;
                    }
                    if (ErrorSeverity.WARNING.equals(severity)) {
                        addWarning();
                    } else {
                        if (warning != null) {
                            remove(warning);
                            warning = null;
                        }
                    }
                }
            }
        }

        /**
         * Adds an error marker to the line.
         */
        private void addError() {
            if (error == null) {
                Image image =
                        MapperActivator.getDefault().getImageCache()
                                .getImage(ImageCache.ERROR);
                error = new ImageFigure(image);
                Rectangle bounds = image.getBounds();
                error.setSize(bounds.width, bounds.height);

                add(error, new MappingLineMarkerLocator(ERROR_X_OFFSET));

            }
            if (error != null && mappings.size() > 0) {
                Mapping mapping = mappings.iterator().next();
                error.setToolTip(getErrorToolTip(mapping));
            }
        }

        /**
         * Adds an error marker to the line.
         */
        private void addWarning() {
            if (error == null && warning == null) {
                Image image =
                        MapperActivator.getDefault().getImageCache()
                                .getImage(ImageCache.WARNING);
                warning = new ImageFigure(image);
                Rectangle bounds = image.getBounds();
                warning.setSize(bounds.width, bounds.height);

                add(warning, new MappingLineMarkerLocator(ERROR_X_OFFSET));

            }
            if (warning != null && mappings.size() > 0) {
                Mapping mapping = mappings.iterator().next();
                warning.setToolTip(getErrorToolTip(mapping));
            }

        }

        public void dispose() {
            if (error != null) {
                remove(error);
                error = null;

            }
            if (warning != null) {
                remove(warning);
                warning = null;
            }

            if (annotation != null) {
                remove(annotation);
                annotation = null;
            }

        }

        /**
         * @param mapping
         * @return Get the MappingError for the given mapping.
         */
        protected abstract MappingError getMappingError(Mapping mapping);

        /**
         * @/*ram mapping The mapping.
         * @return The tool tip figure.
         */
        protected IFigure getErrorToolTip(Mapping mapping) {
            MappingError error = getMappingError(mapping);
            String text = error.getMessage();
            ToolTipFigure tooltip = new ToolTipFigure(text);
            return tooltip;
        }
    }

    /**
     * @author nwilson
     */
    class CrossoverLineFigureStart extends BaseLineStartEndFigure {

        /**
         * @param crossover
         *            The crossover canvas.
         */
        public CrossoverLineFigureStart(final Crossover crossover) {
            super(crossover);

            MappingLineDecorator dec =
                    new MappingLineDecorator(false, sourceViewer.getTree()
                            .getItemHeight());
            setSourceDecoration(dec);
            getLayoutManager().setConstraint(dec, dec.getLocator());
        }

        @Override
        public void paintFigure(Graphics graphics) {
            // Before painting children, configure the decorator.
            /*
             * When tree is filtered TreeItems can become disposed. So we should
             * behave as if the source/target object for mapping could not be
             * found (and hide the middle and source/target parts of line)
             */

            if (configureBeforePaint(graphics)) {
                this.getSourceDecoration().setVisible(true);
                if (error != null) {
                    error.setVisible(true);
                }
                if (warning != null) {
                    warning.setVisible(true);
                }

                super.paintFigure(graphics);

            } else {
                this.getSourceDecoration().setVisible(false);
                if (error != null) {
                    error.setVisible(false);
                }
                if (warning != null) {
                    warning.setVisible(false);
                }
            }
        }

        private boolean configureBeforePaint(Graphics graphics) {
            Mapping mapping = mappings.iterator().next();
            Object source = mapping.getSource();
            TreeItem sourceItem = findItem(sourceViewer, source);
            if (sourceItem == null) {
                sourceItem = findParentItem(sourceViewer, source);
            }

            /*
             * When tree is filtered TreeItems can become disposed. So we should
             * behave as if the source/target object for mapping could not be
             * found (and hide the middle and source/target parts of line)
             */
            if (sourceItem != null && !sourceItem.isDisposed()) {
                MappingLineDecorator decorator =
                        (MappingLineDecorator) this.getSourceDecoration();

                //
                // Configure the line.,
                //
                boolean isSelected = false;
                if (active == crossover
                        && !Collections.disjoint(mappingSelection, mappings)) {
                    isSelected = true;
                }

                if (isSelected) {
                    graphics.setForegroundColor(ColorConstants.blue);
                    decorator.setForegroundColor(ColorConstants.darkBlue);

                } else if (!mappingSelection.contains(mapping)
                        && mapping.getColor() != null) {
                    graphics.setForegroundColor(mapping.getColor());
                    decorator.setForegroundColor(mapping.getColor());

                } else if (filteringOn && isFilteredOut(mappings)) {
                    graphics.setForegroundColor(ColorConstants.lightGray);
                    decorator.setForegroundColor(ColorConstants.gray);

                } else {
                    graphics.setForegroundColor(ColorConstants.black);
                    decorator.setForegroundColor(ColorConstants.black);
                }

                //
                // Configure the decorator.
                //
                TreeItem sourceLinkItem =
                        getExpandedItem(sourceViewer, sourceItem);

                boolean exactSource = false;
                if (sourceLinkItem != null) {
                    exactSource = source.equals(sourceLinkItem.getData());
                }

                decorator.setTreeObjectCollapsed(!exactSource);

                // Adds decoration image to mapping if provide by mappings
                // provider
                decorator.setOverrideImage(getStartLineAnnotation());

                //
                // Update the markers
                //
                updateMarker();

                return true;
            }

            return false;
        }

        /**
         * 
         * @return The additional start line decoration (from first mapping
         *         model that starts with this line that has one)
         */
        private Image getStartLineAnnotation() {
            for (Mapping mapping : mappings) {
                if (mapping.getStartLineAnnotation() != null) {
                    return mapping.getStartLineAnnotation();
                }
            }
            return null;
        }

        @Override
        public void dispose() {
            super.dispose();
        }

        @Override
        protected MappingError getMappingError(Mapping mapping) {
            MappingError error =
                    errorProvider.getErrorsForMappingSource(mapping);
            return error;
        }

        @Override
        protected ErrorSeverity getMarkerSeverity(Mapping mapping) {
            ErrorSeverity current =
                    errorProvider.getSeverityForMappingSource(mapping);
            return current;
        }

    }

    /**
     * @author nwilson
     */
    class CrossoverLineFigure extends PolylineConnection {
        /** The fix between clicked and actual position. */
        private static final int LINE_SELECT_VERTICAL_FIX = 4;

        /** The mapping object associated with this line. */
        private Mapping mapping;

        /** The distance a click can be away from the actual line. */
        private int margin;

        /** The x coordinate of the first bend point. */
        private int x1;

        /** The x coordinate of the second bend point. */
        private int x2;

        /** The starting y coordinate. */
        private int y1;

        /** The ending y coordinate. */
        private int y2;

        /**
         * @param crossover
         *            The crossover canvas.
         * @param mapping
         *            The mapping object associated with this line.
         */
        public CrossoverLineFigure(final Crossover crossover,
                final Mapping mapping) {
            super();
            this.mapping = mapping;

            margin = LINK_WIDTH / 2 + 1;

            setLineWidth(LINK_WIDTH);
            setTolerance(margin);

            // Must have AN anchor set in order for router to get called for the
            // connection line.
            // NOTE that each individual part of the line is a separate polyline
            // connection at the moment (in reality each mapping should have a
            // separate polyline but don't have time to do that right now.
            setSourceAnchor(dummyAnchor);
            setTargetAnchor(dummyAnchor);

            addMouseListener(new MouseListener() {

                @Override
                public void mouseDoubleClicked(MouseEvent me) {
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    if ((me.getState() & MouseEvent.CONTROL) == 0) {
                        mappingSelection.clear();
                    }
                    if (mappingSelection.contains(mapping)) {
                        mappingSelection.remove(mapping);
                    } else {
                        mappingSelection.add(mapping);
                    }
                    crossover.setFocus();
                    updateSelectionFromCrossover();
                    redrawTrees();
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }

            });
        }

        /**
         * @param x
         *            The x position.
         * @param y
         *            The y position.
         * @return The DragItem at that position or null.
         */
        public Mapping getDragItem(int x, int y) {
            Mapping item = null;
            if (containsPoint(x, y)) {
                item = mapping;
            }
            return item;
        }

        @Override
        public void paintFigure(Graphics graphics) {
            // Before painting children, configure the decorator.
            configureBeforePaint(graphics);
            super.paintFigure(graphics);
        }

        private void configureBeforePaint(Graphics graphics) {
            Object source = mapping.getSource();
            Object target = mapping.getTarget();
            TreeItem sourceItem = findItem(sourceViewer, source);
            TreeItem targetItem = findItem(targetViewer, target);
            if (sourceItem == null) {
                sourceItem = findParentItem(sourceViewer, source);
            }
            if (targetItem == null) {
                targetItem = findParentItem(targetViewer, target);
            }
            if (sourceItem != null && targetItem != null) {
                //
                // Configure the line.,
                //
                boolean isSelected = false;
                if (active == crossover && mappingSelection.contains(mapping)) {
                    isSelected = true;
                }

                if (isSelected) {
                    graphics.setForegroundColor(PlatformUI.getWorkbench()
                            .getDisplay().getSystemColor(SWT.COLOR_BLUE));

                } else if (!mappingSelection.contains(mapping)
                        && mapping.getColor() != null) {
                    graphics.setForegroundColor(mapping.getColor());

                } else if (filteringOn
                        && isFilteredOut(Collections.singleton(mapping))) {
                    // Use v transparent Alpha blend black rather than light
                    // gray so that when unselected line is over selected line
                    // it doesn't appear to be 'on top'
                    graphics.setForegroundColor(ColorConstants.black);
                    graphics.setAlpha(65);
                    // graphics.setForegroundColor(ColorConstants.lightGray);

                } else {
                    graphics.setForegroundColor(ColorConstants.black);
                }

            }

            return;
        }

    }

    /**
     * MappingLineDecorator
     * <p>
     * Start/End point decorator for mapping lines.
     * 
     * @author aallway
     */
    private class MappingLineDecorator extends Figure implements
            RotatableDecoration {

        private Point refPoint = new Point(0, 0);

        private boolean isTreeObjectCollapsed = false;

        private boolean isEndMapping = false;

        private int listItemHeight = 0;

        private Color fgColor = ColorConstants.black;

        private Locator locator;

        private Image overrideImage = null;

        public MappingLineDecorator(boolean isEndMapping, int listItemHeight) {
            super();
            this.isEndMapping = isEndMapping;
            this.listItemHeight = listItemHeight;

            // Locator for decorator (start/end anchor).
            locator = new Locator() {
                @Override
                public void relocate(IFigure target) {

                    if (target.getParent() instanceof PolylineConnection) {
                        PointList pts =
                                ((PolylineConnection) target.getParent())
                                        .getPoints();

                        Dimension sz;

                        Point loc;

                        /*
                         * Sid XPD-7399 - Don't override decoration size with
                         * alternate image if we're not going to use it.
                         */
                        if (overrideImage != null && !isTreeObjectCollapsed) {
                            Rectangle imgBnds = overrideImage.getBounds();
                            sz =
                                    new Dimension(
                                            imgBnds.width,
                                            Math.min(imgBnds.height,
                                                    MappingLineDecorator.this.listItemHeight + 2));

                        } else {
                            sz =
                                    new Dimension(
                                            LINK_END_WIDTH,
                                            MappingLineDecorator.this.listItemHeight + 2);

                        }

                        if (!MappingLineDecorator.this.isEndMapping) {
                            // Start mapping decoration figure.
                            loc = pts.getFirstPoint();

                            loc.x -= (sz.width - 1);
                            if (loc.x < 0) {
                                loc.x = 0;
                            }
                            loc.y -= sz.height / 2;

                        } else {
                            loc = pts.getLastPoint();
                            int maxRight = (crossover.getBounds().width);
                            if (loc.x + sz.width > maxRight) {
                                loc.x = maxRight - sz.width;
                            }
                            loc.y -= sz.height / 2;
                        }

                        target.setBounds(new org.eclipse.draw2d.geometry.Rectangle(
                                loc, sz));

                    }
                    return;
                }
            };
        }

        /**
         * Set replacement image for end line annotation
         * 
         * @param overrideImage
         */
        public void setOverrideImage(Image overrideImage) {
            if (overrideImage == null) {
                if (this.overrideImage == null) {
                    return;
                }
            } else {
                if (overrideImage.equals(this.overrideImage)) {
                    return;
                }
            }

            this.overrideImage = overrideImage;

            if (getLocator() != null) {
                getLocator().relocate(this);
            }
            repaint();
        }

        /**
         * @return the locator
         */
        public Locator getLocator() {
            return locator;
        }

        @Override
        public void setForegroundColor(Color fg) {
            // Override the std setForeground color because we wish to do so
            // from the parent mapping line during a paint (and the std
            // setForegroundColor() performs a repaint().
            fgColor = fg;
        }

        /**
         * @param isTreeObjectCollapsed
         *            the isTreeObjectCollapsed to set
         */
        public void setTreeObjectCollapsed(boolean isTreeObjectCollapsed) {
            this.isTreeObjectCollapsed = isTreeObjectCollapsed;
        }

        @Override
        public void setReferencePoint(Point p) {
        }

        @Override
        protected void paintFigure(Graphics gc) {

            if (!isTreeObjectCollapsed) {

                if (overrideImage != null) {
                    Rectangle imgBnds = overrideImage.getBounds();

                    Point location = this.getBounds().getCenter();
                    location.x -= (imgBnds.width / 2) + (imgBnds.width % 2);
                    location.y -= (imgBnds.height / 2) + (imgBnds.height % 2);

                    gc.drawImage(overrideImage, location);

                } else {

                    gc.setBackgroundColor(ColorConstants.green);
                    gc.setForegroundColor(fgColor);

                    Point location = this.getBounds().getCenter();
                    location.x -=
                            (LINK_END_DECORATION_SIZE / 2)
                                    + (LINK_END_DECORATION_SIZE % 2);
                    location.y -=
                            (LINK_END_DECORATION_SIZE / 2)
                                    + (LINK_END_DECORATION_SIZE % 2);

                    gc.fillOval(location.x,
                            location.y,
                            LINK_END_DECORATION_SIZE,
                            LINK_END_DECORATION_SIZE);

                    gc.drawOval(location.x,
                            location.y,
                            LINK_END_DECORATION_SIZE,
                            LINK_END_DECORATION_SIZE);
                }

            } else {
                gc.setBackgroundColor(ColorConstants.red);
                gc.setForegroundColor(getParent().getForegroundColor());

                org.eclipse.draw2d.geometry.Rectangle b = getBounds().getCopy();
                int rightX = b.x + (b.width - 1);
                int midY = b.y + (b.height / 2);
                int botY = b.y + (b.height - 1);

                PointList points = new PointList();
                if (!isEndMapping) {
                    points.addPoint(b.x, botY);
                    points.addPoint(rightX, midY);
                    points.addPoint(rightX, botY);
                } else {
                    points.addPoint(b.x, botY);
                    points.addPoint(b.x, midY);
                    points.addPoint(rightX, botY);
                }

                gc.fillPolygon(points);
                gc.drawPolygon(points);
            }

            return;
        }

    }

    /**
     * @author nwilson
     */
    private class CrossoverLineFigureEnd extends BaseLineStartEndFigure {

        /** The highlight border colour. */
        private Color highlight = null;

        /** The normal border colour. */
        private Color normal = null;

        /** The x coordinate of the second bend point. */
        private int x2;

        /** The ending y coordinate. */
        private int y2;

        /** The height of the target item. */
        private int h2;

        /** The transform control. */
        private ImageFigure transform;

        /**
         * @param crossover
         *            The crossover canvas.
         */
        public CrossoverLineFigureEnd(final Crossover crossover) {
            super(crossover);

            MappingLineDecorator dec =
                    new MappingLineDecorator(true, targetViewer.getTree()
                            .getItemHeight());
            setTargetDecoration(dec);

            getLayoutManager().setConstraint(dec, dec.getLocator());

            normal =
                    PlatformUI.getWorkbench().getDisplay()
                            .getSystemColor(SWT.COLOR_BLACK);
            highlight =
                    PlatformUI.getWorkbench().getDisplay()
                            .getSystemColor(SWT.COLOR_BLUE);
        }

        /**
         * @return the transform
         */
        public ImageFigure getTransform() {
            return transform;
        }

        /**
         * Adds the transform icon to this line.
         */
        private void addTransform() {
            if (transform == null) {
                Image image = transformSection.getTransformImage();
                transform = new ImageFigure(image);
                transform.setOpaque(true);
                final LineBorder border =
                        new LineBorder(TRANSFORM_BORDER_WIDTH);
                border.setColor(normal);
                transform.setBorder(border);
                transform.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseDoubleClicked(MouseEvent me) {
                    }

                    @Override
                    public void mousePressed(MouseEvent me) {
                        if (me.button == 1) {
                            Point p = transform.getLocation();
                            if (mappings.size() > 0) {
                                Mapping mapping = mappings.iterator().next();
                                showTransformPanel(mapping,
                                        crossover.toDisplay(p.x, p.y));
                            }
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent me) {
                    }
                });
                transform.addMouseMotionListener(new MouseMotionListener() {
                    @Override
                    public void mouseDragged(MouseEvent me) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent me) {
                        border.setColor(highlight);
                        transform.repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent me) {
                        border.setColor(normal);
                        transform.repaint();
                    }

                    @Override
                    public void mouseHover(MouseEvent me) {
                    }

                    @Override
                    public void mouseMoved(MouseEvent me) {
                    }

                });

                Image errImg =
                        MapperActivator.getDefault().getImageCache()
                                .getImage(ImageCache.ERROR);
                Rectangle eb = errImg.getBounds();

                Image wrnImg =
                        MapperActivator.getDefault().getImageCache()
                                .getImage(ImageCache.WARNING);
                Rectangle wb = errImg.getBounds();

                add(getWarning(), new MappingLineMarkerLocator(
                        TRANSFORM_X_OFFSET), 0);

            }
        }

        /**
         * Cleans up the figure controls.
         */
        @Override
        public void dispose() {
            if (transform != null) {
                remove(transform);
                transform = null;
            }
            super.dispose();
        }

        @Override
        public void paintFigure(Graphics graphics) {
            // Before painting children, configure the decorator.
            /*
             * When tree is filtered TreeItems can become disposed. So we should
             * behave as if the source/target object for mapping could not be
             * found (and hide the middle and source/target parts of line)
             */
            if (configureBeforePaint(graphics)) {
                this.getTargetDecoration().setVisible(true);
                if (error != null) {
                    error.setVisible(true);
                }
                if (warning != null) {
                    warning.setVisible(true);
                }

                super.paintFigure(graphics);
            } else {
                this.getTargetDecoration().setVisible(false);
                if (error != null) {
                    error.setVisible(false);
                }
                if (warning != null) {
                    warning.setVisible(false);
                }
            }
        }

        private boolean configureBeforePaint(Graphics graphics) {
            Mapping mapping = mappings.iterator().next();
            Object target = mapping.getTarget();
            TreeItem targetItem = findItem(targetViewer, target);
            if (targetItem == null) {
                targetItem = findParentItem(targetViewer, target);
            }

            /*
             * When tree is filtered TreeItems can become disposed. So we should
             * behave as if the source/target object for mapping could not be
             * found (and hide the middle and source/target parts of line)
             */
            if (targetItem != null && !targetItem.isDisposed()) {
                MappingLineDecorator decorator =
                        (MappingLineDecorator) this.getTargetDecoration();

                //
                // Configure the line.,
                //
                boolean isSelected = false;
                if (active == crossover
                        && !Collections.disjoint(mappingSelection, mappings)) {
                    isSelected = true;
                }

                if (isSelected) {
                    graphics.setForegroundColor(ColorConstants.blue);
                    decorator.setForegroundColor(ColorConstants.darkBlue);

                } else if (!mappingSelection.contains(mapping)
                        && mapping.getColor() != null) {
                    graphics.setForegroundColor(mapping.getColor());
                    decorator.setForegroundColor(mapping.getColor());

                } else if (filteringOn && isFilteredOut(mappings)) {
                    graphics.setForegroundColor(ColorConstants.lightGray);
                    decorator.setForegroundColor(ColorConstants.gray);

                } else {
                    graphics.setForegroundColor(ColorConstants.black);
                    decorator.setForegroundColor(ColorConstants.black);
                }

                //
                // Configure the decorator.
                //
                TreeItem targetLinkItem =
                        getExpandedItem(targetViewer, targetItem);

                boolean exactTarget = false;
                if (targetLinkItem != null) {
                    exactTarget = target.equals(targetLinkItem.getData());
                }
                decorator.setTreeObjectCollapsed(!exactTarget);

                // Adds decoration image to mapping if provide by mappings
                // provider
                decorator.setOverrideImage(getEndLineAnnotation());

                //
                // Update the markers
                //
                updateMarker();

                if (exactTarget && transformSection != null) {
                    addTransform();
                } else if (transform != null) {
                    remove(transform);
                    transform = null;
                }

                return true;
            }

            return false;
        }

        /**
         * 
         * @return The additional end line decoration (from first mapping model
         *         that ends with this line that has one)
         */
        private Image getEndLineAnnotation() {
            for (Mapping mapping : mappings) {
                if (mapping.getEndLineAnnotation() != null) {
                    return mapping.getEndLineAnnotation();
                }
            }
            return null;
        }

        @Override
        protected MappingError getMappingError(Mapping mapping) {
            MappingError error =
                    errorProvider.getErrorsForMappingTarget(mapping);
            return error;
        }

        @Override
        protected ErrorSeverity getMarkerSeverity(Mapping mapping) {
            ErrorSeverity current =
                    errorProvider.getSeverityForMappingTarget(mapping);
            return current;
        }

    }

    /**
     * MappingLineMarkerLocator
     * <p>
     * Locator for image figures for markers on mapping line (for
     * problem/warning marker etc)
     * 
     * @author aallway
     */
    private class MappingLineMarkerLocator implements Locator {
        private int xOffset = 0;

        /**
         * @param i
         */
        public MappingLineMarkerLocator(int xOffset) {
            this.xOffset = xOffset;
        }

        @Override
        public void relocate(IFigure target) {
            if (target.getParent() instanceof PolylineConnection) {
                PolylineConnection conn =
                        (PolylineConnection) target.getParent();

                Point p;

                if (xOffset <= 0) {
                    p = conn.getPoints().getFirstPoint().getCopy();
                } else {
                    p =
                            XPDLineUtilities.getLinePointFromOffset(conn
                                    .getPoints(), xOffset);
                }

                Dimension sz = target.getSize();

                p.x -= sz.width / 2;
                p.y -= sz.height / 2;

                target.setLocation(p);
            }
            return;
        }

    }

    /**
     * Mapper control layout.
     * 
     * @author nwilson
     */
    class MapperLayout extends Layout {

        /** Width of source tree. */
        private int sourceWidth;

        /** Width of target tree. */
        private int targetWidth;

        TreeItem overrideTreeItem = null;

        boolean overrideItemState = false;

        /**
         * @param composite
         *            The composite.
         * @param wHint
         *            width hint.
         * @param hHint
         *            height hint.
         * @param flushCache
         *            true to flush cache.
         * @return The preferred size.
         * @see org.eclipse.swt.widgets.Layout#computeSize(org.eclipse.swt.widgets.Composite,
         *      int, int, boolean)
         */
        @Override
        protected org.eclipse.swt.graphics.Point computeSize(
                Composite composite, int wHint, int hHint, boolean flushCache) {

            if (wHint == SWT.DEFAULT) {
                return new org.eclipse.swt.graphics.Point(SWT.DEFAULT,
                        SWT.DEFAULT);
            }

            int treesPreferred = (wHint - DEFAULT_CROSSOVER_WIDTH) / 2;

            targetWidth = treesPreferred;
            sourceWidth = treesPreferred;

            return new org.eclipse.swt.graphics.Point(wHint, hHint);

        }

        /**
         * @param item
         *            The item.
         * @param max
         *            The current max.
         * @return The max width.
         */
        public int getMaxSourceColumnWidth(TreeItem[] items) {

            int prev = sourceColumn.getColumn().getWidth();

            // Set the column width to something stupid to ensure that the
            // getBounds() doesn't truncate at current column width.
            sourceColumn.getColumn().setWidth(16000); // Math.max(max,sourceWidth
            // ));

            int max = internalGetMaxSourceColumnWidth(items, 0, 0);

            sourceColumn.getColumn().setWidth(prev); // Math.max(max,sourceWidth)
            // );

            return max;
        }

        public int internalGetMaxSourceColumnWidth(TreeItem[] items, int max,
                int level) {
            if (items == null || items.length == 0) {
                return max;
            }

            for (TreeItem item : items) {
                Rectangle rectangle = item.getBounds();

                max = Math.max(max, rectangle.x + rectangle.width);

                // ON an item expanded event the item WILL NOT claim to be
                // expanded until AFTER the events have been processed.
                // So if this is the item that is being expanded / collapsed,
                // make the decision whether to recurs on the passed state
                // rather thyan the current state.
                boolean expanded;
                if (item == overrideTreeItem) {
                    expanded = overrideItemState;
                } else {
                    expanded = item.getExpanded();
                }

                if (expanded) {
                    max =
                            internalGetMaxSourceColumnWidth(item.getItems(),
                                    max,
                                    ++level);
                }
            }
            return max;
        }

        /**
         * @param composite
         *            The composite.
         * @param flushCache
         *            true to flush cache.
         * @see org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite,
         *      boolean)
         */
        @Override
        protected void layout(Composite composite, boolean flushCache) {
            Rectangle client = composite.getClientArea();

            // call compute bounds to set up sourceWidth/targetWidth and max
            // source column width reqd.
            computeSize(composite, client.width, client.height, true);

            Rectangle sourceBounds =
                    new Rectangle(0, 0, sourceWidth, client.height);

            Rectangle targetBounds =
                    new Rectangle(DEFAULT_CROSSOVER_WIDTH + sourceWidth, 0,
                            targetWidth, client.height);

            sourceFilteredTree.setBounds(sourceBounds);

            int max =
                    getMaxSourceColumnWidth(sourceViewer.getTree().getItems());

            sourceColumn.getColumn().setWidth(max);

            Rectangle srcTreeBounds = sourceViewer.getControl().getBounds();
            org.eclipse.swt.graphics.Point srcTreeLoc =
                    sourceViewer.getControl().toDisplay(srcTreeBounds.x,
                            srcTreeBounds.y);
            org.eclipse.swt.graphics.Point top =
                    composite.toDisplay(client.x, client.y);

            Rectangle sepBnds = sourceFilteredTree.separator.getBounds();
            /*
             * XPD-6203: crossover should start below the separator, so setting
             * its y coordinate accordingly
             */
            crossover.setBounds(sourceWidth,
                    sepBnds.y,
                    DEFAULT_CROSSOVER_WIDTH,
                    client.height);

            /*
             * Layout the new separator above the corssover mapping lines
             * control.
             */
            org.eclipse.swt.graphics.Point sepLoc =
                    sourceFilteredTree.toDisplay(sepBnds.x, sepBnds.y);

            crossOverSeparator.setBounds(sourceWidth,
                    sepLoc.y - top.y,
                    DEFAULT_CROSSOVER_WIDTH,
                    sepBnds.height);

            // targetViewer.getTree().setBounds(targetBounds);
            targetFilteredTree.setBounds(targetBounds);

            int seperatorWidth = 2;
            int showMappedButtonHeight = sepBnds.y;

            mappedContentSeperatorPre.setBounds(sourceWidth,
                    0,
                    seperatorWidth,
                    showMappedButtonHeight);

            showOrHideUnMappedContentsButton.setBounds(sourceWidth
                    + (seperatorWidth * 3),
                    0,
                    (DEFAULT_CROSSOVER_WIDTH - (seperatorWidth * 4)),
                    showMappedButtonHeight);

            mappedContentSeperatorPost
                    .setBounds(showOrHideUnMappedContentsButton.getBounds().x
                            + showOrHideUnMappedContentsButton.getBounds().width,
                            0,
                            seperatorWidth,
                            showMappedButtonHeight);

        }
    }

    /**
     * SourceOrTargetLabelWrapper
     * <p>
     * Wrapper for the source or target viewer label provider that can add
     * warning / error decorations to the nominal label icon when necessary.
     * 
     * 
     * @author aallway
     * @since 3.3 (11 Dec 2009)
     */
    private class SourceOrTargetLabelWrapper implements ILabelProvider,
            IColorProvider {

        private ILabelProvider delegateLabelProvider;

        private boolean isSourceViewer;

        SourceOrTargetLabelWrapper(ILabelProvider delegateLabelProvider,
                boolean isSourceViewer) {
            this.delegateLabelProvider = delegateLabelProvider;
            this.isSourceViewer = isSourceViewer;
        }

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
         */
        @Override
        public Image getImage(Object element) {

            Image originalImage = delegateLabelProvider.getImage(element);

            log("==> Mapper.SourceOrTargetLabelWrapper.getImage(%s)", element); //$NON-NLS-1$
            long start = System.currentTimeMillis();

            ErrorSeverity severity =
                    getMapperTreeContentSeverity(element,
                            ErrorSeverity.NONE,
                            isSourceViewer,
                            0,
                            new HashSet<Object>());
            if (severity == null) {
                severity = ErrorSeverity.NONE;
            }

            log("<== Mapper.SourceOrTargetLabelWrapper.getImage(%s): Took %d ms", //$NON-NLS-1$
                    element,
                    (System.currentTimeMillis() - start));

            if (ErrorSeverity.ERROR.equals(severity)) {
                if (originalImage != null) {
                    Image decoratedImage =
                            errorImageRegistry.get(originalImage);
                    if (decoratedImage == null) {
                        OverlayImageDescriptor decorator =
                                new OverlayImageDescriptor(
                                        originalImage.getImageData(),
                                        errorDecoratorImage.getImageData());

                        decoratedImage = decorator.createImage();
                        errorImageRegistry.put(originalImage, decoratedImage);
                    }

                    if (decoratedImage != null) {
                        return decoratedImage;
                    }
                }

                /*
                 * If cannot create an overly or there was no original image,
                 * then return the base error decorator.
                 */
                return errorDecoratorImage;

            } else if (ErrorSeverity.WARNING.equals(severity)) {
                if (originalImage != null) {
                    Image decoratedImage =
                            warningImageRegistry.get(originalImage);
                    if (decoratedImage == null) {
                        OverlayImageDescriptor decorator =
                                new OverlayImageDescriptor(
                                        originalImage.getImageData(),
                                        warningDecoratorImage.getImageData());

                        decoratedImage = decorator.createImage();
                        warningImageRegistry.put(originalImage, decoratedImage);
                    }

                    if (decoratedImage != null) {
                        return decoratedImage;
                    }
                }

                /*
                 * If cannot create an overly or there was no original image,
                 * then return the base warning decorator.
                 */
                return warningDecoratorImage;
            }

            return originalImage;
        }

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
         */
        @Override
        public String getText(Object element) {
            return delegateLabelProvider.getText(element);
        }

        /**
         * @param listener
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
         */
        @Override
        public void addListener(ILabelProviderListener listener) {
            delegateLabelProvider.addListener(listener);
        }

        /**
         * 
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
         */
        @Override
        public void dispose() {
            delegateLabelProvider.dispose();
        }

        /**
         * @param element
         * @param property
         * @return
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
         *      java.lang.String)
         */
        @Override
        public boolean isLabelProperty(Object element, String property) {
            return delegateLabelProvider.isLabelProperty(element, property);
        }

        /**
         * @param listener
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
         */
        @Override
        public void removeListener(ILabelProviderListener listener) {
            delegateLabelProvider.removeListener(listener);
        }

        /**
         * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public Color getForeground(Object element) {
            /*
             * Give oppportunity to delegate label provider to provider override
             * color.
             */
            if (delegateLabelProvider instanceof IColorProvider) {
                return ((IColorProvider) delegateLabelProvider)
                        .getForeground(element);
            }
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public Color getBackground(Object element) {
            /*
             * Give oppportunity to delegate label provider to provider override
             * color.
             */
            if (delegateLabelProvider instanceof IColorProvider) {
                return ((IColorProvider) delegateLabelProvider)
                        .getBackground(element);
            }
            return null;
        }

    }

    /**
     * TreeItemProblemTextTooltipProvider
     * <p>
     * Tooltip provider for setting tooltip to the list of problems raised
     * against a given source/target tree item.
     * 
     * @author aallway
     * @since 3.3 (21 Dec 2009)
     */
    private class TreeItemProblemTooltipProvider extends
            AbstractTreeItemToolTipProvider {

        private boolean isSourceTree;

        private TreeItem lastTreeItem = null;

        private String lastToolTip = null;

        /**
         * @param tree
         */
        public TreeItemProblemTooltipProvider(Tree tree, boolean isSourceTree) {
            super(tree);
            this.isSourceTree = isSourceTree;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.mapper.AbstractTreeItemTooltipProvider#getToolTipText
         * (org.eclipse.swt.widgets.TreeItem)
         */
        @Override
        protected String getToolTipText(TreeItem treeItem) {
            // if (true)
            //                return ""; //$NON-NLS-1$

            if (treeItem == lastTreeItem) {
                return lastToolTip;
            }

            lastToolTip = null;
            lastTreeItem = treeItem;

            Collection<String> errorsForObject;

            Object data = treeItem.getData();
            if (isSourceTree) {
                /*
                 * MR 39782: Serious Studio Mapper control performance problems.
                 * 
                 * Attempt to load from cache first.
                 */
                lastToolTip = sourceErrorMessagesCache.get(data);
                if (lastToolTip != null) {
                    return lastToolTip;
                }

                errorsForObject = errorProvider.getErrorsForSourceObject(data);
            } else {
                /*
                 * MR 39782: Serious Studio Mapper control performance problems.
                 * 
                 * Attempt to load from cache first.
                 */
                lastToolTip = targetErrorMessagesCache.get(data);
                if (lastToolTip != null) {
                    return lastToolTip;
                }

                errorsForObject = errorProvider.getErrorsForTargetObject(data);
            }

            if (errorsForObject != null && !errorsForObject.isEmpty()) {
                lastToolTip = getMessage(errorsForObject);
            } else {
                /*
                 * If there are no errors __specifically__ for this item then
                 * check if there are errors in descendents items.
                 */
                //                log("==> Mapper.TreeItemProblemTooltipProvider.getToolTipText(%s)", data); //$NON-NLS-1$
                long start = System.currentTimeMillis();

                ErrorSeverity severity =
                        getMapperTreeContentSeverity(data,
                                ErrorSeverity.NONE,
                                isSourceTree,
                                0,
                                new HashSet<Object>());

                if (severity == null) {
                    severity = ErrorSeverity.NONE;
                }

                //                log("<== Mapper.TreeItemProblemTooltipProvider.getToolTipText(%s): Took %d ms", //$NON-NLS-1$
                // data,
                // (System.currentTimeMillis() - start));

                if (!ErrorSeverity.NONE.equals(severity)) {
                    lastToolTip =
                            Messages.getString("Mapper.ProblemsWithDescendents_tooltip"); //$NON-NLS-1$
                }
            }

            if (lastToolTip == null) {
                lastToolTip = ""; //$NON-NLS-1$
            }

            /*
             * MR 39782: Serious Studio Mapper control performance problems.
             * 
             * Cache the result for quick access next time.
             */
            if (isSourceTree) {
                sourceErrorMessagesCache.put(data, lastToolTip);
            } else {
                targetErrorMessagesCache.put(data, lastToolTip);
            }

            return lastToolTip;
        }

        private String getMessage(Collection<String> messages) {
            int numMsgs = 0;

            StringBuffer buffer = new StringBuffer();
            boolean first = true;
            for (String message : messages) {
                if (numMsgs++ > 5) {
                    break;
                }
                if (first) {
                    first = false;
                } else {
                    buffer.append('\n');
                }
                if (messages.size() > 1) {
                    buffer.append("-"); //$NON-NLS-1$
                }
                buffer.append(message);
            }
            return buffer.toString();
        }

    }

    /**
     * MR 39782: Serious Studio Mapper control performance problems.
     * <p>
     * Clear out any entry in the cache of object -> parent-object's TreeItem
     * which references an expanding tree item.
     * 
     * 
     * @author aallway
     * @since 3.3 (24 Dec 2009)
     */
    private class ParentTreeItemCacheResetListener implements TreeListener {

        TreeViewer treeViewer;

        /**
         * @param treeViewer
         */
        public ParentTreeItemCacheResetListener(TreeViewer treeViewer) {
            super();
            this.treeViewer = treeViewer;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.TreeListener#treeExpanded(org.eclipse.swt.
         * events.TreeEvent)
         */
        @Override
        public void treeExpanded(TreeEvent e) {
            /*
             * Clear out any entry in the cache of object -> parent-object's
             * TreeItem which has the expanding tree item.
             * 
             * Then any object that currently says
             * "my grandfather is my closest expanded ancestor" will be
             * recalculated on next findPArentItem and then will find it's
             * father's TreeItem instead (becaus ethe grandfatehr has just been
             * expanded and that will have created the father TreeItem).
             */
            TreeViewerTreeItemCache parentTreeItemCache =
                    getParentTreeItemCache(treeViewer);

            // parentTreeItemCache.reset();

            List<Object> toRemove = new ArrayList<Object>();

            for (Entry<Object, TreeItem> objectToClosestAncestorEntry : parentTreeItemCache.treeItemCache
                    .entrySet()) {
                if (objectToClosestAncestorEntry.getValue() == e.item) {
                    /*
                     * A cached parent TreeItem is being expanded - remove all
                     * the entries from object->closest allocated parentObject's
                     * TreeItem
                     */
                    toRemove.add(objectToClosestAncestorEntry.getKey());
                }
            }

            for (Object rem : toRemove) {
                parentTreeItemCache.treeItemCache.remove(rem);
            }

            return;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.TreeListener#treeCollapsed(org.eclipse.swt
         * .events.TreeEvent)
         */
        @Override
        public void treeCollapsed(TreeEvent e) {
            // Don't need to worry about collapse.
        }

    }

    /**
     * @param string
     */
    public void log(String string, Object... args) {
        if (loggingOn) {
            System.out.println(String.format(string, args));
        }

        return;
    }

    /**
     * Shows/Hides unmapped contents from the Mapper, based on the boolean value
     * of show.Hides unmapped contents if false value is passed for show.Only
     * applies changed state [show/hide] if it is different than the current
     * state, hence avoiding unneccessary updates.
     * 
     * @param showMapped
     *            , true to show Only mapped contents, false to show all.
     */
    protected void showOnlyMappedContents(boolean showMapped) {
        if (showOnlyMappedContents != showMapped) {
            showOnlyMappedContents = showMapped;
            if (showOnlyMappedContents) {
                sourceViewer.addFilter(sourceMappedContentFilter);
                targetViewer.addFilter(targetMappedContentFilter);
                resetTreeItemCache(sourceViewer);
                resetTreeItemCache(targetViewer);
                crossover.update();
            } else {
                sourceViewer.removeFilter(sourceMappedContentFilter);
                targetViewer.removeFilter(targetMappedContentFilter);
                resetTreeItemCache(sourceViewer);
                resetTreeItemCache(targetViewer);
                crossover.update();
            }
        }
    }

    /**
     * Check if both of the tree items are involved in ANY phsyical mapping link
     * (NOT necesarily the same one)
     * 
     * @param sourceTreeItem
     * @param targetTreeItem
     * 
     * @return <code>true</code> if both of the tree items are involved in ANY
     *         phsyical mapping link (NOT necesarily the same one)
     * 
     */
    private boolean treeItemsAreMapped(TreeItem sourceTreeItem,
            TreeItem targetTreeItem) {

        boolean sourceItemHasPhysicalMapping = false;
        boolean targetItemHasPhysicalMapping = false;

        Object sourceItemData =
                (sourceTreeItem != null ? sourceTreeItem.getData() : null);
        Object targetItemData =
                (targetTreeItem != null ? targetTreeItem.getData() : null);

        for (Mapping explicitMapping : mappings) {
            if (sourceItemData != null
                    && sourceItemData.equals(explicitMapping.getSource())) {
                sourceItemHasPhysicalMapping = true;
            }

            if (targetItemData != null
                    && targetItemData.equals(explicitMapping.getTarget())) {
                targetItemHasPhysicalMapping = true;
            }

            if (sourceItemHasPhysicalMapping && targetItemHasPhysicalMapping) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return The selected mappings.
     */
    public Collection<Mapping> getSelectedMappings() {
        return mappingSelection;
    }

    /**
     * @return the mappings
     */
    public Collection<Mapping> getMappings() {
        return mappings;
    }

    /**
     * Filter to hide/show un-mapped data from the Mapper.This filter selects an
     * object if it is one of the mapped Object OR a parent in the hierarchy of
     * any Mapped object.
     * 
     * @author aprasad
     * @since 06-Dec-2013
     */
    class MappedDataFilter {
        boolean source;

        MappedDataFilter(boolean source) {
            this.source = source;
        }

        public ViewerFilter buildFilter() {
            return new ViewerFilter() {

                @Override
                public boolean select(Viewer viewer, Object parentElement,
                        Object toTest) {

                    List<Object> mappedObjects = new ArrayList<Object>();

                    for (Mapping mapping : mappings) {
                        if (source) {
                            mappedObjects.add(mapping.getSource());
                        } else {
                            mappedObjects.add(mapping.getTarget());
                        }
                    }
                    ITreeContentProvider contentProvider =
                            (ITreeContentProvider) ((TreeViewer) viewer)
                                    .getContentProvider();

                    // for each mapped object
                    for (Object mappedObject : mappedObjects) {

                        Object parent = mappedObject;
                        while (parent != null) {
                            // if mapped object/parent matches test object
                            // RETURN true
                            if (parent.equals(toTest)) {
                                return true;
                            } else {
                                // check for the parent
                                parent = contentProvider.getParent(parent);
                            }
                        }

                    }
                    // Neither Mapped Objects nor any of their parents in the
                    // hierarchy matches
                    return false;
                }

            };

        }

    }

}
