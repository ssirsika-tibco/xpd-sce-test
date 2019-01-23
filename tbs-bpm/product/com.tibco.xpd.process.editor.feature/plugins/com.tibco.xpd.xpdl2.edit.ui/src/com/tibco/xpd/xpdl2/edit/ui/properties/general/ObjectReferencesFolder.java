package com.tibco.xpd.xpdl2.edit.ui.properties.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ObjectReferencesFolder {

    private String name = ""; //$NON-NLS-1$

    private Image image = null;

    private ObjectReferencesFolder parentFolder = null;

    private List<ObjectReferencesFolder> childFolders = null;

    private List<ObjectReferencesItem> childItems = null;

    private boolean sortChildren = true;

    public ObjectReferencesFolder(String name, Image image) {
        if (name != null) {
            this.name = name;
        }
        this.image = image;
    }

    public ObjectReferencesFolder(NamedElement containerElement) {
        name = Xpdl2ModelUtil.getDisplayNameOrName(containerElement);

        image = WorkingCopyUtil.getImage(containerElement);
    }

    public void setSortChildren(boolean sortChildren) {
        this.sortChildren = sortChildren;
    }

    public void addChild(ObjectReferencesFolder folder) {
        if (childFolders == null) {
            childFolders = new ArrayList<ObjectReferencesFolder>();
        }

        childFolders.add(folder);
        folder.parentFolder = this;
    }

    public void addChild(ObjectReferencesItem item) {
        if (childItems == null) {
            childItems = new ArrayList<ObjectReferencesItem>();
        }

        childItems.add(item);

        item._setParentFolder(this);
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public ObjectReferencesFolder getParentFolder() {
        return parentFolder;
    }

    public List<Object> getChildren() {
        List<Object> children = new ArrayList<Object>();

        if (childFolders != null) {
            if (sortChildren) {
                Collections.sort(childFolders,
                        new Comparator<ObjectReferencesFolder>() {

                            public int compare(ObjectReferencesFolder o1,
                                    ObjectReferencesFolder o2) {
                                return o1.getName().compareToIgnoreCase(o2
                                        .getName());
                            }
                        });
            }
            children.addAll(childFolders);
        }

        if (childItems != null) {
            if (sortChildren) {
                Collections.sort(childItems,
                        new Comparator<ObjectReferencesItem>() {

                            public int compare(ObjectReferencesItem o1,
                                    ObjectReferencesItem o2) {
                                return o1.getName().compareToIgnoreCase(o2
                                        .getName());
                            }
                        });
            }
            children.addAll(childItems);
        }

        return children;
    }
}
