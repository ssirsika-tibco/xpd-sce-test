/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.ui.documentation;

/**
 * This interface contains all the information that is necessary to export
 * documentation for a model. Additionally it adds the Priority, ModelTitle and
 * Model Icon path info to the Doc Generation framework.
 * 
 * @author kthombar
 * @since Apr 30, 2015
 */
public interface IDocGenAdditionalModelInfo2 extends IDocGenAdditionalModelInfo {

    /**
     * 
     * @return the title of the model
     */
    public String getModelTitle();

    /**
     * Sets the title of the model.
     * 
     * @param modelContainerTitle
     */
    public void setModelTitle(String modelContainerTitle);

    /**
     * 
     * @return the priority/sequence in which the model elemnts should be
     *         visible in the table.
     */
    public String getModelPriority();

    /**
     * Sets the priority/sequence in which the model elemnts should be visible
     * in the table.
     * 
     * @param modelPriority
     */
    public void setModelPriority(String modelPriority);

    /**
     * 
     * @return the path to the Icon for the model.
     */
    public String getModelIconPath();

    /**
     * Sets the path to the Icon for the model.
     * 
     * @param modelIconPath
     */
    public void setModelIconPath(String modelIconPath);

}
