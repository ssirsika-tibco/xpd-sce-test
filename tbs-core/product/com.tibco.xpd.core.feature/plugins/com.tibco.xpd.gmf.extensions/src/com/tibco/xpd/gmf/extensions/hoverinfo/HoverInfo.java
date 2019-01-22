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

package com.tibco.xpd.gmf.extensions.hoverinfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Information model for tooltip of the figure. It contains a title and set of
 * properties (name, value pairs) or text. 
 * 
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

	public HoverInfo() {
	}

	public HoverInfo(String title) {
		this.title = title;
	}

	public HoverInfo(String title, String text) {
		this(title);
		this.text = text;
	}

	private String title;

	private String text;

	private List<HoverInfoProperty> properties = new ArrayList<HoverInfoProperty>();

	public List getProperties() {
		return properties;
	}

	public void setProperties(List<HoverInfoProperty> properties) {
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
}
