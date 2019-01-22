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

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil.ProjectReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper.GlobalDestinationInfo;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.AbstractRefactorAsSubProcCommand;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsEmbeddedSubProcCommand;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsEventSubProcCommand;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsIndependentPageflowSubProcCommand;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsIndependentServiceProcessSubProcCommand;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsIndependentSubProcCommand;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsPageflowProcessCommand;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.resourcePatterns.RetainFamiliarCommand;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.resourcePatterns.SeparationOfDutiesCommand;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.CopyPasteScope;
import com.tibco.xpd.processwidget.adapters.CreateAccessibleObjectCommand;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.adapters.MarkerAndModelObject;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessPasteCommand;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.adapters.XpdFlowRoutingStyle;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;
import com.tibco.xpd.validation.IValidationListener;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.ValidationEvent;
import com.tibco.xpd.xpdExtension.FlowRoutingStyle;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataObject;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.GraphicalConnector;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.No;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessHeader;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.ocl.NotificationsConstants;
import com.tibco.xpd.xpdl2.util.ocl.OclBasedNotifier;
import com.tibco.xpd.xpdl2.util.ocl.OclQueryListener;
import com.tibco.xpd.xpdl2.util.ocl.SimpleViewerNotification;

/**
 * Adapter for XPDL Process - the root object for the diagram
 * 
 * @author wzurek
 */
public class Xpdl2ProcessDiagramAdapter extends Xpdl2BaseProcessAdapter
        implements ProcessDiagramAdapter, OclQueryListener,
        IValidationListener {

    private OclBasedNotifier packageNotifier;

    private List<ProblemMarkerListChangedListener> problemListChangedListeners =
            new ArrayList<ProblemMarkerListChangedListener>();

    /** Default routing style - New default is multiple entry / exit points. */
    public final static FlowRoutingStyle DEFAULT_FLOW_ROUTING_STYLE =
            FlowRoutingStyle.MULTI_ENTRY_EXIT;

    /**
     * Most recent list of problem markers related to this process.
     * <p>
     * Note that this may contain markers for elements that are (or are
     * descendents of) elements that are not actually related. This is because
     * we wish to keep the caching of problems as efficient as possible and some
     * xpdl elements are difficult to relate to a process, currently these
     * include...
     * <li>MessageFlows
     * <li>Associations
     * <li>DataObjects
     * <p>
     * Markers related to these elements (or descendants) are ALWAYS added to
     * the list of markers for this process as it is quicker to cache them and
     * ignore them later when asked for a problems for a specific diagram model
     * object such as activity)
     */
    private List<MarkerAndModelObject> problemMarkerListCache =
            Collections.EMPTY_LIST;

    private List<MarkerAndModelObject> previousProblemMarkerListCache =
            Collections.EMPTY_LIST;

    @Override
    public Process getProcess() {
        return (Process) getTarget();
    }

    /**
     * XPDL2 has Pools on the package level
     * 
     * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#getPools()
     */
    @Override
    public List getPools() {

        return Xpdl2ProcessDiagramUtils.getProcessPools(getProcess());
    }

    /**
     * XPDL2 has Pools on the Package Level
     * 
     * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#getCreateNewPoolCommand(java.lang.String)
     */
    @Override
    public Command getCreateNewPoolCommand(EditingDomain ed, int index,
            String poolName, String fillColor, String lineColor) {

        Process proc = getProcess();
        Package pck = proc.getPackage();
        Pool pool = ElementsFactory.createPool(poolName, proc.getId());

        NodeGraphicsInfo nodeGraphicsInfo =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(pool);

        nodeGraphicsInfo.setIsVisible(true);
        nodeGraphicsInfo.setFillColor(fillColor);
        nodeGraphicsInfo.setBorderColor(lineColor);

        CompoundCommand cmd = new CompoundCommand();

        //
        // Package has pools for ALL processes, the index we are given is within
        // THIS process. So we have to find the right position in all pools list
        // to insert.
        List<Pool> procPools = new ArrayList<Pool>();
        for (Iterator iter = pck.getPools().iterator(); iter.hasNext();) {
            Pool p = (Pool) iter.next();

            if (proc.getId().equals(p.getProcessId())) {
                procPools.add(p);
            }
        }

        Command add = null;

        if (index < procPools.size()) {
            // Find position of insert above pool in main list.
            int mainIdx = pck.getPools().indexOf(procPools.get(index));

            add = AddCommand.create(ed,
                    pck,
                    Xpdl2Package.eINSTANCE.getPackage_Pools(),
                    pool,
                    mainIdx);
        }

        // Index is beyond range, add to end of list of all procs is fine.
        if (add == null) {
            add = AddCommand.create(ed,
                    pck,
                    Xpdl2Package.eINSTANCE.getPackage_Pools(),
                    pool);
        }

        cmd.append(add);
        cmd.setLabel(Messages.Xpdl2ProcessDiagramAdapter_AddPoolDiagram_menu);
        return cmd;
    }

    /**
     * Change order of pools
     * 
     * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#getMovePoolCommand(int,
     *      int)
     */
    @Override
    public Command getMovePoolCommand(EditingDomain eDomain, int fromIndex,
            int toIndex) {

        Process proc = getProcess();
        Package pck = proc.getPackage();
        //
        // Package has pools for ALL processes, the index we are given is within
        // THIS process. So we have to find the right position in all pools list
        // to insert.

        List<Pool> procPools = new ArrayList<Pool>();
        for (Iterator iter = pck.getPools().iterator(); iter.hasNext();) {
            Pool p = (Pool) iter.next();

            if (proc.getId().equals(p.getProcessId())) {
                procPools.add(p);
            }
        }

        if (fromIndex < procPools.size()) {
            Pool poolToMove = procPools.get(fromIndex);

            if (toIndex < procPools.size()) {
                // Find position of insert above pool in main list.
                int mainIdx = pck.getPools().indexOf(procPools.get(toIndex));

                return MoveCommand.create(eDomain,
                        pck,
                        Xpdl2Package.eINSTANCE.getPackage_Pools(),
                        poolToMove,
                        mainIdx);
            }
        }

        return UnexecutableCommand.INSTANCE;

    }

    /**
     * Not possible to remove the diagram from the diagram
     * 
     * @see com.tibco.xpd.processwidget.adapters.BaseProcessAdapter#getDeleteCommand()
     */
    @Override
    public Command getDeleteCommand(EditingDomain editingDomain) {
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.ProcessContainerAdapter#getChildGraphicalNodes()
     */
    @Override
    public List getChildGraphicalNodes() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Command getAddGraphicalNodeCommand(EditingDomain editingDomain,
            Object model, Point location) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public CreateAccessibleObjectCommand getCreateArtifactCommand(
            EditingDomain editingDomain, Object type, Point location,
            Dimension size, String fillColor, String lineColor) {
        return new CreateAccessibleObjectCommand(UnexecutableCommand.INSTANCE,
                null);
    }

    @Override
    public void notifyChanged(Notification msg) {

        if (getProcess() == null || getProcess().eResource() == null) {
            /*
             * Process has been removed from the model, ignore all
             * notifications.
             */
            return;
        }

        if (msg.getNotifier() == getTarget()) {

            /* Look for Flow Routing Style changes. */
            if (XpdExtensionPackage.eINSTANCE.getDocumentRoot_FlowRoutingStyle()
                    .equals(msg.getFeature())) {
                /* Any change (add, set, remove) means we need to refresh). */
                /*
                 * Sid XPD-8302 - pass message in so can ignore adapter removal
                 */
                fireDiagramNotification(msg);
            }

            switch (msg.getFeatureID(FlowContainer.class)) {
            case Xpdl2Package.FLOW_CONTAINER__TRANSITIONS:
                Collection trans;
                switch (msg.getEventType()) {
                case Notification.ADD:
                    trans = Collections
                            .singleton((Transition) msg.getNewValue());
                    break;
                case Notification.REMOVE:
                    trans = (Collections
                            .singleton((Transition) msg.getOldValue()));
                    break;
                case Notification.REMOVE_MANY:
                    trans = (Collection) msg.getOldValue();
                    break;
                case Notification.ADD_MANY:
                    trans = (Collection) msg.getNewValue();
                    break;
                default:
                    trans = Collections.EMPTY_LIST;
                }

                Set ids = new HashSet(trans.size() * 2);
                for (Iterator iter = trans.iterator(); iter.hasNext();) {
                    Transition tr = (Transition) iter.next();
                    ids.add(tr.getFrom());
                    ids.add(tr.getTo());
                }
                // when we are adding/removing transitions, we need to refresh
                // activities that it conects
                refreshElement(ids);
            }
        }
        refreshLanes();
    }

    private void refreshLanes() {
        List pools = getPools();
        for (Iterator iter = pools.iterator(); iter.hasNext();) {
            Pool pool = (Pool) iter.next();
            for (Iterator iterator = pool.getLanes().iterator(); iterator
                    .hasNext();) {
                Lane lane = (Lane) iterator.next();
                lane.eNotify(new SimpleViewerNotification(lane, true, false));
            }
        }
    }

    private void refreshElement(Set ids) {
        Process proc = getProcess();
        NotificationImpl ev = new NotificationImpl(
                NotificationsConstants.REFRESH_REFERENCED_ELEMENT, null, null);
        for (Iterator iter = ids.iterator(); iter.hasNext();) {
            String id = (String) iter.next();

            NamedElement el;
            if (proc.getId().equals(id)) {
                /*
                 * Sid XPD-8302 - pass message in so can ignore adapter removal
                 */
                fireDiagramNotification(ev);
                continue;
            } else {
                el = proc.getActivity(id);
            }

            if (el == null) {
                el = proc.getPackage().findNamedElement(id);
            }
            if (el != null) {
                if (!(el instanceof Process)) {
                    el.eNotify(ev);
                }
            } else {
                System.out.println(
                        "Xpdl2ProcessDiagramAdapter##refreshElement##Unknown element: " //$NON-NLS-1$
                                + id);
            }

        }
    }

    @Override
    public CreateAccessibleObjectCommand getCreateEventCommand(
            EditingDomain editingDomain, EventFlowType flowType,
            EventTriggerType type, Point location, Dimension size,
            String fillColor, String lineColor) {
        return new CreateAccessibleObjectCommand(UnexecutableCommand.INSTANCE,
                null);
    }

    @Override
    public CreateAccessibleObjectCommand getCreateGatewayCommand(
            EditingDomain editingDomain, GatewayType gatewayType,
            Point location, Dimension size, String fillColor,
            String lineColor) {
        return new CreateAccessibleObjectCommand(UnexecutableCommand.INSTANCE,
                null);
    }

    @Override
    public String getDescription() {

        ProcessHeader header = getProcess().getProcessHeader();
        return header != null && header.getDescription() != null
                ? header.getDescription().getValue() == null ? "" //$NON-NLS-1$
                        : header.getDescription().getValue()
                : ""; //$NON-NLS-1$
    }

    @Override
    public Command getSetDescriptionCommand(EditingDomain ed, String text) {
        if (getProcess().getProcessHeader() != null) {
            return SetCommand.create(ed,
                    getProcess().getProcessHeader(),
                    Xpdl2Package.eINSTANCE.getDescribedElement_Description(),
                    text);
        } else {
            ProcessHeader ph = Xpdl2Factory.eINSTANCE.createProcessHeader();
            Description descVar = Xpdl2Factory.eINSTANCE.createDescription();
            descVar.setValue(text);

            ph.setDescription(descVar);
            return SetCommand.create(ed,
                    getProcess(),
                    Xpdl2Package.eINSTANCE.getProcess_ProcessHeader(),
                    ph);
        }
    }

    @Override
    public String getName() {
        String name = null;

        if (getProcess() != null) {
            name = Xpdl2ModelUtil.getDisplayNameOrName(getProcess());
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }

        return name;
    }

    @Override
    public String getTokenName() {
        String name = null;

        if (getProcess() != null) {
            name = getProcess().getName();
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }

        return name;
    }

    @Override
    public Command getSetNameCommand(EditingDomain ed, String text) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2ProcessDiagramAdapter_SetProcessName_menu);
        cmd.append(SetCommand.create(ed,
                getProcess(),
                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                text));
        return cmd;
    }

    @Override
    public String getId() {
        return getProcess().getId();
    }

    public Command getSetIdCommand(EditingDomain editingDomain, String newId) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public void setTarget(Notifier newTarget) {
        Process proc = getProcess();
        if (proc != null && packageNotifier != null) {
            proc.eAdapters().remove(packageNotifier);
        }

        super.setTarget(newTarget);
        proc = getProcess();
        if (proc != null) {
            if (packageNotifier == null) {
                packageNotifier = new OclBasedNotifier("self.\"package\"", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getProcess(), this);
            }

            proc.eAdapters().add(packageNotifier);
        }

        return;
    }

    @Override
    public void addListener(ProcessWidgetListener listener) {
        boolean first = getListeners().size() < 1;
        super.addListener(listener);

        if (first) {
            // When the process widget starts listening to us is the ideal time
            // to initialise some stuff.

            //
            // Create the initial cache of problem markers.
            cacheProblemMarkerList();

            //
            // Add a listener for "end of validation run's"
            ValidationActivator.getDefault().addValidationListener(this);
        }
    }

    @Override
    public void removeListener(ProcessWidgetListener listener) {
        super.removeListener(listener);

        if (getListeners().size() == 0) {
            // When the process widget stops listening to us it is shutting down
            // so we can drop our problem marker listener and cache.

            // Never seem to get in here but we'll clean up cache just in case.
            clearProblemMarkerList();

            //
            // REmove listener for "end of validation run's"
            ValidationActivator.getDefault().removeValidationListener(this);
        }

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#getCopyObjects
     * (org.eclipse.emf.edit.domain.EditingDomain, java.util.List)
     */
    @Override
    public Collection getCopyObjects(EditingDomain ed, Collection selection,
            boolean wantReferencedProjectEntries) {
        // Validate the items in the selection list according to rules above
        // and get a list of associated objects to copy (i.e. objects that
        // have to be copied that are NOT under the xml model hierarchy of a
        // selection.
        CopyPasteScope copyScope = new CopyPasteScope();
        List elementsToCopy = getModelElementsToCopy(selection, copyScope);

        Collection copied = prepareCopyOfObjects(ed,
                copyScope,
                elementsToCopy,
                wantReferencedProjectEntries);

        return copied;
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#validateCopyObjects(java.util.Collection)
     * 
     * @param selection
     * @return
     */
    @Override
    public boolean validateCopyObjects(Collection selection) {
        CopyPasteScope copyScope = new CopyPasteScope();

        getCopyScope(selection,
                copyScope,
                new ArrayList<Object>(),
                new ArrayList<Object>());

        if (CopyPasteScope.COPY_NONE != copyScope.getCopyScope()) {
            /*
             * XPD-4703 : Don't allow copy of anything with activities that
             * implement process interface events. This was already disallowed
             * once anything call getCopyObjects() but for some commands this
             * was too late so validateCopyObjects() also should check.
             */
            Set<Activity> activities = getAllActivitiesInSelection(selection);

            for (Activity activity : activities) {
                if (ProcessInterfaceUtil.isEventImplemented(activity)) {
                    return false;
                }
            }

            return true;
        }
        return false;

    }

    /**
     * @param selection
     * @return Set of activities in or referenced by selection.
     */
    private Set<Activity> getAllActivitiesInSelection(Collection selection) {
        Set<Activity> activities = new HashSet<Activity>();

        for (Object object : selection) {
            if (object instanceof Activity) {
                activities.add((Activity) object);
            } else if (object instanceof Process) {
                activities.addAll(((Process) object).getActivities());

            } else if (object instanceof Pool) {
                activities.addAll(
                        Xpdl2ModelUtil.getAllActivitiesInPool((Pool) object));

            } else if (object instanceof Lane) {
                activities.addAll(
                        Xpdl2ModelUtil.getActivitiesInLane((Lane) object));

            } else if (object instanceof ActivitySet) {
                activities.addAll(((ActivitySet) object).getActivities());
            }
        }

        /* Handle any activities embedded within the selected activities. */
        Set<Activity> finalActivities = new HashSet<Activity>();

        for (Activity activity : activities) {
            finalActivities.add(activity);
            if (activity.getBlockActivity() != null) {
                finalActivities.addAll(Xpdl2ModelUtil
                        .getAllActivitiesInEmbeddedSubProc(activity));
            }
        }

        return finalActivities;
    }

    /**
     * Create a copy of the given objects and then do any repair work needed.
     * 
     * This will also pick up referenced data fields, formal parameters and
     * participants and add them to the list of objects to copy. Return list
     * contains projects for required references, if
     * wantReferencedProjectEntries is set.
     * 
     * @param copyScope
     * @param elementsToCopy
     * @param wantReferencedProjectEntries
     * @return
     */
    private Collection prepareCopyOfObjects(EditingDomain ed,
            CopyPasteScope copyScope, List elementsToCopy,
            boolean wantReferencedProjectEntries) {
        // Create and run a 'take a copy of the model element objects we have in
        // list'
        Command cmd = CopyCommand.create(ed, elementsToCopy);
        if (!cmd.canExecute()) {
            return null;
        }
        cmd.execute();
        Collection copied = cmd.getResult();

        //
        // Ok, we've now got a valid list of things to copy.
        // Just need to handle any special cases for copy scopes.
        if (copyScope.getCopyScope() == CopyPasteScope.COPY_POOLS) {
            // Whole Pools copy...
            // - getModelElementsToCopy has sorted most things
            // out, leaving us to just to just drop thru and
            // create the copy to clipboard command.

        } else if (copyScope.getCopyScope() == CopyPasteScope.COPY_LANES) {
            // Whole lanes copy...
            // - getModelElementsToCopy has sorted most things
            // out, leaving us to just to just drop thru and
            // create the copy to clipboard command.

        } else if (copyScope
                .getCopyScope() == CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS) {
            // Individual activities artifacts and connecting transitions
            // When copying these we have to change them from lane-offset
            // co-ordinates to absolute ones.
            // Get a map of laneid to y offset
            Map laneOffsets = getLaneOffsets();

            // Adjust the location of activities to be lane independent.
            for (Iterator iter = copied.iterator(); iter.hasNext();) {
                Object obj = iter.next();
                if (obj instanceof Activity || obj instanceof Artifact) {
                    GraphicalNode act = (GraphicalNode) obj;

                    NodeGraphicsInfo gn =
                            Xpdl2ModelUtil.getNodeGraphicsInfo(act);

                    if (gn != null) {
                        String laneID = gn.getLaneId();
                        if (laneID != null) {
                            // Get the y offset for the parent lane and add it
                            // to the activity's y coord.
                            // i.e. make it a global co-ord because we won't be
                            // copying lanes.
                            Double offset = (Double) laneOffsets.get(laneID);
                            if (offset != null) {
                                Coordinates coords = gn.getCoordinates();
                                coords.setYCoordinate(coords.getYCoordinate()
                                        + offset.doubleValue());
                            }
                        } else {
                            // Activity not parented by lane - i.e. it's in an
                            // embedded sub-flow.
                            // So nothing to do - it's offsets are already
                            // relative to the parent activity set.
                        }
                    }
                }
            }
        } else {
            // Unknown/handled copy scope.
            return null;
        }

        // Have to copy to a new list as result of CopyCommand is immutable.
        List newList = new ArrayList(copied.size());
        newList.addAll(copied);
        copied = newList;

        Xpdl2ProcessDiagramUtils.addObjectsReferencedFromCopyObjects(
                getProcess(),
                elementsToCopy,
                copied,
                wantReferencedProjectEntries);

        //
        // Pick up references to processes from independent sub-process call
        // tasks.
        addProcessPkgReferencesToCopy(copied, wantReferencedProjectEntries);

        return copied;
    }

    /**
     * When copying independent sub-process tasks we have to leave hints for
     * when the tasks are pasted.
     * <p>
     * Basically, the user might paste the tasks into a completely different
     * package. If the task references a process in this package then the pasted
     * task will need a package reference to the copy source package.
     * <p>
     * Therefore, in the copy object we will always add the package reference to
     * the copy source package in independent sub-process call tasks. When the
     * paste is performed, the paste command will check whether the paste is
     * within original package or into a different package. If pasting back into
     * original package then the package reference will be removed. If pasting
     * into a different package then the package reference is retained.
     * <p>
     * We must also maintain the ExternalPackage list in paste destination
     * package. So we will add our own package details ExternalPackage element
     * and ExternalPackage elements for any copied tasks that reference process
     * in other packages to the copy list.
     * 
     * @param copyOfElements
     * @param wantReferencedProjectEntries
     */
    private void addProcessPkgReferencesToCopy(Collection copyOfElements,
            boolean wantReferencedProjectEntries) {
        Collection<Activity> activities = Xpdl2ProcessDiagramUtils
                .getActivitiesFromCopyList(copyOfElements);

        if (activities.size() > 0) {
            Package srcPackage = getProcess().getPackage();

            EList extPkgs = srcPackage.getExternalPackages();

            // Keep a track of references to external packages that are
            // referenced by indi subproc tasks in the copy list
            Set<ExternalPackage> refdExtPkgs = new HashSet<ExternalPackage>();

            // This will be created if we are copying any indi subproc task that
            // references a process in source package.
            ExternalPackage addSrcExtPkg = null;

            for (Activity act : activities) {

                if (act.getImplementation() instanceof SubFlow) {
                    SubFlow subFlow = (SubFlow) act.getImplementation();

                    // If the pkg is null or == src package then check if
                    // ref'd process is also being copied.
                    String subProcId = subFlow.getProcessId();
                    if (subProcId != null && subProcId.length() > 0) {
                        //
                        // Note pkgRef is NOT the package id!
                        String pkgRef = subFlow.getPackageRefId();
                        if (pkgRef == null) {
                            // XPD-3033 check requirement for new project
                            // references and ask user
                            // if required add project to be referenced.
                            if (wantReferencedProjectEntries) {
                                Collection<ProjectReference> projectReferences =
                                        new ArrayList<ProjectReference>();
                                ActionUtil.addProjectReference(
                                        projectReferences,
                                        subFlow);
                                copyOfElements.addAll(projectReferences);
                            }
                            // This is a reference to a process in source
                            // package.
                            // Just make sure that process exists in src package
                            // (maybe "-unknown-" if no process selected yet)
                            if (srcPackage.getProcess(subProcId) != null) {
                                // Create an external package element for the
                                // source package if there isn't one yet.
                                if (addSrcExtPkg == null) {
                                    addSrcExtPkg = Xpdl2WorkingCopyImpl
                                            .createExternalPackage(
                                                    WorkingCopyUtil
                                                            .getWorkingCopyFor(
                                                                    srcPackage));
                                }

                                if (addSrcExtPkg != null) {
                                    // Add pkg ref to task. Paste can remove it
                                    // when pasting back to same package.
                                    subFlow.setPackageRefId(
                                            addSrcExtPkg.getHref());
                                }
                            }
                        } else {
                            // For reference to process in another package
                            // then we need to create and add an External
                            // Package reference.
                            for (Iterator iter = extPkgs.iterator(); iter
                                    .hasNext();) {
                                ExternalPackage extPkg =
                                        (ExternalPackage) iter.next();

                                if (pkgRef.equals(extPkg.getHref())) {
                                    refdExtPkgs.add(extPkg);
                                }
                            }
                        }
                    }
                }
            } // Next activitiy in copy list.

            // Add any external package references to the copy list
            if (refdExtPkgs.size() > 0) {
                copyOfElements.addAll(refdExtPkgs);
            }

            // If there were references to processes in src package then add an
            // external package ref for the src package
            if (addSrcExtPkg != null) {
                copyOfElements.add(addSrcExtPkg);
            }

        }
    }

    /**
     * Returns a map of lane id's to their position offset in process NOTE: This
     * offset goes across pools.
     * 
     * @return
     */
    private Map getLaneOffsets() {
        HashMap offsets = new HashMap();

        List pools = getPools();

        int currOffset = 0;

        // Go thru the pools in order, getting lanes from each keeping
        // track of the start y offset of each.
        if (pools != null) {
            for (Iterator iPool = pools.iterator(); iPool.hasNext();) {
                Pool pool = (Pool) iPool.next();

                EList lanes = pool.getLanes();
                if (lanes != null) {
                    for (Iterator iLane = lanes.iterator(); iLane.hasNext();) {
                        Lane lane = (Lane) iLane.next();

                        NodeGraphicsInfo gn =
                                Xpdl2ModelUtil.getNodeGraphicsInfo(lane);

                        if (gn != null) {
                            offsets.put(lane.getId(), new Double(currOffset));

                            currOffset += gn.getHeight();
                        }
                    }
                }
            }
        }

        return (offsets);
    }

    /**
     * Validate the given list of model objects to copy and add any required
     * associated model objects that are not mode-children of things already
     * being copied (for instance activities are not model children of lanes so
     * must be copied separately).
     * 
     * Note that according to getCopyCommand() the model object list has already
     * been rationalised to only contain the highest level of objects that are
     * selected (from a diagram object point of view)
     * 
     * i.e. we will not be passed model object for a selected activity if it's
     * parent lane is also selected.
     * 
     * 
     * @param selections
     *            List of model objects selected.
     * @param copyScope
     *            Return indicating highest object in hierarchy selected (if not
     *            empty list)
     * 
     * @return New list with original objects PLUS any model objects associated
     *         with but not under a selected object (in the model hierarchy)
     *         i.e. If list is of activities then return will contain activities
     *         PLUS transitions between them.
     * @return Empty list if nothing to copy or invalid selection.
     */
    private List getModelElementsToCopy(Collection selections,
            CopyPasteScope copyScope) {

        // Whilst doing so we will separate out the transitions
        // and Associations and MessageFlows
        // This is because we have to add transitions between selected
        // objects anyway, so may as well get rtid of them.
        // We'll keep them in a list so we can check at the end
        // whether there are any spurious ones and hence disable command.
        List selectedConnections = new ArrayList();
        Map copiedConnectionIds = Collections.EMPTY_MAP;
        List selectedObjects = new ArrayList();

        FlowContainer parentFlowContainer = getCopyScope(selections,
                copyScope,
                selectedConnections,
                selectedObjects);

        //
        // If we haven't decided a copy level then it's a 'no go'.
        //
        if (copyScope.getCopyScope() == CopyPasteScope.COPY_NONE) {
            return (Collections.EMPTY_LIST);
        }

        //
        // Ok we've decided the level at which we are copying.
        // Now add the appropriate model elements for the copy scope.
        //
        // That is any element that, in the diagram, is associated with the
        // selected objects but are not child elements of the selected
        // objects in the model
        //
        List elementsToCopy = Collections.EMPTY_LIST;

        if (copyScope.getCopyScope() == CopyPasteScope.COPY_POOLS
                || copyScope.getCopyScope() == CopyPasteScope.COPY_LANES) {
            // Copy whole Pools or Lane(s), need to add...
            //
            // Activities (that are in the lanes).
            // Any Activity set referenced by embedded sub-process activity.
            // Transitions between those activities
            // Artifacts (Groups / that are in the pool's lanes),
            elementsToCopy = new ArrayList();

            // The pools or lanes in selected objects will be in order of
            // selection - if we want pasted objects to come out in 'on-screen'
            // order then we must resort them.

            if (copyScope.getCopyScope() == CopyPasteScope.COPY_POOLS) {
                Set selectedPools = new HashSet();

                for (Iterator iter = selectedObjects.iterator(); iter
                        .hasNext();) {
                    Object obj = iter.next();

                    if (obj instanceof Pool) {
                        // add to our list remove from original.
                        selectedPools.add(obj);
                        iter.remove();
                    }
                }

                // Now go thru the model pools, adding each selected one
                // back in correct order.
                for (Iterator iter = getPools().iterator(); iter.hasNext();) {
                    Pool pool = (Pool) iter.next();

                    if (selectedPools.contains(pool)) {
                        // Add the pool back in again.
                        selectedObjects.add(pool);
                    }
                }

            } else {
                Set selectedLanes = new HashSet();

                for (Iterator iter = selectedObjects.iterator(); iter
                        .hasNext();) {
                    Object obj = iter.next();

                    if (obj instanceof Lane) {
                        // add to our list remove from original.
                        selectedLanes.add(obj);
                        iter.remove();
                    }
                }

                // Now go thru the model pools and lanes in each,
                // adding each selected one back in correct order.
                for (Iterator iter = getPools().iterator(); iter.hasNext();) {
                    Pool pool = (Pool) iter.next();

                    for (Iterator laneIter =
                            pool.getLanes().iterator(); laneIter.hasNext();) {
                        Lane lane = (Lane) laneIter.next();

                        if (selectedLanes.contains(lane)) {
                            // Add the pool back in again.
                            selectedObjects.add(lane);
                        }
                    }
                }
            }

            // Copy everything we've got in first...
            // Along with anything contained in diagram but not
            // contained by selected object in model.
            addSelAndRefdElementsToCopy(selectedObjects, elementsToCopy);

            // Now add any sequence flows, message flows and
            // that connect any selected objects.
            List connectionsInScope = new ArrayList();
            connectionsInScope.addAll(getProcess().getTransitions());

            // Message flows can only be copied when whole pools
            // are copied (in all other circumstances everything will
            // be pasted into a single pool/lane and the message flow will
            // not be valid (can't be between objects in same pool).
            if (copyScope.getCopyScope() == CopyPasteScope.COPY_POOLS) {
                connectionsInScope
                        .addAll(getProcess().getPackage().getMessageFlows());
            }

            copiedConnectionIds =
                    addReferencedConnectionsToCopy(selectedObjects,
                            connectionsInScope,
                            elementsToCopy);

            // Then add associations that connect objects and flows
            // that are being copied.
            copiedConnectionIds
                    .putAll(addReferencedConnectionsToCopy(selectedObjects,
                            getProcess().getPackage().getAssociations(),
                            elementsToCopy));

        } else if (copyScope
                .getCopyScope() == CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS) {
            // Copy activities and artifacts, need to add...
            //
            elementsToCopy = new ArrayList();

            // Copy everything we've got in first...
            // Along with anything contained in diagram but not
            // contained by selected object in model.
            addSelAndRefdElementsToCopy(selectedObjects, elementsToCopy);

            // Add all transitions, associations and messageflows between
            // selected objects.
            List connectionsInScope = new ArrayList();

            if (parentFlowContainer != null) {
                if (parentFlowContainer instanceof ActivitySet) {
                    connectionsInScope
                            .addAll(((ActivitySet) parentFlowContainer)
                                    .getTransitions());
                } else if (parentFlowContainer instanceof Process) {
                    connectionsInScope.addAll(
                            ((Process) parentFlowContainer).getTransitions());
                }
            }

            // Message flows can only be copied when whole pools
            // are copied (in all other circumstances everything will
            // be pasted into a single pool/lane and the message flow will
            // not be valid (can't be between objects in same pool).
            /*
             * Therefore this is commented out on purpose...
             * connectionsInScope.addAll(getProcess().getPackage()
             * .getMessageFlows());
             */

            // This will add transitions connecting selected objects
            copiedConnectionIds =
                    addReferencedConnectionsToCopy(selectedObjects,
                            connectionsInScope,
                            elementsToCopy);

            // Then add associations that connect objects and flows
            // that are being copied.
            copiedConnectionIds
                    .putAll(addReferencedConnectionsToCopy(selectedObjects,
                            getProcess().getPackage().getAssociations(),
                            elementsToCopy));

        }

        //
        // Make sure that all transitions physically selected by the user are in
        // the copiedTransitions. If not then disallow the copy (just like we do
        // for copying activities in different scopes.
        if (!selectedConnections.isEmpty()) {

            for (Iterator iter = selectedConnections.iterator(); iter
                    .hasNext();) {
                Object connection = iter.next();

                String selConnectionId = ""; //$NON-NLS-1$

                if (connection instanceof Transition) {
                    selConnectionId = ((Transition) connection).getId();
                } else if (connection instanceof MessageFlow) {
                    selConnectionId = ((MessageFlow) connection).getId();
                } else if (connection instanceof Association) {
                    selConnectionId = ((Association) connection).getId();
                }

                if (!copiedConnectionIds.containsKey(selConnectionId)) {
                    // An originally selected transition does not connect 2
                    // selected objects.
                    // Forget it! can't copy transitions on their own.
                    elementsToCopy = Collections.EMPTY_LIST;
                    break;
                }
            }

        }

        //
        // After grabbing all the objects, check whether there are any
        // activities that implement events and prevent the copy from happening.
        // (For now, the issues surrounding creating a concrete implementation
        // of the event implementing an interface method is just to hard and not
        // worth the benefits).
        Collection<Activity> activities = Xpdl2ProcessDiagramUtils
                .getActivitiesFromCopyList(elementsToCopy);
        for (Activity act : activities) {
            if (ProcessInterfaceUtil.isEventImplemented(act)) {
                copyScope.setCopyScope(CopyPasteScope.COPY_NONE);
                elementsToCopy = Collections.EMPTY_LIST;
                break;
            }
        }

        // Return the list of model elements to copy (or empty list)
        return (elementsToCopy);

    }

    /**
     * @param selections
     * @param returnCopyScope
     * @param returnSelectedConnections
     * @param returnSelectedObjects
     * 
     * @return IF the copy scope is COPY_ACTIVITIES_AND_ARTIFACTS then this will
     *         be the paret activistSet or process of the activities and flows
     *         (can't handle mixed parents). Any other scope will return
     *         <code>null</code>.
     */
    protected FlowContainer getCopyScope(Collection selections,
            CopyPasteScope returnCopyScope, List returnSelectedConnections,
            List returnSelectedObjects) {
        // First off find out what the selection level is.
        boolean hasPools = false;
        boolean hasLanes = false;
        boolean hasActivities = false;
        boolean hasArtifacts = false;
        boolean hasGroup = false;
        boolean hasOther = false;
        boolean hasParticipants = false, hasDataFields = false,
                hasFormalParams = false;

        // Also grab the parent container of individual activities we are
        // copying.
        // This will either be a process or an activity set.
        // (note that we only need one because 2 different ones for individual
        // activities is illegal).
        FlowContainer parentFlowContainer = null;

        for (Iterator iter = selections.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof Transition || obj instanceof Association
                    || obj instanceof MessageFlow) {
                returnSelectedConnections.add(obj);

            } else if (obj instanceof Process) {
                // For whole process copies we have to downgrade to
                // 'all pools in process' (can't paste a process into
                // a process anyway.
                Process proc = getProcess();
                if (proc != null) {
                    Package pkg = proc.getPackage();
                    if (pkg != null) {
                        List pools = pkg.getPools();

                        String procId = proc.getId();

                        for (Iterator p = pools.iterator(); p.hasNext();) {
                            Pool pool = (Pool) p.next();

                            if (procId.equals(pool.getProcessId())) {
                                returnSelectedObjects.add(pool);
                                hasPools = true;

                            }
                        }
                    }
                }

            } else {
                // Not transition and not process.
                returnSelectedObjects.add(obj);

                if (obj instanceof Pool) {
                    hasPools = true;
                } else if (obj instanceof Lane) {
                    hasLanes = true;
                } else if (obj instanceof Activity) {
                    hasActivities = true;
                } else if (obj instanceof Artifact) {
                    if (((Artifact) obj).getArtifactType()
                            .equals(ArtifactType.GROUP_LITERAL)) {
                        hasGroup = true; // Disallow groups at the moment.
                    }
                    hasArtifacts = true;
                } else if (obj instanceof Participant) {
                    hasParticipants = true;
                } else if (obj instanceof DataField) {
                    hasDataFields = true;
                } else if (obj instanceof FormalParameter) {
                    hasFormalParams = true;
                } else {
                    hasOther = true;
                }
            }
        }

        // Validate the selection and set the level...
        if (hasPools) {
            // If have pools then disallow if separate objects from other pools
            // are selected.
            if (!hasLanes && !hasActivities && !hasOther) {
                returnCopyScope.setCopyScope(CopyPasteScope.COPY_POOLS);
            }
        } else if (hasLanes) {
            // If have lanes then disallow if separate objects from other
            // lanes/pools are selected.
            if (!hasActivities && !hasArtifacts && !hasOther) {
                returnCopyScope.setCopyScope(CopyPasteScope.COPY_LANES);
            }
        } else if (hasActivities || hasArtifacts) {
            if (!hasOther && !hasGroup) {
                // If there is just a bunch of activities selected then
                // we have to make sure that they are all parented by the same
                // Flow Container.
                // (i.e. are all in top level process or are all in same
                // activity set (embedded sub-proc).

                // TODO Not sure what we do about GROUPs at the moment
                boolean sameParent = true;

                for (Iterator iter = returnSelectedObjects.iterator(); iter
                        .hasNext();) {
                    EObject element = (EObject) iter.next();
                    if (element instanceof Activity) {
                        if (parentFlowContainer == null) {
                            parentFlowContainer =
                                    ((Activity) element).getFlowContainer();
                        } else {
                            if (((Activity) element)
                                    .getFlowContainer() != parentFlowContainer) {
                                sameParent = false;
                                break;
                            }
                        }
                    } else if (element instanceof Artifact) {
                        // Get the parent flowcontainer for this artifact.
                        FlowContainer parent = null;

                        NodeGraphicsInfo gn = Xpdl2ModelUtil
                                .getNodeGraphicsInfo((Artifact) element);
                        if (gn != null) {
                            // Parent is defined in lane id but can be a lane OR
                            // activity set id.
                            String parentId = gn.getLaneId();

                            // Check if it's a lane.
                            // In which case it's flow container parent is the
                            // process.
                            List pools = getProcess().getPackage().getPools();

                            if (pools != null) {
                                for (Iterator p = pools.iterator(); p
                                        .hasNext();) {
                                    Pool pool = (Pool) p.next();

                                    if (pool.getLane(parentId) != null) {
                                        // artifact is in a lane!
                                        // Therefore it's flow container is
                                        // Process.
                                        parent = getProcess();
                                        break;
                                    }
                                }
                            }

                            // If it's not in a lane then it should be in
                            // activity set.
                            if (parent == null) {
                                ActivitySet actSet =
                                        getProcess().getActivitySet(parentId);

                                if (actSet != null) {
                                    // It's in an activity set, it's parent flow
                                    // container is that.
                                    parent = actSet;
                                }
                            }
                        }

                        if (parentFlowContainer == null) {
                            parentFlowContainer = parent;
                        } else {
                            if (parent != parentFlowContainer) {
                                sameParent = false;
                                break;
                            }
                        }
                    }

                }
                if (sameParent) {
                    returnCopyScope.setCopyScope(
                            CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS);
                } else {
                    returnCopyScope.setCopyScope(CopyPasteScope.COPY_NONE);
                }
            } else {
                returnCopyScope.setCopyScope(CopyPasteScope.COPY_NONE);
            }

        } else {
            returnCopyScope.setCopyScope(CopyPasteScope.COPY_NONE);
        }
        return parentFlowContainer;
    }

    /**
     * Add the transitions that are connecting the selected activities to the
     * elementsToCOpy
     * 
     * @param selectedObjects
     *            Selected objects
     * @param connectionsInScope
     *            Transitions from process or individual activity set where all
     *            objects reside.
     * @param elementsToCopy
     *            List to add associated transitions to
     * 
     * @return Map of all transitions added key'd by their Id.
     */
    private Map addReferencedConnectionsToCopy(List selectedObjects,
            List connectionsInScope, List elementsToCopy) {

        // Add all the activities to a HashMap for quick lookup.
        Map objIds = new HashMap(elementsToCopy.size());
        for (Iterator iter = elementsToCopy.iterator(); iter.hasNext();) {
            EObject obj = (EObject) iter.next();

            if (obj instanceof NamedElement) {
                objIds.put(((NamedElement) obj).getId(), obj);

                if (obj instanceof Activity) {
                    checkAndAddSubObjectIds((Activity) obj, objIds);
                }

            }
        }

        // Go thru transitions and pick up any that are to AND from selected
        // objects
        Map transIds = new HashMap();

        for (Iterator iter = connectionsInScope.iterator(); iter.hasNext();) {
            Object connection = iter.next();

            if (connection instanceof Transition) {
                Transition trans = (Transition) connection;

                if (objIds.containsKey(trans.getTo())
                        && objIds.containsKey(trans.getFrom())) {
                    elementsToCopy.add(trans);

                    transIds.put(trans.getId(), trans);
                }
            } else if (connection instanceof MessageFlow) {
                MessageFlow flow = (MessageFlow) connection;

                if (objIds.containsKey(flow.getSource())
                        && objIds.containsKey(flow.getTarget())) {
                    elementsToCopy.add(flow);

                    transIds.put(flow.getId(), flow);
                }
            } else if (connection instanceof Association) {
                Association ass = (Association) connection;

                if (objIds.containsKey(ass.getSource())
                        && objIds.containsKey(ass.getTarget())) {
                    elementsToCopy.add(ass);

                    transIds.put(ass.getId(), ass);
                }
            }

        }

        return (transIds);
    }

    /**
     * When creating copy to clipbvoard command we need to have a list of all
     * object and artifact Id's so we can pickup/validate connections between
     * the objects that will actually be copied.
     * 
     * This method adds the ids for any activities and artifacts contained by an
     * activity set (embedded sub-proc) - RECURSIVELY!
     * 
     * @param obj
     * @param objIds
     */
    private void checkAndAddSubObjectIds(Activity act, Map objIds) {
        BlockActivity ba = act.getBlockActivity();

        if (ba != null) {
            String actSetId = ba.getActivitySetId();

            ActivitySet actSet = getProcess().getActivitySet(actSetId);

            if (actSet != null) {
                // Add id's for activities.
                for (Iterator acts = actSet.getActivities().iterator(); acts
                        .hasNext();) {
                    Activity subAct = (Activity) acts.next();

                    objIds.put(subAct.getId(), subAct);

                    checkAndAddSubObjectIds(subAct, objIds);

                }

                // Add Id's for transitions in activity set
                // (need these because there may be associations
                // connected to them.
                for (Iterator trans = actSet.getTransitions().iterator(); trans
                        .hasNext();) {
                    Transition transition = (Transition) trans.next();

                    objIds.put(transition.getId(), transition);

                }

                // Add ids for artifacts referenced
                List artifacts = getProcess().getPackage().getArtifacts();
                for (Iterator art = artifacts.iterator(); art.hasNext();) {
                    Artifact artifact = (Artifact) art.next();

                    NodeGraphicsInfo gn =
                            Xpdl2ModelUtil.getNodeGraphicsInfo(artifact);

                    if (actSetId.equals(gn.getLaneId())) {
                        objIds.put(artifact.getId(), artifact);
                    }
                }
            }
        }
    }

    /**
     * Given a list of top-level selected objects, add any model objects that
     * are contained within the diagram BUT NOT contained within the model (i.e.
     * Activity is not a child of Lane in model, ActivitySet is not a child of
     * Block Activity and so on).
     * 
     * This method will add... - The top level object itself (i.e. all objects
     * in selectedObjects) - Lanes for selected pool. - Activities and artifacts
     * for selected Lanes - ActivitySets for selected embedded sub-procs. -
     * Activities and artifacts for selected activity sets.
     * 
     * @param selectedObjects
     * @param elementsToCopy
     */
    private void addSelAndRefdElementsToCopy(List selectedObjects,
            List elementsToCopy) {

        for (Iterator iter = selectedObjects.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            // Add the top level object itself.
            elementsToCopy.add(obj);

            if (obj instanceof Activity) {
                // We have to check whether activity is embedded sub-proc
                // (BlockActivity)
                Activity act = (Activity) obj;

                // Add the referenced activity set to the copy if it is a block
                // activity (embedded Sub-process).
                addRefdActivitySetToCopy(act, elementsToCopy);

            } else if (obj instanceof Lane) {
                // We have a lane, get all the process level activities that
                // below to it...
                Lane lane = (Lane) obj;

                addLaneRefdElementsToCopy(lane, elementsToCopy);

            } else if (obj instanceof Pool) {
                // We have a pool, it's lanes are model children
                // but we need to add the activities / artifacts for each lane.
                Pool pool = (Pool) obj;

                List lanes = pool.getLanes();

                for (Iterator l = lanes.iterator(); l.hasNext();) {
                    Lane lane = (Lane) l.next();

                    addLaneRefdElementsToCopy(lane, elementsToCopy);

                }
            }

        }

    }

    /**
     * Given a lane model object copy all the objects contained within it (from
     * a diagram perspective - i.e. activities and artifacts).
     * 
     * @param lane
     * @param elementsToCopy
     */
    private void addLaneRefdElementsToCopy(Lane lane, List elementsToCopy) {
        String laneId = lane.getId();

        List activities = getProcess().getActivities();

        for (Iterator a = activities.iterator(); a.hasNext();) {
            Activity activity = (Activity) a.next();

            NodeGraphicsInfo gn = Xpdl2ModelUtil.getNodeGraphicsInfo(activity);
            if (gn != null) {
                if (laneId.equals(gn.getLaneId())) {
                    elementsToCopy.add(activity);

                    // Check and add activity set if it's an embedded
                    // sub-proc.
                    addRefdActivitySetToCopy(activity, elementsToCopy);
                }
            }
        }

        // And then add artifacts in lane...
        List artifacts = getProcess().getPackage().getArtifacts();
        for (Iterator a = artifacts.iterator(); a.hasNext();) {
            Artifact artifact = (Artifact) a.next();

            NodeGraphicsInfo gn = Xpdl2ModelUtil.getNodeGraphicsInfo(artifact);
            if (gn != null) {
                if (laneId.equals(gn.getLaneId())) {
                    elementsToCopy.add(artifact);
                }
            }
        }
    }

    /**
     * If the given activity is a block activity (embedded sub-process) then add
     * the activity set referenced from it.
     * 
     * Then recurs thru the activity set and do the same for sub-activities
     * 
     * @param act
     * @param elementsToCopy
     */
    private void addRefdActivitySetToCopy(Activity act, List elementsToCopy) {
        BlockActivity block = act.getBlockActivity();
        if (block != null) {
            String actSetID = block.getActivitySetId();

            if (actSetID != null && actSetID.length() > 0) {
                Process proc = act.getProcess();

                if (proc != null) {
                    ActivitySet actSet = proc.getActivitySet(actSetID);

                    if (actSet != null) {
                        // Ok found it, add it to the list.
                        elementsToCopy.add(actSet);

                        // Go thru the activities in the activity set
                        // looking for and recursing thru sub-activity sets.
                        List activities = actSet.getActivities();

                        for (Iterator iter = activities.iterator(); iter
                                .hasNext();) {
                            Activity subAct = (Activity) iter.next();

                            addRefdActivitySetToCopy(subAct, elementsToCopy);
                        }

                        // We also have to add artifacts that are in the set
                        // To do so we have to look for package artifacts with
                        // with this activity set id.
                        List artifacts =
                                getProcess().getPackage().getArtifacts();
                        for (Iterator art = artifacts.iterator(); art
                                .hasNext();) {
                            Artifact artifact = (Artifact) art.next();

                            NodeGraphicsInfo gn = Xpdl2ModelUtil
                                    .getNodeGraphicsInfo(artifact);

                            if (actSetID.equals(gn.getLaneId())) {
                                elementsToCopy.add(artifact);
                            }

                        }
                    }
                }
            }
        }
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#
     * getDeleteCutObjects(org.eclipse.emf.edit.domain.EditingDomain,
     * java.util.Collection)
     */
    @Override
    public Command getDeleteCutObjects(EditingDomain ed, Collection selection) {
        // Block cut of whole process,
        for (Iterator iter = selection.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof Process) {
                return UnexecutableCommand.INSTANCE;
            }
        }

        // Validate the items in the selection list according to rules above
        // and get a list of associated objects to copy (i.e. objects that
        // have to be copied that are NOT under the xml model hierarchy of a
        // selection.
        CopyPasteScope copyScope = new CopyPasteScope();

        List toCopy = getModelElementsToCopy(selection, copyScope);

        if (toCopy == null || toCopy.isEmpty()) {
            return (UnexecutableCommand.INSTANCE);
        }

        CompoundCommand result = new CompoundCommand();
        result.setLabel(
                Messages.Xpdl2ProcessDiagramAdapter_CutToClipboard_menu);

        // Now we have to add the delete commands for everything. Best way
        // to do this is to get an adapter for each of the selected objects
        // and use the adapter to create a delete command.
        //
        // This should ensure that any ancilliary things that have to be
        // done (like deleting sequence flows, connections etc function
        // correctly).
        //
        // Should be ok to do this with the original selection because
        // that is already rationalised to contain only top level objects
        // that are selected. EXCEPT for sequence flow, message flow and
        // associations. We will not have got this far unless any of these
        // are guaranteed to be between 2 objects that we are delting
        // anyway.
        // The delete command for these objects will deal with deleting
        // these connections, so we don't want to do the delate here as
        // well!!!

        // So remove all flow connections.
        List toRemove = new ArrayList();
        for (Iterator iter = selection.iterator(); iter.hasNext();) {
            Object element = iter.next();

            if (!(element instanceof GraphicalConnector)) {
                // Remove evertyhign except connections (these'll get
                // removed anyway.
                toRemove.add(element);
            }
        }

        // Then create delete commands for all the other top-level objects.
        for (Iterator iter = toRemove.iterator(); iter.hasNext();) {
            EObject eo = (EObject) iter.next();
            BaseProcessAdapter adapter = (BaseProcessAdapter) factory.adapt(eo,
                    ProcessWidgetConstants.ADAPTER_TYPE);
            result.append(adapter.getDeleteCommand(ed));

        }

        return result;

    }

    /**
     * Get the command to paste the model objects in the clipboard copy buffer
     * and to add them to the local model.
     */
    @Override
    public ProcessPasteCommand getPasteObjectsCommand(EditingDomain ed,
            Object target, Point location, Collection pasteObjects) {

        return Xpdl2ProcessDiagramUtils.getAddDiagramObjectsCommand(ed,
                getProcess(),
                target,
                location,
                pasteObjects,
                null,
                false,
                true);
    }

    /**
     * Get the 'scope' of the given objects to be copied (i.e. the highest level
     * of object to be copied such as Pools or Lanes etc)
     * 
     * @param target
     * @param copyObjects
     * @return
     */
    private CopyPasteScope getPasteObjectsScope(Object target,
            final Collection copyObjects) {
        return Xpdl2ProcessDiagramUtils.getCreateDiagramObjectsScope(target,
                copyObjects);
    }

    @Override
    public List getGroups() {
        Process proc = getProcess();
        EList artifacts = proc.getPackage().getArtifacts();
        List result = new ArrayList();
        for (Iterator iter = artifacts.iterator(); iter.hasNext();) {
            Artifact art = (Artifact) iter.next();
            if (art.getArtifactType().getValue() == ArtifactType.GROUP) {
                NodeGraphicsInfo node = Xpdl2ModelUtil.getNodeGraphicsInfo(art);

                if (node != null && proc.getId().equals(node.getLaneId())) {
                    result.add(art);
                }
            }
        }
        return result;
    }

    @Override
    public Command getCreateNewGroupCommand(EditingDomain ed, Point loc,
            Dimension size, String lineColor) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2ProcessDiagramAdapter_AddGroupDiagram_menu);

        Artifact art = ElementsFactory.createArtifact(loc,
                size,
                ArtifactType.GROUP_LITERAL,
                getProcess().getId(),
                null,
                lineColor);

        Command add = AddCommand.create(ed,
                getProcess().getPackage(),
                Xpdl2Package.eINSTANCE.getPackage_Artifacts(),
                art);

        cmd.append(add);
        return cmd;
    }

    @Override
    public void oclNotifyChanged(EObject base, Object target, Notification n) {
        if (n.getFeature() == Xpdl2Package.eINSTANCE.getPackage_Pools()) {
            /* Sid XPD-8302 - pass message in so can ignore adapter removal */
            fireDiagramNotification(n);
        } else if (n.getFeature() == Xpdl2Package.eINSTANCE
                .getPackage_Artifacts()) {

            List arts = new ArrayList();
            switch (n.getEventType()) {
            case Notification.ADD:
                arts.add(n.getNewValue());
                break;
            case Notification.REMOVE:
                arts.add(n.getOldValue());
                break;
            case Notification.ADD_MANY:
                arts.addAll((Collection) n.getNewValue());
                break;
            case Notification.REMOVE_MANY:
                arts.addAll((Collection) n.getOldValue());
                break;
            }

            Set ids = new HashSet();
            for (Iterator iter = arts.iterator(); iter.hasNext();) {
                Artifact art = (Artifact) iter.next();

                NodeGraphicsInfo gInfo =
                        Xpdl2ModelUtil.getNodeGraphicsInfo(art);

                ids.add(gInfo.getLaneId());
            }
            refreshElement(ids);
        } else if (n.getFeature() == Xpdl2Package.eINSTANCE
                .getPackage_Associations()) {
            Set acts = new HashSet();
            Association mf;
            switch (n.getEventType()) {
            case Notification.ADD:
                mf = (Association) n.getNewValue();
                acts.add(mf.getSource());
                acts.add(mf.getTarget());
                break;
            case Notification.REMOVE:
                mf = (Association) n.getOldValue();
                acts.add(mf.getSource());
                acts.add(mf.getTarget());
                break;
            }
            refreshElement(acts);
        } else if (n.getFeature() == Xpdl2Package.eINSTANCE
                .getPackage_MessageFlows()) {
            Set acts = new HashSet();
            MessageFlow mf;
            switch (n.getEventType()) {
            case Notification.ADD:
                mf = (MessageFlow) n.getNewValue();
                acts.add(mf.getSource());
                acts.add(mf.getTarget());
                break;
            case Notification.REMOVE:
                mf = (MessageFlow) n.getOldValue();
                acts.add(mf.getSource());
                acts.add(mf.getTarget());
                break;
            }
            refreshElement(acts);
        }
    }

    @Override
    public CreateAccessibleObjectCommand getCreateTaskCommand(
            EditingDomain editingDomain, TaskType taskType, Point location,
            Dimension dimension, String fillColor, String lineColor) {
        return new CreateAccessibleObjectCommand(UnexecutableCommand.INSTANCE,
                null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#
     * getActivityList ()
     */
    @Override
    public List<NamedElementAdapter> getActivityList(int filterFlags) {
        // Get all the top level activities.
        List acts = getProcess().getActivities();

        List<NamedElementAdapter> retActList =
                new ArrayList<NamedElementAdapter>(acts.size());

        addActivitiesToList(filterFlags, acts, retActList);

        return retActList;
    }

    /**
     * add activities to the toList from the fromList When an embedded sub-proc
     * is found we RECURS to include it's activities.
     * 
     * @param fromList
     * @param toList
     */
    private void addActivitiesToList(int filterFlags, List fromList,
            List<NamedElementAdapter> toList) {

        for (Iterator iter = fromList.iterator(); iter.hasNext();) {
            Activity act = (Activity) iter.next();

            Implementation impl = act.getImplementation();
            BlockActivity ba = act.getBlockActivity();

            boolean addIt = false;

            if (filterFlags == 0) {
                addIt = true;
            } else if ((filterFlags & ACTIVITY_LIST_TASKS) != 0
                    && (impl instanceof Task || impl instanceof No)) {
                addIt = true;
            } else if ((filterFlags & ACTIVITY_LIST_REFERENCES) != 0
                    && impl instanceof Reference) {
                addIt = true;
            } else if ((filterFlags & ACTIVITY_LIST_SUBFLOWS) != 0
                    && (impl instanceof SubFlow || ba != null)) {
                addIt = true;
            } else if ((filterFlags & ACTIVITY_LIST_EVENTS) != 0
                    && act.getEvent() != null) {
                addIt = true;
            } else if ((filterFlags & ACTIVITY_LIST_GATEWAYS) != 0
                    && act.getRoute() != null) {
                addIt = true;
            }

            if (addIt) {
                toList.add((NamedElementAdapter) getAdapterFactory().adapt(act,
                        ProcessWidgetConstants.ADAPTER_TYPE));
            }

            // If this is a block activity the recurs
            if (ba != null) {
                // Recurs into embedded sub-procs.
                String actSetId = ba.getActivitySetId();

                if (actSetId != null) {
                    ActivitySet actSet = getProcess().getActivitySet(actSetId);

                    if (actSet != null) {
                        List acts = actSet.getActivities();

                        // RECURS
                        addActivitiesToList(filterFlags, acts, toList);
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#
     * getModelObjectById ()
     */
    @Override
    public Object getModelObjectById(String id) {
        return getProcess().getPackage().findNamedElement(id);

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#
     * getRefactorAsEmbeddedSubProcCommand
     * (org.eclipse.emf.edit.domain.EditingDomain, java.util.List)
     */
    @Override
    public CreateAccessibleObjectCommand getRefactorAsEmbeddedSubProcCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider) {
        //
        // Create our refactor helper.
        AbstractRefactorAsSubProcCommand refactor =
                new RefactorAsEmbeddedSubProcCommand(editingDomain, objects,
                        imageProvider);

        return new CreateAccessibleObjectCommand(refactor,
                refactor.getNewSubProcActivity());
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#
     * getRefactorAsEmbeddedSubProcCommand
     * (org.eclipse.emf.edit.domain.EditingDomain, java.util.List)
     */
    @Override
    public CreateAccessibleObjectCommand getRefactorAsEventSubProcCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider) {

        /* Create our refactor helper. */
        RefactorAsEventSubProcCommand refactor =
                new RefactorAsEventSubProcCommand(editingDomain, objects,
                        imageProvider);

        return new CreateAccessibleObjectCommand(refactor,
                refactor.getNewSubProcActivity());
    }

    /**
     * Checks if the given list of Objects represent an Event Handler
     * flow.Returns true if the objects list contain all Elements for a Event
     * Handler Flow, false if it misses at-least one from the Event handler
     * Flow.
     * 
     * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#isEventHandlerFlow(java.util.List)
     * 
     * @param objectList
     * @return
     */
    @Override
    public boolean isEventHandlerFlow(Set<Object> objectList) {

        Activity eventHandlerActivity = null;

        int eventHandlersCount = 0;

        List<Activity> selectedActivities = new ArrayList<Activity>();

        for (Object nodeObject : objectList) {

            if (nodeObject instanceof Activity) {

                Activity activity = (Activity) nodeObject;
                selectedActivities.add(activity);

                /*
                 * We need to refactor to an event sub-process if there's
                 * atleast one event handler in the selected set of activities.
                 */
                if (Xpdl2ModelUtil.isEventHandlerActivity(activity)) {

                    eventHandlerActivity = activity;
                    eventHandlersCount++;

                    if (eventHandlersCount > 1) {
                        return false;
                    }

                }
            }
        }

        /*
         * Return true only when the selected is the Event Handler Activity
         */
        //
        if (eventHandlersCount == 0) {

            return false;
        } else if (eventHandlersCount == 1 && selectedActivities.size() == 1) {
            /* true for Single Event Handler Selection */
            return true;
        }

        boolean allActivitiesDownEventHandler = true;

        /* get all downstream activities for this Event Handler */
        ProcessFlowAnalyser analyser = new ProcessFlowAnalyser(true,
                eventHandlerActivity.getFlowContainer());

        Set<Activity> allEventHandlerFlowActivities = analyser
                .getDownstreamActivities(eventHandlerActivity.getId(), false);
        /* Include Event handler Activity */
        allEventHandlerFlowActivities.add(eventHandlerActivity);

        /* Check all the selected activities are in the downstream flow */
        for (Activity activity : selectedActivities) {
            if (!allEventHandlerFlowActivities.contains(activity)) {

                /*
                 * Maybe the selected acivity is a boundary event attached to
                 * one of the selected tasks/.
                 */
                Activity taskAttachedTo =
                        EventObjectUtil.getTaskAttachedTo(activity);

                boolean isBoundaryEventInHdlrFlow =
                        (taskAttachedTo != null && allEventHandlerFlowActivities
                                .contains(taskAttachedTo));

                if (!isBoundaryEventInHdlrFlow) {
                    return false;
                }
            }
        }

        /* Check that all the downstream activities are selected. */
        for (Activity activity : allEventHandlerFlowActivities) {
            if (!selectedActivities.contains(activity)) {
                /*
                 * Maybe the selected acivity is a boundary event attached to
                 * one of the downstream tasks (not sure if flow analyzer
                 * includes them or not.
                 */

                Activity taskAttachedTo =
                        EventObjectUtil.getTaskAttachedTo(activity);

                boolean isBoundaryEventInHdlrFlow = (taskAttachedTo != null
                        && selectedActivities.contains(taskAttachedTo));

                if (!isBoundaryEventInHdlrFlow) {
                    return false;
                }

            }

        }

        return true;

    }

    /**
     * Returns list of {@link Activity} in the downstream of this Activity in
     * the process flow.
     * 
     * @param activity
     * @return list of {@link Activity} in the downstream of this Activity in
     *         the process flow.
     */
    private Set<Activity> getAllDownStreamActivities(Activity activity) {

        Set<Activity> downStreamActivities = new HashSet<Activity>();

        ProcessFlowAnalyser analyser =
                new ProcessFlowAnalyser(true, activity.getFlowContainer());

        analyser.getDownstreamActivities(activity.getId(), true);
        analyser.getUpstreamActivities(activity.getId());

        return downStreamActivities;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#
     * getRefactorAsIndependentSubProcCommand
     * (org.eclipse.emf.edit.domain.EditingDomain, java.util.List)
     */
    @Override
    public CreateAccessibleObjectCommand getRefactorAsIndependentSubProcCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider) {
        //
        // Create our refactor helper.
        RefactorAsIndependentSubProcCommand refactor =
                new RefactorAsIndependentSubProcCommand(editingDomain, objects,
                        imageProvider);

        return new CreateAccessibleObjectCommand(refactor,
                refactor.getCreatedProcessInvokeActivity());
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#
     * getRefactorAsIndependentPageflowSubProcCommand
     * (org.eclipse.emf.edit.domain.EditingDomain, java.util.List)
     */
    @Override
    public CreateAccessibleObjectCommand getRefactorAsIndependentPageflowSubProcCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider) {
        //
        // Create our refactor helper.
        RefactorAsIndependentPageflowSubProcCommand refactor =
                new RefactorAsIndependentPageflowSubProcCommand(editingDomain,
                        objects, imageProvider);

        return new CreateAccessibleObjectCommand(refactor,
                refactor.getCreatedProcessInvokeActivity());
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#
     * getRefactorAsPageflowProcCommand
     * (org.eclipse.emf.edit.domain.EditingDomain, java.util.List,
     * com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider)
     */
    @Override
    public CreateAccessibleObjectCommand getRefactorAsPageflowProcCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider) {
        //
        // Create our refactor helper.
        RefactorAsPageflowProcessCommand refactor =
                new RefactorAsPageflowProcessCommand(editingDomain, objects,
                        imageProvider);

        return new CreateAccessibleObjectCommand(refactor,
                refactor.getCreatedProcessInvokeActivity());
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#
     * getObjectConnections(java.util.Collection, boolean, boolean)
     */
    @Override
    public Collection getNodeConnections(Collection nodeObjects,
            boolean wantFlows, boolean wantAssociations) {
        return Xpdl2ModelUtil
                .getObjectConnections(nodeObjects, wantFlows, wantAssociations);
    }

    /**
     * @param ed
     * @param parts
     * @return
     * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#
     *      getSeparationOfDutiesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.util.List, com.tibco.xpd.processwidget.impl.ProcessWidgetImpl)
     */
    @Override
    public Command getSeparationOfDutiesCommand(EditingDomain ed,
            List<BaseFlowNodeEditPart> parts) {
        return new SeparationOfDutiesCommand(ed, parts);
    }

    /**
     * Notification that a validation event has just occurred.
     */
    @Override
    public void validationEvent(ValidationEvent event) {

        // Only deal with validation events for the resource ourt process
        // belongs to.
        Process process = getProcess();
        IResource res = event.getResource();
        if (process != null && event != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(process);
            if (wc != null) {
                List<IResource> resources = WorkingCopyUtil
                        .getWorkingCopyFor(process).getEclipseResources();
                if (!resources.isEmpty()) {
                    if (event.getResource().equals(resources.get(0))) {
                        //
                        // recache the problem markers for this processe's
                        // resource.
                        cacheProblemMarkerList();

                        //
                        // Signal the annotations layer process widget that it
                        // needs refreshing.
                        //
                        Display.getDefault().asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                fireProblemMarkerListChanged();
                            }

                        });
                    }
                }
            }
        }
        return;
    }

    /**
     * Get all the problem markers associated with this process's resource.
     * 
     * @return
     */
    public List<MarkerAndModelObject> getCachedProblemMarkers() {
        List<MarkerAndModelObject> markersForObject =
                new ArrayList<MarkerAndModelObject>();

        synchronized (problemMarkerListCache) {
            if (problemMarkerListCache == Collections.EMPTY_LIST) {
                cacheProblemMarkerList();
            }
            markersForObject.addAll(problemMarkerListCache);
        }

        return markersForObject;
    }

    /**
     * Cache/re-cache the problem markers for this process's resource.s
     */
    private void cacheProblemMarkerList() {
        //
        // Build latest and then JUST switch to new list in synch'd block (that
        // way getProblemMakerList() callers are not held up.
        //
        List<MarkerAndModelObject> newList =
                new ArrayList<MarkerAndModelObject>();

        if (getProcess() != null) {

            IFile file = WorkingCopyUtil.getFile(getProcess());
            if (file != null) {
                IMarker[] problemMarkers = null;
                try {
                    problemMarkers = file.findMarkers(IMarker.PROBLEM,
                            true,
                            IResource.DEPTH_ZERO);
                } catch (CoreException e) {
                    e.printStackTrace();
                }

                //
                // Cahce all the markers with a ref to the eobject.
                WorkingCopy wc =
                        WorkingCopyUtil.getWorkingCopyFor(getProcess());
                if (wc != null && !wc.isInvalidFile() && wc.isLoaded()) {

                    for (IMarker marker : problemMarkers) {
                        String uri = marker.getAttribute(IMarker.LOCATION, ""); //$NON-NLS-1$
                        if (uri != null && uri.length() > 0) {
                            EObject eo = wc.resolveEObject(uri);
                            if (isObjectPossiblyRelatedToProcess(eo,
                                    getProcess())) {
                                newList.add(
                                        new MarkerAndModelObject(marker, eo));
                            }
                        }
                    }
                }
            }
        }

        //
        // Only need to synch the switch over to new list
        //
        synchronized (problemMarkerListCache) {
            previousProblemMarkerListCache = problemMarkerListCache;
            problemMarkerListCache = newList;
        }

        return;
    }

    /**
     * Note that this may contain elements that are (or are descendents of)
     * elements that are not actually related. This is because we wish to keep
     * the caching of problems as efficient as possible and some xpdl elements
     * are difficult to relate to a process, currently these include...
     * <li>MessageFlows
     * <li>Associations
     * <li>DataObjects
     * <p>
     * Markers related to these elements (or descendants) are ALWAYS added to
     * the list of markers for this process as it is quicker to cache them and
     * ignore them later when asked for a problems for a specific diagram model
     * object such as activity)
     * 
     * @param eo
     * @param process
     * @return
     */
    private boolean isObjectPossiblyRelatedToProcess(EObject eo,
            Process process) {
        EObject interestingParent = eo;

        if (eo instanceof Artifact) {
            Process process2 = Xpdl2ModelUtil.getProcess(eo);
            if (process.equals(process2)) {
                return true;
            }
        }

        while (interestingParent != null) {
            if (interestingParent instanceof Process) {
                // If element is descendant of process then it must be given
                // process.
                if (interestingParent == process) {
                    return true;
                } else {
                    return false;
                }

            } else if (interestingParent instanceof Pool) {
                // If element is descendant of pool then it must be in given
                // process.
                if (process.getId()
                        .equals(((Pool) interestingParent).getProcessId())) {
                    return true;
                } else {
                    return false;
                }

            } else if (interestingParent instanceof MessageFlow
                    || interestingParent instanceof Association
                    || interestingParent instanceof DataObject) {
                //
                // There isn't a quick way of telling whether these are parented
                // by the process so always add them just in case.
                return true;
            }

            interestingParent = interestingParent.eContainer();
        }

        return false;
    }

    /**
     * Clear the problem marker list.
     */
    private void clearProblemMarkerList() {
        synchronized (problemMarkerListCache) {
            previousProblemMarkerListCache = Collections.EMPTY_LIST;
            problemMarkerListCache = Collections.EMPTY_LIST;
        }
    }

    @Override
    public void addProblemMakerListChangedListener(
            ProblemMarkerListChangedListener listener) {
        problemListChangedListeners.add(listener);
    }

    @Override
    public void removeProblemMakerListChangedListener(
            ProblemMarkerListChangedListener listener) {
        problemListChangedListeners.remove(listener);
    }

    /**
     * Fire problemmaklre list changed event to listeners (nominally the
     * ProcessEditPart for this adapater's process.
     * <p>
     * The event is only fire if the marker list has actually changed since last
     * validation event.
     */
    private void fireProblemMarkerListChanged() {
        // System.out.println("==> fireProblemMarkerListChanged"); //$NON-NLS-1$

        // Copy the lists we have at the moment (so we only need to synch around
        // this.
        List<MarkerAndModelObject> prevProblemList =
                new ArrayList<MarkerAndModelObject>();
        ArrayList<MarkerAndModelObject> problemList =
                new ArrayList<MarkerAndModelObject>();

        synchronized (problemMarkerListCache) {
            prevProblemList.addAll(previousProblemMarkerListCache);
            problemList.addAll(problemMarkerListCache);
        }

        //
        // Only fire events if the marker list for things related tyo this
        // process has changed.
        //
        boolean changed = false;
        if (problemList.size() != prevProblemList.size()) {
            changed = true;
        } else {
            int sz = problemList.size();
            for (int i = 0; i < sz; i++) {
                MarkerAndModelObject newM = problemList.get(i);
                MarkerAndModelObject prevM = prevProblemList.get(i);

                if (newM.getMarker().getId() != prevM.getMarker().getId()
                        || newM.getModelObject() != prevM.getModelObject()) {
                    changed = true;
                    break;
                }
            }
        }

        if (changed) {
            // Fire marker list changed events off to the (nominally)
            // ProcessEditPart.
            for (ProblemMarkerListChangedListener listener : problemListChangedListeners) {
                listener.problemMarkerListChanged(prevProblemList, problemList);
            }
        }

        // System.out.println("<== fireProblemMarkerListChanged"); //$NON-NLS-1$

        return;
    }

    @Override
    public Command getSetGlobalDestinationEnvironmentCommand(EditingDomain ed,
            HashMap<String, String> destEnvs) {
        Process proc = getProcess();
        Command destPasteCommand = null;
        if (ed instanceof TransactionalEditingDomain) {
            destPasteCommand = new XPDDestPasteCommand(ed, proc, destEnvs);
        }
        return destPasteCommand;
    }

    @Override
    public Collection<GlobalDestinationInfo> getGlobalDestinationsAssociated() {
        Collection<GlobalDestinationInfo> enabledGlobalDestinationsInfo =
                GlobalDestinationHelper
                        .getEnabledGlobalDestinationsInfo(getProcess());
        Set<GlobalDestinationInfo> globalDestInfoSet =
                new HashSet<GlobalDestinationInfo>();
        for (GlobalDestinationInfo gdi : enabledGlobalDestinationsInfo) {
            globalDestInfoSet.add(gdi);
        }
        return enabledGlobalDestinationsInfo;
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#getRetainFamiliarCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.util.List)
     * 
     * @param ed
     * @param editParts
     * @return
     */
    @Override
    public Command getRetainFamiliarCommand(EditingDomain ed,
            List<BaseFlowNodeEditPart> editParts) {
        return new RetainFamiliarCommand(ed, editParts);
    }

    @Override
    public IUndoContext getUndoContext() {
        if (getProcess() != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(getProcess());
            if (wc instanceof AbstractTransactionalWorkingCopy) {
                return ((AbstractTransactionalWorkingCopy) wc).getUndoContext();
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.ProcessContainerAdapter#
     * getCreateAndConnectPopupItem(org.eclipse.emf.edit.domain.EditingDomain,
     * com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair,
     * org.eclipse.draw2d.geometry.Point, java.lang.Object)
     */
    @Override
    public DropObjectPopupItem getCreateAndConnectPopupItem(
            EditingDomain editingDomain, CreateAndConnectObjectPair cacPair,
            Point adjustableRelativeLocation, Object actualDropTarget,
            Object connectionSourceObject) {
        return null;
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#getFlowRoutingStyle()
     * 
     * @return
     */
    @Override
    public XpdFlowRoutingStyle getFlowRoutingStyle() {
        FlowRoutingStyle routingStyle = DEFAULT_FLOW_ROUTING_STYLE;

        Process process = getProcess();
        if (process != null) {
            Object attr = Xpdl2ModelUtil.getOtherAttribute(process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_FlowRoutingStyle());
            if (attr instanceof FlowRoutingStyle) {
                routingStyle = (FlowRoutingStyle) attr;
            }
        }

        if (FlowRoutingStyle.UNCENTERED_ON_TASKS.equals(routingStyle)) {
            return XpdFlowRoutingStyle.UncenteredOnTasks;
        } else if (FlowRoutingStyle.SINGLE_ENTRY_EXIT.equals(routingStyle)) {
            return XpdFlowRoutingStyle.SingleEntryExit;
        } else if (FlowRoutingStyle.MULTI_ENTRY_EXIT.equals(routingStyle)) {
            return XpdFlowRoutingStyle.MultiEntryExit;
        }

        throw new RuntimeException("Unexpected FlowRoutingStyle value: " //$NON-NLS-1$
                + routingStyle);
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter#getRefactorAsIndependentServiceProcessCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.util.List,
     *      com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider)
     * 
     * @param editingDomain
     * @param objects
     * @param imageProvider
     * @return
     */
    @Override
    public CreateAccessibleObjectCommand getRefactorAsIndependentServiceProcessCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider) {

        /* Create our refactor helper. */
        RefactorAsIndependentServiceProcessSubProcCommand refactor =
                new RefactorAsIndependentServiceProcessSubProcCommand(
                        editingDomain, objects, imageProvider);

        return new CreateAccessibleObjectCommand(refactor,
                refactor.getCreatedProcessInvokeActivity());
    }

}
