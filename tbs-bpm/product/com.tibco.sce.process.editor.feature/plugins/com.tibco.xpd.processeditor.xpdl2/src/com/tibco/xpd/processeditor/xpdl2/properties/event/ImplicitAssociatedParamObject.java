/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import org.eclipse.swt.graphics.Image;

/**
 * This object can be used as the content for associated parameter table for a
 * dummy all data implicitly associated entry.
 * 
 * @author aallway
 * @since 3.2
 */
public class ImplicitAssociatedParamObject {

    private String paramName;

    private String mode;

    private String description;

    private boolean mandatory;

    private Image image;

    public ImplicitAssociatedParamObject(String paramName, String mode,
            String description, boolean mandatory, Image image) {
        super();
        this.paramName = paramName;
        this.mode = mode;
        this.description = description;
        this.mandatory = mandatory;
        this.image = image;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @return the paramName
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * @param paramName
     *            the paramName to set
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode
     *            the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the mandatory
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * @param mandatory
     *            the mandatory to set
     */
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

}
