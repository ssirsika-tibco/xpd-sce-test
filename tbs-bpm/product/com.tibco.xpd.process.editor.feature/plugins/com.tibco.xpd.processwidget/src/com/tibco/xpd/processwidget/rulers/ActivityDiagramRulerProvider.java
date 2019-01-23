/*
 ** 
 **  MODULE:             $RCSfile: ActivityDiagramRulerProvider.java $ 
 **                      $Revision: 1.5 $ 
 **                      $Date: 2005/04/19 08:28:53Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: ActivityDiagramRulerProvider.java $ 
 **    Revision 1.5  2005/04/19 08:28:53Z  wzurek 
 **    string externalization 
 **    Revision 1.4  2005/04/01 15:41:24Z  wzurek 
 **    Revision 1.3  2005/03/08 13:06:54Z  wzurek 
 **    Revision 1.2  2005/02/18 17:30:35Z  wzurek 
 **    Revision 1.1  2004/12/03 15:50:27Z  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.xpd.processwidget.rulers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.rulers.RulerChangeListener;
import org.eclipse.gef.rulers.RulerProvider;

/**
 * Ruler Provider for Activity Diagram
 * 
 * @author WojciechZ
 */
public class ActivityDiagramRulerProvider extends RulerProvider {

    /**
     * The Guide
     * 
     * @author wzurek
     */
    public class ActivityGuide implements Serializable {

        private static final long serialVersionUID = 3545519516852564022L;
        
        private int position;
        /**
         * @return
         * 
         */
        public int getPosition() {
            return position;
        }

        /**
         * @param position The position to set.
         */
        public void setPosition(int position) {
            this.position = position;
        }
    }

    class AddGuideCommand extends Command {
        int position;
        /**
         * @param position
         */
        public AddGuideCommand(int position) {
            this.position = position;
        }
        /**
         * @see org.eclipse.gef.commands.Command#execute()
         */
        public void execute() {
            ActivityGuide activityGuide = new ActivityGuide();
            activityGuide.setPosition(position);
            guides.add(activityGuide);
            notifyGuidesListChange(activityGuide);
        }
        /**
         * @see org.eclipse.gef.commands.Command#undo()
         */
        public void undo() {
            notifyGuidesListChange((ActivityGuide) guides
                    .remove(guides.size() - 1));
        }
    }

    class DeleteGuideCommand extends Command {
        int id;
        ActivityGuide deletedGuide;
        /**
         * @param id
         */
        public DeleteGuideCommand(int id) {
            this.id = id;
        }
        /**
         * @see org.eclipse.gef.commands.Command#execute()
         */
        public void execute() {
            deletedGuide = (ActivityGuide) guides.remove(id);
            notifyGuidesListChange(deletedGuide);
        }
        /**
         * @see org.eclipse.gef.commands.Command#undo()
         */
        public void undo() {
            guides.add(id, deletedGuide);
            notifyGuidesListChange(deletedGuide);
        }
    }

    class MoveGuideCommand extends Command {
        int prevPos;
        private final ActivityGuide guide;
        private final int pos;
        /**
         * @param guide
         * @param pos
         */
        public MoveGuideCommand(ActivityGuide guide, int pos) {
            this.guide = guide;
            this.pos = pos;
            notifyGuideMove(guide);
        }
        /**
         * @see org.eclipse.gef.commands.Command#execute()
         */
        public void execute() {
            prevPos = guide.getPosition();
            guide.setPosition(prevPos + pos);
            notifyGuideMove(guide);
        }
        /**
         * @see org.eclipse.gef.commands.Command#undo()
         */
        public void undo() {
            guide.setPosition(prevPos);
        }
    }

    /** */
    List guides = new ArrayList();
    private Object ruler;
    private int unit = UNIT_CENTIMETERS;

    /**
     * @see org.eclipse.gef.rulers.RulerProvider#getUnit()
     */
    public int getUnit() {
        return unit;
    }

    /**
     * @see org.eclipse.gef.rulers.RulerProvider#setUnit(int)
     */
    public void setUnit(int newUnit) {
        unit = newUnit;
    }

    protected void notifyGuidesListChange(ActivityGuide guide) {
        for (int i = 0; i < listeners.size(); i++) {
            ((RulerChangeListener) listeners.get(i))
                    .notifyGuideReparented(guide);
        }
    }

    protected void notifyGuideMove(ActivityGuide guide) {
        for (int i = 0; i < listeners.size(); i++) {
            ((RulerChangeListener) listeners.get(i)).notifyGuideMoved(guide);
        }
    }

    /**
     * @see org.eclipse.gef.rulers.RulerProvider#getCreateGuideCommand(int)
     */
    public Command getCreateGuideCommand(int position) {
        return new AddGuideCommand(position);
    }
    /**
     * @see org.eclipse.gef.rulers.RulerProvider#getMoveGuideCommand(java.lang.Object, int)
     */
    public Command getMoveGuideCommand(Object guide, int positionDelta) {
        return new MoveGuideCommand((ActivityGuide) guide, positionDelta);
    }
    /**
     * @see org.eclipse.gef.rulers.RulerProvider#getDeleteGuideCommand(java.lang.Object)
     */
    public Command getDeleteGuideCommand(Object guide) {
        return new DeleteGuideCommand(guides.indexOf(guide));
    }

    /**
     * @see org.eclipse.gef.rulers.RulerProvider#getGuidePosition(java.lang.Object)
     */
    public int getGuidePosition(Object guide) {
        int position = ((ActivityGuide) guide).getPosition();
        return position;
    }
    /**
     * @see org.eclipse.gef.rulers.RulerProvider#getGuidePositions()
     */
    public int[] getGuidePositions() {
        int[] pos = new int[guides.size()];
        int i = 0;
        for (Iterator iter = guides.iterator(); iter.hasNext();) {
            ActivityGuide guide = (ActivityGuide) iter.next();
            pos[i++] = guide.getPosition();
        }
        return pos;
    }

    /**
     * @see org.eclipse.gef.rulers.RulerProvider#getGuides()
     */
    public List getGuides() {
        return guides;
    }

    /**
     * @see org.eclipse.gef.rulers.RulerProvider#getRuler()
     */
    public Object getRuler() {
        if (ruler == null) {
            ruler = new Object();
        }
        return ruler;
    }
}