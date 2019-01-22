package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.ReplaceCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.ConnectionLabelPosition;
import com.tibco.xpd.processwidget.adapters.XPDBendpointType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.GraphicalConnector;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.ocl.OclBasedNotifier;
import com.tibco.xpd.xpdl2.util.ocl.OclQueryListener;

/**
 * Base class for Connectors (Transition and Associations) and Message Flow
 * Adapter
 * 
 * @author wzurek
 * 
 */
public abstract class Xpdl2BaseConnectionAdapter extends Xpdl2BaseProcessAdapter
        implements BaseConnectionAdapter, OclQueryListener {

    // TODO - Currently ocl can only listen to one thing at a time
    // It may be more efficient if we could listen to all of these
    // Connector graphics info's in one go.
    private OclBasedNotifier bendPointChangeListener;

    private OclBasedNotifier oldBendPointChangeListener;

    private OclBasedNotifier startAnchorChangeListener;

    private OclBasedNotifier endAnchorChangeListener;

    private OclBasedNotifier labelAnchorChangeListener;

    private OclBasedNotifier labelSizeChangeListener;

    @Override
    public String getId() {
        return getNamedElement().getId();
    }

    @Override
    public String getName() {
        String name = null;

        if (getNamedElement() != null) {
            name = Xpdl2ModelUtil.getDisplayNameOrName(getNamedElement());
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }

        return name;
    }

    @Override
    public String getTokenName() {
        String name = null;

        if (getNamedElement() != null) {
            name = getNamedElement().getName();
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }

        return name;
    }

    @Override
    public Command getSetNameCommand(EditingDomain editingDomain,
            String newName) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(
                Messages.Xpdl2BaseConnectionAdapter_SetLabelOfConnection_menu);
        cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                getNamedElement(),
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                newName));
        return cmd;
    }

    @Override
    public Command getDeleteCommand(EditingDomain editingDomain) {
        GraphicalConnector conn = getGraphicalConnector();
        return new DeleteFlowCommand(editingDomain, conn, getAdapterFactory());
    }

    @Override
    public void setTarget(Notifier newTarget) {
        if (getTarget() != null) {
            if (bendPointChangeListener != null) {
                bendPointChangeListener.getTarget().eAdapters()
                        .remove(bendPointChangeListener);
            }

            if (oldBendPointChangeListener != null) {
                oldBendPointChangeListener.getTarget().eAdapters()
                        .remove(oldBendPointChangeListener);
            }

            if (startAnchorChangeListener != null) {
                startAnchorChangeListener.getTarget().eAdapters()
                        .remove(startAnchorChangeListener);
            }

            if (endAnchorChangeListener != null) {
                endAnchorChangeListener.getTarget().eAdapters()
                        .remove(endAnchorChangeListener);
            }

            if (labelAnchorChangeListener != null) {
                labelAnchorChangeListener.getTarget().eAdapters()
                        .remove(labelAnchorChangeListener);
            }

            if (labelSizeChangeListener != null) {
                labelSizeChangeListener.getTarget().eAdapters()
                        .remove(labelSizeChangeListener);
            }
        }

        super.setTarget(newTarget);
        if (getTarget() != null) {
            if (bendPointChangeListener == null) {
                bendPointChangeListener = new OclBasedNotifier(
                        "self.getConnectorGraphicsInfoForTool('" //$NON-NLS-1$
                                + Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID + "." //$NON-NLS-1$
                                + Xpdl2ModelUtil.CONNECTION_INFO_IDSUFFIX
                                + "')", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getGraphicalConnector(), this);
            }
            getTarget().eAdapters().add(bendPointChangeListener);

            if (oldBendPointChangeListener == null) {
                oldBendPointChangeListener = new OclBasedNotifier(
                        "self.getConnectorGraphicsInfoForTool('" //$NON-NLS-1$
                                + Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID + "')", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getGraphicalConnector(), this);
            }
            getTarget().eAdapters().add(oldBendPointChangeListener);

            if (startAnchorChangeListener == null) {
                startAnchorChangeListener = new OclBasedNotifier(
                        "self.getConnectorGraphicsInfoForTool('" //$NON-NLS-1$
                                + Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID + "." //$NON-NLS-1$
                                + Xpdl2ModelUtil.START_ANCHOR_IDSUFFIX + "')", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getGraphicalConnector(), this);
            }
            getTarget().eAdapters().add(startAnchorChangeListener);

            if (endAnchorChangeListener == null) {
                endAnchorChangeListener = new OclBasedNotifier(
                        "self.getConnectorGraphicsInfoForTool('" //$NON-NLS-1$
                                + Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID + "." //$NON-NLS-1$
                                + Xpdl2ModelUtil.END_ANCHOR_IDSUFFIX + "')", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getGraphicalConnector(), this);
            }
            getTarget().eAdapters().add(endAnchorChangeListener);

            if (labelAnchorChangeListener == null) {
                labelAnchorChangeListener = new OclBasedNotifier(
                        "self.getConnectorGraphicsInfoForTool('" //$NON-NLS-1$
                                + Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID + "." //$NON-NLS-1$
                                + Xpdl2ModelUtil.LABEL_ANCHOR_IDSUFFIX + "')", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getGraphicalConnector(), this);
            }
            getTarget().eAdapters().add(labelAnchorChangeListener);

            if (labelSizeChangeListener == null) {
                labelSizeChangeListener = new OclBasedNotifier(
                        "self.getConnectorGraphicsInfoForTool('" //$NON-NLS-1$
                                + Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID + "." //$NON-NLS-1$
                                + Xpdl2ModelUtil.LABEL_SIZE_IDSUFFIX + "')", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getGraphicalConnector(), this);
            }
            getTarget().eAdapters().add(labelSizeChangeListener);

        }
    }

    @Override
    public void oclNotifyChanged(EObject base, Object target,
            Notification notification) {
        /* Sid XPD-8302 - pass message in so can ignore adapter removal */
        fireDiagramNotification(notification);
    }

    /**
     * @return (NamedElementAdapter) getTarget();
     */
    protected NamedElement getNamedElement() {
        return (NamedElement) getTarget();
    }

    /**
     * @return (GraphicalConnector) getTarget();
     */
    protected GraphicalConnector getGraphicalConnector() {
        return (GraphicalConnector) getTarget();
    }

    @Override
    public Command getMoveBendpointCommand(EditingDomain ed, int index,
            XPDBendpointType loc) {
        GraphicalConnector gc = getGraphicalConnector();

        ConnectorGraphicsInfo gInfo =
                Xpdl2ModelUtil.getConnectorGraphicsInfo(gc);

        if (gInfo != null) {
            Coordinates c1 = gInfo.getCoordinates().get(index * 2);
            Coordinates c2 = gInfo.getCoordinates().get(index * 2 + 1);

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(
                    Messages.Xpdl2BaseConnectionAdapter_MoveBendConnection_menu);

            // Note:
            // Replace existing coordinates with new ones. changing only values
            // will not deliver correct notification events
            Xpdl2Factory f = Xpdl2Factory.eINSTANCE;
            Coordinates c1r = f.createCoordinates(loc.fromStart.width,
                    loc.fromStart.height);
            EReference feature = Xpdl2Package.eINSTANCE
                    .getConnectorGraphicsInfo_Coordinates();
            cmd.append(ReplaceCommand.create(ed,
                    gInfo,
                    feature,
                    c1,
                    Collections.singleton(c1r)));
            Coordinates c2r =
                    f.createCoordinates(loc.fromEnd.width, loc.fromEnd.height);
            cmd.append(ReplaceCommand.create(ed,
                    gInfo,
                    feature,
                    c2,
                    Collections.singleton(c2r)));

            return cmd;
        }
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public Command getCreateBendpointCommand(EditingDomain editingDomain,
            int index, XPDBendpointType loc) {

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(
                Messages.Xpdl2BaseConnectionAdapter_CreateBendConnection_menu);
        GraphicalConnector mf = getGraphicalConnector();

        ArrayList nc = new ArrayList(2);
        nc.add(Xpdl2Factory.eINSTANCE.createCoordinates(loc.fromStart.width,
                loc.fromStart.height));
        nc.add(Xpdl2Factory.eINSTANCE.createCoordinates(loc.fromEnd.width,
                loc.fromEnd.height));

        ConnectorGraphicsInfo gInfo = Xpdl2ModelUtil
                .getOrCreateConnectorGraphicsInfo(mf, editingDomain, cmd);
        EList coords = gInfo.getCoordinates();
        if (coords.size() % 2 != 0) {
            // invalid list of coordinates, remove existing and put new one
            cmd.append(RemoveCommand.create(editingDomain, coords));
            cmd.append(AddCommand.create(editingDomain,
                    gInfo,
                    Xpdl2Package.eINSTANCE
                            .getConnectorGraphicsInfo_Coordinates(),
                    nc));
        } else {
            // correct list, put new coordinates in place
            cmd.append(AddCommand.create(editingDomain,
                    gInfo,
                    Xpdl2Package.eINSTANCE
                            .getConnectorGraphicsInfo_Coordinates(),
                    nc,
                    index * 2));
        }
        return cmd;
    }

    @Override
    public Command getDeleteBendpointCommand(EditingDomain editingDomain,
            int index) {
        GraphicalConnector gc = getGraphicalConnector();

        ConnectorGraphicsInfo gInfo =
                Xpdl2ModelUtil.getConnectorGraphicsInfo(gc);

        if (gInfo != null) {
            List toDel = new ArrayList(2);
            toDel.add(gInfo.getCoordinates().get(index * 2));
            toDel.add(gInfo.getCoordinates().get(index * 2 + 1));
            return RemoveCommand.create(editingDomain, toDel);
        }
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public List getBendpoints() {
        List bendpoints;

        GraphicalConnector gc = getGraphicalConnector();

        ConnectorGraphicsInfo gInfo =
                Xpdl2ModelUtil.getConnectorGraphicsInfo(gc);

        if (gInfo != null) {
            EList coords = gInfo.getCoordinates();
            if (!coords.isEmpty() && coords.size() % 2 == 0) {
                // correct number of coordiantes
                bendpoints = new ArrayList();
                double size = coords.size() / 2 + 1;
                double no = 1;
                for (Iterator iter = coords.iterator(); iter.hasNext();) {
                    Coordinates c1 = (Coordinates) iter.next();
                    Coordinates c2 = (Coordinates) iter.next();
                    XPDBendpointType b = new XPDBendpointType();
                    b.fromStart = new Dimension((int) c1.getXCoordinate(),
                            (int) c1.getYCoordinate());
                    b.fromEnd = new Dimension((int) c2.getXCoordinate(),
                            (int) c2.getYCoordinate());
                    b.weight = (float) (no / size);
                    bendpoints.add(b);
                }
            } else {
                bendpoints = Collections.EMPTY_LIST;
            }
        } else {
            bendpoints = Collections.EMPTY_LIST;
        }

        return bendpoints;
    }

    @Override
    public List getSourceAssociations() {
        GraphicalConnector gc = getGraphicalConnector();
        return gc.getOutgoingAssociations();
    }

    @Override
    public List getTargetAssociations() {
        GraphicalConnector gc = getGraphicalConnector();
        return gc.getIncomingAssociations();
    }

    @Override
    public Command getCreateAssociationCommand(EditingDomain editingDomain,
            EObject targetNode, List bendpoints, Double startAnchorPos,
            Double endAnchorPos, String lineColor) {

        UniqueIdElement sourceFlow = (UniqueIdElement) getGraphicalConnector();

        if (targetNode instanceof UniqueIdElement) {
            UniqueIdElement target = (UniqueIdElement) targetNode;

            if (sourceFlow != target) {
                Package pck = null;

                if (sourceFlow instanceof Transition) {
                    pck = ((Transition) sourceFlow).getProcess().getPackage();
                } else if (sourceFlow instanceof MessageFlow) {
                    pck = ((MessageFlow) sourceFlow).getPackage();
                } else if (sourceFlow instanceof Association) {
                    pck = ((Association) sourceFlow).getPackage();
                }

                if (pck == null) {
                    return UnexecutableCommand.INSTANCE;
                }

                Association assoc =
                        ElementsFactory.createAssociation(sourceFlow,
                                target,
                                bendpoints,
                                startAnchorPos,
                                endAnchorPos,
                                lineColor);

                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(
                        Messages.Xpdl2BaseConnectionAdapter_CreateAssociationConnection_menu);
                cmd.append(AddCommand.create(editingDomain,
                        pck,
                        Xpdl2Package.eINSTANCE.getPackage_Associations(),
                        assoc));

                return cmd;
            }
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * DeleteFlowCommand When flow (sequence/message) is deleted we have to
     * delete connected associations.
     * 
     * As the association MAY be connected to something else that is being
     * deleted we have to do so only when the command is actually executed. This
     * is so that when the connected object runs IT'S delete command it will not
     * pick up an already deleted association.
     * 
     */
    private static class DeleteFlowCommand extends AbstractCommand {

        private final GraphicalConnector flowConnection;

        private CompoundCommand command;

        private final EditingDomain editingDomain;

        private final AdapterFactory adapterFactory;

        private Command removeCommand;

        public DeleteFlowCommand(EditingDomain editingDomain,
                GraphicalConnector flowConnection,
                AdapterFactory adapterFactory) {
            this.editingDomain = editingDomain;
            this.flowConnection = flowConnection;
            this.adapterFactory = adapterFactory;
        }

        @Override
        protected boolean prepare() {
            removeCommand = RemoveCommand.create(editingDomain, flowConnection);
            command = new CompoundCommand();
            return removeCommand.canExecute();
        }

        @Override
        public void execute() {
            Xpdl2BaseConnectionAdapter flowAdp =
                    (Xpdl2BaseConnectionAdapter) adapterFactory.adapt(
                            flowConnection,
                            ProcessWidgetConstants.ADAPTER_TYPE);

            addAndExecute(flowAdp.getSourceAssociations(), command);
            addAndExecute(flowAdp.getTargetAssociations(), command);

            command.appendAndExecute(
                    RemoveCommand.create(editingDomain, flowConnection));
        }

        void addAndExecute(List list, CompoundCommand cmd) {
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                EObject eo = (EObject) iter.next();
                BaseProcessAdapter ad = (BaseProcessAdapter) adapterFactory
                        .adapt(eo, ProcessWidgetConstants.ADAPTER_TYPE);
                cmd.appendAndExecute(ad.getDeleteCommand(editingDomain));
            }
        }

        @Override
        public void redo() {
            command.redo();
        }

        @Override
        public void undo() {
            command.undo();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.BaseFlowAdapter#
     * getStartAnchorPosition ()
     */
    @Override
    public Double getStartAnchorPosition() {
        GraphicalConnector gc = getGraphicalConnector();

        ConnectorGraphicsInfo gInfo =
                Xpdl2ModelUtil.getConnectorGraphicsInfo(gc,
                        Xpdl2ModelUtil.START_ANCHOR_IDSUFFIX);

        if (gInfo != null) {
            // X-Coord is percent portion of line that anchor is on.
            List coords = gInfo.getCoordinates();
            if (coords.size() > 0) {
                return new Double(
                        ((Coordinates) coords.get(0)).getXCoordinate());
            }
        }

        return (null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.BaseFlowAdapter#
     * getStartAnchorPosition ()
     */
    @Override
    public Double getEndAnchorPosition() {
        GraphicalConnector gc = getGraphicalConnector();

        ConnectorGraphicsInfo gInfo =
                Xpdl2ModelUtil.getConnectorGraphicsInfo(gc,
                        Xpdl2ModelUtil.END_ANCHOR_IDSUFFIX);

        if (gInfo != null) {
            // X-Coord is percent portion of line that anchor is on.
            List coords = gInfo.getCoordinates();
            if (coords.size() > 0) {
                return new Double(
                        ((Coordinates) coords.get(0)).getXCoordinate());
            }
        }

        return (null);
    }

    /**
     * Sort out start anchor position for connection and append necessary update
     * commands to the given command.
     * 
     * @param editingDomain
     * @param cmd
     * @param anchorPos
     */
    public void appendResetStartAnchorCommand(EditingDomain editingDomain,
            CompoundCommand cmd, Double anchorPos) {
        // Create graphical connector info and add it into the
        // transition.
        ConnectorGraphicsInfo gConnectorInfo = Xpdl2ModelUtil
                .getOrCreateConnectorGraphicsInfo(getGraphicalConnector(),
                        editingDomain,
                        cmd,
                        Xpdl2ModelUtil.START_ANCHOR_IDSUFFIX);

        // Percent portion along line anchor position is stored as X-coord.
        List coords = gConnectorInfo.getCoordinates();

        if (coords.size() > 0) {
            // Remove existing start anchor pos.
            cmd.append(RemoveCommand.create(editingDomain, coords));
        }

        // If we have a new start position then save it.
        if (anchorPos != null) {
            cmd.append(AddCommand.create(editingDomain,
                    gConnectorInfo,
                    Xpdl2Package.eINSTANCE
                            .getConnectorGraphicsInfo_Coordinates(),
                    Xpdl2Factory.eINSTANCE
                            .createCoordinates(anchorPos.doubleValue(), 0.0)));
        }

    }

    /**
     * Sort out endanchor position for connection and append necessary update
     * commands to the given command.
     * 
     * @param editingDomain
     * @param cmd
     * @param anchorPos
     */
    public void appendResetEndAnchorCommand(EditingDomain editingDomain,
            CompoundCommand cmd, Double anchorPos) {
        // Create graphical connector info and add it into the
        // transition.
        ConnectorGraphicsInfo gConnectorInfo = Xpdl2ModelUtil
                .getOrCreateConnectorGraphicsInfo(getGraphicalConnector(),
                        editingDomain,
                        cmd,
                        Xpdl2ModelUtil.END_ANCHOR_IDSUFFIX);

        // Percent portion along line anchor position is stored as X-coord.
        List coords = gConnectorInfo.getCoordinates();

        if (coords.size() > 0) {
            // Remove existing start anchor pos.
            cmd.append(RemoveCommand.create(editingDomain, coords));
        }

        // If we have a new start position then save it.
        if (anchorPos != null) {
            cmd.append(AddCommand.create(editingDomain,
                    gConnectorInfo,
                    Xpdl2Package.eINSTANCE
                            .getConnectorGraphicsInfo_Coordinates(),
                    Xpdl2Factory.eINSTANCE
                            .createCoordinates(anchorPos.doubleValue(), 0.0)));
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.BaseConnectionAdapter#
     * getSetLabelPositionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * com.tibco.xpd.processwidget.adapters.ConnectionLabelPosition)
     */
    @Override
    public Command getSetLabelPositionCommand(EditingDomain editingDomain,
            ConnectionLabelPosition connectionLabelPosition) {

        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(
                Messages.Xpdl2BaseConnectionAdapter_MoveLabelOnConnection_menu);

        // Create graphical connector info and add it into the
        // transition.
        ConnectorGraphicsInfo labelAnchorPosition = Xpdl2ModelUtil
                .getOrCreateConnectorGraphicsInfo(getGraphicalConnector(),
                        editingDomain,
                        cmd,
                        Xpdl2ModelUtil.LABEL_ANCHOR_IDSUFFIX);

        // There are 2 possible sets of co-ords.
        //
        // - The first is the percent offset along line stored as X-co-ord
        // - The second is x/y offset from that position.
        List coords = labelAnchorPosition.getCoordinates();

        if (coords.size() > 0) {
            // Remove existing anchor pos info.
            cmd.append(RemoveCommand.create(editingDomain, coords));
        }

        // If we have a new label position then save it.
        if (connectionLabelPosition != null) {
            // - The first is the percent offset along line stored as X-co-ord
            cmd.append(
                    AddCommand.create(editingDomain,
                            labelAnchorPosition,
                            Xpdl2Package.eINSTANCE
                                    .getConnectorGraphicsInfo_Coordinates(),
                            Xpdl2Factory.eINSTANCE.createCoordinates(
                                    connectionLabelPosition
                                            .getPercentAnchorOnConnection(),
                                    0.0)));

            // - The second is x/y offset from that position.
            cmd.append(AddCommand.create(editingDomain,
                    labelAnchorPosition,
                    Xpdl2Package.eINSTANCE
                            .getConnectorGraphicsInfo_Coordinates(),
                    Xpdl2Factory.eINSTANCE.createCoordinates(
                            connectionLabelPosition.getXOffsetFromAnchor(),
                            connectionLabelPosition.getYOffsetFromAnchor())));
        }

        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.BaseConnectionAdapter#
     * getConnectionLabelPosition()
     */
    @Override
    public ConnectionLabelPosition getLabelPosition() {
        ConnectionLabelPosition labelPosition = null;

        GraphicalConnector gc = getGraphicalConnector();

        ConnectorGraphicsInfo gInfo = null;
        if (gc != null) {
            gInfo = Xpdl2ModelUtil.getConnectorGraphicsInfo(gc,
                    Xpdl2ModelUtil.LABEL_ANCHOR_IDSUFFIX);

            if (gInfo != null) {
                // X-Coord is percent portion of line that anchor is on.
                List coords = gInfo.getCoordinates();
                if (coords.size() > 0) {
                    labelPosition = new ConnectionLabelPosition();

                    // - The first is the percent offset along line stored as
                    // X-co-ord
                    labelPosition.setPercentAnchorOnConnection(
                            ((Coordinates) coords.get(0)).getXCoordinate());

                    if (coords.size() > 1) {
                        // - The second is x/y offset from that position.
                        labelPosition.setXOffsetFromAnchor(
                                (int) ((Coordinates) coords.get(1))
                                        .getXCoordinate());
                        labelPosition.setYOffsetFromAnchor(
                                (int) ((Coordinates) coords.get(1))
                                        .getYCoordinate());
                    }

                }
            }
        }

        return (labelPosition);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.BaseConnectionAdapter#
     * getSetLabelSizeCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.draw2d.geometry.Dimension)
     */
    @Override
    public Command getSetLabelSizeCommand(EditingDomain editingDomain,
            Dimension size) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(
                Messages.Xpdl2BaseConnectionAdapter_SizeLabelOnConnection_menu);

        // Create graphical connector info and add it into the
        // transition.
        ConnectorGraphicsInfo cg = Xpdl2ModelUtil
                .getOrCreateConnectorGraphicsInfo(getGraphicalConnector(),
                        editingDomain,
                        cmd,
                        Xpdl2ModelUtil.LABEL_SIZE_IDSUFFIX);

        // Size in the first coordinate
        //
        List coords = cg.getCoordinates();

        if (coords.size() > 0) {
            // Remove existing anchor pos info.
            cmd.append(RemoveCommand.create(editingDomain, coords));
        }

        // If we have a new label size then save it.
        if (size != null) {
            // Add a coordinate whose x and y are the width and height
            cmd.append(AddCommand.create(editingDomain,
                    cg,
                    Xpdl2Package.eINSTANCE
                            .getConnectorGraphicsInfo_Coordinates(),
                    Xpdl2Factory.eINSTANCE.createCoordinates(size.width,
                            size.height)));

        }

        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter#getLabelSize()
     */
    @Override
    public Dimension getLabelSize() {
        Dimension labelSize = null;

        GraphicalConnector gc = getGraphicalConnector();

        ConnectorGraphicsInfo gInfo =
                Xpdl2ModelUtil.getConnectorGraphicsInfo(gc,
                        Xpdl2ModelUtil.LABEL_SIZE_IDSUFFIX);

        if (gInfo != null) {
            List coords = gInfo.getCoordinates();

            if (coords.size() > 0) {
                Coordinates coord = (Coordinates) coords.get(0);

                labelSize = new Dimension((int) coord.getXCoordinate(),
                        (int) coord.getYCoordinate());
            }
        }

        return labelSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter#getLineColor()
     */
    @Override
    public String getLineColor() {
        ConnectorGraphicsInfo gi = Xpdl2ModelUtil
                .getConnectorGraphicsInfo(getGraphicalConnector());
        if (gi != null) {
            return gi.getBorderColor();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.BaseConnectionAdapter#
     * getSetLineColorCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * java.lang.String)
     */
    @Override
    public Command getSetLineColorCommand(EditingDomain editingDomain,
            String colorString) {
        CompoundCommand cmd = new CompoundCommand();
        GraphicalConnector conn = getGraphicalConnector();

        ConnectorGraphicsInfo gInfo = Xpdl2ModelUtil
                .getOrCreateConnectorGraphicsInfo(conn, editingDomain, cmd);

        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getConnectorGraphicsInfo_BorderColor(),
                colorString));

        cmd.setLabel(
                Messages.Xpdl2BaseConnectionAdapter_LineColorConnection_menu);
        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter#getLineColor()
     */
    @Override
    public String getFillColor() {
        ConnectorGraphicsInfo gi = Xpdl2ModelUtil
                .getConnectorGraphicsInfo(getGraphicalConnector());
        if (gi != null) {
            return gi.getFillColor();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.BaseConnectionAdapter#
     * getSetLineColorCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * java.lang.String)
     */
    @Override
    public Command getSetFillColorCommand(EditingDomain editingDomain,
            String colorString) {
        CompoundCommand cmd = new CompoundCommand();
        GraphicalConnector conn = getGraphicalConnector();

        ConnectorGraphicsInfo gInfo = Xpdl2ModelUtil
                .getOrCreateConnectorGraphicsInfo(conn, editingDomain, cmd);

        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getConnectorGraphicsInfo_BorderColor(),
                colorString));

        cmd.setLabel(
                Messages.Xpdl2BaseConnectionAdapter_FillColorConnection_menu);
        return cmd;
    }

}
