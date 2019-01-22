/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.internal.ui.palette.editparts.DrawerEditPart;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleRulerVisibilityAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.palette.editparts.IPinnableEditPart;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.views.palette.PalettePage;
import org.eclipse.gef.ui.views.palette.PaletteViewerPage;
import org.eclipse.help.HelpSystem;
import org.eclipse.help.IContext;
import org.eclipse.help.IContextProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.INavigationHistory;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ActivityIconProviderDescriptor;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper.GlobalDestinationInfo;
import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;
import com.tibco.xpd.gmf.extensions.palette.ButtonStackPaletteEditPartFactory;
import com.tibco.xpd.gmf.extensions.palette.GefButtonStackPaletteViewerProvider;
import com.tibco.xpd.processwidget.HadToShrinkImageException;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.actions.AlignAction;
import com.tibco.xpd.processwidget.actions.CutCopyPasteActionHandler;
import com.tibco.xpd.processwidget.actions.DeleteObjectActionHandler;
import com.tibco.xpd.processwidget.actions.RenameDiagramObjectAction;
import com.tibco.xpd.processwidget.actions.SelectAllActionHandler;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.annotations.AnnotationFactory;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryProvider;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryProviderEx;
import com.tibco.xpd.processwidget.commands.internal.XPDPasteCommand;
import com.tibco.xpd.processwidget.dragdrop.LocalSelectionTransferDropTargetListener;
import com.tibco.xpd.processwidget.dragdrop.ProcessFragmentDragSourceListener;
import com.tibco.xpd.processwidget.dragdrop.ProcessFragmentDropTargetListener;
import com.tibco.xpd.processwidget.figures.IHighlightableFigure;
import com.tibco.xpd.processwidget.figures.LaneFigure;
import com.tibco.xpd.processwidget.figures.PaginationLayer;
import com.tibco.xpd.processwidget.figures.PoolFigure;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.TaskFigure;
import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;
import com.tibco.xpd.processwidget.figures.XPDGridLayer;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.parts.ProcessWidgetEditPartFactory;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;
import com.tibco.xpd.processwidget.policies.EditPolicyEnablementProvider;
import com.tibco.xpd.processwidget.process.progression.EditPartHighlighter;
import com.tibco.xpd.processwidget.tools.PaletteFactory;
import com.tibco.xpd.processwidget.tools.ToolContextHelpUpdater;
import com.tibco.xpd.processwidget.tools.XPDTemplateTransferDragSourceListener;
import com.tibco.xpd.processwidget.tools.XPDTemplateTransferDropTargetListener;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;
import com.tibco.xpd.processwidget.viewer.GEFtoEMFCommandStack;
import com.tibco.xpd.processwidget.viewer.NavigationListener;
import com.tibco.xpd.processwidget.viewer.OverviewViewPage;
import com.tibco.xpd.processwidget.viewer.WidgetPartsLabelProvider;
import com.tibco.xpd.processwidget.viewer.WidgetTextActionHandlers;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.XpdColorRegistry;
import com.tibco.xpd.ui.actions.AddEObjectBookmarkAction;
import com.tibco.xpd.ui.actions.ShowPropertiesViewAction;
import com.tibco.xpd.ui.actions.ShowViewAction;

/**
 * Process Editor Widget <br>
 * This graphical editor provide graphical editin capability to EMF model, the
 * model have to provide implementation of ProcessAtapters that map actual model
 * to graphical elements <br>
 * <ol>
 * Usage:
 * <li>create an instance
 * <li>configure with EditingDomain {@see #setEditingDomain}
 * <li>configure with AdapterFactory for ProcessAdapters {@see
 * #setAdapterFactory}
 * <li><i>(optional)</i> configure wit Site {@see #setSite}
 * <li>create control {@see #createControl}
 * </ol>
 * 
 * @author wzurek
 */
public class ProcessWidgetImpl implements ProcessWidget,
        ToolContextHelpUpdater, DiagramModelImageProvider {

    private static final String FEEDBACK_RECT = "FEEDBACK_RECT"; //$NON-NLS-1$

    private static final String PROCESS_WIDGET_HELP_CONTEXT =
            "com.tibco.xpd.analyst.doc.process_editor"; //$NON-NLS-1$

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    private static final String DEST_ENV = "DEST_ENV"; //$NON-NLS-1$

    private EditingDomain editingDomain;

    private AdapterFactory adapterFactory;

    private GraphicalViewer graphViewer;

    private Composite root;

    private Composite splitter;

    private IWorkbenchSite site;

    private Preferences preferences;

    private IContentOutlinePage overview;

    private CustomPalettePage page;

    private EditDomain editDomain;

    private GefButtonStackPaletteViewerProvider paletteViewerProvider;

    private Object textHandlers;

    private List navigationListeners = new ArrayList(1);

    private ActionRegistry actions;

    private IContextProvider contextProvider;

    private Map pickers = new HashMap();

    private String toolContext = null;

    private EObject input;

    private boolean annotate;

    /** XPD-1140: Allow tag process editor figure as readonly. */
    private boolean readOnly = false;

    private ProcessWidgetType processWidgetType =
            ProcessWidgetType.BPMN_PROCESS;

    private ProcessWidgetDelegatingPolicyEnablementProvider delegatingEditPolicyEnablementProvider =
            new ProcessWidgetDelegatingPolicyEnablementProvider();

    private EditPartHighlighter referenceHighlighter = null;

    // Comparator to ensure event on task border is painted after tasks.
    // (actually, for now we'll cheat and just say any event/gateway etc.)
    private Comparator taskBorderEventsLastSort = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 instanceof ShapeWithDescriptionFigure
                    && !(o2 instanceof ShapeWithDescriptionFigure)) {
                return 1;
            } else if (o2 instanceof ShapeWithDescriptionFigure
                    && !(o1 instanceof ShapeWithDescriptionFigure)) {
                return -1;
            }
            return 0;
        }
    };

    private PaletteRoot paletteRoot;

    private PaletteFactory paletteFactory;

    private EditPolicyEnablementProvider editPolicyEnablementProvider;

    private boolean controlsSetFromInput = false;

    private Set<ProcessEditorObjectType> excludedObjectTypes;

    private List<ActivityIconProviderDescriptor> activityIconProviders =
            Collections.emptyList();

    /**
     * Little graphics class that allows creat image with highlights to override
     * the normal figure's request to set the things that we use for
     * highlighting objects (alpha level and line width). FixedAlphaGraphics
     * 
     */
    private class FixedHighlightGraphics extends SaveAsImageGraphics {
        private Integer fixedLineWidth = null;

        private Color highlightFillColor;

        private Color highlightLineColor;

        private Color origFillColor;

        private Color origLineColor;

        private boolean doHighlightFillColor = false;

        private boolean doHighlightLineColor = false;

        private boolean isLowlightAlpha = false;

        private int lastSetAlpha = 255;

        private int lastBgColorAlpha = 255;

        private static final int LOWLIGHT_ALPHA_VAL = 70;

        public FixedHighlightGraphics(GC gc) {
            super(gc);

            highlightFillColor =
                    XpdColorRegistry.getDefault().getColor(null, 200, 0, 0);
            highlightLineColor =
                    XpdColorRegistry.getDefault().getColor(null, 255, 0, 0);

            origFillColor = super.getBackgroundColor();
            origLineColor = super.getForegroundColor();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.SWTGraphics#dispose()
         */
        @Override
        public void dispose() {
            super.dispose();

        }

        /**
         * @param isLowlightAlpha
         *            the isLowlightAlpha to set
         */
        public void setLowlightAlpha(boolean isLowlightAlpha) {
            this.isLowlightAlpha = isLowlightAlpha;

            if (!isLowlightAlpha) {
                super.setAlpha(255);
            } else {
                super.setAlpha(lastSetAlpha);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.SWTGraphics#setAlpha(int)
         */
        @Override
        public void setAlpha(int alpha) {
            lastSetAlpha = alpha;

            if (!isLowlightAlpha) {
                super.setAlpha(alpha);
                return;
            }

            // Set the actual alpha value to the correct portion of the lowlight
            // alpha.
            double alphaRatio = (double) LOWLIGHT_ALPHA_VAL / (double) 255;

            int newAlpha = (int) (alpha * alphaRatio);

            int minimumAlpha = 1;

            super.setAlpha(Math.max(minimumAlpha, Math.min(255, newAlpha)));

            return;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.SWTGraphics#pushState()
         */
        @Override
        public void pushState() {
            super.setAlpha(lastSetAlpha);
            super.pushState();
            setAlpha(lastSetAlpha);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.draw2d.SWTGraphics#popState()
         */
        @Override
        public void popState() {
            super.popState();
            setAlpha(lastSetAlpha);
        }

        public void setDoHighlightFillColor(boolean doHighlight) {
            this.doHighlightFillColor = doHighlight;

            setBackgroundColor(origFillColor);
        }

        public void setDoHighlightLineColor(boolean doHighlight) {
            this.doHighlightLineColor = doHighlight;

            setForegroundColor(origLineColor);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.draw2d.SWTGraphics#setBackgroundColor(org.eclipse.swt
         * .graphics.Color)
         */
        @Override
        public void setBackgroundColor(Color color) {
            origFillColor = color;

            // Always allow set to white fill color so that objects that want to
            // blank out contents can do so (embedded sub-proc content)!
            boolean isWhite =
                    (color.getBlue() == 255 && color.getRed() == 255 && color
                            .getGreen() == 255);
            if (!doHighlightFillColor || isWhite) {
                super.setBackgroundColor(color);
            } else {
                super.setBackgroundColor(highlightFillColor);
            }

            // When white is selected as a background color, if the last set
            // alpha was 255 then override the fixed alpha setting so that blank
            // with white fills work ok.
            if (isWhite && lastSetAlpha == 255) {
                super.setAlpha(255); // Force full alpha

            } else {
                setAlpha(lastSetAlpha); // set back to relative alpha.
            }
        }

        @Override
        public void setForegroundColor(Color color) {
            origLineColor = color;

            if (!doHighlightLineColor) {
                super.setForegroundColor(color);
            } else {
                super.setForegroundColor(highlightFillColor);
            }

        }

        public void setFixedLineWidth(Integer lineWidth) {
            fixedLineWidth = lineWidth;

            if (fixedLineWidth != null) {
                super.setLineWidth(fixedLineWidth.intValue());
            } else {
                super.setLineWidth(1);
            }
        }

        @Override
        public void setLineWidth(int width) {
            // If a fixed line width is set then ignore caller requests to
            // change.
            if (fixedLineWidth == null) {
                super.setLineWidth(width);
            }
        }

        /**
         * @return the highlightFillColor
         */
        public Color getHighlightFillColor() {
            return highlightFillColor;
        }

        /**
         * @return the highlightLineColor
         */
        public Color getHighlightLineColor() {
            return highlightLineColor;
        }

    }

    /**
     * A custom PalettePage that helps GraphicalEditorWithFlyoutPalette keep the
     * two PaletteViewers (one displayed in the editor and the other displayed
     * in the PaletteView) in sync when switching from one to the other (i.e.,
     * it helps maintain state across the two viewers).
     * 
     * @author Pratik Shah
     * @since 3.0
     */
    protected class CustomPalettePage extends PaletteViewerPage {
        /**
         * Constructor
         * 
         * @param provider
         *            the provider used to create a PaletteViewer
         */
        public CustomPalettePage(PaletteViewerProvider provider) {
            super(provider);
        }

        /**
         * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
         */
        @Override
        public void createControl(Composite parent) {
            super.createControl(parent);
            if (splitter instanceof FlyoutPaletteComposite)
                ((FlyoutPaletteComposite) splitter).setExternalViewer(viewer);
        }

        /**
         * @see org.eclipse.ui.part.IPage#dispose()
         */
        @Override
        public void dispose() {
            if (splitter instanceof FlyoutPaletteComposite) {
                ((FlyoutPaletteComposite) splitter).setExternalViewer(null);
            }

            super.dispose();
        }

        /**
         * @return the PaletteViewer created and displayed by this page
         */
        public PaletteViewer getPaletteViewer() {
            return viewer;
        }
    }

    /**
     * The Constructor
     * 
     * @param annotate
     *            , true if the widget should ask for annotations from external
     *            files
     */
    public ProcessWidgetImpl(boolean annotate,
            ProcessWidgetType processWidgetType) {
        super();
        this.annotate = annotate;
        this.processWidgetType = processWidgetType;
    }

    /**
     * @return the editingDomain
     */
    public EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * Set editing domain to use in the widget
     * 
     * @param editingDomain
     */
    @Override
    public void setEditingDomain(EditingDomain editingDomain) {
        if (graphViewer != null) {
            throw new IllegalArgumentException(
                    "Widget is alredy created, you cannot change editing domain"); //$NON-NLS-1$
        }
        this.editingDomain = editingDomain;
    }

    @Override
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    /**
     * Set adapter factory that can produce adapters expected by the widget.
     * (@see ProcessWidgetConstants#ADAPTER_TYPE)<br>
     * Note: the adapter factory, and expected adapters are different then
     * ItemProviders generated by EMF
     * 
     * @param adapterFactory
     */
    @Override
    public void setAdapterFactory(AdapterFactory adapterFactory) {
        if (!adapterFactory
                .isFactoryForType(ProcessWidgetConstants.ADAPTER_TYPE)) {
            throw new IllegalArgumentException(
                    "Adapter Factory have to be able to produce model adapters expected by the Widget"); //$NON-NLS-1$
        }
        if (graphViewer != null) {
            throw new IllegalArgumentException(
                    "Widget is alredy created, you cannot change adapter factory"); //$NON-NLS-1$
        }
        this.adapterFactory = adapterFactory;
    }

    /**
     * Get the adapter factory for set for this process widget.
     * 
     * @return
     */
    public AdapterFactory getAdapterFactory() {
        return this.adapterFactory;
    }

    /**
     * Returns root control of the widget
     * 
     * @return root control of the widget
     */
    @Override
    public Control getControl() {
        return root;// graphViewer.getControl();
    }

    /**
     * Set site where WorkbenchPart that contains the widget belongs
     * 
     * @param site
     */
    @Override
    public void setSite(IWorkbenchSite site) {
        this.site = site;
    }

    /**
     * Get site where WorkbenchPart that contains the widget belongs
     * 
     * @return site
     */
    @Override
    public IWorkbenchSite getSite() {
        return this.site;
    }

    /**
     * Setup an instance ProcessFragmentTransfer (drag-n-drop data container for
     * process fragments). for currentlky selected diagram edit parts.
     * 
     * Changed to FragmentLocalSelectionTransfer to have the process fragments
     * incorporated with the fragments framework provided.
     */
    public boolean setupProcessFragmentTransfer(
            FragmentLocalSelectionTransfer transfer,
            Collection selectedEditParts) {
        boolean ret = false;

        //
        // Set up the transfer data.
        if (selectedEditParts == null) {
            ISelection selection = getSelection();

            if (selection instanceof IStructuredSelection) {
                selectedEditParts =
                        ToolUtilities
                                .getSelectionWithoutDependants(((IStructuredSelection) selection)
                                        .toList());
            }

        }

        if (selectedEditParts.size() < 1) {
            return false;
        }

        // Get list of model objects that represent the selection.
        Collection selectedModelObjects =
                EditPartUtil.getModelObjectsFromSelection(selectedEditParts);

        if (selectedModelObjects != null) {
            ProcessDiagramAdapter adapter =
                    (ProcessDiagramAdapter) getWidgetAdapter(getInput());

            // Get a copy complete list of objects required to define the
            // selected objects.
            Collection transferObjects =
                    adapter.getCopyObjects(getEditingDomain(),
                            selectedModelObjects,
                            false);

            if (transferObjects != null && transferObjects.size() > 0) {

                transfer.setSelection(new StructuredSelection(transferObjects));

                List<ModelAdapterEditPart> modelEditParts =
                        new ArrayList<ModelAdapterEditPart>();
                for (Iterator iter = selectedEditParts.iterator(); iter
                        .hasNext();) {
                    Object ep = iter.next();

                    if (ep instanceof ModelAdapterEditPart) {
                        modelEditParts.add((ModelAdapterEditPart) ep);
                    }
                }

                if (modelEditParts.size() > 0) {
                    // Add rectangles of the top level objects.
                    transfer.getProperties()
                            .put(FEEDBACK_RECT,
                                    getDndTransferFeedbackRectangles(selectedEditParts));
                    transfer.getProperties().put(DEST_ENV,
                            getDestinationEnvs(adapter));
                    // transfer.setSourceOwner(this);
                    ret = true;
                }
            }
        }

        return ret;
    }

    private Collection<GlobalDestinationInfo> getDestinationEnvs(
            ProcessDiagramAdapter adapter) {
        return adapter.getGlobalDestinationsAssociated();
    }

    /**
     * @param selection
     * @return
     */
    private Collection<Rectangle> getDndTransferFeedbackRectangles(
            Collection selection) {

        List editParts = new ArrayList();

        for (Iterator iter = selection.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof ModelAdapterEditPart) {
                ModelAdapterEditPart part = (ModelAdapterEditPart) obj;
                editParts.add(part);
            }
        }

        // Get list of top level objects
        List parentList =
                ToolUtilities.getSelectionWithoutDependants(editParts);

        Collection<Rectangle> feedbackRects = new ArrayList<Rectangle>();

        Rectangle bnds = null;

        for (Iterator iter = parentList.iterator(); iter.hasNext();) {
            EditPart ep = (EditPart) iter.next();

            if (ep instanceof BaseGraphicalEditPart) { // Exclude connections!
                BaseGraphicalEditPart gep = (BaseGraphicalEditPart) ep;

                // Get figure bounds
                IFigure figure = gep.getFigure();
                Rectangle rc = figure.getBounds().getCopy();

                // Translate to absolute then de-scale so that all figures are
                // on
                // even grounds.
                figure.translateToAbsolute(rc);

                double scale = XPDFigureUtilities.getFigureScale(figure);
                rc.scale(1 / scale);

                feedbackRects.add(rc);

                if (bnds == null) {
                    bnds = rc.getCopy();
                } else {
                    bnds.union(rc);
                }
            }
        }

        // Zero offset all the rects.
        for (Rectangle rc : feedbackRects) {
            rc.x -= bnds.x;
            rc.y -= bnds.y;
        }

        return feedbackRects;
    }

    /**
     * Create Image of process displayed in the Widget, the method has to be
     * invoked from UI thread. The client is responsible for disposing the
     * image.
     * 
     * @return Image of the process
     */
    @Override
    public Image createProcessImage() {
        try {
            return createProcessImageEx();
        } catch (HadToShrinkImageException e) {
            // Default behaviour is to return the shrunken image and be done
            // with it.
            return e.getShrunkenImage();
        }
    }

    @Override
    public Image createProcessImageEx() throws HadToShrinkImageException {
        WidgetRootEditPart wr =
                (WidgetRootEditPart) graphViewer.getRootEditPart();
        IFigure fig = wr.getLayer(LayerConstants.PRINTABLE_LAYERS);
        Device d = Display.getDefault();

        Dimension diagramExt = wr.getDiagramExtent().getCopy();
        Dimension origRequiredExt = diagramExt.getCopy();

        //
        // BIG images can't be created on some platforms. So we will scale the
        // image down until the image creation succeeds.
        //

        Image img = null;
        double scale = 1.0;

        while (((diagramExt.width * diagramExt.height) * 3) > 16000000
                && scale > 0.001) {
            scale = scale * 0.9;

            diagramExt.width = (int) (origRequiredExt.width * scale);
            diagramExt.height = (int) (origRequiredExt.height * scale);
        }

        while (img == null && scale > 0.001) {
            try {
                diagramExt.width = (int) (origRequiredExt.width * scale);
                diagramExt.height = (int) (origRequiredExt.height * scale);

                img = new Image(d, diagramExt.width, diagramExt.height);

            } catch (Exception e) {
                // BIG images can't be created on some platforms. So we will
                // scale the
                // image down until the image creation succeeds.
                scale = scale * 0.75;
            } catch (Throwable t) {
                // BIG images can't be created on some platforms. So we will
                // scale the
                // image down until the image creation succeeds.
                scale = scale * 0.75;
            }
        }

        if (img != null) {
            GC gc = new GC(img);

            gc.setBackground(fig.getBackgroundColor());
            gc.setForeground(fig.getForegroundColor());
            gc.setFont(fig.getFont());

            SWTGraphics graphics = new SaveAsImageGraphics(gc);
            graphics.scale(scale);

            IFigure content = wr.getFigure();
            content.validate();
            fig.paint(graphics);
            graphics.dispose();
            gc.dispose();

            if (scale < 1.0) {
                // If we had to shrink the image to keep it within system limits
                // for
                // size then create and throw an exception to tell the caller
                // all
                // about it.
                HadToShrinkImageException hadToShrink =
                        new HadToShrinkImageException(img, scale,
                                diagramExt.width, diagramExt.height,
                                origRequiredExt.width, origRequiredExt.height);
                throw hadToShrink;
            }

            return img;
        }

        return null;
    }

    @Override
    public Collection<ObjectPositionInfo> getObjectPositionInfo() {
        List<ObjectPositionInfo> objPositions =
                new ArrayList<ObjectPositionInfo>();

        //
        // Get printable image layer figure for the entire process.
        WidgetRootEditPart wr =
                (WidgetRootEditPart) graphViewer.getRootEditPart();
        if (wr != null) {
            IFigure pLayer = wr.getLayer(LayerConstants.PRINTABLE_LAYERS);

            /*
             * Recursively select and add object positions of anything with a
             * named element adapter
             */
            getChildObjsPosInfo(wr, pLayer, objPositions);

        }

        return objPositions;
    }

    private void getChildObjsPosInfo(EditPart parentEP, IFigure printableLayer,
            List<ObjectPositionInfo> objPositions) {

        /* Collect info on children */
        Collection children = parentEP.getChildren();
        for (Iterator iterator = children.iterator(); iterator.hasNext();) {
            EditPart ep = (EditPart) iterator.next();

            /*
             * recurse thru this edit part's children. Do this first so that
             * children appear before parent
             * 
             * Tho if it's a closed lane or pool, don't recurse to do children.
             */
            boolean recurs = true;
            if (ep instanceof PoolEditPart) {
                if (((PoolEditPart) ep).isClosed()) {
                    recurs = false;
                }
            } else if (ep instanceof LaneEditPart) {
                if (((LaneEditPart) ep).isClosed()) {
                    recurs = false;
                }
            }

            if (recurs) {
                getChildObjsPosInfo(ep, printableLayer, objPositions);
            }

            if (ep instanceof GraphicalEditPart) {
                /* Get position info for this edit part. */
                ObjectPositionInfo pos =
                        getObjPosInfo((GraphicalEditPart) ep, printableLayer);
                if (pos != null) {
                    objPositions.add(pos);
                }
            }
        }

        return;

    }

    private ObjectPositionInfo getObjPosInfo(GraphicalEditPart ep,
            IFigure printableLayer) {

        if (ep instanceof ConnectionEditPart || ep instanceof ProcessEditPart) {
            return null;
        }

        Object modelObj = ep.getModel();
        if (modelObj instanceof EObject) {
            Object adapter = getWidgetAdapter((EObject) modelObj);
            if (adapter instanceof NamedElementAdapter) {
                String id = ((NamedElementAdapter) adapter).getId();
                String name = ((NamedElementAdapter) adapter).getName();

                IFigure fig = ep.getFigure();

                if (fig instanceof PoolFigure) {
                    fig = ((PoolFigure) fig).getHeaderFigure();
                } else if (fig instanceof LaneFigure) {
                    fig = ((LaneFigure) fig).getHeaderFigure();
                }

                Rectangle bnds = fig.getBounds();
                fig.translateToAbsolute(bnds);
                printableLayer.translateToRelative(bnds);

                ObjectPositionInfo pos = new ObjectPositionInfo();
                pos.objId = id;
                pos.bounds = bnds;
                pos.objName = name;

                return pos;
            }
        }
        return null;
    }

    /**
     * Create all controls and viewers. This method require that adapter factory
     * and editing domain is already provided
     * 
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {
        if (editingDomain == null || adapterFactory == null
                || getInput() == null) {
            throw new IllegalStateException(
                    "Editing Domain or Adapter Factory or Input is not set"); //$NON-NLS-1$
        }

        /*
         * XPD-2516: Get the configurable object type exclusions from
         * processEditorCOnfiguration ext point.
         */
        excludedObjectTypes =
                ProcessEditorConfigurationUtil
                        .getExcludedObjectTypes(getInput());

        /* And the ActivityIconProvider's applicable to this process. */
        activityIconProviders =
                ProcessEditorConfigurationUtil
                        .getActivityIconProviders(getInput());

        root = new Composite(parent, SWT.NONE);
        GridLayout gl = new GridLayout(1, false);
        gl.marginBottom = 0;
        gl.marginHeight = 0;
        gl.marginLeft = 0;
        gl.marginRight = 0;
        gl.marginTop = 0;
        gl.marginWidth = 0;

        root.setLayout(gl);

        IWorkbench workbench = null;
        if (PlatformUI.isWorkbenchRunning()) {
            workbench = PlatformUI.getWorkbench();
        }
        if (workbench != null) {
            PlatformUI.getWorkbench().getHelpSystem()
                    .setHelp(root, PROCESS_WIDGET_HELP_CONTEXT);
        }

        IWorkbenchPage workbenchPage = null;
        if (site == null) {
            if (workbench != null
                    && workbench.getActiveWorkbenchWindow() != null) {
                workbenchPage =
                        workbench.getActiveWorkbenchWindow().getActivePage();
            }
        } else {
            workbenchPage = site.getPage();
            if (site instanceof IEditorSite) {
                textHandlers =
                        new WidgetTextActionHandlers(
                                ((IEditorSite) site).getActionBars());
            } else if (site instanceof IViewSite) {
                textHandlers =
                        new WidgetTextActionHandlers(
                                ((IViewSite) site).getActionBars());
            }
        }

        editDomain = createEditDomain();

        createPalette(editDomain);

        if (workbenchPage != null) {
            splitter =
                    new FlyoutPaletteComposite(root, SWT.NONE, workbenchPage,
                            paletteViewerProvider, getPalettePreferences());
        } else {
            splitter = new Composite(root, SWT.NONE);
        }
        splitter.setLayoutData(new GridData(GridData.FILL_BOTH));

        if (page != null && splitter instanceof FlyoutPaletteComposite) {
            ((FlyoutPaletteComposite) splitter).setExternalViewer(page
                    .getPaletteViewer());
            page = null;
        }

        graphViewer = createGraphicalViewer();

        // create viewer's control
        graphViewer.createControl(splitter);

        graphViewer.setContextMenu(createContextMenu());

        if (splitter instanceof FlyoutPaletteComposite) {
            ((FlyoutPaletteComposite) splitter).setGraphicalControl(graphViewer
                    .getControl());
        }

        // widgetContributor = new ProcessWidgetContributor(this);
        // widgetContributor.createContribution(site, editingDomain);

        // contribute to navigation history
        if (site != null) {
            graphViewer
                    .addSelectionChangedListener(new ISelectionChangedListener() {

                        @Override
                        public void selectionChanged(SelectionChangedEvent event) {
                            updateHistory();
                        }
                    });
        }

        setupProcessFragmentDrop();

        // NOTE:

        setupProcessFragmentDragCreation();

        /*
         * Set up the parts of control dependent on the input/
         */
        setControlsFromInput();

        parent.addDisposeListener(new DisposeListener() {

            @Override
            public void widgetDisposed(DisposeEvent e) {
                dispose();
            }
        });

    }

    /**
     * Dispose resources.
     */
    private void dispose() {
        if (actions != null) {
            actions.dispose();
            actions = null;
        }

        if (page != null) {
            page.dispose();
        }
    }

    /**
     * Setup the process widget as a drag source for process fragment creation.
     * 
     * When user Ctrl+Drag's diagram objects onto another view window, if that
     * window is a drop target for ProcessFragmentTransfer type then it will be
     * passed the model objects (the same data as adapter would provide for
     * clipboard copy/paste).
     * 
     * Note:
     * 
     * Ctrl+Drag... When you add a drag source listener to GEF EditPArtViewer it
     * disables 'normal' drag drop of edit parts (within same editor) whenever
     * the listener's dragStart is called.
     * 
     * So in order to allow BOTH normal drag-move of edit parts AND native
     * drag-n-drop to another view window, we say that if Ctrl is pressed before
     * drag start then it enables native drag-n-drop, else it is a normal edit
     * part move.
     */
    private void setupProcessFragmentDragCreation() {
        //
        // Setup the drag source listener for process fragment creation
        // transfer.
        //
        graphViewer
                .addDragSourceListener(new ProcessFragmentDragSourceListener(
                        this));

    }

    /**
     * Setup process widget as a drop target listener. Then anything that
     * supports native drag-n-drop ProcessFragmentTransfer type transfer can
     * drop model objects (same data as used for paste) on us.
     */
    private void setupProcessFragmentDrop() {
        graphViewer
                .addDropTargetListener(new ProcessFragmentDropTargetListener(
                        this));

        graphViewer
                .addDropTargetListener(new LocalSelectionTransferDropTargetListener(
                        this));

        graphViewer
                .addDropTargetListener(new XPDTemplateTransferDropTargetListener(
                        graphViewer));

    }

    protected void updateHistory() {
        INavigationHistory nh = site.getPage().getNavigationHistory();
        nh.markLocation(site.getPage().getActiveEditor());
    }

    protected GraphicalViewer createGraphicalViewer() {
        BpmnScrollingGraphicalViewer graphViewer =
                new BpmnScrollingGraphicalViewer();

        // set EMF editing domain for this viewer
        graphViewer.setProperty(ProcessWidgetConstants.PROP_EDITING_DOMAIN,
                editingDomain);
        graphViewer.setProperty(ProcessWidgetConstants.PROP_TEXT_HANDLERS,
                textHandlers);
        graphViewer.setProperty(ProcessWidgetConstants.PROP_WIDGET, this);
        graphViewer.setProperty(ProcessWidgetConstants.PROP_LABEL_PROVIDER,
                new WidgetPartsLabelProvider());

        graphViewer.setEditDomain(editDomain);

        // edit part factory that depends on provided adapter factory
        graphViewer.setEditPartFactory(new ProcessWidgetEditPartFactory(
                adapterFactory));

        // ScalableFreeformRootEditPart that supports ShortcutsLayer
        WidgetRootEditPart rootEditPart = new WidgetRootEditPart();
        graphViewer.setRootEditPart(rootEditPart);

        Dimension gridSpace =
                new Dimension(ProcessWidgetConstants.SNAPGRID_SIZE,
                        ProcessWidgetConstants.SNAPGRID_SIZE);
        graphViewer.setProperty(SnapToGrid.PROPERTY_GRID_SPACING, gridSpace);

        KeyHandler keyHandler = createKeyHandler(graphViewer);
        keyHandler.setParent(getCommonKeyHandler(graphViewer));

        graphViewer.setKeyHandler(keyHandler);

        // ZOOM Stuff...
        // Override default zoom levels of 50, 100, 150 etc
        ZoomManager zoomMgr = rootEditPart.getZoomManager();
        double[] zooms =
                new double[] { 0.1d, 0.25d, 0.5d, 0.75d, 1.0d, 1.25d, 1.5d,
                        2.0d, 3.0d };
        zoomMgr.setZoomLevels(zooms);

        List zoomLevels = new ArrayList(3);
        zoomLevels.add(ZoomManager.FIT_ALL);
        zoomLevels.add(ZoomManager.FIT_WIDTH);
        zoomLevels.add(ZoomManager.FIT_HEIGHT);
        zoomMgr.setZoomLevelContributions(zoomLevels);

        // Scroll-wheel Zoom
        graphViewer
                .setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1),
                        new MouseWheelHandler() {
                            @Override
                            public void handleMouseWheel(Event event,
                                    EditPartViewer viewer) {
                                ZoomManager zoomMgr =
                                        (ZoomManager) viewer
                                                .getProperty(ZoomManager.class
                                                        .toString());
                                if (zoomMgr != null) {
                                    double zoom = zoomMgr.getZoom();

                                    if (event.count > 0) {
                                        // Zoom in...
                                        zoom += 0.1;
                                    } else {
                                        // zoom out
                                        zoom -= 0.1;
                                    }
                                    if (zoom >= zoomMgr.getMinZoom()
                                            && zoom <= zoomMgr.getMaxZoom()) {
                                        zoomMgr.setZoom(zoom);
                                    }
                                    event.doit = false;
                                }
                            }
                        });

        if (site != null && site instanceof IWorkbenchPartSite) {
            IAction zoomIn = new ZoomInAction(zoomMgr);
            IAction zoomOut = new ZoomOutAction(zoomMgr);

            // TODO WZ: find out how new Site services work (see javadoc of
            // IKeyBindingService)
            IKeyBindingService keyBindingService =
                    ((IWorkbenchPartSite) site).getKeyBindingService();

            keyBindingService.registerAction(zoomIn);
            keyBindingService.registerAction(zoomOut);
        }

        graphViewer.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                graphicalPropertyChanged(evt);
            }
        });

        return graphViewer;
    }

    protected void graphicalPropertyChanged(PropertyChangeEvent evt) {
        IAction act;

        // refresh snap actions
        act = getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY);
        act.setChecked(act.isChecked());
        act = getAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY);
        act.setChecked(act.isChecked());
    }

    protected MenuManager createContextMenu() {
        MenuManager manager = new MenuManager();

        manager.add(new Separator("object")); //$NON-NLS-1$
        manager.add(new Separator("visual")); //$NON-NLS-1$
        manager.add(new Separator(IWorkbenchActionConstants.M_EDIT));
        manager.add(new Separator("properties")); //$NON-NLS-1$
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        manager.add(new ShowPropertiesViewAction());
        manager.add(new ShowViewAction(
                "com.tibco.xpd.fragments.view", Messages.ProcessWidgetImpl_ShowDiagramFragments_menu, null)); //$NON-NLS-1$
        manager.add(new ShowViewAction(
                "org.eclipse.ui.views.BookmarkView", Messages.ProcessWidgetImpl_ShowBookmarksView_menu, null)); //$NON-NLS-1$

        if (site != null)
            ((IEditorSite) site).registerContextMenu(manager, graphViewer);

        manager.addMenuListener(new ProcessWidgetContextMenuListener());

        return manager;
    }

    /**
     * Instal diagam extensions, i.e. simulation figure decorators
     * 
     * @param input
     */
    protected void installExtensions(Object input) {

        IExtensionRegistry registry = Platform.getExtensionRegistry();

        IExtension[] exts =
                registry.getExtensionPoint(ProcessWidgetPlugin.ID,
                        "diagramAnnotation").getExtensions(); //$NON-NLS-1$

        List<AnnotationFactory> factories = new ArrayList<AnnotationFactory>();
        for (int i = 0; i < exts.length; i++) {
            IConfigurationElement[] elems = exts[i].getConfigurationElements();
            if (elems.length == 1) {
                try {
                    AnnotationFactoryProvider p =
                            (AnnotationFactoryProvider) elems[0]
                                    .createExecutableExtension("factory"); //$NON-NLS-1$

                    AnnotationFactory f;
                    if (p instanceof AnnotationFactoryProviderEx) {
                        ProcessDiagramAdapter adapter =
                                (ProcessDiagramAdapter) getWidgetAdapter(getInput());

                        f =
                                ((AnnotationFactoryProviderEx) p)
                                        .createAnnotationFactory(adapter,
                                                graphViewer);
                    } else {
                        f = p.getAnnotationFactory();
                    }

                    if (f != null) {
                        factories.add(f);
                    }
                } catch (CoreException e) {
                    ProcessWidgetPlugin
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    "Error installing annotation factory from contributor: " //$NON-NLS-1$
                                            + elems[0].getContributor()
                                                    .getName());
                }
            }
        }
        if (factories.size() != 0) {
            getGraphicalViewer()
                    .setProperty(ProcessWidgetConstants.PROP_ANNOTATION_FACTORY,
                            factories);
        }
    }

    /**
     * Uninstall and clean up any extensions (such as contributed annotation
     * factories)
     */
    protected void uninstallExtensions() {
        List<AnnotationFactory> factories =
                (List<AnnotationFactory>) getGraphicalViewer()
                        .getProperty(ProcessWidgetConstants.PROP_ANNOTATION_FACTORY);
        if (factories != null) {
            for (AnnotationFactory annotationFactory : factories) {
                annotationFactory.disposeFactory();
            }
        }
    }

    private KeyHandler getCommonKeyHandler(BpmnScrollingGraphicalViewer viewer) {
        KeyHandler sharedKeyHandler = new KeyHandler();
        // sharedKeyHandler.put(KeyStroke.getPressed(SWT.F2, 0),
        // getActionRegistry().getAction(DirectEditAction.ID));
        // sharedKeyHandler.put(KeyStroke.getPressed((char) 1, 97, SWT.CONTROL),
        // getActionRegistry().getAction(SelectAllAction.ID));
        return sharedKeyHandler;
    }

    /**
     * @return
     */
    protected EditDomain createEditDomain() {
        // set default GEF edit domain for gef diagram, with command stack that
        // wraps EMF command stack.
        EditDomain ed = new EditDomain();
        ed.setCommandStack(new GEFtoEMFCommandStack(editingDomain));

        return ed;
    }

    /**
     * @param ed
     */
    private void createPalette(EditDomain ed) {
        paletteFactory = new PaletteFactory(this);
        paletteRoot = paletteFactory.createPalette(this, excludedObjectTypes);
        ed.setPaletteRoot(paletteRoot);

        // USe Standard GMF / GEF extensions palette viewer (that enables use of
        // button stacker tool.
        paletteViewerProvider =
                new GefButtonStackPaletteViewerProvider(
                        ed,
                        ProcessWidgetPlugin.ID,
                        ButtonStackPaletteEditPartFactory.USE_BTNSTACK_ALL_DROPDOWNS
                                | ButtonStackPaletteEditPartFactory.USE_BTNSTACK_ALL_DRAWERS) {
                    @Override
                    protected void configurePaletteViewer(PaletteViewer viewer) {
                        super.configurePaletteViewer(viewer);

                        viewer.addDragSourceListener(new XPDTemplateTransferDragSourceListener(
                                viewer));
                        return;
                    }
                };

        paletteViewerProvider.allowUserCustomisation(ProcessWidgetPlugin
                .getDefault().getPreferenceStore(),
                paletteRoot,
                true,
                PaletteEntry.PERMISSION_FULL_MODIFICATION);
    }

    /**
     * @param gv
     * @return
     */
    protected KeyHandler createKeyHandler(GraphicalViewer gv) {
        GraphicalViewerKeyHandler kh = new GraphicalViewerKeyHandler(gv);
        return kh;
    }

    /**
     * Set input for the viewer - the root model element
     * 
     * @param input
     */
    @Override
    public void setInput(EObject input) {
        this.input = input;
    }

    /**
     * Setup the parts of the widget that depend upon the input.
     */
    private void setControlsFromInput() {
        if (this.input == null) {
            throw new IllegalStateException("The input has not been set."); //$NON-NLS-1$
        }

        if (annotate) {
            /*
             * Sid. Tidying up annotation extensions (used to only call
             * AnnotationFactory.disposeFactory() when old disposeListener()
             * (now uninstalLExtensions()) method was called but that was only
             * called by process editor on forced reload rather than normal
             * close.
             * 
             * Anyway, we setup the factories here so WE should dispos them here
             * ourselves.
             */
            installExtensions(input);

            if (graphViewer.getControl() != null) {
                graphViewer.getControl()
                        .addDisposeListener(new DisposeListener() {

                            @Override
                            public void widgetDisposed(DisposeEvent e) {
                                uninstallExtensions();
                            }
                        });
            }
        }

        graphViewer.setContents(input);

        if (actions == null) {
            createActions();
        }

        if (actions != null) {
            Object adapter = getWidgetAdapter(input);
            if (adapter instanceof ProcessDiagramAdapter) {
                IUndoContext undoContext =
                        ((ProcessDiagramAdapter) adapter).getUndoContext();

                UndoActionHandler undo =
                        (UndoActionHandler) actions
                                .getAction(ActionFactory.UNDO.getId());

                undo.setContext(undoContext);

                RedoActionHandler redo =
                        (RedoActionHandler) actions
                                .getAction(ActionFactory.REDO.getId());

                redo.setContext(undoContext);

            } else {
                throw new RuntimeException(
                        "Expected input to adapt to ProcessDiagramAdapter"); //$NON-NLS-1$
            }
        }

        return;
    }

    /**
     * @return the FlyoutPreferences object used to save the flyout palette's
     *         preferences
     */
    protected FlyoutPreferences getPalettePreferences() {
        FlyoutPreferences prefs = new FlyoutPreferences() {
            public final static String DOCK_LOCATION =
                    "ProcessWidgetPaletteDockLocation"; //$NON-NLS-1$

            public final static String PALETTE_STATE =
                    "ProcessWidgetPaletteState"; //$NON-NLS-1$

            public final static String PALETTE_WIDTH =
                    "ProcessWidgetPaletteWidth"; //$NON-NLS-1$

            @Override
            public int getDockLocation() {
                preferences.setDefault(DOCK_LOCATION, -1);
                return preferences.getInt(DOCK_LOCATION);
            }

            @Override
            public int getPaletteState() {
                preferences.setDefault(PALETTE_STATE, -1);
                return preferences.getInt(PALETTE_STATE);
            }

            @Override
            public int getPaletteWidth() {
                preferences.setDefault(PALETTE_WIDTH, -1);
                return preferences.getInt(PALETTE_WIDTH);
            }

            @Override
            public void setDockLocation(int location) {
                preferences.setValue(DOCK_LOCATION, location);
            }

            @Override
            public void setPaletteState(int state) {
                preferences.setValue(PALETTE_STATE, state);
            }

            @Override
            public void setPaletteWidth(int width) {
                preferences.setValue(PALETTE_WIDTH, width);
            }
        };
        return prefs;
    }

    /**
     * Create adapters for: <li><code>IContentOutlinePage.class</code> - the
     * overview page <li><code>Image.class</code> - create image with smapshot
     * of the process <li><code>PalettePage.class</code> - external palete
     * viewer <li><code>ProcessWidget.class</code> - returns self
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == IContentOutlinePage.class) {
            if (overview == null
                    || (overview.getControl() != null && overview.getControl()
                            .isDisposed())) {
                overview = new OverviewViewPage(this);
            }
            return overview;
        } else if (adapter == EditingDomain.class) {
            return editingDomain;
        } else if (adapter == ProcessWidget.class) {
            return this;
        } else if (adapter == CommandStack.class) {
            return graphViewer.getEditDomain().getCommandStack();
        } else if (adapter == Image.class) {
            return createProcessImage();
        } else if (adapter == IContextProvider.class) {
            if (contextProvider == null) {
                contextProvider = new IContextProvider() {
                    @Override
                    public int getContextChangeMask() {
                        return IContextProvider.SELECTION;
                    }

                    @Override
                    public IContext getContext(Object target) {
                        if (toolContext != null) {
                            IContext ctx = HelpSystem.getContext(toolContext);
                            toolContext = null;
                            return ctx;
                        }
                        List parts =
                                getGraphicalViewer().getSelectedEditParts();
                        if (parts.isEmpty() || parts.size() > 1) {
                            return HelpSystem
                                    .getContext(PROCESS_WIDGET_HELP_CONTEXT);
                        }
                        EditPart ep = (EditPart) parts.get(0);
                        IContext ctx = (IContext) ep.getAdapter(IContext.class);
                        if (ctx == null) {
                            return HelpSystem
                                    .getContext(PROCESS_WIDGET_HELP_CONTEXT);
                        }
                        return ctx;
                    }

                    @Override
                    public String getSearchExpression(Object target) {
                        return Messages.ProcessWidgetImpl_Search_label;
                    }
                };
            }
            return contextProvider;
        } else if (adapter == PalettePage.class) {
            if (splitter == null) {
                page = createPalettePage();
                return page;
            }
            return createPalettePage();
        } else if (adapter == GraphicalViewer.class) {
            return getGraphicalViewer();
        } else if (adapter == ZoomManager.class) {
            return getGraphicalViewer()
                    .getProperty(ZoomManager.class.toString());
        }

        return null;
    }

    /**
     * @return a newly-created {@link CustomPalettePage}
     */
    protected CustomPalettePage createPalettePage() {
        return new CustomPalettePage(paletteViewerProvider);
    }

    @Override
    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        graphViewer.removeSelectionChangedListener(listener);
        graphViewer.addSelectionChangedListener(listener);
    }

    @Override
    public ISelection getSelection() {
        return graphViewer.getSelection();
    }

    @Override
    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        graphViewer.removeSelectionChangedListener(listener);
    }

    @Override
    public void setSelection(ISelection selection) {
        graphViewer.setSelection(selection);
    }

    /**
     * @return
     */
    @Override
    public GraphicalViewer getGraphicalViewer() {
        return graphViewer;
    }

    /**
     * Add new navigation listener
     * 
     * @param navigationListener
     */
    @Override
    public void addNavigationListener(NavigationListener navigationListener) {
        if (!navigationListeners.contains(navigationListener)) {
            navigationListeners.add(navigationListener);
        }
    }

    /**
     * Remove navigation listener
     * 
     * @param navigationListener
     */
    @Override
    public void removeNavigationListener(NavigationListener navigationListener) {
        navigationListeners.remove(navigationListener);
    }

    /**
     * Navigate to this object, it ask registered navigation listeners or locate
     * the object on the diagram
     * 
     * @param object
     *            that should be revealed
     */
    @Override
    public void navigateTo(Object object) {
        boolean done = false;
        for (Iterator iter = navigationListeners.iterator(); iter.hasNext();) {
            NavigationListener nl = (NavigationListener) iter.next();
            done = nl.revealObject(object);
            if (done) {
                break;
            }
        }
        if (!done) {
            if (object instanceof EditPart) {
                if (graphViewer instanceof BpmnScrollingGraphicalViewer) {
                    ((BpmnScrollingGraphicalViewer) graphViewer)
                            .reveal((EditPart) object, true);
                } else {
                    graphViewer.reveal((EditPart) object);
                }
            } else {
                EditPart ep =
                        (EditPart) graphViewer.getEditPartRegistry()
                                .get(object);
                if (ep != null) {
                    if (graphViewer instanceof BpmnScrollingGraphicalViewer) {
                        ((BpmnScrollingGraphicalViewer) graphViewer).reveal(ep,
                                true);
                    } else {
                        graphViewer.reveal(ep);
                    }
                }
            }
        }
    }

    /**
     * Show printer pages margins on the layer on top of the diagram. Set null
     * to hide this layer
     * 
     * @param screenPageSize
     *            Size in (unzoomed)screen pixels of 1 (unzoomed) print page
     * @param fitType
     *            One of {@link ProcessWidgetContants}.PRINT_FIT_ values.
     * @param printZoom
     *            Print zoom level (i.e. the zoom from the setup dialog.
     */
    @Override
    public void updatePrintMargins(Dimension screenPageSize, int fitType,
            double printZoom) {
        LayerManager lm =
                (LayerManager) getGraphicalViewer().getEditPartRegistry()
                        .get(LayerManager.ID);
        PaginationLayer pl =
                (PaginationLayer) lm
                        .getLayer(ProcessWidgetConstants.PAGINATION_LAYER);

        boolean currShow = pl.isPaginationShowing();

        pl.setShowPagination(false); // don't repaint for each change!
        pl.setPageSize(screenPageSize);
        pl.setFitType(fitType);
        pl.setPrintZoom(printZoom);

        WidgetRootEditPart wr =
                (WidgetRootEditPart) getGraphicalViewer().getRootEditPart();
        pl.setDiagramSize(wr.getDiagramExtent());

        pl.setShowPagination(currShow);

        return;
    }

    /**
     * Show the on screen pagination print margins.
     * 
     */
    @Override
    public void showPrintMargins() {
        LayerManager lm =
                (LayerManager) getGraphicalViewer().getEditPartRegistry()
                        .get(LayerManager.ID);
        PaginationLayer pl =
                (PaginationLayer) lm
                        .getLayer(ProcessWidgetConstants.PAGINATION_LAYER);

        pl.setShowPagination(true);
    }

    /**
     * Hide the on screen pagination print margins.
     * 
     */
    @Override
    public void hidePrintMargins() {
        LayerManager lm =
                (LayerManager) getGraphicalViewer().getEditPartRegistry()
                        .get(LayerManager.ID);
        PaginationLayer pl =
                (PaginationLayer) lm
                        .getLayer(ProcessWidgetConstants.PAGINATION_LAYER);

        pl.setShowPagination(false);
    }

    /**
     * @see #showPrintMargins(Dimension)
     * @return true if diagam is showing pages margins
     */
    @Override
    public boolean isShowingPrintMargins() {
        LayerManager lm =
                (LayerManager) getGraphicalViewer().getEditPartRegistry()
                        .get(LayerManager.ID);
        PaginationLayer pl =
                (PaginationLayer) lm
                        .getLayer(ProcessWidgetConstants.PAGINATION_LAYER);

        return (pl.isPaginationShowing());
    }

    /**
     * Return action associated to the graphical viewer with given ID, returns
     * null when not found.
     * 
     * @param id
     * @return
     */
    @Override
    public IAction getAction(String id) {
        if (actions == null) {
            createActions();
        }
        return actions.getAction(id);
    }

    /**
     * Create set of actions for graphical viewer
     */
    protected void createActions() {
        if (getSite() != null) {
            actions = new ActionRegistry();
            actions.registerAction(new ToggleSnapToGeometryAction(graphViewer));
            actions.registerAction(new ToggleGridAction(graphViewer));

            AlignAction act;
            int[] ids =
                    new int[] { PositionConstants.LEFT,
                            PositionConstants.CENTER, PositionConstants.RIGHT,
                            PositionConstants.TOP, PositionConstants.MIDDLE,
                            PositionConstants.BOTTOM };

            for (int i = 0; i < ids.length; i++) {
                act = new AlignAction(ids[i]);
                act.setViewer(graphViewer);
                actions.registerAction(act);
            }

            actions.registerAction(new ToggleGridAction(graphViewer));
            actions.registerAction(new ToggleSnapToGeometryAction(graphViewer));
            actions.registerAction(new ToggleRulerVisibilityAction(graphViewer));

            // SID ZOOM
            actions.registerAction(new ZoomInAction(
                    ((WidgetRootEditPart) graphViewer.getRootEditPart())
                            .getZoomManager()));
            actions.registerAction(new ZoomOutAction(
                    ((WidgetRootEditPart) graphViewer.getRootEditPart())
                            .getZoomManager()));

            actions.registerAction(new SelectAllActionHandler(this));

            actions.registerAction(new RenameDiagramObjectAction(this));

            actions.registerAction(new CutCopyPasteActionHandler(this,
                    CutCopyPasteActionHandler.CUT));
            actions.registerAction(new CutCopyPasteActionHandler(this,
                    CutCopyPasteActionHandler.COPY));
            actions.registerAction(new CutCopyPasteActionHandler(this,
                    CutCopyPasteActionHandler.PASTE));

            UndoActionHandler undo =
                    new UndoActionHandler((IWorkbenchPartSite) getSite(), null);
            undo.setId(ActionFactory.UNDO.getId());
            actions.registerAction(undo);

            RedoActionHandler redo =
                    new RedoActionHandler((IWorkbenchPartSite) getSite(), null);
            redo.setId(ActionFactory.REDO.getId());
            actions.registerAction(redo);

            actions.registerAction(new DeleteObjectActionHandler(graphViewer));
            actions.registerAction(new AddEObjectBookmarkAction(graphViewer));
        }

        //
        // PLEASE NOTE: Just registering an action here is NOT enough to get a
        // standard action handler (for standard keystrokes etc) to work).
        // You must also go to the AbstractProcessDiagramEditorContributor and
        // ensure that the action is set as a global action handler.
        //
    }

    @Override
    public void update(String context) {
        toolContext = context;
        setSelection(getSelection());
    }

    @Override
    public EObject getInput() {
        return input;
    }

    public Adapter getWidgetAdapter(EObject eo) {
        return adapterFactory.adapt(eo, ProcessWidgetConstants.ADAPTER_TYPE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.ModelToImage#getImageFromModel(java
     * .util.Collection, java.util.Set)
     */
    @Override
    public Image getDiagramImageFromModel(Collection areaObjects,
            Collection selectionObjects, Collection highlightObjects,
            Dimension sizeHint, int margin, int flags) {

        //
        // Get printable imgae layer figure for the entire process.
        WidgetRootEditPart wr =
                (WidgetRootEditPart) graphViewer.getRootEditPart();
        IFigure pLayer = wr.getLayer(LayerConstants.PRINTABLE_LAYERS);

        Dimension diagramExt = wr.getDiagramExtent();

        //
        // Get a bounding rectangle for the objects that defione the bounding
        // area.
        Map editPartMap = graphViewer.getEditPartRegistry();
        Rectangle outerBounds = null;

        if (areaObjects != null && areaObjects.size() > 0) {
            for (Iterator iter = areaObjects.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                EditPart ep = (EditPart) editPartMap.get(obj);

                if (ep instanceof GraphicalEditPart) {
                    IFigure fig = ((GraphicalEditPart) ep).getFigure();

                    Rectangle bnds = fig.getBounds().getCopy();

                    // Translate to absolute (so all objects are on even
                    // grounds.
                    fig.translateToAbsolute(bnds);
                    pLayer.translateToRelative(bnds);
                    if (outerBounds == null) {
                        outerBounds = bnds;
                    } else {
                        outerBounds.union(bnds);
                    }
                }
            }

            outerBounds.expand(margin, margin);
            if (outerBounds.x < 0 || outerBounds.y < 0) {
                outerBounds.shrink(margin, margin);
            }

        } else {
            outerBounds =
                    new Rectangle(0, 0, diagramExt.width, diagramExt.height);
        }

        //
        // And the bounds of the selection.
        Rectangle selBounds = null;
        HashSet<IFigure> selectFigures = null;

        if (selectionObjects != null && selectionObjects.size() > 0) {
            selectFigures = new HashSet<IFigure>(selectionObjects.size());

            for (Iterator iter = selectionObjects.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                EditPart ep = (EditPart) editPartMap.get(obj);

                if (ep instanceof GraphicalEditPart) {
                    IFigure fig = ((GraphicalEditPart) ep).getFigure();

                    selectFigures.add(fig);

                    Rectangle bnds = fig.getBounds().getCopy();

                    // Translate to absolute (so all objects are on even
                    // grounds.
                    fig.translateToAbsolute(bnds);
                    pLayer.translateToRelative(bnds);
                    if (selBounds == null) {
                        selBounds = bnds;
                    } else {
                        selBounds.union(bnds);
                    }
                }
            }

            selBounds.expand(10, 10);

        } else {
            selectFigures = new HashSet<IFigure>(0);
        }

        //
        // And a list of the highlightable figure.
        HashSet<IFigure> highlightFigures = null;

        if (highlightObjects != null && highlightObjects.size() > 0) {
            highlightFigures = new HashSet<IFigure>(highlightObjects.size());

            for (Iterator iter = highlightObjects.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                EditPart ep = (EditPart) editPartMap.get(obj);

                if (ep instanceof GraphicalEditPart) {
                    highlightFigures.add(((GraphicalEditPart) ep).getFigure());
                }
            }
        } else {
            highlightFigures = new HashSet<IFigure>(0);
        }

        //
        // Calculate the scale required for the best fit to the given size hint.
        double scale = 1.0;
        if (sizeHint != null) {
            // Base size on widest dimension
            double widthScale = 1.0;

            if (sizeHint.width > 0) {
                widthScale =
                        (double) sizeHint.width / (double) outerBounds.width;
            }

            if ((flags & MAX_SCALE_1_TO_1) != 0) {
                if (widthScale > 1.0f) {
                    widthScale = 1.0f;
                }
            }

            double heightScale = 1.0;

            if (sizeHint.height > 0) {
                heightScale =
                        (double) sizeHint.height / (double) outerBounds.height;
            }

            if ((flags & MAX_SCALE_1_TO_1) != 0) {
                if (heightScale > 1.0f) {
                    heightScale = 1.0f;
                }
            }

            if (widthScale < heightScale) {
                scale = widthScale;

                outerBounds.height =
                        Math.min((int) (sizeHint.height / scale),
                                (diagramExt.height - outerBounds.y));
            } else {
                scale = heightScale;

                outerBounds.width =
                        Math.min((int) (sizeHint.width / scale),
                                (diagramExt.width - outerBounds.x));
            }
        }

        //
        // Create a complete image of the process.
        Device d = Display.getDefault();
        try {
            //
            // We used to create one image the whole size of diagram and then
            // (if there were specific subset of objects we wanted image of then
            // copy the area for those objects out of the whole).
            // This caused problems when the diagram was anything bigger than a
            // few screen 'pages' in size).
            // So now, instead, we only create an image large enough for the
            // selectaed objects area (and paint all the objects into as normal
            // BUT shifted and cliiped into the smaller rect).
            //
            // Create the image
            Image processImg;

            //
            // Ensure that the size of the image does not overrun the max size
            // of 16MB for single image, by scaling until whole image will be
            // uinder that size (i.e. width*height*3 (3 bytes per pix in a
            // 24-bit image).
            //
            while ((((outerBounds.width * outerBounds.height) * 3) * scale) > 16000000
                    && scale > 0.001) {
                scale = scale * 0.9;
            }

            processImg =
                    new Image(d, (int) (outerBounds.width * scale),
                            (int) (outerBounds.height * scale));

            GC gc = new GC(processImg);

            gc.setBackground(pLayer.getBackgroundColor());
            gc.setForeground(pLayer.getForegroundColor());
            gc.setFont(pLayer.getFont());

            FixedHighlightGraphics graphics = new FixedHighlightGraphics(gc);

            Rectangle unscaledBounds = outerBounds.getCopy();
            outerBounds.scale(scale);

            graphics.scale(scale);

            // We MUST paint only into the (possibly) smaller area of the
            // objects we need an image of to shift the painting offset and clip
            // around it.
            graphics.translate(-unscaledBounds.x, -unscaledBounds.y);
            graphics.setClip(unscaledBounds);

            long startTime = System.currentTimeMillis();

            /**
             * ================================================================
             * ==== ======== We have to paint things in a specific order
             * (otherwise highlighted objects can 'blank out' previously drawn
             * connections). So we will paint...
             * <p>
             * <li>All objects faded</li>
             * <li>Overwrite with Selected objects normal</li>
             * <li>Overwrite with highlighted objects</li>
             * <li>
             * Paint all connections normal</li>
             * <li>Paint selected connections normal</li>
             * <li>Paint highlighted connections</li>
             * </p>
             * ================================================================
             * ==== ========
             */

            /**
             * ================================================================
             * ==== ======== First paint all the objects faded...
             * ========================
             * ====================================================
             */
            IFigure objectLayer = wr.getLayer(LayerConstants.PRIMARY_LAYER);

            if ((flags & PAINT_UNSELECTED_OBJECTS) != 0) {
                graphics.setLowlightAlpha(true);

                objectLayer.paint(graphics);

                graphics.setLowlightAlpha(false);
            }

            /**
             * ================================================================
             * ==== ======== Then paint all the selected objects normal...
             * ================
             * ============================================================
             */
            if (selectFigures.size() > 0) {

                // Make sure that events attached to task border are always
                // painted
                // last.
                // so they don't appear before task.
                List<IFigure> sortFigs = new ArrayList(selectFigures.size());
                sortFigs.addAll(selectFigures);

                Collections.sort(sortFigs, taskBorderEventsLastSort);

                for (IFigure fig : sortFigs) {
                    if (fig.isVisible() && !(fig instanceof PolylineConnection)) {
                        // Only bother painting select object if it's NOT in
                        // highlight list (no point painting it twice.
                        if (!highlightFigures.contains(fig)) {
                            graphics.pushState();

                            adjustGraphicsForIndividualFigure(graphics,
                                    objectLayer,
                                    fig);

                            // Switch off grid layer for embedded sub-proc
                            // tasks.
                            XPDGridLayer gridLayer = null;
                            boolean taskGridVisible = false;

                            if (fig instanceof TaskFigure) {
                                gridLayer = ((TaskFigure) fig).getGridLayer();
                                if (gridLayer != null) {
                                    gridLayer.setIgnorePaint(true);
                                }
                            }

                            if (fig instanceof IHighlightableFigure) {
                                ((IHighlightableFigure) fig)
                                        .setHighlight(false);
                            }

                            fig.paint(graphics);

                            if (gridLayer != null) {
                                gridLayer.setIgnorePaint(false);
                            }

                            graphics.popState();
                        }
                    }
                }
            }

            /**
             * ================================================================
             * ==== ======== Then paint all the highlighted objects...
             * ====================
             * ========================================================
             */
            if (highlightFigures.size() > 0) {
                graphics.setFixedLineWidth(new Integer(2));

                // If caller wants us to highlight children of highlighted
                // objects
                // then set the whole graphics object up to do highlighting.
                if ((flags & HIGHLIGHT_CHILDREN) != 0) {
                    graphics.setDoHighlightFillColor(true);
                }

                // Make sure that events attached to task border are always
                // painted
                // last. This is so they don't appear before task.
                List<IFigure> sortFigs = new ArrayList(highlightFigures.size());
                sortFigs.addAll(highlightFigures);

                Collections.sort(sortFigs, taskBorderEventsLastSort);

                for (IFigure fig : sortFigs) {
                    if (fig.isVisible() && !(fig instanceof PolylineConnection)) {
                        graphics.pushState();

                        adjustGraphicsForIndividualFigure(graphics,
                                objectLayer,
                                fig);

                        // Switch off grid layer for embedded sub-proc tasks.
                        XPDGridLayer gridLayer = null;
                        boolean taskGridVisible = false;

                        if (fig instanceof TaskFigure) {
                            gridLayer = ((TaskFigure) fig).getGridLayer();
                            if (gridLayer != null) {
                                gridLayer.setIgnorePaint(true);
                            }
                        }

                        if (fig instanceof IHighlightableFigure) {
                            ((IHighlightableFigure) fig).setHighlight(false);
                        }

                        // If not highlighting children of highlighted object
                        // then
                        // just highlight main object by setting background
                        // colour.
                        Color oldBgCol = fig.getBackgroundColor();

                        if ((flags & HIGHLIGHT_CHILDREN) == 0) {
                            fig.setBackgroundColor(graphics
                                    .getHighlightFillColor());
                        }

                        fig.paint(graphics);

                        if ((flags & HIGHLIGHT_CHILDREN) == 0) {
                            fig.setBackgroundColor(oldBgCol);
                        }

                        if (gridLayer != null) {
                            gridLayer.setIgnorePaint(false);
                        }

                        graphics.popState();

                    }
                }

                if ((flags & HIGHLIGHT_CHILDREN) != 0) {
                    graphics.setDoHighlightFillColor(false);
                }

                graphics.setFixedLineWidth(null);
            }

            /**
             * ================================================================
             * ==== ======== Then paint all Connections faded...
             * ==========================
             * ==================================================
             */
            if ((flags & PAINT_UNSELECTED_OBJECTS) != 0) {
                IFigure connLayer =
                        wr.getLayer(LayerConstants.CONNECTION_LAYER);
                graphics.setLowlightAlpha(true);

                connLayer.paint(graphics);

                graphics.setLowlightAlpha(false);
            }

            /**
             * ================================================================
             * ==== ======== Then paint all the selected connections normal...
             * ============
             * ================================================================
             */
            if (selectFigures.size() > 0) {
                for (IFigure fig : selectFigures) {
                    if (fig.isVisible() && (fig instanceof PolylineConnection)) {
                        // Only bother painting select figure if it's NOT in
                        // highlight
                        // list (no point painting it twice).
                        if (!highlightFigures.contains(fig)) {
                            if (fig instanceof IHighlightableFigure) {
                                ((IHighlightableFigure) fig)
                                        .setHighlight(false);
                            }

                            fig.paint(graphics);
                        }
                    }
                }
            }

            /**
             * ================================================================
             * ==== ======== Then paint all the highlighted connections...
             * ================
             * ============================================================
             */
            if (highlightFigures.size() > 0) {
                graphics.setFixedLineWidth(new Integer(2));
                graphics.setDoHighlightLineColor(true);

                for (IFigure fig : highlightFigures) {
                    if (fig.isVisible() && (fig instanceof PolylineConnection)) {
                        if (fig instanceof IHighlightableFigure) {
                            ((IHighlightableFigure) fig).setHighlight(false);
                        }

                        fig.paint(graphics);
                    }
                }

                graphics.setDoHighlightLineColor(false);
                graphics.setFixedLineWidth(null);
            }

            // System.out.println("paint img took: "+(System.currentTimeMillis()
            // -
            // startTime)+"ms");

            //
            // We used to create one image the whole size of diagram and then
            // (if there were specific subset of objects we wanted image of then
            // copy the area for those objects out of the whole).
            // This caused problems when the diagram was anything bigger than a
            // few screen 'pages' in size).
            // So now, instead, we only create an image large enough for the
            // selectaed objects area (and paint all the objects into as normal
            // BUT shifted and cliiped into the smaller rect).
            //
            // Therefore no need to copy into a separate image anymore - just
            // return the one we have built...
            //
            graphics.dispose();
            gc.dispose();

            return processImg;

        } catch (Exception e) {
            System.out.println("Cant create image exception: " + e); //$NON-NLS-1$
            LOG.error(e);
            return null;
        }

    }

    /**
     * Normally a figure is painted as a result of painting it's parent, in
     * which case each figure adjusts scale and X/Y translation before paitning
     * children.
     * 
     * Therefore when painting individual figures the graphics object will not
     * be set up for the correct scale etc. This method sets up the graphics
     * object so that when the figure is painted, it will be at correct scale
     * and correct offset from its parent.
     * 
     * @param graphics
     * @param objectLayer
     * @param fig
     */
    private void adjustGraphicsForIndividualFigure(
            FixedHighlightGraphics graphics, IFigure objectLayer, IFigure fig) {
        double curScale = 1;
        double curXOff = 0;
        double curYOff = 0;

        List<IFigure> parentTree = new ArrayList<IFigure>();

        IFigure parent = fig.getParent();
        while (parent != null && parent != objectLayer) {
            parentTree.add(parent);
            parent = parent.getParent();
        }

        for (IFigure pFig : parentTree) {

            if (pFig instanceof TaskFigure) {
                Point offset = pFig.getBounds().getTopLeft();

                curXOff += offset.x / curScale;
                curYOff += offset.y / curScale;

            }

            if (pFig instanceof ScalableFreeformLayeredPane) {
                ScalableFreeformLayeredPane scaleLayer =
                        (ScalableFreeformLayeredPane) pFig;

                curScale = curScale * scaleLayer.getScale();
                graphics.scale(scaleLayer.getScale());
            }

        }

        graphics.translate((int) (curXOff), (int) (curYOff));
    }

    /**
     * Get the diagram editor feedback layer.
     * 
     * @return
     */
    public IFigure getFeedbackLayer() {
        WidgetRootEditPart wr =
                (WidgetRootEditPart) graphViewer.getRootEditPart();

        IFigure fig = wr.getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);

        return fig;
    }

    public IFigure getPrimaryLayer() {
        WidgetRootEditPart wr =
                (WidgetRootEditPart) graphViewer.getRootEditPart();

        IFigure fig = wr.getLayer(LayerConstants.PRINTABLE_LAYERS);

        return fig;
    }

    /**
     * Given an XPD past command, select the edit parts for the objects that
     * were pasted and focus the diagram editor.
     * 
     * @param cmd
     */
    public void setSelFromPastedObjects(XPDPasteCommand cmd) {
        getGraphicalViewer().getRootEditPart().refresh();

        Collection<Object> modelObjects = cmd.getPasteModelObjects();
        if (modelObjects != null && modelObjects.size() > 0) {
            List<EditPart> affectedEditParts =
                    EditPartUtil
                            .getEditPartsForModelObjects(getGraphicalViewer(),
                                    modelObjects,
                                    false);
            if (affectedEditParts != null && affectedEditParts.size() > 0) {

                // Remove connection edit parts whose src/target exit in the set
                // of objects.
                for (Iterator iterator = affectedEditParts.iterator(); iterator
                        .hasNext();) {
                    EditPart ep = (EditPart) iterator.next();

                    if (ep instanceof BaseConnectionEditPart) {
                        BaseConnectionEditPart connEP =
                                (BaseConnectionEditPart) ep;

                        if (affectedEditParts.contains(connEP.getSource())
                                || affectedEditParts.contains(connEP
                                        .getTarget())) {
                            iterator.remove();
                        }

                    }
                }

                getGraphicalViewer().setSelection(new StructuredSelection(
                        affectedEditParts));
            }
        }

        getGraphicalViewer().getControl().setFocus();

        return;
    }

    @Override
    public EditPolicyEnablementProvider getEditPolicyEnablementProvider() {
        return delegatingEditPolicyEnablementProvider;
    }

    @Override
    public void setEditPolicyEnablementProvider(
            EditPolicyEnablementProvider editPolicyEnablementProvider) {
        this.editPolicyEnablementProvider = editPolicyEnablementProvider;
    }

    /**
     * Edit policy enablement provider class that will delegate to the process
     * editor provider once it has been set on the widget.
     * <p>
     * We have to do this delegation because when edit policies are first
     * created the editor may not yet have set the provider yet.
     * 
     * @author aallway
     * @since
     */
    private class ProcessWidgetDelegatingPolicyEnablementProvider implements
            EditPolicyEnablementProvider {

        @Override
        public boolean isPolicyEnabled(String policyId) {
            if (editPolicyEnablementProvider != null) {
                return editPolicyEnablementProvider.isPolicyEnabled(policyId);
            }
            return true;
        }

        @Override
        public void setPolicyEnabled(String policyId, boolean enabled) {
            if (editPolicyEnablementProvider != null) {
                editPolicyEnablementProvider
                        .setPolicyEnabled(policyId, enabled);
            }
        }

    }

    @Override
    public ProcessWidgetType getProcessWidgetType() {
        return processWidgetType;
    }

    /**
     * Use alternate view for task libraries.
     * 
     * IF YOU WANT TO REVERT TO STANDARD PROCESS VIEW then this is the ONLY
     * place you should have to change.
     * 
     * 
     */
    @Override
    public DiagramViewType getDiagramViewType() {

        /*
         * TRIAL For Analyst - No Pools mode: Default to No Pools mode unless
         * unless there are already multiple pools / lanes.
         * 
         * THIS HAS A BUG AT THE MOMENT IF PROCESS HAS MULTI-POOL / LANES IN
         * first POOL. BECAUSE first time we sanity check for this then there
         * was only one edit part.
         */
        boolean poolsPrefExcluded = false;
        if (ProcessWidgetType.BPMN_PROCESS.equals(getProcessWidgetType())) {
            poolsPrefExcluded =
                    Xpdl2ResourcesPlugin.getDefault().getPreferenceStore()
                            .getBoolean("BusinessProcess_Exclude_pool"); //$NON-NLS-1$

            boolean hasMultiPoolsOrLanes = false;
            if (poolsPrefExcluded) {
                hasMultiPoolsOrLanes =
                        ProcessEditorConfigurationUtil
                                .hasMultiPoolsOrLanes(getInput());
            }

            if (poolsPrefExcluded && !hasMultiPoolsOrLanes) {
                return DiagramViewType.NO_POOLS;
            }

        }

        /* Otherwise setup the default according to the process type. */
        if (ProcessWidgetType.TASK_LIBRARY.equals(getProcessWidgetType())) {
            return DiagramViewType.TASK_LIBRARY_ALTERNATE;

        } else if (ProcessWidgetType.PAGEFLOW_PROCESS
                .equals(getProcessWidgetType())
                || ProcessWidgetType.CASE_SERVICE
                        .equals(getProcessWidgetType())
                || ProcessWidgetType.BUSINESS_SERVICE
                        .equals(getProcessWidgetType())
                || ProcessWidgetType.SERVICE_PROCESS
                        .equals(getProcessWidgetType())
                || ProcessWidgetType.DECISION_FLOW
                        .equals(getProcessWidgetType())) {
            return DiagramViewType.NO_POOLS;
        }

        return DiagramViewType.PROCESS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.ProcessWidget#isReadOnly()
     */
    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * @param readOnly
     *            the readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * @param referenceHighlighter
     *            the referenceHighlighter to set
     */
    @Override
    public void setReferenceHighlighter(EditPartHighlighter referenceHighlighter) {
        this.referenceHighlighter = referenceHighlighter;
    }

    /**
     * @return the referenceHighlighter
     */
    @Override
    public EditPartHighlighter getReferenceHighlighter() {
        return referenceHighlighter;
    }

    /**
     * Sid XPD-2516: Called to reset certain configurations in the event that
     * something in the process model might have changed.
     * <p>
     * Nominally each edit part responds to it's own individual underlying
     * model's changing - that's fine for the diagram - however, for some things
     * we need to know if ANYTHING under the model may have changed.
     * <p>
     * Also loading the contributions based on model is better to do on widget
     * creation and then once per change after that to so that we do not load
     * contributions for every referesh of every edit part (for instance ion the
     * case of contributions to
     * processEditorConfiguration/ActivityIconOverrides.
     */
    public void notifyInputContentModified() {
        /*
         * Changes to the model (for instance changes to destination env's) may
         * have changed the contributions to excluded object types in
         * processEditorConfiguration ext point contributions.
         */
        if (getInput() != null) {
            excludedObjectTypes =
                    ProcessEditorConfigurationUtil
                            .getExcludedObjectTypes(getInput());

            /* And the ActivityIconProvider's applicable to this process. */
            List<ActivityIconProviderDescriptor> newActivityIconProviders =
                    ProcessEditorConfigurationUtil
                            .getActivityIconProviders(getInput());

            if (newActivityIconProviders.size() != activityIconProviders.size()
                    || !activityIconProviders
                            .containsAll(newActivityIconProviders)) {

                activityIconProviders = newActivityIconProviders;

                forceRefreshVisuals();

            }

            /*
             * Refresh the content of the tool palette with the latest
             */
            resetToolPalette();
        }
    }

    private void forceRefreshVisuals() {
        if (getGraphicalViewer() != null) {

            RootEditPart rootEditPart = getGraphicalViewer().getRootEditPart();

            recursiveRefreshVisuals(rootEditPart);

            getGraphicalViewer().flush();

        }
    }

    /**
     * @param rootEditPart
     */
    private void recursiveRefreshVisuals(EditPart editPart) {
        if (editPart instanceof BaseGraphicalEditPart) {
            ((BaseGraphicalEditPart) editPart).forceRefreshVisuals();
        } else if (editPart instanceof BaseConnectionEditPart) {
            ((BaseConnectionEditPart) editPart).forceRefreshVisuals();
        }

        List children = editPart.getChildren();
        if (children != null) {
            for (Object child : children) {
                if (child instanceof EditPart) {
                    recursiveRefreshVisuals((EditPart) child);
                }
            }
        }

    }

    /**
     * Check and reset the contents of the tool palette (if necessary)
     * <p>
     * This can be required when something changes to cause a configuration
     * change (for instance something in the model that affects a
     * processEditorConfiguration/ObjectTypeExclusion)
     */
    private void resetToolPalette() {
        if (this.getControl() != null && !this.getControl().isDisposed()) {
            /*
             * Do an asynchExec to allow normal refreshes from commands to go
             * thru before we update the palette, this is becasue the reset open
             * palette drawers back to open at the end of this method can take a
             * while because the palette drawers animate and that taks a while
             * and I can't turn it off.
             */
            this.getControl().getDisplay().asyncExec(new Runnable() {

                @Override
                public void run() {
                    if (paletteFactory != null && paletteRoot != null
                            && editDomain != null
                            && editDomain.getPaletteViewer() != null) {

                        PaletteViewer paletteViewer =
                                editDomain.getPaletteViewer();

                        if (paletteFactory.needsReset(excludedObjectTypes)) {
                            /*
                             * When we reset the content of the palette IT
                             * ALWAYS opens all the drawers regardless of their
                             * original (or INITIAL_OPEN) state :0(
                             * 
                             * So to get around this we will save the state of
                             * all the drawers edit parts and restore this after
                             * the reset.
                             */
                            Map<String, Boolean> paletteDrawerOpenStates =
                                    new HashMap<String, Boolean>();

                            getPaletteDrawerStates(paletteRoot,
                                    paletteViewer.getEditPartRegistry(),
                                    paletteDrawerOpenStates);

                            Control paletteControl = paletteViewer.getControl();
                            if (paletteControl != null
                                    && !paletteControl.isDisposed()) {
                                /*
                                 * Setting redraw false would stop the palette
                                 * animation.
                                 */
                                // paletteControl.setRedraw(false);
                            }

                            /*
                             * Make sure we ditch the currently set 'active
                             * tool' entry in favour - because that tool may not
                             * exist anymore (even if it is there in new and old
                             * set it will be a different instance.
                             * 
                             * BEcause of the way setActiveTool() falls back on
                             * dfefautl in palette root. Have to set palette
                             * root to null so that setActiveTool() sets the
                             * active tool to null - otherwase setActiveTool()
                             * uses it to find object in tool registry without
                             * checking for null return !!
                             */
                            paletteRoot.setDefaultEntry(null);
                            paletteViewer.setActiveTool(null);

                            /*
                             * Reset the palette content.
                             */
                            paletteFactory.resetPalette(ProcessWidgetImpl.this,
                                    paletteRoot);

                            /* Now set the active tool */
                            paletteViewer.setActiveTool(paletteRoot
                                    .getDefaultEntry());

                            paletteViewerProvider
                                    .allowUserCustomisation(ProcessWidgetPlugin
                                            .getDefault().getPreferenceStore(),
                                            paletteRoot,
                                            true,
                                            PaletteEntry.PERMISSION_FULL_MODIFICATION);
                            /*
                             * Flush to get the edit parts recreated if
                             * necessary.
                             */
                            paletteViewer.flush();

                            setPaletteDrawerStates(paletteRoot,
                                    paletteViewer.getEditPartRegistry(),
                                    paletteDrawerOpenStates);

                            if (paletteControl != null
                                    && !paletteControl.isDisposed()) {
                                /*
                                 * Setting redraw false would stop the palette
                                 * animation.
                                 */
                                // paletteControl.setRedraw(true);
                            }
                        }
                    }
                }
            });
        }

    }

    /**
     * Recursively Setup a map of tool paletter drawer model id entries -->
     * their open state so that it can be reset afterwards.
     * 
     * @param paletteContainer
     * @param toolPartRegistry
     * @param paletteDrawerOpenStates
     *            Return map.
     */
    private void getPaletteDrawerStates(PaletteContainer paletteContainer,
            Map toolPartRegistry, Map<String, Boolean> paletteDrawerOpenStates) {

        if (paletteContainer instanceof PaletteDrawer) {
            Object ep = toolPartRegistry.get(paletteContainer);
            if (ep instanceof IPinnableEditPart) {
                String id = paletteContainer.getId();
                boolean expanded = ((IPinnableEditPart) ep).isExpanded();
                paletteDrawerOpenStates.put(id, expanded);
            }
        }

        for (Object child : paletteContainer.getChildren()) {
            if (child instanceof PaletteContainer) {
                getPaletteDrawerStates((PaletteContainer) child,
                        toolPartRegistry,
                        paletteDrawerOpenStates);
            }
        }
    }

    /**
     * Reset the drawer open states.
     * 
     * @param paletteContainer
     * @param toolPartRegistry
     * @param paletteDrawerOpenStates
     */
    @SuppressWarnings("restriction")
    private void setPaletteDrawerStates(PaletteContainer paletteContainer,
            Map toolPartRegistry, Map<String, Boolean> paletteDrawerOpenStates) {

        if (paletteContainer instanceof PaletteDrawer) {

            String id = paletteContainer.getId();
            Boolean open = paletteDrawerOpenStates.get(id);
            if (open == null) {
                open = false;
            }

            Object ep = toolPartRegistry.get(paletteContainer);

            if (ep instanceof DrawerEditPart) {
                if (((DrawerEditPart) ep).isExpanded() != open) {
                    ((DrawerEditPart) ep).setExpanded(open);
                }
            }

        }

        for (Object child : paletteContainer.getChildren()) {
            if (child instanceof PaletteContainer) {
                setPaletteDrawerStates((PaletteContainer) child,
                        toolPartRegistry,
                        paletteDrawerOpenStates);
            }
        }
    }

    /**
     * @return the excludedObjectTypes
     */
    @Override
    public Set<ProcessEditorObjectType> getExcludedObjectTypes() {
        return Collections.unmodifiableSet(excludedObjectTypes);
    }

    /**
     * @return the activityIconProviders
     */
    @Override
    public List<ActivityIconProviderDescriptor> getActivityIconProviders() {
        return activityIconProviders;
    }

    /**
     * Moved {@link IMenuListener} implementation from {@link ProcessWidgetImpl}
     * to inner class.
     * <p>
     * This adds alignment tools (where appropriate) and also handles hiding set
     * bject type actions according to the
     * processEditoCOnfiguration/ObjectTypeExclusions extension point.
     * <p>
     * Those actions must that have their id prefixed as
     * "com.tibco.xpd.processwidget.setObjectType." followed by a string literal
     * from {@link ProcessEditorObjectType}.
     * <p>
     * i.e. The id=com.tibco.xpd.processwidget.setObjectType.start_event_message
     * will cause that action to be hidden if start message events are excluded.
     * 
     * @author aallway
     * @since 4 Nov 2011
     */
    private class ProcessWidgetContextMenuListener implements IMenuListener {

        private static final String SET_OBJECT_TYPE_ACTION_ID_LEADER =
                "com.tibco.xpd.processwidget.setObjectType."; //$NON-NLS-1$

        /**
         * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
         * 
         * @param manager
         */
        @Override
        public void menuAboutToShow(IMenuManager manager) {
            if (manager instanceof MenuManager
                    && ((MenuManager) manager).getParent() == null) {
                /*
                 * In top level menu replace align with a dynamic one in other
                 * words don't show when disabled..
                 */
                manager.remove("align"); //$NON-NLS-1$

                IAction act = getAction(GEFActionConstants.ALIGN_LEFT);
                if (act.isEnabled()) {
                    MenuManager alignMenu =
                            new MenuManager(
                                    Messages.ProcessWidgetImpl_Align_menu,
                                    "align"); //$NON-NLS-1$
                    manager.add(alignMenu);
                    alignMenu.add(getAction(GEFActionConstants.ALIGN_LEFT));
                    alignMenu.add(getAction(GEFActionConstants.ALIGN_CENTER));
                    alignMenu.add(getAction(GEFActionConstants.ALIGN_RIGHT));
                    alignMenu.add(getAction(GEFActionConstants.ALIGN_TOP));
                    alignMenu.add(getAction(GEFActionConstants.ALIGN_MIDDLE));
                    alignMenu.add(getAction(GEFActionConstants.ALIGN_BOTTOM));
                }
            }

            /*
             * Look for object type selection actions and hide them if their
             * respective object type is excluded via the
             * processEditorConfiguration extension point.
             */
            IContributionItem[] items = manager.getItems();
            if (items != null) {

                for (IContributionItem item : items) {
                    String id = item.getId();
                    if (id != null) {

                        if (id.startsWith(SET_OBJECT_TYPE_ACTION_ID_LEADER)) {
                            String objectTypeLiteral =
                                    id.substring(SET_OBJECT_TYPE_ACTION_ID_LEADER
                                            .length());

                            ProcessEditorObjectType objectType =
                                    ProcessEditorObjectType
                                            .valueOf(objectTypeLiteral);
                            if (objectType != null) {
                                if (excludedObjectTypes.contains(objectType)) {
                                    item.setVisible(false);
                                }
                            } else {
                                ProcessWidgetPlugin
                                        .getDefault()
                                        .getLogger()
                                        .error("Unrecognised Set Object type action contribution id: " + id); //$NON-NLS-1$
                            }
                        }
                    }

                    /*
                     * We need to add ourselves to sub-menus when we come
                     * acrtoss them because teh action items we are looking for
                     * may be (actually are) in sub-menus.
                     */
                    if (item instanceof SubContributionItem) {
                        IContributionItem innerItem =
                                ((SubContributionItem) item).getInnerItem();
                        if (innerItem instanceof IMenuManager) {
                            ((IMenuManager) innerItem).addMenuListener(this);
                        }
                    }
                }

            }

        }

    }
}
