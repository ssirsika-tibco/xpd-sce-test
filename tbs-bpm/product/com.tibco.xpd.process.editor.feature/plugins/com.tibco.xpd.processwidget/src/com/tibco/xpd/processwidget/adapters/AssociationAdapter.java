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

package com.tibco.xpd.processwidget.adapters;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * BPMN's association. It can have the same features like other connections
 * (including bendpoints) and in addition it can be directed or not
 * 
 * @author wzurek
 */
public interface AssociationAdapter extends BaseConnectionAdapter {

    /** association has arrow pointing to the target */
    public static AssociationDirectionType DIRECTION_TO_SOURCE = new AssociationDirectionType(
            "toSource"); //$NON-NLS-1$

    /** association has arrow pointing to the source */
    public static AssociationDirectionType DIRECTION_TO_TARGET = new AssociationDirectionType(
            "toTarget"); //$NON-NLS-1$

    /** association has arrows at both ends */
    public static AssociationDirectionType DIRECTION_BOTH = new AssociationDirectionType(
            "both"); //$NON-NLS-1$

    /** association has no arrow */
    public static AssociationDirectionType DIRECTION_NONE = new AssociationDirectionType(
            "none"); //$NON-NLS-1$

    static public class AssociationDirectionType {
        private final String name;

        private AssociationDirectionType(String name) {
            this.name = name;
        }

        public String toString() {
            return "Direction(" + name + ")"; //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Returns true when the association should be displayed as directed from
     * source to target
     * 
     * @return true when the association is directed
     */
    AssociationDirectionType getAssociationDirection();

    /**
     * Produce the command that change if the association is directed or not
     * 
     * @param ed
     * @param directed
     * @return the EMF command
     */
    Command getSetAssociationDirectionCommand(EditingDomain ed,
            AssociationDirectionType directed);
}
