/**
 * 
 */
package com.tibco.xpd.processwidget.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.internal.InternalImages;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;

/**
 * @author wzurek
 * 
 */
public class AlignAction extends Action implements ISelectionChangedListener,
        GraphicalViewerAction {

    private GraphicalViewer viewer;

    public AlignAction(int align) {
        alignment = align;
        initUI();
    }

    @Override
    public void setViewer(GraphicalViewer newViewer) {
        if (newViewer == viewer) {
            return;
        }
        if (viewer != null) {
            viewer.removeSelectionChangedListener(this);
        }
        viewer = newViewer;
        if (viewer != null) {
            List selection = viewer.getSelectedEditParts();
            boolean valid = validateSelection(selection);
            setEnabled(valid);
            viewer.addSelectionChangedListener(this);
        } else {
            setEnabled(false);
        }
    }

    protected boolean validateSelection(List selection) {
        boolean valid = !selection.isEmpty();
        for (Iterator iter = selection.iterator(); iter.hasNext();) {
            EditPart ep = (EditPart) iter.next();
            if (!(ep instanceof BaseFlowNodeEditPart)) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        setEnabled(calculateEnabled());
    }

    /**
     * Indicates that the bottom edges should be aligned.
     */
    public static final String ID_ALIGN_BOTTOM =
            GEFActionConstants.ALIGN_BOTTOM;

    /**
     * Indicates that the horizontal centers should be aligned.
     */
    public static final String ID_ALIGN_CENTER =
            GEFActionConstants.ALIGN_CENTER;

    /**
     * Indicates that the left edges should be aligned.
     */
    public static final String ID_ALIGN_LEFT = GEFActionConstants.ALIGN_LEFT;

    /**
     * Indicates that the vertical midpoints should be aligned.
     */
    public static final String ID_ALIGN_MIDDLE =
            GEFActionConstants.ALIGN_MIDDLE;

    /**
     * Indicates that the right edges should be aligned.
     */
    public static final String ID_ALIGN_RIGHT = GEFActionConstants.ALIGN_RIGHT;

    /**
     * Indicates that the top edges should be aligned.
     */
    public static final String ID_ALIGN_TOP = GEFActionConstants.ALIGN_TOP;

    private int alignment;

    private List operationSet;

    /**
     * Returns the alignment rectangle to which all selected parts should be
     * aligned.
     * 
     * @param request
     *            the alignment Request
     * @return the alignment rectangle
     */
    protected Rectangle calculateAlignmentRectangle(Request request) {
        List editparts = getOperationSet(request);
        if (editparts == null || editparts.isEmpty())
            return null;
        GraphicalEditPart part =
                (GraphicalEditPart) editparts.get(editparts.size() - 1);

        /*
         * When dealing with event/gateway use only the shap object bounds not
         * the whole figure (including external text)
         */
        Rectangle rect;
        if (part.getFigure() instanceof ShapeWithDescriptionFigure) {
            rect =
                    ((ShapeWithDescriptionFigure) part.getFigure()).getShape()
                            .getBounds().getCopy();
        } else {
            rect = part.getFigure().getBounds().getCopy();
        }

        /*
         * To compensate for rounding errors later it is better to ensure the
         * alignment rectangle is always odd sized.
         * 
         * It's a bit of a horrible fudge, but with this and some mods made to
         * FlowContainerXYLayoutEditPolicy.getContraintFor() it sems to work in
         * all circumstances.
         */
        if (rect.width % 2 == 0) {
            rect.width += 1;
        }
        if (rect.height % 2 == 0) {
            rect.height += 1;
        }

        part.getFigure().translateToAbsolute(rect);
        return rect;
    }

    /**
     * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
     */
    protected boolean calculateEnabled() {
        operationSet = null;
        Command cmd = createAlignmentCommand();
        if (cmd == null)
            return false;
        return cmd.canExecute();
    }

    private Command createAlignmentCommand() {
        AlignmentRequest request =
                new AlignmentRequest(RequestConstants.REQ_ALIGN);
        request.setAlignmentRectangle(calculateAlignmentRectangle(request));
        request.setAlignment(alignment);
        List editparts = getOperationSet(request);
        if (editparts.size() < 2)
            return null;

        CompoundCommand command = new CompoundCommand();
        command.setDebugLabel(getText());
        for (int i = 0; i < editparts.size(); i++) {
            EditPart editpart = (EditPart) editparts.get(i);
            command.add(editpart.getCommand(request));
        }
        return command;
    }

    /**
     * @see org.eclipse.gef.Disposable#dispose()
     */
    public void dispose() {
        operationSet = Collections.EMPTY_LIST;
    }

    /**
     * Returns the list of editparts which will participate in alignment.
     * 
     * @param request
     *            the alignment request
     * @return the list of parts which will be aligned
     */
    protected List getOperationSet(Request request) {
        if (operationSet != null)
            return operationSet;
        List editparts = new ArrayList(viewer.getSelectedEditParts());
        if (editparts.isEmpty()
                || !(editparts.get(0) instanceof GraphicalEditPart))
            return Collections.EMPTY_LIST;
        Object primary = editparts.get(editparts.size() - 1);
        editparts = ToolUtilities.getSelectionWithoutDependants(editparts);
        ToolUtilities.filterEditPartsUnderstanding(editparts, request);
        if (editparts.size() < 2 || !editparts.contains(primary))
            return Collections.EMPTY_LIST;

        if ((alignment & PositionConstants.TOP_MIDDLE_BOTTOM) != 0) {
            EditPart parent = ((EditPart) editparts.get(0)).getParent();
            for (int i = 1; i < editparts.size(); i++) {
                EditPart part = (EditPart) editparts.get(i);
                if (part.getParent() != parent)
                    return Collections.EMPTY_LIST;
            }
        }

        return editparts;
    }

    /**
     * Initializes the actions UI presentation.
     */
    protected void initUI() {
        switch (alignment) {
        case PositionConstants.LEFT:
            setId(GEFActionConstants.ALIGN_LEFT);
            setText(GEFMessages.AlignLeftAction_Label);
            setToolTipText(GEFMessages.AlignLeftAction_Tooltip);
            setImageDescriptor(InternalImages.DESC_HORZ_ALIGN_LEFT);
            setDisabledImageDescriptor(InternalImages.DESC_HORZ_ALIGN_LEFT_DIS);
            break;

        case PositionConstants.RIGHT:
            setId(GEFActionConstants.ALIGN_RIGHT);
            setText(GEFMessages.AlignRightAction_Label);
            setToolTipText(GEFMessages.AlignRightAction_Tooltip);
            setImageDescriptor(InternalImages.DESC_HORZ_ALIGN_RIGHT);
            setDisabledImageDescriptor(InternalImages.DESC_HORZ_ALIGN_RIGHT_DIS);
            break;

        case PositionConstants.TOP:
            setId(GEFActionConstants.ALIGN_TOP);
            setText(GEFMessages.AlignTopAction_Label);
            setToolTipText(GEFMessages.AlignTopAction_Tooltip);
            setImageDescriptor(InternalImages.DESC_VERT_ALIGN_TOP);
            setDisabledImageDescriptor(InternalImages.DESC_VERT_ALIGN_TOP_DIS);
            break;

        case PositionConstants.BOTTOM:
            setId(GEFActionConstants.ALIGN_BOTTOM);
            setText(GEFMessages.AlignBottomAction_Label);
            setToolTipText(GEFMessages.AlignBottomAction_Tooltip);
            setImageDescriptor(InternalImages.DESC_VERT_ALIGN_BOTTOM);
            setDisabledImageDescriptor(InternalImages.DESC_VERT_ALIGN_BOTTOM_DIS);
            break;

        case PositionConstants.CENTER:
            setId(GEFActionConstants.ALIGN_CENTER);
            setText(GEFMessages.AlignCenterAction_Label);
            setToolTipText(GEFMessages.AlignCenterAction_Tooltip);
            setImageDescriptor(InternalImages.DESC_HORZ_ALIGN_CENTER);
            setDisabledImageDescriptor(InternalImages.DESC_HORZ_ALIGN_CENTER_DIS);
            break;

        case PositionConstants.MIDDLE:
            setId(GEFActionConstants.ALIGN_MIDDLE);
            setText(GEFMessages.AlignMiddleAction_Label);
            setToolTipText(GEFMessages.AlignMiddleAction_Tooltip);
            setImageDescriptor(InternalImages.DESC_VERT_ALIGN_MIDDLE);
            setDisabledImageDescriptor(InternalImages.DESC_VERT_ALIGN_MIDDLE_DIS);
            break;
        }
    }

    /**
     * @see org.eclipse.jface.action.IAction#run()
     */
    @Override
    public void run() {
        operationSet = null;
        viewer.getEditDomain().getCommandStack()
                .execute(createAlignmentCommand());
    }
}
