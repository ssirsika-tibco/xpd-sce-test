/**
 * 
 */
package com.tibco.xpd.processwidget.adapters;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Adapter for EObject that represent BPMN's Group
 * 
 * @author wzurek
 */
public interface GroupAdapter extends NamedElementAdapter {

    /**
     * Location of the group in Diagram coordinates
     * 
     * @return
     */
    Point getLocation();

    /**
     * @return group size
     */
    Dimension getSize();
    
    /**
     * @return location change command, UnExecutableCommand if not possible
     */
    Command getSetLocationCommand(EditingDomain ed, Point newLocation,
            Dimension newDimension);

}
