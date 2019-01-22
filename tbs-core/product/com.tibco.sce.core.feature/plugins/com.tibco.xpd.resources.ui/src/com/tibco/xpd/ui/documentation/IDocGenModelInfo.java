/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.ui.documentation;

import java.util.List;

/**
 * This interface contains all the information of the generation specific to the
 * model
 * 
 * @author mtorres
 * 
 * @deprecated Use {@link IDocGenAdditionalModelInfo2} instead because it
 *             additionally provides methods to add the Priority, ModelTitle and
 *             Model Icon path info to the Doc Generation framework.
 */
@Deprecated
public interface IDocGenModelInfo {

    /**
     * Returns the typeContainer
     * 
     * @return String
     */
    public String getTypeContainer();

    /**
     * Sets the typeContainer
     * 
     * @param typeContainer
     */
    public void setTypeContainer(String typeContainer);

    /**
     * Returns the type
     * 
     * @return String
     */
    public String getType();

    /**
     * Sets the type
     * 
     * @param type
     */
    public void setType(String type);

    /**
     * Returns the name
     * 
     * @return String
     */
    public String getName();

    /**
     * Sets the name
     * 
     * @param name
     */
    public void setName(String name);

    /**
     * Returns the modelName
     * 
     * @return String
     */
    public String getModelName();

    /**
     * Sets the modelName
     * 
     * @param modelName
     */
    public void setModelName(String modelName);

    /**
     * Returns the thumbnail path
     * 
     * @return String
     */
    public String getThumbnailPath();

    /**
     * Sets the thumbnail
     * 
     * @param thumbnail
     */
    public void setThumbnailPath(String thumbnailPath);

    /**
     * Returns the description
     * 
     * @return String
     */
    public String getDescription();

    /**
     * Sets the description
     * 
     * @param description
     */
    public void setDescription(String description);

    /**
     * Returns the changeDate
     * 
     * @return String
     */
    public String getChangeDate();

    /**
     * Sets the changeDate
     * 
     * @param changeDate
     */
    public void setChangeDate(String changeDate);

    /**
     * Returns the author
     * 
     * @return String
     */
    public String getAuthor();

    /**
     * Sets the author
     * 
     * @param author
     */
    public void setAuthor(String author);

    /**
     * Returns the notes
     * 
     * @return String
     */
    public String getNotes();

    /**
     * Sets the notes
     * 
     * @param notes
     */
    public void setNotes(String notes);

    /**
     * Returns the notes
     * 
     * @return String
     */
    public String getLink();

    /**
     * Sets the link
     * 
     * @param link
     */
    public void setLink(String link);

    /**
     * Returns the list of doc gen model info
     * 
     * @return IDocGenModelInfo list
     */
    public List<IDocGenModelInfo> getDocGenModelInfo();

    /**
     * Sets the list of doc gen model info
     * 
     * @param IDocGenModelInfo
     *            list
     */
    public void setDocGenModelInfo(List<IDocGenModelInfo> docGenModelInfoList);

    /**
     * Returns the extra stuff
     * 
     * @return String
     */
    public String getExtraStuff();

    /**
     * Sets the extra stuff
     * 
     * @param extraStuff
     */
    public void setExtraStuff(String extraStuff);

}
