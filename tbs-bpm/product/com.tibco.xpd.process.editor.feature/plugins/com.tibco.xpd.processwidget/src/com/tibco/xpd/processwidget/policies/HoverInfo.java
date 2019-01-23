/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;

/**
 * @author wzurek
 */
public class HoverInfo {

    public static final class HoverInfoProperty {
        public String name;

        public String value;

        public HoverInfoProperty(String name, String value) {
            this.name = name;
            this.value = value;
        }

    }

    public HoverInfo(String title) {
        this.title = title;
    }

    public HoverInfo(String title, String text) {
        this(title);
        this.text = text;
    }

    private String title;

    private List properties = new ArrayList();

    private String text;

    private String documentationURL;

    private EditPart hostEditPart;

    public List getProperties() {
        return properties;
    }

    public void setProperties(List properties) {
        this.properties = properties;
        if (properties != null && !properties.isEmpty()) {
            text = null;
        }
    }

    public void addProperty(String name, String value) {
        text = null;
        properties.add(new HoverInfoProperty(name, value));
    }

    public void clearProperties() {
        properties.clear();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        clearProperties();
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HoverInfoProperty getProperty(int index) {
        return (HoverInfoProperty) properties.get(index);
    }

    /**
     * Add documentation URL. If not null or "" then extra hyperlink control
     * added to bottom of tooltip.
     * 
     * @param documentationURL
     */
    public void setDocumentationURL(String documentationURL,
            EditPart hostEditPart) {
        this.documentationURL = documentationURL;
        this.hostEditPart = hostEditPart;
    }

    /**
     * @return the documentationURL
     */
    public String getDocumentationURL() {
        return documentationURL;
    }

    /**
     * @return the hostEditPart
     */
    public EditPart getHostEditPart() {
        return hostEditPart;
    }
}
