/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

/**
 * The TransformShell public interface.
 * @author nwilson
 */
public interface ITransformShell {

    /**
     * Sets the shell bounds and radius of the rounded corners.
     * 
     * @param x The x position.
     * @param y The y position.
     * @param width The shell width.
     * @param height The shell height.
     * @param radius The radius of the rounded corners.
     */
    void setBounds(int x, int y, int width, int height, int radius);

    /**
     * Sets the current section.
     * 
     * @param section The new section.
     */
    void setSection(ITransformSection section);

    /**
     * Opens the shell.
     */
    void open();

}
