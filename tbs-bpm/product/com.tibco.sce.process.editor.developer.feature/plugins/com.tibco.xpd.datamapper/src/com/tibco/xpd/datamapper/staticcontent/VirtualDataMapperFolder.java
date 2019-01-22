/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.staticcontent;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * The data mapper content object that represents a virtual folder.
 * <p>
 * This folder is not included in data mapping object paths etc (i.e. it is just
 * a visual container).
 * 
 * 
 * @author aallway
 * @since 14 May 2015
 */
public class VirtualDataMapperFolder extends ConceptPath {

    private String id;

    private String label;

    private List<ConceptPath> children = new ArrayList<ConceptPath>();

    private AbstractUIPlugin plugin;

    private String imageKey;

    private Image img;

    /**
     * @param id
     *            Unique Id for Folder.
     * @param label
     *            Display Label for Folder.
     * @param plugin
     *            The plugin providing the image registry.
     * @parm imageKey The key of the image in the registry.
     */
    public VirtualDataMapperFolder(String id, String label,
            AbstractUIPlugin plugin, String imageKey) {
        this(id, label, plugin, imageKey, null);
    }

    /**
     * @param id
     *            Unique Id for Folder.
     * @param label
     *            Display Label for Folder.
     * @param plugin
     *            The plugin providing the image registry.
     * @parm imageKey The key of the image in the registry.
     * @param parent
     *            The parent of this folder (or <code>null</code> for root
     *            folder).
     */
    public VirtualDataMapperFolder(String id, String label,
            AbstractUIPlugin plugin, String imageKey, ConceptPath parent) {
        super(parent, id, null);
        this.id = id;
        this.label = label;
        this.plugin = plugin;
        this.imageKey = imageKey;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath#isArtificialConceptPath()
     * 
     * @return
     */
    @Override
    public boolean isArtificialConceptPath() {
        return true;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath#getChildren()
     * 
     * @return
     */
    @Override
    public List<ConceptPath> getChildren() {
        return children;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the image to use for folder.
     */
    public Image getImage() {
        if (img == null) {
            img = plugin.getImageRegistry().get(imageKey);
        }
        return img;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VirtualDataMapperFolder) {
            if (id.equals(((VirtualDataMapperFolder) obj).getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        /*
         * return the label
         */
        String lbl = getLabel();

        return lbl != null ? lbl : super.toString();
    }
}
