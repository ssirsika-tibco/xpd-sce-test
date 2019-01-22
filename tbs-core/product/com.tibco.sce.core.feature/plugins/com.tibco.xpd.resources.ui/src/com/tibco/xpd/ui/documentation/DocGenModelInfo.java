/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.ui.documentation;

import java.util.List;

/**
 * This class contains all the information of the generation specific Model
 * required for documentation export.
 * 
 * @author mtorres
 */
public class DocGenModelInfo implements IDocGenAdditionalModelInfo2 {

    private String thumbnailPath;

    private String name;

    private String modelName;

    private String description;

    private String changeDate;

    private String author;

    private String notes;

    private String typeContainer;

    private String type;

    private String link;

    private List<IDocGenModelInfo> docGenModelInfoList;

    private String extraStuff;

    private String iconPath;

    private String modelContainerTitle;

    private String modelPriority;

    private String modelIconPath;

    @Override
    public String getThumbnailPath() {
        return thumbnailPath;
    }

    @Override
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getChangeDate() {
        return changeDate;
    }

    @Override
    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getTypeContainer() {
        return typeContainer;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setTypeContainer(String typeContainer) {
        this.typeContainer = typeContainer;
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLink() {
        return link;
    }

    @Override
    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public List<IDocGenModelInfo> getDocGenModelInfo() {
        return docGenModelInfoList;
    }

    @Override
    public void setDocGenModelInfo(List<IDocGenModelInfo> docGenModelInfoList) {
        this.docGenModelInfoList = docGenModelInfoList;
    }

    @Override
    public String getExtraStuff() {
        return extraStuff;
    }

    @Override
    public void setExtraStuff(String extraStuff) {
        this.extraStuff = extraStuff;
    }

    /**
     * 
     * @see com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo#getIconPath()
     * 
     * @return
     */
    @Override
    public String getIconPath() {

        return iconPath;
    }

    /**
     * 
     * @see com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo#setIconPath(java.lang.String)
     * 
     * @param iconPath
     */
    @Override
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;

    }

    /**
     * @see com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo2#getModelTitle()
     * 
     * @return
     */
    @Override
    public String getModelTitle() {

        return modelContainerTitle;
    }

    /**
     * @see com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo2#setModelTitle(java.lang.String)
     * 
     * @param modelContainerTitle
     */
    @Override
    public void setModelTitle(String modelContainerTitle) {
        this.modelContainerTitle = modelContainerTitle;

    }

    /**
     * @see com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo2#getModelPriority()
     * 
     * @return
     */
    @Override
    public String getModelPriority() {

        return modelPriority;
    }

    /**
     * @see com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo2#setModelPriority(java.lang.String)
     * 
     * @param modelPriority
     */
    @Override
    public void setModelPriority(String modelPriority) {
        this.modelPriority = modelPriority;

    }

    /**
     * @see com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo2#getModelIconPath()
     * 
     * @return
     */
    @Override
    public String getModelIconPath() {

        return modelIconPath;
    }

    /**
     * @see com.tibco.xpd.ui.documentation.IDocGenAdditionalModelInfo2#setModelIconPath(java.lang.String)
     * 
     * @param modelIconPath
     */
    @Override
    public void setModelIconPath(String modelIconPath) {
        this.modelIconPath = modelIconPath;

    }

}
