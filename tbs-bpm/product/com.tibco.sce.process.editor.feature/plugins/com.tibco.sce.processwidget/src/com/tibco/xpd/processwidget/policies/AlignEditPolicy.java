/**
 * 
 */
package com.tibco.xpd.processwidget.policies;

import java.util.Arrays;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * @author wzurek
 * 
 */
public class AlignEditPolicy extends GraphicalEditPolicy {

    public static final String BASE_EDIT_PART_KEY = "base"; //$NON-NLS-1$

    public static final String ALIGN_TYPE_KEY = "align"; //$NON-NLS-1$

    public static String ALIGN_TOP = "ALIGN_TOP"; //$NON-NLS-1$

    public static String ALIGN_MIDDLE = "ALIGN_MIDDLE"; //$NON-NLS-1$

    public static String ALIGN_BOTTOM = "ALIGN_BOTTOM"; //$NON-NLS-1$

    public static String ALIGN_LEFT = "ALIGN_LEFT"; //$NON-NLS-1$

    public static String ALIGN_CENTER = "ALIGN_CENTER"; //$NON-NLS-1$

    public static String ALIGN_RIGHT = "ALIGN_RIGHT"; //$NON-NLS-1$

    private static String[] ALIGNS = new String[] { ALIGN_TOP, ALIGN_MIDDLE,
            ALIGN_BOTTOM, ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT };

    private static List ALIGNS_LIST = Arrays.asList(ALIGNS);

    /**
     * True, it is the ALIGN request and has proper extended data
     */
    public boolean understandsRequest(Request req) {
        return req.getType().equals(RequestConstants.REQ_ALIGN)
                && ALIGNS_LIST.contains(req.getExtendedData().get(
                        ALIGN_TYPE_KEY));
    }

    /**
     * Create align command for host edit part
     */
    public Command getCommand(Request req) {
        Object align = req.getExtendedData().get(ALIGN_TYPE_KEY);
        GraphicalEditPart base = (GraphicalEditPart) req.getExtendedData().get(
                BASE_EDIT_PART_KEY);
        if (req.getType().equals(RequestConstants.REQ_ALIGN)
                && ALIGNS_LIST.contains(align) && base != null) {
            if (align.equals(ALIGN_TOP) || align.equals(ALIGN_TOP)
                    || align.equals(ALIGN_TOP)) {
                // for vertical align ignore edit parts that are on different
                // lanes
                if (base.getParent() != getHost().getParent()) {
                    return null;
                }
            }
            Rectangle baseBnds = base.getFigure().getBounds();
            Rectangle bnds = getHostFigure().getBounds();

            Point offset = new Point();

            if (align.equals(ALIGN_TOP)) {
                offset.y = baseBnds.y - bnds.y;
            } else if (align.equals(ALIGN_MIDDLE)) {
                offset.y = baseBnds.y + baseBnds.height / 2 - bnds.y
                        + bnds.height / 2;
            } else if (align.equals(ALIGN_BOTTOM)) {
                offset.y = baseBnds.y + baseBnds.height - bnds.y + bnds.height;
            } else if (align.equals(ALIGN_LEFT)) {
                offset.x = baseBnds.x - bnds.x;
            } else if (align.equals(ALIGN_CENTER)) {
                offset.x = baseBnds.x + baseBnds.width / 2 - bnds.x
                        + bnds.width / 2;
            } else if (align.equals(ALIGN_RIGHT)) {
                offset.x = baseBnds.x + baseBnds.width - bnds.x + bnds.width;
            }
            ChangeBoundsRequest cb = new ChangeBoundsRequest();
            cb.setType(RequestConstants.REQ_MOVE);
            cb.setMoveDelta(offset);
            return getHost().getCommand(cb);
        }
        return null;
    }
}
