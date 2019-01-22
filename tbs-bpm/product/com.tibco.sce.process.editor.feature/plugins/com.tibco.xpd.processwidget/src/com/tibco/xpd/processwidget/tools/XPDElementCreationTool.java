package com.tibco.xpd.processwidget.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.Tool;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.policies.SequenceFlowLayoutEditPolicy;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Tool entry that have help context
 */
class XPDElementCreationTool extends CombinedTemplateCreationEntry {
    private ToolContextHelpUpdater toolContextHelpUpdater;

    private String contextHelpId;

    private Map requestExtendedData;

    private boolean noSizeOnCreate = false;

    private ProcessWidget processWidgetImpl;

    private CreationFactory creationFactory;

    public XPDElementCreationTool(String label, String shortDesc,
            CreationFactory factory, ImageDescriptor iconSmall,
            ImageDescriptor iconLarge, String contextHelpId,
            ToolContextHelpUpdater toolContextHelpUpdater,
            boolean noSizeOnCreate, ProcessWidget processWidgetImpl) {
        super(label, shortDesc, factory, iconSmall, iconLarge);

        creationFactory = factory;

        this.processWidgetImpl = processWidgetImpl;
        // Now done in CreationToolEntry
        // setToolProperty(CreationTool.PROPERTY_CREATION_FACTORY, factory);
        this.toolContextHelpUpdater = toolContextHelpUpdater;
        this.contextHelpId = contextHelpId;
        this.noSizeOnCreate = noSizeOnCreate;
    }

    /**
     * @return the creationFactory
     */
    public CreationFactory getCreationFactory() {
        return creationFactory;
    }

    public Dimension getDefaultObjectSize() {
        return (Dimension) (requestExtendedData
                .get(PaletteFactory.EXTDATA_INITIAL_SIZE));
    }

    /**
     * @return the noSizeOnCreate
     */
    public boolean isNoSizeOnCreate() {
        return noSizeOnCreate;
    }

    public void setRequestExtendedData(Object key, Object value) {
        if (requestExtendedData == null) {
            requestExtendedData = new HashMap();
        }
        requestExtendedData.put(key, value);
    }

    /**
     * @return the requestExtendedData
     */
    public Map getRequestExtendedData() {
        return requestExtendedData;
    }

    @Override
    public Tool createTool() {

        Tool tool = new CreationTool() {
            @Override
            protected void executeCommand(Command command) {
                super.executeCommand(command);

                // After executing a command select the created edit part.
                Collection<Object> modelObjects =
                        getModelObjectFromCommand(command);

                if (modelObjects != null && modelObjects.size() > 0) {
                    List<EditPart> editParts =
                            EditPartUtil
                                    .getEditPartsForModelObjects(processWidgetImpl
                                            .getGraphicalViewer(),
                                            modelObjects,
                                            false);

                    if (editParts != null && editParts.size() > 0) {
                        // If there are connection type objects in the
                        // affected objects AND other types of objects then
                        // only select the connection objects (the
                        // connections have only been affected because of
                        // insert object onto connection in this case).
                        List<EditPart> connectionParts =
                                new ArrayList<EditPart>();
                        List<EditPart> otherParts = new ArrayList<EditPart>();

                        for (EditPart ep : editParts) {
                            if (ep instanceof BaseConnectionEditPart) {
                                connectionParts.add(ep);
                            } else {
                                otherParts.add(ep);
                            }
                        }

                        List<EditPart> chosenList = editParts;

                        if (connectionParts.size() > 0 && otherParts.size() > 0) {
                            chosenList = otherParts;
                        }

                        processWidgetImpl.getGraphicalViewer()
                                .setSelection(new StructuredSelection(
                                        chosenList));
                    }
                }

                return;
            }

            private Collection getModelObjectFromCommand(Command command) {

                if (command instanceof EMFCommandWrapper) {
                    EMFCommandWrapper cmd = (EMFCommandWrapper) command;

                    return cmd.getEmfCommand().getAffectedObjects();

                } else if (command instanceof CompoundCommand) {
                    //
                    // Handle compound commands by recursing for each one.
                    Set<Object> affectedObjects = new HashSet<Object>();

                    for (Iterator iterator =
                            ((CompoundCommand) command).getCommands()
                                    .iterator(); iterator.hasNext();) {
                        Object subCmd = iterator.next();

                        if (subCmd instanceof Command) {
                            Collection subObjs =
                                    getModelObjectFromCommand((Command) subCmd);

                            if (subObjs != null && subObjs.size() > 0) {
                                affectedObjects.addAll(subObjs);
                            }
                        }
                    }

                    if (affectedObjects.size() > 0) {
                        return affectedObjects;
                    }
                }

                return null;
            }

            private boolean dragCreateStarted = false;

            @Override
            public void activate() {
                super.activate();
                toolContextHelpUpdater.update(contextHelpId);

                dragCreateStarted = false;
            }

            @Override
            protected void handleFinished() {
                if (getCurrentInput().isControlKeyDown())
                    reactivate();
                else
                    getDomain().loadDefaultTool();
            }

            @Override
            protected boolean handleKeyDown(KeyEvent e) {
                if (e.keyCode == SWT.ESC) {
                    getDomain().loadDefaultTool();
                    return true;
                }
                return super.handleKeyDown(e);
            }

            @Override
            protected Request createTargetRequest() {
                CreateRequest req = (CreateRequest) super.createTargetRequest();
                if (requestExtendedData != null) {
                    req.getExtendedData().putAll(requestExtendedData);
                }

                return req;
            }

            @Override
            protected void updateTargetRequest() {
                // After setting target (so that we know edit part so we can
                // get snap helper
                // re-adjust the location according to snap grid - BEFORE
                // creating the command.
                // SID - Standard updateTargetRequest doesn't employ snap to
                // grid.
                // Unless a DRAG (size) create is being performed (in which
                // case snap works ok and is handledd in
                // updateTargetRequest()).
                if (!noSizeOnCreate && isInState(STATE_DRAG_IN_PROGRESS)) {
                    // If this is the first drag on create then reset the
                    // location so that top left isn't
                    // placed wher the pre-drag centre was.
                    if (!dragCreateStarted) {
                        CreateRequest req = getCreateRequest();

                        Point location = getLocation().getCopy();
                        Dimension size = req.getSize();

                        if (location != null && size != null) {
                            location.x -= size.width / 2;
                            location.y -= size.height / 2;
                            setStartLocation(location);
                        }

                        dragCreateStarted = true;
                    }

                    super.updateTargetRequest();

                } else {
                    CreateRequest req = getCreateRequest();

                    Point location = getLocation().getCopy();

                    // Check if snap helper is set and user hasn't
                    // overridden with Alt key.
                    GraphicalEditPart target =
                            (GraphicalEditPart) getTargetEditPart();

                    if (target instanceof SequenceFlowEditPart) {
                        // We allow creation (splicing) of objects on
                        // sequence flows.
                        // The target for the object will be the container
                        // under the
                        // sequence flow.
                        SequenceFlowLayoutEditPolicy editPolicy =
                                (SequenceFlowLayoutEditPolicy) target
                                        .getEditPolicy(EditPolicy.LAYOUT_ROLE);

                        target = null;

                        if (editPolicy != null) {
                            EditPart tmpEP =
                                    editPolicy.getContainerForSpliceObject(req);

                            if (tmpEP instanceof GraphicalEditPart) {
                                target = (GraphicalEditPart) tmpEP;
                            }
                        }

                    }

                    if (target != null) {

                        Dimension size = null;

                        if (requestExtendedData != null) {
                            Dimension initsize =
                                    (Dimension) (requestExtendedData
                                            .get(PaletteFactory.EXTDATA_INITIAL_SIZE));
                            if (initsize != null) {
                                double scale =
                                        XPDFigureUtilities
                                                .getFigureScale(target
                                                        .getContentPane());
                                size =
                                        new Dimension(
                                                (int) (initsize.width * scale),
                                                (int) (initsize.height * scale));
                                req.setSize(size);
                            }
                        }

                        // Centre object on mouse.
                        if (size != null) {
                            location.x -= size.width / 2;
                            location.y -= size.height / 2;
                        }

                        SnapToHelper helper =
                                (SnapToHelper) target
                                        .getAdapter(SnapToHelper.class);

                        if (!getCurrentInput().isAltKeyDown() && helper != null) {
                            // Can only do a snap to grid if the tool
                            // creator set an initial size on the object in
                            // extended data.
                            // We use this if nothing has set a size in the
                            // request yet (which happens on a drag-size
                            // create).

                            if (size != null) {
                                // Ok, to snap to grid,

                                Rectangle tmprc =
                                        new Rectangle(location.x, location.y,
                                                size.width, size.height);
                                PrecisionRectangle baseRect =
                                        new PrecisionRectangle(tmprc);

                                PrecisionRectangle result =
                                        baseRect.getPreciseCopy();

                                helper.snapRectangle(req,
                                        PositionConstants.HORIZONTAL
                                                | PositionConstants.VERTICAL,
                                        baseRect,
                                        result);
                                location = result.getLocation();

                            }
                        }
                    }
                    req.setLocation(location);
                }

                if (requestExtendedData != null) {
                    getCreateRequest().getExtendedData()
                            .putAll(requestExtendedData);
                }
            }
        };

        tool.setProperties(getToolProperties());
        return tool;
    }

}