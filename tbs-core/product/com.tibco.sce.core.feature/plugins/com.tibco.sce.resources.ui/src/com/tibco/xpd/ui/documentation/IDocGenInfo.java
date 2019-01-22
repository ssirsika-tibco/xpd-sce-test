/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.ui.documentation;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;


/**
 * This interface contains all the information 
 * of the generation 
 * 
 * @author mtorres
 */
public interface IDocGenInfo {
    /**
     * Returns the status of the generation
     * 
     * @return IStatus
     */
    IStatus getGenerationStatus();
    /**
     * Sets the status of the generation
     * 
     * @param status
     */
    void setGenerationStatus(IStatus status);
    /**
     * Returns the source resource of the document
     * 
     * @return the source resource
     */
    IResource getSource();
    /**
     * Sets the source resource of the document
     * 
     * @param the source resource
     */
    void setSource(IResource source);
    /**
     * Returns the root output path of the document generation
     * 
     * @return the path
     */
    IPath getBaseOutputPath();
    /**
     * Sets the root output path of the document generation
     * 
     * @param baseOutputPath
     */
    void setBaseOutputPath(IPath baseOutputPath);
    /**
     * Returns the output path of the document generation,
     * this is normally a level down from the BaseOutputPath
     * 
     * @return generatorOutputPath
     */
    IPath getGeneratorOutputPath();
    /**
     * Returns the output path of the document generation,
     * this is normally a level down from the BaseOutputPath
     * 
     * @param generatorOutputPath
     */
    void setGeneratorOutputPath(IPath generatorOutputPath);  
    
    /**
     * Returns the path to the main page
     * 
     * @return the main documentation page path
     */
    IPath getMainDocumentationPagePath();
    /**
     * Sets the path to the main page
     * 
     * @param path
     */
    void setMainDocumentationPagePath(IPath path);
    
    /**
     * Returns the information specific to the model that 
     * has been generated
     * 
     * @return IDocGenModelInfo
     */
    List<IDocGenModelInfo> getDocGenModelInfo();
    
    /**
     *  Sets the information specific to the model that 
     *  has been generated
     * 
     * @param docGenModelInfos
     */
    void setDocGenModelInfo(List<IDocGenModelInfo> docGenModelInfos);
    
}
