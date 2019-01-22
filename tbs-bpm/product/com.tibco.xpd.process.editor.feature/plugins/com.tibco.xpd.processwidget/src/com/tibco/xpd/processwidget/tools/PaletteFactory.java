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

package com.tibco.xpd.processwidget.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.PaletteToolbar;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.processwidget.adapters.DataObjectAdapter;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GatewayAdapter;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.adapters.GroupAdapter;
import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.adapters.MessageFlowAdapter;
import com.tibco.xpd.processwidget.adapters.NoteAdapter;
import com.tibco.xpd.processwidget.adapters.PoolAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;
import com.tibco.xpd.processwidget.internal.Messages;

/**
 * Simple factory that create a Palette
 * 
 * @author wzurek
 */
public class PaletteFactory implements PaletteToolsHelpConstants {

    /**
     * Added to request extended data for task creation
     */
    public static final String EXTDATA_TASK_TYPE = "taskType"; //$NON-NLS-1$

    /**
     * Added to request extended data for event creation
     */
    public static final String EXTDATA_EVENT_TYPE = "eventType"; //$NON-NLS-1$

    /**
     * Added to request extended data for event creation
     */
    public static final String EXTDATA_EVENT_FLOW_TYPE = "eventFlowType"; //$NON-NLS-1$

    /**
     * Added to request extended data for gateway creation
     */
    public static final String EXTDATA_GATEWAY_TYPE = "gatewayType"; //$NON-NLS-1$

    /**
     * Added to request extended data for sequence flow creation
     */
    public static final String EXTDATA_SEQUENCEFLOW_TYPE = "sequenceFlowType"; //$NON-NLS-1$

    /**
     * Added to request extended data for all element's creations
     */
    public static final String EXTDATA_INITIAL_SIZE = "initialSize"; //$NON-NLS-1$

    private static final String FAVOURITES_DRAWER_ID = "FAVOURITES_DRAWER_ID"; //$NON-NLS-1$

    /**
     * Added to request extended data for all element's creations
     */
    public static final String EXTDATA_DEFAULT_FILL_COLORID =
            "DefaultFillColor"; //$NON-NLS-1$

    /**
     * Added to request extended data for all element's creations
     */
    public static final String EXTDATA_DEFAULT_LINE_COLORID =
            "DefaultLineColor"; //$NON-NLS-1$

    /**
     * Added to request extended data by FlowCOntainer - new object (to be)
     * added by creation command.
     */
    public static final String EXTDATA_CREATED_MODEL_OBJECT =
            "CreatedModelObject"; //$NON-NLS-1$

    /**
     * Added to each tool this is the {@link ProcessEditorObjectType} created by
     * the given tool - this can be used to show/hide the tool as appropriate.
     */
    private static final String EXTDATA_PROCESS_EDITOR_OBJECT_TYPE =
            "processEditorObjectType"; //$NON-NLS-1$

    private ProcessWidget processWidgetImpl;

    private Set<ProcessEditorObjectType> excludedObjectTypes;

    /**
     * The Constructor
     */
    public PaletteFactory(ProcessWidget processWidgetImpl) {
        this.processWidgetImpl = processWidgetImpl;
    }

    /**
     * Create Palette for Process Widget
     * 
     * @return palette root
     */
    public PaletteRoot createPalette(
            ToolContextHelpUpdater toolContextHelpUpdater,
            Set<ProcessEditorObjectType> excludedObjectTypes) {

        this.excludedObjectTypes = excludedObjectTypes;

        PaletteRoot paletteRoot = new PaletteRoot();

        createPaletteChildren(toolContextHelpUpdater, paletteRoot);

        // Ensure all drawers are closed by default!
        recursiveCloseDrawers(paletteRoot);

        return paletteRoot;
    }

    /**
     * Check whether the tool palette requires a reset.
     * <p>
     * This can be required when something changes to cause a configuration
     * change (for instance something in the model that affects a
     * processEditorConfiguration/ObjectTypeExclusion)
     * 
     * @return <code>true</code> if a reset is required
     */
    public boolean needsReset(
            Set<ProcessEditorObjectType> newExcludedObjectTypes) {
        boolean resetRequired = false;

        if (newExcludedObjectTypes.size() != excludedObjectTypes.size()) {
            resetRequired = true;
        } else {
            for (ProcessEditorObjectType excludedType : newExcludedObjectTypes) {
                if (!excludedObjectTypes.contains(excludedType)) {
                    resetRequired = true;
                    break;
                }
            }
        }

        if (resetRequired) {
            excludedObjectTypes = newExcludedObjectTypes;
        }

        return resetRequired;
    }

    /**
     * Refresh the content of the tool palette.
     * <p>
     * This can be required when something changes to cause a configuration
     * change (for instance something in the model that affects a
     * processEditorConfiguration/ObjectTypeExclusion)
     * <p>
     * <b>There is ONLY any point calling this method if a previous call to
     * {@link #needsReset()} is called and has returned <code>true</code>.
     * 
     * @param paletteRoot
     */
    public void resetPalette(ToolContextHelpUpdater toolContextHelpUpdater,
            PaletteRoot paletteRoot) {

        List children = new ArrayList(paletteRoot.getChildren());
        for (Object child : children) {
            if (child instanceof PaletteEntry) {
                paletteRoot.remove((PaletteEntry) child);
            }
        }

        createPaletteChildren(toolContextHelpUpdater, paletteRoot);

        return;
    }

    /**
     * @param toolContextHelpUpdater
     * @param paletteRoot
     */
    private void createPaletteChildren(
            ToolContextHelpUpdater toolContextHelpUpdater,
            PaletteRoot paletteRoot) {

        // create main selection tools
        createSelectionTools(paletteRoot);

        if (!ProcessWidgetType.TASK_LIBRARY.equals(processWidgetImpl
                .getProcessWidgetType())
                && !ProcessWidgetType.DECISION_FLOW.equals(processWidgetImpl
                        .getProcessWidgetType())) { // Base Events...
            createFavouritesDrawer(toolContextHelpUpdater, paletteRoot);
        }

        // create tools cor main elements, like tasks, events etc.
        if (ProcessWidgetType.DECISION_FLOW.equals(processWidgetImpl
                .getProcessWidgetType())) {

            // create connections - sequence flow, message flow and associations
            PaletteDrawer drawer =
                    addConnectionToolsDrawer(toolContextHelpUpdater,
                            paletteRoot);
            if (drawer != null) {
                drawer.setInitialState(PaletteDrawer.INITIAL_STATE_PINNED_OPEN);
            }

            addDecisionFlowObjectCreationTools(toolContextHelpUpdater,
                    paletteRoot);
        } else {
            // create connections - sequence flow, message flow and associations
            addConnectionToolsDrawer(toolContextHelpUpdater, paletteRoot);

            addObjectCreationTools(toolContextHelpUpdater, paletteRoot);

        }

    }

    private void recursiveCloseDrawers(PaletteContainer paletteContainer) {
        // By default set all drawers to initially closed - this default will be
        // used UNLESS the user makes their own settings via tge palette's
        // standard customize dialog.
        //
        // Except for Favourites draw which we want to keep open by default.
        // And in the case of decision flow (as there are only 3 drawers anyway
        // so all fits ok.

        boolean isDecisionFlow =
                ProcessWidgetType.DECISION_FLOW.equals(processWidgetImpl
                        .getProcessWidgetType());

        for (Object child : paletteContainer.getChildren()) {
            if (child instanceof PaletteDrawer) {
                if (!isDecisionFlow
                        && !FAVOURITES_DRAWER_ID.equals(((PaletteDrawer) child)
                                .getId())) {
                    ((PaletteDrawer) child)
                            .setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
                }
            }

            if (child instanceof PaletteContainer) {
                if (!isDecisionFlow
                        || !FAVOURITES_DRAWER_ID
                                .equals(((PaletteContainer) child).getId())) {
                    recursiveCloseDrawers((PaletteContainer) child);
                }
            }

        }
    }

    /**
     * Create main selection tools - selection and marque
     * 
     * @param paletteRoot
     * @return
     */
    protected void createSelectionTools(PaletteRoot paletteRoot) {
        PaletteToolbar controlGroup =
                new PaletteToolbar(Messages.PaletteFactory_Tools_label);

        ToolEntry tool;

        tool = new PanningSelectionToolEntry();
        tool.setToolClass(PanningSelectionToolWithSpyglass.class);

        controlGroup.add(tool);
        paletteRoot.setDefaultEntry(tool);

        tool = new MarqueeToolEntry();
        controlGroup.add(tool);

        paletteRoot.add(controlGroup);

        return;
    }

    /**
     * Create a drawer that is initially open and has ALL possible tools in.
     * <p>
     * By default most of them are hidden. The user can then use the palette
     * customise dialog to override the defaults and show their favourite tools.
     * 
     * @param toolContextHelpUpdater
     * @param paletteRoot
     */
    private void createFavouritesDrawer(
            ToolContextHelpUpdater toolContextHelpUpdater,
            PaletteRoot paletteRoot) {
        ImageRegistry imageReg =
                ProcessWidgetPlugin.getDefault().getImageRegistry();

        List<PaletteEntry> drawer = new ArrayList<PaletteEntry>();

        // Create and add copies of all tools to this drawer - the individual
        // add methods will decide which ones are visible by default.
        addStartEventTools(drawer, true, toolContextHelpUpdater, imageReg);
        addIntermediateCatchEventTools(drawer,
                true,
                toolContextHelpUpdater,
                imageReg);
        addIntermediateThrowEventTools(drawer,
                true,
                toolContextHelpUpdater,
                imageReg);
        addEndEventTools(drawer, true, toolContextHelpUpdater, imageReg);

        addTaskTools(drawer, true, toolContextHelpUpdater, imageReg);
        addGatewayTools(drawer, true, toolContextHelpUpdater, imageReg);
        addArtifactTools(drawer, true, toolContextHelpUpdater, imageReg);

        addConnectionsTools(drawer, true, toolContextHelpUpdater, imageReg);

        PaletteDrawer favourites =
                new PaletteDrawer(
                        Messages.PaletteFactory_Favorites_label,
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_FAVOURITES_16));

        if (!isEmptyDrawer(drawer)) {
            favourites.addAll(drawer);

            favourites.setId(FAVOURITES_DRAWER_ID);
            favourites
                    .setDescription(Messages.PaletteFactory_FavoritesDrawer_description);
            favourites.setInitialState(PaletteDrawer.INITIAL_STATE_PINNED_OPEN);

            paletteRoot.add(favourites);

        }

        return;
    }

    /**
     * Create tools for main node style elements...
     * 
     * 
     * @param toolContextHelpUpdater
     * @param toolContainer
     */
    protected void addObjectCreationTools(
            ToolContextHelpUpdater toolContextHelpUpdater,
            PaletteContainer toolContainer) {

        // create main elements
        ImageRegistry imageReg =
                ProcessWidgetPlugin.getDefault().getImageRegistry();

        if (!ProcessWidgetType.TASK_LIBRARY.equals(processWidgetImpl
                .getProcessWidgetType())) { // Base Events...
            addEventToolsDrawer(toolContextHelpUpdater, toolContainer, imageReg);
        }

        // Generic task, independent sub-proc, embedded sub-proc, event sub-proc
        addTaskToolsDrawer(toolContextHelpUpdater, toolContainer);

        if (!ProcessWidgetType.TASK_LIBRARY.equals(processWidgetImpl
                .getProcessWidgetType())
                && !ProcessWidgetType.DECISION_FLOW.equals(processWidgetImpl
                        .getProcessWidgetType())) { // Gateways
            addGatewayToolsDrawer(toolContextHelpUpdater, toolContainer);
        }

        // Artifacts
        addArtifactToolsDrawer(toolContextHelpUpdater, toolContainer, imageReg);

    }

    /**
     * Create tools for main node style elements...
     * 
     * 
     * @param toolContextHelpUpdater
     * @param toolContainer
     */
    protected void addDecisionFlowObjectCreationTools(
            ToolContextHelpUpdater toolContextHelpUpdater,
            PaletteContainer toolContainer) {

        // create main elements
        ImageRegistry imageReg =
                ProcessWidgetPlugin.getDefault().getImageRegistry();

        // Base Events...
        PaletteDrawer drawer =
                addDecisionFlowTaskToolsDrawer(toolContextHelpUpdater,
                        toolContainer,
                        imageReg);
        if (drawer != null) {
            drawer.setInitialState(PaletteDrawer.INITIAL_STATE_PINNED_OPEN);
        }

        // Artifacts
        drawer =
                addArtifactToolsDrawer(toolContextHelpUpdater,
                        toolContainer,
                        imageReg);
        if (drawer != null) {
            drawer.setInitialState(PaletteDrawer.INITIAL_STATE_PINNED_OPEN);
        }
    }

    private void addEventToolsDrawer(
            ToolContextHelpUpdater toolContextHelpUpdater,
            PaletteContainer toolContainer, ImageRegistry imageReg) {

        // Start Events.
        List<PaletteEntry> startdrawer = new ArrayList<PaletteEntry>();
        addStartEventTools(startdrawer, false, toolContextHelpUpdater, imageReg);

        PaletteDrawer drawer;

        if (!isEmptyDrawer(startdrawer)) {
            drawer =
                    new PaletteDrawer(
                            Messages.PaletteFactory_StartEvents_label,
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_16));

            drawer.addAll(startdrawer);

            toolContainer.add(drawer);

        }

        // Intermediate Catch
        List<PaletteEntry> intermediatedrawer = new ArrayList<PaletteEntry>();
        addIntermediateCatchEventTools(intermediatedrawer,
                false,
                toolContextHelpUpdater,
                imageReg);

        if (!isEmptyDrawer(intermediatedrawer)) {
            drawer =
                    new PaletteDrawer(
                            Messages.PaletteFactory_CatchEvents_label,
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_CATCH_16));

            drawer.addAll(intermediatedrawer);

            toolContainer.add(drawer);

        }

        // Intermediate Throw
        List<PaletteEntry> intermediateThrowDrawer =
                new ArrayList<PaletteEntry>();
        addIntermediateThrowEventTools(intermediateThrowDrawer,
                false,
                toolContextHelpUpdater,
                imageReg);

        if (!isEmptyDrawer(intermediateThrowDrawer)) {
            drawer =
                    new PaletteDrawer(
                            Messages.PaletteFactory_ThrowEvents_label,
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_THROW_16));

            drawer.addAll(intermediateThrowDrawer);

            toolContainer.add(drawer);
        }

        // End Event
        List<PaletteEntry> endEventDrawer = new ArrayList<PaletteEntry>();

        addEndEventTools(endEventDrawer,
                false,
                toolContextHelpUpdater,
                imageReg);

        if (!isEmptyDrawer(endEventDrawer)) {
            drawer =
                    new PaletteDrawer(
                            Messages.PaletteFactory_EndEvents_label,
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_16));

            drawer.addAll(endEventDrawer);

            toolContainer.add(drawer);
        }

    }

    private PaletteDrawer addDecisionFlowTaskToolsDrawer(
            ToolContextHelpUpdater toolContextHelpUpdater,
            PaletteContainer toolContainer, ImageRegistry imageReg) {

        // All tasks and events
        List<PaletteEntry> drawer = new ArrayList<PaletteEntry>();

        XPDElementCreationTool tool;

        //
        // DTABLE
        //
        TaskType taskType = TaskType.DTABLE_LITERAL;
        tool =
                createElementTool(Messages.PaletteFactory_DecisionTableTask_tooltip,
                        Messages.PaletteFactory_DecisionTableTaskToll_description,
                        new SimpleFactory(TaskAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_DTABLETASK_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_DTABLETASK_24),
                        ACTIVITY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        false,
                        new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                        ProcessWidgetColors.DTABLE_TASK_FILL,
                        ProcessWidgetColors.DTABLE_TASK_LINE,
                        ProcessEditorObjectType.task_decisiontable);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);

            drawer.add(tool);
        }

        //
        // Generic start event
        tool =
                createElementTool(Messages.PaletteFactory_StartEventTool_menu,
                        Messages.PaletteFactory_StartEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_24),
                        START_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                                ProcessWidgetConstants.START_EVENT_SIZE),
                        ProcessWidgetColors.START_EVENT_FILL,
                        ProcessWidgetColors.START_EVENT_LINE,
                        ProcessEditorObjectType.start_event_none);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_START_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_NONE_LITERAL);

            drawer.add(tool);
        }

        //
        // Message start event
        tool =
                createElementTool(Messages.PaletteFactory_MsgStartEventTool_menu,
                        Messages.PaletteFactory_MsgStartEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_MESSAGE_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_MESSAGE_24),
                        START_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                                ProcessWidgetConstants.START_EVENT_SIZE),
                        ProcessWidgetColors.START_EVENT_FILL,
                        ProcessWidgetColors.START_EVENT_LINE,
                        ProcessEditorObjectType.start_event_message);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_START_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);

            drawer.add(tool);
        }

        // Generic end event
        tool =
                createElementTool(Messages.PaletteFactory_EndEventTool_menu,
                        Messages.PaletteFactory_EndEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_24),
                        END_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        ProcessWidgetColors.END_EVENT_FILL,
                        ProcessWidgetColors.END_EVENT_LINE,
                        ProcessEditorObjectType.end_event_none);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_END_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_NONE_LITERAL);

            drawer.add(tool);
        }

        // Message end event
        tool =
                createElementTool(Messages.PaletteFactory_MsgEndEventTool_menu,
                        Messages.PaletteFactory_MsgEndEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_MESSAGE_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_MESSAGE_24),
                        END_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        ProcessWidgetColors.END_EVENT_FILL,
                        ProcessWidgetColors.END_EVENT_LINE,
                        ProcessEditorObjectType.end_event_message);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_END_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);

            drawer.add(tool);
        }

        if (!isEmptyDrawer(drawer)) {
            PaletteDrawer taskDrawer =
                    new PaletteDrawer(
                            Messages.PaletteFactory_ActivitiesDrawer_label,
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_16));

            taskDrawer.addAll(drawer);

            toolContainer.add(taskDrawer);

            return taskDrawer;
        }

        return null;
    }

    /**
     * @param toolContextHelpUpdater
     * @param toolContainer
     * @param imageReg
     */
    private void addEndEventTools(List<PaletteEntry> drawer,
            boolean isFavourites,
            ToolContextHelpUpdater toolContextHelpUpdater,
            ImageRegistry imageReg) {
        XPDElementCreationTool tool;

        //
        // Generic end event
        tool =
                createElementTool(Messages.PaletteFactory_EndEventTool_menu,
                        Messages.PaletteFactory_EndEventPalette_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_24),
                        END_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        ProcessWidgetColors.END_EVENT_FILL,
                        ProcessWidgetColors.END_EVENT_LINE,
                        ProcessEditorObjectType.end_event_none);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_END_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_NONE_LITERAL);

            drawer.add(tool);
        }

        //
        // Message end event
        tool =
                createElementTool(Messages.PaletteFactory_MsgEndEventTool_menu,
                        Messages.PaletteFactory_MsgEndEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_MESSAGE_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_MESSAGE_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        ProcessWidgetColors.END_EVENT_FILL,
                        ProcessWidgetColors.END_EVENT_LINE,
                        ProcessEditorObjectType.end_event_message);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_END_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Multiple end event
        tool =
                createElementTool(Messages.PaletteFactory_MultipleEndEventTool_menu,
                        Messages.PaletteFactory_MultipleEndEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_MULTIPLE_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_MULTIPLE_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        ProcessWidgetColors.END_EVENT_FILL,
                        ProcessWidgetColors.END_EVENT_LINE,
                        ProcessEditorObjectType.end_event_multi);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_END_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Error end event
        tool =
                createElementTool(Messages.PaletteFactory_ErrorEndEventTool_menu,
                        Messages.PaletteFactory_ErrorEndEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_ERROR_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_ERROR_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        ProcessWidgetColors.END_EVENT_FILL,
                        ProcessWidgetColors.END_EVENT_LINE,
                        ProcessEditorObjectType.end_event_error);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_END_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_ERROR_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Compensation end event
        tool =
                createElementTool(Messages.PaletteFactory_CompensationEndEventTool_menu,
                        Messages.PaletteFactory_CompensationEndEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_COMPENSATION_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_COMPENSATION_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        ProcessWidgetColors.END_EVENT_FILL,
                        ProcessWidgetColors.END_EVENT_LINE,
                        ProcessEditorObjectType.end_event_compensation);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_END_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Cancel end event
        tool =
                createElementTool(Messages.PaletteFactory_CancelEndEventTool_menu,
                        Messages.PaletteFactory_CancelEndEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_CANCEL_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_CANCEL_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        ProcessWidgetColors.END_EVENT_FILL,
                        ProcessWidgetColors.END_EVENT_LINE,
                        ProcessEditorObjectType.end_event_cancel);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_END_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_CANCEL_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Signal throw end event
        tool =
                createElementTool(Messages.PaletteFactory_SignalEndEventTool_menu,
                        Messages.PaletteFactory_SignalEndEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_SIGNAL_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_SIGNAL_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        ProcessWidgetColors.END_EVENT_FILL,
                        ProcessWidgetColors.END_EVENT_LINE,
                        ProcessEditorObjectType.end_event_signal);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_END_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_SIGNAL_THROW_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Terminate end event
        tool =
                createElementTool(Messages.PaletteFactory_TerminateEndEventTool_menu,
                        Messages.PaletteFactory_TerminateEndEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_TERMINATE_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_END_TERMINATE_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                                ProcessWidgetConstants.END_EVENT_SIZE),
                        ProcessWidgetColors.END_EVENT_FILL,
                        ProcessWidgetColors.END_EVENT_LINE,
                        ProcessEditorObjectType.end_event_terminate);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_END_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_TERMINATE_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

    }

    /**
     * Add tools to create Intermediate Catch events.
     * 
     * @param toolContextHelpUpdater
     * @param toolContainer
     * @param imageReg
     */
    private void addIntermediateCatchEventTools(List<PaletteEntry> drawer,
            boolean isFavourites,
            ToolContextHelpUpdater toolContextHelpUpdater,
            ImageRegistry imageReg) {
        XPDElementCreationTool tool;

        //
        // Generic intermediate event
        tool =
                createElementTool(Messages.PaletteFactory_InterCatchEventTool_menu,
                        Messages.PaletteFactory_InterEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_none);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_NONE_LITERAL);

            drawer.add(tool);
        }

        //
        // Message catch intermediate event
        tool =
                createElementTool(Messages.PaletteFactory_MsgInterCatchEventTool_menu,
                        Messages.PaletteFactory_MsgInterEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_CATCH_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_CATCH_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_message_catch);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Timer intermediate event
        tool =
                createElementTool(Messages.PaletteFactory_TimerInterEventTool_menu,
                        Messages.PaletteFactory_TimerInterEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_TIMER_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_TIMER_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_timer);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_TIMER_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Conditional intermediate event
        tool =
                createElementTool(Messages.PaletteFactory_ConditionalInterEventTool_menu,
                        Messages.PaletteFactory_ConditionalInterEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_CONDITIONAL_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_CONDITIONAL_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_conditional);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_CONDITIONAL_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Link intermediate event
        tool =
                createElementTool(Messages.PaletteFactory_LinkInterCatchEventTool_menu,
                        Messages.PaletteFactory_LinkInterCatchEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_LINK_CATCH_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_LINK_CATCH_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_link_catch);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_LINK_CATCH_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Signal intermediate Catch event
        tool =
                createElementTool(Messages.PaletteFactory_SignalInterCatchEventTool_menu,
                        Messages.PaletteFactory_SignalInterCatchEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_CATCH_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_CATCH_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_signal_catch);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Multiple intermediate catch event
        tool =
                createElementTool(Messages.PaletteFactory_MultipleInterCatchEventTool_menu,
                        Messages.PaletteFactory_MultipleInterCatchEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_CATCH_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_CATCH_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_multi_catch);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Error intermediate event
        tool =
                createElementTool(Messages.PaletteFactory_ErrorInterEventTool_menu,
                        Messages.PaletteFactory_ErrorInterEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_ERROR_CATCH_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_ERROR_CATCH_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_error_catch);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_ERROR_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Compensation Catch intermediate event
        tool =
                createElementTool(Messages.PaletteFactory_CompensationInterCatchEventTool_menu,
                        Messages.PaletteFactory_CompensationInterCatchEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_CATCH_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_CATCH_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_compensation_catch);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Cancel Catch intermediate event
        tool =
                createElementTool(Messages.PaletteFactory_CancelInterEventTool_menu,
                        Messages.PaletteFactory_CancelInterEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_CANCEL_CATCH_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_CANCEL_CATCH_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_cancel_catch);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_CANCEL_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

    }

    /**
     * @param toolContextHelpUpdater
     * @param toolContainer
     * @param imageReg
     */
    private void addIntermediateThrowEventTools(List<PaletteEntry> drawer,
            boolean isFavourites,
            ToolContextHelpUpdater toolContextHelpUpdater,
            ImageRegistry imageReg) {
        XPDElementCreationTool tool;

        //
        // Message throw intermediate event
        tool =
                createElementTool(Messages.PaletteFactory_MessageInterThrowEvent_menu,
                        Messages.PaletteFactory_MessageInterThrowEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_THROW_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_THROW_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_message_throw);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_MESSAGE_THROW_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Compensation Throw intermediate event
        tool =
                createElementTool(Messages.PaletteFactory_CompensationThrowEvent_menu,
                        Messages.PaletteFactory_CompensationThrowEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_THROW_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_THROW_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_compensation_throw);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Signal intermediate throw event
        tool =
                createElementTool(Messages.PaletteFactory_SignalInterThrowEventTool_menu,
                        Messages.PaletteFactory_SignalInterThrowEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_THROW_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_THROW_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_signal_throw);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_SIGNAL_THROW_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Multiple intermediate event
        tool =
                createElementTool(Messages.PaletteFactory_MultipleInterThrowEvent_menu,
                        Messages.PaletteFactory_MultipleInterThrowEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_THROW_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_THROW_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_multi_throw);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Link intermediate throw event
        tool =
                createElementTool(Messages.PaletteFactory_LinkInterThrowEvent_menu,
                        Messages.PaletteFactory_LinkInterThrowEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_LINK_THROW_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_LINK_THROW_24),
                        INTERMEDIATE_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                                ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE),
                        ProcessWidgetColors.INTERMEDIATE_EVENT_FILL,
                        ProcessWidgetColors.INTERMEDIATE_EVENT_LINE,
                        ProcessEditorObjectType.intermediate_event_link_throw);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_INTERMEDIATE_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_LINK_THROW_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }
    }

    /**
     * @param toolContextHelpUpdater
     * @param toolContainer
     * @param imageReg
     */
    private void addStartEventTools(List<PaletteEntry> drawer,
            boolean isFavourites,
            ToolContextHelpUpdater toolContextHelpUpdater,
            ImageRegistry imageReg) {
        XPDElementCreationTool tool;

        tool =
                createElementTool(Messages.PaletteFactory_StartEventTool_menu,
                        Messages.PaletteFactory_StartEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_24),
                        START_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                                ProcessWidgetConstants.START_EVENT_SIZE),
                        ProcessWidgetColors.START_EVENT_FILL,
                        ProcessWidgetColors.START_EVENT_LINE,
                        ProcessEditorObjectType.start_event_none);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_START_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_NONE_LITERAL);

            //
            // Generic start event
            drawer.add(tool);
        }

        //
        // Message start event
        tool =
                createElementTool(Messages.PaletteFactory_MsgStartEventTool_menu,
                        Messages.PaletteFactory_MsgStartEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_MESSAGE_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_MESSAGE_24),
                        START_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                                ProcessWidgetConstants.START_EVENT_SIZE),
                        ProcessWidgetColors.START_EVENT_FILL,
                        ProcessWidgetColors.START_EVENT_LINE,
                        ProcessEditorObjectType.start_event_message);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_START_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Timer start event
        tool =
                createElementTool(Messages.PaletteFactory_TimerStartEventTool_menu,
                        Messages.PaletteFactory_TimerStartEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_TIMER_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_TIMER_24),
                        START_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                                ProcessWidgetConstants.START_EVENT_SIZE),
                        ProcessWidgetColors.START_EVENT_FILL,
                        ProcessWidgetColors.START_EVENT_LINE,
                        ProcessEditorObjectType.start_event_timer);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_START_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_TIMER_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Conditional start event
        tool =
                createElementTool(Messages.PaletteFactory_ConditionalStartEventTool_menu,
                        Messages.PaletteFactory_ConditionalStartEvent_tooltip,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_CONDITIONAL_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_CONDITIONAL_24),
                        START_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                                ProcessWidgetConstants.START_EVENT_SIZE),
                        ProcessWidgetColors.START_EVENT_FILL,
                        ProcessWidgetColors.START_EVENT_LINE,
                        ProcessEditorObjectType.start_event_conditional);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_START_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_CONDITIONAL_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Multiple start event
        tool =
                createElementTool(Messages.PaletteFactory_MultipleStartEventTool_menu,
                        Messages.PaletteFactory_Multiple,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_MULTIPLE_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_MULTIPLE_24),
                        START_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                                ProcessWidgetConstants.START_EVENT_SIZE),
                        ProcessWidgetColors.START_EVENT_FILL,
                        ProcessWidgetColors.START_EVENT_LINE,
                        ProcessEditorObjectType.start_event_multi);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_START_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // Signal start event
        tool =
                createElementTool(Messages.PaletteFactory_SignalStartEventTool_menu,
                        Messages.PaletteFactory_Signal,
                        new SimpleFactory(EventAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_SIGNAL_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_EVENT_START_SIGNAL_24),
                        START_EVENT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                                ProcessWidgetConstants.START_EVENT_SIZE),
                        ProcessWidgetColors.START_EVENT_FILL,
                        ProcessWidgetColors.START_EVENT_LINE,
                        ProcessEditorObjectType.start_event_signal);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_EVENT_FLOW_TYPE,
                    EventFlowType.FLOW_START_LITERAL);
            tool.setRequestExtendedData(EXTDATA_EVENT_TYPE,
                    EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL);

            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }
    }

    protected void addTaskToolsDrawer(
            ToolContextHelpUpdater toolContextHelpUpdater,
            PaletteContainer toolContainer) {
        ImageRegistry imageReg =
                ProcessWidgetPlugin.getDefault().getImageRegistry();

        List<PaletteEntry> drawer = new ArrayList<PaletteEntry>();

        addTaskTools(drawer, false, toolContextHelpUpdater, imageReg);

        if (!isEmptyDrawer(drawer)) {
            PaletteDrawer taskDrawer =
                    new PaletteDrawer(
                            Messages.PaletteFactory_Tasks_label,
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_TASK_16));

            taskDrawer.addAll(drawer);

            toolContainer.add(taskDrawer);
        }
    }

    /**
     * @param drawer
     * @param isFavourites
     * @param toolContextHelpUpdater
     * @param imageReg
     */
    private void addTaskTools(List<PaletteEntry> drawer, boolean isFavourites,
            ToolContextHelpUpdater toolContextHelpUpdater,
            ImageRegistry imageReg) {
        XPDElementCreationTool tool;

        TaskType taskType;

        //
        // UNSPECIFIED
        //
        taskType = TaskType.NONE_LITERAL;
        tool =
                createElementTool(taskType.toString(),
                        Messages.PaletteFactory_TaskTool_tooltip,
                        new SimpleFactory(TaskAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_TASK_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_TASK_24),
                        ACTIVITY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        false,
                        new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                        ProcessWidgetColors.TASK_FILL,
                        ProcessWidgetColors.TASK_LINE,
                        ProcessEditorObjectType.task_none);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);

            drawer.add(tool);
        }

        //
        // USER
        //
        taskType = TaskType.USER_LITERAL;
        tool =
                createElementTool(taskType.toString(),
                        Messages.PaletteFactory_UserTaskTool_tooltip,
                        new SimpleFactory(TaskAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_USERTASK_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_USERTASK_24),
                        ACTIVITY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        false,
                        new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                        ProcessWidgetColors.USER_TASK_FILL,
                        ProcessWidgetColors.USER_TASK_LINE,
                        ProcessEditorObjectType.task_user);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // MANUAL
        //
        taskType = TaskType.MANUAL_LITERAL;
        tool =
                createElementTool(taskType.toString(),
                        Messages.PaletteFactory_ManualTaskTool_tooltip,
                        new SimpleFactory(TaskAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_MANUALTASK_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_MANUALTASK_24),
                        ACTIVITY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        false,
                        new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                        ProcessWidgetColors.MANUAL_TASK_FILL,
                        ProcessWidgetColors.MANUAL_TASK_LINE,
                        ProcessEditorObjectType.task_manual);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // SERVICE
        //
        taskType = TaskType.SERVICE_LITERAL;
        tool =
                createElementTool(taskType.toString(),
                        Messages.PaletteFactory_ServiceTaskTool_tooltip,
                        new SimpleFactory(TaskAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SERVICETASK_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SERVICETASK_24),
                        ACTIVITY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        false,
                        new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                        ProcessWidgetColors.SERVICE_TASK_FILL,
                        ProcessWidgetColors.SERVICE_TASK_LINE,
                        ProcessEditorObjectType.task_service);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // SCRIPT
        //
        taskType = TaskType.SCRIPT_LITERAL;
        tool =
                createElementTool(taskType.toString(),
                        Messages.PaletteFactory_ScriptTaskTool_tooltip,
                        new SimpleFactory(TaskAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SCRIPTTASK_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SCRIPTTASK_24),
                        ACTIVITY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        false,
                        new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                        ProcessWidgetColors.SCRIPT_TASK_FILL,
                        ProcessWidgetColors.SCRIPT_TASK_LINE,
                        ProcessEditorObjectType.task_script);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        //
        // SEND
        //
        taskType = TaskType.SEND_LITERAL;
        tool =
                createElementTool(taskType.toString(),
                        Messages.PaletteFactory_SendTaskTool_tooltip,
                        new SimpleFactory(TaskAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SENDTASK_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SENDTASK_24),
                        ACTIVITY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        false,
                        new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                        ProcessWidgetColors.SEND_TASK_FILL,
                        ProcessWidgetColors.SEND_TASK_LINE,
                        ProcessEditorObjectType.task_send);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        if (!ProcessWidgetType.TASK_LIBRARY.equals(processWidgetImpl
                .getProcessWidgetType())
                && !ProcessWidgetType.DECISION_FLOW.equals(processWidgetImpl
                        .getProcessWidgetType())) {
            //
            // RECEIVE
            //
            taskType = TaskType.RECEIVE_LITERAL;
            tool =
                    createElementTool(taskType.toString(),
                            Messages.PaletteFactory_ReceiveTaskTool_tooltip,
                            new SimpleFactory(TaskAdapter.class),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_RECEIVETASK_16),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_RECEIVETASK_24),
                            ACTIVITY_TOOL_HELP_CONTEXT,
                            toolContextHelpUpdater,
                            false,
                            new Dimension(
                                    ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                    ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                            ProcessWidgetColors.RECEIVE_TASK_FILL,
                            ProcessWidgetColors.RECEIVE_TASK_LINE,
                            ProcessEditorObjectType.task_receive);

            if (tool != null) {
                tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);
                if (isFavourites) {
                    tool.setVisible(false);
                }
                drawer.add(tool);
            }
        }

        if (!ProcessWidgetType.TASK_LIBRARY.equals(processWidgetImpl
                .getProcessWidgetType())
                && !ProcessWidgetType.DECISION_FLOW.equals(processWidgetImpl
                        .getProcessWidgetType())) {
            //
            // REFERENCE
            //
            taskType = TaskType.REFERENCE_LITERAL;
            tool =
                    createElementTool(taskType.toString(),
                            Messages.PaletteFactory_ReferenceTaskTool_tooltip,
                            new SimpleFactory(TaskAdapter.class),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_REFERENCETASK_16),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_REFERENCETASK_24),
                            ACTIVITY_TOOL_HELP_CONTEXT,
                            toolContextHelpUpdater,
                            false,
                            new Dimension(
                                    ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                    ProcessWidgetConstants.TASK_HEIGHT_SIZE),
                            ProcessWidgetColors.REFERENCE_TASK_FILL,
                            ProcessWidgetColors.REFERENCE_TASK_LINE,
                            ProcessEditorObjectType.task_reference);

            if (tool != null) {
                tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);
                if (isFavourites) {
                    tool.setVisible(false);
                }
                drawer.add(tool);
            }
        }

        //
        // Sub-process tasks..
        //
        if (!isFavourites) {
            drawer.add(new PaletteSeparator());
        }

        taskType = TaskType.SUBPROCESS_LITERAL;

        tool =
                createElementTool(taskType.toString(),
                        Messages.PaletteFactory_SubProcTaskTool_tooltip,
                        new SimpleFactory(TaskAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SUBPROC_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SUBPROC_24),
                        ACTIVITY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        false,
                        new Dimension(
                                ProcessWidgetConstants.SUBFLOW_WIDTH_SIZE,
                                ProcessWidgetConstants.SUBFLOW_HEIGHT_SIZE),
                        ProcessWidgetColors.SUBPROCESS_TASK_FILL,
                        ProcessWidgetColors.SUBPROCESS_TASK_LINE,
                        ProcessEditorObjectType.task_subprocess);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        if (!ProcessWidgetType.TASK_LIBRARY.equals(processWidgetImpl
                .getProcessWidgetType())
                && !ProcessWidgetType.DECISION_FLOW.equals(processWidgetImpl
                        .getProcessWidgetType())) {
            taskType = TaskType.EMBEDDED_SUBPROCESS_LITERAL;

            tool =
                    createElementTool(taskType.toString(),
                            Messages.PaletteFactory_EmbeddedSubProcTaskTool_tooltip,
                            new SimpleFactory(TaskAdapter.class),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SUBPROC_EMBEDDED_16),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SUBPROC_EMBEDDED_24),
                            ACTIVITY_TOOL_HELP_CONTEXT,
                            toolContextHelpUpdater,
                            false,
                            new Dimension(
                                    ProcessWidgetConstants.EMBSUBFLOW_WIDTH_SIZE,
                                    ProcessWidgetConstants.EMBSUBFLOW_HEIGHT_SIZE),
                            ProcessWidgetColors.EMBEDDEDSUBPROCESS_TASK_FILL,
                            ProcessWidgetColors.EMBEDDEDSUBPROCESS_TASK_LINE,
                            ProcessEditorObjectType.embedded_subprocess);

            if (tool != null) {
                tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);
                drawer.add(tool);
            }
        }

        /*
         * ABPM-911: Saket: Add Event sub-process.
         */
        if (!ProcessWidgetType.TASK_LIBRARY.equals(processWidgetImpl
                .getProcessWidgetType())
                && !ProcessWidgetType.DECISION_FLOW.equals(processWidgetImpl
                        .getProcessWidgetType())) {
            taskType = TaskType.EVENT_SUBPROCESS_LITERAL;

            tool =
                    createElementTool(taskType.toString(),
                            Messages.PaletteFactory_EventSubProcTaskTool_tooltip,
                            new SimpleFactory(TaskAdapter.class),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SUBPROC_EVENT_16),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_SUBPROC_EVENT_24),
                            ACTIVITY_TOOL_HELP_CONTEXT,
                            toolContextHelpUpdater,
                            false,
                            new Dimension(
                                    ProcessWidgetConstants.EMBSUBFLOW_WIDTH_SIZE,
                                    ProcessWidgetConstants.EMBSUBFLOW_HEIGHT_SIZE),
                            ProcessWidgetColors.EMBEDDEDSUBPROCESS_TASK_FILL,
                            ProcessWidgetColors.EMBEDDEDSUBPROCESS_TASK_LINE,
                            ProcessEditorObjectType.event_subprocess);

            if (tool != null) {
                tool.setRequestExtendedData(EXTDATA_TASK_TYPE, taskType);
                drawer.add(tool);
            }
        }
    }

    protected void addGatewayToolsDrawer(
            ToolContextHelpUpdater toolContextHelpUpdater,
            PaletteContainer toolContainer) {
        ImageRegistry imageReg =
                ProcessWidgetPlugin.getDefault().getImageRegistry();

        List<PaletteEntry> drawer = new ArrayList<PaletteEntry>();

        addGatewayTools(drawer, false, toolContextHelpUpdater, imageReg);

        if (!isEmptyDrawer(drawer)) {
            PaletteDrawer gatewayDrawer =
                    new PaletteDrawer(
                            Messages.PaletteFactory_Gateways_label,
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_GENERIC_GATEWAY_16));

            gatewayDrawer.addAll(drawer);

            toolContainer.add(gatewayDrawer);
        }

    }

    /**
     * @param drawer
     * @param isFavourites
     * @param toolContextHelpUpdater
     * @param imageReg
     */
    private void addGatewayTools(List<PaletteEntry> drawer,
            boolean isFavourites,
            ToolContextHelpUpdater toolContextHelpUpdater,
            ImageRegistry imageReg) {
        XPDElementCreationTool tool;

        tool =
                createElementTool(Messages.PaletteFactory_XORGatewayTool_menu,
                        Messages.PaletteFactory_XORGatewayTool_tooltip,
                        new SimpleFactory(GatewayAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_XORDATA_GATEWAY_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_XORDATA_GATEWAY_24),
                        GATEWAY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.GATEWAY_WIDTH_SIZE,
                                ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE),
                        ProcessWidgetColors.EXCLUSIVE_DATA_GATEWAY_FILL,
                        ProcessWidgetColors.XORDATA_GATEWAY_LINE,
                        ProcessEditorObjectType.gateway_exclusive_data_based);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_GATEWAY_TYPE,
                    GatewayType.EXCLUSIVE_DATA_LITERAL);
            drawer.add(tool);
        }

        tool =
                createElementTool(Messages.PaletteFactory_ParallelGatewayTool_menu,
                        Messages.PaletteFactory_ParallelGatewayTool_tooltip,
                        new SimpleFactory(GatewayAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_PARALLEL_GATEWAY_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_PARALLEL_GATEWAY_24),
                        GATEWAY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.GATEWAY_WIDTH_SIZE,
                                ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE),
                        ProcessWidgetColors.PARALLEL_GATEWAY_FILL,
                        ProcessWidgetColors.AND_GATEWAY_LINE,
                        ProcessEditorObjectType.gateway_parallel);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_GATEWAY_TYPE,
                    GatewayType.PARALLEL_LITERAL);
            drawer.add(tool);
        }

        tool =
                createElementTool(Messages.PaletteFactory_XOREventGatewayTool_menu,
                        Messages.PaletteFactory_XOREventGatewayTool_tooltip,
                        new SimpleFactory(GatewayAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_XOREVENT_GATEWAY_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_XOREVENT_GATEWAY_24),
                        GATEWAY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.GATEWAY_WIDTH_SIZE,
                                ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE),
                        ProcessWidgetColors.EXCLUSIVE_EVENT_GATEWAY_FILL,
                        ProcessWidgetColors.XOREVENT_GATEWAY_LINE,
                        ProcessEditorObjectType.gateway_exclusive_event_based);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_GATEWAY_TYPE,
                    GatewayType.EXLCUSIVE_EVENT_LITERAL);
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        tool =
                createElementTool(Messages.PaletteFactory_ORGatewayTool_menu,
                        Messages.PaletteFactory_ORGatewayTool_tooltip,
                        new SimpleFactory(GatewayAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_OR_GATEWAY_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_OR_GATEWAY_24),
                        GATEWAY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.GATEWAY_WIDTH_SIZE,
                                ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE),
                        ProcessWidgetColors.INCLUSIVE_GATEWAY_FILL,
                        ProcessWidgetColors.OR_GATEWAY_LINE,
                        ProcessEditorObjectType.gateway_inclusive);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_GATEWAY_TYPE,
                    GatewayType.INCLUSIVE_LITERAL);
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        tool =
                createElementTool(Messages.PaletteFactory_ComplexGatewayTool_menu,
                        Messages.PaletteFactory_ComplexGatewayTool_tooltip,
                        new SimpleFactory(GatewayAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_COMPLEX_GATEWAY_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_COMPLEX_GATEWAY_24),
                        GATEWAY_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.GATEWAY_WIDTH_SIZE,
                                ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE),
                        ProcessWidgetColors.COMPLEX_GATEWAY_FILL,
                        ProcessWidgetColors.COMPLEX_GATEWAY_LINE,
                        ProcessEditorObjectType.gateway_complex);

        if (tool != null) {
            tool.setRequestExtendedData(EXTDATA_GATEWAY_TYPE,
                    GatewayType.COMPLEX_LITERAL);
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }
    }

    protected PaletteDrawer addConnectionToolsDrawer(
            ToolContextHelpUpdater toolContextHelpUpdater,
            PaletteContainer toolContainer) {

        ImageRegistry imageReg =
                ProcessWidgetPlugin.getDefault().getImageRegistry();

        List<PaletteEntry> drawer = new ArrayList<PaletteEntry>();

        addConnectionsTools(drawer, false, toolContextHelpUpdater, imageReg);

        if (!isEmptyDrawer(drawer)) {
            PaletteDrawer connDrawer =
                    new PaletteDrawer(
                            Messages.PaletteFactory_Connections_label,
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_FLOW_16));

            connDrawer.addAll(drawer);
            toolContainer.add(connDrawer);

            return connDrawer;
        }

        return null;
    }

    /**
     * @param drawer
     * @param toolContextHelpUpdater
     * @param imageReg
     */
    private void addConnectionsTools(List<PaletteEntry> drawer,
            boolean isFavourites,
            ToolContextHelpUpdater toolContextHelpUpdater,
            ImageRegistry imageReg) {
        FlowConnectionToolEntry tool;
        if (!ProcessWidgetType.TASK_LIBRARY.equals(processWidgetImpl
                .getProcessWidgetType())) {
            tool =
                    createFlowConnectionToolEntry(Messages.PaletteFactory_SeqFlowTool_menu,
                            Messages.PaletteFactory_SeqFlowTool_tooltip,
                            new SimpleFactory(SequenceFlowAdapter.class),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_FLOW_16),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_FLOW_24),
                            SEQUENCE_FLOW_TOOL_HELP_CONTEXT,
                            toolContextHelpUpdater,
                            processWidgetImpl,
                            ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE,
                            ProcessEditorObjectType.sequenceflow_uncontrolled);

            if (tool != null) {
                tool.setRequestExtendedData(EXTDATA_SEQUENCEFLOW_TYPE,
                        SequenceFlowType.UNCONTROLLED_LITERAL);

                drawer.add(tool);
            }

            if (!ProcessWidgetType.DECISION_FLOW.equals(processWidgetImpl
                    .getProcessWidgetType())) {
                tool =
                        createFlowConnectionToolEntry(Messages.PaletteFactory_ConditionalSeqFlowTool_menu,
                                Messages.PaletteFactory_ConditionalSeqFlowTool_tooltip,
                                new SimpleFactory(SequenceFlowAdapter.class),
                                imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_FLOWCONDITIONAL_16),
                                imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_FLOWCONDITIONAL_24),
                                SEQUENCE_FLOW_TOOL_HELP_CONTEXT,
                                toolContextHelpUpdater,
                                processWidgetImpl,
                                ProcessWidgetColors.CONDITIONAL_SEQ_FLOW_LINE,
                                ProcessEditorObjectType.sequenceflow_conditional);

                if (tool != null) {
                    tool.setRequestExtendedData(EXTDATA_SEQUENCEFLOW_TYPE,
                            SequenceFlowType.CONDITIONAL_LITERAL);

                    if (isFavourites) {
                        tool.setVisible(false);
                    }
                    drawer.add(tool);
                }

                tool =
                        createFlowConnectionToolEntry(Messages.PaletteFactory_DefaultSeqFlowTool_menu,
                                Messages.PaletteFactory_DefaultSeqFlowTool_tooltip,
                                new SimpleFactory(SequenceFlowAdapter.class),
                                imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_FLOWDEFAULT_16),
                                imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_FLOWDEFAULT_24),
                                SEQUENCE_FLOW_TOOL_HELP_CONTEXT,
                                toolContextHelpUpdater,
                                processWidgetImpl,
                                ProcessWidgetColors.DEFAULT_SEQ_FLOW_LINE,
                                ProcessEditorObjectType.sequenceflow_default);

                if (tool != null) {
                    tool.setRequestExtendedData(EXTDATA_SEQUENCEFLOW_TYPE,
                            SequenceFlowType.DEFAULT_LITERAL);

                    if (isFavourites) {
                        tool.setVisible(false);
                    }
                    drawer.add(tool);
                }
            }
        }

        //
        // Message flow and associations.
        if (!ProcessWidgetType.TASK_LIBRARY.equals(processWidgetImpl
                .getProcessWidgetType())
                && !ProcessWidgetType.DECISION_FLOW.equals(processWidgetImpl
                        .getProcessWidgetType())) {
            if (!isFavourites) {
                drawer.add(new PaletteSeparator());
            }

            tool =
                    createFlowConnectionToolEntry(Messages.PaletteFactory_MessageFlowTool_menu,
                            Messages.PaletteFactory_MessageFlowTool_tooltip,
                            new SimpleFactory(MessageFlowAdapter.class),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_MESSAGE_16),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_MESSAGE_24),
                            MESSAGE_FLOW_TOOL_HELP_CONTEXT,
                            toolContextHelpUpdater,
                            processWidgetImpl,
                            ProcessWidgetColors.MESSAGE_FLOW_LINE,
                            ProcessEditorObjectType.messageflow);

            if (tool != null) {

                if (isFavourites) {
                    tool.setVisible(false);
                }
                drawer.add(tool);
            }
        }

        tool =
                createFlowConnectionToolEntry(Messages.PaletteFactory_AssociationTool_menu,
                        Messages.PaletteFactory_AssociationTool_tooltip,
                        new SimpleFactory(AssociationAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_ASSOCIATION_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_ASSOCIATION_24),
                        ASSOCIATION_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        processWidgetImpl,
                        ProcessWidgetColors.ASSOCIATION_LINE,
                        ProcessEditorObjectType.association_connection);

        if (tool != null) {
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }
    }

    /**
     * @param toolContextHelpUpdater
     * @param toolContainer
     * @param imageReg
     */
    private PaletteDrawer addArtifactToolsDrawer(
            ToolContextHelpUpdater toolContextHelpUpdater,
            PaletteContainer toolContainer, ImageRegistry imageReg) {

        List<PaletteEntry> drawer = new ArrayList<PaletteEntry>();

        addArtifactTools(drawer, false, toolContextHelpUpdater, imageReg);

        if (!isEmptyDrawer(drawer)) {
            PaletteDrawer artDrawer =
                    new PaletteDrawer(
                            Messages.PaletteFactory_Artifacts_label,
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_LANE_16));

            artDrawer.addAll(drawer);

            toolContainer.add(artDrawer);

            return artDrawer;
        }

        return null;

    }

    /**
     * @param drawer
     * @param isFavourites
     * @param toolContextHelpUpdater
     * @param imageReg
     */
    private void addArtifactTools(List<PaletteEntry> drawer,
            boolean isFavourites,
            ToolContextHelpUpdater toolContextHelpUpdater,
            ImageRegistry imageReg) {
        XPDElementCreationTool tool;
        tool =
                createElementTool(Messages.PaletteFactory_TextAnnotTool_menu,
                        Messages.PaletteFactory_TextAnnotTool_tooltip,
                        new SimpleFactory(NoteAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_NOTE_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_NOTE_24),
                        NOTE_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(0, ProcessWidgetConstants.NOTE_HEIGHT),
                        ProcessWidgetColors.NOTE_FILL,
                        ProcessWidgetColors.NOTE_LINE,
                        ProcessEditorObjectType.artifact_text_annotation);

        if (tool != null) {
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        tool =
                createElementTool(Messages.PaletteFactory_DataObjectTool_menu,
                        Messages.PaletteFactory_DataObjectTool_tooltip,
                        new SimpleFactory(DataObjectAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_DATA_OBJECT_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_DATA_OBJECT_24),
                        DATAOBJECT_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        true,
                        new Dimension(
                                ProcessWidgetConstants.DATAOBJECT_WIDTH_SIZE,
                                ProcessWidgetConstants.DATAOBJECT_HEIGHT_SIZE),
                        ProcessWidgetColors.DATAOBJECT_FILL,
                        ProcessWidgetColors.DATAOBJECT_LINE,
                        ProcessEditorObjectType.artifact_data_object);

        if (tool != null) {
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        tool =
                createElementTool(Messages.PaletteFactory_GroupTool_menu,
                        Messages.PaletteFactory_GroupTool_tooltip,
                        new SimpleFactory(GroupAdapter.class),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_GROUP_16),
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_GROUP_24),
                        GROUP_TOOL_HELP_CONTEXT,
                        toolContextHelpUpdater,
                        false,
                        new Dimension(ProcessWidgetConstants.GROUP_WIDTH_SIZE,
                                ProcessWidgetConstants.GROUP_HEIGHT_SIZE),
                        null,
                        ProcessWidgetColors.GROUP_LINE,
                        ProcessEditorObjectType.artifact_group_box);

        if (tool != null) {
            if (isFavourites) {
                tool.setVisible(false);
            }
            drawer.add(tool);
        }

        if (!isFavourites) {
            drawer.add(new PaletteSeparator());
        }

        //
        // Pool (except fr task library and pageflow
        if (!DiagramViewType.TASK_LIBRARY_ALTERNATE.equals(processWidgetImpl
                .getDiagramViewType())
                && !DiagramViewType.NO_POOLS.equals(processWidgetImpl
                        .getDiagramViewType())) {
            tool =
                    createElementTool(Messages.PaletteFactory_PoolTool_menu,
                            Messages.PaletteFactory_PoolTool_tooltip,
                            new SimpleFactory(PoolAdapter.class),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_POOL_16),
                            imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_POOL_24),
                            POOL_TOOL_HELP_CONTEXT,
                            toolContextHelpUpdater,
                            false,
                            null,
                            ProcessWidgetColors.POOL_HEADER_FILL,
                            ProcessWidgetColors.POOL_HEADER_LINE,
                            null);

            if (tool != null) {
                if (isFavourites) {
                    tool.setVisible(false);
                }
                drawer.add(tool);
            }
        }

        //
        // Lane (or Task Set) or none for pageflow.
        if (!DiagramViewType.NO_POOLS.equals(processWidgetImpl
                .getDiagramViewType())) {
            String laneLabel;
            String laneTooltip;
            ImageDescriptor laneToolImage16;
            ImageDescriptor laneToolImage24;

            if (ProcessWidgetType.TASK_LIBRARY.equals(processWidgetImpl
                    .getProcessWidgetType())) {
                laneLabel = Messages.PaletteFactory_TaskSetTool_menu;
                laneTooltip = Messages.PaletteFactory_TaskSet_tooltip;

                laneToolImage16 =
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_TASKSET_16);
                laneToolImage24 =
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_TASKSET_24);

            } else {
                laneLabel = Messages.PaletteFactory_LaneTool_menu;
                laneTooltip = Messages.PaletteFactory_LaneTool_tooltip;

                laneToolImage16 =
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_LANE_16);
                laneToolImage24 =
                        imageReg.getDescriptor(ProcessWidgetConstants.IMG_TOOL_LANE_24);
            }

            tool =
                    createElementTool(laneLabel,
                            laneTooltip,
                            new SimpleFactory(LaneAdapter.class),
                            laneToolImage16,
                            laneToolImage24,
                            LANE_TOOL_HELP_CONTEXT,
                            toolContextHelpUpdater,
                            false,
                            null,
                            ProcessWidgetColors.LANE_FILL,
                            ProcessWidgetColors.LANE_LINE,
                            null);
            if (tool != null) {
                if (isFavourites) {
                    tool.setVisible(false);
                }
                drawer.add(tool);
            }
        }
    }

    private XPDElementCreationTool createElementTool(String name, String desc,
            CreationFactory creationFactory, ImageDescriptor smallImg,
            ImageDescriptor bigImg, String contextHelpId,
            ToolContextHelpUpdater toolContextHelpUpdater,
            boolean noSizeOnCreate, Dimension defaultSize,
            String defFillColorId, String defLineColorid,
            ProcessEditorObjectType objectType) {

        /*
         * XPD-2516: Don't create tools that are in the object type exclusion
         * list.
         */
        if (excludedObjectTypes.contains(objectType)) {
            return null;
        }
        XPDElementCreationTool tool;
        tool =
                new XPDElementCreationTool(name, desc, creationFactory,
                        smallImg, bigImg, contextHelpId,
                        toolContextHelpUpdater, noSizeOnCreate,
                        processWidgetImpl);

        if (defaultSize != null) {
            tool.setRequestExtendedData(EXTDATA_INITIAL_SIZE, defaultSize);
        }

        if (defFillColorId != null) {
            tool.setRequestExtendedData(EXTDATA_DEFAULT_FILL_COLORID,
                    defFillColorId);
        }

        if (defLineColorid != null) {
            tool.setRequestExtendedData(EXTDATA_DEFAULT_LINE_COLORID,
                    defLineColorid);
        }

        tool.setRequestExtendedData(EXTDATA_PROCESS_EDITOR_OBJECT_TYPE,
                objectType);

        return tool;
    }

    /**
     * @param label
     * @param tooltip
     * @param simpleFactory
     * @param descriptor
     * @param descriptor2
     * @param associationToolHelpContext
     * @param toolContextHelpUpdater
     * @param processWidgetImpl
     * 
     * @return connection tool entry with given parameters.
     */
    private FlowConnectionToolEntry createFlowConnectionToolEntry(String label,
            String tooltip, SimpleFactory simpleFactory,
            ImageDescriptor iconSmall, ImageDescriptor iconLarge,
            String helpContext, ToolContextHelpUpdater toolContextHelpUpdater,
            ProcessWidget processWidgetImpl, String defaultColorId,
            ProcessEditorObjectType objectType) {

        /*
         * XPD-2516: Don't create tools that are in the object type exclusion
         * list.
         */
        if (excludedObjectTypes.contains(objectType)) {
            return null;
        }

        FlowConnectionToolEntry tool =
                new FlowConnectionToolEntry(label, tooltip, simpleFactory,
                        iconSmall, iconLarge, helpContext,
                        toolContextHelpUpdater, processWidgetImpl);

        tool.setRequestExtendedData(EXTDATA_DEFAULT_LINE_COLORID,
                defaultColorId);

        tool.setRequestExtendedData(EXTDATA_PROCESS_EDITOR_OBJECT_TYPE,
                objectType);

        return tool;
    }

    /**
     * @param drawer
     * @return true if list has no entries or only palette separator entries.
     */
    private boolean isEmptyDrawer(List<PaletteEntry> drawer) {
        if (drawer.isEmpty()) {
            return true;
        }

        for (PaletteEntry paletteEntry : drawer) {
            if (!(paletteEntry instanceof PaletteSeparator)
                    && paletteEntry.isVisible()) {
                return false;
            }
        }
        return true;
    }

}
