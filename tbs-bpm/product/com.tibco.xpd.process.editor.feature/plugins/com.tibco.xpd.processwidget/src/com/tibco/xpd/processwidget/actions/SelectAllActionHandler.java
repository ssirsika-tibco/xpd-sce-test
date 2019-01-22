package com.tibco.xpd.processwidget.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

public class SelectAllActionHandler extends Action {

    private ProcessWidget widget;

    public SelectAllActionHandler(ProcessWidget widget) {
        this.widget = widget;
        setEnabled(true);
    }

    @Override
    public String getId() {
        return ActionFactory.SELECT_ALL.getId();
    }

    @Override
    public void run() {
        GraphicalViewer viewer = widget.getGraphicalViewer();

        // 
        // Find all activities and artifacts.
        // If there are any, make them the selection.

        EditPart root = viewer.getRootEditPart();

        List<EditPart> actsAndArts = new ArrayList<EditPart>();

        List<EditPart> pools = new ArrayList<EditPart>();

        findEditParts(root, actsAndArts, pools);

        if (actsAndArts.size() > 0) {
            viewer.setSelection(new StructuredSelection(actsAndArts));

        } else if (pools.size() > 0) {
            viewer.setSelection(new StructuredSelection(pools));

        } else {
            viewer.setSelection(new StructuredSelection());
        }

    }

    /**
     * Recursively find activities and artifacts from given root.
     * 
     * @param root
     * @param actsAndArts
     * @param pools
     */
    private void findEditParts(EditPart parent, List<EditPart> actsAndArts,
            List<EditPart> pools) {

        if (parent instanceof BaseGraphicalEditPart) {
            if ((parent instanceof PoolEditPart)) {
                pools.add(parent);
            } else if (!(parent instanceof ProcessEditPart)
                    && !(parent instanceof LaneEditPart)) {
                actsAndArts.add(parent);
            }
        }

        /* XPD-1695: Don't select objects in collapsed lanes/emb-sub-procs */
        if (!isCollapsedLaneOrSubProc(parent)) {
            // Recurs thru children.
            for (Iterator iter = parent.getChildren().iterator(); iter
                    .hasNext();) {
                EditPart child = (EditPart) iter.next();

                findEditParts(child, actsAndArts, pools);
            }
        }

    }

    /**
     * XPD-1695:
     * 
     * @param part
     * @return <code>true</code> if the object a collapsed lane or edit part.
     */
    private boolean isCollapsedLaneOrSubProc(EditPart part) {
        if (part instanceof LaneEditPart) {
            if (((LaneEditPart) part).isClosed()) {
                return true;
            }

            PoolEditPart parentPool = ((LaneEditPart) part).getParentPool();
            if (parentPool.isClosed()) {
                /* Count lanes as collapsed if parent pool is. */
                return true;
            }

        } else if (part instanceof TaskEditPart) {
            if (((TaskEditPart) part).isEmbeddedSubProc()
                    && ((TaskEditPart) part).isCollapsedEmbeddedSubproc()) {
                return true;
            }
        }
        return false;
    }

}
