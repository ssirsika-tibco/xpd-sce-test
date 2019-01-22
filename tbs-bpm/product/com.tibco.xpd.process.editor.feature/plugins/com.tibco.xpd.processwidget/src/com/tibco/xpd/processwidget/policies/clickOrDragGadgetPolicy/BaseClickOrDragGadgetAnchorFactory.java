/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.IFigure;

/**
 * Connection anchor factory for various parts of the click or drag edit policy.
 * 
 * @author aallway
 * @since 3.2
 */
public class BaseClickOrDragGadgetAnchorFactory {

    public ConnectionAnchor getAnchor(IFigure owner) {
        if (owner instanceof ClickOrDragGadgetHandle) {
            return new EllipseAnchor(owner);
        }

        return new ChopboxAnchor(owner);
    }

}
