/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

/**
 * A section that can contribute controls and graphical display to a
 * TransformShell.
 * @author nwilson
 */
public interface ITransformSection {

    /**
     * Adds controls to the parent component.
     * @param parent The parent component.
     */
    void contribute(Composite parent);

    /**
     * Adds graphical elements to the parent canvas.
     * @param gc The graphics context.
     */
    void contribute(GC gc);

    /**
     * @return The title of the section.
     */
    String getTitle();

    /**
     * @return The preferred size for the dialog.
     */
    Point getPreferredSize();
    
    /**
     * @param mapping The mapping.
     */
    void setInput(Mapping mapping);

    /**
     * Called when the parent shell is closing.
     */
    void close();

    /**
     * @param owner The owning object.
     * @param direction The mapping direction.
     */
    void setOwner(Object owner, MappingDirection direction);

    /**
     * @return The image used on the mapper.
     */
    Image getTransformImage();
}
