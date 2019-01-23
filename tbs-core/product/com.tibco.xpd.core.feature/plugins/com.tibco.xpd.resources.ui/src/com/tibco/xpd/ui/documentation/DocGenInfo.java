/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.ui.documentation;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;


/**
 * This class contains all the information 
 * of the post generation 
 * 
 * @author mtorres
 */
public class DocGenInfo implements IDocGenInfo{

    private IStatus status;
    
    private IResource source;
    
    private IPath baseOutputPath;
    
    private IPath generatorOutputPath;

    private IPath mainDocumentationPagePath;
    
    private List<IDocGenModelInfo> docGenModelInfo;
    
    public DocGenInfo() {
        status = Status.OK_STATUS;
    }
    
    @Override
    public IStatus getGenerationStatus() {
        return status;
    }

    @Override
    public void setGenerationStatus(IStatus status) {
        this.status = status;
    }
    
    @Override
    public IResource getSource() {
        return source;
    }
    
    @Override
    public void setSource(IResource source) {
        this.source = source;
    }

    @Override
    public IPath getBaseOutputPath() {
        return baseOutputPath;
    }

    @Override
    public void setBaseOutputPath(IPath baseOutputPath) {
        this.baseOutputPath = baseOutputPath;
    }

    @Override
    public IPath getGeneratorOutputPath() {
        return generatorOutputPath;
    }

    @Override
    public void setGeneratorOutputPath(IPath generatorOutputPath) {
        this.generatorOutputPath = generatorOutputPath;
    }
    
    public IPath getMainDocumentationPagePath() {
        return mainDocumentationPagePath;
    }

    public void setMainDocumentationPagePath(IPath path) {
        this.mainDocumentationPagePath = path;
    }

    @Override
    public List<IDocGenModelInfo> getDocGenModelInfo() {
        return docGenModelInfo;
    }

    @Override
    public void setDocGenModelInfo(List<IDocGenModelInfo> docGenModelInfo) {
        this.docGenModelInfo = docGenModelInfo;
    }
    
}
